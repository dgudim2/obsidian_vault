using Snaky.Tui.UiElements;
using Snaky.Tui.Utils;

namespace Snaky.Tui.Screens;

public class TuiMenuScreen : TuiScreen
{
    public TuiMenuScreen()
    {
        Objects.Add(new Border(this));
        Objects.Add(new MessageBox(this,
            $"{UnicodeSymbols.DOUBLE_MIDDLE_HORIZONTAL_LINE}Welcome to snaky!{UnicodeSymbols.DOUBLE_MIDDLE_HORIZONTAL_LINE}",
            1, MessageBox.Alignment.BOTTOM));
        Objects.Add(new MessageBox(this,
            $"{UnicodeSymbols.DOUBLE_MIDDLE_HORIZONTAL_LINE}Press ENTER to start{UnicodeSymbols.DOUBLE_MIDDLE_HORIZONTAL_LINE}",
            1, MessageBox.Alignment.CENTER));
    }

    public override bool DispatchKeyEvent(ConsoleKey key)
    {
        if (key != ConsoleKey.Enter) return base.DispatchKeyEvent(key);
        
        ChangeScreen(new MainGameScreen());
        return false;
    }
}