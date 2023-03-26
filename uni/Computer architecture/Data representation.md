# Digital logic

- Built on <u>two-valued</u> logic system
	- *+5v* and *0v*
	- *High* and *low*
	- *true* and *false*
	- *asserted* and *not asserted*

> [!note] 
> Underneath it's all electrons and wires

## Data representation

- Builds on [[#Digital logic|digital logic]]
- Applies familiar abstractions
- Interprets sets of boolean values as
	- numbers
	- characters
	- addresses

> [!note] 
> Underneath it's all just bits

### Bit (Binary digit)

>Direct representation of digital logic values

- Can be *0* or *1*
- Multiple **bits** are used to represent complex data items
- Same underlying hardware can represent bits of an integer or bits of a character

### Byte

> Set of multiple [[#Bit (Binary digit)|bits]]

- Size depends on underlying hardware (computer)
	- CDC - 6 [[#Bit (Binary digit)|bits]]
	- BB&N - 10 [[#Bit (Binary digit)|bits]]
	- IBM - 8 [[#Bit (Binary digit)|bits]] (standard nowadays)
- On most computers the **byte** is the <u>smallest addressable</u> unit of storage

#### Byte size

> Numbers of [[#Bit (Binary digit)|bits]] per [[#Byte|byte]] determines range of values that can be stored 

- [[#Byte]] of *k* [[#Bit (Binary digit)|bits]] can store *$2^k$* values
	- 6 [[#Bit (Binary digit)|bits]] - *64* values
	- 8 [[#Bit (Binary digit)|bits]] - *256* values
	- 10 [[#Bit (Binary digit)|bits]] - *1024* values

### Binary representation

> [[#Bit (Binary digit)|Bits]] themselves have no meaning
> [[#Byte]] just stores *1's* and *0's*
> i.e: 
> - 000 001 010 011
> - 100 101 110 111

All meaning is determined by how the [[#Bit (Binary digit)|bits]] are interpreted

> [!example] 
> Possible interpretation of 3 [[#Bit (Binary digit)|bits]] 
> - Device status
> 	- First [[#Bit (Binary digit)|bit]] is *1* if disk is connected
> 	- Seconds [[#Bit (Binary digit)|bit]] is *1* if printer is connected
> 	- Third [[#Bit (Binary digit)|bit]] is *1* is keyboard is connected

#### Integer interpretation

- Positional representation uses base *2*
- Values are from *0* to *7*
- Order of bits must be specified

> [!example] 
> <u>0 1 0 1 0 1</u> is interpreted as:
> $0 * 2^5 + 1 * 2^4 + 0 * 2^3 + 1 * 2^2 + 0 * 2^1 + 1 * 2^0 = 21$

- A set of *k* [[#Bit (Binary digit)|bits]] can represent integers from *0* to *$2^k-1$*

##### Powers of 2

| Power of 2 | Decimal value | Decimal digits |
| ---------- | ------------- | -------------- |
| 0          | 1             | 1              |
| 1          | 2             | 1              |
| 2          | 4             | 1              |
| 3          | 8             | 1              |
| 4          | 16            | 2              |
| 5          | 32            | 2              |
| 6          | 64            | 2              |
| 7          | 128           | 3              |
| 8          | 256           | 3              |
| 9          | 512           | 3              |
| 10         | 1024          | 4              |
| 11         | 2048          | 4              |
| 12         | 4096          | 4              |
| 15         | 16384         | 5              |
| 16         | 32768         | 5              |
| 20         | 1048576       | 7              |
| 30         | 1073741824    | 10             |
| 32         | 4294967296    | 20             |

##### Hexadecimal notation

- Mathematically it's *base 16*
- Each hex digit encodes *4* [[#Bit (Binary digit)|bits]]
- Typically syntax: starts with *0x*

#### Number systems

> [!example] 
> $42_{10}=101010_{2}=52_{8}=2A_{16}$
> Binary: *0b*
> Octal: *0o*
> Hex: *0x*

##### Any system to decimal

$$\large (x_{n-1},x_{n-2},\dots ,x_{0},x_{-1},x_{-2}, \dots ,x_{-l})=\sum_{i=-l}^{n-1}x_{i}*p^i$$

- *n* - amount of decimals <u>before</u> the coma
- *i* - amount of decimals <u>after</u> the coma
- *$x_{i}$*- number of i-th order 
- *p* - base of the number system

##### Decimal to any system

1. For the whole part:
	1. Divide by the *base* of the system 
	2. Keep track of *quotient* and *remainder*
	3. Keep dividing the *quotient* until you get a *0*
	4. Write *remainders* in reverse order
2. For the fractional part:
	1. Multiply the fractional part by the *base*
	2. Take the *whole* part 
		1. *1,345* becomes *0,345*, *1* is recorded
		2. *0,765* does not change, *0* is recorded
	3. Repeat until the desired *precision* is reached

##### Binary to hex and octal and reverse

> Group/ungroup by *4* for *hex*, by *3* for *octal*

- $1000 \ 0001 = 81_{16}$ 
	- $1000_{2}=8_{16}$
	- $0001 = 1_{16}$

$B43_{16} = 1011 \ 0100 \ 0011_{2}$

> [!summary] 
> - Fundamental value in digital logic is a [[#Bit (Binary digit)|bit]]
> - Binary conversations are used to represent bits to programmer
> - Hexadecimal number system is used to shorten binary representation

--- 
<br>

# ASCII

- American Standard Code for Information Interchange
- Vendor independent
- Adopted by PC manufacturers
- Specifies 128 characters
- Unprintable characters for modem control

> Full ASCII table

|     | Dec | Char                     | Dec | Char  | Dec | Char | Dec | Char |
| --- | --- | ------------------------ | --- | ----- | --- | ---- | --- | ---- |
| 0   | NUL | (null)                   | 32  | SPACE | 64  | @    | 96  | `    |
| 1   | SOH | (start of heading)       | 33  | !     | 65  | A    | 97  | a    |
| 2   | STX | (start of text)          | 34  | "     | 66  | B    | 98  | b    |
| 3   | ETX | (end of text)            | 35  | #     | 67  | C    | 99  | c    |
| 4   | EOT | (end of transmission)    | 36  | $     | 68  | D    | 100 | d    |
| 5   | ENQ | (enquiry)                | 37  | %     | 69  | E    | 101 | e    |
| 6   | ACK | (acknowledge)            | 38  | &     | 70  | F    | 102 | f    |
| 7   | BEL | (bell)                   | 39  | '     | 71  | G    | 103 | g    |
| 8   | BS  | (backspace)              | 40  | (     | 72  | H    | 104 | h    |
| 9   | TAB | (horizontal tab)         | 41  | )     | 73  | I    | 105 | i    |
| 10  | LF  | (NL line feed, new line) | 42  | *     | 74  | J    | 106 | j    |
| 11  | VT  | (vertical tab)           | 43  | +     | 75  | K    | 107 | k    |
| 12  | FF  | (NP form feed, new page) | 44  | ,     | 76  | L    | 108 | l    |
| 13  | CR  | (carriage return)        | 45  | -     | 77  | M    | 109 | m    |
| 14  | SO  | (shift out)              | 46  | .     | 78  | N    | 110 | n    |
| 15  | SI  | (shift in)               | 47  | /     | 79  | O    | 111 | o    |
| 16  | DLE | (data link escape)       | 48  | 0     | 80  | P    | 112 | p    |
| 17  | DC1 | (device control 1)       | 49  | 1     | 81  | Q    | 113 | q    |
| 18  | DC2 | (device control 2)       | 50  | 2     | 82  | R    | 114 | r    |
| 19  | DC3 | (device control 3)       | 51  | 3     | 83  | S    | 115 | s    |
| 20  | DC4 | (device control 4)       | 52  | 4     | 84  | T    | 116 | t    |
| 21  | NAK | (negative acknowledge)   | 53  | 5     | 85  | U    | 117 | u    |
| 22  | SYN | (synchronous idle)       | 54  | 6     | 86  | V    | 118 | v    |
| 23  | ETB | (end of trans. block)    | 55  | 7     | 87  | W    | 119 | w    |
| 24  | CAN | (cancel)                 | 56  | 8     | 88  | X    | 120 | x    |
| 25  | EM  | (end of medium)          | 57  | 9     | 89  | Y    | 121 | y    |
| 26  | SUB | (substitute)             | 58  | \:    | 90  | Z    | 122 | z    |
| 27  | ESC | (escape)                 | 59  | ;     | 91  | \[   | 123 | {    |
| 28  | FS  | (file separator)         | 60  | <     | 92  | \    | 124 | \|   |
| 29  | GS  | (group separator)        | 61  | =     | 93  | ]    | 125 | }    |
| 30  | RS  | (record separator)       | 62  | >     | 94  | ^    | 126 | ~    |
| 31  | US  | (unit separator)         | 63  | ?     | 95  | _    | 127 | DEL  |

## Unicode

- Extends [[#ASCII]]
	- Assigns meaning to values from 128 to 255
	- Characters can be 16/32 [[#Bit (Binary digit)|bits]] long
- Can represent larger set of characters
- Can accommodate languages such as Chinese