---
icon: TiBoxMultiple
---

# Multiples, divisors

> [!definition] 
> - Integer *$a$* is a **multiple** of some integer *$b$* if we can write *$a=bm$*
> - Non-zero integer *$b$* **divides** integer *$a$* or *$b|a$* (there are several notions), if number *$a$* is it’s **multiple**. 
> - Then number *$b$* is called a **divisor** of number *$a$*

> [!theorem] 
> If *$a$*, *$b$* and *$c$* are integers, then
> - For all *$a$*: *$a|a$*
> - For all *$a$*, *$b$* and *$c$*: if *$a|b$* and *$b|c$*, then *$a|c$*
> - For all *$a$*, *$b$* and *$c$*: *$b|a$* and *$b|c$* <u>if and only if</u> _$b|(m*a+n*c)$_ with any integer numbers *$m$* and *$n$* 

> [!theorem] 
> - If *$a$* and *$b$* are natural numbers and *$a|b$*, then *$a \leq b$*
> - If *$a$* and *$b$* are natural numbers, *$a|b$* and *$b|a$*, then *$a=b$* or *$a=-b$*

--- 
<br>

# Division with remainder

> [!theorem] 
> 
> If *$a$* and *$b$* are natural numbers, then <u>there is only one pair of natural numbers</u> *$q$* and *$r$*, where *$0\leq r < b$*, such that *$a=bq+r$*

> [!theorem] 
> If ${\color{green}a}={\color{orange}b}q+{\color{cyan}c}$, then $gcd({\color{green}a};{\color{orange}b})=gcd({\color{orange}b};{\color{cyan}c})$
> 
> > [!example] 
> > - 17=3\*5+2; $gcd(17;3)=1, \ gcd(3;2)=1$
> > - 33=6\*5+3; $gcd(33;6)=3, \ gcd(6;3)=3$

--- 
<br>

# Common divisor

> [!definition] 
> Natural number *$d$* is **common [[#Multiples, divisors|divisor]]** of numbers *$a$* and *$b$*, if *$d|a$* and *$d|b$*

## Greatest common divisor

> [!definition] 
> 
> - Natural number *$d$* is called *greatest* **common divisor** of numbers *$a$* and *$b$*, if
> 	- It is a [[#Common divisor|common divisor]]
> 	- If *$c|a$* and *$c|b$*, then *$c|d$* (*$c$* - any **common divisor**) (should be *divisible* by all other [[#Common divisor|common divisors]])
> - $ Notion $gcd(a;b)$ 

> [!example] 
> *Common* divisors of **1540** and **294** are *1*, *2*, *7*, *14*
> $gcd(1540,294)=14$; (2|14, 7|14)

### Euclid's algorithm

If *$a$* and *$b$* are natural numbers, and

$$a=bq_{0}+{\color{red}r_{0}} \ \ \ \ \ 0\leq {\color{red}r_{0}} \leq b$$
$$b={\color{red}r_{0}}q_{1}+r_{1} \ \ \ \ \ 0\leq r_{1} \leq r_{0}$$
$$r_{0}=r_{1}q_{2}+r_{2} \ \ \ \ \ 0\leq r_{2} \leq r_{1}$$
$$r_{1}=r_{2}q_{3}+r_{3} \ \ \ \ \ 0\leq r_{3} \leq r_{2}$$
$$r_{2}=r_{3}q_{4}+r_{4} \ \ \ \ \ 0\leq r_{4} \leq r_{3}$$

- Let *$s$* be the first integer with which *$r_{s}=0$*. Then
	- *$r_{s-1}=gcd(a;b)$*, if *$s>0$*
	- *$b=gcd(a;b)$*, if *$s=0$*

> [!example] 
> Find $gcd({\color{cyan}203};{\color{green}91})$
> 
> ````col
> ```col-md
> flexGrow=1
> ===
> 
> #c/blue **203** = #c/green **91** \* 2 + #c/red **21**;    
> #c/green **91** = #c/red **21** \* 4 + #c/purple **7**;  
> #c/red **21** = #c/purple **7** \* 3 + 0;             
> 
> ```
> ```col-md
> flexGrow=3
> ===
> 
> $a=bq_{0}+{\color{red}r_{0}}$
> $b={\color{red}r_{0}}q_{1}+r_{1}$
> $r_{0}=r_{1}q_{2}+r_{2}$
> 
> ```
> ````
> 
> *$s=2$*
> $gcd(203;91)=r_{s-1}=r_{1}={\color{purple}7}$

### UV form

> [!theorem] 
> if *$d$* and *$c$* are [[#Greatest common divisor|greatest common divisors]] of numbers *$a$* and *$b$*, then *$d=c$*
> 
> > [!tldr] 
> > There can't be **2** *different* [[#Greatest common divisor|greatest common divisors]]

> [!theorem] 
> If *$a$* and *$b$* - are natural numbers, then their [[#Greatest common divisor|greatest common divisor]] can be written in a form $u{\color{red}a}+v{\color{red}b}$, where *$u$* and *$v$* - <u>integer</u> numbers
> 
> > [!example] 
> > $gcd({\color{cyan}85};{\color{green}34})$ in form ${\color{cyan}85}u+{\color{green}34}v$
> > $gcd({\color{cyan}85};{\color{green}34}) = 17 = {\color{cyan}85} * 1 + {\color{green}34}*(-2)$

#### Matrix method

````col
```col-md
flexGrow=1
===

$$ 
\begin{pmatrix}  
1 & 0 & 252 \\
0 & 1 & 575
\end{pmatrix} 
$$

> First row(-2) + second row

```
```col-md
flexGrow=1
===

$$ 
\begin{pmatrix}  
1  & 0 & 252 \\
-2 & 1 & 72
\end{pmatrix} 
$$

> Second row(−3) + first row

```
```col-md
flexGrow=1
===

$$ 
\begin{pmatrix}  
7  & -3 & 36 \\
-2 & 1  & 72
\end{pmatrix} 
$$

> First row(−2) + second row

```
```col-md
flexGrow=1
===

$$ 
\begin{pmatrix}  
\color{red} 7 & \color{orange} -3 & \color{red} 36 \\
-16           & 7                 & \color{red} 0
\end{pmatrix} 
$$

> $gcd(252;576)=$
> $252 * {\color{red}7} + 576 * ({\color{orange}-3})$

```
````

> [!seealso] 
> [[#ax+by=c equation]]

--- 
<br>

# Prime numbers

> [!definition] 
> - An integer greater than **1** is called **prime** if it has *no positive [[#Multiples, divisors|divisors]]* other than **1** and itself
> - ~ Otherwise, that number is called **composite** (can be written as a *product* of **primes**)

> [!theorem] 
> - Euclid's theorem
> - $ There are an *infinite* amount of **prime numbers**

> [!theorem] 
> - If natural number *$n$* is **composite**, then it has such **prime** [[#Multiples, divisors|divisor]] *$p$*, that *$p^2<n$*
> - If *$p$* - **prime** number and *$p|ab$*, where *$a$* and *$b$* - natural numbers, then *$p|a$* or *$p|b$*
> 	- Generalized: If *$p$* divides a *product* of natural numbers, it at least divides *one* of those numbers
> - If **prime** number *$p$* divides a *product* of **prime** numbers, it is *equal* to at least one number from the *product*

## Canonical form of number

$$\Large n=p_{1}^{\alpha_{1}}*p_{2}^{\alpha_{2}}*\dots * p_{k}^{\alpha_{k}}$$

- $p_{1},p_{2},p_{3},\dots,p_{k}$ - different **prime** numbers
- $\alpha_{i}\geq 1, i=1,2,\dots,k$

> [!example] 
> - $24=2^3*3^1$
> - $18=2^1*3^2$

### Number of divisors

$d(n)=(\alpha_{1}+1)*(\alpha_{2}+1)*\dots*(\alpha_{k}+1)$

> [!example] 
> - $24=2^3*3^1$, $d(n)=(3+1)*(1+1)=8$ 
> - $ Check: (1, 2, 3, 4, 6, 8, 12, 24)

## Coprime numbers

> [!definition] 
> - If [[#Greatest common divisor|greatest common divisor]] of numbers *$a$* and *$b$* is equal to **1**, then they are called **coprime**.
> - Numbers $\frac{a}{gcd(a;b)}$ and $\frac{b}{gcd(a;b)}$ are **coprime**
> - If *$a$*, *$b$* and *$c$* are integers, *$a$* and *$b$* are **coprime** and *$a|bc$*, then *$a|c$*

> [!example] 
> - *$gcd(17;3)=1$*
> - 3|(**6**\*17), 3|6

--- 
<br>

# Common multiple

> [!definition] 
> Natural number *$m$* is a **common [[#Multiples, divisors|multiple]]** of numbers *$a$* and *$b$*, if *$a|m$* and *$b|m$*

> [!example] 
> [[#Multiples, divisors|Multiples]] of **14** are 14,28,*42*,56,70,*84*,98,112,*126*,...
> [[#Multiples, divisors|Multiples]] of **21** are 21,*42*,63,*84*,105,*126*,...

## Least common multiple

> [!definition] 
> - Natural number *$m$* is called **nearest common multiple** of numbers *$a$* and *$b$*, if
> 	- It is [[#Common multiple|common multiple]] 
> 	- If *$a|n$* and *$b|n$*, then *$m|n$* (should divide all other [[#Common multiple|common multiples]])
> - $ Notion: $lcm(a;b)$

> [!theorem] 
> If *$a$* and *$b$* - natural numbers, then $gcd({\color{orange}a};{\color{red}b})*lcm({\color{orange}a};{\color{red}b})={\color{orange}a}*{\color{red}b}$

--- 
<br>

# ax+by=c equation

> [!theorem] 
> Say *$a$*, *$b$* and *$c$* are integers. Solution $(x;y)$ of equation $$ax+by=c$$ is pair of integers if, and only if *$c$* is *divisible* by *$gcd(a;b)$* (See: [[#Greatest common divisor]])
> Then $$x_{0}=\frac{uc}{gcd(a;b)},\ \ \ y_{0}=\frac{vc}{gcd(a;b)}$$
> Other solutions: $$x = x_{0}=+\frac{bt}{gcd(a;b)},\ \ \ y = y_{0}-\frac{at}{gcd(a;b)}$$
> Where 
> *$t$* - any integer
> *$u$* and *$v$* are any solutions of equation $$gcd(a;b)=au+bv$$

> [!example] 
> Solve the equation $$85x+34y=51$$
> 1. $gcd(85;34)=17$
> 2. *$c=51$* is divisible by **17**
> 3. $17=85*1+34*(-2)$
> 4. $x_{0}={\frac{uc}{gcd(a;b)}} = {\frac{1*51}{17}} = 3$, $x_{1}=5$
> 5. $y_{0}={\frac{vc}{gcd(a;b)}} = {\frac{(-2)*51}{17}} = -6$, $y_{1}=-11$

--- 
<br>

# Sieve of Eratosthenes

Let’s find [[#Prime numbers|prime]] numbers less than **101** 

1. The first [[#Prime numbers|prime]] number is **2**. Every *second* (except **2**) is marked as not [[#Prime numbers|prime]]. They can be *divided* by **2**
2. The first *unmarked* number after **2** is **3**. Every *third* number (except **3**) is marked as not [[#Prime numbers|prime]]. They can be *divided* by **3**
3. The first *unmarked* number after **3** is **5**. Every *fifth* number (except **5**) is marked as not [[#Prime numbers|prime]]. They can be *divided* by **5**
4. The first *unmarked* number after **5** is **7**. Every *seventh* number (except **7**) is marked as not [[#Prime numbers|prime]]. They can be *divided* by **7**
5. $ We found all numbers. Why?
	- $\sqrt{100}=10$, the largest [[#Prime numbers|prime]] number *smaller* than **10** is **7**

```asciidoc
[frame=none]
[cols="20*"]
|===

^|1  ^|2  ^|3  ^|[orange-cell]#4#  ^|5  ^|[orange-cell]#6#  ^|7  ^|[orange-cell]#8#  ^|[yellow-cell]#9#   ^|[orange-cell]#10# ^|11 ^|[orange-cell]#12# ^|13 ^|[orange-cell]#14# ^|[yellow-cell]#15# ^|[orange-cell]#16# ^|17 ^|[orange-cell]#18# ^|19 ^|[orange-cell]#20# 
^|[yellow-cell]#21# ^|[orange-cell]#22# ^|23 ^|[orange-cell]#24# ^|[green-cell]#25# ^|[orange-cell]#26# ^|[yellow-cell]#27# ^|[orange-cell]#28# ^|29 ^|[orange-cell]#30# ^|31 ^|[orange-cell]#32# ^|[yellow-cell]#33# ^|[orange-cell]#34# ^|[green-cell]#35# ^|[orange-cell]#36# ^|37 ^|[orange-cell]#38# ^|[yellow-cell]#39# ^|[orange-cell]#40# 
^|41 ^|[orange-cell]#42# ^|43 ^|[orange-cell]#44# ^|[yellow-cell]#45# ^|[orange-cell]#46# ^|47 ^|[orange-cell]#48# ^|[cyan-cell]#49# ^|[orange-cell]#50# ^|[yellow-cell]#51# ^|[orange-cell]#52# ^|53 ^|[orange-cell]#54# ^|[green-cell]#55# ^|[orange-cell]#56# ^|[yellow-cell]#57# ^|[orange-cell]#58# ^|59 ^|[orange-cell]#60# 
^|61 ^|[orange-cell]#62# ^|[yellow-cell]#63# ^|[orange-cell]#64# ^|[green-cell]#65# ^|[orange-cell]#66# ^|67 ^|[orange-cell]#68# ^|[yellow-cell]#69# ^|[orange-cell]#70# ^|71 ^|[orange-cell]#72# ^|73 ^|[orange-cell]#74# ^|[yellow-cell]#75# ^|[orange-cell]#76# ^|[cyan-cell]#77# ^|[orange-cell]#78# ^|79 ^|[orange-cell]#80# 
^|[yellow-cell]#81# ^|[orange-cell]#82# ^|83 ^|[orange-cell]#84# ^|[green-cell]#85# ^|[orange-cell]#86# ^|[yellow-cell]#87# ^|[orange-cell]#88# ^|89 ^|[orange-cell]#90# ^|[cyan-cell]#91# ^|[orange-cell]#92# ^|[yellow-cell]#93# ^|[orange-cell]#94# ^|[green-cell]#95# ^|[orange-cell]#96# ^|97 ^|[orange-cell]#98# ^|[yellow-cell]#99# ^|[orange-cell]#100#



|===
```

--- 
<br>

# Go to other topics
``` dataview
list from "uni/Discrete math"
```