# Key aspects and characteristics of memory

- **Technology**
	- The type of the underlying *hardware*
	- Choice determines cost, persistence, performance
	- Many variants are available
- **Organization**
	- How underlying hardware is used to build memory system (i.e., [[Data representation#Byte|bytes]] , words, etc.)
	- Directly visible to programmer
- **Characteristics**
	- [[#Volatile memory|Volatile]] or [[#Nonvolatile memory|nonvolatile]]
	- [[#Random access memory|Random]] or [[#Sequential access memory|sequential]] access
	- *Read-write* or *read-only*
	- [[#Primary memory|Primary]] or [[#Secondary memory|secondary]]

## Volatile memory

- <u>Contents disappear</u> when power is removed
- **Fastest** access times
- $ **Least expensive**
- Some *embedded* systems use a *battery* to maintain memory contents

## Nonvolatile memory

- <u>Contents remain</u> without power
- More *expensive* than volatile [[Memory|memory]]
- May have *slower* access times

### ROM

> **R**ead **O**nly **M**[[Memory|emory]]

- Values can be *read*, but *not changed*
- $ Useful for **firmware**

### PROM 

> **P**rogrammable **R**ead **O**nly **M**[[Memory|emory]]

- Contents can be *altered*, but doing so is *time-consuming*
- Change may involve *removal* from a circuit, exposure to *ultraviolet* light

### Flash

- Contents can be *altered easily*
- Used in solid state disks (SSDs), digital cameras, phones, etc.

## Random access memory

- Typical for most applications

> **RAM** - **R**andom **A**ccess **M**[[Memory|emory]]
> - Can be [[#Volatile memory|volatile]] (**VRAM**)
> - Can be [[#Nonvolatile memory|nonvolatile]] (**NVRAM**)

## Sequential access memory

- Known as a **FIFO** (**F**irst-**I**n-**F**irst-**O**ut)
- Typically associated with *streaming* applications
- Requires *special* purpose *hardware*

## Memory hierarchy

- Key concept to [[Memory|memory]] *design*
- Extend the [[#Primary memory|primary]]/[[#Secondary memory|secondary]] trade-off to multiple levels

- @ Basic idea
	- Can obtain better *performance* at lower *cost* by using a set of memories
	- The key is choosing memory *sizes* and *speeds* carefully
		- A *small* memory has *highest* performance
		- A slightly *larger* amount of memory has somewhat *lower* performance
		- The *largest* memory has the *lowest* performance

> [!example] 
> Dozens of general-purpose [[Processor#Registers|registers]] 
> A dozen **gigabytes** of *main* memory
> Several **terabytes** of solid state disk ([[#Flash]])

### Primary memory

- $ *Highest* speed
- ! *Most expensive*, and therefore the *smallest*
- Typically *solid* state technology

### Secondary memory

> Nowadays the distinction between [[#Primary memory|primary]] and [[#Secondary memory|secondary]] is blurring

- *Lower* speed
- *Less expensive*, and therefore can be larger
- Traditionally used magnetic media and electro-mechanical drive mechanisms
	- Today moving to solid state ([[#Flash]])

### Two paradigms

> [!seealso] 
> [[Processor#Architechture]]

#### Harvard architecture

- Two separate **memories** known as
	- *Instruction* store
	- *Data* store
- % One memory holds programs and the other holds data
- Used on *early computers* and some *embedded* systems

- $ Advantages
	- Allows separate caches
	- Permits memory technology to be *optimized* for access patterns
	- Instructions: [[#Sequential access memory|sequential access]]
	- Data: [[#Random access memory|random access]]

- ! Disadvantage
	- Must *choose* a size for each *memory* when computer is designed

#### Von Neumann architecture

- A *single memory* holds both **programs** and **data**
- Used on most general-purpose computers

## Fetch-store paradigm

> [[Memory]] systems use this paradigm

> [!seealso] 
> [[IO#Bus operations]]

- Access paradigm used by [[Memory|memory]]
- Hardware only supports *two operations*
	- **Fetch** (read) a value from a specified location
	- **Store** (write) a value into a specified location

--- 
<br>

# Physical memory

## Computer memory

- Main memory
	- Designed to permit arbitrary reference patterns
	- Known by the term **RAM** ([[#Random access memory]])
- Usually [[#Volatile memory|volatile]]
- Two basic technologies available
	- **S**tatic [[#Random access memory|RAM]] ([[#SRAM]])
	- **D**ynamic [[#Random access memory|RAM]] ([[#DRAM]])

### SRAM

> **S**tatic [[#Random access memory|RAM]]

- $ Easiest to understand
- Basic elements built from a *latch*
- When enable is **HIGH**, output is *same* as input
- Once enable line goes **LOW**, output is the *last* input *value*

`````col 
````col-md 
flexGrow=1
===

```dynamic-svg
---
invert-shade
width:100%
---
[[simple-sram.svg]]
```

```` 
````col-md 
flexGrow=1
===

> - Ilustration of a *miniature* **S**tatic **RAM** circuit that stores one data [[Data representation#Bit (Binary digit)|bit]]
> - The circuit contains multiple transistors


```` 
`````

- $ Advantages
	- High *speed*
	- Access circuitry is *straightforward*
- ! Disadvantages
	- Higher *power consumption*
	- *Heat* generation
	- High *cost*

### DRAM

> **D**ynamic [[#Random access memory|RAM]]

- Alternative to [[#SRAM]]
- Consumes *less power*
- Analogous to a **capacitor** (i.e., stores electrical charge)

- ! Disadvantages
	- When left for a long time, a bit in DRAM changes from **1** to **0**
	- Discharge time can be *less than a second*

#### Making DRAM work

> Cannot leave bits too long or they change
> Additional hardware known as a *refresh circuit* is used

- Refresh circuit
	+ **Steps** through each location **i** of **DRAM**
	+ **Reads** the value from location **i**
	+ Writes same value back into location i (i.e., recharges the memory)
	- Runs in the *background* at *all times*

`````col 
````col-md 
flexGrow=1
===

```dynamic-svg
---
invert-shade
width:100%
---
[[dram-refresh.svg]]
```

```` 
````col-md 
flexGrow=1
===

- *Much more complex* than the figure implies
- Refresh *must not interfere* with normal **read** and **write** operations
	- Performance must not suffer

> - Illustration of a bit in **DRAM**. 
> - An external *refresh circuit* must periodically **read** the data value and write it back again, or the *charge will dissipate* and the value will be lost.


```` 
`````

## Measures of memory

#### Density

- Refers to *memory* cells *per square* area of silicon
- Usually stated as number of [[Data representation#Bit (Binary digit)|bits]] on standard size chip
- @ Note: *higher density* chip generates *more heat*

#### Latency

- **Time** that elapses between the *start* of an operation and the *completion* of the operation
- May depend on previous operations

##### Separation of read and write latency

- In many memory technologies
	- The time required to *store exceeds* the time required to *fetch*
	- Difference can be **dramatic**
- @ Consequence: any measure of memory performance must give *two values*
	- Performance of *read*
	- Performance of *write*

#### Memory cycle time

- More accurate measure than [[#Latency|latency]]
- Two separate measures
	- **Read** cycle time (t**RC**)
	- **Write** cycle time (t**WC**)

## Memory controller (organization)

- Hardware unit called a **memory controller** connects a [[Processor|processor]] to a physical [[Memory|memory]] module
- @ Main point: because all memory *requests go through the controller*, the interface a processor "sees" *can differ* from the underlying hardware organization

```dynamic-svg
---
invert-shade
width:100%
---
[[memory-controller.svg]]
```

> [!seealso] 
> [[IO#Memory bus]]

### Steps taken to honor a memory request

* [[Processor]]
	- **Presents** *request* to controller
	* **Waits** for response
+ **Controller**
	+ **Translates** request *into signals* for *physical memory* chips
	- **Returns** answer to processor as quickly as possible
	- **Sends** signals [[#Consequence of memory reset|to reset]] physical memory for *next request*

#### Consequence of memory reset

+ Means next memory operation *may be delayed*

+ @ Conclusion
	* [[#Latency]] of a single operation is an *insufficient measure* of performance
	+ Must measure the **time** required for *successive operations* ([[#Memory cycle time]])

### Parallel memory controller (organization)

- A **memory controller** can use a parallel interface ([[IO#Buses|bus]])
- This allows **N** [[Data representation#Bit (Binary digit)|bits]] of data to be transferred *simultaneously*

```dynamic-svg
---
invert-shade
width:100%
---
[[memory-controller-bus.svg]]
```

#### Memory transfer size

- *Amount of memory* that can be transferred to computer **simultaneously**
- Determined by [[IO#Buses|bus]] between computer and controller
- Important to programmers

> [!example] 
> - Memory transfer sizes
> 	- 16 [[Data representation#Bit (Binary digit)|bits]]
> 	- 32 [[Data representation#Bit (Binary digit)|bits]]
> 	- 64 [[Data representation#Bit (Binary digit)|bits]]

## Synchronous memory techniques

- **Both** memory and processor use a *clock*

- *Synchronized* memory systems ensure *two clocks coincide*
- Allows *higher* memory *speeds*

- Technologies
	- **S**ynchronous [[#SRAM]] (**SSRAM**) 
	- **S**ynchronous [[#DRAM]] (**SDRAM**)
- @ Note: the [[#Random access memory|RAM]] in most computers is **SDRAM**

## Multiple data rate memory techniques

+ Goals
	+ *Improve* memory *performance*
	* *Avoid mismatch* between **CPU** speed and memory *speed*
+ @ Technique: memory hardware *runs at a multiple* of the **CPU** [[Processor#Clock rate|clock rate]]
	- Available for both **SRAM** and [[#DRAM]]
	+ Double Data Rate **SDRAM** (DDR-**S**[[#DRAM]])
	* Quad Data Rate **SRAM** (QDR-[[#SRAM]])

## Other memory techniques and technologies

| Technology | Description                        |
| ---------- | ---------------------------------- |
|            |                                    |
| FCRAM      | Fast Cycle **RAM**                 |
| FPM-DRAM   | Fast Page Mode Dynamic **RAM**     |
| ZBT-SRAM   | Zero Bus Turnaround Static **RAM** |
| RDRAM      | Rambus Dynamic **RAM**             |
| RLDRAM     | Reduced Latency Dynamic **RAM**    |

