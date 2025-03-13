using Snaky.Core.Base;
using Snaky.Core.Interfaces;
using Snaky.Core.Utils;

namespace Snaky.Game.Objects;

public class Snake : IUpdatable
{
    private SnakeHead head;
    private List<SnakeNode> snakeParts;

    public Vector2<int> Position
    {
        get => head.Position;
        set => head.Position = value;
    }

    public Vector2<int> Size => head.Size;

    public Snake(ISized parent)
    {
        head = new SnakeHead(parent);
        head.Position = parent.Size / 2 + parent.Position;
        snakeParts =
        [
            head
        ];
    }

    public void Render()
    {
        foreach (var snakePart in snakeParts)
        {
            snakePart.Render();
        }
    }

    public void Update(float dt)
    {
        foreach (var snakePart in snakeParts)
        {
            snakePart.Update(dt);
        }
    }

    public bool DispatchKeyEvent(ConsoleKey key)
    {
        return head.DispatchKeyEvent(key);
    }
}