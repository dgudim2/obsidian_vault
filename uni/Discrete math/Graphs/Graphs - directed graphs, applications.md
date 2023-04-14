
# Directed graph

> [!definition] 
> A graph, [[Graphs - basics#Vertices and edges|edges]] of which have *directions*
> See also: [[Graphs - basics#Directed graphs]]

# Applications

## Scheduling

> [!definition] 
> **Executors** - workers performing a task ($V_{1}, V_{2}, V_{3}$)
> **Tasks** - separate jobs that *can't* be subdivided (Must be performed by *one* **Executor**)
> **Execution times** - a number assigned to each task. (how much time does the task take to execute)
> **Consistency relationships** - are constraints on the order in which tasks are performed. 
> 
> > [!note] 
> > Task *X* precedes Task *Y*, and this means that Task *Y* cannot be started until Task *X* is completed.

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

### Priority lists

> [!definition] 
> Rules according to which we assign tasks to *executors* based on a *list of priorities* are:
> - **All executors are busy**
> 	- Wait
> - **One executor is free** -
> 	- Review the list of priorities from the beginning, looking for the first *prepared* task, and assign it to that executor. 

#### Random

> [!note] 
> There are $5!=120$ different schedules
> - ! But not every can be fulfilled

- A B C D E 
	- **17** time units

```asciidoc
[cols=18*]
|===

^|
^|1
^|2
^|3
^|4
^|5
^|6
^|7
^|8
^|9
^|10
^|11
^|12
^|13
^|14
^|15
^|16
^|17

| V1
6+^| [yellow-cell]#A#
2+^| [green-cell]#D#
9+^| -

| V1
5+^| [orange-cell]#B#
7+^| [cyan-cell]#C#
5+^| [blue-cell]#E#

|===
```

- A B D C E
	- **18** time units

```asciidoc
[cols=19*]
|===

^|
^|1
^|2
^|3
^|4
^|5
^|6
^|7
^|8
^|9
^|10
^|11
^|12
^|13
^|14
^|15
^|16
^|17
^|18

| V1
6+^| [yellow-cell]#A#
2+^| [green-cell]#D#
5+^| -
5+^| [blue-cell]#E#

| V1
5+^| [orange-cell]#B#
  ^| -
7+^| [cyan-cell]#C#
5+^| -

|===
```

- C E B A D
	- **20** time units

```asciidoc
[cols=21*]
|===

^|
^|1
^|2
^|3
^|4
^|5
^|6
^|7
^|8
^|9
^|10
^|11
^|12
^|13
^|14
^|15
^|16
^|17
^|18
^|19
^|20

| V1
7+^| [cyan-cell]#C#
5+^| [blue-cell]#E#
6+^| [yellow-cell]#A#
2+^| [green-cell]#D#


| V1
7+^| 
5+^| [orange-cell]#B#
8+^| -

|===
```

#### Descending time algorithm

> Longer jobs should be done earlier than short-term ones.

- C A B E D

```asciidoc
[cols=14*]
|===

^|
^|1
^|2
^|3
^|4
^|5
^|6
^|7
^|8
^|9
^|10
^|11
^|12
^|13

| V1
7+^| [cyan-cell]#C#
5+^| [blue-cell]#E#
  ^| -


| V1
6+^| [yellow-cell]#A#
5+^| [orange-cell]#B#
2+^| [green-cell]#D#

|===
```

#### Critical path algorithm

