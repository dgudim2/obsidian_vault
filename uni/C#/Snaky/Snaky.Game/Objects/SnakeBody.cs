using Snaky.CommonUI.Screens;
using Snaky.Core.Base;
using Snaky.Core.Interfaces;
using Snaky.Core.Utils;

namespace Snaky.Game.Objects;

public class SnakeBody(BaseScreen parent) : SnakeNode(parent)
{
    protected override string GetRenderChar()
    {
        return "▣";
    }
    
    public override bool DispatchKeyEvent(ConsoleKeyInfo key)
    {
        return true;
    }
}