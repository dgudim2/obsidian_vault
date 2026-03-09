
>[!note]
> Author: **Danila Gudim**
> Group: **ITvfu-22**
> Completed on: **09.03.2026**

> Links
> - https://lab4-en.dclab.lt
> - https://www.nordpoolgroup.com/en/

## Task 1

> Change the PuE from 1.8 to 1.3 for a 6 MW IT power data center.
> How much money would be saved per year? (Use the aggregate electricity price from NordPool on 03-01)

> Aggregated price is 102.35€/MW

`````col 
````col-md 
flexGrow=1
===

### PuE of 1.8

![[Pasted image 20260309192037.png]]

*Electricity use (24/7, 365 days per year)*
- Annual IT energy use:        **52,735** MWh
- Annual total DC consumption: **94,871** MWh
- Annual electricity bill:     **9,710,026** €
- Monthly bill (average):      **809,169** €

```` 
````col-md 
flexGrow=1
===

### PuE of 1.3

![[Pasted image 20260309192339.png]]

*Electricity use (24/7, 365 days per year)*
- Annual IT energy use: **52,735** MWh
- Annual total DC consumption: **68,503** MWh
- Annual electricity bill: **7,011,303** €
- Monthly bill (average): **584,275** €

```` 
`````


Total savings per year: **2,698,723** €

## Task 2

> How many years would it take to recoup the investment in a 4 MW IT power data center if it costs €500,000 but reduces PuE from 1.6 to 1.3? (use the aggregate electricity price from NordPool on 03-01)


Yearly price for a *4 MW* IT power Datacenter with a pue or *1.6* is **5,747,116** €
Yearly price for a *4 MW* IT power Datacenter with a pue or *1.3* is **4,662,247** €

The difference is **1,084,869**, so if it costs **500,000** €, the investment will be recouped in *~half a year* (0.46 of a year)

## Task 3

> How do annual energy costs differ if we consider half of the year to be "winter" and half to be "summer"? Model this (use the aggregated electricity price from NordPool on 03-01).


We will calculate this assuming that the energy price is **60€/MW** for summer and **120€/MW** for winter. The cooling will be 2x cheaper for the winter months as well since the air is colder and the system is more efficient

`````col 
````col-md 
flexGrow=1
===

### Without splitting

Price: **102.35** €/MW 

For a **5MW** IT power datacenter with a regular *PuE* or **1.4** and cooling power at **1.15 MW**, this gives us **6,276,102** €/Year

```` 
````col-md 
flexGrow=1
===

### With splitting

If we apply the mentioned assumptions for the summer and winter periods, the cost will be
- **3,679,200** €/Year for the summer
- **6,748,704** €/Year for the winter

Total per year is 3,679,200/2 + 6,748,704/2 = **5,213,952** €

> This is cheaper than if we calculate the price using the yearly average price per MW by **1,062,150** €

```` 
`````



