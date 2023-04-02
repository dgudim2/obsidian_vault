# GIT

![[Git_icon.svg|300]]

---

# What is git? 

## Is it <u>git</u>hub just without the hub?

  ![[git.png|300]]

---

# No! 

### It is a <u>version control</u> system

Here are some more examples of such systems

````col
```col-md
flexGrow=1
===

![[Git_icon.svg|150]]

```
```col-md
flexGrow=1
===

![[Mercurial-Logo.wine.svg|150]]

```
```col-md
flexGrow=1
===

![[Apache_Subversion_logo.svg|150]]

```
```col-md
flexGrow=1
===

![[Bazaar_logo_(software_product).svg|150]]

```
````

---

# Ok, but What is github then?

**Github.com** is a website that hosts *git* <u>repositories</u> on a remote server

> [!note] 
> It also provides a GUI to easily *fork* or *clone* repositories to a local machine

---

# Version control

### But what is a <u>version control</u> system?

<br>

#### Version control system features

- Easily *keep track* of file versions
- *Rollback* to previous file versions
- Ability to have *unlimited* number of developers working on the same code base

---

# More about version control

## 3 Main categories

`````col

````col-md
flexGrow=1
===

### Local VSC

```dot
graph G {

    graph [compound=true, fontcolor=white, bgcolor=transparent, splines=polyline];
    node [shape=oval,color=white];
    edge [color=white]

    subgraph cluster_2 {
	    label = "Local computer";
	    node [style=filled,fillcolor=blue];
	    "File"
	    subgraph cluster_3 {
	        node [style=filled,fillcolor=green];
	        "version 3" "version 2" "version 1";
	        label = "Version database";
	        color=blue;
        }
        color=gray;
    }

    // Edges between nodes render fine
    "version 2" -- "version 3";
    "version 1" -- "version 2";

    "version 3" -- "File" [label="  checkout", fontcolor=white];
}
```

````

````col-md
flexGrow=2.1
===

### Centralized VSC

```dot
digraph G {

    graph [compound=true, fontcolor=white, bgcolor=transparent, nodesep=1];
    node [shape=oval,color=white];
    edge [color=white]

    subgraph cluster_0 {
        node [style=filled];
        node[fillcolor=blue]
        "File A"[label="File"];
        label = "Computer A";
        color=blue;
    }

	subgraph cluster_2 {
	    label = "Central VSC server";
	    subgraph cluster_3 {
	        node [style=filled,fillcolor=green];
	        "version 3" "version 2" "version 1";
	        label = "Version database";
	        color=blue;
        }
        color=gray;
    }

    subgraph cluster_1 {
        node [style=filled,fillcolor=blue];
        "File B"[label="File"];
        label = "Computer B";
        color=blue;
    }

    // Edges between nodes render fine
    "version 1" -> "version 2";
    "version 2" -> "version 3";

    // Edges that directly connect one cluster to another
    "version 1" -> "File A" [ltail=cluster_2, label="  checkout", fontcolor=white];
    "version 1" -> "File B" [ltail=cluster_2, label="  checkout", fontcolor=white,constraint=false];
}
```

````

````col-md
flexGrow=3
===

### Distributed VSC

```dot
graph G {

    graph [compound=true, fontcolor=white, bgcolor=transparent, nodesep=1];
    node [shape=oval,color=white];
    edge [color=white]

	subgraph cluster_2 {
	    label = "Server computer";
	    subgraph cluster_3 {
	        node [style=filled,fillcolor=green];
	        "version 3" "version 2" "version 1";
	        label = "Version database";
	        color=blue;
        }
        color=gray;
    }

    subgraph cluster_10 {
	    label = "Computer A";
	    node [style=filled,fillcolor=blue];
	    "FileA"[label=File]
	    subgraph cluster_11 {
	        node [style=filled,fillcolor=green];
	        "version 3_"[label="version 3"];
	        "version 2_"[label="version 2"];
	        "version 1_"[label="version 1"];
	        label = "Version database";
	        color=blue;
        }
        color=gray;
    }

    subgraph cluster_23 {
	    label = "Computer B";
	    node [style=filled,fillcolor=blue];
	    "FileB"[label=File]
	    subgraph cluster_24 {
	        node [style=filled,fillcolor=green];
	        "version 3__"[label="version 3"];
	        "version 2__"[label="version 2"];
	        "version 1__"[label="version 1"];
	        label = "Version database";
	        color=blue;
        }
        color=gray;
    }
    
    "version 1" -- "version 2";
    "version 2" -- "version 3";
    
    "version 1_" -- "version 2_";
    "version 2_" -- "version 3_";
    
    "version 1__" -- "version 2__";
    "version 2__" -- "version 3__";

	"version 3_" -- "FileA"[ltail=cluster_11]
	"version 3__" -- "FileB"[ltail=cluster_24]

	"version 2__" -- "version 3_" [ltail=cluster_23,lhead=cluster_10,constraint=false]
	
	"version 1__" -- "version 2"  [ltail=cluster_23,lhead=cluster_2,constraint=false]

	"version 1"   -- "version 1_" [ltail=cluster_2,lhead=cluster_10,constraint=false]
}
```

````


`````


