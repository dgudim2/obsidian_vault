/*
5.
Artur R.
1) Realizuokite praplėtimo metodą kurio algoritmo rezultatas būtų atitinkamas rezultatui metodo 'System.Linq.Any' (1 t.)
2) Realizuokite praplėtimo metodą kurio algoritmo rezultatas būtų atitinkamas rezultatui metodo 'System.Linq.All' (1 t.)
3) Realizuokite praplėtimo metodą kurio algoritmo rezultatas būtų atitinkamas rezultatui metodo 'System.Linq.LastOrDefault' (1 t.)
4) Patikrinkite ar taisingai atlikote užduotis 1-3 naudojant 'System.Linq.SequenceEqual' (0,5 t.)
5) Deserializuokite gautą JSON į 'Person' objektų sarašą. (2 t.)
6) Su gautu 5 punkte sąrašu atlikite sekančius veiksmus: (2 t.)
      1. Palikite sąraše tik tuos žmones, kurie mirė iki šiandienos
      2. Surikiuokite elementus pagal parametrą 'Weight', po to pagal 'Height'
              2.1. Patikrinkite ar nors vienas elementas atitinka salygą: Pavardė neturi raidės 'I'
              2.2. Išveskite elementą su indeksu 434 arba reikšme pagal nutylėjimą, jei elemento nėra
      3. Praleiskite 18 elementų(-us) iš galo
      4. Pasirinkite tik parametrą 'childCount'

7) Su gautu 5 punkte sąrašu atlikite sekančius veiksmus: (2 t.)
      1. Palikite sąraše tik tuos žmones, kurių pavardės turi mažiau nei 6 raides
      2. Surikiuokite elementus pagal parametrą 'Motherland', po to pagal 'Id'
              2.1. Patikrinkite ar visi elementai atitinka salygą: Vardas neturi raidės 'J'
              2.2. Išveskite elementą su indeksu 205 arba reikšme pagal nutylėjimą, jei elemento nėra
      3. Praleiskite 22 elementų(-us) iš priekio
      4. Pasirinkite kiek dienų pragyveno žmonės

8) Gautus sąrašus išsaugokite į skirtingus failus (0,5 t.)
*/

using LinqExam.Models;
using System.Text.Json;

// 4
List<int> nums = new List<int> { 1, 2, 3, 4, 5 };
Console.WriteLine("My LINQ methods:");
Console.WriteLine("MyAny: " + (nums.MyAny(x => x == 3) == nums.Any(x => x == 3)));
Console.WriteLine("MyAny: " + (nums.MyAny() == nums.Any()));
Console.WriteLine("MyAll: " + (nums.MyAll(x => x > 0) == nums.All(x => x > 0)));
Console.WriteLine("MyLastOrDefault: " + (nums.MyLastOrDefault() == nums.LastOrDefault()));

// 5
string json = File.ReadAllText("People.json");
List<Person> people = JsonSerializer.Deserialize<List<Person>>(json)!;

// 6
IEnumerable<Person> t6_1 = people.Where(x => x.DeathDate < DateTime.Now.Date);
IEnumerable<Person> t6_2 = t6_1.OrderBy(x => x.Weight).ThenBy(x => x.Height);

Console.WriteLine("t6_2_1: " + t6_2.Any(x => !x.Surname.Contains('I', StringComparison.InvariantCultureIgnoreCase)));
Console.WriteLine("t6_2_2: " + t6_2.ElementAtOrDefault(434) ?? "Default");

IEnumerable<Person> t6_3 = t6_2.SkipLast(18);
IEnumerable<int> t6_4 = t6_3.Select(x => x.ChildCount);

// 7
IEnumerable<Person> t7_1 = people.Where(x => x.Surname.Length < 6);
IEnumerable<Person> t7_2 = t7_1.OrderBy(x => x.Motherland).ThenBy(x => x.Id);

Console.WriteLine("t7_2_1: " + t7_2.All(x => !x.Name.Contains('J', StringComparison.InvariantCultureIgnoreCase)));
Console.WriteLine("t7_2_2: " + t7_2.ElementAtOrDefault(205) ?? "Default");

IEnumerable<Person> t7_3 = t7_2.Skip(22);
IEnumerable<int> t7_4 = t7_3.Select(x => (x.DeathDate - x.BirthDate)!.Value.Days);

// 8
File.WriteAllText("t6_4.json", JsonSerializer.Serialize(t6_4));
File.WriteAllText("t7_4.json", JsonSerializer.Serialize(t7_4));



public static class MyCustomLinq
{
    // 1
    public static bool MyAny<T>(this IEnumerable<T> source, Func<T, bool> predicate)
    {
        if (source == null)
        {
            throw new ArgumentNullException(nameof(source));
        }

        if (predicate == null)
        {
			throw new ArgumentNullException(nameof(predicate));
		}

		foreach (var item in source)
        {
			if (predicate(item))
            {
				return true;
			}
		}
		return false;
	}

    // 1
    public static bool MyAny<T>(this IEnumerable<T> source)
    {
        if (source == null)
        {
			throw new ArgumentNullException(nameof(source));
		}

        if (source is IList<T> list)
        {
            return list.Count > 0;
        }

        foreach (var item in source)
        {
			return true;
		}

        return false;
    }

    // 2
    public static bool MyAll<T>(this IEnumerable<T> source, Func<T, bool> predicate)
    {
		if (source == null)
        {
			throw new ArgumentNullException(nameof(source));
		}

		if (predicate == null)
        {
            throw new ArgumentNullException(nameof(predicate));
        }

        foreach (var item in source)
        {
            if (!predicate(item))
            {
				return false;
			}
        }

        return true;
    }

    // 3
    public static T MyLastOrDefault<T>(this IEnumerable<T> source)
    {
		if (source == null)
        {
			throw new ArgumentNullException(nameof(source));
		}

		if (source is IList<T> list)
        {
			if (list.Count == 0)
            {
				return default;
			}

			return list[list.Count - 1];
		}

		T result = default;
		foreach (var item in source)
        {
			result = item;
		}

		return result;
	}   
}