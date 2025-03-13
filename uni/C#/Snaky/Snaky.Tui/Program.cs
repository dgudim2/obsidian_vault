using Snaky.Tui.Screens;

namespace Snaky.Game;

internal static class Program
{
    private static void Main()
    {
        var startingScreen = new TuiMenuScreen();
        var game = new Game(startingScreen);
        game.Render();
    }
}