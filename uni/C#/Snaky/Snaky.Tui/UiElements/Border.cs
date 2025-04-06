using Snaky.Core.Interfaces;
using Snaky.Core.Utils;
using Snaky.Tui.Utils;

namespace Snaky.Tui.UiElements;

public class Border : IUpdatable
{
    private readonly IRenderable _parent;

    public Border(IRenderable parent, Vector2<int> position = default)
    {
        _parent = parent;
        Position = position;
    }

    public Vector2<int> Position { get; set; }
    public Vector2<int> Size => _parent.Size - 2;

    public void Render()
    {
        if (DimensionsValid())
        {
            return;
        }

        var xOffset = Position.X + _parent.Position.X;
        var yOffset = Position.Y + _parent.Position.Y;

        for (var x = 1 + xOffset; x < _parent.Size.X - 1; x++)
        {
            Console.SetCursorPosition(x, yOffset);
            Console.Out.Write(UnicodeSymbols.MIDDLE_HORIZONTAL_LINE);

            Console.SetCursorPosition(x, _parent.Size.Y - 1);
            Console.Out.Write(UnicodeSymbols.MIDDLE_HORIZONTAL_LINE);
        }

        for (var y = 1 + yOffset; y < _parent.Size.Y - 1; y++)
        {
            Console.SetCursorPosition(xOffset, y);
            Console.Out.Write(UnicodeSymbols.MIDDLE_VERTICAL_LINE);

            Console.SetCursorPosition(_parent.Size.X - 1, y);
            Console.Out.Write(UnicodeSymbols.MIDDLE_VERTICAL_LINE);
        }

        Console.SetCursorPosition(xOffset, yOffset);
        Console.Out.Write(UnicodeSymbols.TOP_LEFT_ROUND_CORNER);
        Console.SetCursorPosition(_parent.Size.X - 1, yOffset);
        Console.Out.Write(UnicodeSymbols.TOP_RIGHT_ROUND_CORNER);
        Console.SetCursorPosition(xOffset, _parent.Size.Y - 1);
        Console.Out.Write(UnicodeSymbols.BOTTOM_LEFT_ROUND_CORNER);
        Console.SetCursorPosition(_parent.Size.X - 1, _parent.Size.Y - 1);
        Console.Out.Write(UnicodeSymbols.BOTTOM_RIGHT_ROUND_CORNER);
    }

    public bool DimensionsValid()
    {
        return _parent.DimensionsValid();
    }

    public void Update(float dt)
    {
    }

    public bool DispatchKeyEvent(ConsoleKeyInfo key)
    {
        return true;
    }

    public bool IsValid()
    {
        return true;
    }
}