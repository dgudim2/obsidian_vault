>[!note]
> Author: **Danila Gudim**
> Group: **ITvfu-22**
> Completed on: **12.02.2026**


The scraper is written in python and uses *scrapy* for scraping, *numpy* and *scipy* for calculating mean, mode, std, etc. and *seaborn* for the visualisation.

When running the script it will scrape every book or quote from https://books.toscrape.com and https://quotes.toscrape.com, output them into json file near the script. The script can be ran with [uv](https://docs.astral.sh/uv/) or, if you want, you can manually install all the dependencies and run it manually.

## Result of scraping books

```
-------------------------
Median of ratings: 3.0
Mean or ratings: 2.923
Mode or ratings: 1
Standard deviation of ratings: 1.434249280982912
```

`````col 
````col-md 
flexGrow=1
===

> Rating distribution, looks uniform except for a higher number of 5-star reviews

![[Pasted image 20260212215726.png]]

```` 
````col-md 
flexGrow=1
===

> Price distribution, looks uniform

![[Pasted image 20260212215821.png]]

> Corellation of rating to the price, this looks too uniform, the data of the website is randomly generated, so no wonder

![[Pasted image 20260212215944.png]]

```` 
`````

## Result of scraping quotes

```
-------------------------
Median of tag count per quote: 2.0
Mean or tag count per quote: 2.32
Mode or tag count per quote: 1
Standard deviation of tag count per quote: 1.6726027621644055
```

> Tag distribution, filtered by tags with more than one entry to reduce noise

![[Pasted image 20260212220305.png]]


> Per-quote tag distribution

`````col 
````col-md 
flexGrow=0.6
===

![[Pasted image 20260212220349.png]]

```` 
````col-md 
flexGrow=1
===

> Tag count per author box plot
> This doesn't look that good because there are some authors with only 1 quote and so, only one number of tags 

![[Pasted image 20260212220529.png]]

```` 
`````

