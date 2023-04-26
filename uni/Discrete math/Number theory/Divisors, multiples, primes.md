
> [!definition] 
> Integer *$a$* is a **multiple** of some integer *$b$* if we can write *$a=bm$*
> Non-zero integer *$b$* **divides** integer *$a$* or *$b|a$* (there are several notions), if number *$a$* is it’s **multiple**. Then number *$b$* is called a **divisor** of number *$a$*

# Theorems

## Multiples

> [!theorem] 
> If *$a$*, *$b$* and *$c$* are integers, then
> - For all *$a$*: *$a|a$*
> - For all *$a$*, *$b$* and *$c$*: if *$a|b$* and *$b|c$*, then *$a|c$*
> - For all *$a$*, *$b$* and *$c$*: *$b|a$* and *$b|c$* <u>then and only then</u> if with any integer numbers *$m$* and *$n$* _$b|(m*a+n*c)$_

> [!theorem] 
> - If *$a$* and *$b$* are natural numbers and *$a|b$*, then *$a \leq b$*
> - If *$a$* and *$b$* are natural numbers, *$a|b$* and *$b|a$*, then *$a=b$* or *$a=-b$*

## Division with remainder

> [!theorem] 
> 
> If *$a$* and *$b$* are natural numbers, then <u>there is only one pair of natural numbers</u> *$q$* and *$r$*, where *$0\leq r < b$*, such that *$a=bq+r$*

## Greatest common divisor

> [!theorem] 
> - Natural number *$d$* is **common divisor** of numbers *$a$* and *$b$*, if *$d|a$* and *$d|b$*
> - Natural number *$d$* is called *greatest* **common divisor** of numbers *$a$* and *$b$*, if
> 	- It is a **common divisor** and 
> 	- if $c|a$ and $c|b$, then $c|d$ (*$c$* - any **common divisor**) (should be divisible by all other common divisors)
> - $ Notion $gcd(a;b)$ 
> 
> > [!example] 
> > *Common* divisors of **1540** and **294** are *1*, *2*, *7*, *14*
> > $gcd(1540,294)=14$ (2|14, 7|14)

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

### UV form

> [!theorem] 
> 