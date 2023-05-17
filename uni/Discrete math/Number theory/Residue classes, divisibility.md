# Residue classes

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
> 2. If *${\color{green}a}{\color{blue}c} \equiv {\color{orange}b}{\color{blue}c}(mod\ \ {\color{red}n})$*, and *$gcd({\color{blue}c};{\color{red}n})=1$* (See [[Divisors, multiples, primes#Greatest common divisor|GCD]]), then *${\color{green}a} \equiv {\color{orange}b}(mod\ \ {\color{red}n})$*
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

# Equations and systems

