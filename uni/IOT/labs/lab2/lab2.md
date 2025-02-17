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
> *autoAck* is set to **false** here, for a more reliable communication

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
> Same goes for the *address0* and *address1*

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

```` 
`````


