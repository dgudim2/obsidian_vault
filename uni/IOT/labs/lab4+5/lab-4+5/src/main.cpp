/*
  Web client

 This sketch connects to a website (http://www.google.com)
 using an Arduino Wiznet Ethernet shield.

 Circuit:
 * Ethernet shield attached to pins 10, 11, 12, 13

 created 18 Dec 2009
 by David A. Mellis
 modified 9 Apr 2012
 by Tom Igoe, based on work by Adrian McEwen

 */

#include <Ethernet.h>
#include <SPI.h>

// Enter a MAC address for your controller below.
// Newer Ethernet shields have a MAC address printed on a sticker on the shield
byte mac[] = {0xDE, 0xAD, 0xBE, 0xEF, 0xFE, 0xFF};

IPAddress server(10, 21, 46, 14); // IP of the pc with thingsboard running

// Set the static IP address to use if the DHCP fails to assign
IPAddress ip(10, 21, 46, 68);
IPAddress myDns(192, 168, 0, 1);

// Initialize the Ethernet client library
// with the IP address and port of the server
// that you want to connect to (port 80 is default for HTTP):
EthernetClient client;

// Variables to measure the speed
unsigned long beginMicros, endMicros;
unsigned long byteCount = 0;
bool printWebData = true; // set to false for better speed measurement

const int trigPin = A3;
const int echoPin = A4;

void setup() {

    // Ultrasonic sensor setup
    pinMode(A2, OUTPUT);
    pinMode(trigPin, OUTPUT);
    pinMode(echoPin, INPUT);
    pinMode(A5, OUTPUT);

    digitalWrite(A5, LOW);
    digitalWrite(A2, HIGH);

    Serial.begin(505000);
    while (!Serial) {
        ;
    }

    Serial.println("Initialize Ethernet with DHCP:");
    if (Ethernet.begin(mac) == 0) {
        Serial.println("Failed to configure Ethernet using DHCP");
        // Check for Ethernet hardware present
        if (Ethernet.hardwareStatus() == EthernetNoHardware) {
            Serial.println("Ethernet shield was not found.  Sorry, can't run without hardware. :(");
            while (true) {
                delay(1); // do nothing, no point running without Ethernet hardware
            }
        }
        if (Ethernet.linkStatus() == LinkOFF) {
            Serial.println("Ethernet cable is not connected.");
        }
        // try to congifure using IP address instead of DHCP:
        Ethernet.begin(mac, ip, myDns);
    } else {
        Serial.print("  DHCP assigned IP ");
        Serial.println(Ethernet.localIP());
    }
    // give the Ethernet shield a second to initialize:
    delay(1000);
}

long microsecondsToCentimeters(long microseconds) { return microseconds / 29 / 2; }

void loop() {
    Serial.println("measuring distance...");

    long duration, cm;
    
    digitalWrite(trigPin, HIGH);
    delayMicroseconds(10);
    digitalWrite(trigPin, LOW);

    duration = pulseIn(echoPin, HIGH);
    
    cm = microsecondsToCentimeters(duration);

    Serial.print("measured distance is ");
    Serial.print(cm);
    Serial.println(" cm");

    Serial.print("connecting to ");
    Serial.print(server);
    Serial.println("...");

    String json = String("{\"distance\": ") + String(cm) + String(", \"raw\": ") + String(duration) + String("}");
    Serial.print("Sending json: ");
    Serial.println(json);

    // if you get a connection, report back via serial:
    if (client.connect(server, 8080)) {
        Serial.print("connected to ");
        Serial.println(client.remoteIP());
        // Make an HTTP request:
        client.println("POST /api/v1/uwo688l5ki16ipnjvtjd/telemetry HTTP/1.1");
        client.println("Host: localhost");
        client.println("User-Agent: Arduino/1.0");
        client.println("Content-Type: application/json;charset=UTF-8");
        client.println("Connection: close");
        client.print("Content-Length: ");
        client.println(json.length());
        client.println();
        client.println(json);
    } else {
        // if you didn't get a connection to the server:
        Serial.println("connection failed");
    }

    beginMicros = micros();
    byteCount = 0;

    while (client.connected()) {
        // if there are incoming bytes available
        // from the server, read them and print them:
        int len = client.available();
        if (len > 0) {
            byte buffer[80];
            if (len > 80)
                len = 80;
            client.read(buffer, len);
            if (printWebData) {
                Serial.write(buffer, len); // show in the serial monitor (slows some boards)
            }
            byteCount = byteCount + len;
        }
    }

    endMicros = micros();
    Serial.println();
    Serial.println("disconnecting.");
    client.stop();
    Serial.print("Received ");
    Serial.print(byteCount);
    Serial.print(" bytes in ");
    float seconds = (float)(endMicros - beginMicros) / 1000000.0;
    Serial.print(seconds, 4);
    float rate = (float)byteCount / seconds / 1000.0;
    Serial.print(", rate = ");
    Serial.print(rate);
    Serial.print(" kbytes/second");
    Serial.println();
    Serial.println();
    Serial.println();
    Serial.println();

    delay(1000);
}
