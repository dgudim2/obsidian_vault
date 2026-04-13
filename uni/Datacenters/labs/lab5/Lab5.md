
>[!note]
> Author: **Danila Gudim**
> Group: **ITvfu-22**
> Completed on: **30.03.2026**

> Links
> - https://networkengineering.stackexchange.com/questions/60002/what-is-oversubscription-in-networking
> - https://en.wikipedia.org/wiki/Equal-cost_multi-path_routing
> - https://community.cisco.com/t5/application-centric-infrastructure/why-lateral-connectivity-not-allowed-in-cisco-aci/td-p/5216593

## The diagram

### Specification

- **Spines:** 2 (Spine 1, Spine 2)
- **Leaves:** 5 (Leaf 1 through Leaf 5)
- **Servers:** 2 per leaf (Srv-A and Srv-B, e.g., L1-Srv-A, L1-Srv-B)

`````col 
````col-md 
flexGrow=1
===

![[spine-leaf.png]]

```` 
````col-md 
flexGrow=1
===

### Rule Checklist when implementing spine-leaf topology

- **No Spine-to-Spine or Leaf-to-Leaf links:** Spines do not talk to each other directly (makes re-routing traffic around a failed (e.g. leaf-to-leaf) link in a non-blocking way extremely difficult)  
- **Every Leaf connects to every Spine:** For maximum redundancy.    
- **Servers only connect to Leaves:** Servers never plug directly into a Spine.

```` 
`````

### Connection diagram

| **Server Pair**      | **Spine Used** | **Path**                                 |
| -------------------- | -------------- | ---------------------------------------- |
| L1-Srv-A to L3-Srv-B | Spine 1        | `L1-Srv-A -> L1 -> S1 -> L3 -> L3-Srv-B` |
| L2-Srv-B to L5-Srv-A | Spine 2        | `L2-Srv-B -> L2 -> S2 -> L5 -> L5-Srv-A` |
| L4-Srv-A to L1-Srv-B | Spine 1        | `L4-Srv-A -> L4 -> S1 -> L1 -> L1-Srv-B` |
| L3-Srv-B to L2-Srv-A | Spine 2        | `L3-Srv-B -> L3 -> S2 -> L2 -> L2-Srv-A` |

## Question

> If you add a **third spine** to this environment, how does it affect the uplink count for each leaf?

- Each leaf must have exactly one connection to every spine. Therefore, adding a third spine requires adding **one additional uplink** to every leaf in the fabric (increasing the total uplinks per leaf from 2 to 3).

> If L1-Srv-A sends traffic to L1-Srv-B, which spine does the traffic pass through?
    
- **None**. This is "local" or "intra-rack" traffic. The leaf switch will handle the switching/routing on its own without sending the packet up to the spine layer.
        
> If multiple flows are sent from L1 to L5, how does the fabric ensure both Spines are used?
    
- Using **ECMP (Equal-Cost Multi-Pathing)**. The leaf will distribute different traffic flows across all available paths to the spines.

## Fail scenario

> [!Scenario]
> A technician accidentally pulls the power cables for **Spine 1**, and the switch turns off.

> Is any-to-any connectivity lost between the servers?
    
- **No, because every leaf is connected to both spines**, Spine 2 remains as a valid path.
        
> What is the specific impact on the fabric's total bandwidth (throughput)?
    
- There is a **50% loss in bandwidth**. Connectivity is maintained, but the bandwidth is reduced, which may lead to congestion and service degradation, but not total loss
        
> Does a server on Leaf 1 lose its connection to a server on Leaf 2?
    
- **No, full any-to-any connectivity is preserved**. The routing protocol will route everything through **Spine 2**