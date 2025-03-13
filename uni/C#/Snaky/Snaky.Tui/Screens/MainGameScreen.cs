using Snaky.Game.Objects;

namespace Snaky.Tui.Screens;

public class MainGameScreen: TuiScreen
{
    public MainGameScreen()
    {
        Objects.Add(new Snake(this));
    }
}