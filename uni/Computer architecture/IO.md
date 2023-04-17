# I/O Devices

> Third *major* component of a computer system

`````col
````col-md
flexGrow=1
===

- ? Types:
	- Keyboards and mice
	- Hard disks
	- Solid state disks
	- Printers
	- Cameras
	- Audio speakers
	- Sensors
	- ...


````
````col-md
flexGrow=1
===

- ? Properties:
	- Operates *independent* of processor
	- May have *separate* power supply
	- Digital signals used for control

- $ Example: panel lights

````
````col-md
flexGrow=1
===

```dot 
---
preset:math-graph
---
digraph neato { 

bgcolor="transparent" 
layout="neato"

node [shape = Mrecord, 
      style = filled, 
      width=0.3, 
      height=0.3, 
      color=green, 
      fillcolor = white] 

a [label="<f0> Processor |<f1> Controller", pos="0,0!"];
b [label="<f0> Device |<f1> Controller ", pos="0,-1.3!"];

edge [color = lightgrey] 
 
a:f1 -> b:f1 
b:f1 -> a:f1 [label="external\nconnection", fontcolor=white]

} 
```
- Controller is placed at each end of *physical connection*
- Allows *arbitrary* voltages and signals to be used

````
`````

## Types of interfaces

### Serial interface

- Single signal wire (and ground)
- One [[Data representation#Bit (Binary digit)|bit]] at a time
- $ Less complex hardware with **lower cost**

- & Examples:
	- [SPI](https://en.wikipedia.org/wiki/Serial_Peripheral_Interface)
	- [I2C](https://en.wikipedia.org/wiki/I%C2%B2C)
	- [1-Wire](https://en.wikipedia.org/wiki/PCI_Express)

### Parallel interface

- More than a **single** wire, each one caries one [[Data representation#Bit (Binary digit)|bit]] at a time
- Number of wires - **width** of the interface
- ! More complex hardware with **higher cost**
- Faster than [[#Serial interface|serial interface]]
- ! Limitation: at *high data rates*, *close parallel* wires have potential for *interference*

- & Examples:
	- [Memory bus](https://en.wikipedia.org/wiki/Bus_(computing)#Memory_bus)
	- [SCSI](https://en.wikipedia.org/wiki/Parallel_SCSI)
	- [PCI](https://en.wikipedia.org/wiki/Peripheral_Component_Interconnect)

## Duplex technology

### Full-duplex

- Simulation, **bidirectional** transfer
- & Example: disk drive supports simultaneous *read* and *write* operations

### Half-duplex

- Transfer in **one direction** at a time
- Interfaces must *negotiate* access before transmitting
- & Example: processor can *read* or *write* to disk, but can only perform one operation at a time




