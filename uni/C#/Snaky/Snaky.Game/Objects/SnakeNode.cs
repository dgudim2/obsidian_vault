using Snaky.CommonUI.Screens;
using Snaky.Core.Base;
using Snaky.Core.Interfaces;
using Snaky.Core.Utils;

namespace Snaky.Game.Objects;

public abstract class SnakeNode(BaseScreen parent) : GameObject(parent)
{
    protected enum Direction
    {
        Up,
        Down,
        Left,
        Right
    }

    public Vector2<int> prevPosition = new(0, 0);
    protected Direction _direction = Direction.Right;

    protected internal void SetDirection(SnakeNode parentNode)
    {
        _direction = parentNode._direction;
    }
    
    public override void Update(float dt)
    {
        MoveInDirection();
    }

    public override bool DimensionsValid()
    {
        return parent.DimensionsValid();
    }

    protected void MoveInDirection()
    {
        
        prevPosition = new Vector2<int>(_position.X, _position.Y);
        
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
        
        ISized p = parent;
        
        if (Position.X >= p.SizeWithPosOffset.X - 1)
        {
            _position.X = parent.Position.X + 1;
        }
        
        if (Position.X <= parent.Position.X)
        {
            _position.X = p.SizeWithPosOffset.X - 2;
        }
        
        if (Position.Y >= p.SizeWithPosOffset.Y - 1)
        {
            _position.Y = parent.Position.Y + 1;
        }
        
        if (Position.Y <= parent.Position.Y)
        {
            _position.Y = p.SizeWithPosOffset.Y - 2;
        }
    }
}
