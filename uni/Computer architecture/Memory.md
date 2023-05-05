# Key aspects and characteristics of memory

- **Technology**
	- The type of the underlying *hardware*
	- Choice determines *cost*, *persistence*, *performance* ([[#Measures of memory]])
	- Many variants are available
- [[#Memory controller (organization)|Organization]]
	- How underlying hardware is used to build memory system (i.e., [[Data representation#Byte|bytes]], [[#Words|words]], etc.)
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
- Used in solid state [[#I/O Devices|disks]] (SSDs), digital cameras, phones, etc.

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

- *Key concept* to [[Memory|memory]] *design*
- Extend the [[#Primary memory|primary]]/[[#Secondary memory|secondary]] trade-off to multiple levels

- @ Basic idea
	- Can obtain better *performance* at lower *cost* by using a set of memories
	- The key is choosing memory *sizes* and *speeds* carefully
		- A *small* memory has *highest* performance
		- A slightly *larger* amount of memory has somewhat *lower* performance
		- The *largest* memory has the *lowest* performance

> [!example] 
> Dozens of general-purpose [[Processor#Registers|registers]] 
> A dozen [[#Memory size|gigabyte]] of *main* memory
> Several [[#Memory size|terabytes]] of solid state [[#I/O Devices|disk]] ([[#Flash]])

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
	1. *Instruction* store
	2. *Data* store
- % One memory holds programs and the other holds data
- Used on *early computers* and some *embedded* systems

- $ Advantages
	- [[#Instruction and data caches|Allows separate caches]]
	- Permits [[Memory|memory]] technology to be *optimized* for access patterns
	- Instructions: [[#Sequential access memory|sequential access]]
	- Data: [[#Random access memory|random access]]

- ! Disadvantage
	- Must *choose* a size for each *memory* when computer is designed

#### Von Neumann architecture

- A *single memory* holds both **programs** and **data**
- Used on most **general-purpose** computers

## Fetch-store paradigm

> [[Memory]] systems use this paradigm

> [!seealso] 
> [[IO#Bus operations]]

- Access paradigm used by [[Memory|memory]]
- Hardware only supports *two operations*
	1. **Fetch** (read) a value from a specified location
	2. **Store** (write) a value into a specified location

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

- In many [[Memory|memory]] technologies
	- The time required to *store exceeds* the time required to *fetch*
	- Difference can be **dramatic**
- @ Consequence: any measure of memory performance must give *two values*
	- Performance of *read*
	- Performance of *write*

### Memory cycle time

- More accurate measure than [[#Latency|latency]]
- Two separate measures
	1. **Read** cycle time (t**RC**)
	2. **Write** cycle time (t**WC**)

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

--- 
<br>

# Physical memory

## Computer memory

- Main [[Memory|memory]]
	- Designed to permit arbitrary reference patterns
	- Known by the term **RAM** ([[#Random access memory]])
- Usually [[#Volatile memory|volatile]]
- Two basic technologies available
	1. **S**tatic [[#Random access memory|RAM]] ([[#SRAM]])
	2. **D**ynamic [[#Random access memory|RAM]] ([[#DRAM]])

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

> Cannot leave [[Data representation#Bit (Binary digit)|bits]] too long or they change
> **Additional hardware** known as a *refresh circuit* is used

- Refresh circuit
	- Runs in the *background* at *all times*
	1. **Steps** through each location **i** of **DRAM**
	2. **Reads** the value from location **i**
	3. Writes same value back into location i (i.e., recharges the memory)

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

## Memory controller (organization)

- Hardware unit called a **memory controller** connects a [[Processor|processor]] to a physical [[Memory|memory]] module
- @ Main point: because all memory *requests go through the controller*, the interface a [[Processor|processor]] "sees" *can differ* from the underlying hardware organization

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

- [[Processor]]
	- **Presents** *request* to controller
	- **Waits** for response
- **Controller**
	- **Translates** request *into signals* for *physical memory* chips
	- **Returns** answer to [[Processor|processor]] as quickly as possible
	- **Sends** signals [[#Consequence of memory reset|to reset]] physical memory for *next request*

#### Consequence of memory reset

- Means next memory operation *may be delayed*

- @ Conclusion
	- [[#Latency]] of a single operation is an *insufficient measure* of performance
	- Must measure the **time** required for *successive operations* ([[#Memory cycle time]])

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

- Each [[#Words|word]] of [[Memory|memory]] is assigned a unique number, known as a *[[#Physical memory|physical memory]] address*
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
	- $ Results in *higher* **performance**
	- Requires more *parallel wires* and circuits
	- ! Has *higher* **cost** and **power** *consumption*

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
> > Example of a [[Data representation#Byte|byte]] *address* assigned to each [[Data representation#Byte|byte]] of memory even though the underlying hardware uses [[#Words|word]] *addressing* and 32-[[Data representation#Bit (Binary digit)|bit]] [[#Words|word]] size
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
- The [[Data representation#Byte|byte]] offset within the [[#Words|word]] is $$O=B\ mod\ N$$

#### Efficient address translation

- Think *binary* and choose [[#Words|word]] size **N** to be a *power of* **2**
	- **Avoids** arithmetic calculations, especially *division* and [[Divisors, multiples, primes#Division with remainder|remainder]]
	- [[#Words|Word]] address computed by extracting *high-order* [[Data representation#Bit (Binary digit)|bits]]
	- **Offset** computed by extracting *low-order* [[Data representation#Bit (Binary digit)|bits]]

> [!example] 
> 
> An example of a **mapping** from [[Data representation#Byte|byte]] address **17** to [[#Words|word]] address **4** and *offset* **1**
> > Using a power of two for the number of [[Data representation#Byte|bytes]] per [[#Words|word]] avoids arithmetic calculations
> 
> ```dynamic-svg
> ---
> invert-shade
> width:100%
> ---
> [[address-translation.svg]]
> ```

### Byte alignment

> [!definition] 
> Refers to storing **multibyte** values (e.g., [[Data representation#Integer representation in binary|integers]]) in [[Memory|memory]]

- Two designs have been used
	1. Access must correspond to [[#Words|word]] boundary in underlying [[#Physical memory|physical memory]]
	2. Access can be *unaligned*, [[#Memory controller (organization)|memory controller]] handles details, but [[#Fetch-store paradigm|fetch and store]] operations are slower (common)

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
- @ Note: [[#Words|word]] addressing allows larger memory than [[#Byte addressing and translation|byte addressing]], but is *rarely used* because it is *difficult to program*

> [!seealso] 
> [[#Memory size]]

## Increasing memory performance

- Two major techniques
	1. [[#Memory banks]]
	2. [[#Interleaving]]
- Both employ **parallel hardware**

### Memory banks

> [!definition] 
> > **Modular** approach to constructing *large memory*
> - Basic [[Memory|memory]] module is *replicated* multiple times
> - Selection circuitry chooses **bank**

`````col 
````col-md 
flexGrow=1
===

```dynamic-svg
---
invert-shade
width:100%
---
[[memory-banks.svg]]
```

```` 
````col-md 
flexGrow=1
===

- @ Basic idea
	- Use **high-order** [[Data representation#Bit (Binary digit)|bits]] of address to *select a* **bank**
	- Use **low-order** [[Data representation#Bit (Binary digit)|bits]] to *select a* [[#Words|word]] within a **bank**
	- **Hardware** for each bank *is identical*
	- ~ Parallel access - one **bank** can *reset* while *another* is being *used*

```` 
`````

> [!seealso] 
> [[#Modules]]

#### Approaches

- $ Transparent
	- **Programmer** is *not concerned* with [[#Memory banks|banks]]
	- **Hardware** *automatically* finds and *exploits* parallelism
- $ Opaque
	- **Programmer** *knows* about [[#Memory banks|banks]]
	- To optimize performance, **programmer** *must place* items that will be accessed *sequentially* in separate [[#Memory banks|banks]]

### Interleaving

> [!definition] 
> > Related to [[#Memory banks|memory banks]], transparent to programmer
> 
> **Hardware** places *consecutive* [[#Words|words]] (or consecutive [[Data representation#Byte|bytes]]) in separate [[#Physical memory|physical memories]]

- @ Technique: use *low-order* bits of address to choose module
	- Known as **N**-*way* **interleaving**, where **N** is number of [[#Physical memory|physical memories]]

`````col 
````col-md 
flexGrow=1.8
===

```dynamic-svg
---
invert-shade
width:100%
---
[[4-way-interleaving.svg]]
```

```` 
````col-md 
flexGrow=1
===

> Ilustration of **4**-way **interleaving** that shows how *successive* [[#Words|words]] of memory are placed into *memory modules* to optimize performance


```` 
`````

## Content-addressable memory (CAM)

> - Blends two key ideas
> 	1. [[Memory]] **technology**
> 	2. [[#Memory controller (organization)|Memory organization]]
> - ~ Includes *parallel hardware* for *high-speed search*

> [!definition] 
> 
> > Think of **CAM** as a *two-dimensional* array of special-purpose *hardware cells*
> - A **row** in the array is called a **slot**
> 
> - The hardware cells
> 	- Can answer the question: "Is **X** stored in any *row* of the **CAM**?"
> 	- *Operate in parallel* to make search fast
> - ~ **Query** is known as a **key**

`````col 
````col-md 
flexGrow=2
===

```dynamic-svg
---
invert-shade
width:100%
---
[[CAM.svg]]
```

```` 
````col-md 
flexGrow=1
===

> - Illustration of a Content Addressable Memory (**CAM**)
> - **CAM** provides both a [[Memory|memory]] **technology** and [[#Memory controller (organization)|memory organization]]

```` 
`````

### CAM lookup

- [[#Content-addressable memory (CAM)|CAM]] presented with **key** for *lookup*
- *Hardware* cells test whether *key is present*
	- **Search** operation performed *in parallel* on *all slots* **simultaneously**
	- **Result** is *index of slot* where value found
- @ Note: parallel search hardware makes [[#Content-addressable memory (CAM)|CAM]] *expensive*

### Ternary CAM (TCAM)

- Variation of [[#Content-addressable memory (CAM)|CAM]] that adds *partial match* searching
- Each [[Data representation#Bit (Binary digit)|bit]] in slot can have one of **3** possible values
	- Zero
	- One
	- Don't care (*ignores* and reports *match*)
- **TCAM** can either report
	- **First** match
	- **All** matches ([[Data representation#Bit (Binary digit)|bit]] vector)

## Synchronous memory techniques

- **Both** [[Memory|memory]] and [[Processor|processor]] use a *clock*
- *Synchronized* memory systems ensure *two clocks coincide*
- Allows *higher* memory *speeds*
- Technologies
	- **S**ynchronous [[#SRAM]] (**SSRAM**) 
	- **S**ynchronous [[#DRAM]] (**SDRAM**)
- @ Note: the [[#Random access memory|RAM]] in most computers is **SDRAM**

## Multiple data rate memory techniques

- Goals
	- *Improve* memory *performance*
	- *Avoid mismatch* between **CPU** speed and memory *speed*
- @ Technique: memory hardware *runs at a multiple* of the **CPU** [[Processor#Clock rate|clock rate]]
	- Available for both **SRAM** and [[#DRAM]]
	- Double Data Rate **SDRAM** (DDR-**S**[[#DRAM]])
	- Quad Data Rate **SRAM** (QDR-[[#SRAM]])

## Other memory techniques and technologies

| Technology | Description                        |
| ---------- | ---------------------------------- |
|            |                                    |
| FCRAM      | **F**ast **C**ycle **RAM**                 |
| FPM-DRAM   | **F**ast **P**age **M**ode **D**ynamic **RAM**     |
| ZBT-SRAM   | **Z**ero **B**us **T**urnaround Static **RAM** |
| RDRAM      | **R**ambus **D**ynamic **RAM**             |
| RLDRAM     | **R**educed **L**atency **D**ynamic **RAM**    |

--- 
<br>

# Cache

> [!definition] 
> 
> - Acts as an **intermediary** 
> 	- Located *between* source of *requests* and source of *replies*
> 	- **Copy** of selected items kept in local storage
> 	- **Cache** answers requests from *local copy when possible*
> - Contains *temporary* local storage

> Conceptual organization of a **cache**, which is positioned on the path *between* a mechanism that *makes requests* and a storage mechanism that *answers requests*
```dynamic-svg
---
invert-shade
width:100%
---
[[cache.svg]]
```

## Cache characteristics

- $ [[#Cache]] is very high-speed
- ! Limited in size (usually *much smaller* than storage *needed* for entire set of items)
- **Automatic** (uses sequence of requests; does not receive *extra instructions*)
- **Active** (*makes decisions* about which items to save)
- **Transparent** (*invisible* to both *requester* and data *store*)

### Cache Implementation details

- [[#Cache]] can be 
	- Implemented in *hardware*, *software*, or a *combination*
	- Store small or large data items (a [[Data representation#Byte|byte]] of [[Memory|memory]] or a complete file)
	- For an *individual* processor or *shared* among processors
	- Retrieval-only or store-and-retrieve

## Cache terminology

- [[#Cache]] **hit**: request *can be satisfied* from cache
- [[#Cache]] **miss**: request *cannot be satisfied* from cache
- **Locality of reference**: refers to whether requests are *repeated*
	- **High** locality means *many* repetitions
	- **Low** locality means *few* repetitions
- @ Note: cache works well with *high locality* of reference

### Caching

- *Key concept* in computing (*one of the most important* optimization techniques available)
- Used in *hardware* and *software*
- @ Note: [[#Memory caches|Memory cache]] is essential to reduce the [[Processor#Von Neumann bottleneck|Von Neumann bottleneck]]

> [!note] 
> 
> - In many programs, **caching** works well *without extra work* (See: [[#Why does caching work so well]])
> - To *optimize* [[#Cache|cache]] performance
> - **Group** related data items into same [[#Cache|cache]] line (e.g., related [[Data representation#Byte|bytes]] into a [[#Words|word]])
> - Perform all operations on *one data item* before moving to the *next*

### Cache blocks

- Divide [[Memory|memory]] into **blocks** of size **B**
- *Blocks* are numbered *modulo* **C**, where **C** is *slots* in [[#Cache|cache]]
- & Example: **block** size of **B**=**8** [[Data representation#Byte|bytes]] and [[#Cache|cache]] size **C**=**4**

`````col 
````col-md 
flexGrow=2
===

```dynamic-svg
---
invert-shade
width:100%
---
[[direct-mapped-mem-cache.svg]]
```

```` 
````col-md 
flexGrow=1
===


> An example assignment of block numbers to [[Memory|memory]] locations for a [[#Cache|cache]] of *four* blocks with *eight* [[Data representation#Byte|bytes]] per **block**


```` 
`````

### Cache tags

> [!definition] 
> **Tag** is the *relative number* of the [[#Cache blocks|block]] in [[Memory|memory]]

- @ General idea: using **tags** allows a *smaller* [[#Cache|cache]]

> - An example [[Memory|memory]] [[#Cache|cache]] with space for *four* [[#Cache blocks|blocks]] and a [[Memory|memory]] divided into *conceptual*  [[#Cache blocks|block]] of **8** [[Data representation#Byte|bytes]]
> - Each group of *four* [[#Cache blocks|blocks]] in [[Memory|memory]] is assigned a *unique* **tag**.
```dynamic-svg
---
invert-shade
width:100%
---
[[cache-tags.svg]]
```


## Cache performance

- **Cost** measured with respect to *requester*
- $C_{h}$, is the *cost* of an item *found* in the [[#Cache|cache]] ([[#Cache terminology|hit]])
- $C_{m}$, is the *cost* of an item *not found* in the [[#Cache|cache]] ([[#Cache terminology|miss]])

> Illustration of *access costs* when using a [[#Cache|cache]]. **Costs** are measured with respect to the requester
```dynamic-svg
---
invert-shade
width:100%
---
[[cache-cost.svg]]
```

### Analysis of cache performance

- **Worst** *case* for sequence of **N** requests: $$C_{worst}=NC_{m}$$
- **Best** *case* for sequence of **N** requests: $$C_{best}=C_{m}+(N-1)*C_{h}$$
- For **best** *case*, the *average* cost per request is: $$\frac{C_{m}+(N-1)*C_{h}}{N}=\frac{C_{m}}{N}-\frac{C_{h}}{N}+C_{h}$$
- @ Key idea: as **$N\to \infty$**, average **cost** approaches **$C_{h}$** ([[#Cache terminology|cache hit]] cost)

#### Why does caching work so well

- If we ignore *overhead*
	- In the **worst** *case*, the performance of caching is *no worse* than if the cache were not present
	- In the **best** *case*, the **cost** per request is approximately equal to the *cost of accessing the cache*
- @ Note: for [[#Memory caches|memory caches]], parallel hardware means *almost no overhead*

#### Expected performance of a cache

- Access *cost* depends on [[#Cache terminology|hit]] ratio **r**
$$Cost=r*C_{h}+(1-r)*C_{m}$$
- @ Notes
	- The **cost** of a [[#Cache terminology|miss]] is often *much larger* than the cost of a [[#Cache terminology|hit]]
	- The *performance improves* if [[#Cache terminology|hit]] **ratio** *increases* or **cost** of access from cache *decreases*

##### Analysis of 2-level cache

- For a [[#Multilevel cache hierarchy|2-level cache]] cost is: $$Cost=r_{1}*C_{h1}+r_{2}*C_{h2}+(1-r_{1}-r_{2})*C_{m}$$ where
	- *$r_{1}$* is **fraction** of [[#Cache terminology|hits]] for the *new* [[#Cache|cache]] 
	- *$r_{2}$* **fraction** of [[#Cache terminology|hits]] for the *original* [[#Cache|cache]] 
	- *$C_{h1}$* is **cost** of accessing the *new* [[#Cache|cache]] 
	- *$C_{h2}$* is **cost** of accessing the *original* [[#Cache|cache]] 

### Cache replacement policy

- Because [[#Cache|cache]] is smaller than data store
- Once [[#Cache|cache]] is full, existing item must be *ejected* before another can be *inserted*
- **Replacement policy** chooses *item to eject*
	- *Most popular* **replacement policy** known as **L**east **R**ecently **U**sed (**LRU**)
		- *Easy* to implement
		- Tends to retain items that will be *requested again*
		- *Works well* in practice

### Cache preloading

> [!definition] 
> 
> - *Optimization* technique
> - Stores items in [[#Cache|cache]] *before requests arrive*
> - Works well if data accessed is in *related groups* (See: [[#Caching]])

- & Examples
	- When web page is fetched, web cache can **preload** images that appear on the page
	- When [[Data representation#Byte|byte]] byte of [[Memory|memory]] is fetched, [[#Memory caches|memory cache]] can **preload** succeeding

## Multilevel cache hierarchy

- Can use multiple [[#Cache|caches]] to *improve performance*
- Arranged in hierarchy by *speed* (i.e., by **cost**)
- & Example: insert an extra, *faster* [[#Cache|cache]] in previous diagram

> The organization of a system with an *additional* [[#Cache|cache]] inserted.
```dynamic-svg
---
invert-shade
width:100%
---
[[multilevel-cache.svg]]
```

## Memory caches

- Several [[Memory|memory]] mechanisms operate as a [[#Cache|cache]]
- & Examples
	- [[#Physical memory caches]]
	- [[#Translation look-aside buffer (TLB)|TLB]] in a [[#Virtual memory|virtual memory system]]
	- [[#Demand paging|Pages]] in a [[#Demand paging|demand paging system]]

> [!seealso] 
> [[#Virtual memory caching]]

### Physical memory caches

- Located between [[Processor|processor]] and [[#Physical memory|physical memory]]
- *Smaller* than [[#Physical memory|physical memory]]
- Use *parallel* hardware to achieve *high performance*
- Perform *2 operations in parallel*
	- Search local [[#Cache|cache]]
	- Send request to *underlying* [[#Physical memory|physical memory]]
		- If answer found in [[#Cache|cache]], *cancel request*

#### Types of physical memory caches

##### Write-through cache

- *Place* a copy of item in [[#Cache|cache]] 
- *Also send* (write) a copy to [[#Physical memory|physical memory]]

##### Write-back cache 

> Much faster than [[#Write-through cache]]

- *Place* a copy of item in [[#Cache|cache]] 
- *Only write* the copy to [[#Physical memory|physical memory]] when *necessary*
- $ Works well for *frequent updates* (e.g., a loop increments a value)

#### Cache coherence

- **Each** [[Processor|processor]] (or *core*) has its own [[#Cache|cache]]
- **Each** [[#Cache|cache]] can retain *copy* of item
- ~ [[#Cache]] **coherence** is needed to *ensure correctness* when one *core changes an item* and others *hold a copy*

`````col 
````col-md 
flexGrow=1.5
===

```dynamic-svg
---
invert-shade
width:100%
---
[[2-caches.svg]]
```

```` 
````col-md 
flexGrow=1
===

> - Illustration of two [[Processor|processors]] sharing underlying [[#Physical memory|physical memory]]
> - Because each [[Processor|processor]] has a separate [[#Cache|cache]] , conflicts can occur if both [[Processor|processors]] reference the same [[Memory|memory]] *address*

```` 
`````

### Multilevel memory caches

- Traditional [[#Memory caches|memory cache]] was *separate* from both the [[Memory|memory]] and the [[Processor|processor]]
- To access traditional memory [[#Cache|cache]], a [[Processor|processor]] used pins that connect it to the rest of the computer
- Using pins to access *external hardware* takes *much longer* than accessing functional units that are **internal** to the [[Processor|processor]] chip
- Advances in technology have *made it possible* to increase the number of transistors per chip, which means a [[Processor|processor]] *chip can contain* a [[#Cache|cache]]

> [!definition] 
> 
> - Level 1 [[#Cache|cache]] (**L1** cache)
> 	- *Per core*
> - Level 2 [[#Cache|cache]] (**L2** cache)
> 	- May be *per core*
> - Level 3 [[#Cache|cache]] (**L3** cache)
> 	- *Shared* among all cores

- @ Historical note: definitions used to specify **L1** as *on-chip*, **L2** as *off-chip*, and **L3** as *part of* [[Memory|memory]]

> [!example] 
> - Example [[#Cache|cache]] sizes in 2016. Although absolute sizes continue to change
> - Readers should focus on the amount of cache relative to [[#Random access memory|RAM]] that is **4 GB** to **32 GB**.
> 
> | [[#Cache]] | Size                   | Notes                  |
> | ----------------- | ---------------------- | ---------------------- |
> | **L1**            | 64 **KB** to **96** KB | Per core               |
> | **L2**            | 256 **KB** to **2** MB | May be per core        |
> | **L3**            | 8 **MB** to **24** MB  | Shared among all cores | 

### Memory cache technologies

- [[#Direct mapped memory cache]] (No parallelism)
- [[#Set associative memory cache]] (Some parallelism)
- [[#Fully associative cache]] (More parallelism)

> [!seealso] 
> Arbitrary parallelism: [[#Content-addressable memory (CAM)]]

#### Direct mapped memory cache

- When [[Data representation#Byte|byte]] is referenced, always place *entire* [[#Cache blocks|block]] in the [[#Cache|cache]]
- If *block number* is **n**, place the [[#Cache blocks|block]] in [[#Cache|cache]] slot **n**
	- Use a [[#Cache tags|tag]] to specify which *actual addresses* are currently in slot **n**

##### Direct mapped memory cache lookup

- ~ Given
	A [[Memory|memory]] *address*
- ? Find
	The **data** [[Data representation#Byte|byte]] at that *address*
- @ Method
	- **Extract** the [[#Cache tags|tag]] number, **t**, [[#Cache blocks|block]] number, **b**, and offset, **o**, from the *address*.
	- **Examine** the [[#Cache tags|tag]] in slot **b** of the [[#Cache|cache]]. If the [[#Cache tags|tag]] matches **t**, extract the value from slot **b** of the [[#Cache|cache]]
	- If the tag in slot b of the [[#Cache|cache]] does not match **t**, use the [[Memory|memory]] *address* to extract the [[#Cache blocks|block]] from [[Memory|memory]], place a copy in slot **b** of the [[#Cache|cache]], replace the [[#Cache tags|tag]] with **t**, and use **o** to select the appropriate [[Data representation#Byte|byte]] from the value

> Block diagram of the hardware used to implement lookup in a [[#Memory caches|memory cache]]

```dynamic-svg
---
invert-shade
width:100%
---
[[cache-lookup.svg]]
```

#### Set associative memory cache

- Alternative to [[#Direct mapped memory cache|direct mapped memory cache]]
- Uses *parallel hardware*
- Maintains *two*, **independent** [[#Cache|caches]]
- Allows *two items* with same [[#Cache blocks|block]] number to be **[[#Caching|cached]] simultaneously**

> Illustration of a **set associative memory cache** with *two copies* of the underlying hardware. The [[#Cache|cache]] includes hardware to *search both* copies *in parallel*
```dynamic-svg
---
invert-shade
width:100%
---
[[set-associative-cache.svg]]
```

- $ Advantages: 
	- Assume two [[Memory|memory]] addresses **A1** and **A2**
- Both have [[#Cache blocks|block]] number **0**
- In [[#Direct mapped memory cache|direct mapped memory cache]]
	- **A1**, and **A2**, contend for *single slot*
	- *Only one* can be [[#Caching|cached]] at a given time
- In **set associative cache**
	- **A1** and **A2**, can be placed in *separate* [[#Cache|caches]]
	- *Both* can be [[#Caching|cached]] at a given time

#### Fully associative cache

- Generalization of [[#Set associative memory cache|set associative cache]]
- Many *parallel* [[#Cache|caches]]
- Each [[#Cache|cache]] has exactly *one slot*
- Slot can hold *arbitrary item*

### Efficient memory cache

- Think *binary*: if all values are *powers of two*, [[Data representation#Bit (Binary digit)|bits]] of an *address* can be used to specify a [[#Cache tags|tag]], [[#Cache blocks|block]], and **offset**
- For the example above (an unrealistically small cache)
- [[#Cache blocks|Block]] size **B** is **8**, so use **3** [[Data representation#Bit (Binary digit)|bits]] of offset
- [[#Cache]] size **C** is **4**, so use **2** [[Data representation#Bit (Binary digit)|bits]] of [[#Cache blocks|block]] number
- [[#Cache tags|Tag]] is [[Divisors, multiples, primes#Division with remainder|remainder]] of address (32 — 5 [[Data representation#Bit (Binary digit)|bits]])

`````col 
````col-md 
flexGrow=1
===

```dynamic-svg
---
invert-shade
width:100%
---
[[eff-mem-cache.svg]]
```

```` 
````col-md 
flexGrow=1
===

Illustration of how using *powers of two* allows a [[#Cache|cache]] to divide
a [[Memory|memory]] *address* into three separate fields that correspond to a [[#Cache tags|tag]], a [[#Cache blocks|block]] number, and a [[Data representation#Byte|byte]] offset within the [[#Cache blocks|block]]


```` 
`````

## Instruction and data caches

- Instruction references are typically *sequential*
	- High [[#Cache terminology|locality of reference]]
	- Amenable to [[#Cache preloading|prefetching]]
- Data references typically exhibit *more randomness*
	- Lower [[#Cache terminology|locality of reference]]
	- [[#Cache preloading|Prefetching]] does not work well
- ? Question: does performance improve with *separate caches* for **data** and **instructions**?
	- [[#Cache]] tends to work well with **sequential** *references*
	- Adding *many random references* tends to *lower* [[#Cache|cache]] *performance*
	- Therefore, separating instruction and data [[#Cache|caches]] can improve performance
	- ~ However: if cache is "large enough", separation doesn’t help
	- @ Current thinking: instead of separate [[#Cache|caches]], simply use a *single larger* [[#Cache|cache]]

`````col 
````col-md 
flexGrow=2
===

```dynamic-svg
---
invert-shade
width:100%
---
[[instruction-data-cache.svg]]
```

```` 
````col-md 
flexGrow=1
===

> A Modified [[Processor#Harward|Harvard Architecture]] with separate **instruction** and **data** [[#Cache|caches]] leading to the same underlying [[Memory|memory]]


```` 
`````

--- 
<br>

# Virtual memory

> [!definition] 
> 
> - Broad concept with lots of variants
> 	- @ General idea
> 		- *Hide* the details of the underlying [[#Physical memory|physical memory]]
> 		- Provide a view of [[Memory|memory]] that is more *convenient to a programmer*
> - Goal is to allow [[#Physical memory|physical memory]] and addressing to be structured in a way that is *optimal for hardware* while providing an **interface** that is *optimal for software*

> [!example] 
> - [[#Byte addressing and translation]] fits our definition of **virtual memory**
> 	- Architecture uses [[Data representation#Byte|byte]] addresses
> 	- Underlying [[#Physical memory|physical memory]] uses [[#Words|word]] addresses
> 	- [[#Memory controller (organization)|Memory controller]] [[#Address translation (address mapping)|translates]] automatically

> [!example] 
> 
> - Most computers have more than one [[#Physical memory|physical memory]] **module**
> - Each [[#Physical memory|physical memory]] **module**
> - Offers *addresses* from **0** to **N-1** for some **N**
> - May use an *arbitrary* [[Memory|memory]] technology (e.g., [[#SRAM]] or [[#DRAM]])
> - **Virtual memory** system can provide *uniform [[#Memory size and address space|address space]]* for all [[#Physical memory|physical memories]]
> 

- $ Motivations
	- **Hardware** perspective
		- Allow multiple [[Memory|memory]] [[#Modules|modules]]
		- Provide *homogeneous* integration
	- **Software** prospective
		- Programmer *convenience*
		- Support for *multiprogramming* and *protection*

## Modules

- Concepts are similar
- [[#Memory banks|Bank]]
	- Generally refers to [[#Physical memory|physical memory]]
	- Used when identical [[Memory|memory]] *modules* are *replicated*
- **Module**
	- More *generic* term often used with **virtual memory** systems
	- Preferred when *heterogeneous* [[Memory|memory]] units are combined

## Virtual memory terminology

- **M**emory **M**anagement **U**nit (**MMU**)
	- Hardware unit
	- Provides [[#Address translation (address mapping)|translation]] between **virtual** and [[#Physical memory|physical memory]] schemes
- **Virtual** address
	- Generated by [[Processor|processor]] (either *instruction fetch* or *data fetch*)
	- *Translated* into corresponding [[#Physical memory|physical memory]] address by **MMU**
- **Physical** address
	- Used by *underlying hardware*
	- May be *completely hidden* from programmer
- **Virtual** [[#Memory size and address space|address space]]
	- Set of all possible *virtual addresses*
	- Can be larger or smaller than [[#Physical memory|physical memory]]
	- Each process may have its own *virtual space*

## Virtual addressing for multiple modules

`````col 
````col-md 
flexGrow=1
===

```dynamic-svg
---
invert-shade
width:100%
---
[[MMU-1-2.svg]]
```

```` 
````col-md 
flexGrow=1
===

> - Illustration of an architecture in which two *dissimilar memories* connect to a [[Processor|processor]]
> - The [[Processor|processor]] can use either [[Memory|memory]]

- Typical scheme: [[Processor|processor]] has a single [[#Virtual memory terminology|virtual address space]]
- [[#Virtual memory terminology|Address space]] covers all memory [[#Modules|modules]]
- & Example
	- Two physical memories with **1GB** each (**0x40000000**) [[Data representation#Byte|bytes]]
	- *Virtual addresses* from **0** to **0x3fffffff** correspond to [[Memory|memory]] **1**
	- *Virtual addresses* from **0x40000000** to **0x7fffffff** correspond to [[Memory|memory]] **2**
- @ Note
	- **0x40000000** is **1** [[#Memory size|gigabyte]] or **1073741824** [[Data representation#Byte|bytes]]
	- For identical [[#Modules|module]], these are called [[Memory|memory]] [[#Memory banks|banks]]

```` 
`````

### Address translation (address mapping)

- Performed by [[#Virtual memory terminology|MMU]]
- & Example
	- To determine which [[#Physical memory|physical memory]], *test* if address is **0x40000000** or above
	- Both memory [[#Modules|module]] use addresses **0** through **0x3fffffff**
	- *Subtract* **0x40000000** from address when **forwarding** a request to [[Memory|memory]]

`````col 
````col-md 
flexGrow=1
===

```dynamic-svg
---
invert-shade
width:100%
---
[[virtual-addressing.svg]]
```

```` 
````col-md 
flexGrow=1
===

> - Illustration of a [[#Virtual memory|virtual memory]] system that divides an [[#Virtual memory terminology|address space]] among two [[#Physical memory|physical memories]]
> - The [[#Virtual memory terminology|MMU]] uses an address to decide which [[Memory|memory]] to access

```` 
`````

### Optimizing calculations

- **Subtraction** is relatively *expensive*
	- Always divide the [[#Virtual memory terminology|virtual address]] space *along boundaries* that correspond to *powers of two*
- [[#Virtual memory terminology|Virtual address]] can be divided into groups of [[Data representation#Bit (Binary digit)|bits]] that
	- Choose among underlying [[#Physical memory|physical memories]]
	- Specify an *address* in the [[#Physical memory|physical memory]]
- @ Note: selecting [[Data representation#Bit (Binary digit)|bits]] in hardware merely requires *running wires* (no **gates** and no **computation**)

> [!example] 
> 
> - Addresses above **0x3fffffff** are the same as the *previous* set except for **high-order** [[Data representation#Bit (Binary digit)|bit]]
> - Hardware uses **high-order** [[Data representation#Bit (Binary digit)|bit]] to select a [[#Physical memory|physical memory]] [[#Modules|module]]
> 
> 
> > The *binary* values for addresses in the range **0** through **2** [[#Memory size|gigabytes]]. Except for the **high-order** [[Data representation#Bit (Binary digit)|bit]], values above **1** [[#Memory size|gigabyte]] are the same as those below
> 
> ```asciidoc
> [cols="1,3"]
> |===
> 
> ^| Addresses
> ^| Values in binary (31 bits)
> 
> ^| **0x00000000** to **0x3fffffff**
> ^| 00000000000000000000000000000000 to 0111111111111111111111111111111
> 
> ^| **0x40000000** to **0x7fffffff**
> ^| 10000000000000000000000000000000 to 1111111111111111111111111111111
> 
> |===
> ```

### Address space continuity

- $ Contiguous [[#Memory size and address space|address space]]
	- All locations correspond to [[#Physical memory|physical memory]]
	- ! Inflexible: requires all [[Memory|memory]] [[IO#Address configuration and sockets|sockets]] to be populated
- $ Discontiguous [[#Memory size and address space|address space]]
	- One or more **blocks** of [[#Memory size and address space|address space]] do not correspond to [[#Physical memory|physical memory]]
		- Called **hole** (See: [[IO#Address assignment (**Address map**)|address assignment]])
		- For most systems, holes are only relevant to **OS** programmers
		- For an [[Processor#Embedded system processors|embedded]] system, *application programmer* may need to avoid **holes**
	- [[#Fetch-store paradigm|Fetch or store]] to any *address* in a **hole** causes an #c/red*error*
	- $ Flexible: allows owner to decide *how much* [[Memory|memory]] to install

`````col 
````col-md 
flexGrow=1
===

```dynamic-svg
---
invert-shade
width:100%
---
[[discontiguous-address-space.svg]]
```

```` 
````col-md 
flexGrow=1
===

> - Example of a *noncontiguous* [[#Memory size and address space|address space]] of **N** [[Data representation#Byte|bytes]] that is mapped onto two [[#Physical memory|physical memories]]. 
> - Some addresses *do not correspond* to [[#Physical memory|physical memory]]


```` 
`````

## Dynamic address space creation

`````col 
````col-md 
flexGrow=1
===

```dynamic-svg
---
invert-shade
width:100%
---
[[virtual-spaces.svg]]
```

```` 
````col-md 
flexGrow=1
===


> - Illustration of *four* partitions mapped onto a *single* [[#Physical memory|physical memory]]. 
> - Each [[#Virtual memory terminology|virtual address]] space starts at address **zero**


```` 
`````

- @ Note: [[#Virtual memory terminology|MMU]] translates each [[#Virtual memory terminology|virtual address]] to a [[#Virtual memory terminology|physical address]]
- The [[#Virtual memory terminology|MMU]] configuration can be changed *at any time*
- ~ Typically
	- Access to [[#Virtual memory terminology|MMU]] restricted to **OS**
	- When operating system runs, *no [[#Address translation (address mapping)|remapping]]* is performed
	- [[Processor]] only changes to [[#Virtual memory|virtual memory]] mode when running an *application*

## Technologies for address space creation

- [[#Base-round registers]] (not used)
- [[#Segmentation]] (used rarely)
- [[#Demand paging]] (most popular)

### Base-round registers

- Requires *two* special hardware [[Processor#Registers|registers]] (part of the [[#Virtual memory terminology|MMU]])
- **Base** [[Processor#Registers|register]] specifies starting **address**
- **Bound** [[Processor#Registers|register]] specifies size of [[#Memory size and address space|address space]]
- Values changed by *operating system*
	- *Set before* application runs
	- Changed by *operating system* when *switching* to another application
- @ Note: Was once popular, but no longer used

`````col 
````col-md 
flexGrow=1
===

```dynamic-svg
---
invert-shade
width:100%
---
[[base-bound-registers.svg]]
```

```` 
````col-md 
flexGrow=1
===

> - Illustration of a [[#Virtual memory|virtual memory]] that uses a **base-bound** mechanism. 
> - The *base* [[Processor#Registers|register]] specifies the *location* of the [[#Memory size and address space|address space]], and the bound [[Processor#Registers|register]] specifies the *size*.

```` 
`````

#### Protection using base-bound technology

- **Key** for systems that run *multiple* applications *concurrently*
- Each applications is given *separate area* of [[#Physical memory|physical memory]]
- *Operating system* sets [[#Base-round registers|base-bound registers]] *before* application *runs*
- [[#Virtual memory terminology|MMU]] hardware checks each [[Memory|memory]] reference
- Reference to any *address* outside the *valid range* results in an #c/red*error*
	- ~ Prevents an application from *snooping* or *changing* another application’s [[Memory|memory]]

### Segmentation

> [!definition] 
> 
> - Alternative to [[#Base-round registers|base-bound registers]]
> - Provides **fine-granularity** [[#Address translation (address mapping)|mapping]]
> 	- Divides program into *segments* (typical segment corresponds to *one procedure*)
> 	- Maps each *segment* to [[#Physical memory|physical memory]]

- @ Key idea
	- When *segment* is no longer needed, **OS** moves it to [[#I/O Devices|disk]]

#### Problems with segmentation

- Need <u>hardware support</u> to make *moving segments* **efficient**
- Two choices
	1. **Variable-size** *segments* cause [[Memory|memory]] #c/red*fragmentation*
	2. **Fixed-size** *segments* may be *too small* or *too large*
- @ Consequence: segmentation is *rarely used*

### Demand paging

> [!definition] 
> 
> - Alternative to [[#Segmentation|segmentation]] and [[#Base-round registers|base-bound registers]]
> - Divides program into *fixed-size pieces* called **pages**
> - No attempt is made to *align* page boundaries with *functions*, *objects*, or large *data structures*

- $ Currently, the *most popular* [[#Virtual memory|virtual memory]] technology
- @ Typical page size **4K** [[Data representation#Byte|bytes]] (4 [[#Memory size|kilobytes]])
- Only some **pages** of a given *application* are in [[Memory|memory]] at any time; Others are kept on [[#I/O Devices|disk]] and *fetched when needed*
	- Allows the [[#Physical memory|physical memory]] allocated to a process to *change over time*
	- **Hardware** is needed to handle [[#Address translation (address mapping)|address mapping]] and detect *missing* **pages**
	- **Software** is needed to move **pages** between *external store* and [[#Physical memory|physical memory]]

#### Paging terminology

1. **Page**: fixed-size piece of program’s [[#Memory size and address space|address space]]
2. **Frame**: *slot* in [[#Physical memory|physical memory]] exactly the size of one **page**
3. **Resident**: a **page** that is currently in [[Memory|memory]]
4. **Resident set**: **pages** from a given *application* that are present in [[Memory|memory]]

#### Paging hardware

- Part of [[#Virtual memory terminology|MMU]]
- Intercepts each [[Memory|memory]] reference
- If *referenced* [[#Demand paging|page]] is present in [[Memory|memory]], *translate* address and *perform* the operation
- If *referenced* [[#Demand paging|page]] not present in [[Memory|memory]], *generate* a page #c/red*fault* (i.e., an error condition)
	- **Record** the details and allow *operating system* to handle the #c/red*fault*

#### Paging software

> 1. Part of the *operating system*
> 2. Works closely with **hardware**

- *Responsible* for overall [[Memory|memory]] management
- **Determines** which pages of each application to keep in [[Memory|memory]] and which to keep on [[#I/O Devices|disk]]
- **Records** location of *all* [[#Demand paging|pages]]
- **Fetches** pages *on demand* (when an application *references* an address that is not in [[Memory|memory]])
- **Configures** the [[#Virtual memory terminology|MMU]]

#### Page replacement

- When a *computer starts*
	- Applications run and reference [[#Demand paging|pages]]
	- Each referenced [[#Demand paging|page]] is placed in [[#Physical memory|physical memory]]
- ~ Eventually
	- [[Memory]] is completely **full**
	- An existing [[#Demand paging|page]] must be *written* to [[#I/O Devices|disk]] before [[Memory|memory]] can be used for *new* [[#Demand paging|page]]
- Choosing a [[#Demand paging|page]] to *expel* is known as **page replacement**
- @ Optimization: replace a [[#Demand paging|page]] that will not be *needed soon*

#### Paging data structure (page table)

> [!definition] 
> 
> - One [[#Demand paging|page]] **table** *per process*
> - *Created* and *managed* by the **OS**
> - Used by the [[#Virtual memory terminology|MMU]] when [[#Address translation (address mapping)|translating an address]]

- Think of a [[#Demand paging|page]] **table** as a *one-dimensional array*
	- Indexed by [[#Demand paging|page]] number
	- Entry stores a *pointer* to the location of the page in [[Memory|memory]] (or a [[Data representation#Bit (Binary digit)|bit]] that indicates the [[#Demand paging|page]] is currently on [[#I/O Devices|disk]])

`````col 
````col-md 
flexGrow=1
===

```dynamic-svg
---
invert-shade
width:100%
---
[[page-table.svg]]
```

```` 
````col-md 
flexGrow=1
===

Each [[#Demand paging|page]] **table** entry points to a [[#Paging terminology|frame]] in [[Memory|memory]] or **null**

> - Illustration of an active [[#Demand paging|page]] **table** with some entrics pointing to [[#Paging terminology|frames]] in [[Memory|memory]]
> - A **null** *pointer* in a [[#Demand paging|page]] table entry (denoted by **A**) means the **page** is not currently [[#Paging terminology|resident]] in [[Memory|memory]]


```` 
`````

##### Address translation with a page table

> Given [[#Virtual memory terminology|virtual address space]] **V**, find underlying [[Memory|memory]] *address* **P**

- @ Three conceptual steps
	1. Determine the *number* of the [[#Demand paging|page]] on which address **V** lies
		- Computed by dividing the [[#Virtual memory terminology|virtual address]] but the number of [[Data representation#Byte|bytes]] per [[#Demand paging|page]], **K** 
		- $N=\frac{V}{K}$
	1. Use the [[#Demand paging|page]] *number* as an *index* into the process’s [[#Demand paging|page]] table to find the starting *address* of a frame in [[Memory|memory]] that contains the specified [[Data representation#Byte|byte]]
	2. Determine *how far* into the [[#Demand paging|page]] address **V** lies, and convert to a *position* in the [[#Paging terminology|frame]] in [[Memory|memory]]
		- **Offset** within the page, **O** is computed as the [[Divisors, multiples, primes#Division with remainder|remainder]] 
		- $O=V\ mod\ K$

`````col 
````col-md 
flexGrow=1
===

```dynamic-svg
---
invert-shade
width:100%
---
[[page-address-translation.svg]]
```

```` 
````col-md 
flexGrow=1
===

Use **N** and **O** to translate [[#Virtual memory terminology|virtual address]] **V** to real [[Memory|memory]] address **A**
$$A=pagetable[N]+O$$

> Illustration of a [[#Virtual memory terminology|virtual address space]] divided into [[#Demand paging|pages]] of **K** [[Data representation#Byte|bytes]] per [[#Demand paging|page]]


```` 
`````

`````col 
````col-md 
flexGrow=1
===

```dynamic-svg
---
invert-shade
width:100%
---
[[page_table_MMU_translation.svg]]
```

```` 
````col-md 
flexGrow=1
===

> - Illustration of how an [[#Virtual memory terminology|MMU]] performs [[#Address translation (address mapping)|address translation]] on a [[#Demand paging|paging]] system
> - Making the [[#Demand paging|page]] size a *power of two* eliminates the need for *division* and [[Divisors, multiples, primes#Division with remainder|remainder]] computation

- @ Typical [[#Demand paging|paging]] system uses *12* [[Data representation#Bit (Binary digit)|bits]] of **offset** (**4K** [[Data representation#Byte|bytes]] (4 [[#Memory size|kilobytes]]))

```` 
`````

> [!seealso] 
> [[#Efficient address translation]]
> 1. *low-order* [[Data representation#Bit (Binary digit)|bits]] - offset **O**
> 2. *high-order* [[Data representation#Bit (Binary digit)|bits]] - [[#Demand paging|page]] number
>

#### Presence, use and modified bits

> [!definition] 
> 
> - Found in most [[#Demand paging|paging]] hardware
> - One set for each [[#Paging data structure (page table)|page table]] entry

```asciidoc
[cols="1,5"]
|===

^| Control bit
^| Purpose

>| **Presence** bit
| **Tested** by hardware to determine if *page* is currently **resident** in memory

>| **Use** bit
| **Set** by hardware whenever *page* is **referenced**

>| **Modified** bit
| **Set** by hardware whenever *page* is **changed**

|===
```

#### Page table storage

> In some systems, the [[#Virtual memory terminology|MMU]] holds [[#Paging data structure (page table)|page tables]]
> **Most** systems place the [[#Paging data structure (page table)|page tables]] in [[Memory|memory]]

- @ Interesting idea:
	- [[#Paging data structure (page table)|Page table]] entry only needs to store the address of a [[#Paging terminology|frame]]
	- Each [[#Paging terminology|frame]] is a *power of two* [[Data representation#Byte|bytes]], so the starting address will have zero in the **k** *low-order* [[Data representation#Bit (Binary digit)|bits]]
	- Instead of storing *zeros*, store the [[#Presence, use and modified bits|presence, use, and modify bits]]
	- Allows [[#Paging data structure (page table)|page table]] entry to *remain aligned* on [[#Words|word]] *boundary*


> - Illustration of how [[#Physical memory|physical memory]] might be *divided* in an architecture that stores [[#Paging data structure (page table)|page tables]] in [[Memory|memory]]
> - A large area of [[#Physical memory|physical memory]] is reserved for [[#Paging terminology|frames]]
```dynamic-svg
---
invert-shade
width:100%
---
[[pages_in_memory.svg]]
```

#### Translation look-aside buffer (TLB)

> [!definition] 
> 
> - Hardware mechanism used to optimize [[#Address translation (address mapping)|address translation]]
> - Employs a form of [[#Content-addressable memory (CAM)]] 
> - Hardware unit stores *pairs* of ([[#Virtual memory terminology|virtual address]], [[#Virtual memory terminology|physical address]])

- @ If pair is in **TLB**
	- [[#Virtual memory terminology|Virtual address]] can be translated without a [[#Paging data structure (page table)|page table]] *reference*
	- [[#Virtual memory terminology|MMU]] returns the translation *much faster* than a [[#Paging data structure (page table)|page table]] *lookup*

> [!note] 
> 
> - A [[#Virtual memory|virtual memory system]] without **TLB** is <u>unacceptable</u>
> - The **TLB** approach works well because application programs tend to *reference* a given [[#Demand paging|page]] *many times*
> 	- @ Principle known as [[#Cache terminology|locality of reference]]

##### Consequences of using look-aside buffer

- Programmer can *optimize program performance* by accommodating the [[#Demand paging|paging]] system
- & Examples
	- **Group** related data items on same [[#Demand paging|page]]
	- [[#Array reference|Reference arrays]] in an order that accesses *contiguous* [[Memory|memory]] locations

###### Array reference

> Consider an *array* stored in **row-major** order
```dynamic-svg
---
invert-shade
width:100%
---
[[array.svg]]
```

- Location \[i,j\] is $location(A)+i*Q=j$ where *$Q$* - number of [[Data representation#Byte|bytes]] per row
- @ Accessing items by *row* makes repeated accesses to the same [[#Demand paging|page]] before moving on

## Virtual memory caching

- Can build a system that [[#Cache|caches]]
	- [[#Physical memory]] *address* and *contents*
	- [[#Virtual memory]] *address* and *contents*
- If [[#Virtual memory terminology|MMU]] is *off-chip*
	- [[#Multilevel memory caches|L1 cache]] must use [[#Virtual memory terminology|virtual addresses]] (cache them)
	- @ Key point: *multiple processes* have separate [[#Memory size and address space|address spaces]], but each uses the same set of [[#Virtual memory terminology|virtual addresses]]

## Handling overlapping

- Each application process uses [[#Virtual memory terminology|virtual addresses]] from **0** through **N**
- System must ensure that an application does not receive data from another application’s [[Memory|memory]]
- @ Two possible approaches
	1. **OS** performs [[#Cache|cache]] *flush* operation when *changing applications*
	2. [[#Cache]] includes [[#ID register|disambiguating tag]] with each entry (i.e., a **process ID**)

### ID register

- Assign each *running application* a unique **ID** (e.g., use a **process ID**)
- **OS** places **ID** in a special hardware [[Processor#Registers|register]] when an application runs
- [[Memory]] system *attaches* **ID** to each address in the [[#Cache|cache]]

`````col 
````col-md 
flexGrow=1
===

```dynamic-svg
---
invert-shade
width:100%
---
[[id_register.svg]]
```

```` 
````col-md 
flexGrow=1
===

> - Illustration of an **ID register** used to *disambiguate* among a set of [[#Virtual memory terminology|virtual address space]] 
> - Each [[#Memory size and address space|address space]] is assigned a *unique number*, which the **OS** loads into the **ID register**


```` 
`````

--- 
<br>

# Go to other topics
``` dataview
list from "uni/Computer architecture"
```
