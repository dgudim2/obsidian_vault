# Lab 2 - NRF24L01 Radio

## Task 1

> [!info] 
> Scanning the frequency spectrum

This task requires opening the 'scanner' example and observing the result via the serial monitor


`````col 
````col-md 
flexGrow=1
===

```cpp
#include "RF24.h"
#include "printf.h"

//
// Hardware configuration
//

#define CE_PIN 38
#define CSN_PIN 40
// instantiate an object for the nRF24L01 transceiver
RF24 radio(CE_PIN, CSN_PIN);

//
// Channel info
//

const uint8_t num_channels = 126; // 0-125 are supported
uint8_t values[num_channels];    

----------------------------
... 100 more lines ...
----------------------------

        Serial.print(F("."));
        delay(1000);
    }
} // end loop()

void printHeader() {
    // Print the hundreds digits
    for (uint8_t i = 0; i < num_channels; ++i)
        Serial.print(i / 100);
    Serial.println();

    // Print the tens digits
    for (uint8_t i = 0; i < num_channels; ++i)
        Serial.print((i % 100) / 10);
    Serial.println();

    // Print the singles digits
    for (uint8_t i = 0; i < num_channels; ++i)
        Serial.print(i % 10);
    Serial.println();

    // Print the header's divider
    for (uint8_t i = 0; i < num_channels; ++i)
        Serial.print(F("~"));
    Serial.println();
}
```

```` 
````col-md 
flexGrow=1
===

> [!note]
> I am not including the whole code, it's available via the library examples

![[scan.png]]

> [!explanation] 
> We see that channels after 82 are free (no activity) and channels 1,2,3,24,25,26,80 and some other have activity

```` 
`````

## Task 2

> [!info]
> Exchanging sample data between 2 arduinos

This task requires us to setup 2 arduinos, one as a *transmitter*, the other as a *receiver*

> [!note]
> *autoAck* is set to **false** here, for a more reliable communication. If it is set to **true**, acknowledgement from the other (receiving side) are not necessary, this is useful in message broadcast scenarios, in 1-1 communication there will be higher *packet loss* on longer distances

### Transmitter

`````col 
````col-md 
flexGrow=1
===

```cpp
#include "RF24.h"
#include "printf.h"

//CE, CSN. SPI speed
RF24 radio(38, 40, 4000000);                    

uint8_t data = 123; // Message-data
unsigned char address[] = "00000";

void setup() {
  Serial.begin(115200);
  printf_begin();
  
  // Setup and configure rf radio
  radio.begin();
  radio.setAutoAck(false);
  radio.setChannel(112);
  // How many bytes in the message
  radio.setPayloadSize(sizeof(uint8_t));
  // How much to wait, how much to repeat
  radio.setRetries(15, 15);
  radio.openWritingPipe(address);
  radio.printDetails();
}

void send() {
  radio.stopListening();
  while (1) {                                 
    if(radio.write(&data, sizeof(uint8_t))) {
      Serial.println("succeeded");
      Serial.println(data);
    } else {
      Serial.println("failed");
    }
    data++;
    delay(100);
  }
}

void loop() {
  send();
}

```

```` 
````col-md 
flexGrow=1
===

> [!note]
> Here we do some basic configuration to make the radio work as a transmitter and start sending a number repeatedly

![[send.png]]

> [!important] 
> The *channel* is set to **112**, the same channel must be set on the *receiving* side
> Same goes for the *address*

```` 
`````


### Receiver

`````col 
````col-md 
flexGrow=1
===

```cpp
#include "RF24.h"
#include "printf.h"

//CE, CSN. SPI speed
RF24 radio(38, 40, 4000000);                   

uint8_t data = 0;
unsigned char address[] = "00000";

void setup() {
  Serial.begin(115200);
  printf_begin();
  
  // Setup and configure rf radio
  radio.begin();
  radio.setAutoAck(false);
  radio.setChannel(112);
  radio.setPayloadSize(sizeof(uint8_t));                     
  radio.setRetries(15, 15);                                   
  radio.openReadingPipe(1, address);
  radio.printDetails();
}

void listen() {
  radio.startListening();
  while (1) {
    if (radio.available(&address[0])) { 
      radio.read(&data, sizeof(uint8_t));
      Serial.print("I received:");
      Serial.println(data);
    }
  }
}

void loop() {
  listen();
}

```

```` 
````col-md 
flexGrow=1
===

> [!note]
> Here we do some basic configuration to make the radio work as a receiver and start listening for data

![[receive.png]]

> [!note] 
> Looks like we are successfully receiving the messages!

```` 
`````


## Task 3 + 4

> [!info] 
> Controlling leds of another arduino

This task requires us to modify the example bi-directional communication example to support 4 leds

`````col 
````col-md 
flexGrow=1
===

```cpp
#include "RF24.h"
#include "printf.h"

#define M1 2
#define M2 3
#define M3 43
#define M4 44
#define LED1 A12
#define LED2 A13
#define LED3 A14
#define LED4 A15

// CE, CSN. SPI speed
RF24 radio(38, 40, 4000000); // 4000000 = 4MHz

uint8_t data = 123; // Message-data
unsigned char address0[] = "00000";
unsigned char address1[] = "00000";

void setup() {
    pinMode(M1, INPUT_PULLUP);
    pinMode(M2, INPUT_PULLUP);
    pinMode(M3, INPUT_PULLUP);
    pinMode(M4, INPUT_PULLUP);
    pinMode(LED1, OUTPUT);
    pinMode(LED2, OUTPUT);
    pinMode(LED3, OUTPUT);
    pinMode(LED4, OUTPUT);

    Serial.begin(2000000);
    printf_begin();

    // Setup and configure rf radio
    radio.begin();
    radio.setAutoAck(true);
    radio.setChannel(17);
    radio.setPayloadSize(sizeof(uint8_t)); 
    radio.setRetries(15, 15);              
    radio.openReadingPipe(1, address1);
    radio.openWritingPipe(address0);
    radio.setPALevel(RF24_PA_MIN);
    radio.startListening();
    radio.setAutoAck(true);
    radio.printDetails();
}

void listen(void) {
    // if (radio.available(&address1[0])) {
    if (radio.available()) {
        radio.read(&data, sizeof(uint8_t));
        Serial.write(data);
        if(data == 82) {
            digitalWrite(LED1, !digitalRead(LED1));
        }
        if(data == 76) {
            digitalWrite(LED2, !digitalRead(LED2));
        }
        if(data == 77) {
            digitalWrite(LED3, !digitalRead(LED3));
        }
        if(data == 78) {
            digitalWrite(LED4, !digitalRead(LED4));
        }
    }
}

void send(uint8_t d) {
    radio.stopListening();
    if (radio.write(&d, sizeof(uint8_t))) { // true if transferred successfully
        Serial.println("Data was sent successfully");
    } else {
        Serial.println("Data Transfer Failed");
    }
    delay(500);
    radio.startListening();
}

void loop() {
    if (digitalRead(M1) == 0) { // Pressed M1
        send('R');
    }
    if (digitalRead(M2) == 0) { // Pressed M2
        send('L');
    }
    if (digitalRead(M3) == 0) { // Pressed M3
        send('M');
    }
    if (digitalRead(M4) == 0) { // Pressed M4
        send('N');
    }
    listen();
}
```

```` 
````col-md 
flexGrow=1
===

> [!note] 
> Here we *start listening to messages by default*, when a button is pressed we *send a particular byte* and when that byte is received (from another arduino with the same firmware) we *light up corresponding led*
> 'R' = 82
> 'L' = 76
> 'M' = 77
> 'N' = 78

![[receive_leds.png]]
![[real-world.jpg]]

> [!seealso] 
> If we add this before `listen()` we will get a crude chat app!
> ```cpp
> if (Serial.available() > 0) {
>    send(Serial.read());
> }
> ```

![[chat.png]]

```` 
`````
