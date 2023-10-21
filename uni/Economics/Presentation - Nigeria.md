
# Nigeria

`````col 
````col-md 
flexGrow=1.5
===

![[World-Data-Locator-Map-Nigeria.webp|450]]

```` 
````col-md 
flexGrow=1
===

### Key facts

- A country in West Africa
- Federal republic comprising **36** states
- 923,769 square kilometres
- 230 million people
- Most populous country in Africa
- \> **250** ethnic groups speaking **500** distinct languages
- Capital - **Abuja**
- Largest city - **Lagos**

![[Flag_of_Nigeria.svg]]

```` 
`````

---

# Macroeconomic measures

---

## GDP
```dataviewjs
const data = dv.current()

p = dv.paragraph(`\`\`\`chart
type: line
labels: [1995,1996,1997,1998,1999,2000,2001,2002,2003,2004,2005,2006,2007,2008,2009,2010,2011,2012,2013,2014,2015,2016,2017,2018,2019,2020,2021,2022]
series:
    - title: GDP (current US$)
	  data: [44062465417.9421,51075815092.5,54457835603.1357,54604050168.1818,59372679433.1746,69448962760.8787,74030562269.9655,95054094655.3277,104738980156.958,135764715375.206,175670536601.006,238454952231.572,278260808841.03,339476215683.592,295008767295.038,366990528103.078,414466540786.738,463971000388.612,520117163617.721,574183825592.358,493026782401.561,404649048648.026,375745732274.676,421739210176.152,474517470742.749,432198936002.177,440833583992.485,477386120635.845]
tension: 0.5
transparency: 1
\`\`\``)
```

- 31st in the world
- GDP per capita (2022) is 2184$ / 24826$ in Lithuania

---

## GDP growth

```dataviewjs
const data = dv.current()

dv.paragraph(`\`\`\`chart
type: bar
labels: [1995,1996,1997,1998,1999,2000,2001,2002,2003,2004,2005,2006,2007,2008,2009,2010,2011,2012,2013,2014,2015,2016,2017,2018,2019,2020,2021,2022]
series:
    - title: GDP growth (annual %)
	  data: [-0.0726647666765956,4.19592404526841,2.937099419752,2.58125410282553,0.584126894585225,5.01593475720539,5.91768465163287,15.3291557381864,7.34719497034284,9.25055822849694,6.43851652509105,6.05942803125548,6.59113036073542,6.764472777848,8.03692510189684,8.00565591528178,5.30792420366642,4.23006117510553,6.67133539288378,6.30971865572383,2.65269329541835,-1.61686894991816,0.805886619542704,1.92275734157302,2.2084292771582,-1.79425308233591,3.64718654100696,3.25168140839833]
tension: 0.5
transparency: 1
\`\`\``)
```

---
## GDP comparison
  
```dataviewjs
const data = dv.current()

dv.paragraph(`\`\`\`chart
type: line
labels: [1995,1996,1997,1998,1999,2000,2001,2002,2003,2004,2005,2006,2007,2008,2009,2010,2011,2012,2013,2014,2015,2016,2017,2018,2019,2020,2021,2022]
series:
    - title: GDP of Nigeria (current US$)
	  data: [44062465417.9421,51075815092.5,54457835603.1357,54604050168.1818,59372679433.1746,69448962760.8787,74030562269.9655,95054094655.3277,104738980156.958,135764715375.206,175670536601.006,238454952231.572,278260808841.03,339476215683.592,295008767295.038,366990528103.078,414466540786.738,463971000388.612,520117163617.721,574183825592.358,493026782401.561,404649048648.026,375745732274.676,421739210176.152,474517470742.749,432198936002.177,440833583992.485,477386120635.845]
	- title: GDP of Lithuania (current US$)
	  data: [7867140395.33705,8382519637.46224,10118631851.5322,11239547690.9797,10971583944.7561,11524776866.6379,12237388001.7264,14259781159.0119,18781721376.1985,22627507451.5648,26097677571.8373,30183575103.5262,39697891351.9431,47797551587.8823,37388122046.1496,37128694028.243,43535051482.3869,42927454291.478,46523420074.4372,48533659592.1728,41435533340.3883,43047309305.7375,47758736931.7805,53751411409.4437,54760617012.9166,56914826860.6918,66414994991.7935,70334299008.3797]
	- title: GDP of Egypt (current US$)
	  data: [60159245060.4541,67629716981.1321,78436578171.0914,84828807556.0803,90710704806.8416,99838543960.0763,96684636118.5984,85146067415.7303,80288461538.4615,78782467532.4675,89600665557.4043,107426086956.522,130437828371.278,162818181818.182,189147005444.646,218983666061.706,235989672977.625,279116666666.667,288434108527.132,305595408895.265,329366576819.407,332441717791.411,248362771739.13,262588632526.73,318678815489.749,383817841547.099,424671765455.704,476747720364.742]
tension: 0.5
transparency: 1.0
\`\`\``)
```

---

## Unemployment and poverty rate

```dataviewjs
const data = dv.current()

dv.paragraph(`\`\`\`chart
type: line
labels: [Q1 2016,Q2 2016,Q3 2016,Q4 2016,Q1 2017,Q2 2017,Q3 2017,Q4 2017,Q1 2018,Q2 2018,Q3 2018,Q2 2020,Q4 2020,Q4 2022,Q1 2023]
series:
    - title: Uneployment rate
	  data: [12.1,13.3,13.9,14.2,14.4,16.2,18.8,20.4,21.8,22.7,23.1,27.1,33.3,5.3,4.1]
    - title: Povery rate
	  data: [32.3,31.4,31.5,30.8,30.6,30.6,31.8,30.8,30.9,34.8,34.1,35.1,38.6,40.1]
tension: 0.3
transparency: 1.0
\`\`\``)
```

- 5th worst poverty rate. 
- Big difference between states (Lagos - 4.5, Sokoto - 87.7)

---

## Inflation

```dataviewjs
const data = dv.current()

dv.paragraph(`\`\`\`chart
type: line
labels: [1998,1999,2000,2001,2002,2003,2004,2005,2006,2007,2008,2009,2010,2011,2012,2013,2014,2015,2016,2017,2018,2019,2020,2021,2022]
series:
    - title: Inflation, consumer prices (annual %)
	  data: [9.99637812386806,6.61837339479755,6.93329215565158,18.8736462093863,12.8765792031099,14.0317836131437,14.9980338183251,17.8634933661605,8.22522152017048,5.38800796858628,11.5810751748252,12.5378277304689,13.7400521363694,10.8261371880017,12.2242413020584,8.49551838265667,8.04741087967911,9.00943498007705,15.6968126387972,16.5022662139641,12.0951065173432,11.3964223378115,13.2460234276598,16.9528457221608,18.8471877843274]
tension: 0.3
transparency: 1.0
\`\`\``)
```

---

## Inflation comparison

```dataviewjs
const data = dv.current()

dv.paragraph(`\`\`\`chart
type: line
labels: [1998,1999,2000,2001,2002,2003,2004,2005,2006,2007,2008,2009,2010,2011,2012,2013,2014,2015,2016,2017,2018,2019,2020,2021,2022]
series:
    - title: Inflation, consumer prices in Nigeria (annual %)
	  data: [9.99637812386806,6.61837339479755,6.93329215565158,18.8736462093863,12.8765792031099,14.0317836131437,14.9980338183251,17.8634933661605,8.22522152017048,5.38800796858628,11.5810751748252,12.5378277304689,13.7400521363694,10.8261371880017,12.2242413020584,8.49551838265667,8.04741087967911,9.00943498007705,15.6968126387972,16.5022662139641,12.0951065173432,11.3964223378115,13.2460234276598,16.9528457221608,18.8471877843274]
	- title: Inflation, consumer prices in Lithuania (annual %)
	  data: [5.06755385841976,0.727550626557433,0.981615478857527,1.36712030817826,0.281506610345511,-1.13430854990244,1.16410252104669,2.65848484065266,3.73912004060656,5.73717382799847,10.9258856241655,4.4530445651853,1.31921362055463,4.13027562645087,3.08998277055416,1.04747937065349,0.103758009433715,-0.884097405500613,0.905525075460439,3.72288862303693,2.69792779208397,2.33450937988024,1.19989444983569,4.68354420903459,19.7050461518394]
	- title: Inflation, consumer prices in Egypt (annual %)
	  data: [3.87257546427492,3.07949912638328,2.68380535348548,2.26975720475954,2.73723855000332,4.50777636319307,11.270619332052,4.86939696871988,7.64452644526432,9.31896905799228,18.3168316831684,11.7634954386443,11.2651882653186,10.0649259874818,7.11172943343078,9.4697198106492,10.0702154687482,10.370490343517,13.8136062148289,29.5066083940039,14.4014657807421,9.15279959324812,5.04493288977533,5.21404940513043,13.8956609775178]
tension: 0.2
transparency: 1.0
\`\`\``)
```

---

# Fiscal sector

---

# Economic sectors

`````col 
````col-md 
flexGrow=1
===

```dataviewjs
const data = dv.current()

dv.paragraph(`\`\`\`chart
type: pie
labels: [Agriculture, Information and telecommunication, Manufacturing, Trade, Construction, Mining, Other]
series:
  - title: Composition in 2022
    data: [25, 15, 15, 13, 9.5, 6.5, 16]
labelColors: true
\`\`\``)
```

```` 
````col-md 
flexGrow=1
===

<font color="#d3869b">Agriculture</font>: 25%
<font color="#b8bb26">Information and telecommunication</font>: 15%
<font color="#fe8019">Manufacturing</font>: 15%
<font color="#8ec07c">Trade</font>: 13%
<font color="#fabd2f">Construction</font>: 9.5%
<font color="#83a598">Mining</font>: 6.5%
<font color="#fb4934">Other</font>: 16%

```` 
`````

---
## Circular economy

`````col 
````col-md 
flexGrow=0.4
===

![[6338890.svg]]

```` 
````col-md 
flexGrow=1
===

**Nigeria**, with a population of over **200 million people** generates huge quantities of
solid waste as a result of the **take-use-dispose approach**, which has been
estimated to be more than **32 million tonnes** yearly

### Waste composition:
43.43% - food waste, 
15.27% - plastic
7.76% - paper
1.39% - textile
3.36% - wood
0.081% - rubber and leather
2.02% - metal
2.39% - glass 
24.18% - others

```` 
`````

> Recent surveys indicate that **< 20%** of the waste is collected through the **formal system** while **< 10%** is **recycled**.

---

## Blue economy

`````col 
````col-md 
flexGrow=0.4
===

```dynamic-svg
---
invert-shade
---
[[waves-thin-svgrepo-com.svg]]
```

```` 
````col-md 
flexGrow=1
===

- More than **853km** of coastline
- Access to **Atlantic ocean**

### BUT

- Oil spills 
- Piracy

> **Ecological damage** to the ocean and rivers is *one of the main challenges* facing the development of a sustainable blue economy in **Nigeria**

```` 
`````

**Bayelsa State** has recently signed a memorandum of understanding with a European consortium known as the *African Atlantic Gulf of Guinea Fisheries* to develop a <u>massive aquaculture project</u> that includes setting up a <u>300-hectare offshore aquaculture farm</u>, a <u>20,000-metric-tonne capacity fish processing plant</u> and a <u>boatbuilding yard</u> across the state’s three senatorial districts

---

## Green economy

`````col 
````col-md 
flexGrow=0.4
===

```dynamic-svg
---
invert-shade
---
[[leaf-svgrepo-com.svg]]
```

```` 
````col-md 
flexGrow=1
===

- There is a conflict between **Nigeria’s** *dependence on oil* for its economic wellbeing, and the sustainable development the nation strives to achieve.

<hr>

- The country is the *largest oil and gas producer in Africa* and the *fourth largest gas producer in the world*. Resource extraction is the most important sector of the economy.

<hr>

- Major renewable energy resources include *solar*, *wind*, *hydropower*, *biomass*, and *tidal waves*. However there is lack of access to affordable and reliable energy services.

<hr>

- There are numerous unexplored sectors in Nigeria where Green Economy initiatives can be adopted, including *transportation*, *housing*, *food processing*, *fashion*, and in various *manufacturing sectors*.

```` 
`````

---
## Defense economy

`````col 
````col-md 
flexGrow=0.4
===

![[Pasted image 20231021135407.png]]

```` 
````col-md 
flexGrow=1
===

- 42nd out of 138 countries in global military strength

```dataviewjs
const data = dv.current()

dv.paragraph(`\`\`\`chart
type: bar
labels: [2012,2013,2014,2015,2016,2017,2018,2019,2020,2021,2022]
series:
    - title: Ratio of military spending to GDP
	  data: [0.5,0.47,0.41,0.42,0.43,0.43,0.51,0.46,0.63,1.01,0.64]
tension: 0.3
transparency: 1.0
beginAtZero: true
\`\`\``)
```

```` 
`````

---
## Monetary economy

`````col 
````col-md 
flexGrow=0.4
===

```dynamic-svg
---
invert-shade
---
[[bank-svgrepo-com.svg]]
```

```` 
````col-md 
flexGrow=1
===

Central bank - **Central Bank of Nigeria** is responsible for the country's monetary policy.

Interest rates are a **key tool** in monetary policy. The bank uses interest rates to control the *money supply* and *influence inflation rates*.

```dataviewjs
const data = dv.current()

dv.paragraph(`\`\`\`chart
type: line
labels: [2002,2003,2004,2005,2006,207,2008,2009,2010,2011,2012,2013,2014,2015,2016,2017,2018,2019,2020,2021,2022]
series:
    - title: Lending interest rate (%)
	  data: [24.7708333333333,20.7141666666667,19.1808333333333,17.9483333333333,16.8933333333333,16.9391666666667,15.1358333333333,18.9908333333333,17.585,16.02,16.7916666666667,16.7225,16.5483333333333,16.8491666666667,16.8680157641191,17.5533333333333,16.9038969646886,15.3765869808333,13.6420216718796,11.4831327947137,12.3345442352326]
tension: 0.3
transparency: 1.0
\`\`\``)
```

```` 
`````

---

## Political economy



---

# Economic issues and possible solutions

---

## Issues

`````col 
````col-md 
flexGrow=1
===


1. **Limited opportunities** to most of the citizens 
	- A Nigerian born in 2020 was expected to be **36% as productive as they could have been** if they had **full access to education and health**

<hr>

2. The **poverty rate** is very high, with an estimated **84 million** Nigerians living **below the poverty line**

<hr>

3. **Spatial inequality**
	- Best-performing regions of Nigeria comparing favorably to **middle-income countries**
	- Worst performing states fall **below the average** for low-income

<hr>

4. **Wide infrastructure gaps** constrain access to *electricity* and *hinder the domestic economic integration* 

<hr>

5. In most areas of Nigeria **service delivery** is *limited* and *insecurity* and *violence* are widespread

````
`````

---

## Solutions

`````col 
````col-md 
flexGrow=1
===


- Focus on the tradable services sector
	- (**80** percent of workers are employed in sectors with *low levels of productivity* - **agriculture** and **non-tradable services**)

<hr>

- Implement some kind of programs to upskill young people to take full advantage of this sector

<hr>

- Expand the manufacturing sector
	- The sector has a **much higher productivity level** than *agriculture* and can accommodate the kind of *labor that is abundant* in the country


```` 
`````

---

# Thanks for your attention!