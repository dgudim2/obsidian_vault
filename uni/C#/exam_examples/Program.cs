using LinqExam.Models;
using System.Text.Json;

/*
1) Realizuokite praplėtimo metodą kurio algoritmo rezultatas būtų atitinkamas rezultatui metodo 'System.Linq.Last' (1 t.)
2) Realizuokite praplėtimo metodą kurio algoritmo rezultatas būtų atitinkamas rezultatui metodo 'System.Linq.LastOrDefault' (1 t.)
3) Realizuokite praplėtimo metodą kurio algoritmo rezultatas būtų atitinkamas rezultatui metodo 'System.Linq.Any' (1 t.)
4) Patikrinkite ar taisingai atlikote užduotis 1-3 naudojant 'System.Linq.SequenceEqual' (0,5 t.)
5) Deserializuokite gautą JSON į 'Person' objektų sarašą. (2 t.)
6) Su gautu 5 punkte sąrašu atlikite sekančius veiksmus: (2 t.)
      1. Palikite sąraše tik tuos žmones, kurie iki šiandienos vis dar gyvi
      2. Surikiuokite elementus pagal parametrą 'SalaryPerMonth', po to pagal 'BirthDate'
              2.1. Patikrinkite ar visi elementai atitinka salygą: Jaunesni nei 39 m.
              2.2. Išveskite elementą su indeksu 12 arba reikšme pagal nutylėjimą, jei elemento nėra
      3. Paimkite 28 elementų(-us) iš priekio
      4. Pasirinkite sukankatenuotus per tarpą parametrus parametrus 'birthDate' ir 'deathDate'

7) Su gautu 5 punkte sąrašu atlikite sekančius veiksmus: (2 t.)
      1. Palikite sąraše tik tuos žmones, kurie sveria mažiau nei 25,51 kg.
      2. Surikiuokite elementus pagal parametrą 'Surname', po to pagal 'Weight'
              2.1. Patikrinkite ar visi elementai atitinka salygą: Pavardė turi raidę 'R'
              2.2. Išveskite elementą su indeksu 92 arba reikšme pagal nutylėjimą, jei elemento nėra
      3. Praleiskite 47 elementų(-us) iš galo
      4. Pasirinkite sukankatenuotus per tarpą parametrus parametrus 'birthDate' ir 'deathDate'

8) Gautus sąrašus išsaugokite į skirtingus failus (0,5 t.)
*/

// 4
// Sequence for testing
int[] arr = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };

// Last
Console.Write("Last: ");
Console.WriteLine(arr.Last().Equals(arr.MyLast()));

// LastOrDefault
Console.Write("LastOrDefault: ");
Console.WriteLine(arr.LastOrDefault().Equals(arr.MyLastOrDefault()));

// Any
Console.Write("Any: ");
Console.WriteLine(arr.Any().Equals(arr.MyAny()));

// All
Console.Write("All: ");
Console.WriteLine(arr.All(x => x > 0).Equals(arr.MyAll(x => x > 0)));

// Any with predicate
Console.Write("Any with predicate: ");
Console.WriteLine(arr.Any(x => x > 5).Equals(arr.MyAny(x => x > 5)));

// Select
Console.Write("Select: ");
Console.WriteLine(arr.Select(x => x * 2).SequenceEqual(arr.MySelect(x => x * 2)));

// Where
Console.Write("Where: ");
Console.WriteLine(arr.Where(x => x > 5).SequenceEqual(arr.MyWhere(x => x > 5)));

// Take
Console.Write("Take: ");
Console.WriteLine(arr.Take(5).SequenceEqual(arr.MyTake(5)));

// Skip
Console.Write("Skip: ");
Console.WriteLine(arr.Skip(5).SequenceEqual(arr.MySkip(5)));

// TakeWhile
Console.Write("TakeWhile: ");
Console.WriteLine(arr.TakeWhile(x => x < 5).SequenceEqual(arr.MyTakeWhile(x => x < 5)));


// 5
string json = File.ReadAllText("People.json");
Person[] people = JsonSerializer.Deserialize<Person[]>(json)!;

// 6
IEnumerable<Person> peopleAlive_6_1 = people.Where(x => x.DeathDate > DateTime.UtcNow);
IEnumerable<Person> peopleSorted_6_2 = peopleAlive_6_1.OrderBy(x => x.SalaryPerMonth).ThenBy(x => x.BirthDate);

Console.WriteLine("6.2.1");
Console.WriteLine("Yaer younger than 39: " + peopleSorted_6_2.All(x => DateTime.UtcNow.Year - x.BirthDate.Year < 39));
Console.WriteLine("6.2.2");
Console.WriteLine("Element at index 12: " + peopleSorted_6_2.ElementAtOrDefault(12)?.Name);

IEnumerable<Person> peopleTaken_6_3 = peopleSorted_6_2.Take(28);
IEnumerable<string> peopleConcatenated_6_4 = peopleTaken_6_3.Select(x => $"{x.BirthDate} {x.DeathDate}");

// 8
File.WriteAllText("PeopleAlive.json", JsonSerializer.Serialize(peopleAlive_6_1));
File.WriteAllText("PeopleSorted.json", JsonSerializer.Serialize(peopleSorted_6_2));
File.WriteAllText("PeopleTaken.json", JsonSerializer.Serialize(peopleTaken_6_3));
File.WriteAllLines("PeopleConcatenated.json", peopleConcatenated_6_4);


// 7
IEnumerable<Person> peopleLighter_7_1 = people.Where(x => x.Weight < 25.51);
IEnumerable<Person> peopleSorted_7_2 = peopleLighter_7_1.OrderBy(x => x.Surname).ThenBy(x => x.Weight);

Console.WriteLine("7.2.1");
Console.WriteLine("Surname contains 'R': " + peopleSorted_7_2.All(x => x.Surname.Contains("R") || x.Surname.Contains("r")));
Console.WriteLine("7.2.2");
Console.WriteLine("Element at index 92: " + peopleSorted_7_2.ElementAtOrDefault(92)?.Name);

IEnumerable<Person> peopleSkipped_7_3 = peopleSorted_7_2.SkipLast(47);
IEnumerable<string> peopleConcatenated_7_4 = peopleSkipped_7_3.Select(x => $"{x.BirthDate} {x.DeathDate}");

// 8
File.WriteAllText("PeopleLighter.json", JsonSerializer.Serialize(peopleLighter_7_1));
File.WriteAllText("PeopleSorted.json", JsonSerializer.Serialize(peopleSorted_7_2));
File.WriteAllText("PeopleSkipped.json", JsonSerializer.Serialize(peopleSkipped_7_3));
File.WriteAllLines("PeopleConcatenated.json", peopleConcatenated_7_4);


// 1 - 3
static class MyLinq
{
    public static T MyLast<T>(this IEnumerable<T> source)
    {
		IEnumerator<T> enumerator = source.GetEnumerator();

        if (!enumerator.MoveNext())
        {
			throw new InvalidOperationException("Sequence contains no elements");
		}

        T last = enumerator.Current;
        while (enumerator.MoveNext())
			last = enumerator.Current;

        return last;
	}

	public static T MyLastOrDefault<T>(this IEnumerable<T> source)
    {
        T last = default!;

        foreach (T item in source)
			last = item;

        return last;
    }

    public static bool MyAny<T>(this IEnumerable<T> source)
    {
		IEnumerator<T> enumerator = source.GetEnumerator();
		return enumerator.MoveNext();
    }

    public static bool MyAll<T>(this IEnumerable<T> source, Func<T, bool> predicate)
    {
		foreach (T item in source)
			if (!predicate(item))
                return false;

		return true;
    }

    public static bool MyAny<T>(this IEnumerable<T> source, Func<T, bool> predicate)
    {
        foreach (T item in source)
            if (predicate(item))
				return true;

        return false;
    }

    public static IEnumerable<TOut> MySelect<TIn, TOut>(this IEnumerable<TIn> source, Func<TIn, TOut> predicate)
    {
		foreach (TIn item in source)
            yield return predicate(item);
    }

    public static IEnumerable<T> MyWhere<T>(this IEnumerable<T> source, Func<T, bool> predicate)
    {
        foreach (T item in source)
			if (predicate(item))
                yield return item;
    }

    public static IEnumerable<T> MyTake<T>(this IEnumerable<T> source, int count)
    {
		int i = 0;
		foreach (T item in source)
        {
			if (i >= count)
                yield break;
            i++;
            yield return item;
        }
    }

    public static IEnumerable<T> MySkip<T>(this IEnumerable<T> source, int count)
    {
        IEnumerator<T> enumerator = source.GetEnumerator();
        for (int i = 0; i < count; i++)
			if (!enumerator.MoveNext())
				yield break;

        while (enumerator.MoveNext())
            yield return enumerator.Current;
    }

    public static IEnumerable<T> MyTakeWhile<T>(this IEnumerable<T> source, Func<T, bool> predicate)
    {
		foreach (T item in source)
        {
            if (!predicate(item))
                yield break;

            yield return item;
        }
    }
}