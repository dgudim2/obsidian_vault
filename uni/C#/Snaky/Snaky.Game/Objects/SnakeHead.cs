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
}