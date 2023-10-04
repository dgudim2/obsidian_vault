
# Definitions

## Experiment

> [!definition] 
> **An experiment**
> 
> An *experiment* is any procedure that can be *infinitely repeated* and has a <u>well 
> defined set of outcomes</u>.

## Random experiment

> [!definition] 
> 
> A **random experiment**
> 
> A *random experiment* is an experiment that produces **random outcomes**.

## Trial

> [!definition] 
> 
> A **trial**
> 
> A **trial** is a single moment of a *random experiment*.
> > (If a die is thrown ten times, there would be ten trials. The key concept to note here is that each trial produces exactly one outcome.)

## Elementary event

> [!definition] 
> 
> An **elementary event**
> 
> **Elementary event** is a result of **experiment**, which *can not be split* in to separate events.

## Space of elementary events

> [!definition] 
> 
> **Space of elementary events**
> 
> A set of all elementary events $\Omega$ is called a <u>space of elementary events</u> or <u>sample space</u>.
> 
> > [!example] 
> > If a coin is tossed twice, then $\Omega = \{HH, HT, TH, TT\}$
> 
> A *subset* of the **sample space** will be called an **event**.
> > There are three types of events: **random** event, **certain** event and **impossible** event.


--- 
<br>


# Types of events

## Sum of events

> [!definition] 
> An event **C** is called a sum of the events **A** and **B** ($C=A \cup B$), if the event **C** happens _if and only if_ <u>either</u> the event **A** happens or the event **B** happens.

```latex
---
preset:default-tikz
width:450px
---

\begin{tikzpicture}

% Set A
\node [circle,
	fill=green,
	minimum size =3cm,
	label={135:$A$}] (A) at (0,0){};

% Set B
\node [circle,
	fill=green,
	minimum size =3cm,
	label={45:$B$}] (B) at (1.8,0){};

% Circles outline
\draw[black,thick] (0,0) circle(1.5cm);
\draw[black,thick] (1.8,0) circle(1.5cm);

% Union text label
\node[orange] at (0.9,1.8) {$A\cup B$};

```

> [!example] 
> 
> A dice is tossed. Let **$A=\{\text{4 occurred}\}$**, and **$A=\{\text{2 or 6 occurred}\}$**, then $C = \{\text{even number occured}\}$ is the <u>union</u> of event **A** and **B** ($C=A \cup B$)

Also see [[Graphs - operations#Union (OR/$ cup$)|Union in graphs]]

## Product (intersection) of events

> [!definition] 
> An event **C** is called a **product** of the events **A** and **B** ($C=A \cap B$), if the event **C** happens _if and only if_ <u>both</u> event **A** and event **B** happen.

```latex
---
preset:default-tikz
width:450px
---

\begin{tikzpicture}[thick,
	set/.style = {circle,
		minimum size = 3cm,
		fill=white}]

% Set A
\node [circle, label={135:$A$}] (A) at (0,0){};

% Set B
\node [circle, label={45:$B$}] (B) at (1.8,0){};

% Intersection
\begin{scope}
	\clip (0,0) circle(1.5cm);
	\clip (1.8,0) circle(1.5cm);
	\fill[green](0,0) circle(1.5cm);
\end{scope}

% Circles outline
\draw (0,0) circle(1.5cm);
\draw (1.8,0) circle(1.5cm);

% Set intersection label
\node at (0.9,0) {$A\cap B$};

```

> [!example] 
> 
> A dice is tossed. Let **$A=\{\text{even number occured}\}$**, and **$B=\{\text{less then 3 occured}\}$**, then $C=\{\text{2 occured}\}$ is the <u>intersection</u> of event **A** and **B** ($C=A \cap B$)

Also see: [[Graphs - operations#Intersection (AND/$ cap$)|Intersection in graphs]]

## Difference of events

> [!definition] 
> An event **C** is called a **difference** of the events **A** and **B** ($C=A-B=A \setminus B$), if the event **C** happens *if and only if* the event **A** <u>happens</u> and event **B** <u>doesn’t</u>.

```latex
---
preset:default-tikz
width:450px
---

\begin{tikzpicture}[thick,
	set/.style = { circle, minimum size = 3cm}]

% Set A
\node[set,fill=green,label={135:$A$}] (A) at (0,0) {};

% Set B
\node[set,fill=white,label={45:$B$}] (B) at (0:2) {};

% Circles outline
\draw (0,0) circle(1.5cm);
\draw (2,0) circle(1.5cm);

% Difference text label
\node[left,black] at (A.center){$A-B$};

```

> [!example]
>  
> A dice is tossed. Let **$A=\{\text{even number occured}\}$**, and **$B=\{\text{less then 3 occured}\}$**, then $C=\{\text{4 or 6 occured}\}$ is the <u>difference</u> of event **A** and **B** ($C=A \setminus B$).

## Complementary event

> [!definition] 
> An event **$A^-$** is called a **complementary event** to event **A**, if the event **A** doesn't occur

## Mutually exclusive event

> [!definition] 
> Events **A** and **B** are called **mutually exclusive** ($A \cap B=\emptyset$), if their <u>simultaneous occurrence is impossible</u>.

## Equally likely events

> [!definition] 
> If *several events* can happen as a *result of an experiment*, and each of them <u>isn’t more possible</u> than others according to objective conditions, then such events are called **equally likely** events.

> [!example] 
> An appearance of any number from **1** to **6** at throwing of a dice


--- 
<br>

# Permutation, arrangement and combination

- If the <u>order <b>doesn’t</b> matter</u>, it is a **Combination**
- If the <u>order <b>does</b> matter</u> it is a **Permutation** or **Arrangement**

## Permutation

- Order **does** matter
- **All** elements are used

> **No** repetition: $P_{n}=n!$
> **With** repetition: $P(n_{1},n_{2},\dots,n_{k})=\frac{n!}{n_{1}!n_{2}!\dots n_{k}!}$

## Arrangement

- Order **does** matter
- **Some** elements are used

> **No** repetition: $A_{n}^k=\frac{n!}{(n-k)!}$
> **With** repetition: $\overline{A_{n}^k} = n^k$

## Combination

- Order **doesn't** matter
- **Some** elements are used

> **No** repetition: $C_{n}^k=\frac{n!}{(n-k)!\ *\ k!}$
> **with** repetition: $\overline{C_{n}^k}=C_{n}^{k+n-1}$



# Go to other topics
``` dataview
list from "uni/Probability theory"
```