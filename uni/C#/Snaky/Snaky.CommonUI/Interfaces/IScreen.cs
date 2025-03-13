using Snaky.Core.Interfaces;

namespace Snaky.CommonUI.Interfaces;

public delegate void ScreenChangeDelegate(IScreen screen);

public interface IScreen: IUpdatable
{
    public event ScreenChangeDelegate OnScreenTransition;
}