using Snaky.Core.Base;
using Snaky.Core.Interfaces;
using Snaky.Core.Utils;

namespace Snaky.Game.Objects;

public class Snake : IUpdatable
{
    private readonly ISized _parent;
    private readonly Func<List<IUpdatable>> _gameObjects;
    private readonly SnakeHead _head;

    private readonly Queue<SnakeBody> _pendingParts = [];
    private readonly List<SnakeBody> _snakeParts = [];

    private bool _isAlive = true;
    public int Score { get; set; } = 0;

    public Vector2<int> Position
    {
        get => _head.Position;
        set => _head.Position = value;
    }

    public Vector2<int> Size => _head.Size;

    private double _positionUpdateTicker;
    private double _speed = 1;

    public Snake(ISized parent, Func<List<IUpdatable>> gameObjects)
    {
        _parent = parent;
        _gameObjects = gameObjects;
        _head = new SnakeHead(parent)
        {
            Position = parent.Size / 2 + parent.Position
        };
    }

    public void Render()
    {
        foreach (var snakePart in _snakeParts)
        {
            snakePart.Render();
        }

        _head.Render();
    }

    public void Update(float dt)
    {
        foreach (var apple in _gameObjects().Where(o => o is Apple).Cast<Apple>())
        {
            if (!_head.CollidesWith(apple)) continue;

            AddBodyPart();
            apple.MarkAsInvalid();
            _speed += 0.3;
            Score++;
            break; // 2 can't collide with more than 1 apple
        }

        if (_snakeParts.Any(body => _head.CollidesWith(body)))
        {
            _isAlive = false;
            return;
        }

        if (_positionUpdateTicker < 1)
        {
            _positionUpdateTicker += dt / 100 * _speed;
            return;
        }

        _positionUpdateTicker = 0;

        var oldHeadPosition = _head.Position;
        
        // Move the head
        _head.Update(dt);
        
        // Add all pending body parts
        if (_pendingParts.TryDequeue(out var pendingPart))
        {
            pendingPart.Position = oldHeadPosition;
            pendingPart.SetDirection(_head);
            _snakeParts.Insert(0, pendingPart);
            return;
        }

        // Move all body parts
        _snakeParts.ForEach(snakePart => snakePart.Update(dt));

        // Shift directions
        for (var i = _snakeParts.Count - 1; i >= 0; i--)
        {
            SnakeNode copyFrom;
            if (i == 0)
            {
                copyFrom = _head; // The head
            }
            else
            {
                copyFrom = _snakeParts[i - 1]; // One node forward
            }

            _snakeParts[i].SetDirection(copyFrom);
        }
    }

    public bool DispatchKeyEvent(ConsoleKey key)
    {
        return _head.DispatchKeyEvent(key);
    }

    public bool IsValid()
    {
        return _isAlive;
    }

    public void AddBodyPart()
    {
        _pendingParts.Enqueue(new SnakeBody(_parent));
    }
}