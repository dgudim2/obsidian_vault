

# Shortest path problem

> In graph theory, the *shortest [[Graphs - connectivity#Path|path]] problem* is the problem of finding a path between two [[Graphs - basics#Directed graphs|vertices]] in a graph such that the *sum of the weights* of its [[Graphs - basics#Undirected graph|edges]] *is minimized*

> [!note] 
> The problem of finding the shortest path between two *intersections* <u>on a road map</u> may be modeled as <u>a special case of the shortest path problem</u> in graphs, where the [[Graphs - basics#Directed graphs|vertices]] correspond to *intersections* and the [[Graphs - basics#Undirected graph|edges]] correspond to *road segments*, <u>each weighted by the length of the segment</u>

## Variations

### Single-source

- We have to find **shortest** [[Graphs - connectivity#Path|paths]] from a *source* [[Graphs - basics#Directed graphs|vertex]] to *all other* [[Graphs - basics#Directed graphs|vertices]] in the graph

> [!note] 
> Applicable algorithms:
> 1. [[#Algorithms|Dijkstra's algorithm]]
> 2. [[#Algorithms|Bellman–Ford algorithm]]

### Single-destination

- We have to find <u>shortest paths</u> from *all vertices* in the [[Graphs - basics#Directed graphs|directed graphs]] graph to a *single destination* vertex. This can be reduced to the **single-source** shortest path problem by reversing the arcs in the directed graph

### All-pairs

- We have to find <u>shortest paths</u> between *every pair of [[Graphs - basics#Directed graphs|vertices]]* in the graph

> [!note] 
> Applicable algorithms:
> 1. [[#Algorithms|Floyd–Warshall algorithm]]
> 2. [[#Algorithms|Johnson's algorithm]]
> 1. [[#Algorithms|A* search algorithm]] ([[#All-pairs|single-pair]])

## Algorithms

1. [[#Dijkstra's algorithm]] - solves the [[#Single-source|single-source]] problem with <u>non-negative edge weight</u>
2. **Bellman–Ford algorithm** - solves the [[#Single-source|single-source]] problem if edge <u>weights may be negative</u>
3. __A* search algorithm__ - solves for [[#All-pairs|single-pair]] shortest path using heuristics to try to speed up the search
4. [[#Floyd-Warshall algorithm]] - solves [[#All-pairs|all-pairs]] shortest paths
5. **Johnson's algorithm** - solves [[#All-pairs|all-pairs]] shortest paths, and may be faster than *Floyd–Warshall* on sparse graphs
6. **Viterbi algorithm** - the shortest stochastic path problem with an additional probabilistic weight on each node 

> [!note] 
>  - <u>The Stochastic Shortest Path problem</u> generalizes the classic deterministic shortest path problem

### Dijkstra's algorithm

1. Mark all nodes <u>unvisited</u>. Put them in a set, we will call it *the unvisited set*
2. Assign a tentative [[Graphs - metrics#Distances|distance]] value to every node: 
	- **Zero** for our *initial(starting) node* and 
	- **Infinity** for all other nodes
	- Set the initial node as current

> [!note]  
> - The *tentative distance *of a node is the [[Graphs - connectivity#Length of the path|length]] of the shortest path discovered so far between the node and the starting node
> - Since initially no path is known to any other [[Graphs - basics#Directed graphs|vertex]] other than the source itself, all other *tentative distances* are set to *infinity*

3. For the current node, consider all of its <u>unvisited</u> [[Graphs - basics#Properties|neighbors]] and *calculate their tentative distances through the current node*. Compare the newly calculated tentative distance to the current assigned value and *assign the smallest one* 
4. Mark the current node as <u>visited</u> and remove it from the *unvisited set*. (A visited node will never be checked again)
5. If the *destination* node has been marked visited or if the smallest tentative [[Graphs - metrics#Distances|distance]] among the nodes in the <u>unvisited set</u> is infinity (no connection between the *initial* node and *remaining* unvisited nodes), then stop. 
6. Otherwise, select the <u>unvisited</u> node that is marked with the smallest tentative [[Graphs - metrics#Distances|distance]], set it as the new current node, and go back to step 3.

#### Example

> Find shortest path between *A* and *F*

1. Assign a tentative [[Graphs - metrics#Distances|distance]] value to every node
```dot 
---
ellipse-fill:keep-shade
---
graph neato { 

bgcolor="transparent" 

rankdir="LR"

graph [layout = dot, ranksep=1,nodesep=.5] 

subgraph cluster_space {
	graph [peripheries=0]
	
	node [shape = circle, 
	      style = filled, 
	      width=0.45, 
	      height=0.45, 
	      color=yellow, 
	      fillcolor=snow, 
	      fontcolor=ghostwhite,
	      fixedsize=true,
	      penwidth=3]  
	
	a [label="\n\n\nA(0,0)",color=orange]
	b [label="\n\n\nB(∞,0)"]
	c [label="\n\n\nC(∞,0)"]
	d [label="D(∞,0)\n\n\n\n"]
	e [label="E(∞,0)\n\n\n\n"]
	f [label="\n\n\nF(∞,0)",color=red]
	
	edge [color=lightgray, fontcolor=lightgray]  
	
	a -- b [label="5",weight=5]
	a -- c [label="6",weight=6]
	b -- e [label="2",weight=2]
	b -- f [label="10",weight=10]
	b -- c [label="3",weight=3]
	b -- d [label="7",weight=7]
	c -- e [label="7",weight=7]
	d -- f [label="2",weight=2]
	f -- e [label="4",weight=4]
	
	}
} 
```

2. Consider neighbours of *A*. 
	- Assign **(5,A)** to *B*
	- Assign **(6,A)** to *C*
	- *B* has the smallest tentative [[Graphs - metrics#Distances|distance]], we will work with *B* next
```dot 
---
ellipse-fill:keep-shade
---
graph neato { 

bgcolor="transparent" 

rankdir="LR"

graph [layout = dot, ranksep=1,nodesep=.5] 

subgraph cluster_space {
	graph [peripheries=0]
	
	node [shape = circle, 
	      style = filled, 
	      width=0.45, 
	      height=0.45, 
	      color=yellow, 
	      fillcolor=snow, 
	      fontcolor=ghostwhite,
	      fixedsize=true,
	      penwidth=3] 
	
	a [label="\n\n\nA(0,0)",color=darkcyan,fontcolor=blue]
	b [label="\n\n\nB(5,A)",fontcolor=red]
	c [label="\n\n\nC(6,A)",fontcolor=red]
	d [label="D(∞,0)\n\n\n\n"]
	e [label="E(∞,0)\n\n\n\n"]
	f [label="\n\n\nF(∞,0)",color=red]
	
	edge [color=lightgray, fontcolor=lightgray] 
	
	a -- b [label="5",weight=5,color=darkred,penwidth=2]
	a -- c [label="6",weight=6,color=darkred,penwidth=2]
	b -- e [label="2",weight=2]
	b -- f [label="10",weight=10]
	b -- c [label="3",weight=3]
	b -- d [label="7",weight=7]
	c -- e [label="7",weight=7]
	d -- f [label="2",weight=2]
	f -- e [label="4",weight=4]
	
	}
} 
```

3. Consider neighbors of *B*. 
	- Assign **(12,B)** to *D*
	- Assign **(7,B)** to *E*
	- Assign **(15,B)** to *F*
	- *C* already has a smaller tentative weight, don't assign **(8,B)**. 
	- *C* has the smallest tentative [[Graphs - metrics#Distances|distance]], we will work with *C* next
```dot 
---
ellipse-fill:keep-shade
---
graph neato { 

bgcolor="transparent" 

rankdir="LR"

graph [layout = dot, ranksep=1,nodesep=.5] 

subgraph cluster_space {
	graph [peripheries=0]
	
	node [shape = circle, 
	      style = filled, 
	      width=0.45, 
	      height=0.45, 
	      color=yellow, 
	      fillcolor=snow, 
	      fontcolor=ghostwhite,
	      fixedsize=true,
	      penwidth=3]
	
	a [label="\n\n\nA(0,0)",color=orange,fontcolor=blue]
	b [label="\n\n\nB(5,A)",color=darkcyan,fontcolor=blue]
	c [label="\n\n\n    C(6,A)",xlabel=<<font color="red">(8,B)</font>>]
	d [label="D(12,B)\n\n\n\n",fontcolor=red]
	e [label="E(7,B)\n\n\n\n",fontcolor=red]
	f [label="\n\n\nF(15,B)",color=red,fontcolor=red]
	
	edge [color=lightgray, fontcolor=lightgray] 
	
	a -- b [label="5",weight=5,color=green,penwidth=2]
	a -- c [label="6",weight=6]
	b -- e [label="2",weight=2,color=darkred,penwidth=2]
	b -- f [label="10",weight=10,color=darkred,penwidth=2]
	b -- c [label="3",weight=3,color=darkred,penwidth=2]
	b -- d [label="7",weight=7,color=darkred,penwidth=2]
	c -- e [label="7",weight=7]
	d -- f [label="2",weight=2]
	f -- e [label="4",weight=4]
	
	}
} 
```

4. Consider neighbors of *C*. 
	- We don't calculate tentative distance to *B*, because it is <u>visited</u>. 
	- *E* already has a smaller tentative weight, don't assign **(13,C)**. 
	- *E* has the smallest tentative [[Graphs - metrics#Distances|distance]], we will work with *E* next
```dot 
---
ellipse-fill:keep-shade
---
graph neato { 

bgcolor="transparent" 

rankdir="LR"

graph [layout = dot, ranksep=1,nodesep=.5] 

subgraph cluster_space {
	graph [peripheries=0]
	
	node [shape = circle, 
	      style = filled, 
	      width=0.45, 
	      height=0.45, 
	      color=yellow, 
	      fillcolor=snow, 
	      fontcolor=ghostwhite,
	      fixedsize=true,
	      penwidth=3] 
	
	a [label="\n\n\nA(0,0)",color=orange,fontcolor=blue]
	b [label="\n\n\nB(5,A)",color=purple,fontcolor=blue]
	c [label="\n\n\nC(6,A)",color=darkcyan,fontcolor=blue]
	d [label="D(12,B)\n\n\n\n"]
	e [label="E(7,B)\n\n\n\n",xlabel=<<font color="red">(13,C)</font>>]
	f [label="\n\n\nF(15,B)",color=red]
	
	edge [color=lightgray, fontcolor=lightgray]  
	
	a -- b [label="5",weight=5]
	a -- c [label="6",weight=6,color=green,penwidth=2]
	b -- e [label="2",weight=2]
	b -- f [label="10",weight=10]
	b -- c [label="3",weight=3]
	b -- d [label="7",weight=7]
	c -- e [label="7",weight=7,color=darkred,penwidth=2]
	d -- f [label="2",weight=2]
	f -- e [label="4",weight=4]
	
	}
} 
```

5. Consider neighbors of *E*. 
	- We don't calculate tentative distance to *B* and *C*, because they are <u>visited</u>.
	- Assign **(11,E)** to *F* (smaller tentative [[Graphs - metrics#Distances|distance]])
```dot 
---
ellipse-fill:keep-shade
---
graph neato { 

bgcolor="transparent" 

rankdir="LR"

graph [layout = dot, ranksep=1,nodesep=.5] 

subgraph cluster_space {
	graph [peripheries=0]
	
	node [shape = circle, 
	      style = filled, 
	      width=0.45, 
	      height=0.45, 
	      color=yellow, 
	      fillcolor=snow, 
	      fontcolor=ghostwhite,
	      fixedsize=true,
	      penwidth=3] 
	
	a [label="\n\n\nA(0,0)",color=orange,fontcolor=blue]
	b [label="\n\n\nB(5,A)",color=purple,fontcolor=blue]
	c [label="\n\n\nC(6,A)",color=purple,fontcolor=blue]
	d [label="D(12,B)\n\n\n\n"]
	e [label="E(7,B)\n\n\n\n",color=darkcyan,fontcolor=blue]
	f [label="\n\n\n      F(15,B)",color=red,xlabel=<<font color="red">(11,E)</font>>]
	
	edge [color=lightgray, fontcolor=lightgray] 
	
	a -- b [label="5",weight=5,color=lightgreen,penwidth=2]
	a -- c [label="6",weight=6]
	b -- e [label="2",weight=2,color=lightgreen,penwidth=2]
	b -- f [label="10",weight=10]
	b -- c [label="3",weight=3]
	b -- d [label="7",weight=7]
	c -- e [label="7",weight=7]
	d -- f [label="2",weight=2]
	f -- e [label="4",weight=4,color=darkred,penwidth=2]
	
	}
} 
```

6. We have reached our destination *F*, stop
	- First element shows [[Graphs - metrics#Distances|distance]], second - **directions**
	- So, final directions are: $A \to B \to E \to F$
```dot 
---
ellipse-fill:keep-shade
---
graph neato { 

bgcolor="transparent" 

rankdir="LR"

graph [layout = dot, ranksep=1,nodesep=.5]

subgraph cluster_space {
	graph [peripheries=0] 
	
	node [shape = circle, 
	      style = filled, 
	      width=0.45, 
	      height=0.45, 
	      color=yellow, 
	      fillcolor=snow, 
	      fontcolor=ghostwhite,
	      fixedsize=true,
	      penwidth=3]
	
	a [label="\n\n\nA(0,0)",color=orange,fontcolor=blue]
	b [label="\n\n\nB(5,A)",color=purple,fontcolor=blue]
	c [label="\n\n\nC(6,A)",color=purple,fontcolor=blue]
	d [label="D(12,B)\n\n\n\n"]
	e [label="E(7,B)\n\n\n\n",color=purple,fontcolor=blue]
	f [label="\n\n\nF(11,E)",color=red,fontcolor=red]
	
	edge [color=lightgray, fontcolor=lightgray] 
	
	a -- b [label="5",weight=5,color=lightgreen,penwidth=3]
	a -- c [label="6",weight=6]
	b -- e [label="2",weight=2,color=lightgreen,penwidth=3]
	b -- f [label="10",weight=10]
	b -- c [label="3",weight=3]
	b -- d [label="7",weight=7]
	c -- e [label="7",weight=7]
	d -- f [label="2",weight=2]
	f -- e [label="4",weight=4,color=lightgreen,penwidth=3]
	
	}
} 
```

### Floyd-Warshall algorithm

```dot 
---
preset:math-graph
---
graph neato { 

bgcolor="transparent" 

rankdir="LR"

graph [layout = dot, ranksep=0.5,nodesep=.5] 

subgraph cluster_space {
	graph [peripheries=0]
	
	node [shape = circle, 
	      style = filled, 
	      width=0.45, 
	      height=0.45, 
	      color=yellow, 
	      fillcolor=snow, 
	      fontcolor=black,
	      fixedsize=true,
	      penwidth=1]  
	
	v1, v2, v3, v4, v5
	
	edge [color=lightgray, fontcolor=darkgray]  
	
	v1 -- v2 [label="2",weight=2]
	v1 -- v4 [label="3",weight=3]
	v2 -- v3 [label="4",weight=4]
	v3 -- v4 [label="6",weight=6]
	v3 -- v5 [label="2",weight=2]
	v2 -- v5 [label="1",weight=1]
	v4 -- v5 [label="3",weight=3]
	
	}
} 
```

1. Make matrices $D$ and $P$, $D$ stores [[Graphs - metrics#Distances|distances]], $P$ - information about *paths*
```latex
\newcommand\x{\times}

\begin{multicols}{2}

	\begin{equation*}
	  D= 
	  \left(\begin{array}{>{\columncolor{ForestGreen}}ccccc}
	    \rowcolor{ForestGreen}
	    0       & 2      & \infty & 3      & \infty \\
	    2       & 0      & 4      & \infty & 1      \\
	    \infty  & 4      & 0      & 6      & 2      \\
	    3       & \infty & 6      & 0      & 3      \\
	    \infty  & 1      & 2      & 3      & 0      \\
	  \end{array}\right)
	\end{equation*}\break

	\begin{equation*}
	  P= 
	  \left(\begin{array}{ccccc}
	    0 & 0 & 0 & 0 & 0 \\
	    0 & 0 & 0 & 0 & 0 \\
	    0 & 0 & 0 & 0 & 0 \\
	    0 & 0 & 0 & 0 & 0 \\
	    0 & 0 & 0 & 0 & 0 \\
	  \end{array}\right)
	\end{equation*}\break

\end{multicols}

```

2. Take every *row* and *column* sequentially. If *element* is <u>not</u> **0** or **$\infty$** we add them.
	- Take *smaller* element

3. $2 + \infty > 4$, so we *don't* change the element
```latex
\newcommand\x{\times}
\newcommand\y{\cellcolor{olive}}
\newcommand\z{\cellcolor{green!80!black}}

\begin{multicols}{2}

	
	\begin{equation*}
	  D= 
	  \left(\begin{array}{>{\columncolor{ForestGreen}}ccccc}
	    \rowcolor{ForestGreen}
	    0       & 2      & \z \infty & 3      & \infty \\
	    \z 2    & 0      & \y 4      & \infty & 1      \\
	    \infty  & 4      & 0         & 6      & 2      \\
	    3       & \infty & 6         & 0      & 3      \\
	    \infty  & 1      & 2         & 3      & 0      \\
	  \end{array}\right)
	\end{equation*}\break

	\begin{equation*}
	  P= 
	  \left(\begin{array}{ccccc}
	    0 & 0 & 0 & 0 & 0 \\
	    0 & 0 & 0 & 0 & 0 \\
	    0 & 0 & 0 & 0 & 0 \\
	    0 & 0 & 0 & 0 & 0 \\
	    0 & 0 & 0 & 0 & 0 \\
	  \end{array}\right)
	\end{equation*}\break

\end{multicols}

```

4. $2+3< \infty$, so we *change* the element to **5** and mark it in the matrix $P$
5. Same for 1 more element
```latex
\newcommand\x{\times}
\newcommand\y{\cellcolor{olive}}
\newcommand\yy{\cellcolor{yellow}}
\newcommand\z{\cellcolor{green!80!black}}
\newcommand\zz{\cellcolor{green!30!black}}

\begin{multicols}{2}

	
	\begin{equation*}
	  D= 
	  \left(\begin{array}{>{\columncolor{ForestGreen}}ccccc}
	    \rowcolor{ForestGreen}
	    0       & \zz 2 & \infty & \z 3  & \infty \\
	    \z 2    & 0     & 4      & \yy 5 & 1      \\
	    \infty  & 4     & 0      & 6     & 2      \\
	    \zz 3   & \y 5  & 6      & 0     & 3      \\
	    \infty  & 1     & 2      & 3     & 0      \\
	  \end{array}\right)
	\end{equation*}\break

	\begin{equation*}
	  P= 
	  \left(\begin{array}{ccccc}
	    0 & 0    & 0 &     0 & 0 \\
	    0 & 0    & 0 & \yy 1 & 0 \\
	    0 & 0    & 0 &     0 & 0 \\
	    0 & \y 1 & 0 &     0 & 0 \\
	    0 & 0    & 0 &     0 & 0 \\
	  \end{array}\right)
	\end{equation*}\break

\end{multicols}

```

6. Repeat for the next *row* and *column* 
```latex
\newcommand\x{\times}
\newcommand\y{\cellcolor{olive}}
\newcommand\yy{\cellcolor{yellow}}
\newcommand\z{\cellcolor{green!80!black}}
\newcommand\zz{\cellcolor{green!30!black}}

\newcommand\bl{\cellcolor{blue!80!black}}
\newcommand\blb{\cellcolor{blue!30!black}}

\begin{multicols}{2}

	
	\begin{equation*}
	  D= 
	  \left(\begin{array}{c>{\columncolor{ForestGreen}}cccc}
	     0      & \z  2     & 6      & 3     & \yy 3 \\
	    \rowcolor{ForestGreen}
	    \zz  2  &     0     & 4      & 5     & \z  1      \\
	    \y   6  & \zz 4     & 0      & 6     &     2      \\
	     3      &     5     & 6      & 0     &     3      \\
	     3      &     1     & 2      & 3     &     0      \\
	  \end{array}\right)
	\end{equation*}\break

	\begin{equation*}
	  P= 
	  \left(\begin{array}{ccccc}
	         0 &    0    & \bl 2 &     0 & \bl 2 \\
	         0 &    0    &     0 & \yy 1 &     0 \\
	    \blb 2 &    0    &     0 &     0 &     0 \\
	         0 & \y 1    &     0 &     0 &     0 \\
	    \blb 2 &    0    &     0 &     0 &     0 \\
	  \end{array}\right)
	\end{equation*}\break

\end{multicols}

```

7. Do this for **all** *rows* and *columns*, after doing it, we get these
	- In $D$ we have *shortest* [[Graphs - metrics#Distances|distances]] 
	- In $P$ we have information about [[Graphs - connectivity#Path|paths]] 
	- & For example: from vertex *4* to vertex *2* we should go through vertex *5*
```latex
---
mix-multiplier:0.4
---
\newcommand\x{\cellcolor{violet!80}}
\newcommand\y{\cellcolor{violet!10}}
\newcommand\z{\cellcolor{blue!20}}
\newcommand\g{\cellcolor{green!20}}
\newcommand\m{\cellcolor{green!10}}
\newcommand\q{\cellcolor{red!90}}

\begin{multicols}{2}

	
	\begin{equation*}
	  D= 
	  \left(\begin{array}{ccccc}
	     0 & \x 2 & \y 5 & \z 3 & \z 3 \\
	     \x 2 & 0 & \z 3 & \g 4 & \m 1 \\
	     \y 5 & \z 3 & 0 & \y 5 & \x 2 \\
	     \z 3 & \g 4 & \y 5 & 0 & \z 3 \\
	     \z 3 & \m 1 & \x 2 & \z 3 & 0
	  \end{array}\right)
	\end{equation*}\break

	\begin{equation*}
	  P= 
	  \left(\begin{array}{ccccc}
	     0    &    0 & \y 5 &    0 & \x 2 \\
	     0    &    0 & \y 5 & \y 5 &    0 \\
	     \y 5 & \y 5 & 0    & \y 5 &    0 \\
	     0    & \q 5 & \y 5 &    0 &    0 \\
	     \x 2 &    0 & 0    &    0 &    0 \\
	  \end{array}\right)
	\end{equation*}\break

\end{multicols}

```

> [!note] 
> 1. Presented graph is [[Graphs - basics#Undirected graph|undirected]], so really we needed to calculate only **half** of values
> 2. If there are any *$\infty$* after finishing algorithm – graph is **not** [[Graphs - connectivity#Connected graph|connected]]

```dataview
list from "uni/Discrete math"
```