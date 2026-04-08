>[!note]
> Author: **Danila Gudim**
> Group: **ITvfu-22**
> Completed on: **26.03.2026**

I ran the clustering on 70k samples only, because of the memory constrains

## Intro

**SSD** - lower is better
**Silhoutte** - higher is better
**Bouldin** - lower is better
**Harabasz** - higher is better

> Read more: https://medium.com/@a.cervantes2012/interpreting-and-validating-clustering-results-with-k-means-e98227183a4d

## K-Means

`````col 
````col-md 
flexGrow=2
===

![[Pasted image 20260401220909.png]]

```` 
````col-md 
flexGrow=1
===

In this case I would choose 9 or 13 clusters since they show the best parameters

```` 
`````

## K-Medoids

`````col 
````col-md 
flexGrow=2
===

![[Pasted image 20260402211645.png]]

```` 
````col-md 
flexGrow=1
===

Here I would choose 9 or 10 clusters

```` 
`````

## SOM

`````col 
````col-md 
flexGrow=2
===

![[Pasted image 20260401220948.png]]

```` 
````col-md 
flexGrow=1
===

Here I would choose 9 clusters

```` 
`````

## CLARA

`````col 
````col-md 
flexGrow=2
===

![[Pasted image 20260401221149.png]]

```` 
````col-md 
flexGrow=1
===

Here I would choose 12 clusters

```` 
`````

## CLARANS

`````col 
````col-md 
flexGrow=2
===

![[Pasted image 20260402211307.png]]

```` 
````col-md 
flexGrow=1
===

Here, a version with 12 clusters looks suprisingly good, maybe something is not right in the training/testing setup or the dataset

```` 
`````

## DBSCAN

`````col 
````col-md 
flexGrow=2
===

![[Pasted image 20260401221218.png]]

```` 
````col-md 
flexGrow=1
===

> [!note] 
> Here the k value is not number of clusters, but *eps* value

I would choose a value of 13 or even higher, which corresponds to about 8-9 clusters

```` 
`````

## Agglomerative

`````col 
````col-md 
flexGrow=2
===

![[Pasted image 20260401223302.png]]

```` 
````col-md 
flexGrow=1
===

Again, 9 or 12 clusters judging by the characteristic

```` 
`````