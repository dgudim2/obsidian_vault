
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

# Common divisor

> [!definition] 
> Natural number *$d$* is **common divisor** of numbers *$a$* and *$b$*, if *$d|a$* and *$d|b$*

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

## Euclid's algorithm

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
> Find $gcd({\color{cyan}203};{\color{lime}91})$
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

## UV form

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

## Matrix form

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

# Prime numbers

## Coprime numbers

> [!definition] 
> - If [[#Greatest common divisor|greatest common divisor]] of numbers *$a$* and *$b$* is equal to **1**, then they are called **coprime**.
> - Numbers $\frac{a}{gcd(a;b)}$ and $\frac{b}{gcd(a;b)}$ are **coprime**
> - If *$a$*, *$b$* and *$c$* are integers, *$a$* and *$b$* are **coprime** and *$a|bc$*, then *$a|c$*

> [!example] 
> - *$gcd(17;3)=1$*
> - 3|(**6**\*17), 3|6

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

# Equations

> [!theorem] 
> Say *$a$*, *$b$* and *$c$* are integers. Solution $(x;y)$ of equation $$ax+by=c$$ is pair of integers if, and only if *$c$* is *divisible* by *$gcd(a;b)$*
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

