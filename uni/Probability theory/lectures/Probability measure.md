
# Probability measure

> [!definition] 
> 
> A **probabilistic model** is a <u>mathematical description of an uncertain situation</u>. 
> 
> The elements of a Probabilistic Model are:
> - A sample space **$\Omega$**, which is the *set of all possible outcomes* of an experiment.
> - a probability law (or probability measure), which assigns to a set **A** of possible outcomes (an event) a non-negative number **P(A)** (called the *probability* of **A**) that encodes our knowledge or belief about the collective "*likelihood*" of the elements of **A**

> [!definition] 
> 
> The *probability measure* **P** is a function, that assigns a probability between **0** and **1** to *each event*
> It must satisfy the probability axioms:
> - $P(\Omega)=1$
> - $P(A)\geq 0$
> - For each *pair* of [[Random events#Mutually exclusive event|mutually exclusive]] events **A**, $B \subset \Omega$ the equality $P(A \cup B) = P(A) + P(B)$

## Basic properties

- Let a space of [[Random events#Elementary event|elementary events]] $\Omega$ be given and *probabilities* **P** are defined on events of $\Omega$
- Then:
	1. $P(\emptyset)=0$
	2. If $A \subset B \subset \Omega$, then $P(A)\leq P(B)$
	3. For each $A \subset \Omega$ the inequality $P(A)\leq 1$ takes place;
	4. For each $A \subset \Omega$ the equality $P(\overline{A})=1-P(A)$ takes place.

--- 
<br>
# Definitions of probability

## Statistical definition of probability

> [!definition] 
> 
> If [[Random events#Trial|trials]] are to be repeated a *great number of times* essentially *under the same conditions*, then the limit of the ratio of the number of times an event **A** happens to the *total number of trials* is called **the probability** of the event **A**
> 
> $$P_{n}(A)=\frac{n(A)}{N}\to P(A), \text{when } n\to \infty$$
> 
> It follows that if we have the data from **n runs** of the experiment, the observed *relative frequency* **$P_{n}(A)$** can be used as <u>an approximation</u> for **$P(A)$**. This <u>approximation</u> is called the **statistical definition of probability**.

## Classical definition of probability

> [!definition] 
> 
> Let a space of [[Random events#Elementary event|elementary events]] $\Omega$ be given and this space consists
> of **N** *equally likely* [[Random events#Elementary event|elementary events]], among which there are **n** events, *favorable* for an event **A**. Then the number
> $$P(A)=\frac{n}{N}$$
> is called the **probability of an event A**.

## Geometric definition of probability

> [!definition] 
> We have geometric figure $\Omega$. Geometric figure **A** is a subset of $\Omega$.
> $P(\text{the point is in the figure A})=\frac{\text{area of figure A}}{\text{area of figure } \Omega}$

```latex
---
preset:default-tikz
width:450px
---

\begin{tikzpicture}[thick]

\node [label={135:$A$}] at (3,1.5){};

\draw (0,0) rectangle ++(7,3);
\draw (3,1.5) circle(1.1cm);

```

--- 
<br>

# Inclusion-exclusion principle

> [!definition] 
> 
> The **inclusion-exclusion** principle tells us how to keep track of *what to add* and
> *what to subtract* in the problems where events intersect:
> - Let **S** be a *finite set*, and suppose there is a list of **r** *properties* that every element of **S** may or may not have. We call **$S_{1}$** the <u>subset of elements</u> of **S** that have property **1**; **$S_{1,2}$** the <u>subset of elements</u> in **S** that have properties **1** and **2**, etc. Notice that **$\cup S_{i}$** is the <u>subset of elements</u> of **S** that have *at least one* of the **r** properties.
> 
> $$\mid \cup S_{i} \mid=\sum_{i=1}^{r}\mid S_{i}\mid-\sum_{1\leq i\leq j\leq r}\mid S_{i,j} \mid + \sum_{1\leq i\leq j\leq k\leq r}\mid S_{i,j,k} \mid - \dots + (-1)^{r-1}\mid S_{i,j,k,\dots,r} \mid$$

> [!example] 
> **$r=4$**
> 
> $$
> \begin{matrix}
> \mid \cup S_{i}=\mid S_{1} \mid + \mid S_{2} \mid + \mid S_{3} \mid + \mid S_{4} \mid - \mid S_{1,2} \mid - \mid S_{1,3} \mid - \mid S_{1,4} \mid -  \\
\mid S_{2,3} \mid - \mid S_{2,4} \mid - \mid S_{3,4} \mid + \mid S_{1,2,3} \mid + \mid S_{1,2,4} \mid + \mid S_{1,3,4} \mid + \mid S_{2,3,4} \mid - \mid S_{1,2,3,4} \mid
> \end{matrix}
> $$

## Inclusion-exclusion for probabilities

- $P(A \cup B)=P(A)+P(B)-P(A \cap B)$
- $P(A \cup B \cup C) = P(A) + P(B) + P(C) - P(A \cap B) - P(A \cap C) - P(B \cap C) + P(A \cap B \cap C)$

--- 
<br>

# Go to other topics
``` dataview
list from "uni/Probability theory"
```