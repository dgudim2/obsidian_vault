# Residue classes

> [!definition] 
> 
> Take some natural number *$n$*. For any integer *$k$* we can define a set $$\bar{k}=\{a+nt\ |\ t \in Z\}$$ which consist of integers *$a$* such that *$a \equiv k(mod\ n)$*
> This set is called the **residual class** by module *$n$*
> 
> > [!note] 
> > If *$a \in \bar{k}$*, then *$\bar{a}=\bar{k}$*

## Set of residue classes

> [!definition] 
> Set of all **residue classes** by module *$n$* is denoted by *$Z_{n}$*

> [!note] 
> 
> Operations are similar to residue classes
> 1. $\bar{k}+\bar{l}:=\overline{k + l}$
> 2. $\bar{k}*\bar{l}:=\overline{k * l}$

> [!example] 
> 
> Take **residue class** by module *5*: *$Z_{5}=\{\bar{0}, \bar{1}, \bar{2}, \bar{3}, \bar{4}\}$*
> Then
> 
> $$\bar{3}+\bar{4}:=\overline{3 + 4}=\bar{7}=\bar{2}$$
> $$\bar{3}*\bar{4}:=\overline{3 * 4}=\overline{12}=\bar{2}$$

## Set U of coprimes

> [!definition] 
> 
> $U_{n}=\{\bar{a} \in Z_{n}\ |\ gcd(a;n)=1 \}$
> Element $\bar{a}, a \in \{1, 2, 3, \dots, n-1\}$, is in set *$U_{n}$* if and only if *$gcd(a;n)=1$* (*$a$* and *$n$* are [[Divisors, multiples, primes#Coprime numbers|coprime]])
> So set *$U_{n}$* has *$\varphi (n)$* elements (See: [[#Euler's function]])

> [!example] 
> Set $U_{10}=\{\bar{1}, \bar{3}, \bar{7}, \bar{9}\}$ has *$\varphi (10)=4$* elements

## Properties

1. **Associativity**. For all *$\bar{a}, \bar{b}, \bar{c} \in U_{n}$*$$\bar{a}*(\bar{b} * \bar{c}) = (\bar{a}*\bar{b}) * \bar{c}$$
2. **Neutral** element. $\bar{a}*\bar{1}=\bar{a}$
3. **Inverse** element. Let $\bar{a} \in U_{n}$. Since $gcd(a;n)=1$, then exists such $x, y \in Z$, that $$ax+ny=1$$ Then $gcd(x;n)=1$, so *$\bar{x} \in U_{n}$* and $$\bar{a} * \bar{x} = \overline{ax} = \overline{ax + ny} = \bar{1}$$ So we get that *$\bar{a}^{-1}=\bar{x}$*

## Misc

> [!definition] 
> If *$a, b, n$* - natural numbers, and *$n$* divides *$(a-b)$* (*$a/n$* and *$b/n$* have the same **remainder**)
> Then we can write *$a \equiv b(mod\ \ n)$*
> 
> > [!example] 
> > *$10 \equiv 1(mod\ \ 3)$*, since *$3|(10-1)$*
> > *$14 \equiv 4(mod\ \ 5)$*, since *$5|(14-4)$*

> [!theorem] 
> Relation *$\equiv$* is **equivalence** relation (reflective, symmetric and transitive)
> 1. For all integers *$a$* it is true that *$a \equiv a(mod\ \ n)$*
> 2. For all integers *$a$* and *$b$* it is true that: if *$a \equiv b(mod\ \ n)$*, then *$b \equiv a(mod\ \ n)$*
> 3. For all integers *$a$*, *$b$* and *$c$* it is true that: if *$a \equiv b(mod\ \ n)$* and *$b \equiv c(mod\ \ n)$*, then *$a \equiv c(mod\ \ n)$*

> [!theorem] 
> 1. If *${\color{green}a} \equiv {\color{orange}b}(mod\ \ {\color{red}n})$* and *${\color{blue}c} \equiv {\color{yellow}d}(mod\ \ {\color{red}n})$*, 
> then *${\color{green}a}+{\color{blue}c} \equiv {\color{orange}b}+{\color{yellow}d}(mod\ \ {\color{red}n})$* and *${\color{green}a}{\color{blue}c} \equiv {\color{orange}b}{\color{yellow}d}(mod\ \ {\color{red}n})$* 
> 2. If *${\color{green}a}{\color{blue}c} \equiv {\color{orange}b}{\color{blue}c}(mod\ \ {\color{red}n})$*. (See [[Divisors, multiples, primes#Greatest common divisor|GCD]]), then *${\color{green}a} \equiv {\color{orange}b}\left( mod\ \ {\frac{\color{red}n}{gcd({\color{blue}c};{\color{red}n})}} \right)$*
> 
>> [!example] 
>> Let's *$n=3$*
>> Then *$1 \equiv 7(mod\ \ 3)$* and *$2 \equiv -4(mod\ \ 3)$*
>> From the theorem we have
>> - $1+2 \equiv 7-4(mod\ \ 3)$, so $3 \equiv 3(mod\ \ 3)$ and $3|(3-3)$
>> - $1*2 \equiv 7*(-4)(mod\ \ 3)$, so $2 \equiv -28(mod\ \ 3)$ and $3|(2+28)$

> [!theorem] 
> 
> 1. If ${\color{green}a} \equiv {\color{orange}b}(mod\ \ {\color{red}n})$, then ${\color{green}a}^{\color{cyan}m} \equiv {\color{orange}b}^{\color{cyan}m}(mod\ \ {\color{red}n})$ for any natural ${\color{cyan}m}$
> 2. If ${\color{green}a} \equiv {\color{orange}b}(mod\ \ {\color{cyan}m}{\color{red}n})$, then ${\color{green}a} \equiv {\color{orange}b}(mod\ \ {\color{red}n})$ and ${\color{green}a} \equiv {\color{orange}b}(mod\ \ {\color{cyan}m})$
> 
>> [!example] 
>> Let's take *$7 \equiv 1(mod\ \ 6)$* and *$m=3$*
>> From the theorem we have
>> - $7^3=1^3(mod\ \ 6)$

--- 
<br>

# Equations and systems

## $ax \equiv b(mod\ n)$

1. If $gcd(a;b;n)\neq 1$, simplify
2. If $gcd(a;n)>1$ (after simplification), then there are no solutions
3. If $gcd(a;n)=1$, [[Divisors, multiples, primes#ax+by=c equation|Solve]] and find *$u$* and *$v$* from *$au+nv=1$*
4. Then _$x=bu(mod\ n)$_

> [!example] 
> *$72x \equiv 68(mod\ 108)$*
> 1. $gcd(72;68;108)=4$, simplify: $18x \equiv 17(mod\ 27)$
> 2. $gcd(18;27)=9>1$, no solutions

> [!example] 
> *$71x \equiv 3(mod\ 91)$*
> 1. $gcd(71;91)=1$, [[Divisors, multiples, primes#ax+by=c equation|find]] *$u$* and *$v$*, such that *$71u+91v=1$*
> 2. *$u=-41$*
> 3. $x \equiv 3*(-41)(mod\ 91)$, so _$x \equiv 59(mod\ 91)$_

## System of $ax \equiv b(mod\ n)$

$$\begin{cases} a_{1}x \equiv b_{1}(mod\ {\color{red}m_{1}}) \\ a_{2}x \equiv b_{2}(mod\ {\color{orange}m_{2}}) \end{cases}, gcd({\color{red}m_{1}};{\color{orange}m_{2}})=1, gcd(a_{i};b_{i};m_{i})=1$$

1. If any equation has no solution, then system also has no solution.
2. [[#$ax equiv b(mod n)$|Solve]] all equations separately: $\begin{cases} x \equiv {\color{cyan}a}(mod\ {\color{red}m_{1}}) \\ x \equiv {\color{purple}b}(mod\ {\color{orange}m_{2}}) \end{cases}$ 
3. Find ${\color{green}u}$ and ${\color{blue}v}$, such that ${\color{red}m_{1}}{\color{green}u}+{\color{orange}m_{2}}{\color{blue}v}=1$. 
	- Then $x_{0}={\color{red}m_{1}}{\color{green}u}{\color{purple}b}+{\color{orange}m_{2}}{\color{blue}v}{\color{cyan}a}$
	- And $\ \ \ \ x \equiv {\color{red}m_{1}}{\color{green}u}{\color{purple}b}+{\color{orange}m_{2}}{\color{blue}v}{\color{cyan}a}(mod\ {\color{red}m_{1}}{\color{orange}m_{2}})$

> [!example] 
> 
> 1. $$\begin{cases}
> \ \ 14x \equiv 5(mod\ 71) \\
> 17x \equiv 12(mod\ 91) 
> \end{cases}\ \ {\huge\xrightarrow[\text{separately}]{\text{solve}}}\ \begin{cases}
> x \equiv 46(mod\ 71) \\
> x \equiv 81(mod\ 91)
> \end{cases}$$
> 2. [[Divisors, multiples, primes#ax+by=c equation|Solve]] $71*u+91v=1$. $u=-41$, $v=32$
> 3. $x \equiv 71*(-41)*81+91*32*46(mod\ 71*91) \equiv \color{yellow}1537(mod\ 71*91)$

## $a^b(mod\ n)$

1. Write *$b$* in binary $$b=\alpha_{0}+\alpha_{1}2^1+\alpha_{2}2^2+\dots+\alpha_{k}2^k$$
2. Find remainders when dividing *$\large a^{2^j}$* by *$n$*. Use formula $$\large a^{2^{j+1}} \equiv (a^{2^j})^2(mod\ n)$$

> [!example] 
> *$7^{39}(mod\ 41)$*
> - $39=1+2^1+2^2+2^5$
> - $r_{0}=7$
> - $r_{1}=8$
> - $r_{2}=23$
> - $r_{5}=10$
> $7^{39}(mod\ 41) \equiv 7*8*23*10 \equiv 6(mod\ 41)$

> [!seealso]
> [[#Properties of Euler's function]]

--- 
<br>

# Indicators of divisibility

1. Write number *$N$* in form $$N=\alpha_{0}+\alpha_{1}*10^1+\alpha_{2}*10^2+\dots+\alpha_{k}*10^k$$
2. Find remainders *$r_{j}$* of division of *$10^j$* by *$n$*
3. Then $$N \equiv \alpha_{0}+\alpha_{1}*r_{1}+\alpha_{2}*r_{2}+\dots+\alpha_{k}*r_{k}(mod\ n)$$
**Sequence of remainders is periodical**

> [!example] 
> Division by **2**
> 
> Find remainders
> 1. $r_{0}=1$
> 2. $r_{1}=0$
> 3. $r_{2}=0$
> Only first remainder is nonzero, so *$N \equiv \alpha_{0}(mod\ 2)$*, it means that remainder obtained by division of *$N$* by **2** is equal to remainder of division of last digit of *$N$* by **2**

> [!example] 
> Division by **3**
> 
> 1. $r_{0}=1$
> 2. $r_{1}=1$
> 3. $r_{2}=1$
> Remainders are repeating, so *$N \equiv \alpha_{0}+\alpha_{1}+\alpha_{2}+\dots+\alpha_{k}(mod\ 3)$*, so the number itself and *sum of it’s digits* have same remainders if divided by **3**


# Euler's function

> [!definition] 
> **Euler's function** shows how many natural numbers are prime with *$n$* and less than *$n$*. If the prime divisors of the number *$n$* are *$p_{1}, p_{2},\dots,p_{k}$*, then $$\varphi(n)=n*\left( 1-\frac{1}{p_{1}} \right)*\left( 1-\frac{1}{p_{2}} \right)*\dots*\left( 1-\frac{1}{p_{k}} \right)$$

> [!example] 
> Find *$\varphi(25)$*
> $25=5^2$, so $p_{1}=5$ and $\varphi(25)=25*\left( 1-\frac{1}{5} \right) = 20$

## Properties of Euler's function

1. If $gcd(m;n)=1$, then $\varphi(mn)=\varphi(m)*\varphi(n)$
2. If $p$ - prime number, then $\varphi=p-1$
3. If $gcd(a;n)=1$, then $a^{\varphi(n)} \equiv 1(mod\ n)$

> [!example] 
> 
> $\varphi(21)=\varphi(3*7)=\varphi(3)*\varphi(7)=2*6=12$

> [!example] 
> 
> - $3^{203}(mod\ 17) \equiv 3^{16*12+11}(mod\ 17) \equiv 3^{11}(mod\ 17) \equiv 7(mod\ 17)$
>	- $gcd(3;17)=1$
>	- $\upvarphi(17)=16$
>	- $203=16*12+11$

--- 
<br>

# Go to other topics
``` dataview
list from "uni/Discrete math"
```