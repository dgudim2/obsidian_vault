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

> **RAM** - **R**andom **A**ccess **M**[[Memory|emory]]

- Can be [[#Volatile memory|volatile]] (**VRAM**)
- Can be [[#Nonvolatile memory|nonvolatile]] (**NVRAM**)
- Typical for most applications

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

- Main [[Memory|memory]]
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

### Density

> [!definition] 
> Refers to *memory* cells *per square* area of silicon

- Usually stated as number of [[Data representation#Bit (Binary digit)|bits]] on standard size chip
- @ Note: *higher density* chip generates *more heat*

### Latency

> [!definition] 
> **Time** that elapses between the *start* of an operation and the *completion* of the operation

- May depend on previous operations

> [!seealso] 
> [[#Memory cycle time]]

#### Separation of read and write latency

- In many memory technologies
	- The time required to *store exceeds* the time required to *fetch*
	- Difference can be **dramatic**
- @ Consequence: any measure of memory performance must give *two values*
	- Performance of *read*
	- Performance of *write*

### Memory cycle time

- More accurate measure than [[#Latency|latency]]
- Two separate measures
	- **Read** cycle time (t**RC**)
	- **Write** cycle time (t**WC**)

### Memory size

> Memory sizes are expressed as *powers of* **2**, not *powers of* **10**

- **Kilobyte** defined to be $2^{10}$ [[Data representation#Byte|bytes]]
- **Megabyte** defined to be $2^{20}$ [[Data representation#Byte|bytes]]
- **Gigabyte** defined to be $2^{30}$ [[Data representation#Byte|bytes]]
- **Terabyte** defined to be $2^{40}$ [[Data representation#Byte|bytes]]

#### Measures of network speed

> Speeds of *data networks* and other [[IO#I/O Devices|I/O devices]] are usually expressed in *powers of* **10**

- & Example: a **Gigabit** Ethernet operates at $10^9$ [[Data representation#Bit (Binary digit)|bits]] per second
- ~ Programmer must accommodate **differences** between measures for *storage* and *transmission*

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

### Parallel memory controller

- A **memory controller** can use a [[IO#Parallel interface|parallel interface]] ([[IO#Buses|bus]])
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
- Determined by [[IO#Buses|bus]] between *computer* and *controller*
- Important to programmers

> [!example] 
> - Memory transfer sizes
> 	- 16 [[Data representation#Bit (Binary digit)|bits]]
> 	- 32 [[Data representation#Bit (Binary digit)|bits]]
> 	- 64 [[Data representation#Bit (Binary digit)|bits]]

## Words

> [!definition] 
> 1. [[Data representation#Bit (Binary digit)|Bits]] of [[#Physical memory|physical memory]] are divided into *blocks* of **N** [[Data representation#Bit (Binary digit)|bits]] each called **words**
> 2. **N** is known as the **width** of a *word* or the **word size**

- **N** is determined by [[IO#Bus width|bus width]]
- Computer is often characterized by its **word size** (e.g., 64-[[Data representation#Bit (Binary digit)|bit]] computer)

### Memory address

- Each [[#Words|word]] of [[Memory|memory]] is assigned a unique number, known as a *physical memory address*
- [[#Physical memory]] is organized as an array of [[#Words|words]]
- Underlying hardware applies **read** or **write** to *entire word*

`````col 
````col-md 
flexGrow=1
===

```dynamic-svg
---
invert-shade
width:100%
---
[[memory-address.svg]]
```

```` 
````col-md 
flexGrow=1
===

> - Physical addressing on a computer where a [[#Words|word]] is 32 [[Data representation#Bit (Binary digit)|bits]]
> - We think of the memory as an *array* of [[#Words|words]]


```` 
`````

### Choosing word size

- [[#Words|Word]] size represents a fundamental **trade-off**
- *Larger* [[#Words|word]] size
	- Results in *higher* **performance**
	- Requires more *parallel wires* and circuits
	- Has *higher* **cost** and **power** *consumption*

> [!note] 
> 

Architect usually designs *all data paths* in a computer to use **one size**
- [[#Words|Word]] in [[#Physical memory|physical memory]]
- [[Data representation#Integer representation in binary|Integers]] and [[Processor#General-purpose registers|general-purpose registers]]
- [[Data representation#Floating point|Floating point]]  numbers and [[Processor#Floating point registers|floating-point registers]] 

## Byte addressing and translation

- [[Data representation#Byte|Byte]] addressing
	- **View** of [[Memory|memory]] presented to [[Processor|processor]]
	- Each [[Data representation#Byte|byte]] of [[Memory|memory]] assigned an *address*
	- $ Convenient for programmers
- @ However... the underlying memory uses [[#Words|word]] addressing
* [[#Memory controller (organization)|Memory controller]]
	- Provides **translation**
	- Allows programmers to use [[Data representation#Byte|byte]] *addresses* (convenient)
	- Allows [[#Physical memory|physical memory]] to use [[#Words|word]] *addresses* (efficient)

> [!example] 
> 
> 
> > Example of a [[Data representation#Byte|byte]] *address* assigned to each [[Data representation#Byte|byte]] of memory even though the underlying hardware uses [[#Words|word]] *addressing* and 32-[[Data representation#Bit (Binary digit)|bit]] [[#Words|word]] size.
> > 
> `````col 
> ````col-md 
> flexGrow=1.8
> ===
> 
> ```dynamic-svg
> ---
> invert-shade
> width:100%
> ---
> [[byte-word-addressing.svg]]
> ```
> 
> ```` 
> ````col-md 
> flexGrow=1
> ===
> 
> - Assume [[#Physical memory|physical memory]] is organized into 32-[[Data representation#Bit (Binary digit)|bit]] [[#Words|words]]
> - Programmer views [[Memory|memory]] as an array of [[Data representation#Byte|bytes]]
> - We think of each [[Data representation#Byte|byte]] has having an *address* **0** through **N-1**
> - Each physical [[#Words|word]] corresponds to 4 [[Data representation#Byte|byte]] *addresses*
> 
> ```` 
> `````
> 

### Translation examples

- Let **N** be the number of [[Data representation#Byte|bytes]] per [[#Words|word]], **B** - our [[Data representation#Byte|byte]] address
- The *physical address* of the [[#Words|word]] containing the [[Data representation#Byte|byte]] is $$W=\frac{B}{N}$$
- The [[Data representation#Byte|byte]] offset within the word is $$O=B\ mod\ N$$

#### Efficient translation

- Think *binary* and choose [[#Words|word]] size **N** to be a *power of* **2**
	- **Avoids** arithmetic calculations, especially *division* and *remainder*
	- [[#Words|Word]] address computed by extracting *high-order* bits
	- **Offset** computed by extracting *low-order* bits

> An example of a **mapping** from [[Data representation#Byte|byte]] address **17** to [[#Words|word]] address **4** and offset **1**
> > Using a power of two for the number of [[Data representation#Byte|bytes]] per [[#Words|word]] avoids arithmetic calculations

```dynamic-svg
---
invert-shade
width:100%
---
[[address-translation.svg]]
```

### Byte alignment

> [!definition] 
> Refers to storing **multibyte** values (e.g., [[Data representation#Integer representation in binary|integers]]) in [[Memory|memory]]

- Two designs have been used
	- Access must correspond to [[#Words|word]] boundary in underlying [[#Physical memory|physical memory]]
	- Access can be *unaligned*, [[#Memory controller (organization)|memory controller]] handles details, but [[#Fetch-store paradigm|fetch and store]] operations are slower (common)

- @ Consequences for programmers
	- Performance may be improved *by aligning* [[Data representation#Integer representation in binary|integers]]
	- Some [[IO#I/O Devices|I/O devices]] require buffers to be aligned

### C and memory 

> C has a heritage of both [[Data representation#Byte|byte]] and [[#Words|word]] addressing

- & Example of [[Data representation#Byte|byte]] *pointer* declaration
	```c
	char *iptr;
	```

- & Example of [[#Words|word]] *pointer* declaration
	```c
	int *iptr;
	```
	If [[Data representation#Integer representation in binary|integer]] size is **4** [[Data representation#Byte|bytes]], *iptr++* increments by **4**

## Memory size and address space

- Size of **address** limits *maximum memory*
- & Example: 32-[[Data representation#Bit (Binary digit)|bit]] address can represent $2^{32}=4,294,967,296$ unique addresses
	- Known as **address space**
- @ Note: [[#Words|word]] addressing allows larger memory than [[#Byte addressing and translation|byte addressing]], but is rarely used because it is *difficult to program*

> [!seealso] 
> [[#Memory size]]

## Increasing memory performance

- Two major techniques
	- [[#Memory banks]]
	- [[#Interleaving]]
- Both employ **parallel hardware**

### Memory banks

> [!definition] 
> **Modular** approach to constructing *large memory*

- Basic [[Memory|memory]] module is *replicated* multiple times
- Selection circuitry chooses **bank**

- @ Basic idea
	- Use **high-order** [[Data representation#Bit (Binary digit)|bits]] of address to *select a* **bank**
	- Use **low-order** [[Data representation#Bit (Binary digit)|bits]] to *select a* [[#Words|word]] within a **bank**
	- **Hardware** for each bank *is identical*
	- ~ Parallel access - one **bank** can *reset* while *another* is being *used*



### Interleaving

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

