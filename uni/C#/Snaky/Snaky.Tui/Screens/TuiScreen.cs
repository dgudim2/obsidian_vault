using System.Runtime.InteropServices;
using Snaky.CommonUI.Screens;
using Snaky.Core.Interfaces;
using Snaky.Core.Utils;
using Snaky.Tui.UiElements;
using Snaky.Tui.Utils;

namespace Snaky.Tui.Screens;

public abstract class TuiScreen : BaseScreen
{
    private bool _windowSizeValid;
    protected bool WindowSizeValidForRender;
    private readonly MessageBox _warningMessageBox;

    protected TuiScreen()
    {
        new Thread(_ =>
        {
            Thread.Sleep(200);
            WindowSizeValidForRender = false;
            _windowSizeValid = false;
        }).Start();
        PosixSignalRegistration.Create(PosixSignal.SIGWINCH, _ =>
        {
            WindowSizeValidForRender = false;
            _windowSizeValid = false;
        });
        _warningMessageBox = new MessageBox(this, "<Terminal window is too small>", 1,
            MessageBox.Alignment.H_CENTER | MessageBox.Alignment.V_CENTER);
    }

    public override bool DimensionsValid()
    {
        return WindowSizeValidForRender && _windowSizeValid;
    }

    protected override Vector2<int> RecalculateDimensions()
    {
        _windowSizeValid = true;
        return new Vector2<int>(Console.BufferWidth, Console.BufferHeight);
    }

    public override void Render()
    {
        var size = Size;

        if (!DimensionsValid())
        {
            Console.Clear();
        }

        if (size.X < 20 || size.Y < 20)
        {
            Console.Clear();
            _warningMessageBox.Render();
            return;
        }

        Objects.ForEach(o => o.Render());
        
        if (!DimensionsValid())
        {
            WindowSizeValidForRender = true;
        }
    }
}