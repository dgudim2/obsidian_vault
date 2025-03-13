using System.Numerics;
using Snaky.Core.Utils;

namespace Snaky.Core.Interfaces;

public interface IGameObject: IUpdatable
{
    bool CollidesWith(IGameObject other);
}