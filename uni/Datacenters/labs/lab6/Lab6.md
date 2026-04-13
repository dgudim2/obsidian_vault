>[!note]
> Author: **Danila Gudim**
> Group: **ITvfu-22**
> Completed on: **13.04.2026**

## The setup

Current setup: **4x** *Dell PowerEdge T360*, each one running one service.


| Service                 | cpu load % | specifics                         |
| ----------------------- | ---------- | --------------------------------- |
| Active Directory/DNS    | 5-10       | Usually light                     |
| File sharing            | 10-15      | More storage-heavy than CPU-heavy |
| Web/application hosting | 10-20      | Bursty load                       |
| Small database system   | 15-30      | More CPU-sensitive than others    |

The CPU load is minimal for each of them, so it makes sense to consolidate them into one physical server

![[4apps-physical_hosts.excalidraw.svg]]


## Virtualization


| Metric                             | Before | After |     |
| ---------------------------------- | ------ | ----- | --- |
| Number of physical servers         | 4      | 1     |     |
| Number of service instances        | 4      | 4     |     |
| Rack / floor space                 | 18U    | 4.5U  |     |
| Estimated power and cooling demand | 500W   | 150W  |     |
|                                    |        |       |     |
Virtual CPU cores are going to be allocated according to this guide:

| Role                 | Starting | When to increase                                           |
| -------------------- | -------- | ---------------------------------------------------------- |
| DNS/DHCP             | 2        | Only if CPU stays high and service is degraded             |
| File server          | 2-4      | Increase with user demand                                  |
| Web server           | 2        | Increase with webapp demands/user depands                  |
| Database server      | 4        | Increase if queries become CPU-bound, use lower overcommit |
| Backup/monitoring    | 2-4      | Increase if jobs overlap                                   |
| Management/jump host | 1-2      | Usually no need                                            |

![[4apps-virtual-host.excalidraw.svg]]

## Questions for Discussion

> Why is a dedicated physical server for each low-load service often inefficient?

Because it's very rare that one service will utilize the entire server so your CPU will be idling most of the time.

> Why should vCPU assignment begin conservatively rather than assigning the maximum possible number of vCPUs to every VM?

To decrease contention and guard against a VM eating all of the CPU time.

> What is the difference between physical cores, logical processors, and allocated vCPU?

Physical cores are well, physical cores on the die, logical cores are threads and vCPU are just virtual cpus you VM sees

> Why can light infrastructure workloads usually tolerate more vCPU overcommit than database workloads?

Because they are not very cpu sensitive and don't need it all the time

> What risks arise when several business-critical services are consolidated onto one host?

If that host fails it's a big issue and your business is impacted if you don't have a copy running somewhere

> How does server consolidation affect power usage, cooling demand, and physical space?

They all go down since you you are decreasing the number of physical hosts. 4 machines running at 10% CPU load will consume more than 1 machine running at 40% CPU

> Under what circumstances should an organization avoid aggressive vCPU overcommit?

If they are running CPU sensitive services like databases

> How would the design change if the database workload became CPU-intensive?

Disable overcommit, enable CPU pinning for that VM