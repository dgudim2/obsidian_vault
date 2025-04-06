using System.Diagnostics;
using Snaky.CommonUI.Interfaces;
using Snaky.Core.Interfaces;
using Snaky.Core.Utils;
using Snaky.Game.Objects;

namespace Snaky.Game;

public class Game : IRenderable
{
    protected TimeSpan UpdateDelta = TimeSpan.FromSeconds(1 / 240f);
    protected IScreen CurrentScreen;
    protected GameState CurrentGameState;

    public Game(IScreen startingScreen)
    {
        CurrentGameState = GameState.ACTIVE;
        OnScreenTransition(startingScreen);
        Console.Out.WriteLine("Starting Game...");
    }

    private void OnScreenTransition(IScreen newScreen)
    {
        CurrentScreen = newScreen;
        CurrentScreen.OnScreenTransition += OnScreenTransition;
    }

    private static ConsoleKeyInfo GetKeyEvent()
    {
        return Console.KeyAvailable
            ? Console.ReadKey(true)
            : new ConsoleKeyInfo(char.MinValue, ConsoleKey.None, false, false, false);
    }

    public void Render()
    {
        var sw = new Stopwatch();
        float dt = 0;
        while (true)
        {
            try
            {
                sw.Restart();
                var pressedKey = GetKeyEvent();

                if (pressedKey.Key == ConsoleKey.Delete) {
                    break;
                }

                if (pressedKey.Key == ConsoleKey.Escape)
                {
                    CurrentGameState = CurrentGameState == GameState.ACTIVE ? GameState.PAUSED : GameState.ACTIVE;
                    Thread.Sleep(100);
                    dt -= 100;
                }

                if (CurrentGameState != GameState.PAUSED)
                {
                    CurrentScreen.DispatchKeyEvent(pressedKey);
                    CurrentScreen.Update(dt);
                    CurrentScreen.Render();
                }

                Thread.Sleep(UpdateDelta);
                sw.Stop();
                dt = sw.ElapsedTicks / (float)Stopwatch.Frequency * 1000;
            }
            catch (Exception e)
            {
                Console.Clear();
                Console.WriteLine("Render error: {0}", e);
                throw;
            }
        }
    }

    public bool DimensionsValid()
    {
        return CurrentScreen.DimensionsValid();
    }

    public Vector2<int> Position
    {
        get => CurrentScreen.Position;
        set => CurrentScreen.Position = value;
    }

    public Vector2<int> Size => CurrentScreen.Size;
}
