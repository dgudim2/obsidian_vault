namespace Snaky.Core.Interfaces;

public interface IRenderable: ISized
{
    void Render();
    bool DimensionsValid();
}