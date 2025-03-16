using Snaky.Core.Base;
using Snaky.Core.Interfaces;
using Snaky.Core.Utils;

namespace Snaky.Game.Objects;

public class SnakeHead(ISized parent) : SnakeNode(parent)
{
    protected override string GetRenderChar()
    {
        return _direction switch
        {
            Direction.Up => "▲",
            Direction.Down => "▼",
            Direction.Right => "▶",
            Direction.Left => "◀",
            _ => throw new ArgumentOutOfRangeException()
        };
    }

    public override bool DispatchKeyEvent(ConsoleKey key)
    {
        switch (key)
        {
            case ConsoleKey.LeftArrow:
                if (_direction != Direction.Right)
                    _direction = Direction.Left;
                return false;
            case ConsoleKey.RightArrow:
                if (_direction != Direction.Left)
                    _direction = Direction.Right;
                return false;
            case ConsoleKey.DownArrow:
                if (_direction != Direction.Up)
                    _direction = Direction.Down;
                return false;
            case ConsoleKey.UpArrow:
                if (_direction != Direction.Down)
                    _direction = Direction.Up;
                return false;
            default:
                return true;
        }
    }
}