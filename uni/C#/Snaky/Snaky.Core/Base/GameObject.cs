using Snaky.Core.Interfaces;
using Snaky.Core.Utils;

namespace Snaky.Core.Base;

public abstract class GameObject : IGameObject
{
    private readonly ISized _parent;
    protected Vector2<int> _position;
    private readonly Vector2<int> _size = new(1, 1);
    public Vector2<int> Size => _size;

    public Vector2<int> Position
    {
        get => _position;
        set => _position = value;
    }

    public GameObject(ISized parent)
    {
        _parent = parent;
    }
    
    protected abstract string GetRenderChar();

    public virtual void Render()
    {
        Console.SetCursorPosition(_position.X, _position.Y);
        Console.Out.Write(GetRenderChar());
    }
    
    public abstract void Update(float dt);
    public abstract bool DispatchKeyEvent(ConsoleKey key);

    public bool CollidesWith(IGameObject other)
    {
        return other.Position == Position;
    }
}