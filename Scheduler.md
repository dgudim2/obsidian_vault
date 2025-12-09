---

kanban-plugin: board

---

## Bugs

- [ ] **Transparency is broken on 100%**
    
    If the transparency slider is at 100%, the background is black
    
    @{2024-02-22}
- [ ] **Turn off predictive gesture**
    
    @{2024-02-22}
- [ ] **Lock screen not refreshing on bg change**
    
    Setting a new per day bg in settings does not trigger a lockscreen update
    
    @{2024-03-02}
- [ ] **changing blend on an entry does not work**
    
    changing 'blend with background' on a specific entry does not work
    
    @{2024-08-31}
- [ ] "No events" buggy when enabling and disabling calendar
    
    @{2025-12-09}


## Features

- [ ] **Contribution section**
    
    Add contribution section with translators, open when clicking the "version" text
    
    @{2023-03-12}
- [ ] **Option to archive entries for faster loading**
    
    Add an option to put completed entries into a separate list
    
    @{2023-05-08}
- [ ] **Menu for editing groups**
    
    Add a menu to edit group parameters (better than re-saving groups)
    
    @{2023-05-12}
- [ ] **Put the event on the day it was completed**
    Add an option to put the event on the day it was completed (expired events completed 2 days later)
    
    @{2023-08-01}
- [ ] **More group options**
    - Option to choose default group
    - Option to collapse events by groups
    
    @{2023-08-01}
- [ ] **Per event backgrounds**
    Ability to set backgrounds if some event happens
    @{2023-08-31}
- [ ] **Cache view for dialogs in a ViewModel**
    
    Recreate on config change (bind to lifetime)
    
    @{2023-10-01}
- [ ] **Collapse events hidden by content**
    
    Optionally show an expand button
    
    @{2023-10-15}
- [ ] **Option to pad events on the lockscreen**
    
    Option to configure padding, not just min/max width of events on the lockscreen
    
    @{2023-04-01}
- [ ] **Add settings to view types (rectangle, rounded, etc)**
    
    Also make the dialog more clear with selection, graying-out of not selected views
    
    @{2023-11-09}
- [ ] **Sync with markdown files and nextcloud/calDAV**
    
    Sync integration with those services
    
    @{2023-04-01}
- [ ] **Event length**
    Add an option to display event length in brackets for regular events
    
    @{2023-12-05}
- [ ] **Bottom menu**
    Add a bottom menu (switcher)?
    
    @{2024-01-05}
- [ ] **DB stats in the debug menu**
    
    Display stats for the vents in the debug menu
    @{2024-01-05}
- [ ] **Granular time reminders**
    
    Add an option for granular reminders (i.e. in 2 hours)
    @{2024-01-05}
- [ ] **Display full date for todo events**
    
    Display a full date for upcoming/expired todo events
    
    @{2024-02-22}
- [ ] **Expose saves via a file provider**
    
    Self-explanatory
    
    @{2024-02-22}
- [ ] **Auto-updater**
    
    Use the new UPDATE_PACKAGE_WITHOUT_USER_REACTION
    
    @{2024-02-22}
- [ ] **Option to open links**
    
    Search for links in todos, add a button/menu to  open them
    
    @{2024-02-24}
- [ ] **Add as a target in share menu**
    
    Register with android's share menu (send from other apps)
    
    @{2024-02-24}
- [ ] **Cleanup database**
    
    Remove invalid entries from deleted calendars periodically
    
    @{2024-03-02}
- [ ] **Merge events with the same timeframe**
    
    Display 2 events with the same starting/ending time as one event (optional)
    
    @{2024-03-07}
- [ ] **Indicator settings**
    
    Add settings for calendar event indicators
    
    @{2024-03-13}
- [ ] **Auto-export**
    
    Run export on schedule
     
    @{2024-05-31}
- [ ] **Photo picker opt-out**
    
    A way to opt out of google's crappy crappy picker (Same as in saucenao)
    
    @{2024-05-31}
- [ ] **Group/prompt on event duplicates**
    
    Warn or group events with the same contents
    
    @{2024-05-31}
- [ ] **'Complete today' button**
    
    'Complete today' option for past of future events
    
    @{2024-08-31}
- [ ] **> in a month.**
    
    Be more specific (i.e in about 5 months)
    
    
    
    @{2024-08-31}
- [ ] **events api**
    
    api to get events, integrate with tasker auromation (remind if there are uncompleted events when leaving work)
    
    @{2024-08-31}
- [ ] **locale day of the week**
    
    option to use device default first day of the week
    
    @{2024-08-31}
- [ ] **global group**
    
    Add an option to make a group have a global flag
    
    @{2024-08-31}
- [ ] **Multi-select mode**
    
    Option to select and edit/delete multiple events
    
    @{2024-08-31}
- [ ] **Multi-day event labels**
    
    show length of events / date for events spanning multiple days
    
    @{2024-08-31}
- [ ] **Regex styling**
    
    Option to apply styles based on regex rules
    
    @{2024-08-31}
- [ ] ability to mark calendar events as completed for the day
    
    @{2025-12-09}
- [ ] ability to mark calendar events as completed for the day
    
    @{2025-12-09}
- [ ] font boldness, blur effect for text as well, blur bleed out for 'glow in the dark' effect
    
    @{2025-12-09}
- [ ] option to clear logcat, clear Pictures (cached crops) and export zip periodically
    
    @{2025-12-09}
- [ ] implement search + integrate with system search
    
    @{2025-12-09}
- [ ] option to use a system calendar as the backing store
    
    @{2025-12-09}
- [ ] save creation datetime of events
    
    @{2025-12-09}
- [ ] scheduler wallpaper integration with weather (breeze weather)
    
    @{2025-12-09}
- [ ] shortcut to settings on system app info page
    
    @{2025-12-09}
- [ ] statistics on completed events
    
    @{2025-12-09}
- [ ] style calendar days if some event is present
    
    @{2025-12-09}
- [ ] tasks support
    
    @{2025-12-09}
- [ ] when editing event text, show event color in the corner/with border
    
    @{2025-12-09}


## Fixed bugs

- [x] **Event count doesn't update**
    
    When adding an event/sync from calendar event count doesn't update
    
    @{2023-03-12}
- [x] **Differently sized icons in sorting settings**
    
    Icons in the sorting settings have different sizes
    @{2024-01-05}
- [x] **Dialog fragment**
    
    Broken gestures in event settings
    
    @{2023-03-12}
- [x] **Bugged "Отчет Виктории"**
    
    See event in the calendar


## Implemented features

- [x] **Possibility to view uncompleted events**
    
    @{2023-11-02}
- [x] **Hide events excluded by content from tommorow/yesterday**
    
    Currently still visible which is kinda annoying
    
    @{2023-04-01}
- [x] **Open settings on long click**
    
    self-describing
    
    @{2023-09-01}
- [x] **Open calendar event descriptions/details on click**
    
    When clicking on event from system calendar open it's details instead of doing nothing
    
    @{2023-04-01}
- [x] **Import/export as zip**
    
    better import/export
    @{2023-09-01}
- [x] **An option to sort calendar entries separately**
    
    @{2023-03-12}
- [x] **Add a toast when the even is added**
    
    Display "Event was added" toast
    
    @{2023-08-01}
- [x] **download readme**
    Add obtanium to scheduler readme
    
    @{2023-08-01}
- [x] **An option to put "global" in front of event**
    
    Currently (Global) is added only to the back
    
    @{2023-04-01}


## Archive

- [ ] **Broken calendar name updates**
    
    When the calendar is renamed in Google calendar it's not renamed in the app
    
    @{2023-03-12}




%% kanban:settings
```
{"kanban-plugin":"board","tag-colors":[]}
```
%%