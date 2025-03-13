# Lab 5 - Ethernet Internet Module 2

> [!seealso] 
> This is a continuation of [[lab4]]

## Task 1

> [!info] 
> Installing thingsboard

This is a prerequisite to the next task. Instead of using **thingspeak** cloud platform, I used **thingsboard** local webserver because I hate cloud services that have no need to be in the cloud.

1. Follow official thingsboard docs for running a server
    - https://thingsboard.io/docs/user-guide/install/docker/
2. Create a device in the webui
    - https://thingsboard.io/docs/user-guide/ui/devices/
3. That's it :)

## Task 2

> [!info] 
> Posting *json* payloads to **thingsboard**

`````col 
````col-md 
flexGrow=1
===

> [!note] 
> I left only the relevant parts of the code, the rest is the same as in the *web client* demo

```cpp

......
Includes and stuff
......

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

    ......
    Init Ethernet hardware
    ......
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
        ....
        Print message to serial
        ....
    }

    endMicros = micros();
    .....
    Print speed
    .....

    delay(1000);
}

```

```` 
````col-md 
flexGrow=1
===

> [!note] 
> Here we intialize our hardware and start an infinite loop sending the distance and raw timing to the dashboard


> ### Output from serial
![[Screenshot_20250304_150644.png]]

> ### Graphs on the dashboard
![[Screenshot_20250304_150726.png]]

```` 
`````