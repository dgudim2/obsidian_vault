using Snaky.Core.Base;
using Snaky.Core.Interfaces;
using Snaky.Core.Utils;
using Snaky.Game.Objects;
using Snaky.Tui.UiElements;
using Snaky.Tui.Utils;

namespace Snaky.Tui.Screens;

public class MainGameScreen : TuiScreen
{
    private readonly AppleType _appleType;
    private readonly MessageBox _fpsMonitor;
    private readonly MessageBox _scoreMonitor;
    private static readonly Random Rand;

    private readonly Snake _snake;
    private readonly ISized _border;

    static MainGameScreen()
    {
        Rand = new Random();
    }

    public MainGameScreen(AppleType appleType)
    {
        _appleType = appleType;
        _border = new Border(this);
        _fpsMonitor = new MessageBox(this, "Delta", 1, MessageBox.Alignment.V_BOTTOM | MessageBox.Alignment.H_LEFT)
        {
            Position = new Vector2<int>(2, 0)
        };
        _scoreMonitor = new MessageBox(this, "Score:", 1, MessageBox.Alignment.V_BOTTOM | MessageBox.Alignment.H_LEFT)
        {
            Position = new Vector2<int>(35, 0)
        };

        var snake = new Snake(parent: this, gameObjects: () => Objects);
        _snake = snake;

        for (var i = 0; i < 5; i++)
        {
            snake.AddBodyPart();
        }

        for (var i = 0; i < 40; i++)
        {
            AddApple();
        }

        Objects.Add((IUpdatable)_border);
        Objects.Add(snake);
        Objects.Add(_fpsMonitor);
        Objects.Add(_scoreMonitor);
    }

    private void AddApple()
    {
        var apple = new Apple(this, _appleType);
        while (true)
        {
            var x = Rand.Next(_border.Position.X + 1, _border.SizeWithPosOffset.X - 1);
            var y = Rand.Next(_border.Position.Y + 1, _border.SizeWithPosOffset.Y - 1);

            var newApplePosition = new Vector2<int>(x, y);

            // Prevent overlaps
            if (Objects.Any(a => a.Position == newApplePosition))
            {
                continue;
            }

            apple.Position = newApplePosition;
            Objects.Add(apple);
            break;
        }
    }

    public override void Update(float dt)
    {
        _fpsMonitor.Text = $" Delta: {dt:.} {UnicodeSymbols.MIDDLE_HORIZONTAL_LINE} Fps: {1 / dt * 1000:.} ";
        _scoreMonitor.Text = $" Score: {_snake.Score} ";

        base.Update(dt);

        var badApples = Objects.Where(o => o is Apple).Count(apple => !apple.IsValid());
        for (var i = 0; i < badApples; i++)
        {
            AddApple();
        }

        Objects.RemoveAll(o => !o.IsValid()); // Remove invalid objects

        if (!_snake.IsValid())
        {
            ChangeScreen(new GameOverScreen(_snake.Score, _appleType));
        }
    }
}