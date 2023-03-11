# Subgraph ($G' \subset G$)

> Graph $G' = (V', E')$ is a *subgraph* of graph $G = (V, E)$ if $V' \subset V$, $E' \subset E$
`````col
````col-md
flexGrow=1
===
$G$

```dot 
graph neato { 

bgcolor="transparent" 

graph [layout = neato] 

node [shape = circle, 
      style = filled, 
      width=0.3, 
      height=0.3, 
      color=green, 
      fillcolor = white] 

a [pos="1,0!"] 
b [pos="0,1!"] 
c [pos="0,0!"] 
d [pos="1,1!"] 

edge [color = grey] 
 
a -- {b d} 
c -- {b a d} 
d -- {b b} 
} 
```

````
````col-md
flexGrow=1
===
$T \subset G$

```dot 
graph neato { 

bgcolor="transparent" 

graph [layout = neato] 

node [shape = circle, 
      style = filled, 
      width=0.3, 
      height=0.3, 
      color=yellow, 
      fillcolor = white] 

a [pos="1,0!"] 
b [pos="0,1!"] 
c [pos="0,0!"] 
d [pos="1,1!"] 

edge [color = grey] 
 
a -- {b d} 
c -- {b} 
d -- {b} 
} 
```

````
`````

--- 
<br>

# Special vertices

`````col
````col-md
flexGrow=1
===
## Isolated

```dot 
graph neato { 

bgcolor="transparent" 

graph [layout = neato] 

node [shape = circle, 
      style = filled, 
      width=0.3, 
      height=0.3, 
      color=green, 
      fillcolor = white] 

a [pos="1,0!"] 
b [pos="0,1!"] 
c [pos="0,0!"] 
d [pos="1,1!"] 

edge [color = grey] 
 
a -- {b} 
c -- {b a} 
} 
```

> In this case [[Graphs - basics#Directed graphs|vertex]] **d** is **isolated**

````
````col-md
flexGrow=1
===
## Rising

```dot 
graph neato { 

bgcolor="transparent" 

graph [layout = neato] 

node [shape = circle, 
      style = filled, 
      width=0.3, 
      height=0.3, 
      color=green, 
      fillcolor = white] 

a [pos="1,0!"] 
b [pos="0,1!"] 
c [pos="0,0!"] 
d [pos="1,1!"] 

edge [color = grey] 
a -- {b d} 
c -- {b a} 
} 
```
> In this case [[Graphs - basics#Directed graphs|vertex]] **d** is **rising** (it's [[Graphs - basics#Order (degree) of vertices|order]] is 1 | $p(v)=1$)

````
`````

--- 
<br>

# Homogeneous graph

```dot 
graph neato { 

bgcolor="transparent" 

graph [layout = neato] 

node [shape = circle, 
      style = filled, 
      width=0.3, 
      height=0.3, 
      color=green, 
      fillcolor = white] 

a [pos="0,0!"] 
b [pos="0.5,0.9!"] 
c [pos="1.5,0.9!"] 
d [pos="2,0!"] 
e [pos="1.5,-0.9!"]
f [pos="0.5,-0.9!"]

edge [color = grey] 
a -- {b c}
b -- {c d}
c -- {d e}
d -- {e f}
e -- {f a}
f -- {a b}
} 
```

> Graph is **homogeneous**, if all [[Graphs - basics#Directed graphs|vertices]] have the same [[Graphs - basics#Order (degree) of vertices|degree]] 

--- 
<br>

# Connected graph

> Graph is called **connected**, if any two [[Graphs - basics#Directed graphs|vertices]] can be connected by a [[#Path|path]]

`````col
````col-md
flexGrow=1
===
### Connected

```dot 
graph neato { 

bgcolor="transparent" 

graph [layout = neato] 

node [shape = circle, 
      style = filled, 
      width=0.3, 
      height=0.3, 
      color=green, 
      fillcolor = white] 

a [pos="0,0!"] 
b [pos="0.5,0.9!"] 
c [pos="1.5,0.9!"] 
d [pos="2,0!"] 
e [pos="1.5,-0.9!"] 
f [pos="0.5,-0.9!"] 

edge [color = grey] 

a -- {b d e} 
b -- {c d} 
e -- f
} 
```

````
````col-md
flexGrow=1
===
### Not connected

```dot 
graph neato { 

bgcolor="transparent" 

graph [layout = neato] 

node [shape = circle, 
      style = filled, 
      width=0.3, 
      height=0.3, 
      color=green, 
      fillcolor = white] 

a [pos="0,0!"] 
b [pos="0.5,0.9!"] 
c [pos="1.5,0.9!"] 
d [pos="2,0!"] 
e [pos="1.5,-0.9!"] 
f [pos="0.5,-0.9!"] 

edge [color = grey] 

a -- {b d e} 
b -- {c d} 
} 
```

````
`````

> [!note]
> *Connected* means that we can start *journey* from any [[Graphs - basics#Directed graphs|vertex]] and access any vertex we want

## Connected components

> Maximal [[#Connected graph|connected]] [[#Subgraph ($G' subset G$)|subgraph]] is called a **connected component**. 
> Second [[#Connected graph|graph]] has *2* **connected components**

> [!note]
> 1. Any graph of [[Graphs - basics#Graph order|order]] *n* has no more than *n* connected components
> 2. If graph of [[Graphs - basics#Graph order|order]] *n* has *n* connected components, then all [[Graphs - basics#Directed graphs|vertices]] are [[#Special vertices|isolated]]
> 3. A [[#Connected graph|connected graph]] of second [[Graphs - basics#Graph order|order]] has one [[Graphs - basics#Undirected graph|edge]] 
> 4. A [[#Connected graph|connected graph]] of third [[Graphs - basics#Graph order|order]] has 2 or 3 [[Graphs - basics#Undirected graph|edges]] 

--- 
<br>

# Walk

> *Walk* is any finite sequence of graph [[Graphs - basics#Directed graphs|vertices]] and [[Graphs - basics#Undirected graph|edges]] 

```dot 
graph neato { 

bgcolor="transparent" 

graph [layout = neato] 

node [shape = circle, 
      style = filled, 
      width=0.3, 
      height=0.3, 
      color=green, 
      fillcolor = white] 

a [pos="0,0!"] 
b [pos="0.5,0.9!"] 
c [pos="1.5,0.9!"] 
d [pos="2,0!"] 
e [pos="1.5,-0.9!"] 
f [pos="0.5,-0.9!"] 

edge [color = grey] 

a -- {b d e} 
b -- {c d} 

} 
```

Can be written in 2 ways:
1. *e*, {e, a}, *a*, {a, d}, *d*, {b, d}, *b*, {a, b}, *a*
2. *e*, *a*, *d*, *b*, *a*

> [!note]
> If you are going for a walk, you can visit same street ([[Graphs - basics#Undirected graph|edges]]) several times

## Open / closed walk

> [[#Walk]] is **open** if first and last [[Graphs - basics#Directed graphs|vertices]] are different
> If walk starts and ends in the same vertex, it called **closed**

## Circuit

> If all [[Graphs - basics#Undirected graph|edges]] of [[#Walk|walk]] are distinct, it called a **circuit**

## Path

> [[#Open / closed walk|Open]] [[#Circuit|circuit]] is called a **path** 

## Cycle

> [[#Open / closed walk|Closed]] [[#Circuit|circuit]] is called a **cycle** 

```dot 
graph neato { 

bgcolor="transparent" 

graph [layout = neato] 

node [shape = circle, 
      style = filled, 
      width=0.3, 
      height=0.3, 
      color=green, 
      fillcolor = white] 

a [pos="0,0!"] 
b [pos="0.5,0.9!"] 
c [pos="1.5,0.9!"] 
d [pos="2,0!"] 
e [pos="1.5,-0.9!"] 
f [pos="0.5,-0.9!"] 

edge [color = grey] 
 
a -- b
c -- d
c -- f
d -- e
e -- b
f -- a 
} 
```

> [[#Connected graph]] is a [[#Cycle|cycle]], if all vertices have [[Graphs - basics#Order (degree) of vertices|degree]] 2 | $p(v_{i})=2$

--- 
<br>

# Separability of graphs

```dot 
graph neato { 

bgcolor="transparent" 

graph [layout = neato] 

node [shape = circle, 
      style = filled, 
      width=0.3, 
      height=0.3, 
      color=green, 
      fillcolor = white] 

a [pos="0,0!"] 
b [pos="0.5,0.9!"] 
c [pos="1.5,0.9!"] 
d [pos="2,0!"] 
e [pos="1.5,-0.9!"] 
f [pos="0.5,-0.9!"] 

edge [color = grey] 
d -- c
c -- {a b}
b -- a
a -- f
f -- e
} 
```

## Articulations

> [[Graphs - basics#Directed graphs|Vertex]] $v \in V$ of graph $G=(V, E)$ is called **articulation**, if graph $G-v$ has more [[#Connected components|connected components]] than graph $G$

> If graph has an **articulation** it is **separable**

> [[Graphs - basics#Directed graphs|Vertices]] *f*, *a*, *c* are **articulations** in [[#Separability of graphs|our graph]]

`````col
````col-md
flexGrow=1
===
### $G-c$

```dot 
graph neato { 

bgcolor="transparent" 

graph [layout = neato] 

node [shape = circle, 
      style = filled, 
      width=0.3, 
      height=0.3, 
      color=green, 
      fillcolor = white] 

a [pos="0,0!"] 
b [pos="0.5,0.9!"] 
d [pos="2,0!"] 
e [pos="1.5,-0.9!"] 
f [pos="0.5,-0.9!"] 

edge [color = grey] 
b -- a
a -- f
f -- e
} 
```

````
````col-md
flexGrow=0.8
===
### $G-a$

```dot 
graph neato { 

bgcolor="transparent" 

graph [layout = neato] 

node [shape = circle, 
      style = filled, 
      width=0.3, 
      height=0.3, 
      color=green, 
      fillcolor = white] 

b [pos="0.5,0.9!"] 
c [pos="1.5,0.9!"] 
d [pos="2,0!"] 
e [pos="1.5,-0.9!"] 
f [pos="0.5,-0.9!"] 

edge [color = grey] 
d -- c
c -- b
f -- e
} 
```

````
````col-md
flexGrow=1
===
### $G-f$

```dot 
graph neato { 

bgcolor="transparent" 

graph [layout = neato] 

node [shape = circle, 
      style = filled, 
      width=0.3, 
      height=0.3, 
      color=green, 
      fillcolor = white] 

a [pos="0,0!"] 
b [pos="0.5,0.9!"] 
c [pos="1.5,0.9!"] 
d [pos="2,0!"] 
e [pos="1.5,-0.9!"] 

edge [color = grey] 
d -- c
c -- {a b}
b -- a
} 
```

````
`````

## Blocks

> **Block** - maximal [[#Subgraph ($G' subset G$)|subgraph]] without [[#Articulations|articulations]] 

> [[#Separability of graphs|Our graph]] has *4* **blocks**
`````col

````col-md
flexGrow=1
===

```dot 
graph neato { 

bgcolor="transparent" 

graph [layout = neato] 

node [shape = circle, 
      style = filled, 
      width=0.3, 
      height=0.3, 
      color=green, 
      fillcolor = white] 

c [pos="0,0!"] 
d [pos="1,1!"] 

edge [color = grey] 
 c -- d
} 
```

````
````col-md
flexGrow=1
===

```dot 
graph neato { 

bgcolor="transparent" 

graph [layout = neato] 

node [shape = circle, 
      style = filled, 
      width=0.3, 
      height=0.3, 
      color=green, 
      fillcolor = white] 

f [pos="0,0!"] 
e [pos="1,1!"] 

edge [color = grey] 
 f -- e
} 
```

````
````col-md
flexGrow=1
===

```dot 
graph neato { 

bgcolor="transparent" 

graph [layout = neato] 

node [shape = circle, 
      style = filled, 
      width=0.3, 
      height=0.3, 
      color=green, 
      fillcolor = white] 

a [pos="0,0!"] 
f [pos="1,1!"] 

edge [color = grey] 
 a -- f
}
```

````
````col-md
flexGrow=1
===

```dot 
graph neato { 

bgcolor="transparent" 

graph [layout = neato] 

node [shape = circle, 
      style = filled, 
      width=0.3, 
      height=0.3, 
      color=green, 
      fillcolor = white] 

a [pos="0,0!"] 
b [pos="0.5,1!"] 
c [pos="1,0!"]

edge [color = grey] 
 a -- b
 b -- c
 c -- a
}
```

````
`````

## Bridges

> [[Graphs - basics#Undirected graph|Edge]] $e \in E$ of graph $G=(V, E)$ is called **articulation**, if graph $G-e$ has more [[#Connected components|connected components]] than graph $G$ 

> [[Graphs - basics#Undirected graph|Edges]] $\{c, d\}$, $\{e, f\}$ and $\{a, f\}$ are **bridges** in [[#Separability of graphs|our graph]] 

`````col
````col-md
flexGrow=1
===
### $G-\{c,d\}$

```dot 
graph neato { 

bgcolor="transparent" 

graph [layout = neato] 

node [shape = circle, 
      style = filled, 
      width=0.3, 
      height=0.3, 
      color=green, 
      fillcolor = white] 

a [pos="0,0!"] 
b [pos="0.5,0.9!"] 
c [pos="1.5,0.9!"] 
d [pos="2,0!"] 
e [pos="1.5,-0.9!"] 
f [pos="0.5,-0.9!"] 

edge [color = grey] 
c -- {a b}
b -- a
a -- f
f -- e
} 
```

````
````col-md
flexGrow=1
===
### $G-\{e,f\}$

```dot 
graph neato { 

bgcolor="transparent" 

graph [layout = neato] 

node [shape = circle, 
      style = filled, 
      width=0.3, 
      height=0.3, 
      color=green, 
      fillcolor = white] 

a [pos="0,0!"] 
b [pos="0.5,0.9!"] 
c [pos="1.5,0.9!"] 
d [pos="2,0!"] 
e [pos="1.5,-0.9!"] 
f [pos="0.5,-0.9!"] 

edge [color = grey] 
d -- c
c -- {a b}
b -- a
a -- f
} 
```

````
````col-md
flexGrow=1
===
### $G-\{a,f\}$

```dot 
graph neato { 

bgcolor="transparent" 

graph [layout = neato] 

node [shape = circle, 
      style = filled, 
      width=0.3, 
      height=0.3, 
      color=green, 
      fillcolor = white] 

a [pos="0,0!"] 
b [pos="0.5,0.9!"] 
c [pos="1.5,0.9!"] 
d [pos="2,0!"] 
e [pos="1.5,-0.9!"] 
f [pos="0.5,-0.9!"] 

edge [color = grey] 
d -- c
c -- {a b}
b -- a
f -- e
} 
```

````
`````

## Separating sets

```dot 
graph neato { 

bgcolor="transparent" 

graph [layout = circo] 

node [shape = circle, 
      style = filled, 
      width=0.3, 
      height=0.3, 
      color=green, 
      fillcolor = white] 

edge [color = grey] 
a -- {c b}
c -- b
d -- {c b e}
f -- {i}
g -- {h}

edge [color = red]
d -- f
e -- f
g -- f

edge [color = yellow]
e -- g

} 
```

> Subset $S \subset E$ is a **separating** set of [[#Connected graph|connected graph]] $G=(V,E)$, if graph $G'=(V,E \textbackslash S)$ is a *disconnected* graph

> In this case $\{\{d,f\}, \{e,f\}, \{g,f\}, \{e,g\}\}$ is a **separating set**

### Cuts

> Any minimal [[#Separating sets|separating set]] is a **cut**

> [!note]
> Any [[#Bridges|bridge]] is a [[#Separating sets|separating set]] and a **cut**

> In this case $\{\{d,f\}, \{e,f\}, \{g,f\}\}$ is a **cut**