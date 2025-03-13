using Snaky.Core.Base;
using Snaky.Core.Interfaces;
using Snaky.Core.Utils;

namespace Snaky.Game.Objects;

public abstract class SnakeNode(ISized parent) : GameObject(parent)
{
    protected readonly ISized _parent = parent;

    private double _positionUpdateTicker;
    
    protected enum Direction
    {
        Up,
        Down,
        Left,
        Right
    }

    protected Direction _direction = Direction.Right;

    public override bool DispatchKeyEvent(ConsoleKey key)
    {
        switch (key)
        {
            case ConsoleKey.LeftArrow:
                _direction = Direction.Left;
                return false;
            case ConsoleKey.RightArrow:
                _direction = Direction.Right;
                return false;
            case ConsoleKey.DownArrow:
                _direction = Direction.Down;
                return false;
            case ConsoleKey.UpArrow:
                _direction = Direction.Up;
                return false;
            default:
                return true;
        }
    }
    
    public override void Update(float dt)
    {
        if (_positionUpdateTicker < 1)
        {
            _positionUpdateTicker += dt / 500;
            return;
        }

        _positionUpdateTicker = 0;

        switch (_direction)
        {
            case Direction.Up:
                _position.Y -= 1;
                break;
            case Direction.Down:
                _position.Y += 1;
                break;
            case Direction.Left:
                _position.X -= 1;
                break;
            case Direction.Right:
                _position.X += 1;
                break;
            default:
                throw new ArgumentOutOfRangeException();
        }

        if (Position.X >= _parent.ComputedPosition.X)
        {
            _position.X = _parent.Position.X + 1;
        }
        
        if (Position.X <= _parent.Position.X)
        {
            _position.X = _parent.ComputedPosition.X - 1;
        }
        
        if (Position.Y >= _parent.ComputedPosition.Y)
        {
            _position.Y = _parent.Position.Y + 1;
        }
        
        if (Position.Y <= _parent.Position.Y)
        {
            _position.Y = _parent.ComputedPosition.Y - 1;
        }
    }
}