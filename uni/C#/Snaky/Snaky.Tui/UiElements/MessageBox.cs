using System.Numerics;
using Snaky.Core.Base;
using Snaky.Core.Interfaces;
using Snaky.Core.Utils;

namespace Snaky.Tui.UiElements;

public class MessageBox : IUpdatable
{
    [Flags]
    public enum Alignment
    {
        V_TOP = 0,
        V_CENTER = 2,
        V_BOTTOM = 4,
        H_RIGTH = 8,
        H_CENTER = 16,
        H_LEFT = 32
    }

    public Vector2<int> Position { get; set; }

    public Vector2<int> Size => new(Text.Length, Lines);

    private readonly Alignment _alignment;

    private readonly IRenderable _parent;

    public string Text { get; set; }

    public int Lines { get; }

    public MessageBox(IRenderable parent, string text, int lines,
        Alignment alignment)
    {
        _parent = parent;
        Text = text;
        Lines = lines;
        _alignment = alignment;
    }

    private bool HasAlignment(Alignment alignment)
    {
        return (_alignment & alignment) == alignment;
    }

    public void Render()
    {
        var pos = _parent.Position + Position;
        var parentCenter = _parent.Size / 2;

        var (startX, startY) = pos;

        if (HasAlignment(Alignment.V_CENTER))
        {
            startY += parentCenter.Y - Size.Y / 2;
        }
        else if (HasAlignment(Alignment.V_BOTTOM))
        {
            startY += _parent.Size.Y - 1;
        }

        if (HasAlignment(Alignment.H_CENTER))
        {
            startX += parentCenter.X - Size.X / 2;
        }
        else if (HasAlignment(Alignment.H_RIGTH))
        {
            startX += _parent.Size.X - 1;
        }

        Console.SetCursorPosition(startX, startY);
        Console.Write(Text);
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