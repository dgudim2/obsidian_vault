>[!note]
> Author: **Danila Gudim**
> Group: **ITvfu-22**
> Completed on: **21.09.2025**
> File to analyze: **120080923.pcap.gz**

## TOC

```toc
```
## Task 1 - Nodes

> Identify the source and destination nodes. Group them by Internet data flows. Characterize them and provide assessment.

### Conversations

We can use the 'conversations' analysis functions of wireshark. 

![[Pasted image 20250921222043.png|400]]

#### IMG 1 - Wireshark conversations analysis / nodes
![[Pasted image 20250921222616.png]]


`````col 
````col-md 
flexGrow=0.3
===

![[Pasted image 20250921224325.png|400]]

```` 
````col-md 
flexGrow=1
===

I disabled name resolution here to see raw MACs, node order is the same as in the previous table

```` 
`````


> [!note] 
> **Here we can see** 
 > - All the source and destination nodes
 > - Number of sent/received packets
 > - Total bytes sent/received per node pair.
 > - Speed/duration/etc.

> [!analysis] 
> Looking at this table, we can see that most communication took place between **DLink_30:95:66 (00:19:5b:30:95:66)**, **DLink_30:95:99 (00:19:5b:30:95:99)** and **Foxconn_84:23:d8 (00:15:58:84:23:d8)**
> 
> > [!note] 
> > Both *DLink* devices have very similar **MACs** (only last byte is different), this can mean that it's the <u>same device with 2 network cards</u> or 2 devices in the same 'group'

### Protocol hierarchy

We can also use the 'Protocol hierarchy' analysis function

![[Pasted image 20250921230908.png|400]]

#### IMG 2 - Wireshark protocol hierarchy

![[Pasted image 20250921231002.png]]

> [!note] 
> Here we can see the used protocols in hierarchical order, total packets and bytes per protocol, etc.

> [!analysis] 
> Looking at this tree we can see that
> - *100%* of the traffic is **Ethernet**
> - *100%* of the traffic is **IPv4** (except for 17 ARP packets)
> - *99.6%* of the traffic is **TCP**
> - *0.3%* of the traffic is **UDP**
> - The rest (14) packets are **ICMP**

## Task 2 - Protocols

> Set *L4* protocol interfaces (*UDP*/*TCP* ports), prevailing the *L1*, *L2*, *L3*, *L4* protocols. Present the obtained results in percentage terms. Determine the hierarchical statistics of the OSI levels. The obtained data must be presented in the form of tables and summarized in detail.




## Task 3 - Protocol overhead

> Set header and payload lengths in numerical (bit) and time axes. Link the received data with each other, and evaluate redundant information and its impact on the overall Internet ow. The obtained results must be presented in the form of tables and graphs, summarizing them.

## Task 4 - QoS parameters

> Set and analyze QoS parameters (TOS, Di Serv, ...). Determine the deviation of sending, receiving, and delaying individual traffic c components in a packet. Use mathematical and statistical means to estimate the minimums and maximums and deviations of these characteristics. Summarize the obtained results.

## Task 5 - Flow rates

> Determine the rate of individual ow components and the total ow rate. To evaluate speed minimum, maximum and deviations by mathematical and statistical means. Summarize the obtained results.

## Task 6 - Conclusion

> Provide summarized conclusions of whole practical work.