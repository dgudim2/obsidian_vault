using Snaky.Game.Objects;
using Snaky.Tui.UiElements;

namespace Snaky.Tui.Screens;

public class MainGameScreen : TuiScreen
{
    private MessageBox _fpsMonitor;

    public MainGameScreen()
    {
        var border = new Border(this);
        _fpsMonitor = new MessageBox(this, "Delta", 1, MessageBox.Alignment.V_BOTTOM | MessageBox.Alignment.H_LEFT);
        
        Objects.Add(border);
        Objects.Add(new Snake(this));        
        Objects.Add(_fpsMonitor);
    }

    public override void Update(float dt)
    {
        _fpsMonitor.Text = $"Delta: {dt}";
        base.Update(dt);
    }
}