using System.Collections;
using System.Diagnostics.CodeAnalysis;
using System.Globalization;
using System.Numerics;
using System.Runtime.CompilerServices;
using Snaky.Core.Interfaces;

namespace Snaky.Core.Utils;

public struct Vector2<T>(T x, T y) : IEquatable<Vector2<T>>, IFormattable, ICloneable, IComparable<Vector2<T>>, IComparable, IEnumerable
    where T : INumber<T>
{
    /// <summary>The X component of the vector.</summary>
    public T X = x;

    /// <summary>The Y component of the vector.</summary>
    public T Y = y;

    public static Vector2<T> operator +(Vector2<T> a, Vector2<T> b) => new(a.X + b.X, a.Y + b.Y);
    
    public static Vector2<T> operator /(Vector2<T> a, T num) => new(a.X / num, a.Y / num);
    
    public static Vector2<T> operator -(Vector2<T> a, T num) => new(a.X - num, a.Y - num);

    public void Deconstruct(out T x, out T y)
    {
        x = X;
        y = Y;
    }

    public override string ToString()
    {
        return ToString("G", CultureInfo.CurrentCulture);
    }

    public IEnumerator GetEnumerator()
    {
        return new VectorEnumetaror<T>(this);
    }

    public int CompareTo(object? obj)
    {
        return obj == null ? -1 : CompareTo((Vector2<T>)obj);
    }
    
    public object Clone()
    {
        return new Vector2<T>(X, Y);
    }

    public string ToString([StringSyntax("NumericFormat")] string? format)
    {
        return ToString(format, CultureInfo.CurrentCulture);
    }

    public string ToString([StringSyntax("NumericFormat")] string? format, IFormatProvider? formatProvider)
    {
        var numberGroupSeparator = NumberFormatInfo.GetInstance(formatProvider).NumberGroupSeparator;
        var interpolatedStringHandler = new DefaultInterpolatedStringHandler(3, 3);
        interpolatedStringHandler.AppendLiteral("<");
        interpolatedStringHandler.AppendFormatted(X.ToString(format, formatProvider));
        interpolatedStringHandler.AppendFormatted(numberGroupSeparator);
        interpolatedStringHandler.AppendLiteral(" ");
        interpolatedStringHandler.AppendFormatted(Y.ToString(format, formatProvider));
        interpolatedStringHandler.AppendLiteral(">");
        return interpolatedStringHandler.ToStringAndClear();
    }

    public bool Equals(Vector2<T> other)
    {
        return EqualityComparer<T>.Default.Equals(X, other.X) && EqualityComparer<T>.Default.Equals(Y, other.Y);
    }

    public int CompareTo(Vector2<T> other)
    {
        return X.CompareTo(other.X) + Y.CompareTo(other.Y);
    }
    
    public override bool Equals(object? obj)
    {
        return obj is Vector2<T> other && Equals(other);
    }

    public override int GetHashCode()
    {
        return HashCode.Combine(X, Y);
    }

    public static bool operator ==(Vector2<T> left, Vector2<T> right)
    {
        return left.Equals(right);
    }

    public static bool operator !=(Vector2<T> left, Vector2<T> right)
    {
        return !(left == right);
    }
}