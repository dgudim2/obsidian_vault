using System.Numerics;
using Snaky.Core.Utils;

namespace Snaky.Core.Interfaces;

public interface IGameObject: IUpdatable
{
    public bool CollidesWith(IGameObject other);
}