using Snaky.Core.Utils;
using Snaky.Game.Objects;
using Snaky.Tui.UiElements;
using Snaky.Tui.Utils;

namespace Snaky.Tui.Screens;

public class GameOverScreen : TuiScreen
{
    private readonly int _score;
    private readonly AppleType _appleType;

    public GameOverScreen(int score, AppleType appleType)
    {
        _score = score;
        _appleType = appleType;
        var border = new Border(this);
        Objects.Add(border);
        Objects.Add(new MessageBox(this,
            $"{UnicodeSymbols.DOUBLE_MIDDLE_HORIZONTAL_LINE}Game over, press enter to restart{UnicodeSymbols.DOUBLE_MIDDLE_HORIZONTAL_LINE}",
            1, MessageBox.Alignment.V_CENTER | MessageBox.Alignment.H_CENTER));

        var scoreBox = new MessageBox(this, $"Your score is: {score}", 1,
            MessageBox.Alignment.V_CENTER | MessageBox.Alignment.H_CENTER)
        {
            Position = new Vector2<int>(0, 2)
        };

        var leaderboardBox = new MessageBox(this, "Press L for leaderboard", 1,
            MessageBox.Alignment.V_CENTER | MessageBox.Alignment.H_CENTER)
        {
            Position = new Vector2<int>(0, -2)
        };

        Objects.Add(scoreBox);
        Objects.Add(leaderboardBox);
    }

    public override bool DispatchKeyEvent(ConsoleKeyInfo key)
    {
        switch (key.Key)
        {
            case ConsoleKey.Enter:
                ChangeScreen(new MainGameScreen(_appleType));
                return false;
            case ConsoleKey.L:
                ChangeScreen(new LeaderboardScreen(_score, _appleType));
                return false;
            default:
                return base.DispatchKeyEvent(key);
        }
    }
}