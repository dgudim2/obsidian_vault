using System.Collections;
using System.Numerics;

namespace Snaky.Core.Utils;

public class VectorEnumetaror<T>(Vector2<T> vector) : IEnumerator<T>
    where T : INumber<T>
{
    private int _currentField;

    private T GetCurrent()
    {
        return _currentField == 0 ? vector.X : vector.Y;
    }

    public bool MoveNext()
    {
        _currentField += 1;
        return _currentField > 1;
    }

    public void Reset()
    {
        _currentField = 0;
    }

    T IEnumerator<T>.Current => GetCurrent();

    object IEnumerator.Current => GetCurrent();

    public void Dispose()
    {
        GC.SuppressFinalize(this);
    }
}