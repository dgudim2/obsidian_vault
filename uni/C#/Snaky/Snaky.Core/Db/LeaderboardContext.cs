using Microsoft.EntityFrameworkCore;

namespace Snaky.Core.Db;

public class LeaderboardContext : DbContext
{
    public DbSet<ScoreModel> Scores { get; set; }

    private string DbPath { get; } = Path.Join("../Snaky.Core", "leaderboard.db");

    protected override void OnConfiguring(DbContextOptionsBuilder options)
        => options.UseSqlite($"Data Source={DbPath}");
}