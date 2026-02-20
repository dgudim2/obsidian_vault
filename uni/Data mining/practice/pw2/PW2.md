>[!note]
> Author: **Danila Gudim**
> Group: **ITvfu-22**
> Completed on: **20.02.2026**

The script is written in python and uses *wordcloud* for wordcloud generation, *scikit-learn* for ML and *matplotlib* for the visualisation.

When running the script it will show a wordcloud, use k-means clustering to cluster the book titles, show different performance parameters and train an MLP. The script can be ran with [uv](https://docs.astral.sh/uv/) or, if you want, you can manually install all the dependencies and run it manually.

## Wordcloud

Before passing the words into wordcloud visualizer, I removed some short and generic words and punctuation. Here is the result

![[Pasted image 20260220210428.png|500]]

## K-means clustering

For the clustering, I decided to use words from the titles as features. I could've used prices or ratings, but since we are trying get genres, we need something related to genres.

I took top 20 most common words in the titles and used **one-hot** encoding to encode the titles.

Top 20 words:
```
['life', 'love', 'girl', 'world', 'art', 'story', 'new', 'history', 'guide', 'recipes', 'fruits', 'basket', 'little', 'city', 'harry', 'potter', 'black', 'american', 'saga', 'god']
```

The resulting dataframe looks like this (some columns are truncated):

```
┌─────────────────────────────────┬──────┬──────┬───────┬─────┬───────┬─────┬────────┬─────┐
│ Title                           ┆ love ┆ girl ┆ world ┆ art ┆ story ┆ new ┆ history┆ god │
│ ---                             ┆ ---  ┆ ---  ┆ ---   ┆ --- ┆ ---   ┆ --- ┆ ---    ┆ --- │
│ str                             ┆ i64  ┆ i64  ┆ i64   ┆ i64 ┆ i64   ┆ i64 ┆ i64    ┆ i64 │
╞═════════════════════════════════╪══════╪══════╪═══════╪═════╪═══════╪═════╪════════╪═════╡
│ A Light in the Attic            ┆ 1    ┆ 0    ┆ 0     ┆ 0   ┆ 0     ┆ 0   ┆ 0      ┆ 0   │
│ Tipping the Velvet              ┆ 0    ┆ 0    ┆ 0     ┆ 0   ┆ 0     ┆ 0   ┆ 0      ┆ 0   │
│ Soumission                      ┆ 0    ┆ 0    ┆ 0     ┆ 0   ┆ 0     ┆ 0   ┆ 0      ┆ 0   │
│ Sharp Objects                   ┆ 0    ┆ 0    ┆ 0     ┆ 0   ┆ 0     ┆ 0   ┆ 0      ┆ 0   │
│ Sapiens: A Brief History of Hu… ┆ 0    ┆ 0    ┆ 0     ┆ 0   ┆ 1     ┆ 0   ┆ 1      ┆ 0   │
│ …                               ┆ …    ┆ …    ┆ …     ┆ …   ┆ …     ┆ …   ┆ …      ┆ …   │
│ Alice in Wonderland (Alice's A… ┆ 0    ┆ 0    ┆ 0     ┆ 0   ┆ 0     ┆ 0   ┆ 0      ┆ 0   │
│ Ajin: Demi-Human, Volume 1 (Aj… ┆ 0    ┆ 0    ┆ 1     ┆ 0   ┆ 0     ┆ 0   ┆ 0      ┆ 0   │
│ A Spy's Devotion (The Regency … ┆ 0    ┆ 1    ┆ 0     ┆ 0   ┆ 0     ┆ 0   ┆ 1      ┆ 0   │
│ 1st to Die (Women's Murder Clu… ┆ 0    ┆ 0    ┆ 0     ┆ 0   ┆ 0     ┆ 0   ┆ 0      ┆ 0   │
│ 1,000 Places to See Before You… ┆ 0    ┆ 0    ┆ 0     ┆ 0   ┆ 0     ┆ 0   ┆ 0      ┆ 0   │
└─────────────────────────────────┴──────┴──────┴───────┴─────┴───────┴─────┴────────┴─────┘
```

I hard-coded the seed for the random number generator in the clusterizer so the results are the same every time

```python
kmeans_model = KMeans(n_clusters=k, max_iter=300, random_state=np.random.RandomState(seed=11099226))
```

After running the clusterizer with different number of clusters, I decided to use **8** clusters since this is when *SSD* stops falling rapidly and *silhouettes* stops rising rapidly.

![[Pasted image 20260220210929.png]]

After clustering, I made a heatmap with word counts per cluster, here how it looks like:

![[Pasted image 20260220211828.png|600]]

We can see that most clases except for the top 2 have clear word combinations associated with them. Here is how I named them:

```python
genre_for_clusters_mapping = {
    0: "Cooking",
    1: "Other/new world/guides",
    2: "Love stories",
    3: "Life stories",
    4: "Art",
    5: "Child stories",
    6: "Girl stories",
    7: "History books",
}
```

Here are book counts per cluster:

```
┌───────┬────────────────────────┐
│ Title ┆ Cluster name           │
│ ---   ┆ ---                    │
│ u32   ┆ str                    │
╞═══════╪════════════════════════╡
│ 14    ┆ Cooking                │
│ 784   ┆ Other/new world/guides │
│ 31    ┆ Love stories           │
│ 39    ┆ Life stories           │
│ 59    ┆ Art                    │
│ 12    ┆ Child stories          │
│ 31    ┆ Girl stories           │
│ 29    ┆ History books          │
└───────┴────────────────────────┘
```

We can see that the generic cluster has the most books since they don't share common words in their titles.

## MLP classifier

After clustering, I trained an MLP to classify books into the determined clusters.
I tested it with different layer sizes, iteration counts and activation functions:

![[Pasted image 20260220212918.png]]

Each graph represents a different activation function, layer density increases from left to right in each graph and iteration count increases in this order: red (300) -> green (500) -> blue (700) -> purple (1000).

- We can see that *logistic* activation function is the slowest to **converge**, requiring <u>700 iteration or more</u>. 
- *Identity* activation function is the fastest to **converge**, requiring <u>less than 300 iterations</u> while still being accurate on some layer densities.
- *Relu* and *tanh* sit in between and are moderately fast to **converge** requiring about 500 iteration

> [!note] 
> In some cases accuracy reaches 100%, which is impossible, this means we don't have enough data and the mode is overfit.

I chose the following parameters:

```python
mlp = MLPClassifier(hidden_layer_sizes=(16, 8),
                    max_iter=300,
                    activation='identity',
                    random_state=42)
```

So, *2 hidden layers*: 16 and 8 tensors, *identity* activation and *300 iterations*

This is the classification report:

```
Classification Report:
                         precision    recall  f1-score   support

                   Art       1.00      1.00      1.00        12
         Child stories       1.00      1.00      1.00         1
               Cooking       1.00      1.00      1.00         7
          Girl stories       0.80      1.00      0.89         4
         History books       1.00      1.00      1.00         9
          Life stories       1.00      0.88      0.93         8
          Love stories       1.00      1.00      1.00         5
Other/new world/guides       1.00      1.00      1.00       154

              accuracy                           0.99       200
             macro avg       0.97      0.98      0.98       200
          weighted avg       1.00      0.99      1.00       200
```

And the confusion matrix:
![[Pasted image 20260220213726.png|800]]

We can see that the model only misclassified child stories and otherwise is 100% accurate which is unrealistic, we need more data