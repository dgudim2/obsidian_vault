using Snaky.Core.Interfaces;
using Snaky.Core.Utils;

namespace Snaky.Core.Base;

public abstract class GameObject(ISized parent) : IGameObject
{
    protected readonly ISized _parent = parent;
    protected Vector2<int> _position;
    private readonly Vector2<int> _size = new(1, 1);
    public Vector2<int> Size => _size;

    private bool _valid = true;
    
    public Vector2<int> Position
    {
        get => _position;
        set => _position = value;
    }

    public void MarkAsInvalid()
    {
        _valid = false;
    }

    protected abstract string GetRenderChar();

    public virtual void Render()
    {
        if (!_valid)
        {
            return;
        }

        Console.SetCursorPosition(_position.X, _position.Y);
        Console.Out.Write(GetRenderChar());
    }
    
    public abstract void Update(float dt);
    public abstract bool DispatchKeyEvent(ConsoleKeyInfo key);
    public bool IsValid()
    {
        return _valid;
    }

    public bool CollidesWith(IGameObject other)
    {
        if (!_valid)
        {
            return false;
        }
        return other.Position == Position;
    }
}