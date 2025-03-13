using Snaky.Core.Base;
using Snaky.Core.Interfaces;
using Snaky.Core.Utils;

namespace Snaky.Tui.UiElements;

public class MessageBox : IUpdatable
{
    public enum Alignment { TOP, CENTER, BOTTOM }
    
    public Vector2<int> Position { get; set; }
    public Vector2<int> Size { get; }
    
    private Alignment _alignment;

    private readonly IRenderable _parent;
    private string _text;

    public MessageBox(IRenderable parent, string text, int lines, Alignment alignment)
    {
        _parent = parent;
        _text = text;
        _alignment = alignment;
        Size = new Vector2<int>(_text.Length, lines);
    }

    public void Render()
    {
        var pos = _parent.Position + Position;
        var parentCenter = _parent.Size / 2;

        var startX = parentCenter.X - Size.X / 2 + pos.X;


        var startY = pos.Y;
        switch (_alignment)
        {
            case Alignment.TOP:
                break;
            case Alignment.CENTER:
                startY += parentCenter.Y - Size.Y / 2;
                break;
            case Alignment.BOTTOM:
                startY += _parent.Size.Y - 1;
                break;
            default:
                throw new ArgumentOutOfRangeException();
        }
        
        Console.SetCursorPosition(startX, startY);
        Console.Write(_text);
    }

    public void Update(float dt)
    {
    }

    public bool DispatchKeyEvent(ConsoleKey key)
    {
        return true;
    }
}