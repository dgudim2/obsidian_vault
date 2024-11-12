---
icon: TiVersions
---

# Development without version control


`````col 
````col-md 
flexGrow=1
===

## -----

- Maintaining a project with a team is very hard
- Difficult to roll back
- Testing new features or A/B is compicated 
- Maintaining features for a specific client is complicated

## -----

```` 
````col-md 
flexGrow=1
===

```dynamic-svg
[[no-version-control.excalidraw.svg]]
```

```` 
`````
---
<br>

# So what is version control?

`````col 
````col-md 
flexGrow=1
===

> #### In a nutshell it's

### A system for recording and keeping track of file versions

![[Git presentation#Version control system features]]

```` 
````col-md 
flexGrow=1
===

```dynamic-svg
[[version-control-simple.excalidraw.svg]]
```

```` 
`````

---
<br>

# More benefits

`````col 
````col-md 
flexGrow=1
===

## Version control helps you to

- **Look back** and recall/compare previous versions
- See **who made** which **changes**
- **Save yourself** when things go wrong

## It also allows you to

- **Test out new features quickly** without fear of breaking code *(you can always roll back)*
- **Branch out** and easily maintain different features for different clients

```` 
````col-md 
flexGrow=1
===

```dynamic-svg
---
invert-color
---
[[commit-list.excalidraw.svg]]
```

```` 
`````

---
<br>

# Types of version control

![[Git presentation#3 Main categories]]

---
<br>

![[Git presentation#Here are some examples of such systems]]

---
<br>

# How subversion works

![[Git presentation#Centralized VCS]]

> - https://subversion.apache.org/docs/

--- 
<br>

# How git works

![[Git presentation#Distributed VCS]]


> - https://git-scm.com/doc

---
<br>

`````col 
````col-md 
flexGrow=0.6
===

![[Git presentation#Git commands]]

```` 
````col-md 
flexGrow=1
===

![[Git presentation#Git workflow]]

```` 
`````

---
<br>

![[Git presentation#Git branching]]

---
<br>

# Pull requests (PRs)

> One of the ways to handle merges/rebases is via pull requests

`````col 
````col-md 
flexGrow=0.5
===

- This is **NOT** part of *git*
- It is fully handled by your hosting provider of choice

## Some checks before merge

- Approval from several contributors
- Successfull build
- Successfull tests

```` 
````col-md 
flexGrow=1
===

![[Pasted image 20241027170620.png]]

```` 
`````

> https://docs.github.com/en/pull-requests/collaborating-with-pull-requests/proposing-changes-to-your-work-with-pull-requests/creating-a-pull-request

---
<br>

![[Git presentation#Merge conflicts]]

> - https://docs.github.com/en/pull-requests/collaborating-with-pull-requests/addressing-merge-conflicts/resolving-a-merge-conflict-using-the-command-line

---
<br>

# Different type of versioning

`````col 
````col-md 
flexGrow=2
===

#### Some types of versioning systems are

- Semantic versioning (Major.minor.patch)
- Date of release (Calendar versioning)
- Increment whenever you want

> Linus torvalds
> ![[Pasted image 20241027154750.png]]

```` 
````col-md 
flexGrow=1
===

![[Pasted image 20241027154653.png]]

![[Pasted image 20241027154845.png]]

```` 
`````

`````col 
````col-md 
flexGrow=1
===

- https://semver.org/

```` 
````col-md 
flexGrow=1
===

- https://calver.org/

```` 
````col-md 
flexGrow=1
===

- https://lkml.org/lkml/2019/1/6/178

````
`````

---
<br>

# Thanks for your attention!

![[static-assets-upload2926279536208541530.webp|500]]