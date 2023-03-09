# Intersection

If $G_{1}=(V_{1}, E_{1})$, and $G_{2}=(V_{2}, E_{2})$, then $G_{1} \cap G_{2}=(V_{1} \cap V_{2}, E_{1} \cap E_{2})$ 

> (Intersection of vertices and edges)


```start-multi-column
ID: intersection
Number of Columns: 3
largest column: center
Largest Column: standard
```

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
a -- {b d}
c -- {b}
d -- {b}
}
```

--- column-end ---

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
a -- d
d -- c
c -- b
}
```

--- column-end ---

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
a -- d
c -- b
}
```

=== end-multi-column ===

# Union









