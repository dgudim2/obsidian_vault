using Snaky.Core.Utils;
using Snaky.Game.Objects;
using Snaky.Tui.UiElements;
using Snaky.Tui.Utils;

namespace Snaky.Tui.Screens;

public class TuiMenuScreen : TuiScreen
{
    int selectedAppleIndex = 0;
    int totalAppleTypeCount = 0;
    private List<MessageBox> appleSelectionBoxes = [];

    public TuiMenuScreen()
    {
        var border = new Border(this);
        Objects.Add(border);
        Objects.Add(new MessageBox(this,
            $"{UnicodeSymbols.DOUBLE_MIDDLE_HORIZONTAL_LINE}Welcome to snaky!{UnicodeSymbols.DOUBLE_MIDDLE_HORIZONTAL_LINE}",
            1, MessageBox.Alignment.V_BOTTOM | MessageBox.Alignment.H_CENTER));
        Objects.Add(new MessageBox(this,
            $"{UnicodeSymbols.DOUBLE_MIDDLE_HORIZONTAL_LINE}Press ENTER to start{UnicodeSymbols.DOUBLE_MIDDLE_HORIZONTAL_LINE}",
            1, MessageBox.Alignment.V_CENTER | MessageBox.Alignment.H_CENTER));

        Objects.Add(new MessageBox(this,
            "Select you apple type :3",
            1, MessageBox.Alignment.V_TOP | MessageBox.Alignment.H_CENTER)
        {
            Position = new Vector2<int>(0, 3)
        });

        var appleTypes = Enum.GetNames<AppleType>();

        totalAppleTypeCount = appleTypes.Length;
        var i = 0;
        foreach (var name in appleTypes)
        {
            appleSelectionBoxes.Add(new MessageBox(this, name.ToLower(), 1,
                MessageBox.Alignment.V_TOP | MessageBox.Alignment.H_CENTER)
            {
                Position = new Vector2<int>(0, i + 5)
            });
            i++;
        }

        Objects.AddRange(appleSelectionBoxes);
    }

    public override bool DispatchKeyEvent(ConsoleKeyInfo key)
    {
        if (key.Key == ConsoleKey.Enter)
        {
            ChangeScreen(new MainGameScreen(Enum.GetValues<AppleType>()[selectedAppleIndex]));
        }

        selectedAppleIndex = key.Key switch
        {
            ConsoleKey.DownArrow => (selectedAppleIndex + 1) % totalAppleTypeCount,
            ConsoleKey.UpArrow => (selectedAppleIndex - 1) % totalAppleTypeCount,
            _ => selectedAppleIndex
        };

        if (selectedAppleIndex < 0)
        {
            selectedAppleIndex = totalAppleTypeCount - 1;
        }

        var appleTypes = Enum.GetNames<AppleType>();

        for (var i = 0; i < totalAppleTypeCount; i++)
        {
            appleSelectionBoxes[i].Text = (i == selectedAppleIndex ? "> " : "  ") + appleTypes[i].ToLower();
        }

        return base.DispatchKeyEvent(key);
    }
}