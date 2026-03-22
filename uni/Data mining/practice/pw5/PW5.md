>[!note]
> Author: **Danila Gudim**
> Group: **ITvfu-22**
> Completed on: **18.03.2026**

## Graphs

I did various graphs before training the models, here they are

`````col 
````col-md 
flexGrow=1
===

> Distribution of radius from breast_cancer dataset

![[Pasted image 20260321205556.png]]

It's a normal distrubution with a sligh skew to the left (lower values)

```` 
````col-md 
flexGrow=1
===

> Joined plot of area and concavity marked by diagnosis

![[Pasted image 20260321205742.png]]

We can see that diagnosis is strongly correlated to those 2 features,
the higher the area and concavity, the higher is the likelihood of cancer

```` 
`````

`````col 
````col-md 
flexGrow=1
===

> Violin plot of concavity by diagnosis

![[Pasted image 20260321205921.png]]

We see the same correlationa as in the previous graph

```` 
````col-md 
flexGrow=1
===

Feature correlation matrix

![[Pasted image 20260321210010.png]]

We can see that most of the features are positively correlated

```` 
`````

## Predicting cancer

I implemented an **MLP** and **logistic regression** classifiers, used *z-score* scaling for **MLP** and min-max for the logistic regression.
For the regression I also implemented *stratified k-fold* method

### Results for MLP

`````col 
````col-md 
flexGrow=1
===

```
Accuracy for MLP is 94.15%
Classification Report:
               precision    recall  f1-score   support

           0       0.93      0.98      0.95       105
           1       0.97      0.88      0.92        66

    accuracy                           0.94       171
   macro avg       0.95      0.93      0.94       171
weighted avg       0.94      0.94      0.94       171
```

```` 
````col-md 
flexGrow=1
===

![[Pasted image 20260321210423.png]]

We can see that the model has some *false negatives*, which in the context of predicting cancer is not that great

```` 
`````

### Results for regression

`````col 
````col-md 
flexGrow=1
===

```
List of possible accuracies: [0.9298245614035088, 0.9473684210526315, 0.9298245614035088, 0.956140350877193, 0.9646017699115044]
Maximum Accuracy That can be obtained from this model is: 96.46017699115043%
Minimum Accuracy: 92.98245614035088 %
Overall Accuracy: 94.55519329296693%
Standard Deviation is: 0.015596561664438773
Classification Report:
               precision    recall  f1-score   support

           0       0.97      0.97      0.97        71
           1       0.95      0.95      0.95        42

    accuracy                           0.96       113
   macro avg       0.96      0.96      0.96       113
weighted avg       0.96      0.96      0.96       113
```

```` 
````col-md 
flexGrow=1
===

![[Pasted image 20260321210535.png]]

This model has way less *false negatives* and better accuracy in general, this makes it a better fit for a medical prediction

```` 
`````

### Comparison


|                     | Accuracy | Recall | F1     |     |
| ------------------- | -------- | ------ | ------ | --- |
| MLP                 | 94       | 93     | 93.5   |     |
| Logistic regression | **96**   | **96** | **96** |     |

Logistic regression is the winner here, I can't say for sure why, but perhaps it found the separation between classes more clearly. I haven't tried different activation functions and layer counts for **MLP**, it's likely that tweaking it will produce even better results