using System.Diagnostics.CodeAnalysis;
using Snaky.Core.Base;
using Snaky.Core.Interfaces;

namespace Snaky.Game.Objects;

[SuppressMessage("ReSharper", "InconsistentNaming")] // Stoopid
public enum AppleType
{
    COMPANY,
    FOOD,
    FOOD_OUTLINE
}

public class Apple(ISized parent, AppleType appleType) : GameObject(parent)
{
    protected override string GetRenderChar()
    {
        switch (appleType)
        {
            case AppleType.COMPANY:
                return "\ue711";
            case AppleType.FOOD:
                return "\udb80\ude5b";
            case AppleType.FOOD_OUTLINE:
                return "\udb83\udc84";
            default:
                throw new ArgumentOutOfRangeException();
        }
    }

    public override void Update(float dt)
    {
    }

    public override bool DispatchKeyEvent(ConsoleKeyInfo key)
    {
        return true;
    }
}