
# RSA coding

## Preparation

1. Chose different (and big!) prime numbers *$p$*, *$q$*, calculate _$n=p*q$_. Then _$\varphi (n) = (p-1)*(q-1)$_ (Note: see [[Residue classes, divisibility#Euler's function|Euler's function]])
2. Choose *$E$*, [[Divisors, multiples, primes#Coprime numbers|coprime]] with *$\varphi (n)$*
3. Calculate *$D$*, such that _$E*D \equiv 1(mod\ \varphi (n))$_

## Encoding and decoding

1. Let *$x \in Z_{n}$*. Sender calculates $y=e(x)$, it means that *$y \equiv x^E(mod\ n)$* and sends it to the receiver
2. Receiver calculates *$x=d(y)$*, so $x \equiv y^D(mod\ n)$

> [!example] 
> - _$n=23*29=667$_, 
> - $\varphi(667)=\varphi(23)*\varphi(29)=22*28=616$ (See: [[Residue classes, divisibility#Properties of euler's function|Properties of euler's function]])
> - *$E=19$*
> - Solve $19x=1(mod\ 616)$ (See: [[Residue classes, divisibility#Equations and systems|Equations]])
> 	- From $x=227(mod\ 616)$ obtain *$D=227$* (take smallest positive)

> [!example] 
> Encode *$\overline{93}$*
> 
> 1. Calculate $e(\overline{93}) = 93^{19}(mod\ 667) = \overline{323}$
> 2. Receiver decodes $d(\overline{323})=323^{227}(mod\ 667) = \overline{93}$

# Error correcting codes

> [!problem] 
> Untrusted or noisy channels
> - Telephone line (equipment, noise, etc.)
> - Radio communication (radiation, interference)
> - Hard disk (can demagnetize, heat)

> [!solution]
> 
> - We can *extend* messages with **additional information**
> - We are *correcting* a possibly distorted message using this **additional information**

## Error correcting code examples

> [!note] 
> 
> - Codes can obtain an error or *obtain* and *correct* it.
> - Code can correct **one** or **several** errors

````col
```col-md
flexGrow=1
===

1. AN codes
2. BCH code, which can be designed to correct any arbitrary number of errors per code block.
3. Barker code used for radar, telemetry, ultra sound, Wifi, DSSS mobile phone networks, GPS etc.
4. Berger code
5. Constant-weight code
6. Convolutional code
7. Expander codes
8. Group codes
9. Golay codes
10. Goppa code, used in the McEliece cryptosystem
11. Hadamard code
12. Hagelbarger code
13. [[#Hamming code]]
14. Latin square based code for non-white noise (prevalent for example in broadband over powerlines)
15. Lexicographic code
16. Linear Network Coding, a type of erasure correcting code across networks instead of point-to-point links

```
```col-md
flexGrow=1
===


17. Long code
18. Low-density parity-check code, also known as Gallager code
19. LT code, which is a near-optimal rateless erasure correcting code (Fountain code)
20. m of n codes
21. Online code, a near-optimal rateless erasure correcting code
22. Polar code 
23. Raptor code, a near-optimal rateless erasure correcting code
24. Reed–Solomon error correction
25. Reed–Muller code
26. Repeat-accumulate code
27. [[#Repetition]] codes, such as Triple modular redundancy
28. Spinal code, a rateless, nonlinear code based on pseudo-random hash functions
29. Tornado code, a near-optimal erasure correcting code, and the precursor to Fountain codes
30. Turbo code
31. Walsh–Hadamard code
32. Cyclic redundancy checks (CRCs)

```
````

## Repetition

> [!definition] 
> We *repeat* each character *$n$* times, for example *$n=3$*
> Then *$1$* will be encoded by *$111$* and *$0$* - by *$000$*

- If we received *$001$*, *$010$*, or *$100$*, *$0$* was probably sent
- If we received *$011$*, *$101$*, or *$110$*, *$1$* was probably sent
- If **two** mistakes have been made, we will *not* be able to *correct* them

> [!note] 
> What will happen if *$n=4$*? It is *impossible* to check. So, in **repetition** codes *$n$* is *odd*.

## Control symbol

> [!definition] 
> 
> $$m_{k+1}=\left( \sum_{i=1}^{k}m_{i} \right)mod\ 2= \begin{cases} {\color{green}0},\ \text{if sum of vector's elements is}\ \color{green}even \\{\color{yellow}1},\ \text{if sum of vector's elements is}\ \color{yellow}odd \end{cases}$$

> [!note] 
> This code does not *corrects* errors, only **detects**

> [!example] 
> Say *$k=4$*
> - If *$m=1011$*, then *$c=10111$*
> - If *$m=1001$*, then *$c=10010$*

## Table 2x2 code

`````col 
````col-md 
flexGrow=1
===

- $k_{1}={\color{yellow}A} \oplus {\color{orange}B}$
- $k_{2}={\color{blue}C} \oplus {\color{cyan}D}$
- $k_{3}={\color{yellow}A} \oplus {\color{blue}C}$
- $k_{4}={\color{orange}B} \oplus {\color{cyan}D}$

Excoded message will be $abk_1cdk_2k_3k_4$

```` 
````col-md 
flexGrow=1
===

```asciidoc
[cols=3*]
|===

^| [yellow-cell]#A#
^| [orange-cell]#B#

^| A xor B

^| [blue-cell]#C#
^| [cyan-cell]#D#

^| C xor D

^| A xor C
^| B xor D
^|

|===
```

```` 
`````

> [!example] 
> Say, *$c=01101110$*
> Write everything into a table:
> 
> `````col 
> ````col-md 
> flexGrow=0.8
> ===
> 
> - If only *one checksum* is wrong, the error is *in it*. **Don’t change** message
> - $m=0101$
> 
> ```asciidoc
> [cols=3*]
> |===
> 
> ^| 0 
> ^| 1 
> 
> ^| 1 
> 
> ^| 0 
> ^| 1 
> 
> ^| 1
> 
> ^| [red-cell]#1#
> ^| 0
> ^|
> 
> |===
> ```
> 
> ```` 
> ````col-md 
> flexGrow=1
> ===
> 
> - If *two checksums* are wrong, *change message* element that is in the row and column where there were errors
> - $m=0{\color{purple}1}01$
> 
> ```asciidoc
> [cols=3*]
> |===
> 
> ^| 0 
> ^| [purple-cell]#1#
> 
> ^| [red-cell]#1#
> 
> ^| 0 
> ^| 1 
> 
> ^| 1
> 
> ^| 0
> ^| [red-cell]#0#
> ^|
> 
> |===
> ```
> 
> ```` 
> `````

## Hamming code

`````col 
````col-md 
flexGrow=1
===

```dynamic-svg
---
invert-shade
width:100%
---
[[hamming-encode.svg]]
```

```` 
````col-md 
flexGrow=1
===

- A message *$m=m_1m_2m_3m_4$* is encoded by *$c=m_1m_2m_3m_4c_5c_6c_7$*
- Control numbers *$c_5\ c_6\ c_7$* are selected to have **even** sum in *every circle*
- Code corrects *one error*

> [!example] 
> - $m=1000$
> - $c_{5}=1,\ c_{6}=0,\ c_{7}=1$
> - $c=1000101$

```` 
`````

### Decoding

- Check if in *every circle* sum is *even*
	- **One** error, **only checksum** is wrong
	- **Two** wrong checksums, so error is in the area **between**
	- **Three** wrong checksums, so error is in the **center**

### Hamming distance

> [!definition] 
> Amount of different values in same positions

> [!example] 
> 
> - 1011 and 1110
> 	- Distance: **2**
> - 2014 and 1982
> 	- Distance: **4**

--- 
<br>

# Go to other topics
``` dataview
list from "uni/Discrete math"
```