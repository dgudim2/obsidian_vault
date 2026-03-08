import numpy as np
import pandas as pd
import matplotlib.pyplot as plt
from sklearn.impute import SimpleImputer, KNNImputer
from sklearn.preprocessing import MinMaxScaler, StandardScaler
from sklearn.decomposition import PCA

df = pd.read_csv("adult.csv")
print (df.describe())

for col in df.select_dtypes(include=["object", "string"]).columns:
    df[col] = df[col].astype(str).str.strip()
df = df.replace("?", np.nan)

df = df.drop_duplicates()

before = len(df)
df = df.drop_duplicates()
print("Duplicates removed:", before - len(df))
print("New shape:", df.shape)

num_cols = df.select_dtypes(include=[np.number]).columns
print("Numeric columns:", list(num_cols))

before = len(df)

for col in num_cols:
    q1 = df[col].quantile(0.25)
    q3 = df[col].quantile(0.75)
    iqr = q3 - q1

    if iqr == 0:
        continue

    low = q1 - 1.5 * iqr
    high = q3 + 1.5 * iqr

    df = df[df[col].isna() | ((df[col] >= low) & (df[col] <= high))]

print("Rows removed as outliers:", before - len(df))
print("New shape:", df.shape)

plt.figure(figsize=(12,4))

plt.subplot(1,3,1)
plt.hist(df["age"], bins=100)
plt.title(f"{"age"} before")

plt.show()

df.to_csv("cleaned.csv", index=False)

num_cols = df.select_dtypes(include="number").columns
cat_cols = df.select_dtypes(include=["object", "string"]).columns

knn_imp = KNNImputer(n_neighbors=5)
cat_imp = SimpleImputer(strategy="most_frequent")

df_knn = df.copy()
df_knn[num_cols] = knn_imp.fit_transform(df_knn[num_cols])
df_knn[cat_cols] = cat_imp.fit_transform(df_knn[cat_cols])

print(df_knn.isna().sum())

minmax = MinMaxScaler()
zscore = StandardScaler()

df_minmax = df_knn.copy()
df_minmax[num_cols] = minmax.fit_transform(df_minmax[num_cols])

df_zscore = df_knn.copy()
df_zscore[num_cols] = zscore.fit_transform(df_zscore[num_cols])

features = ["age","hours.per.week","capital.gain"]

for f in features:
    plt.figure(figsize=(12,4))

    plt.subplot(1,3,1)
    plt.hist(df_knn[f], bins=30)
    plt.title(f"{f} before")

    plt.subplot(1,3,2)
    plt.hist(df_minmax[f], bins=30)
    plt.title(f"{f} MinMax")

    plt.subplot(1,3,3)
    plt.hist(df_zscore[f], bins=30)
    plt.title(f"{f} Z-score")

    plt.show()

df_pca = df_zscore.copy()

num_cols = df_pca.select_dtypes(include="number").columns

X = df_pca[num_cols].copy()

pca = PCA()
pca.fit(X)

cum_var = np.cumsum(pca.explained_variance_ratio_)

plt.figure(figsize=(8,5))
plt.plot(range(1, len(cum_var)+1), cum_var, marker="o")
plt.xlabel("Number of principal components")
plt.ylabel("Cumulative explained variance")
plt.title("PCA: Cumulative Explained Variance")
plt.grid(True)
plt.show()