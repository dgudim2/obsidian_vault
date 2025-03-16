using System.ComponentModel.DataAnnotations;
using Microsoft.EntityFrameworkCore;

namespace Snaky.Core.Db;

public class ScoreModel
{
    [Key]
    public int Id { get; set; }
    public string Name { get; set; }
    public int Score { get; set; }
}