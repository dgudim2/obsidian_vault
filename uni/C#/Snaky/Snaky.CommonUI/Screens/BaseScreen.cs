using System.Numerics;
using Snaky.CommonUI.Interfaces;
using Snaky.Core.Base;
using Snaky.Core.Interfaces;
using Snaky.Core.Utils;

namespace Snaky.CommonUI.Screens;

public abstract class BaseScreen : IScreen
{
    private Vector2<int>? _cachedDimensions;
    protected readonly List<IUpdatable> Objects = [];
    private Vector2<int> _position = new(0, 0);

    public Vector2<int> Position
    {
        get => _position;
        set => _position = value;
    }

    public Vector2<int> Size => GetCurrentSize();

    public event ScreenChangeDelegate? OnScreenTransition;

    protected abstract bool DimensionsValid();

    protected abstract Vector2<int> RecalculateDimensions();

    private Vector2<int> GetCurrentSize()
    {
        if (_cachedDimensions == null || !DimensionsValid())
        {
            _cachedDimensions = RecalculateDimensions();
        }

        return (Vector2<int>)_cachedDimensions;
    }

    protected void ChangeScreen(IScreen newScreen)
    {
        OnScreenTransition?.Invoke(newScreen);
        OnScreenTransition = null;
    }

    public abstract void Render();

    public virtual void Update(float dt)
    {
        Objects.ForEach(o => o.Update(dt));
    }

    public virtual bool DispatchKeyEvent(ConsoleKeyInfo key)
    {
        foreach (var obj in Objects)
        {
            if (!obj.DispatchKeyEvent(key))
            {
                // Even consumed
                return false;
            }
        }

        return true;
    }

    public bool IsValid()
    {
        throw new NotImplementedException();
    }
}