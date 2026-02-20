#!/usr/bin/env -S uv run --script

# /// script
# dependencies = [
#   "wordcloud",
#   "matplotlib",
#   "scikit-learn",
#   "polars"
# ]
# ///

from pathlib import Path
import json
import re
from wordcloud import STOPWORDS, WordCloud
import matplotlib.pyplot as plt
import numpy as np
import polars as pl
import collections
from sklearn.cluster import KMeans
from sklearn.metrics import silhouette_score, davies_bouldin_score

dataset_path = Path('./books.json')
dataset = json.loads(dataset_path.read_text(encoding='utf-8'))

# Generate wordcloud
words_joined = ' '.join(book['title'] for book in dataset)
words_joined = re.sub(r'[^A-Za-z\s]', '', words_joined) # Leave only letters
words_joined = words_joined.lower()

stopwords = set(STOPWORDS)
stopwords.add('vol')
stopwords.add('one')
stopwords.add('volume')
stopwords.add('book')
words_filtered = list(word for word in words_joined.split() if word not in stopwords)
words_joined = ' '.join(words_filtered) # Remove stopwords

counts = collections.Counter(words_filtered)
top_words = [tup[0] for tup in counts.most_common(20)]
print(f"Top words: {top_words}")

wordcloud = WordCloud(width=800, height=400, background_color='white', random_state=np.random.RandomState(seed=20)).generate(words_joined)

plt.figure(figsize=(10, 5))
plt.imshow(wordcloud, interpolation='bilinear')
plt.axis('off')  
plt.title("Book title Word Cloud")
plt.show()


# K-means clustering
all_prices = [float(el["price"].replace("£", "").strip()) for el in dataset]
all_ratings = [el["rating"] for el in dataset]
all_titles = [el["title"] for el in dataset]

word_count_columns: dict[str, list[int]] = dict()
for word in top_words:
    word_count_columns[word] = []
    
for title in all_titles:
    for word in top_words:
        word_count_columns[word].append(1 if word in title.lower() else 0)

kmeans_df = pl.DataFrame(
    {
        # "price": all_prices,
        # "rating": all_ratings,
        **word_count_columns
    }
)

# Elbow method
ssd = []
silhouette_scores = []
db_indexes = []
for k in range(2, 14):
    kmeans_model = KMeans(n_clusters=k, max_iter=300, random_state=np.random.RandomState(seed=11099226))
    clusters = kmeans_model.fit_predict(kmeans_df)
    ssd.append(kmeans_model.inertia_)
    silhouette_scores.append(silhouette_score(kmeans_df, clusters))
    db_indexes.append(davies_bouldin_score(kmeans_df, clusters))

fig, ((ax1, ax2), (ax3, ax4)) = plt.subplots(2, 2)
fig.suptitle('Characteristics')
fig.tight_layout()

ax1.plot(range(2, 14), ssd, color="green", marker="o")
ax1.set(xlabel="Number of clusters (K)", ylabel="SSD for K")

ax2.plot(range(2, 14), silhouette_scores, color="red", marker="o")
ax2.set(xlabel="Number of clusters (K)", ylabel="Silhouettes for K")

ax3.plot(range(2, 14), db_indexes, color="blue", marker="o")
ax3.set(xlabel="Number of clusters (K)", ylabel="Davies Bouldin for K")

plt.show()

genre_for_clusters = {
    0: "Cooking",
    1: "Other/new world/guides",
    2: "Love stories",
    3: "Life stories",
    4: "Art",
    5: "Child stories",
    6: "Girl stories",
    7: "History books",
}

clusters = KMeans(n_clusters=8, random_state=np.random.RandomState(seed=11099226)).fit_predict(kmeans_df)
cluster_names = [genre_for_clusters.get(cluster) for cluster in clusters]

kmeans_df.insert_column(0, pl.Series("Cluster name", cluster_names))
kmeans_df.insert_column(1, pl.Series("Cluster index", clusters))
kmeans_df.insert_column(2, pl.Series("Title", all_titles))

kmeans_grouped_clusters = kmeans_df.drop("Title", "Cluster name").group_by("Cluster index", maintain_order=True).sum().sort("Cluster index").with_columns(pl.Series("Cluster name", genre_for_clusters.values()))

kmeans_grouped_clusters_unique = kmeans_df.drop("Cluster name").group_by("Cluster index", maintain_order=True).n_unique().sort("Cluster index")

with pl.Config(tbl_cols=5, tbl_width_chars=1000):
    print(kmeans_df)

with pl.Config(tbl_cols=200):
    print(kmeans_grouped_clusters)

print(kmeans_grouped_clusters_unique)

fig, ax = plt.subplots()
ax.imshow(kmeans_grouped_clusters.drop("Cluster index"), cmap='hot', interpolation='nearest')
ax.set_xticks(range(len(word_count_columns.keys())), labels=word_count_columns.keys(), rotation=45, ha="right", rotation_mode="anchor")
ax.set_title('2D Cluster heatmap')
fig.tight_layout()
plt.show()
