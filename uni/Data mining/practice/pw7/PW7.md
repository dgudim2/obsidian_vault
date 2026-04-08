>[!note]
> Author: **Danila Gudim**
> Group: **ITvfu-22**
> Completed on: **08.04.2026**

## Dataset pre-processing

Before training the models on the data, I applied this function to every tweet

```python
def clean_text(text: str):
    text = text.lower()
    text = re.sub(r'\d+', '', text)
    text = text.translate(str.maketrans('', '', string.punctuation))
    text = re.sub(r'\W+', ' ', text)
    text = re.sub(r'\s+', ' ', text).strip()

return text

ef split_and_clean(text: str):
    text = clean_text(text)
    text_tokens = word_tokenize(text)
    
    lemmed = (lemmatizer.lemmatize(word) for word in text_tokens)
    fixed = " ".join(contractions.fix(word) for word in lemmed if word not in stop_words)

return fixed
```

which tokenizes by word boundary, lemmatizes all the tokens, removes he stop words and expands the contraction. It also lowercases the tokens and removes punctuation


The dataset has **6** classes: `'age', 'gender', 'other_cyberbullying', 'ethnicity', 'not_cyberbullying', 'religion'` and **14k** samples

## Data vectorization

Because the data is text and non numbers, vectorization is required, I used **TFID vertorizer** for this:

```python
vectorizer = TfidfVectorizer()

X_train_tfid = vectorizer.fit_transform(X_train)
X_test_tfid = vectorizer.transform(X_test)
```

## Data visualization

I used t-SNE to reduce the number of dimensions in the high-dimension vectors to just **2**, here is the result:

![[Pasted image 20260408231016.png]]

A cool looking doughnut shape with several clearly visible clusters

## Random forest

`````col 
````col-md 
flexGrow=1
===

![[Pasted image 20260408230444.png]]

```` 
````col-md 
flexGrow=1
===

```
Accuracy of Random Forest: 82.06
              precision    recall  f1-score   support

           0       0.90      0.82      0.86      2392
           1       0.99      0.98      0.99      2388
           2       0.95      0.96      0.95      2399
           3       0.97      0.97      0.97      2398
           4       0.56      0.70      0.62      2347
           5       0.58      0.49      0.53      2384

    accuracy                           0.82     14308
   macro avg       0.83      0.82      0.82     14308
weighted avg       0.83      0.82      0.82     14308
```

```` 
`````

In general, the model is quite good at classifying all the classes except for `other_cyberbullying` and `not_cyperbullying`, those classes are the most troublesome.

## XGBoost

`````col 
````col-md 
flexGrow=1
===

![[Pasted image 20260408230631.png]]

```` 
````col-md 
flexGrow=1
===

```
Accuracy of XgBoost: 83.83%
              precision    recall  f1-score   support

           0       0.92      0.83      0.87      2392
           1       1.00      0.98      0.99      2388
           2       0.96      0.95      0.96      2399
           3       0.99      0.97      0.98      2398
           4       0.58      0.82      0.68      2347
           5       0.66      0.48      0.56      2384

    accuracy                           0.84     14308
   macro avg       0.85      0.84      0.84     14308
weighted avg       0.85      0.84      0.84     14308
```

```` 
`````

Performance of XGBoost is almost identical to the random forest, but better in all of the classes by a few percent.

## BERT

I also trained a transformer model: BERT with the following parameters:

```
bert_model_name = 'bert-base-uncased'
num_classes = len(category_map) # 6 classes
max_length = 128
batch_size = 16
num_epochs = 2
learning_rate = 2e-5
```

`````col 
````col-md 
flexGrow=1
===

After first iteration:

```
Epoch 1/2
Validation Accuracy: 0.9333
              precision    recall  f1-score   support

           0       0.92      0.85      0.88      2392
           1       0.98      0.97      0.97      2388
           2       0.94      0.95      0.95      2399
           3       0.96      0.98      0.97      2398
           4       0.81      0.81      0.86      2347
           5       0.70      0.84      0.87      2384

    accuracy                           0.93     14308
   macro avg       0.93      0.93      0.93     14308
weighted avg       0.94      0.93      0.93     14308
```


```` 
````col-md 
flexGrow=1
===

After the second iteration:

```
Epoch 2/2
Validation Accuracy: 0.9420
              precision    recall  f1-score   support

           0       0.91      0.87      0.89      2392
           1       0.98      0.98      0.98      2388
           2       0.94      0.96      0.95      2399
           3       0.97      0.98      0.98      2398
           4       0.82      0.82      0.87      2347
           5       0.82      0.74      0.88      2384

    accuracy                           0.94     14308
   macro avg       0.94      0.94      0.94     14308
weighted avg       0.94      0.94      0.94     14308
```

```` 
`````

This model gave the best result but it's the slowest and heaviest one as well. Training took 11 minutes on an RTX2060 with 6 gigs or VRAM