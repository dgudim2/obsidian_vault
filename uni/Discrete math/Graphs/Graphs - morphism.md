# Homomorphism

> [!definition]
> Graphs $G_{1}=(V_{1},E_{1})$ and $G_{2}=(V_{2},E_{2})$ are **homomorphic**, if there exists such function: $f:G_{1}\to G_{2}$
> 1. If $v \in V_{1}$, then $f(v) \in V_{2}$
> 2. If $e \in E_{1}$, then $f(e) \in E_{2}$
> 3. If in graph $G$ [[Graphs - basics#Directed graphs|vertices]] $u$ and $v$ were [[Graphs - basics#Vertices and edges|adjacent]], then in $G_{2}$ [[Graphs - basics#Directed graphs|vertices]] $f(u)$ and $f(v)$ are also [[Graphs - basics#Vertices and edges|adjacent]]

> [!theorem] 
> 1. If function $f$ is **homomorphism** from $G_{1}$ to $G_{2}$, then $f(G_{1})$ is a [[Graphs - connectivity#Subgraph ($G' subset G$)|subgraph]] of $G_{2}$
> 2. If graph $G_{1}$ is [[Graphs - connectivity#Connected graph|connected]] and function $f$ is **homomorphism**, then $f(G_{1})$ is also [[Graphs - connectivity#Connected graph|connected]]
> 3. If graph $G_{1}$ is [[Graphs - basics#Complete graph|complete]] and function $f$ is **homomorphism**, then $f(G_{1})$ is also [[Graphs - basics#Complete graph|complete]]

> [!tldr] 
> We map one graph onto another

