using Snaky.Core.Db;
using Snaky.Core.Utils;
using Snaky.Tui.UiElements;

namespace Snaky.Tui.Screens;

public class LeaderboardScreen : TuiScreen
{
    private readonly List<ScoreModel> _scores;
    private readonly MessageBox _nicknameInputField;
    private string _nickname = "";

    public LeaderboardScreen()
    {
        using var ctx = new LeaderboardContext();
        _scores = ctx.Scores.OrderByDescending(s => s.Score).Take(10).ToList();

        Objects.Add(new Border(this));
        var i = 1;

        Objects.Add(new MessageBox(this, "Leaderboard, showing top 10 leaders", 1,
            MessageBox.Alignment.V_TOP | MessageBox.Alignment.H_CENTER));

        Objects.Add(new MessageBox(this, "Enter your nickname and press enter to save your score", 1,
            MessageBox.Alignment.V_TOP | MessageBox.Alignment.H_CENTER)
        {
            Position = new Vector2<int>(0, i + 5)
        });

        _nicknameInputField = new MessageBox(this, "Your nickname: ", 1,
            MessageBox.Alignment.V_TOP | MessageBox.Alignment.H_CENTER)
        {
            Position = new Vector2<int>(0, i + 6)
        };

        Objects.Add(_nicknameInputField);

        foreach (var score in _scores)
        {
            Objects.Add(new MessageBox(this, $"{i}. {score.Name}: {score.Score}", 1,
                MessageBox.Alignment.V_TOP | MessageBox.Alignment.H_CENTER)
            {
                Position = new Vector2<int>(0, i + 10)
            });
            i++;
        }
    }
    
    public override bool DispatchKeyEvent(ConsoleKeyInfo key)
    {
        var c = key.KeyChar;
        if (!char.IsControl(c) || char.IsWhiteSpace(c))
        {
            _nickname += key.KeyChar;
        }
        else if (key.Key == ConsoleKey.Backspace)
        {
            _nickname = _nicknameInputField.Text[..^1];
        } else if (key.Key == ConsoleKey.Enter)
        {
        }

        _nicknameInputField.Text = $"Your nickname: {_nickname}";

        return base.DispatchKeyEvent(key);
    }
}