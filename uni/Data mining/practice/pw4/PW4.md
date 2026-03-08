>[!note]
> Author: **Danila Gudim**
> Group: **ITvfu-22**
> Completed on: **08.03.2026**

## Distributions and normalization

When normalizing features using the minmax and z-score sclares we get the same distrubution, but different X-axis range (0-1 for minmax and from -2 to 2/3 for z-score depending on the feature)

`````col 
````col-md 
flexGrow=1
===

![[Pasted image 20260308154457.png]]

```` 
````col-md 
flexGrow=1
===

![[Pasted image 20260308154502.png]]

```` 
`````

`````col 
````col-md 
flexGrow=1
===

The PCA curve is very linear for the Adult dataset, looks like we can't reaaly reduce the number of components

![[pca.png]]

```` 
````col-md 
flexGrow=1
===

PCA curve fo rhe titanic dataset settles as 4 components, we can use PCA to reduce the number of components to 4 principal

![[Pasted image 20260308161444.png]]

```` 
`````


## MLP classification

### Adult dataset

> In this dataset I am predicting the income based on the following columns: 
> "age", "fnlwgt", "education", "race", "sex", "hours.per.week", "native.country"
> 
> The income has 2 possible values: **>50k** = *1* and **<=50k** = *0*

We can see that accuracy are almost identical across function and layer parameters with the best one at 78%

![[Pasted image 20260308152854.png]]

```
Classification Report:
               precision    recall  f1-score   support

           0       0.52      0.42      0.46      1124
           1       0.82      0.87      0.84      3343

    accuracy                           0.75      4467
   macro avg       0.67      0.64      0.65      4467
weighted avg       0.74      0.75      0.75      4467
```

`````col 
````col-md 
flexGrow=2
===
![[Pasted image 20260308153528.png]]
```` 
````col-md 
flexGrow=1
===

We can see that the model tend to missclassify people who have lower than 50k income, but for high income people it's pretty accurate, at 87!

```` 
`````

Loss curves looks pretty alright, blue one is the training one, orange is testing

![[Pasted image 20260308163256.png]]