
# Conditional probability

> [!definition] 
> 
> - Suppose we assign a *sample space* to an [[Random events#Experiment|experiment]] and then learn that an event **B** has occurred.
> - We shall call the new probability for an event **A** the *conditional probability* of **A** given **B** and denote it by **$P(A\mid B)$**.
> - $P(A\mid B)=\frac{P(A\cap B)}{P(B)}$ where $A,B \subset \Omega, P(B)\neq 0$

--- 
<br>

# Multiplication of probabilities

> From the [[#Conditional probability|conditional probability]] formula we can get the following formulas

- **2 events**: $P(A\cap B)=P(A\mid B) * P(B)$
- **3 events**: $P(A\cap B\cap C)=P(A)*P(B\mid A)*P(C\mid A\cap B)$

--- 
<br>

# Independent events

> [!info]
> 
> - It often happens that the knowledge that a certain event **A** has occurred has *no effect on the probability* that some other event **B** has occurred, that is, that **$P(A\mid B)=P(A)$**.
> - One would expect that in this case, the equation **$P(B\mid A)=P(B)$** would also be true. In fact, each equation implies the other.
> - If these equations are true, we might say the **A** is *independent* of **B**

> [!definition] 
> Two events **A** and **B** are **independent** if *both* **A** and **B** have *positive probability* and if
> - $P(A\mid B)=P(A)$
> - $P(B\mid A)=P(B)$

> [!theorem] 
> If $P(A)>0$ and $P(B)>0$, then **A** and **B** are *independent if and only if*
> $$P(A\cap B)=P(A)*P(B)$$

## Mutually independent events

> [!definition] 
> If events $\{A_{1},A_{2},\dots ,A_{n}\}$ are **mutually independent**, then for any subset $\{A_{i},A_{j},\dots,A_{m}\}$ of these events we have $$P(A_{i}\cap A_{j}\cap\dots \cap A_{m})=P(A_{i})*P(A_{j})*\dots*P(A_{m})$$

> [!note] 
> It is important to note that the upper-mentioned formula statement *does not imply* that events *$A_{1},A_{2},\dots,A_{n}$* are **mutually independent**

--- 
<br>

# Go to other topics
``` dataview
list from "uni/Probability theory"
```