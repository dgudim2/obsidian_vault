using Snaky.Core.Utils;

namespace Snaky.Core.Interfaces;

public interface ISized
{
    public Vector2<int> Position { get; set; }
    public Vector2<int> Size { get; }

    public Vector2<int> SizeWithPosOffset => Position + Size;
}