
<br><br><br><br><br><br>

`````col 
````col-md 
flexGrow=1
===

```` 
````col-md 
flexGrow=1
===

## Presentation by 
- **Danila Gudim**
- **Alexey Prokopuk**
- **Mykola Moskalevskyi**

````
````col-md 
flexGrow=1
===

````  
`````


---

## Backups? What?


Lets start with the basics

`````col 
````col-md 
flexGrow=1
===


> [!definition] 
> Backup is a **copy** of data **stored somewhere else** used for restoring the system after an unexpected **data loss** event


> [!note] 
> Only the **critical data** is usually backed up, *cache or runtime* data that can be reconstructed on the fly does not always make sense to backup


> [!note] 
> **RAID** is **NOT** a backup, it will protect you from a *physical* failure of one or two drives, but not from any other unexpected data loss events


```` 
````col-md 
flexGrow=1.2
===

![[Pasted image 20260314123456.png]]

```` 
`````

---

## How?


`````col 
````col-md 
flexGrow=1
===

### Influence factors:

- Type of data
- Frequency of data updated
- Importance of the data
- Company strategy 
- Cost constraints
- Etc.

```` 
````col-md 
flexGrow=1
===

### Backup strategy:

General rule of thumb: **3-2-1**

Types of backup strategies:

- Periodic backups
  - Full
  - Incremental
  - Differential
- Streaming backups (replication)

```` 
````col-md 
flexGrow=1
===

### Storage types:

- Cold storage
- Hot storage

### Storage mediums:

- HDDs
- SSDs
- Magnetic tape
- Optical disks

```` 
`````

<br>

`````col 
````col-md 
flexGrow=0.5
===

```` 
````col-md 
flexGrow=2
===
![[Pasted image 20260309214823.png]]
````
````col-md 
flexGrow=0.5
===

````  
`````


---

## Periodic backups

`````col 
````col-md 
flexGrow=1
===
### Things to consider
- Backup type and frequency
- Retention
- Do you want fast back up or fast restore?
  
#### Full
- ! Slow to perform and large
- $ Fast to restore and portable

#### Differential
- ! Longer restore times and less portable
- $ Take less space and fater to perform than full

#### Incremental
- ! Slowest restore time and high cpu usage
- $ Fastest back up time and least storage used


```` 
````col-md 
flexGrow=1
===

![[Pasted image 20260314114726.png]]

```` 
`````

---


## Streaming backups / replication

`````col 
````col-md 
flexGrow=1
===

### Database read-replica

![[Pasted image 20260314115530.png]]

```` 
````col-md 
flexGrow=1
===

![[Pasted image 20260314115619.png]]

```` 
`````

---

## Hot and cold storage

`````col 
````col-md 
flexGrow=1
===

### Hot storage

![[Pasted image 20260314122053.png]]

> - Fast acccess
> - Lives in a running server and is on 24/7

```` 
````col-md 
flexGrow=1
===

### Cold storage

![[Pasted image 20260314122830.png]]

> - Slow access
> - Lives on a storage shelf disconnected from the server

```` 
`````

---

## Hot storage

`````col 
````col-md 
flexGrow=1.5
===

![[Pasted image 20260314124318.png]]

```` 
````col-md 
flexGrow=1
===

![[hdd_nbg.png]]

```` 
`````

```````col 

``````col-md 
flexGrow=1
===
### Hot storage in general

- $ Fast
- $ Dense
- ! Expensive
- ! Lifespan of just several years
`````` 

``````col-md 
flexGrow=1
===
### SSD

- $ Power efficient
- $ Fast
- ! Expensive
- ! Shorter lifespan
`````` 

``````col-md 
flexGrow=1
===
### HDD

- $ More dense
- $ Cheaper and longer-lasting
- ! Less power efficient
- ! Slower especially for random load
`````` 

```````

---

## Cold storage

`````col 
````col-md 
flexGrow=1
===

![[Pasted image 20260314125238.png]]

```` 
````col-md 
flexGrow=1
===

![[Pasted image 20260314125639.png]]

```` 
`````

- ! Slow, less dense
- $ Cheaper for long term storage and in general for LTO
- $ Very long shelf life

---

## Importance of testing backups

![[Pasted image 20260314131756.png]]

---

## Backup compression


`````col 
````col-md 
flexGrow=1
===

![[Pasted image 20260314132145.png]]

```` 
````col-md 
flexGrow=1
===


- Compression of files
- Deduplication across backup points on the FS level
<br>
- Compression ratio depends on the type of data 
- For cold storage a very high compression level can be used


```` 
`````


---

## Backup tools for enterprise

- Native filesystem snapshots if you manage the filesystem and are running on ZFS of BTRFS.
- HYCU
- Rubrik
- Acronis
- etc...

<br>

`````col 
````col-md 
flexGrow=1
===

![[Pasted image 20260314132723.png]]

```` 
````col-md 
flexGrow=0.5
===

![[Pasted image 20260314132747.png]]

```` 
````col-md 
flexGrow=1
===

![[Pasted image 20260314132838.png]]

```` 
`````

---

## Backup tools for you

`````col 
````col-md 
flexGrow=1
===

![[Pasted image 20260314133036.png]]

```` 
````col-md 
flexGrow=1
===

![[Pasted image 20260314133326.png]]

```` 
`````

<br>

`````col 
````col-md 
flexGrow=1
===

![[Pasted image 20260314133433.png|280]]

```` 
````col-md 
flexGrow=1
===

![[Pasted image 20260314133419.png|200]]

```` 
````col-md 
flexGrow=1
===

![[Pasted image 20260314133511.png|280]]

```` 
`````

---

## Note about RAID

`````col 
````col-md 
flexGrow=1
===

#### RAID - **R**edundant **a**rray of **i**nexpensive **d**isks

> Protects agains failure of one or two drives in a storage array, does not protect against anything else 

![[Pasted image 20260314134108.png|300]]

```` 
````col-md 
flexGrow=1
===

![[Pasted image 20260314133709.png]]

```` 
`````

---

`````col 
````col-md 
flexGrow=1
===

```` 
````col-md 
flexGrow=1
===

## The end, questions?

```` 
````col-md 
flexGrow=1
===

```` 
`````

