# Homomorphism 

> [!definition]
> Graphs $G_{1}=(V_{1},E_{1})$ and $G_{2}=(V_{2},E_{2})$ are **homomorphic**, if there exists such function: $f:G_{1}\to G_{2}$
> 1. If $v \in V_{1}$, then $f(v) \in V_{2}$
> 2. If $e \in E_{1}$, then $f(e) \in E_{2}$
> 3. If in graph $G$ [[Graphs - basics#Directed graphs|vertices]] $u$ and $v$ were [[Graphs - basics#Vertices and edges|adjacent]], then in $G_{2}$ [[Graphs - basics#Directed graphs|vertices]] $f(u)$ and $f(v)$ are also [[Graphs - basics#Vertices and edges|adjacent]]
> 
> > [!tldr] 
> > We map one graph onto another, [[#Folding|folding]] if necessary

> [!theorem] 
> 1. If function $f$ is **homomorphism** from $G_{1}$ to $G_{2}$, then $f(G_{1})$ is a [[Graphs - connectivity#Subgraph ($G' subset G$)|subgraph]] of $G_{2}$
> 2. If graph $G_{1}$ is [[Graphs - connectivity#Connected graph|connected]] and function $f$ is **homomorphism**, then $f(G_{1})$ is also [[Graphs - connectivity#Connected graph|connected]]
> 3. If graph $G_{1}$ is [[Graphs - basics#Complete graph|complete]] and function $f$ is **homomorphism**, then $f(G_{1})$ is also [[Graphs - basics#Complete graph|complete]]

> [!example] 
> Construct **homomorphism** from *B* to *A*
> 
> `````col
> ````col-md
> flexGrow=1
> ===
> **A**
> 
> ```dot 
> graph neato { 
> 
> bgcolor="transparent" 
> 
> graph [layout = neato] 
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
> "3/b" -- {1 "2/a"}
> "2/a" -- 1
> 
> } 
> ```
> 
> ````
> ````col-md
> flexGrow=1
> ===
> 
> **B**
> 
> ```dot 
> graph neato { 
> 
> bgcolor="transparent" 
> 
> graph [layout = neato] 
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
> a -- b
> 
> } 
> ```
> 
> ````
> `````
> 
> $f(a)=2, f(b)=3$

## Folding

> One of the techniques for constructing [[#Homomorphism|homomorphism]] is **graph folding**

> [!example] 
> Map *A* to *B* 
> 
> `````col 
> ````col-md 
> flexGrow=1
> ===
> 
> **A**
> 
> ```dot 
> graph neato { 
> 
> bgcolor="transparent" 
> 
> graph [layout = neato] 
> 
> node [shape = circle, 
>       style = filled, 
>       width=0.3, 
>       height=0.3, 
>       color=green, 
>       fillcolor = white] 
> 
> 1 [pos="-1,-0.5!"] 
> 2 [pos="1,-0.5!"] 
> 3 [pos="-1,0.5!"] 
> 4 [pos="1,0.5!"] 
> 5 [pos="0,0!"] 
> 
> 
> node [color=transparent, 
>       fillcolor=transparent, 
>       label="", 
> 	  width=0,
>       height=0]
> "-" [pos="0, 1!"] 
> "." [pos="0,-1!"]
> 
> edge [color = grey] 
> 
> 5 -- {1 2 3 4}
> 1 -- 3
> 2 -- 4
> 
> edge [style=dashed, color=green]
> 5 -- {".", "-"}
> 
> } 
> ```
> 
> ```` 
> ````col-md 
> flexGrow=1
> ===
> 
> **B**
> 
> ```dot 
> graph neato { 
> 
> bgcolor="transparent" 
> 
> graph [layout = neato] 
> 
> node [shape = circle, 
>       style = filled, 
>       width=0.3, 
>       height=0.3, 
>       color=green, 
>       fillcolor = white] 
> 
> a [pos="-1,-0.5!"] 
> b [pos="-1,0.5!"] 
> c [pos="0,0!"] 
> 
> edge [color = grey] 
> 
> a -- b
> b -- c
> c -- a
> 
> } 
> ```
> 
> ```` 
> `````
> 
> We get this:
> 
> $f(1)=f(2)=a$
> $f(3)=f(4)=b$
> $f(5)=c$
> 
> ```dot 
> graph neato { 
> 
> bgcolor="transparent" 
> 
> graph [layout = neato] 
> 
> node [shape = circle, 
>       style = filled, 
>       width=0.3, 
>       height=0.3, 
>       color=green, 
>       fillcolor = white] 
> 
> "1,2" [pos="-1,-0.5!"] 
> "3,4" [pos="-1,0.5!"] 
> 
> node [width=0.65, 
>       height=0.65]
> 5 [pos="0,0!"] 
> 
> edge [color = grey] 
> 
> 5 -- {"3,4", "1,2"}
> "3,4" -- "1,2"
> 
> 
> } 
> ```

# Isomorphism

> [!definition] 
> Graphs $G_{1}=(V_{1},E_{1})$ and $G_{2}=(V_{2},E_{2})$ are **isomorphic** ($G_{1} \cong G_{2}$), if there exists such *bijection* $f:G_{1} \to G_{2}$, that 
> $\forall \{u,v\} \in E_{1} \implies \{f(u), f(v)\} \in E_{2}$ and 
> $\forall \{w,y\} \in E_{2} \implies \{f^{-1}(w), f^{-1}(y)\} \in E_{1}$ 
> $|V_{1}|=|V_{2}|$, $|E_{1}|=|E_{2}|$
> 
> > [!tldr] 
> > The same graph written differently

> [!example] 
> 
> `````col 
> ````col-md 
> flexGrow=1
> ===
> 
> ## $G$
> 
> ```dot 
> graph neato { 
> 
> bgcolor="transparent" 
> 
> graph [layout = neato] 
> 
> node [shape = circle, 
>       style = filled, 
>       width=0.3, 
>       height=0.3, 
>       color=green, 
>       fillcolor = white] 
> 
> a [pos="1,0!"] 
> b [pos="0,1!"] 
> c [pos="0,0!"] 
> d [pos="1,1!"] 
> 
> edge [color = grey] 
>  
> c -- {a b d}
> a -- {b d}
> b -- d
> 
> } 
> ```
> 
> ```` 
> ````col-md 
> flexGrow=2
> ===
> 
> ## $T$
> 
> ```dot 
> graph neato { 
> 
> rankdir=LR;
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
> 
> edge [color = grey] 
>  
> 3 -- {1 2 4}
> 1 -- {2 4}
> 2 -- 4
> 
> } 
> ```
> 
> ```` 
> `````
> 

> [!info] 
> Properties:
> 
> 1. Graph is **isomorphic** to itself
> 2. If $G_{1}$ is **isomorphic** to $G_{2}$, then $G_{2}$ is **isomorphic** to $G_{1}$
> 3. If $G_{1}$ is **isomorphic** to $G_{2}$ and $G_{2}$ to $G_{3}$, then $G_{1}$ is **isomorphic** to $G_{3}$

