
# Tree

> [!definition] 
> [[Graphs - connectivity#Connected graph|Connected]] and [[Graphs - connectivity#Cyclic graph|acyclic]] graph is called a **tree**

> [!example] 
> 
> ```dot 
> graph neato { 
> 
> rankdir="LR"
> 
> bgcolor="transparent" 
> 
> graph [layout = dot] 
> 
> node [shape = circle, 
>       style = filled, 
>       width=0.3, 
>       height=0.3, 
>       color=green, 
>       fillcolor = white] 
> 
> edge [color = grey] 
> 
> 1 -- 2
> 2 -- 3
> 3 -- {4 5}
> 5 -- {6 7}
> 7 -- {8 9 10}
> 
> } 
> ```
> 

> [!note] 
> 1. Any [[Graphs - basics#Directed graphs|vertex]] of a **tree** with a [[Graphs - basics#Order (degree) of vertices|degree]] $\geq 2$ is an [[Graphs - connectivity#Articulations|articulation]]
> 2. Any [[Graphs - basics#Undirected graph|edge]] of a **tree** is a [[Graphs - connectivity#Bridges|bridge]] 
> 3. Any **tree** with *$n$* [[Graphs - basics#Directed graphs|vertices]] has *$n-1$* [[Graphs - basics#Undirected graph|edges]] ([[#Forest (k-tree)|forest]] will have less)
> 4. Graph is a **tree** if and only if any 2 [[Graphs - basics#Directed graphs|vertices]] can be connected by *only* 1 [[Graphs - connectivity#Circuit|circuit]]

## Forest (k-tree)

> [!definition] 
> If [[Graphs - connectivity#Cyclic graph|acyclic]] graph has *k* [[Graphs - connectivity#Connected components|connected components]], it's called a **forest** or a **k-tree**

> [!example] 
> 
> ```dot 
> graph neato { 
> 
> rankdir="LR";
> 
> bgcolor="transparent" 
> 
> graph [layout = dot] 
> 
> node [shape = circle, 
>       style = filled, 
>       width=0.3, 
>       height=0.3, 
>       color=green, 
>       fillcolor = white] 
>       
> edge [color = grey] 
> 
> 1 -- 2
> 2 -- 3
> 3 -- 4
> 
> 5 -- 6
> 
> 7 -- 8
> 8 -- {9 10}
> 
> } 
> ```

## Spanning tree

> [!definition]
> *Tree* $T$ is *spanning* of graph $G$ it is a [[Graphs - connectivity#Subgraph ($G' subset G$)|subset]] of $G$ and has all vertices of $G$	

#TODO include examples, link to trees


