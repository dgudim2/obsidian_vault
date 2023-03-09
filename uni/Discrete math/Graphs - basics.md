
> Multi-graph is a collection of points
> and line segments that connect these points

# Directed graphs

```dot
digraph neato {

bgcolor="transparent"

graph [layout = neato]

node [shape = circle,
      style = filled,
      width=0.3,
      height=0.3,
      color = grey,
      label = ""]

node [fillcolor = red]
a

node [fillcolor = lightgreen]
b c d

node [fillcolor = orange]

edge [color = grey]
a -> {b c d}
b -> {e f g h i j}
c -> {k l m n o p}
d -> {q r s t u v}
}
```

$$
V = \{V_{1}, V_{2}, V_{3}, ... V_{n}\} - vertices \\
$$

$$
L = (L_{1}, L_{2}, L_{3}, ... L_{n}) - lines
$$

> Two lines are **parallel**, if their initial and final vertices coincide

> Line, whose initial and final vertices coincide, is called a **loop**

> A multi-graph that contains no parallel lines is called **directed graph** or **digraph**. 

> **Digraph** without loops is a **simple digraph**.

# Undirected graph


```start-multi-column
ID: Graphs
Number of Columns: 2
Largest Column: standard
```

```dot
digraph neato {

bgcolor="transparent"

graph [layout = neato]

node [shape = circle,
      style = filled,
      width=0.3,
      height=0.3,
      color = grey,
      label = ""]

node [fillcolor = red]
a

node [fillcolor = lightgreen]
b c d

node [fillcolor = orange]

edge [color = grey]
a -> {b c d}
b -> a
c -> a
d -> a

}
```

--- column-end ---

```dot
graph neato {

bgcolor="transparent"

graph [layout = neato]

node [shape = circle,
      style = filled,
      width=0.3,
      height=0.3,
      color = grey,
      label = ""]

node [fillcolor = red]
a

node [fillcolor = lightgreen]
b c d

node [fillcolor = orange]

edge [color = grey]
a -- {b c d}

}
```

=== end-multi-column

> If the graph is symmetric we can replace any pair of arcs 

$$
(V_{i}, V_{j},) \ and \ (V_{j}, V_{i},) \ with \ a \ pair \ \{V_{i}, V_{j}\} \ and \ call \ it \ EDGE
$$



# Defining a graph

$$
V = \{a, b, c, d, e\}
$$
$$
E = \{\{a, b\}, \{a, c\}, \{a, d\}, \{b, c\}, \{b, d\}, \{d, e\}\},
$$
$$
G = (V, E)
$$
OR
$$
G = (\{a, b, c, d, e\}, \{\{a, b\}, \{a, c\}, \{a, d\}, \{b, c\}, \{b, d\}, \{d, e\}\})
$$

```dot
graph neato {

bgcolor="transparent"

graph [layout = circo]

node [shape = circle,
      style = filled,
      width=0.3,
      height=0.3,
      color = grey]

node [fillcolor = white]

edge [color = grey]
a -- {b c d}
c -- {b}
d -- {e, b}
}
```

