
# Bernoulli trials

> Let suppose we are doing an [[Random events#Experiment|experiment]]. In this experiment the *probability*, that event **A** will occur is equal to *$P(A)=p$*, and *probability*, that opposite event *$A^-$* will occur is equal to *$q = 1-p$*

> [!definition] 
> **Bernoulli trials** are [[Random events#Trial|trials]], which satisfy these requests:
> 1. In each [[Random events#Trial|trial]] two events can occur: event *$A$*, or opposite event *$A^-$*;
> 2. Trials are [[Conditional probability#Independent events|independent]].
> 3. the *probability* of event **A** in all trials *is the same* and equal to $p=P(A)$. (The probability of opposite event is $q=P(A^-)=1-p$

--- 
<br>

# Bernoulli formula 

> [!definition] 
> Let suppose we are doing n independent [[Random events#Trial|trials]], where each trial consists only of *two possible outcomes* *$A$* (success) and *$A^-$* (failure) with $p=P(A)$ and $q=p(A^-)=1-p$ where $0 < p < 1$. The probability $P_{n}(k)$ of the event which corresponds to **k** ’*successes*’ in **n** Bernoulli trials is $$P_{n}(k)=C_{n}^k*p^k*q^{n-k}$$ for $k=0,1,2,\dots,n$

> [!definition] 
> 
> Probability, that after **n** *independent Bernoulli trials*, the event **A** occurs between *$k_{1}$* and *$k_{2}$* times, *$k_{1} < k_{2}$*, is equal to $$P(k_{1}\leq k\leq k_{2})=\sum_{k=k_{1}}^{k_{2}}C_{n}^k*p^k*q^{n-k}$$

> [!definition] 
> Probability, that after **n** *independent Bernoulli trials*, the event **A** occurs <u>one or more times</u>, is equal to $$P(1\leq k\leq n)=1-q^n$$

--- 
<br>

# Maximum likelihood value of event A

> [!definition] 
> The value $k=k^*$, where *$P_{n}(k)$* takes the largest value is called **maximum likelihood value of event A**

> [!theorem] 
> The **maximum likelihood value** $k=k^*$ of event **A** in [[#Bernoulli trials]], where $0<p=P(A)<1$ is in this interval $$np-q\leq k^* \leq np + p$$

--- 
<br>

# Go to other topics
``` dataview
list from "uni/Probability theory"
```