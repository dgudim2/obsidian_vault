
>[!note]
> Author: **Danila Gudim**
> Group: **ITvfu-22**
> Completed on: **02.03.2026**

## Models

![[n-models.png]]

Here are the 3 models 
- First one without any redundancy
- Second one with a redndant UPS, PDU and generator
- Third one with a redundant power source and 2N+1 architecture

## Failure scenarios

### Scenario A: Failure of the primary PDU

- ! **Model 1**: The servers *shut down* because the is no backup PDU, there is some downtime
- $ **Model 2**: The servers *keep running* because there is a backup PDU
- $ **Model 3**: The servers *keep running* because there is a backup PDU

### Scenario B: Failure of one UPS unit

- ! **Model 1**: The servers *shut down* because the is no backup UPS, there is some downtime
- $ **Model 2**: The servers *keep running* because there is a backup UPS
- $ **Model 3**: The servers *keep running* because there are 2 backup UPSes

### Scenario C: Complete power failure in one path

- ! **Model 1**: The servers *shut down* because the system relies on one power source only, there is some downtime
- ! **Model 2**: The servers *shut down* because the system relies on one power source only, there is some downtime
- $ **Model 3**: The servers *keep running* because there is a secondary backup circuit

## Questions

### Why is 2N+1 considered the most reliable but costly model?

Since it provides the most reliable and fault tolerant power distribution but requires you to basically duplicate all of the power infrastructure + 1 extra

### How does power redundancy impact overall data center availability?

Higher redundancy means higher availability because there are less ways the power can fail

### What are the cost implications of implementing each model?

- Model 1 - minimal, only basic infrastructure
- Model 2 - marginal, only 1 extra generator, PDU and a UPS are required, switchgear is not duplicated
- Model 3 - higher, everyhting is duplicated + 1 extra unit

### How do organizations decide the appropriate redundancy level?

Depending on what services are hosted, different redundancy levels are appropriate. For example you don't need 99.999 uptime for a file sharing service, but do need for a banking system. Higher redundancy is more expensive as well, the organisation may be cost constrained.