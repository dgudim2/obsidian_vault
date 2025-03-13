namespace Snaky.Core.Interfaces;

public interface IUpdatable : IRenderable
{
    void Update(float dt);

    bool DispatchKeyEvent(ConsoleKey key);
}