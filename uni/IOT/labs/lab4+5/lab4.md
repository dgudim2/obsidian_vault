# Lab 4 - Ethernet Internet Module

## Task 1

> [!info]
> Arduino as a web client

This task requires us to setup an arduino as a web client and connect to google (from the library examples)

``````col 
`````col-md 
flexGrow=1
===

````cpp
#include <SPI.h>
#include <Ethernet.h>

byte mac[] = { 0xDE, 0xAD, 0xBE, 0xEF, 0xFE, 0xED };

char server[] = "www.google.com";

// Set the static IP address to use if the DHCP fails to assign
IPAddress ip(192, 168, 0, 177);
IPAddress myDns(192, 168, 0, 1);

EthernetClient client;

unsigned long beginMicros, endMicros;
unsigned long byteCount = 0;
bool printWebData = true;  // set to false for better speed measurement

void setup() {
  Serial.begin(505000);
  while (!Serial) {
    ;
  }

  // start the Ethernet connection:
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
  Serial.print("connecting to ");
  Serial.print(server);
  Serial.println("...");

  // if you get a connection, report back via serial:
  if (client.connect(server, 80)) {
    Serial.print("connected to ");
    Serial.println(client.remoteIP());
    // Make a HTTP request:
    client.println("GET /search?q=arduino HTTP/1.1");
    client.println("Host: www.google.com");
    client.println("Connection: close");
    client.println();
  } else {
    // if you didn't get a connection to the server:
    Serial.println("connection failed");
  }
  beginMicros = micros();
}

void loop() {
  int len = client.available();
  if (len > 0) {
    byte buffer[80];
    if (len > 80) len = 80;
    client.read(buffer, len);
    if (printWebData) {
      Serial.write(buffer, len);
    }
    byteCount = byteCount + len;
  }

  if (!client.connected()) {
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

    // do nothing forevermore:
    while (true) {
      delay(1);
    }
  }
}

````

`````
`````col-md 
flexGrow=1
===

> [!note] 
> Here we can see arduino contacting **dhcp** *server*, getting an assigned an ip address (**10.21.46.68**)
> and then contating **dns** *server*, getting ip of *google.com* (**216.58.212.132**) and connecting to it on *port 80* (http)

![[connected-to-google.png]]

> [!note] 
> After connecting to google and geting the **web page**, we can see the *transfer rate* (11.7Kb/s)

> [!important] 
> In this case *transfer speed is limited* by the **serial communication** line speed (we need to *echo* all received data to serial) and arduino only has *1 core*

![[11.7kb.png]]

> Let's increase the serial speed!

![[22kb.png]]

````col 
```col-md 
flexGrow=1
===

> Even MORE!

``` 
```col-md 
flexGrow=1
===

![[Pasted image 20250304093903.png]]

```
````

![[34.9.png]]

At this point I have increased the communication speed to **5500000**, increasing it even higher *does not increase ethernet speed*, so the bottleneck here is the *shield* or arduinos' *limited processing speed*

````` 
``````

## Task 2

> [!info] 
> Arduino as a web server

This task requires us to setup an arduino as a simple web server (from the library examples)

### Task 2.1

> Basic web server showing button states

`````col 
````col-md 
flexGrow=1
===

```cpp
#include <SPI.h>
#include <Ethernet.h>

byte mac[] = {0xDE, 0xAD, 0xBE, 0xEF, 0xFE, 0x14};
IPAddress ip(10, 21, 46, 68);

EthernetServer server(80);

void setup() {
  // Open serial communications and wait for port to open:
  Serial.begin(505000);
  while (!Serial) {
    ; 
  }
  Serial.println("Ethernet WebServer Example");

  // start the Ethernet connection and the server:
  Ethernet.begin(mac, ip);

  // Check for Ethernet hardware present
  if (Ethernet.hardwareStatus() == EthernetNoHardware) {
    Serial.println("Ethernet shield was not found.  Sorry, can't run without hardware. :(");
    while (true) {
      delay(1);
    }
  }
  if (Ethernet.linkStatus() == LinkOFF) {
    Serial.println("Ethernet cable is not connected.");
  }

  server.begin();
  Serial.print("server is at ");
  Serial.println(Ethernet.localIP());
}


void loop() {
  // listen for incoming clients
  EthernetClient client = server.available();
  if (client) {
    Serial.println("new client");
    boolean currentLineIsBlank = true;
    while (client.connected()) {
      if (client.available()) {
        char c = client.read();
        Serial.write(c);
        
        if (c == '\n' && currentLineIsBlank) {
          client.println("HTTP/1.1 200 OK");
          client.println("Content-Type: text/html");
          client.println("Connection: close");
          client.println("Refresh: 5");
          client.println();
          client.println("<!DOCTYPE HTML>");
          client.println("<html>");
          // output the value of each analog input pin
          for (int analogChannel = 1; analogChannel <= 3; analogChannel++) {
            int sensorReading = analogRead(analogChannel);
            client.print("Button on A");
            client.print(analogChannel);
            client.print(" is ");
            client.print(sensorReading > 500 ? "UP" : "DOWN");
            client.println("<br />");
          }
          client.println("</html>");
          break;
        }
        if (c == '\n') {
          // you're starting a new line
          currentLineIsBlank = true;
        } else if (c != '\r') {
          // you've gotten a character on the current line
          currentLineIsBlank = false;
        }
      }
    }
    // give the web browser time to receive the data
    delay(1);
    // close the connection:
    client.stop();
    Serial.println("client disconnected");
  }
}
```

```` 
````col-md 
flexGrow=1
===

> [!note] 
> This is the output of the original sketch, it *reads analog ports from 1 to 6*

![[A1-A6.png]]

> [!note] 
> This is a modified version which *reads analog ports 1 to 3* and interprets values as button **up**(not pressed) and **down**(pressed)

![[A1-A2-A3.png]]

> [!note] 
> Here we can see what the **client**(browser) sends when we access the arduino via it's **static ip**(10.21.46.68)
> In particular, we are accessing the **root path**(/) using **firefox** on **linux**

![[client-connected.png]]

```` 
`````

### Task 2.2

> Let's modify the code to do something more interesting!

> [!note] 
> Here is my modified code, I changed it to be **line buffered** so I can detect the *path* we are accessing (*ledOn* and *ledOff*). I then added code to detect those path accesses and *turn a LED on and off*, I also added an image

```cpp

......

void loop() {
    EthernetClient client = server.available();
    if (client) {
        Serial.println("\nnew client\n");
        boolean currentLineIsBlank = true;
        String currentLine = "";
        while (client.connected()) {
            if (client.available()) {
                char c = client.read();
                if (c == '\n' && currentLineIsBlank) {
                    // send a standard http response header
                    client.println("HTTP/1.1 200 OK");
                    client.println("Content-Type: text/html");
                    client.println("Connection: close");
                    client.println("Refresh: 5");
                    client.println();
                    client.println("<!DOCTYPE HTML>");
                    client.println("<html>");
                    
                    for (int analogChannel = 1; analogChannel <= 3; analogChannel++) {
                        int sensorReading = analogRead(analogChannel);
                        client.print("Button on A");
                        client.print(analogChannel);
                        client.print(" is ");
                        client.print(sensorReading > 500 ? "UP" : "DOWN");
                        client.println("<br />");
                    }
                    client.println("<script> function ledOn() { fetch('http://" + myIp + "/ledOn'); } </script>");
                    client.println("<script> function ledOff() { fetch('http://" + myIp + "/ledOff'); } </script>");
                    client.println("<button onclick=\"ledOn()\">Launch the nuke</button>");
                    client.println("<button onclick=\"ledOff()\">Un-launch the nuke XD</button>");
                    client.println("<img src='https://static.wikia.nocookie.net/nicos-nextbots-fanmade/images/3/36/Goofy_Cat.png'>");
                    client.println("</html>");
                    break;
                }
                if (c == '\n') {
                    // you're starting a new line
                    currentLineIsBlank = true;
                    Serial.println(currentLine.c_str());
                    if (currentLine.startsWith("GET /ledOn")) {
                        digitalWrite(LED_PIN, HIGH);
                        Serial.println("Turning on led");
                    } else if (currentLine.startsWith("GET /ledOff")) {
                        digitalWrite(LED_PIN, LOW);
                        Serial.println("Turning off led");
                    }
                    currentLine = "";
                } else if (c != '\r') {
                    // you've gotten a character on the current line
                    currentLineIsBlank = false;
                    currentLine += c;
                }
            }
        }
        // give the web browser time to receive the data
        delay(1);
        client.stop();
        Serial.println("\nclient disconnected\n");
    }
}

......

```

![[my-web-page.png]]