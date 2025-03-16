using Snaky.Core.Base;
using Snaky.Core.Interfaces;

namespace Snaky.Game.Objects;

public class Apple(ISized parent) : GameObject(parent)
{
    protected override string GetRenderChar()
    {
        return "🍎";
    }

    public override void Update(float dt)
    {
    }
    
    public override bool DispatchKeyEvent(ConsoleKey key)
    {
        return true;
    }
}