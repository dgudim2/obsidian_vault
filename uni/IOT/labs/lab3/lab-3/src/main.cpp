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
    bool resistorsOnSegments = false; // 'false' means resistors are on digit pins
    byte hardwareConfig = COMMON_CATHODE; // See README.md for options
    bool updateWithDelays = true; // Default 'false' is Recommended
    bool leadingZeros = false; // Use 'true' if you'd like to keep the leading zeros
    bool disableDecPoint = true; // Use 'true' if your decimal point doesn't exist or isn't connected
    
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