#!/usr/bin/env -S uv run --script

# /// script
# dependencies = [
#   "polars",
#   "seaborn",
#   "scikit-learn-extra",
#   "scikit-learn",
#   "python3-distutils",
#   "setuptools",
#   "scikit-clarans",
#   "sklearn-som"
# ]
# ///

import sys

from subprocess import Popen

import polars as pl
import numpy as np
from sklearn.cluster import KMeans, DBSCAN, AgglomerativeClustering
from sklearn_extra.cluster import KMedoids, CLARA
from clarans import CLARANS
from sklearn_som.som import SOM
from sklearn.metrics import silhouette_score, davies_bouldin_score, calinski_harabasz_score
import matplotlib.pyplot as plt
import subprocess

dataset = pl.read_csv('./data.csv').head(50_000)

dataset_only_numerical = dataset.drop("artists", "id", "name", "release_date")

args = sys.argv
algo_id = int(args[-1])

def test_using_method(cls: type):
    
    print(f"Clustering using {cls}")
    
    # Elbow method
    ssd = []
    
    silhouette_scores = []
    db_indexes = []
    hb_scores = []
    for k in range(2, 14):
        print(k)
        
        if str(cls) == str(SOM):
            model = cls(m=k, n=2, dim=len(dataset_only_numerical.columns), max_iter=300, random_state=np.random.RandomState(seed=11099226))
            clusters = model.fit_predict(X=dataset_only_numerical.to_numpy())
        elif str(cls) == str(DBSCAN):
            model = cls(min_samples=2, eps=k/3)
            clusters = model.fit_predict(X=dataset_only_numerical)
        elif str(cls) == str(AgglomerativeClustering):
            model = cls(n_clusters=k)
            clusters = model.fit_predict(dataset_only_numerical)  
        else:
            model = cls(n_clusters=k, max_iter=300, random_state=np.random.RandomState(seed=11099226))
            clusters = model.fit_predict(dataset_only_numerical)            
        
        try:
            ssd.append(model.inertia_)
        except:
            ssd.append(0)
            pass
        silhouette_scores.append(silhouette_score(dataset_only_numerical, clusters))
        db_indexes.append(davies_bouldin_score(dataset_only_numerical, clusters))
        hb_scores.append(calinski_harabasz_score(dataset_only_numerical, clusters))

    fig, ((ax1, ax2), (ax3, ax4)) = plt.subplots(2, 2)
    fig.suptitle(f"Characteristics for {cls}")
    fig.tight_layout()

    ax1.plot(range(2, 14), ssd, color="green", marker="o")
    ax1.set(xlabel="Number of clusters (K)", ylabel="SSD for K")

    ax2.plot(range(2, 14), silhouette_scores, color="red", marker="o")
    ax2.set(xlabel="Number of clusters (K)", ylabel="Silhouettes for K")

    ax3.plot(range(2, 14), db_indexes, color="blue", marker="o")
    ax3.set(xlabel="Number of clusters (K)", ylabel="Davies Bouldin for K")

    ax4.plot(range(2, 14), hb_scores, color="blue", marker="o")
    ax4.set(xlabel="Number of clusters (K)", ylabel="Harabasz for K")

    plt.show()
    
    return 1

algos = [KMeans, KMedoids, CLARA, CLARANS, DBSCAN, SOM, AgglomerativeClustering]

if algo_id == -1:
    processes: list[Popen] = []
    for algo_index in range(len(algos)):
        subprocess.Popen(["python", "pw6.py", f"{algo_index}"])
else:
    test_using_method(algos[algo_id])
        

