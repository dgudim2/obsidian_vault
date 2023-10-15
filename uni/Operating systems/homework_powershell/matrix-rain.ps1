class CharGenerator {
    [char[]]$possibleGlyphs
    [int]$glyphCount

    CharGenerator() {
        # Different sets of glyphs
        #$this.possibleGlyphs="     `"ACBDEFGHIJKLMNOPQRSTUVWXYZ1234567890!@#$%^&*()<>?{}[]<>~".ToCharArray()
        #$this.possibleGlyphs = ("   ｻｶﾎﾖｦﾛｯβαγπΣθΩΞφµ@#$%&8953" -split "").Where({$_ -ne ""})
        $this.possibleGlyphs="   +=1234567890!@#$%^&*()<>?{}[]<>~".ToCharArray()

        $this.glyphCount = $this.possibleGlyphs.Count
    }

    [char]RandomGlyph() {
        return $this.possibleGlyphs[$script:rand.Next($this.glyphCount + 1)]
    }
}

class Glyph {
    [int]$LastPosition
    [int]$CurrentPosition
    [int]$Velocity
    [int]$Intensity
    [double]$IntensityChange
    [char]$CurrentGlyph
    [char]$LastGlyph

    [int]$index
    [float[]]$ColorDivs

    Glyph([int]$index, [float[]]$colors) {
        $this.index = $index
        $this.Setup()
        $this.ColorDivs = $colors
    }

    [void]Setup() {
        $this.CurrentPosition = $script:rand.Next(-$script:ScreenHeight,.6 * $script:ScreenHeight)
        $this.Velocity = 1
        $this.Intensity = 0
        $this.IntensityChange = ($script:rand.Next(1,20) / 100)
        $this.CurrentGlyph = $script:chargen.RandomGlyph()
        $this.LastGlyph = $script:chargen.RandomGlyph()
    }

    [void]Move() {
        $this.LastPosition = $this.CurrentPosition
        $this.CurrentPosition += $this.Velocity

        # Increment and clamp intensity to avoid weird stuff
        $this.Intensity = [Math]::Clamp($this.Intensity + 255 * $this.IntensityChange, 0, 255)

        # Switch glyphs
        $this.LastGlyph = $this.CurrentGlyph

        # Random-length trails
        if ($this.CurrentGlyph -ne ' ') {
            $this.CurrentGlyph = $script:chargen.RandomGlyph()
        }

        # Reset to the start, we are out of bounds
        if ($this.CurrentPosition -gt $script:ScreenHeight -1) {
            $this.Setup()
        }
    }

    [void]Render() {
        # Draw the leading Glyph
        if ($this.CurrentPosition -ge 0) {
            [Console]::CursorLeft = $this.index
            [Console]::CursorTop = [Math]::Floor($this.CurrentPosition)
            [Console]::Write("$script:e[48;5;16m$script:e[38;5;15m$($this.CurrentGlyph)")
        }

        # Draw the trail
        if ($this.LastPosition -ge 0) {
            [Console]::CursorLeft = $this.index
            [Console]::CursorTop = [Math]::Floor($this.LastPosition)
            $red = [Math]::Floor($this.Intensity/$this.ColorDivs[0])
            $green = [Math]::Floor($this.Intensity/$this.ColorDivs[1])
            $blue = [Math]::Floor($this.Intensity/$this.ColorDivs[2])
            [Console]::Write("$script:e[48;5;16m$script:e[38;2;$red;$green;$blue`m$($this.LastGlyph)")
        }
    }
}

<#
.Synopsis
    Display matrix 'Rain' in the console
.DESCRIPTION
    This function fills the screen with black and starts an animation with falling characters as seen in the movie "The Matrix"
.EXAMPLE
    Matrix-Rain -Keyexit
.EXAMPLE
    Matrix-Rain -Color @(1, 4, 1.9) -Keyexit
                            ^
                            |
                 This is magenta
.EXAMPLE
    Matrix-Rain -Color @(3, 4, 1) -Keyexit -Speed 15

    Fast and purple
#>
function Matrix-Rain {

    [CmdletBinding(PositionalBinding = $false)]
    param(
        # Target speed of the glyphs (delta time, so it's inverted)
        [Parameter(Mandatory = $false, HelpMessage = 'Please enter the target speed on the glyphs:')]
        [ValidateRange (10, 70)]
        [float]$Speed = 33.33, # 30 fps

        # Color division factors (R,G,B)
        [Parameter(Mandatory = $false, HelpMessage = 'Please enter the desired colors on the glyphs:')]
        [ValidateCount (3, 3)]
        [ValidateRange (1, 256)]
        [float[]]$Colors = @(1.1, 1, 8),

        # Whether to exit if any key was pressed
        [Parameter(Mandatory = $false, HelpMessage = "Do you want to exit on any keypress?:")] [switch]$Keyexit
    )

    $script:rand = [System.Random]::new()
    $script:chargen = [CharGenerator]::new()

    # $host is shorthand for (Get-Host)
    $script:ScreenWidth = $host.UI.RawUI.WindowSize.Width
    $script:ScreenHeight = $host.UI.RawUI.WindowSize.Height

    $script:e=[char]27 # Escape character

    #create array of glyphs, one for each column
    [Glyph[]]$AllGlyphs=[Glyph[]]::new($script:ScreenWidth)
    for ($i=0; $i -lt $AllGlyphs.Count; $i++) {
        $AllGlyphs[$i]=[Glyph]::new($i, $Colors)
    }

    # Disable cursor and save BG and FG colors
    [Console]::CursorVisible = $false
    $originalBG = [Console]::BackgroundColor
    $originalFG = [Console]::ForegroundColor

    #Blank-Screen
    Write-Host "$e[38;5;16m$e[48;5;16m$e[H$e[Jm" -NoNewline

    $stopwatch = [System.Diagnostics.Stopwatch]::StartNew()

    #loop until a key is pressed
    while (-not [System.Console]::KeyAvailable -or -not $Keyexit) {
        if ($stopwatch.Elapsed.TotalMilliseconds -gt $Speed) {
            # Loop through each glyph and move it
            for ($i = 0; $i -lt $script:ScreenWidth; $i++) {
                $curr = $AllGlyphs[$i]
                $curr.Move()
                $curr.Render()
            }
            $stopwatch.Restart()
        }
    }

    # Restore console colors
    [Console]::BackgroundColor = $originalBG
    [Console]::ForegroundColor = $originalFG

    # Disregard the keystroke that was pressed to exit the loop
    $null = [Console]::ReadKey($true)

    Clear-Host
}
