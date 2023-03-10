# Intersection

If $G_{1}=(V_{1}, E_{1})$, and $G_{2}=(V_{2}, E_{2})$, then $G_{1} \cap G_{2}=(V_{1} \cap V_{2}, E_{1} \cap E_{2})$ 

> (Intersection of vertices and edges)

`````col

````col-md
## $G_{1}$
---
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
````

````col-md
## $G_{2}$
---
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
````

````col-md
## $G_{1} \cap G_{2}$
---
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
````

`````








# Union









