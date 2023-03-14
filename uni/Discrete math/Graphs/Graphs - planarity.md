# Planar graph

> [!definition] 
> Graph is called **planar**, it it can be drawn with [[Graphs - basics#Undirected graph|edges]] not intersecting.

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

a [pos="1,0!"] 
b [pos="0,1!"] 
c [pos="0,0!"] 
d [pos="1,1!"] 

edge [color = grey] 
 
a -- {b d} 
c -- {a d b} 
d -- {b} 
} 
```

```` 
````col-md 
flexGrow=2
===

```dot 
graph neato { 

rankdir=LR;

bgcolor="transparent" 

graph [layout = dot] 

node [shape = circle, 
      style = filled, 
      width=0.3, 
      height=0.3, 
      color=green, 
      fillcolor = white] 

edge [color = grey] 
 
a -- {b d} 
c -- {a d b} 
d -- {b} 
} 
```

```` 
`````

## Area of a [[#Planar graph|planar]] graph

> [!definition] 
> **Area** of a [[#Planar graph|planar]] graph is the *biggest* possible area of plane where *any two points* can be connected by *line* that does not intersect with [[Graphs - basics#Undirected graph|edges]]

