using Snaky.Tui.UiElements;
using Snaky.Tui.Utils;

namespace Snaky.Tui.Screens;

public class TuiMenuScreen : TuiScreen
{
    public TuiMenuScreen()
    {
        var border = new Border(this);
        Objects.Add(border);
        Objects.Add(new MessageBox(this,
            $"{UnicodeSymbols.DOUBLE_MIDDLE_HORIZONTAL_LINE}Welcome to snaky!{UnicodeSymbols.DOUBLE_MIDDLE_HORIZONTAL_LINE}",
            1, MessageBox.Alignment.V_BOTTOM | MessageBox.Alignment.H_CENTER));
        Objects.Add(new MessageBox(this,
            $"{UnicodeSymbols.DOUBLE_MIDDLE_HORIZONTAL_LINE}Press ENTER to start{UnicodeSymbols.DOUBLE_MIDDLE_HORIZONTAL_LINE}",
            1, MessageBox.Alignment.V_CENTER | MessageBox.Alignment.H_CENTER));
    }

    public override bool DispatchKeyEvent(ConsoleKeyInfo key)
    {
        if (key.Key != ConsoleKey.Enter) return base.DispatchKeyEvent(key);
        
        ChangeScreen(new MainGameScreen());
        return false;
    }
}