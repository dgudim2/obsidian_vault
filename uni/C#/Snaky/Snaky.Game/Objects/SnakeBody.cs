using Snaky.Core.Base;
using Snaky.Core.Interfaces;
using Snaky.Core.Utils;

namespace Snaky.Game.Objects;

public class SnakeBody(ISized parent) : SnakeNode(parent)
{
    protected override string GetRenderChar()
    {
        return "▣";
    }
    
    public override bool DispatchKeyEvent(ConsoleKey key)
    {
        return true;
    }
}