
using System.ComponentModel.DataAnnotations;

namespace LinqExam.Models
{
    public class Person
    {
        public int Id { get; set; }
        public string Name { get; set; }
        public string Surname { get; set; }
        public string Motherland { get; set; }
        public DateTime BirthDate { get; set; }
        public DateTime? DeathDate { get; set; }
        public int SalaryPerMonth { get; set; }
        public int ChildCount { get; set; }
        public float Height { get; set; }
        public float Weight { get; set; }
    }
}
