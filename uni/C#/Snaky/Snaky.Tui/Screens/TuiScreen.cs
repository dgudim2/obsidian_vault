using System.Runtime.InteropServices;
using Snaky.CommonUI.Screens;
using Snaky.Core.Interfaces;
using Snaky.Core.Utils;
using Snaky.Tui.UiElements;
using Snaky.Tui.Utils;

namespace Snaky.Tui.Screens;

public abstract class TuiScreen : BaseScreen
{
    private bool _windowSizeValid;
    private readonly MessageBox _warningMessageBox;
    
    protected TuiScreen()
    {
        PosixSignalRegistration.Create(PosixSignal.SIGWINCH, _ => _windowSizeValid = false);
        _warningMessageBox = new MessageBox(this, "<Terminal window is too small>", 1, MessageBox.Alignment.CENTER);
    }

    protected override bool DimensionsValid()
    {
        return _windowSizeValid;
    }

    protected override Vector2<int> RecalculateDimensions()
    {
        return new Vector2<int>(Console.BufferWidth, Console.BufferHeight);
    }

    public override void Render()
    {
        var size = Size;

        Console.Clear();

        if (size.X < 20 || size.Y < 20)
        {
            _warningMessageBox.Render();
            return;
        }
        
        Objects.ForEach(o => o.Render());
    }
}