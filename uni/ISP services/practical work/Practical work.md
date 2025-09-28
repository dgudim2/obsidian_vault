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
> IMG 0.1 - Wireshark conversations analysis / nodes menu

![[Pasted image 20250921222616.png]]
> IMG 0.2 - Wireshark conversations analysis / nodes

`````col 
````col-md 
flexGrow=0.3
===

![[Pasted image 20250921224325.png|400]]
> IMG 0.3 - Wireshark conversations analysis / nodes, raw MACs

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
> IMG 1.1 - Wireshark protocol hierarchy menu

![[Pasted image 20250921231002.png]]
> IMG 1.2 - Wireshark protocol hierarchy

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

> Set *L4* protocol interfaces (*UDP*/*TCP* ports), prevailing *L1*, *L2*, *L3*, *L4* protocols. Present the obtained results in percentage terms. Determine the hierarchical statistics of the OSI levels. The obtained data must be presented in the form of tables and summarized in detail.

### Used ports

We can use the **resolved addresses** tab of wireshark to view all used ports

#### Table - TCP ports

`````col 
````col-md 
flexGrow=1
===

|service-name|port/protocol|
|-|-|
|ftp|21/tcp|
|ssh|22/tcp|
|re-mail-ck|50/tcp|
|http|80/tcp|
|microsoft-ds|445/tcp|
|solid-mux|1029/tcp|
|sbl|1039/tcp|
|netarx|1040/tcp|
|danf-ak2|1041/tcp|
|afrog|1042/tcp|
|boinc-client|1043/tcp|
|dcutility|1044/tcp|
|fpitp|1045/tcp|
|wfremotertm|1046/tcp|
|neod1|1047/tcp|
|neod2|1048/tcp|
|cma|1050/tcp|
|optima-vnet|1051/tcp|
|ddt|1052/tcp|
|remote-as|1053/tcp|
|brvread|1054/tcp|
|ansyslmd|1055/tcp|
|vfo|1056/tcp|
|startron|1057/tcp|
|nim|1058/tcp|
|nimreg|1059/tcp|
|polestar|1060/tcp|
|kiosk|1061/tcp|
|veracity|1062/tcp|
|kyoceranetdev|1063/tcp|
|jstel|1064/tcp|
|syscomlan|1065/tcp|
|fpo-fns|1066/tcp|
|instl-boots|1067/tcp|
|instl-bootc|1068/tcp|
|cognex-insight|1069/tcp|
|gmrupdateserv|1070/tcp|
|bsquare-voip|1071/tcp|
|cardax|1072/tcp|
|bridgecontrol|1073/tcp|
|warmspotMgmt|1074/tcp|
|rdrmshc|1075/tcp|
|dab-sti-c|1076/tcp|
|imgames|1077/tcp|
|avocent-proxy|1078/tcp|
|asprovatalk|1079/tcp|
|socks|1080/tcp|
|pvuniwien|1081/tcp|
|amt-esd-prot|1082/tcp|

```` 
````col-md 
flexGrow=1
===

|service-name|port/protocol|
|-|-|
|ansoft-lm-1|1083/tcp|
|ansoft-lm-2|1084/tcp|
|webobjects|1085/tcp|
|cplscrambler-lg|1086/tcp|
|cplscrambler-in|1087/tcp|
|cplscrambler-al|1088/tcp|
|ff-annunc|1089/tcp|
|ff-fms|1090/tcp|
|ff-sm|1091/tcp|
|obrpd|1092/tcp|
|proofd|1093/tcp|
|rootd|1094/tcp|
|nicelink|1095/tcp|
|cnrprotocol|1096/tcp|
|sunclustermgr|1097/tcp|
|rmiactivation|1098/tcp|
|rmiregistry|1099/tcp|
|mctp|1100/tcp|
|pt2-discover|1101/tcp|
|adobeserver-1|1102/tcp|
|adobeserver-2|1103/tcp|
|xrl|1104/tcp|
|ftranhc|1105/tcp|
|isoipsigport-1|1106/tcp|
|isoipsigport-2|1107/tcp|
|ratio-adp|1108/tcp|
|webadmstart|1110/tcp|
|lmsocialserver|1111/tcp|
|icp|1112/tcp|
|ltp-deepspace|1113/tcp|
|mini-sql|1114/tcp|
|ardus-trns|1115/tcp|
|ardus-cntl|1116/tcp|
|ardus-mtrns|1117/tcp|
|sacred|1118/tcp|
|bnetfile|1120/tcp|
|rmpp|1121/tcp|
|availant-mgr|1122/tcp|
|murray|1123/tcp|
|hpvmmcontrol|1124/tcp|
|hpvmmagent|1125/tcp|
|hpvmmdata|1126/tcp|
|kwdb-commn|1127/tcp|
|saphostctrl|1128/tcp|
|saphostctrls|1129/tcp|
|casp|1130/tcp|
|caspssl|1131/tcp|
|kvm-via-ip|1132/tcp|
|dfn|1133/tcp|
|aplx|1134/tcp|

```` 
````col-md 
flexGrow=1
===





|service-name|port/protocol|
|-|-|
|omnivision|1135/tcp|
|hhb-gateway|1136/tcp|
|trim|1137/tcp|
|encrypted-admin|1138/tcp|
|evm|1139/tcp|
|autonoc|1140/tcp|
|mxomss|1141/tcp|
|edtools|1142/tcp|
|imyx|1143/tcp|
|fuscript|1144/tcp|
|x9-icue|1145/tcp|
|audit-transfer|1146/tcp|
|capioverlan|1147/tcp|
|elfiq-repl|1148/tcp|
|bvtsonar|1149/tcp|
|blaze|1150/tcp|
|unizensus|1151/tcp|
|winpoplanmess|1152/tcp|
|c1222-acse|1153/tcp|
|resacommunity|1154/tcp|
|nfa|1155/tcp|
|iascontrol-oms|1156/tcp|
|iascontrol|1157/tcp|
|dbcontrol-oms|1158/tcp|
|oracle-oms|1159/tcp|
|olsv|1160/tcp|
|health-polling|1161/tcp|
|health-trap|1162/tcp|
|vrts-ipcserver|1317/tcp|
|mc2studios|1899/tcp|
|autocuesmi|3103/tcp|
|mediavault-gui|3673/tcp|
|itose|4348/tcp|
|unify-debug|4867/tcp|
|tritium-can|4876/tcp|
|wmlserver|4883/tcp|
|lyskom|4894/tcp|
|flr-agent|4901/tcp|
|eq-office-4942|4942/tcp|
|webyast|4984/tcp|
|bmc-perf-agent|6767/tcp|
|infotos|18881/tcp|
|dfserver|21554/tcp|
|med-ltp|24000/tcp|
|mysqlx|33060/tcp|
|heathview|35000/tcp|


```` 
`````

> [!note]
> Most of our traffic is **TCP**, so it's expected

#### Table - UDP ports

| service-name  | port/protocol |
| ------------- | ------------- |
| domain        | 53/udp        |
| netbios-ns    | 137/udp       |
| netbios-dgm   | 138/udp       |
| serialgateway | 1243/udp      |
| icap          | 1344/udp      |
| plysrv-https  | 6771/udp      |
| connected     | 16384/udp     |

### Protocol hierarchy (in-depth)

We can use the same **protocol hierarchy** section user earlier to get a more in-depth view into used protocols.

![[Pasted image 20250928144228.png]]
> IMG 2 - Wireshark protocol hierarchy view (in-depth)

##### Table - protocol hierarchy

| Protocol        | OSI layer                                                                   | %         | Port                  |
| --------------- | --------------------------------------------------------------------------- | --------- | --------------------- |
| Frame           | 1 (Physical) ([not a protocol](https://wiki.wireshark.org/Protocols/frame)) | 100       | -                     |
| [[#Ethernet]]   | 2 (Data link)                                                               | *100*     | -                     |
| [[#IPv4]]       | 3 (Network)                                                                 | **99.96** | -                     |
| [[#ARP]]        | 3 (Network)                                                                 | 0.04      | -                     |
| [[#TCP]]        | 4 (Transport)                                                               | **99.6**  | -                     |
| [[#UDP]]        | 4 (Transport)                                                               | 0.3       | -                     |
| [[#ICMP]]       | 4 (Transport)                                                               | 0.1       | -                     |
| [[#NetBIOS]]    | 5-7 (Application)                                                           | <0.1      | 137,138,139           |
| [[#LSD]]        | 5-7 (Application)                                                           | <0.1      | 6771 + 4 random ports |
| [[#DNS]]        | 5-7 (Application)                                                           | 0.2       | 53                    |
| [[#DHT]]        | 5-7 (Application)                                                           | 0.1       | random                |
| [[#SSH]]        | 5-7 (Application)                                                           | 1.4       | 22                    |
| [[#HTTP]]       | 5-7 (Application)                                                           | <0.1      | 80                    |
| [[#FTP]]        | 5-7 (Application)                                                           | 0.1       | 20,21                 |
| [[#BitTorrent]] | 5-7 (Application)                                                           | 1.4       | random                |

> [!note] 
> There are packets without a recognized protocol (about 50%), those are applications exchanging raw data over the **TCP/UDP** connections, those are most likely *BitTorrent*


- $ Here is a tree-like overview (you can also click the protocol names in the table)

### Ethernet

> [!definition] 
> 
> It's a **layer 2** protocol for interconnection. It provides (**MAC**) addressing for routing dataframes between physical devices.
> A **MAC** address is *48-bit* number typically written in 6 hextets separated by colons.
> The protocol is widely used in **LANs**

> [!analysis] 
> 
> All of the captured traffic was transmitted over Ethernet, which means that all the devices are physically connected, most likely with patch cords (Ethernet cables)
>

#### IPv4

> [!definition] 
> **IPv4** (**I**nternet **P**rotocol version 4) is a **layer 3** protocol for interconnection. 
> It provides an addressing system for routing **IP** packets between hosts (not necessarily physical).
> IPv4 uses a **32-bit address** space which provides 4,294,967,296 (2^32) unique addresses (but large blocks are reserved for special networking purposes)

> [!analysis] 
> 
> Almost all the packets in the capture are **IPv4**, there are no **IPv6** packets which shows that either this network is quite old and does not support **IPv6**, or it's simply not needed

##### TCP

> [!definition] 
> **TCP** (**T**ransmission **C**ontrol **P**rotocol) is a **layer 4** protocol providing reliable and error-checked delivery of data between applications, many higher layer protocols rely on it

> [!analysis] 
> Almost all the traffic in the capture is **TCP**, which means that most of the applications used in this time-frame required reliable communication (Web traffic, file transfers, remote desktop connections)

###### SSH

> [!definition] 
> **SSH** (**S**ecure **Sh**ell) This protocol is used for *remote login*

> [!analysis] 
> In our capture we see that **172.31.187.100** logs into and does something on **172.31.187.1**.
> > [!note] 
> > **SSH** is rarely used on *Windows*, so *172.31.187.1* is a *linux* host
> > 
> > ![[Pasted image 20250928165023.png]]
> > > IMG 3 - SSH

###### HTTP

> [!definition] 
> **HTTP** (**Hy**pertext **T**ransfer **P**rotocol). This protocol is used for web communication. 
> 
> > [!note]
> > Plain **HTTP** is not used nowadays, instead **HTTPS** is used, which is a layer (*TLS*) on top of **HTTP** for encryption)

> [!analysis] 
> In our capture we see **77.247.176.151** calling 2 endpoint: *announce* and *scrape* from **172.31.187.1**. Can't tell what those are for, more data/analysis is required
> 
> ![[Pasted image 20250928165708.png]]
> > IMG 4 - HTTP

###### FTP

> [!definition] 
> **FTP** (**F**ile **T**ransfer **P**rotocol) is used for file transfers (As the name suggests)
> > [!note] 
> > Just like **HTTP**, plain **FTP** is rarely used (although still useful in *LANs*), more secure and versatile protocols like **FTPS**, **SFTP** and **NFS** are used 

> [!analysis] 
> In our packet capture we can see **172.31.187.1** connecting to **172.31.187.11** with username **lukas**, failing to login several times, listing files in the home directory and creating **Sony_Demo** file.
> 
> ![[Pasted image 20250928171547.png]]
> > IMG 5 - FTP
> 
> > [!note] 
> > 
> > **172.31.187.1** seems to be a linux host, because the home directory for the user is **/home/lukas**

###### BitTorrent

> [!definition] 
> 
> This protocol is used by torrent client for transferring related data

> [!analysis] 
> In our capture, we see **114.205.219.87.dynamic.jazztel.es** downloading something from **172.31.187.1** (or the other way around)
> 
> ![[Pasted image 20250928171957.png]]
> > IMG 6 - BitTorrent

##### UDP

> [!definition] 
> **UDP** (**U**ser **D**atagram **P**rotocol) is a **layer 4** protocol providing fast, but potentially unreliable delivery of data between applications. Comparing with [[#TCP]], no prior communication is required to send data and it doesn't track what has been received on the other side or sent for that matter

> [!analysis] 
> Only *0.3%* of the packets in our capture use **UDP** and all of them are for some kind of name resolution/discovery. **UDP** is a good protocol to use in this case, discovery/resolution *implies* that some side does not know the whole 'picture' (state).

###### NetBIOS

> [!definition] 
> This is technically not a protocol, but an API, [Read more info here](https://miloserdov.org/?p=4261)
> 
> There are *3* parts to this, *NetBIOS Name Service* (port 137), *NetBIOS Datagram Service* (port 138) and *NetBIOS Session Service* (port 139)
> - The *Name service* part is responsible for local name resolution (like [[#DNS]])
> - The *Datagram service* is responsible for message exchange (connectionless communication)
> - The *Session service* is responsible for session management (connection-oriented communication)

> [!analysis] 
> 
> In our capture we see some queries to **172.31.187.127**. Can't tell what those are for, more data/analysis is required
> 
> ![[Pasted image 20250928173914.png]]
> > IMG 6 - NetBios

###### DNS

- @ From my essay about **DNS**

![[uni/Data communication and networking/DNS essay/DNS#What is DNS and why do we need it|DNS]]

> [!analysis] 
> 
> In our capture we see some requests to **172.31.187.126**, it's running a **DNS** server on default port *53*
> 
> ![[Pasted image 20250928174705.png]]
> > IMG 7 - DNS

###### LSD

> [!definition] 
> 
> **LSD** (**L**ocal **S**ervice **D**iscovery) is a protocol which helps discover which service are present on a host (the host announces them)

> [!analysis] 
> 
> Our capture contains only one packet, but I don't think it's an **LSD** packet, wireshark failed to decode it properly and it the contents we see *BT-SEARCH*, which suggests that it's *bittorrent* protocol, wireshark just misclassified the packet because of the port 
> 
> ![[Pasted image 20250928175542.png]]
> > IMG 8 - Misclassified LSD

###### DHT

> [!definition] 
> 
> **DHT** (**D**istributed **H**ash **T**able) implemented by [Kademlia](https://en.wikipedia.org/wiki/Kademlia) protocol, used for discovering **peers** in a peer-to-peer network

> [!analysis] 
> 
> We can see some **DHT** packet captures, but none are decoded correctly, which suggests a miss-classification
> 
> ![[Pasted image 20250928180252.png]]
> > IMG 9 - Misclassified DHT

##### ICMP

> [!definition] 
> **ICMP** (**I**nternet **C**ontrol **M**essage **P**rotocol) is a **layer 4** protocol, it's used for sending *control*/*status* messages. It's not used to exchange data (typically).

> [!analysis] 
> All of the **ICMP** packets in our capture don't have any response. They all have the same destination: **172.31.187.1** and originate from different hosts. I suspect they were doing some kind of connectivity check.
> 
> ![[Pasted image 20250928161919.png]]
> > IMG 10 - ICMP (failed)

#### ARP

> [!definition] 
> **ARP** (**A**ddress **R**esolution **P**rotocol) is a **layer 3** (or 2) protocol for resolving **IP** addresses to a **MAC** adresses

> [!analysis] 
> We see some **ARP** packets in our capture, which is totally normal. We don't see a lot of them, so the network seems to be configured properly and there are no frequent hardware connects/disconnects.


## Task 3 - Protocol overhead

> Set header and payload lengths in numerical (bit) and time axes. Link the received data with each other, and evaluate redundant information and its impact on the overall Internet flow. The obtained results must be presented in the form of tables and graphs, summarizing them.

> [!note] 
> I am going to analyze **TCP** traffic, since a significant majority of captured traffic is **TCP**

> [!info] 
> **[[#Ethernet]]** header: *14 bytes*
> **[[#IPv4]]** header: *20 bytes*
> **[[#TCP]]** header: *variable* (22.5 bytes on avg)
> 
> **Total header length**: $(14 + 20 + 22.5) * 8 = 452$ *bits*

### Analyzing TCP header size

1. Add a field to see the header size

![[Pasted image 20250928183359.png]]
> Img 11 - adding TCP header length

2. Apply *TCP* filter and export the data into an *sqlite* DB


`````col 
````col-md 
flexGrow=1
===

- For the table
```sql
select TCP_header_length, count(*) 
from 'all-tcp' 
where TCP_header_length is not NULL
group by TCP_header_length
order by count(*) desc
```

- For the graph
```sql
select TCP_header_length || ' bytes', count(*) 
from 'all-tcp' 
where TCP_header_length is not NULL
group by TCP_header_length
order by TCP_header_length desc
```

```` 
````col-md 
flexGrow=1
===

#### Table - TCP header sizes in bytes

|TCP_header_length|count(*)|
|-|-|
|20|42803|
|32|9217|
|44|349|
|28|344|
|40|220|
|52|192|
|60|11|
|48|8|
|24|1|

````

`````

![[Pasted image 20250928184803.png]]
> IMG 12 - Tcp header sizes

> [!analysis] 
> 
> We can see that most of the headers are either *20* or *32* bytes (**98%**), average size is **22.5 bytes**

### Analyzing payload size

We have the packet size: *Length*, we can subtract the header size to get the payload size

`````col 
````col-md 
flexGrow=0.5
===

```sql
select 
sum(payload_size), avg(payload_size), 
max(payload_size), min(payload_size) 
from 
    (select Length - TCP_header_length - 34 as payload_size
        from 'all-tcp' 
    where TCP_header_length is not NULL)
```

```` 
````col-md 
flexGrow=1
===

#### Table - TCP payload size statistics in bytes

|sum(payload_size)|avg(payload_size)|max(payload_size)|min(payload_size)|
|---|---|---|---|
|43524514|818.976648791043|2920|0|

```` 
`````

### Analysing overhead

We can plot the header size (green) and payload size (purple) and see that the overhead is not that big

![[Pasted image 20250928191340.png]]
> IMG 13 - TCP payload and header sizes

We can also calculate it in percent

`````col 
````col-md 
flexGrow=1
===

```sql
select sum((TCP_header_length + 34)) / sum(Length + 0.0) * 100 from 
(select Length, Length - TCP_header_length - 34 as payload_size, TCP_header_length
from 'all-tcp' 
where TCP_header_length is not NULL)
```

```` 
````col-md 
flexGrow=1
===

Average overhead is **6.45%**, which is not nothing, but reasonably low

> [!note] 
> For small packets the overhead will be bigger and for bigger ones - smaller

```` 
`````

## Task 4 - QoS parameters

> Set and analyze QoS parameters (TOS, DiffServ, ...). Determine the deviation of sending, receiving, and delaying individual traffic components in a packet. Use mathematical and statistical means to estimate the minimums and maximums and deviations of these characteristics. Summarize the obtained results.


> [!note] 
> In this section, I am going to analyze **TCP** and **UDP** traffic

### Analyzing QoS parameters

1. Add custom columns to view DSCP (**D**ifferentiated **s**ervices **c**ode **p**oint) and TOS (**T**ype **o**f **s**ervice)

![[Pasted image 20250928203621.png]]

## Task 5 - Flow rates

> Determine the rate of individual flow components and the total flow rate. To evaluate speed minimum, maximum and deviations by mathematical and statistical means. Summarize the obtained results.

## Task 6 - Conclusion

> Provide summarized conclusions of whole practical work.