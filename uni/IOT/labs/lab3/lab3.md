# Lab3 - Distance sensing

## Task 1

> [!info] 
> Simple distance measurement

This task requires us to write a simple program to measure the distance and print it to serial monitor

`````col 
````col-md 
flexGrow=1
===

```cpp
#include <Arduino.h>

const int trigPin = 48;
const int echoPin = 8;

void setup() {
    Serial.begin(9600);
    pinMode(trigPin, OUTPUT);
    pinMode(echoPin, INPUT);
}

long microsecondsToCentimeters(long microseconds) { return microseconds / 29 / 2; }

void loop() {
    long duration, cm;
    
    digitalWrite(trigPin, HIGH);
    delayMicroseconds(10);
    digitalWrite(trigPin, LOW);

    duration = pulseIn(echoPin, HIGH);
    
    cm = microsecondsToCentimeters(duration);

    Serial.print("distance: ");
    Serial.print(cm);
    Serial.print("cm");
    Serial.println();

    delay(1000);
}
```

```` 
````col-md 
flexGrow=1
===

> [!note] 
> - **trigPin** is the pin used to send an *ultrasonic pulse*, it also sets an *internal sr-latch* on the sensor to *HIGH*, so we can measure the pulse duration on the **echoPin**
> - After adjusting for room temperature (*22°*) and calibrating the sensor with a ruler, the formula for converting microseconds to cm is $\text{microseconds} / 29 / 2$

![[Screenshot_20250218_153710.png]]

![[Screenshot_20250218_153716.png]]

```` 
`````

## Task 2

> [!info] 
> Observing signals with an *oscilloscope*

This task requires us to use an oscilloscope to observe the **echo** and **trig** pins

`````col 
````col-md 
flexGrow=1
===

> [!note] 
> It's advised to *disable any artificial delays in the code* so the oscilloscope tracks our pulses better

![[20250218_153637.jpg]]

```` 
````col-md 
flexGrow=1
===

>[!note]
> Here we can observe the **trig** pulse hapenning every *200* ms

![[20250218_152058.jpg]]

```` 
`````

### Let's measure both pins!

> [!info] 
> - Here, I connected *both probes* to the oscilloscope, <font color="#ffc000">yellow</font> channel is the **trig** pin, <font color="#e90ac2">pink</font> channel is the **echo** pin. 
> - We can clearly see the **trig** signal being sent, the sensor latching **echo** pin to *HIGH*, pulse delay (traveling, reflecting, coming back) and resetting **echo** latch back to *LOW*

`````col 
````col-md 
flexGrow=1
===

> [!note] 
> Here the distance to the object was about **12cm**, the pulse duration if quite short

![[Screenshot_20250218_152801.png]]

![[20250218_161033.jpg]]

```` 
````col-md 
flexGrow=1
===

> [!note]
> Here the distance was increased to **46cm**, the pulse duration got much longer (3.88 times)

![[Screenshot_20250218_153716.png]]

![[20250218_161024(0).jpg]]

```` 
`````


## Task 3

> [!info] 
> Displaying measured distance on the seven segment display

This task requires us to display the measured distance on the included *7-segment* display

`````col 
````col-md 
flexGrow=1
===

> [!note]
> I modified the original code to use *sevSeg* library to work with the display


```cpp
#include <Arduino.h>
#include "SevSeg.h"

SevSeg sevseg;

const int trigPin = 48;
const int echoPin = 8;

void setup() {
    Serial.begin(9600);
    pinMode(trigPin, OUTPUT);
    pinMode(echoPin, INPUT);

    byte numDigits = 2;
    byte digitPins[] = {47, 46};
    byte segmentPins[] = {22, 23, 24, 25, 26, 27, 28};
    bool resistorsOnSegments = false;
    byte hardwareConfig = COMMON_CATHODE;
    bool updateWithDelays = true;
    bool leadingZeros = false;
    bool disableDecPoint = true;
    
    sevseg.begin(hardwareConfig, numDigits, digitPins, segmentPins, resistorsOnSegments,
    updateWithDelays, leadingZeros, disableDecPoint);
    sevseg.setBrightness(100);
}

long microsecondsToCentimeters(long microseconds) { return microseconds / 29 / 2; }

void loop() {
    long duration, cm;
    
    digitalWrite(trigPin, HIGH);
    delayMicroseconds(10);
    digitalWrite(trigPin, LOW);

    duration = pulseIn(echoPin, HIGH);
    
    cm = microsecondsToCentimeters(duration);

    Serial.print("distance: ");
    Serial.print(cm);
    Serial.print("cm");
    Serial.println();

    sevseg.setNumber(cm, 1);
    for(int i = 0; i < 14; i++) {
      sevseg.refreshDisplay(); // Must run repeatedly
    }
}
```

```` 
````col-md 
flexGrow=1
===

![[20250218_151002.jpg]]

![[20250218_151016.jpg]]

```` 
`````
