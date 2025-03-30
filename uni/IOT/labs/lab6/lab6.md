# Lab 6 - Analog Signals Digitization

## Task 1

> [!info] 
> Reading and plotting values

`````col 
````col-md 
flexGrow=1
===

> [!note] 
> I am converting raw **ADC** (**A**nalog to **D**igital **C**onverter)
> > Default resolution for the arduino we are using is **10 bits** $2^{10} = 1024$

```cpp
#include <Arduino.h>

#define TEMP_SENSE_PIN A1
#define LIGHT_SENSE_PIN A0

float adc_max = 1024;

void setup() {
    pinMode(TEMP_SENSE_PIN, INPUT);
    pinMode(LIGHT_SENSE_PIN, INPUT);

    Serial.begin(115200);
}

void loop() {
    float temp_voltage = analogRead(TEMP_SENSE_PIN) / adc_max * 5.0;
    float light_voltage = analogRead(LIGHT_SENSE_PIN) / adc_max * 5.0;

    Serial.print(">T:");
    Serial.println(temp_voltage);

    Serial.print(">L:");
    Serial.println(light_voltage);
}
```

```` 
````col-md 
flexGrow=1
===

> [!note] 
> I am using *teleplot* VsCode extension

![[Screenshot_20250325_150649.png]]

> [!info] 
> Here, I touched the temperature sensor and covered the light sensor. We can see the voltage changing as the value changes

> [!note] 
> To convert the voltage to actual value, we would need a *conversion function* calibrated to this sensor

```` 
`````


## Task 2 - Controlling LEDs and 7-segment indicator by conditions


### Code for leds

> [!note] 
> <font color="#d83931">RED</font> solid - Too hot or too bright
> <font color="#92d050">GREEN</font> flashing - Too cold
> <font color="#00b0f0">BLUE</font> flashing - Too dark

```c++

#define RGB_PIN_B 10
#define RGB_PIN_G 11
#define RGB_PIN_R 12

int current_blue = false;
int current_green = false;

......

void setup() {
    ......

    pinMode(RGB_PIN_R, OUTPUT);
    pinMode(RGB_PIN_G, OUTPUT);
    pinMode(RGB_PIN_B, OUTPUT);
    
    // OFF - LOW (Inverted)
    digitalWrite(RGB_PIN_R, HIGH);
    digitalWrite(RGB_PIN_G, HIGH);
    digitalWrite(RGB_PIN_B, HIGH);
    
    ......
}

void loop() {
    ......
    
    bool temp_out_of_range = temp_voltage < 2.32 || temp_voltage > 2.35;

    if (light_voltage > 0.5 || temp_voltage > 2.35) { // Too cold or too dark
        if (temp_out_of_range) {
            digitalWrite(RGB_PIN_G, current_green % 30 > 15);
            current_green += 1;
        }
        current_blue += 1;
        digitalWrite(RGB_PIN_B, current_blue % 30 > 15);
    } else {
        current_green = 0;
        current_blue = 0;
        digitalWrite(RGB_PIN_G, HIGH);
        digitalWrite(RGB_PIN_B, HIGH);
    }

    bool too_big = light_voltage < 0.3 || temp_voltage < 2.32;

    digitalWrite(RGB_PIN_R, !too_big); // Too hot or too bright
}

```

### Code for 7-segment display

> [!note] 
> I am displaying <u>1 digit of voltage from each sensor</u>, but using a button to switch 2 sensors them is not hard to implement as well

```cpp

......

#define seg_a 25
#define seg_b 26
#define seg_c 27
#define seg_d 22
#define seg_e 23
#define seg_f 24
#define seg_g 28

......

void setup() {
......

pinMode(seg_a, OUTPUT);
pinMode(seg_b, OUTPUT);
pinMode(seg_c, OUTPUT);
pinMode(seg_d, OUTPUT);
pinMode(seg_e, OUTPUT);
pinMode(seg_f, OUTPUT);
pinMode(seg_g, OUTPUT);

pinMode(46, OUTPUT);
pinMode(47, OUTPUT);

digitalWrite(46, HIGH);
digitalWrite(47, HIGH);

......
}



void seg_first() {
    digitalWrite(46, HIGH);
    digitalWrite(47, LOW);
}

void seg_second() {
    digitalWrite(46, LOW);
    digitalWrite(47, HIGH);
}

void seg_none() {
    digitalWrite(46, HIGH);
    digitalWrite(47, HIGH);
}

void seg_0() {
    digitalWrite(seg_a, HIGH);
    digitalWrite(seg_b, HIGH);
    digitalWrite(seg_c, HIGH);
    digitalWrite(seg_d, HIGH);
    digitalWrite(seg_e, HIGH);
    digitalWrite(seg_f, HIGH);
    digitalWrite(seg_g, LOW);
}

void seg_1() {
    digitalWrite(seg_a, LOW);
    digitalWrite(seg_b, HIGH);
    digitalWrite(seg_c, HIGH);
    digitalWrite(seg_d, LOW);
    digitalWrite(seg_e, LOW);
    digitalWrite(seg_f, LOW);
    digitalWrite(seg_g, LOW);
}

void seg_2() {
    digitalWrite(seg_a, HIGH);
    digitalWrite(seg_b, HIGH);
    digitalWrite(seg_c, LOW);
    digitalWrite(seg_d, HIGH);
    digitalWrite(seg_e, HIGH);
    digitalWrite(seg_f, LOW);
    digitalWrite(seg_g, HIGH);
}

void seg_3() {
    digitalWrite(seg_a, HIGH);
    digitalWrite(seg_b, HIGH);
    digitalWrite(seg_c, HIGH);
    digitalWrite(seg_d, HIGH);
    digitalWrite(seg_e, LOW);
    digitalWrite(seg_f, LOW);
    digitalWrite(seg_g, HIGH);
}

void seg_4() {
    digitalWrite(seg_a, LOW);
    digitalWrite(seg_b, HIGH);
    digitalWrite(seg_c, HIGH);
    digitalWrite(seg_d, LOW);
    digitalWrite(seg_e, LOW);
    digitalWrite(seg_f, HIGH);
    digitalWrite(seg_g, HIGH);
}

void num_to_seg(int num) {
    switch (num) {
    case 0:
        seg_0();
        break;
    case 1:
        seg_1();
        break;
    case 2:
        seg_2();
        break;
    case 3:
        seg_3();
        break;
    case 4:
        seg_4();
        break;

    default:
        break;
    }
}

void loop() {

......

if (too_big) {
    seg_none();

    num_to_seg((int)temp_voltage);
    seg_first();
    delay(10);

    seg_none();

    num_to_seg((int)light_voltage);
    seg_second();
    delay(10);

    seg_none();
}

......

}

```

`````col 
````col-md 
flexGrow=1
===

![[20250325_155748.jpg]]
![[20250325_155804.jpg]]

```` 
````col-md 
flexGrow=1
===

![[20250325_155833.jpg]]
![[20250325_155856.jpg]]

```` 
`````

