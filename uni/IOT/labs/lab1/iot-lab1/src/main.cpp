#include <Arduino.h>

#define BTN_PIN_1 2
#define BTN_PIN_2 3
#define BTN_PIN_3 43
#define BTN_PIN_4 44

#define RGB_PIN_R 10
#define RGB_PIN_G 11
#define RGB_PIN_B 12

#define LED_PIN1 A12
#define LED_PIN2 A13
#define LED_PIN3 A14
#define LED_PIN4 A15

#define BUZZER_PIN A8

void setup()
{
    Serial.begin(9600);

    Serial.println("Danila Gudim");
    Serial.println("20222136");
    Serial.println("Programming");
    Serial.println("04.02.2025");

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

    pinMode(BTN_PIN_1, INPUT_PULLUP);
    pinMode(BTN_PIN_2, INPUT_PULLUP);
    pinMode(BTN_PIN_3, INPUT_PULLUP);
    pinMode(BTN_PIN_4, INPUT_PULLUP);

    pinMode(RGB_PIN_R, OUTPUT);
    pinMode(RGB_PIN_G, OUTPUT);
    pinMode(RGB_PIN_B, OUTPUT);

    pinMode(LED_PIN1, OUTPUT);
    pinMode(LED_PIN2, OUTPUT);
    pinMode(LED_PIN3, OUTPUT);
    pinMode(LED_PIN4, OUTPUT);

    pinMode(BUZZER_PIN, OUTPUT);
}

void all_star()
{
    // first verse
    tone(BUZZER_PIN, 370, 600);
    delay(600);

    tone(BUZZER_PIN, 554, 300);
    delay(300);

    tone(BUZZER_PIN, 466, 300);
    delay(300);

    tone(BUZZER_PIN, 466, 600);
    delay(600);

    tone(BUZZER_PIN, 415, 300);
    delay(300);

    tone(BUZZER_PIN, 370, 300);
    delay(300);

    tone(BUZZER_PIN, 370, 300);
    delay(300);

    tone(BUZZER_PIN, 494, 600); // B
    delay(600);

    tone(BUZZER_PIN, 466, 300);
    delay(300);

    tone(BUZZER_PIN, 466, 300);
    delay(300);

    tone(BUZZER_PIN, 415, 300);
    delay(300);

    tone(BUZZER_PIN, 415, 300);
    delay(300);

    tone(BUZZER_PIN, 370, 600);
    delay(600);

    tone(BUZZER_PIN, 370, 300);
    delay(300);

    tone(BUZZER_PIN, 554, 300);
    delay(300);

    tone(BUZZER_PIN, 466, 300);
    delay(300);

    tone(BUZZER_PIN, 466, 600);
    delay(600);

    tone(BUZZER_PIN, 415, 300);
    delay(300);

    tone(BUZZER_PIN, 370, 300);
    delay(300);

    tone(BUZZER_PIN, 370, 300);
    delay(300);

    tone(BUZZER_PIN, 311, 600);
    delay(600);

    tone(BUZZER_PIN, 277, 900);
    delay(1500);

    tone(BUZZER_PIN, 370, 300);
    delay(300);

    tone(BUZZER_PIN, 370, 300);
    delay(300);

    tone(BUZZER_PIN, 554, 300);
    delay(300);
}

int r = 0;
int g = 0;
int b = 0;

int counter = 0;

void loop()
{

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
        all_star();
    }

    analogWrite(RGB_PIN_R, 255 - r);
    analogWrite(RGB_PIN_G, 255 - g);
    analogWrite(RGB_PIN_B, 255 - b);

    counter += 4;

    analogWrite(LED_PIN1, (counter > 200 && counter < 300) * 255);
    analogWrite(LED_PIN2, (counter > 300 && counter < 400) * 255);
    analogWrite(LED_PIN3, (counter > 400 && counter < 500) * 255);
    analogWrite(LED_PIN4, (counter > 500 && counter < 600) * 255);

    if (counter > 600) {
        counter = 200;
    }
}
