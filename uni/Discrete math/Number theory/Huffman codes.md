# Data compression

> [!definition] 
> 
> In information theory, **data compression**, source coding, or [[Data representation#Bit (Binary digit)|bit]]-rate *reduction* is the process of encoding information using *fewer* [[Data representation#Bit (Binary digit)|bits]] than the original representation. Any particular compression is either **lossy** or **lossless**

- **Lossless** compression reduces [[Data representation#Bit (Binary digit)|bits]] by *identifying* and *eliminating* statistical **redundancy**
	- <u>No information is lost</u> in lossless compression
	- Process is *reversible*
	- Used in *archivers*
- **Lossy** compression reduces [[Data representation#Bit (Binary digit)|bits]] by removing *unnecessary* or *less important* information
	- Usually based on *transform coding*, especially the *discrete cosine transform*
	- Can cause <u>information loss</u>
	- Used in *video*, *audio*, *image* compression

## Lossless compression examples

- Run-length encoding (RLE) –good compression of data containing many runs of the same value
- [[#Huffman coding]] – Entropy encoding, pairs well with other algorithms
	- See: [[#Huffman coding variations]]
- Arithmetic coding – Entropy encoding
- ANS – Entropy encoding, used by LZFSE and Zstandard
- Lempel-Ziv compression (LZ77 and LZ78) – Dictionary-based algorithm that forms the basis for many other algorithms
- Lempel–Ziv–Storer–Szymanski (LZSS) – Used by WinRAR in tandem with Huffman coding
- Deflate – Combines LZSS compression with Huffman coding, used by ZIP, gzip, and PNG images
- Lempel–Ziv–Welch (LZW) – Used by GIF images and Unix's compress utility
- Lempel–Ziv–Markov chain algorithm (LZMA) – Very high compression ratio, used by 7zip and xz
- Burrows–Wheeler transform reversible transform for making textual data more compressible, used by bzip2
- Prediction by partial matching (PPM) – Optimized for compressing plain text

## Huffman coding variations

- n-ary Huffman coding
- Adaptive Huffman coding
- Huffman template algorithm
- Length-limited Huffman coding/minimum variance Huffman coding
- Huffman coding with unequal letter costs
- Optimal alphabetic binary trees (Hu–Tucker coding)
- The canonical Huffman code

# Huffman coding

> [!definition] 
> 
> - Binary [[Graphs - trees|trees]] are used, so characters are replaced by sequences of *zeros* and *units*
> - **More often** used characters are encoded in **shorter sequences**

## Example

> Let’s encode "**at five we meet at the university**"

1. Length of this message with *spaces* is **33** symbols. 
	- We can minimize amount of symbols by *not using spaces*, so now there are **27** symbols
2. Make table of *frequencies*
3. Sort symbols by their *frequencies* (**ascending**) and symbols of the *same frequency* alphabetically

```asciidoc
[cols=14*]
|===

^|F ^|H ^|M ^|N ^|R ^|S ^|U ^|W ^|Y ^|A ^|V ^|I ^|T ^|E 
^|1 ^|1 ^|1 ^|1 ^|1 ^|1 ^|1 ^|1 ^|1 ^|2 ^|2 ^|3 ^|5 ^|6

|===
```

4. Join two leftmost elements to a *group*

`````col 
````col-md 
flexGrow=1
===

```asciidoc
[cols=14*]
|===

^|[green-cell]#F# ^|[green-cell]#H# ^|M ^|N ^|R ^|S ^|U ^|W ^|Y ^|A ^|V ^|I ^|T ^|E 
^|1 ^|1 ^|1 ^|1 ^|1 ^|1 ^|1 ^|1 ^|1 ^|2 ^|2 ^|3 ^|5 ^|6

|===
```

```` 
````col-md 
flexGrow=1
===

```asciidoc
[cols=13*]
|===

^|M ^|N ^|R ^|S ^|U ^|W ^|Y ^|[green-cell]#FH# ^|A ^|V ^|I ^|T ^|E 
^|1 ^|1 ^|1 ^|1 ^|1 ^|1 ^|1 ^|2                ^|2 ^|2 ^|3 ^|5 ^|6

|===
```

```` 
`````

5. Repeat until there is only **1** element in the table

`````col 
````col-md 
flexGrow=1
===

```asciidoc
[cols=13*]
|===

^|[cyan-cell]#M# ^|[cyan-cell]#N# ^|R ^|S ^|U ^|W ^|Y ^|[green-cell]#FH# ^|A ^|V ^|I ^|T ^|E 
^|1 ^|1 ^|1 ^|1 ^|1 ^|1 ^|1 ^|2                ^|2 ^|2 ^|3 ^|5 ^|6

|===
```

```asciidoc
[cols=12*]
|===

^|[blue-cell]#R# ^|[blue-cell]#S# ^|U ^|W ^|Y ^|[cyan-cell]#MN# ^|[green-cell]#FH# ^|A ^|V ^|I ^|T ^|E 
^|1 ^|1 ^|1 ^|1 ^|1 ^|2               ^|2                ^|2 ^|2 ^|3 ^|5 ^|6

|===
```

```asciidoc
[cols=11*]
|===

^|[yellow-cell]#U# ^|[yellow-cell]#W# ^|Y ^|[blue-cell]#RS# ^|[cyan-cell]#MN# ^|[green-cell]#FH# ^|A ^|V ^|I ^|T ^|E 
^|1 ^|1 ^|1 ^|2             ^|2               ^|2                ^|2 ^|2 ^|3 ^|5 ^|6

|===
```

......

```asciidoc
[cols=2*]
|===

^|TE ^|IFHARSMNVYUW 
^|11 ^|16 

|===
```


```` 
````col-md 
flexGrow=1
===

```asciidoc
[cols=12*]
|===

^|R ^|S ^|U ^|W ^|Y ^|[cyan-cell]#MN# ^|[green-cell]#FH# ^|A ^|V ^|I ^|T ^|E 
^|1 ^|1 ^|1 ^|1 ^|1 ^|2               ^|2                ^|2 ^|2 ^|3 ^|5 ^|6

|===
```

```asciidoc
[cols=11*]
|===

^|U ^|W ^|Y ^|[blue-cell]#RS# ^|[cyan-cell]#MN# ^|[green-cell]#FH# ^|A ^|V ^|I ^|T ^|E 
^|1 ^|1 ^|1 ^|2             ^|2               ^|2                ^|2 ^|2 ^|3 ^|5 ^|6

|===
```

```asciidoc
[cols=10*]
|===

^|Y ^|[yellow-cell]#UW# ^|[blue-cell]#RS# ^|[cyan-cell]#MN# ^|[green-cell]#FH# ^|A ^|V ^|I ^|T ^|E 
^|1 ^|2                 ^|2             ^|2               ^|2                ^|2 ^|2 ^|3 ^|5 ^|6

|===
```
......

```asciidoc
[cols=1*]
|===

^|TEIFHARSMNVYUW 
^|27

|===
```

```` 
`````
