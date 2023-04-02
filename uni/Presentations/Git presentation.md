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

### Git

![[Git_icon.svg|150]]

```
```col-md
flexGrow=1
===

### Mercurial

![[Mercurial-Logo.wine.svg|150]]

```
```col-md
flexGrow=1
===

### Subversion

![[Apache_Subversion_logo.svg|150]]

```
```col-md
flexGrow=1
===

### Bazaar

![[Bazaar_logo_(software_product).svg|150]]

```
````

---

# Ok, but What is github then?

**Github.com** is a website that hosts *git* <u>repositories</u> on a remote server
- It also provides a GUI to easily *fork* or *clone* repositories to a local machine

> But **github** is not the only one, here are some more examples

````col
```col-md
flexGrow=1
===
### Bitbucket

![[bitbucket_logo_logos_icon.svg|150]]

```
```col-md
flexGrow=1
===
### GitLab

![[GitLab-2022-New-Logomark.svg|150]]

```
```col-md
flexGrow=1
===
### GitTea

![[Gitea_Logo.svg|150]]

```
```col-md
flexGrow=1
===
### Forgejo

![[Forgejo_logo.svg|150]]

```
````

> You can even self-host your own server of set up a local repo with users on the same file system

---

# Vilniustech gitlab instance

![[vt_git_instance.svg|500]]

---

# Version control

### But what is a <u>version control</u> system?

<br>

#### Version control system features

- Easily *keep track* of file versions
- *Rollback* to previous file versions
- Ability to have *unlimited* number of developers working on the same code base
- Losing work is *really* hard

---

# More about version control

## 3 Main categories

`````col

````col-md
flexGrow=1
===

### Local VCS

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

### Centralized VCS

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

### Distributed VCS

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

---

# More about categories

## Local VCS

![[Screenshot_20230402_143505.png|100%]]

---

# More about categories

## Centralized VCS

`````col 
````col-md 
flexGrow=1.2
===

- A central server repository (repo) holds the "official copy" of the code
	- The server maintains the *sole* version of the repo

- You make *checkouts* of it to your local copy
	- You make *local modifications*
	- Your changes are *not versioned*

- When you're done, you "*check in*" back to the server
	- Your checkin increments the repo's version

```` 
````col-md 
flexGrow=1
===

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
`````

---

# More about categories

## Distributed VCS

`````col 
````col-md 
flexGrow=1
===

- You don't "checkout" from a central repo
	- You "*clone*" and "*pull*" changes from it

- Your local repo is a complete copy of everything on the remote server
	- Yours is "*just as good*" as theirs

- Many operations are local
	- Check in/out from *local* repo
	- Commit changes to *local* repo
	- Local repo keeps version hostory

- When you are ready, you can "*push*" changes back to the server

```` 
````col-md 
flexGrow=1
===

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

---

# Git areas

### In your local copy files can be:

- Checked out, *Modified*, but not yet committed (*working copy*)
- *Staged*, but not yet committed - Staged files are ready to be committed
- *Committed* - A commit saves a *snapshot* of all *staged* state

```mermaid
sequenceDiagram
	participant Working dir;
	participant Staging area;
	participant Git repo (.git);
	participant Remote repo;
	Git repo (.git)-)Working dir: Checkout the project
    Working dir-)Staging area: Stage files
    Staging area-)Git repo (.git): Commit
    Git repo (.git)-)Remote repo: Push
    Remote repo-)Git repo (.git): Fetch
    Remote repo-)Working dir: Pull
```

---

# Git workflow

- **Modify** files in your working directory
- **Stage** files, adding snapshots of them to your *staging* area
- **Commit**, which takes the files in the *staging* area and stores that snapshot permanently to your *Git* directory

```mermaid
sequenceDiagram
	participant Untracked;
	participant Unmodified;
	participant Modified;
	participant Staged;
	Unmodified-)Modified: Edit the file
	Untracked--)Unmodified: Add the file
	Modified-)Staged: Stage the file
	Unmodified--)Untracked: Remove the file
	Staged-)Unmodified: Commit
```

---

# Git commands

```dot 
graph neato { 

bgcolor="transparent" 

graph [layout=fdp, nodesep=3] 

node [shape = circle, 
      style = filled, 
      width=0.3, 
      height=0.3, 
      color=green, 
      fillcolor=white] 

A[label="Git commands", fillcolor=yellow] 
B[label=clone]
C[label=add]
D[label=commit]
E[label=init]
F[label=status]
G[label=diff]
H[label=pull]
I[label=push]
J[label=branch]
K[label=merge]
L[label=tag]


edge [color=gray] 

A -- {B, C, D, E, F, G, H, I, J, K, L}

} 
```

---

# Command usages

---

# Git branching

```mermaid
gitGraph
       commit
       commit
       branch nice_feature
       checkout nice_feature
       commit
       checkout main
       commit
       checkout nice_feature
       branch very_nice_feature
       checkout very_nice_feature
       commit
       checkout main
       commit
       checkout nice_feature
       commit
       checkout main
       merge nice_feature
       checkout very_nice_feature
       commit
       checkout main
       commit
       commit
```

`````col
````col-md
flexGrow=1
===

- To create a new *local* branch:

```bash
git branch "name"
```

````
````col-md
flexGrow=1
===

- To list all branches:

```bash
git branch
```

````
`````

`````col 
````col-md
flexGrow=1
===

- To switch to a given *local* branch

```bash
git checkout "branch_name"
```

````
````col-md
flexGrow=1
===

- To *merge* changes from a branch into the *local* **master** branch

```bash
git checkout master
git merge "branch_name"
```

````
`````

---

# Thanks for your attention!

![[static-assets-upload2926279536208541530.webp|500]]