namespace Snaky.Core.Interfaces;

public interface IUpdatable : IRenderable
{
    void Update(float dt);

    bool DispatchKeyEvent(ConsoleKeyInfo key);

    bool IsValid();
}