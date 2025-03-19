using Snaky.Core.Db;
using Snaky.Core.Utils;
using Snaky.Game.Objects;
using Snaky.Tui.UiElements;

namespace Snaky.Tui.Screens;

public class LeaderboardScreen : TuiScreen
{
    private readonly int _currentScore;
    private readonly AppleType _appleType;
    private MessageBox? _nicknameInputField;
    private string _nickname = "";

    public LeaderboardScreen(int currentScore, AppleType appleType)
    {
        _currentScore = currentScore;
        _appleType = appleType;
        ReadLeaderboard(true);
    }

    private void ReadLeaderboard(bool addNickInput)
    {
        using var ctx = new LeaderboardContext();
        var scores = ctx.Scores.OrderByDescending(s => s.Score).Take(10).ToList();
        
        Objects.Clear();

        Objects.Add(new Border(this));

        Objects.Add(new MessageBox(this, "Leaderboard, showing top 10 leaders", 1,
            MessageBox.Alignment.V_TOP | MessageBox.Alignment.H_CENTER));
        
        if (addNickInput)
        {
            Objects.Add(new MessageBox(this, "Enter your nickname and press enter to save your score", 1,
                MessageBox.Alignment.V_TOP | MessageBox.Alignment.H_CENTER)
            {
                Position = new Vector2<int>(0, 6)
            });
            _nicknameInputField = new MessageBox(this, "Your nickname: ", 1,
                MessageBox.Alignment.V_TOP | MessageBox.Alignment.H_CENTER)
            {
                Position = new Vector2<int>(0, 7)
            };

            Objects.Add(_nicknameInputField);
        }
        else
        {
            Objects.Add(new MessageBox(this, "Press M to return to the main menu, press R to restart", 1,
                MessageBox.Alignment.V_TOP | MessageBox.Alignment.H_CENTER)
            {
                Position = new Vector2<int>(0, 7)
            });
            _nicknameInputField = null;
        }

        var i = 1;
        foreach (var score in scores)
        {
            AddScoreMessageBox(score, i);
            i++;
        }
    }

    private void AddScoreMessageBox(ScoreModel score, int index)
    {
        Objects.Add(new MessageBox(this, $"{index}. {score.Name}: {score.Score}", 1,
            MessageBox.Alignment.V_TOP | MessageBox.Alignment.H_CENTER)
        {
            Position = new Vector2<int>(0, index + 10)
        });
    }

    private void AddScore(ScoreModel score)
    {
        using var ctx = new LeaderboardContext();
        ctx.Scores.Add(score);
        ctx.SaveChanges();
        ReadLeaderboard(false);
    }

    public override bool DispatchKeyEvent(ConsoleKeyInfo key)
    {
        var c = key.KeyChar;
        if (_nicknameInputField != null)
        {
            if ((!char.IsControl(c) || char.IsWhiteSpace(c)) && key.Key != ConsoleKey.Enter)
            {
                _nickname += key.KeyChar;
            }
            else
                switch (key.Key)
                {
                    case ConsoleKey.Backspace:
                        _nickname = _nickname.Length > 0 ? _nickname[..^1] : "";
                        return false;
                    case ConsoleKey.Enter:
                        if (_nickname.Length > 0)
                        {
                            AddScore(new ScoreModel
                            {
                                Name = _nickname,
                                Score = _currentScore
                            });
                        }

                        return false;
                }

            _nicknameInputField.Text = $"Your nickname: {_nickname}";
        }
        else
        {
            switch (key.Key)
            {
                case ConsoleKey.M:
                    ChangeScreen(new TuiMenuScreen());
                    return false;
                case ConsoleKey.R:
                    ChangeScreen(new MainGameScreen(_appleType));
                    return false;
            }
        }

        return base.DispatchKeyEvent(key);
    }
}