using Snaky.Core.Base;
using Snaky.Core.Interfaces;
using Snaky.Core.Utils;

namespace Snaky.Game.Objects;

public abstract class SnakeNode(ISized parent) : GameObject(parent)
{
    protected enum Direction
    {
        Up,
        Down,
        Left,
        Right
    }
    
    protected Direction _direction = Direction.Right;

    protected internal void SetDirection(SnakeNode parentNode)
    {
        _direction = parentNode._direction;
    }
    
    public override void Update(float dt)
    {
        MoveInDirection();
    }
    
    protected void MoveInDirection()
    {
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

        if (Position.X >= _parent.SizeWithPosOffset.X - 1)
        {
            _position.X = _parent.Position.X + 1;
        }
        
        if (Position.X <= _parent.Position.X)
        {
            _position.X = _parent.SizeWithPosOffset.X - 1;
        }
        
        if (Position.Y >= _parent.SizeWithPosOffset.Y - 1)
        {
            _position.Y = _parent.Position.Y + 1;
        }
        
        if (Position.Y <= _parent.Position.Y)
        {
            _position.Y = _parent.SizeWithPosOffset.Y - 1;
        }
    }
}
