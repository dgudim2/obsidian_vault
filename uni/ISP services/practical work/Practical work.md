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
> There are packets without a recognized protocol (about 50%), those are applications exchanging raw data over the **TCP/UDP** connections, those are most likely *BitTorrent* or websites fetching data from an *API*


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
> We can see some **DHT** packet captures, but none are decoded correctly, which suggests a missclassification
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
> 
> ![[Pasted image 20251004191232.png]]
> > IMG 11 - ARP


## Task 3 - Protocol overhead

> Set header and payload lengths in numerical (bit) and time axes. Link the received data with each other, and evaluate redundant information and its impact on the overall Internet flow. The obtained results must be presented in the form of tables and graphs, summarizing them.

> [!note] 
> I am going to analyze **TCP** traffic, since a significant majority of captured traffic is **TCP**

> [!info] 
> **[[#Ethernet]]** header: *14 bytes*
> **[[#IPv4]]** header: *20 bytes*
> **[[#TCP]]** header: *variable* (22.5 bytes on avg)
> 
> **Total avg header length**: $(14 + 20 + 22.5) * 8 = 452$ *bits*

1. Add a field to see the header size

![[Pasted image 20250928183359.png]]
> Img 12 - adding TCP header length

2. Apply *TCP* filter and export the data into an *sqlite* DB

### Analyzing TCP header size


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

|TCP header length|count(*)|percentage|
|---|---|---|
|20|42803|80.25|
|32|9217|17.28|
|44|349|0.65|
|28|344|0.64|
|40|220|0.41|
|52|192|0.36|
|60|11|0.02|
|48|8|0.01|
|24|1|0.00|

````

`````

![[Pasted image 20250928184803.png]]
> IMG 13 - Tcp header sizes

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
> IMG 14 - TCP payload and header sizes

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
> For small packets the overhead will be *bigger* and for bigger ones - *smaller*

```` 
`````

> [!analysis] 
> 
> In this particular capture, overhead is not that big in comparison with the average payload size of *819* bytes

## Task 4 - QoS parameters

> Set and analyze QoS parameters (TOS, DiffServ, ...). Determine the deviation of sending, receiving, and delaying individual traffic components in a packet. Use mathematical and statistical means to estimate the minimums and maximums and deviations of these characteristics. Summarize the obtained results.

> https://en.wikipedia.org/wiki/Differentiated_services

> [!note] 
> In this section, I am going to analyze **TCP** and **UDP** traffic

1. Add custom columns to view DSCP (**D**ifferentiated **s**ervices **c**ode **p**oint) and TOS (**T**ype **o**f **s**ervice)

![[Pasted image 20250928203621.png]]
> Img 14 - adding TCP header length

2. Apply *TCP + UDP* filter and export the data into an *sqlite* DB

### Analyzing protocol distribution

`````col 
````col-md 
flexGrow=1
===

```sql
select Protocol, count(*),
printf("%.2f", (count(*) / (select count(*) + 0.0 from all_tcp_udp_qos)) * 100.0) as percentage
from all_tcp_udp_qos
group by Protocol 
having count(*) > 10
order by count(*) desc
```

```` 
````col-md 
flexGrow=1
===

|Protocol|count(*)|percentage|
|---|---|---|
|TCP|51591|96.72|
|SSHv2|738|1.38|
|BitTorrent|737|1.38|
|DNS|100|0.19|
|FTP|74|0.14|
|BT-DHT|49|0.09|
|UDP|25|0.05|
|ICMP|14|0.03|

```` 
`````

> [!analysis] 
> 
> Most of our packets (*96.7%*) are either raw *TCP* or some *higher level* protcols on top of *TCP* (FTP/SSH)

> [!note] 
> Only the **DSCP value** is set in our capture, so that's what we will be analyzing

### QoS overview

`````col 
````col-md 
flexGrow=1
===

```sql
select "DSCP Value", count(distinct Protocol), 
CASE WHEN count(distinct Protocol) < 4 then string_agg(Protocol, ',') END,
count(*),
printf("%.2f", (count(*) / (select count(*) + 0.0 from all_tcp_udp_qos)) * 100.0) as percentage
from all_tcp_udp_qos 
group by "DSCP Value"
order by count(*) desc
```

```` 
````col-md 
flexGrow=1
===

|DSCP Value|count(distinct Protocol)|Protocols|Num packets|Percentage|
|---|---|---|---|---|
|CS0|11||30241|56.69|
|2|1|TCP|8992|16.86|
|CS2|7||8613|16.15|
|AF11|2|TCP,SSHv2|4702|8.82|
|AF21|3|TCP,BitTorrent,BT-DHT|789|1.48|
|CS6|1|ICMP|2|0.00|
|CS1|1|ICMP|1|0.00|

```` 
`````

> [!analysis] 
> 
> - Most of our packets (*56.7%*) are **CS0** (Standard service class, precedence of 0)
> - ~9k *TCP* packets (*16.9%*) have a *DSCP* value of **2**, which I think is also **CS0**, **LE** or maybe something custom
> - ~8.5k packets (7 different protocols) (*16.2%*) are **CS2** (Network administration and management, precedence of 2)
> - 4.7k *TCP* packets (*8.8%*) are **AF11** (Assured forwarding 11 (class 1, low drop, elastic), High-throughput data)
> - 789 packets (*1.48%*) are **AF21** (Assured forwarding 21 (class 2, low drop, elastic), Low-latency data)
> - 3 *ICMP* packets with **CS1** (Low-priority data, precedence of 1) and **CS6** (Network control, precedence of 6)


### QoS of particular protocols

> [!note] 
> All other protocols not listed here are either **CS0** or **CS2** with some **AF21** in *BitTorrent*

#### TCP

`````col 
````col-md 
flexGrow=1
===

```sql
select "DSCP Value",
count(*),
printf("%.2f", (count(*) / (select count(*) + 0.0 from all_tcp_udp_qos where Protocol = 'TCP')) * 100.0) as percentage
from all_tcp_udp_qos
where Protocol = 'TCP'
group by "DSCP Value"
order by count(*) desc
```

```` 
````col-md 
flexGrow=1
===

|DSCP Value|count(*)|percentage|
|---|---|---|
|CS0|29403|55.12|
|2|8992|16.86|
|CS2|8300|15.56|
|AF11|4220|7.91|
|AF21|676|1.27|

```` 
`````

`````col 
````col-md 
flexGrow=1
===

```` 
````col-md 
flexGrow=1
===

```` 
`````
#### SSH (TCP)


`````col 
````col-md 
flexGrow=1
===

```sql
select "DSCP Value",
count(*),
printf("%.2f", (count(*) / (select count(*) + 0.0 from all_tcp_udp_qos where Protocol = 'SSHv2')) * 100.0) as percentage
from all_tcp_udp_qos
where Protocol = 'SSHv2'
group by "DSCP Value"
order by count(*) desc
```

```` 
````col-md 
flexGrow=1
===

|DSCP Value|count(*)|percentage|
|---|---|---|
|AF11|482|65.31|
|CS0|256|34.69|

```` 
`````

#### ICMP

`````col 
````col-md 
flexGrow=1
===

```sql
select "DSCP Value",
count(*),
printf("%.2f", (count(*) / (select count(*) + 0.0 from all_tcp_udp_qos where Protocol = 'ICMP')) * 100.0) as percentage
from all_tcp_udp_qos
where Protocol = 'ICMP'
group by "DSCP Value"
order by count(*) desc
```

```` 
````col-md 
flexGrow=1
===

|DSCP Value|count(*)|percentage|
|---|---|---|
|CS2|11|78.57|
|CS6|2|14.29|
|CS1|1|7.14|

```` 
`````

### Timings and jitter (deviation)

> [!note] 
> I am going to analyze **TCP** traffic, since a significant majority of captured traffic is **TCP**

1. Add delta time column to wireshark

![[Pasted image 20251004215738.png]]
> Img 15 - adding TCP delta time

2. Apply *TCP* filter and export the data into an *sqlite* DB

#### Delta distribution

```sql
select 
printf("%.2f", (COUNT(CASE WHEN "DSCP Value" = 'CS0' THEN 1 END) / (select count(*) + 0.0 from 'all-tcp' where  "DSCP Value" = 'CS0')) * 100.0) as "CS0 timings", 
printf("%.2f", (COUNT(CASE WHEN "DSCP Value" = '2' THEN 1 END) / (select count(*) + 0.0 from 'all-tcp' where  "DSCP Value" = '2')) * 100.0) as "2 timings", 
printf("%.2f", (COUNT(CASE WHEN "DSCP Value" = 'CS2' THEN 1 END) / (select count(*) + 0.0 from 'all-tcp' where  "DSCP Value" = 'CS2')) * 100.0) as "CS2 timings", 
printf("%.2f", (COUNT(CASE WHEN "DSCP Value" = 'AF11' THEN 1 END) / (select count(*) + 0.0 from 'all-tcp' where  "DSCP Value" = 'AF11')) * 100.0) as "AF11 timings", 
printf("%.2f", (COUNT(CASE WHEN "DSCP Value" = 'AF21' THEN 1 END) / (select count(*) + 0.0 from 'all-tcp' where  "DSCP Value" = 'AF21')) * 100.0) as "AF21 timings", 
tcp_delta_time from 'all-tcp' 
where tcp_delta_time > 0
and tcp_delta_time > 0.00001
and tcp_delta_time < 2
group by trunc(tcp_delta_time * 10)
order by tcp_delta_time desc
```

`````col 
````col-md 
flexGrow=1
===

##### CS0, 2, CS2

![[Pasted image 20251004222854.png]]
> Img 16 - CS0, 2, CS2 delta distribution

```` 
````col-md 
flexGrow=1
===

##### AF11, AF21

![[Pasted image 20251004222923.png]]
> Img 17 - AF11, AF21 delta distribution

```` 
`````


#### CS0 vs AF11

![[Pasted image 20251004223314.png]]
> Img 18 - CS0 vs AF11

#### Averages and deviation

`````col 
````col-md 
flexGrow=1
===

```sql
select 
	count(*) as num_packets,
	"DSCP Value" as dscp,
	printf("%.5f", avg_) as avg_,
	printf("%.5f", sum((tcp_delta_time - avg_) * (tcp_delta_time - avg_)) / (COUNT(tcp_delta_time) - 1)) AS Deviation,
	printf("%.5f", min(tcp_delta_time)) as min_,
	printf("%.5f", max(tcp_delta_time)) as max_
	from 'all-tcp' src 
left join
	(
		select 
			"DSCP Value" as dscp,
			avg(tcp_delta_time) as avg_
		from 'all-tcp' 
		where tcp_delta_time > 0 
		group by "DSCP Value" 
	) avgs
	on avgs.dscp = src."DSCP Value"
where tcp_delta_time > 0 
group by "DSCP Value"
```

```` 
````col-md 
flexGrow=1
===

|num_packets|dscp|avg_|Deviation|min_|max_|
|---|---|---|---|---|---|
|8992|2|0.00007|0.00000|0.00000|0.03898|
|4701|AF11|0.03121|0.01333|0.00000|6.80143|
|786|AF21|0.35945|0.85365|0.00001|11.48556|
|29899|CS0|0.11114|1.50789|0.00000|47.98444|
|8546|CS2|0.38031|2.87614|0.00000|36.96510|

![[Pasted image 20251004225628.png]]
> Img 19 - DSCP deviations

```` 
`````


> [!analysis] 
> 
> From all those graphs we can make several conclusions
> 
> 1. **CS2** has the highest deviation and not the best average, this is seen in the last graph and in the delta distribution graph (most gradual fall-off)
> 2. **CS0** has the second highest deviation and lower average than **CS2**, this is seen in the last graph and in the delta distribution graph (less gradual, sharper fall-off than **CS2**)
> 3. **AF21** average is almost the same as **CS2** average, but the deviation is way lower (*3.4x* lower) (seen in the delta distribution as a more smoothed-out fall-off), which suggests that the packets are delivered more reliably
> 4. **AF11** has the lowest average and deviation
> 5. The mysterious **2** class has average and deviation close to 0, looking at wireshark, all packets with this value are between *FoxConn* and *DLink* devices mentioned at the beginning of the report, this suggests that they are connected with an ethernet cable directly with no routing equipment in between.

## Task 5 - Flow rates

> Determine the rate of individual flow components and the total flow rate. To evaluate speed minimum, maximum and deviations by mathematical and statistical means. Summarize the obtained results.

> [!note]
> I'm going to analyze communication between *2 Dlink* devices and the *Foxconn* device mentioned at the start of the report

### Dlink_99 and Foxconn

#### Send/receive graph

`````col 
````col-md 
flexGrow=1
===

```sql
select 
	t_out.time_bucket,
	bytes_per_sec_log_out,
	bytes_per_sec_log_in,
	packets_out,
	packets_in
from
(
	select 
		trunc(Time) as time_bucket,
		log(2, sum(Length)) as bytes_per_sec_log_out,
		log(2, count(*)) as packets_out
	from from_dlink99_to_fox
	group by trunc(Time)
) t_out
left join
(
	select 
		trunc(Time) as time_bucket,
		log(2, sum(Length)) as bytes_per_sec_log_in,
		log(2, count(*)) as packets_in
	from from_fox_to_dlink99
	group by trunc(Time)
) t_in
on t_in.time_bucket = t_out.time_bucket

```

```` 
````col-md 
flexGrow=1
===

> [!note] 
> 
> I'm using *logarithmic scale* here for better visuals

![[Pasted image 20251005152815.png]]
> Img 20 - Dlink_99 and foxconn send/receive graphs

```` 
`````

> [!analysis] 
> 
> We can see that there was a <u>short burst of packets</u> at the start, reaching **20 Mebibytes/s** and *~13k* packets/s, and from *40 to 47 seconds* packets from *foxconn* were a bit bigger (number of packets stayed the about same, but bytes/s went up to **8Kib/s**). Other than that, network traffic is stable and *quite low* at about **2Kib/s** and between *8 and 16* packets/s
#### Min/max/avg

##### Bytes

```sql
with t_out as (
	select 
		trunc(Time) as time_bucket,
		sum(Length) as bytes_per_sec_out,
		count(*) as packets_out
	from from_dlink99_to_fox
	group by trunc(Time)
),
t_in as (
	select 
		trunc(Time) as time_bucket,
		sum(Length) as bytes_per_sec_in,
		count(*) as packets_in
	from from_fox_to_dlink99
	group by trunc(Time)
),
avgs as (
select 
	avg(bytes_per_sec_in) as avg_bytes_per_sec_in,
	avg(bytes_per_sec_out) as avg_bytes_per_sec_out,
	
	min(bytes_per_sec_in) as min_bytes_per_sec_in,
	min(bytes_per_sec_out) as min_bytes_per_sec_out,
	
	max(bytes_per_sec_in) as max_bytes_per_sec_in,
	max(bytes_per_sec_out) as max_bytes_per_sec_out
from
t_out left join t_in on t_in.time_bucket = t_out.time_bucket
GROUP by true
)
select 
	printf("%.2f", sum((bytes_per_sec_in - avg_bytes_per_sec_in) * (bytes_per_sec_in - avg_bytes_per_sec_in)) 
		/ (COUNT(bytes_per_sec_in) - 1) / 1024) 
		AS bytes_per_sec_in_deviation,

	printf("%.2f", avg_bytes_per_sec_in / 1024) as avg_kibibytes_per_sec_in,
	printf("%.2f", min_bytes_per_sec_in / 1024) as min_kibibytes_per_sec_in,
	printf("%.2f", max_bytes_per_sec_in / 1024) as max_kibibytes_per_sec_in,
	
	printf("%.2f", sum((bytes_per_sec_out - avg_bytes_per_sec_out) * (bytes_per_sec_out - avg_bytes_per_sec_out)) 
	/ (COUNT(bytes_per_sec_out) - 1) / 1024) 
	AS bytes_per_sec_out_deviation,
	
	printf("%.2f", avg_bytes_per_sec_out / 1024) as avg_kibibytes_per_sec_out,
	printf("%.2f", min_bytes_per_sec_out / 1024) as min_kibibytes_per_sec_out,
	printf("%.2f", max_bytes_per_sec_out / 1024) as max_kibibytes_per_sec_out
from t_out 
left join t_in on t_in.time_bucket = t_out.time_bucket
left join avgs on true

```

##### Packets

```sql
with t_out as (
	select 
		trunc(Time) as time_bucket,
		count(*) as packets_out
	from from_dlink99_to_fox
	group by trunc(Time)
),
t_in as (
	select 
		trunc(Time) as time_bucket,
		count(*) as packets_in
	from from_fox_to_dlink99
	group by trunc(Time)
),
avgs as (
select 	
	avg(packets_in) as avg_packets_in,
	avg(packets_out) as avg_packets_out,
	
	min(packets_in) as min_packets_in,
	min(packets_out) as min_packets_out,
	
	max(packets_in) as max_packets_in,
	max(packets_out) as max_packets_out
from
t_out left join t_in on t_in.time_bucket = t_out.time_bucket
GROUP by true
)
select 
	printf("%.2f", sum((packets_in - avg_packets_in) * (packets_in - avg_packets_in)) 
		/ (COUNT(packets_in) - 1) / 1024) 
		AS packets_in_deviation,

	printf("%.2f", avg_packets_in / 1024) as avg_kpackets_in,
	printf("%.2f", min_packets_in / 1024) as min_kpackets_in,
	printf("%.2f", max_packets_in / 1024) as max_kpackets_in,
	
	printf("%.2f", sum((packets_out - avg_packets_out) * (packets_out - avg_packets_out)) 
	/ (COUNT(packets_out) - 1) / 1024) 
	AS packets_out_deviation,
	
	printf("%.2f", avg_packets_out / 1024) as avg_kpackets_out,
	printf("%.2f", min_packets_out / 1024) as min_kpackets_out,
	printf("%.2f", max_packets_out / 1024) as max_kpackets_out
from t_out 
left join t_in on t_in.time_bucket = t_out.time_bucket
left join avgs on true

```

##### From foxconn to dlink99

| bytes_per_sec_in_deviation | avg_kibibytes_per_sec_in | min_kibibytes_per_sec_in | max_kibibytes_per_sec_in |
| -------------------------- | ------------------------ | ------------------------ | ------------------------ |
| 2057946.94                 | 7.63                     | 0.00                     | 379.00                   |

| packets_in_deviation | avg_kpackets_in | min_kpackets_in | max_kpackets_in |
| -------------------- | --------------- | --------------- | --------------- |
| 708.07               | 0.12            | 0.00            | 7.00            |

##### From dlink99 to foxconn

|bytes_per_sec_out_deviation|avg_kibibytes_per_sec_out|min_kibibytes_per_sec_out|max_kibibytes_per_sec_out|
|---|---|---|---|
|5462468842.03|320.47|0.00|19618.00|

|packets_out_deviation|avg_kpackets_out|min_kpackets_out|max_kpackets_out|
|---|---|---|---|
|2709.87|0.23|0.00|13.00|
> [!analysis] 
> 
> - Maximum data rate is almost **20 Mebibytes/s** (*13k packets/sec*) from dlink to foxconn and **379 Kibibytes/s** (*7k packets/s*) from foxconn to dlink
> - Minimum is 0 for both directions, there were times where no device was sending packets
> - Average data rate is **320 Kibibytes/s** (*230 packets/s*) from dlink to foxconn and **7.6 Kibibytes/s** (*120 packets/s*) from foxconn to dlink

### Dlink_99 and Dlink_66

> [!note] 
> 
> I won't be pasting queries, they are the same, just use different tables

#### Send/receive graph

![[Pasted image 20251005180821.png]]
> Img 21 - Dlink_99 and Dlink_66 send/receive graphs

> [!analysis] 
> 
> Here we can see that traffic <u>slowly increases with time</u>, *dips* an the end *and returns* to the baseline. Also, after about **38 seconds** packet sizes going *from dlink99 to dlink66* <u>increase</u> and almost reach the size of packets in the other direction.

#### Min/max/avg

##### From from dlink99 to dlink66

|bytes_per_sec_in_deviation|avg_kibibytes_per_sec_in|min_kibibytes_per_sec_in|max_kibibytes_per_sec_in|
|---|---|---|---|
|1136428.32|42.75|1.00|142.00|

| packets_in_deviation | avg_packets_in | min_packets_in | max_packets_in |
| -------------------- | -------------- | -------------- | -------------- |
| 1164.22              | 79.74          | 15.00          | 148.00         |

##### From from dlink66 to dlink99

| bytes_per_sec_out_deviation | avg_kibibytes_per_sec_out | min_kibibytes_per_sec_out | max_kibibytes_per_sec_out |
| --------------------------- | ------------------------- | ------------------------- | ------------------------- |
| 1759595.28                  | 93.48                     | 1.00                      | 212.00                    |

| packets_out_deviation | avg_packets_out | min_packets_out | max_packets_out |
| --------------------- | --------------- | --------------- | --------------- |
| 1390.97               | 88.89           | 6.00            | 188.00          |

> [!analysis] 
> 
> - Maximum data rate is **142 Kibibytes/s** (*148 packets/sec*) from dlink99 to dlink66 and **212 Kibibytes/s** (*118 packets/s*) from dlink66 to dlink99
> - Minimum is > 0 for either directions, the communication was more consistent then between foxconn and dlink
> - Average data rate is **43 Kibibytes/s** (*80 packets/s*) from dlink99 to dlink66 and **94 Kibibytes/s** (*89 packets/s*) from dlink66 to dlink99
> - Comparing to the previous flow, communication speed is more consistent and the packet sizes are bigger

### Total flow

#### Speed graph

`````col 
````col-md 
flexGrow=1
===

```sql
select 
	trunc(Time) as time_bucket,
	log(2, sum(Length)) as bytes_per_sec_log,
	log(2, count(*)) as packets
from 'all-tcp'
group by trunc(Time)
```

```` 
````col-md 
flexGrow=2
===

![[Pasted image 20251005183726.png]]

```` 
`````

> [!analysis] 
> 
> Total graph is a sum of the 2 most prominent flows analyzed above: traffic is stable except for 1 burst at the start and a dip at the end

#### Min/max/avg

```sql
with bucketed as (
	select 
		trunc(Time) as time_bucket,
		sum(Length) as bytes_per_sec,
		count(*) as packets
	from 'all-tcp'
	group by trunc(Time)
),
avgs as (
select 	
	avg(packets) as avg_packets,
	min(packets) as min_packets,
	max(packets) as max_packets,
	
	avg(bytes_per_sec) as avg_bytes_per_sec,
	min(bytes_per_sec) as min_bytes_per_sec,
	max(bytes_per_sec) as max_bytes_per_sec
from
bucketed 
GROUP by true
)
select 

	printf("%.2f", sum((bytes_per_sec - avg_bytes_per_sec) * (bytes_per_sec - avg_bytes_per_sec)) 
	/ (COUNT(bytes_per_sec) - 1) / 1024) 
	AS bytes_per_sec_deviation,
	
	printf("%.2f", avg_bytes_per_sec / 1024) as avg_kibibytes_per_sec,
	printf("%.2f", min_bytes_per_sec / 1024) as min_kibibytes_per_sec,
	printf("%.2f", max_bytes_per_sec / 1024) as max_kibibytes_per_sec,

	printf("%.2f", sum((packets - avg_packets) * (packets - avg_packets)) 
		/ (COUNT(packets) - 1)) 
		AS packets_deviation,

	printf("%.2f", avg_packets) as avg_packets,
	printf("%.2f", min_packets) as min_packets,
	printf("%.2f", max_packets) as max_packets
	
from bucketed 
left join avgs on true
```

|bytes_per_sec_deviation|avg_kibibytes_per_sec|min_kibibytes_per_sec|max_kibibytes_per_sec|
|---|---|---|---|
|2771048911.17|296.98|18.00|20054.00|

|packets_deviation|avg_packets|min_packets|max_packets|
|---|---|---|---|
|3094665.04|347.39|21.00|21441.00|

> [!analysis] 
> 
> Situation here is the same, this is a sum of the 2 most prominent flows analyzed above
> - Maximum data rate: **20 Mebibytes/s** (*21.5k packets/s*)
> - Minimum data rate: **18 Kibibytes/s** (*21 packets/s*)
> - Average data rate: **297 Kibibytes/s** (*347 packets/s*) (matches with our average payload size of ~800 kibibytes)

## Task 6 - Conclusion

> Provide summarized conclusions of whole practical work.

I did basic network traffic analysis on real-life network traffic, determined that most of the traffic was TCP. Determined devices responsible for most of the traffic (2 DLink devices and 1 Foxconn). Calculated protocol overhead (6.5%). Analyzed QoS parameters and how they affect data flow and analyzed data flow speeds. Overall, it was an interesting and practical work with a good collection of different analyses.