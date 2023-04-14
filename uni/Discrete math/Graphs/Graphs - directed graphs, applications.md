
# Directed graph

> [!definition] 
> A graph, [[Graphs - basics#Vertices and edges|edges]] of which have *directions*
> See also: [[Graphs - basics#Directed graphs]]

## Applications

### Scheduling
 
The main stages of the problem are conveniently described by a [[#Directed graph|directed graph]]:
- Graph [[Graphs - basics#Vertices and edges|vertices]] – tasks
- Arcs (directed [[Graphs - basics#Vertices and edges|edges]]) - Relationships of Consistency.

> Directed graphs describe *relationships* between objects, but these relationships are not necessarily reciprocal

When object is *associated* with another object, this is indicated by an *arrow* pointing from point to point

```dot 
---
preset:math-graph
---
digraph neato { 

bgcolor="transparent" 
rankdir = "LR"
graph [layout = dot] 

node [shape = circle, 
      style = filled, 
      width=0.7, 
      height=0.7, 
      fixedsize=true
      color=green, 
      fillcolor = white] 

A [label="A(6)"]
B [label="B(5)"]
C [label="C(7)"]
D [label="D(2)"]
E [label="C(5)"]

node [penwidth=2, color=purple]
BEGIN [label="Δ\nBEG\n(0)"]
END [label="Δ\nEND\n(0)"]

edge [color = lightgrey] 
 
BEGIN -> A
BEGIN -> B
BEGIN -> C
A -> D
B -> D
C -> E

D -> END
E -> END

} 
```

A [[#Directed graph|directed graph]] has *5* tasks and *2* fictive (BEGIN and END) with *0* **execution time**

> The notion *A(6)* means that the execution of task *A* takes *6* units of time
> *A* is executed before *D* (shown by arrow)

> [!definition] 
> **Executors** - workers performing a task ($V_{1}, V_{2}, V_{3}$)
> **Tasks** - separate jobs that *can't* be subdivided (Must be performed by *one* **Executor**)

#### Priority lists

1. Random

A B C D E

```asciidoc
|===

| [d-red-cell]#Obvious# and [l-red]*very obvious*.
| [red]#Obvious# and [red]*very obvious*.
| [red]#Obvious# and [red]*very obvious*.
| [red]#Obvious# and [red]*very obvious*.

|===
```


