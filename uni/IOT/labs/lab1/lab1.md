# Lab 1 - General IO

## Task 1

> [!info] 
> Display your first name, last name, the group you are studying in, your hobby, and today's date on separate lines on the Serial Monitor. The example on the previous slide shows how to output text to the Serial Monitor.

This task requires setting up serial communication and printing some stuff

> [!note]
> - `Serial.begin(9600)` enables serial communication (**UART**) on the default serial port (pins 0 and 1) on the arduino with the specified baud rate
> - `Serial.println(":)")` Uses that serial connection and sends text to the other connected device (pc in our case)

`````col 
````col-md 
flexGrow=1
===

```cpp
...
void setup() {
    Serial.begin(9600);

    Serial.println("Danila Gudim");
    Serial.println("20222136");
    Serial.println("Programming");
    Serial.println("04.02.2025");

...
```

```` 
````col-md 
flexGrow=1
===

![[task1.png]]

```` 
`````

## Task 2

> [!info]
> Display your age on the seven-segment indicators so that both indicators are lit at the same time.

This task requires us to turn the leds on the 7-segment indicator in proper order and with proper timings

>[!note]
>`pinMode` sets the mode of the pin on the arduino, can be either **OUTPUT** (pulled low by default), **INPUT** (floating), **INPUT_PULLUP** (+5v, 30k resistor) or **INPUT_PULLDOWN** (not supported on ATMega\*8)
>`digitalWrite` sets the pin logic level, either **HIGH** (+5v), or **LOW** (0v), pin needs to be set to **OUTPUT** for this to work
>`delay` just pauses program execution for a specified amount in milliseconds

`````col 
````col-md 
flexGrow=1
===

### Setup:

```cpp
// Segments
pinMode(22, OUTPUT);
pinMode(23, OUTPUT);
pinMode(24, OUTPUT);
pinMode(25, OUTPUT);
pinMode(26, OUTPUT);
pinMode(27, OUTPUT);
pinMode(28, OUTPUT);

// Indicators
pinMode(46, OUTPUT);
pinMode(47, OUTPUT);
```

### Loop:

```cpp
// 0 on the second segment
digitalWrite(46, LOW);
digitalWrite(47, HIGH);

digitalWrite(22, HIGH);
digitalWrite(23, HIGH);
digitalWrite(24, HIGH);
digitalWrite(25, HIGH);
digitalWrite(26, HIGH);
digitalWrite(27, HIGH);
digitalWrite(28, LOW);

delay(1);
digitalWrite(46, HIGH);
digitalWrite(47, HIGH);
delay(1);

digitalWrite(46, HIGH);
digitalWrite(47, LOW);

// 2 on the first segment
digitalWrite(22, HIGH);
digitalWrite(23, HIGH);
digitalWrite(24, LOW);
digitalWrite(25, HIGH);
digitalWrite(26, HIGH);
digitalWrite(27, LOW);
digitalWrite(28, HIGH);

delay(2);
```

```` 
````col-md 
flexGrow=1
===
![[20250204_154553.jpg]]
```` 
`````

## Task 3

> [!info]
> There are four switches - buttons on the El_lab board: M1, M2, M3, and M4. They are connected to terminals 2, 3, 43, 44. Work with three buttons and an RGB LED with pins 10, 11, and 12.

This task requires us to read the state of the buttons and control leds in response (**PWM** brightness)

>[!note]
>`#define` just lets us define compile-time substitutions
>`pinMode` has been described before, here we set button pins to **INPUT_PULLUP** because the buttons are disconnected by default and connected to **GND** (0v) when pressed, so we need to connected them to **HIGH** (+5v) internally or externally, which we do with **INPUT_PULLUP**, it connects a *30k* resistor to +5v in parallel with our button. Button state is inverted in code because of this
>`digitalRead` reads the logic value of a pin, **HIGH** (> 2.5v) or **LOW** (< 2.5v)
>`analogWrite` outputs either a variable voltage if the target pin is **analog** or a **PWM** signal if the target pin is digital, ether of them allow us to control the brightness of the leds


`````col 
````col-md 
flexGrow=1
===

### Setup + defines

```cpp
#define BTN_PIN_1 2
#define BTN_PIN_2 3
#define BTN_PIN_3 43
#define BTN_PIN_4 44

#define RGB_PIN_R 10
#define RGB_PIN_G 11
#define RGB_PIN_B 12

...

pinMode(BTN_PIN_1, INPUT_PULLUP);
pinMode(BTN_PIN_2, INPUT_PULLUP);
pinMode(BTN_PIN_3, INPUT_PULLUP);
pinMode(BTN_PIN_4, INPUT_PULLUP);

pinMode(RGB_PIN_R, OUTPUT);
pinMode(RGB_PIN_G, OUTPUT);
pinMode(RGB_PIN_B, OUTPUT);
```

### Loop:
```cpp
int btn1 = digitalRead(BTN_PIN_1);
int btn2 = digitalRead(BTN_PIN_2);
int btn3 = digitalRead(BTN_PIN_3);
int btn4 = digitalRead(BTN_PIN_4);

if (!btn1) {
    r = (r + 1) % 256;
}

if (!btn2) {
    g = (g + 1) % 256;
}

if (!btn3) {
    b = (b + 1) % 256;
}

if (!btn4) {
    r = 0;
    g = 0;
    b = 0;
}

analogWrite(RGB_PIN_R, 255 - r);
analogWrite(RGB_PIN_G, 255 - g);
analogWrite(RGB_PIN_B, 255 - b);
```

```` 
````col-md 
flexGrow=1
===

![[20250204_152833.jpg]]
![[20250204_152836.jpg]]

```` 
`````