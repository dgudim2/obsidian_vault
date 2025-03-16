using Snaky.Core.Interfaces;
using Snaky.Core.Utils;
using Snaky.Game.Objects;
using Snaky.Tui.UiElements;

namespace Snaky.Tui.Screens;

public class MainGameScreen : TuiScreen
{
    private readonly MessageBox _fpsMonitor;
    private readonly MessageBox _scoreMonitor;
    private readonly Random _rand = new();

    private readonly Snake _snake;
    private readonly ISized _border;
    
    public MainGameScreen()
    {
        _border = new Border(this);
        _fpsMonitor = new MessageBox(this, "Delta", 1, MessageBox.Alignment.V_BOTTOM | MessageBox.Alignment.H_LEFT)
        {
            Position = new Vector2<int>(2, 0)
        };
        _scoreMonitor = new MessageBox(this, "Score:", 1, MessageBox.Alignment.V_BOTTOM | MessageBox.Alignment.H_LEFT)
        {
            Position = new Vector2<int>(25, 0)
        };

        var snake = new Snake(this, () => Objects);
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
        var apple = new Apple(this);
        while (true)
        {
            var x = _rand.Next(_border.Position.X + 1, _border.SizeWithPosOffset.X - 1);
            var y = _rand.Next(_border.Position.Y + 1, _border.SizeWithPosOffset.Y - 1);

            var position = new Vector2<int>(x, y);

            // Prevent overlaps
            if (Objects.Any(a => a.Position == position))
            {
                continue;
            }

            apple.Position = position;
            Objects.Add(apple);
            break;
        }
    }

    public override void Update(float dt)
    {
        _fpsMonitor.Text = $"Delta: {dt}";
        _scoreMonitor.Text = $"Score: {_snake.Score}";
        
        base.Update(dt);
        
        var badApples = Objects.Where(o => o is Apple).Count(apple => !apple.IsValid());
        for (var i = 0; i < badApples; i++)
        {
            AddApple();
        }
        
        Objects.RemoveAll(o => !o.IsValid()); // Remove invalid objects

        if (!_snake.IsValid())
        {
            ChangeScreen(new GameOverScreen(_snake.Score));
        }
    }
}