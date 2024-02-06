# You have such data

4 4 4 3 4 0 3 0 2 2 1 3 3 4 2 0 1 3

`````col 
````col-md 
flexGrow=1
===

Frequency table:

| 0   | 1   | 2   | 3   | 4   | 
| --- | --- | --- | --- | --- |
| 3   | 2   | 3   | 5   | 5   |

```` 
````col-md 
flexGrow=1
===

Cumulative frequency table:

| 0   | 1   | 2   | 3   | 4   | 
| --- | --- | --- | --- | --- |
| 3   | 5   | 8   | 13   | 18   |

```` 
`````

`````col 
````col-md 
flexGrow=1
===

Relative frequency table:

| 0    | 1    | 2    | 3    | 4    | 
| ---- | ---- | ---- | ---- | ---- |
| 3/18 | 2/18 | 3/18 | 5/18 | 5/18 |

```` 
````col-md 
flexGrow=1
===

Relative cumulative frequency table:

| 0    | 1    | 2    | 3     | 4    | 
| ---- | ---- | ---- | ----- | ---- |
| 3/18 | 5/18 | 8/18 | 13/18 | 1    |

```` 
`````

- Mean: $\frac{0*3}{18} + \frac{1*2}{18} + \frac{2*3}{18} + \frac{3*5}{18} + \frac{4*5}{18} = 2.389$

- Variance: $(0 - \text{mean})^2 * \frac{3}{18} + (1 - \text{mean})^2 * \frac{2}{18} + (2 - \text{mean})^2 * \frac{3}{18} + (3 - \text{mean})^2 * \frac{5}{18} + (4 - \text{mean})^2 * \frac{5}{18}$

- Mode: 3 or 4
- Median: 3 $P(X\leq x_{m})\geq 0.5$ and $P(X \geq x_{m})\geq 0.5$



### 95% confidence interval of the mean: 

$$CI=\hat{x}\pm z \frac{s}{\sqrt{ n }}$$

Where:
- CI - confidence interval
- $\hat{x}$ - sample mean
- z - confidence level value (The Z value for 95% confidence is Z=1.96)
- s - sample standard deviation
- n - sample size

$$\text{Standard deviation for sample: }S=\sqrt{ \frac{\sum{(x_{i}-\hat{x})^2}}{n-1}}$$

Where:
- $\hat{x}$ - sample mean
- $x_{i}$ - individual value
- n - total number of samples

### Sample mean is significantly different from 2 at 0.1 significance level:



### Task 5

| Systolic BP  | 140 | 170 | 141 | 171 | 158 | 175 | 150 |
| ------------ | --- | --- | --- | --- | --- | --- | --- |
| Diastolic BP | 78  | 101 | 84  | 92  | 80  | 91  | 80  | 

- Corellation coef:

$$r=\frac{n*\sum(xy)-\sum(x)\sum(y)}{\sqrt{ (n\sum(x^2)-\sum(x)^2)*(n\sum(y^2)-\sum(y)^2) }}$$

