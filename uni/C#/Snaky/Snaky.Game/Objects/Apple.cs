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
        return appleType switch
        {
            AppleType.COMPANY => "\ue711",
            AppleType.FOOD => "\udb80\ude5b",
            AppleType.FOOD_OUTLINE => "\udb83\udc84",
            _ => throw new ArgumentOutOfRangeException()
        };
    }

    public override bool DimensionsValid()
    {
        return true;
    }

    public override void Update(float dt)
    {
    }

    public override bool DispatchKeyEvent(ConsoleKeyInfo key)
    {
        return true;
    }
}