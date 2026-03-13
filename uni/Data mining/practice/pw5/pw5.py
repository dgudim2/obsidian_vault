#!/usr/bin/env -S uv run --script

# /// script
# dependencies = [
#   "polars",
#   "seaborn",
#   "scikit-learn"
# ]
# ///

from sklearn.metrics import ConfusionMatrixDisplay
from sklearn.metrics import classification_report
from sklearn.metrics import accuracy_score
from sklearn.neural_network import MLPClassifier
from statistics import stdev
from statistics import mean
from sklearn.preprocessing import MinMaxScaler
from sklearn.model_selection import train_test_split
from sklearn.preprocessing import StandardScaler
from typing import cast
from pathlib import Path
import polars as pl
import seaborn as sb
import matplotlib.pyplot as plt
import numpy as np

from sklearn.model_selection import StratifiedKFold
from sklearn import linear_model

breast_dataset = pl.read_csv(Path("./data.csv"))


def clean_df(df: pl.DataFrame, columns: list[str]):
    len_before = len(df)

    df = df.unique().select(columns).drop_nans().drop_nulls()

    for col in df.select(pl.col(pl.Float64, pl.Int64, pl.Float32, pl.Int32)).columns:
        q1, q3 = cast(list[float], df[col].quantile([0.25, 0.75], interpolation="linear"))
        iqr = q3 - q1

        if iqr == 0:
            continue

        low = q1 - 1.5 * iqr
        high = q3 + 1.5 * iqr

        df = df.filter((df[col] >= low) & (df[col] <= high))

    len_after = len(df)

    dropped = len_before - len_after

    print(f"Cleaned df, before: {len_before}, after: {len_after} ({dropped} dropped, {dropped / len_before * 100:.4}%)")

    return df


breast_dataset = clean_df(
    breast_dataset,
    [
        "diagnosis",
        "radius_mean",
        "texture_mean",
        "perimeter_mean",
        "area_mean",
        "smoothness_mean",
        "compactness_mean",
        "concavity_mean",
        "concave points_mean",
        "symmetry_mean",
        "fractal_dimension_mean",
        "radius_se",
        "texture_se",
        "perimeter_se",
        "area_se",
        "smoothness_se",
        "compactness_se",
        "concavity_se",
        "concave points_se",
        "symmetry_se",
        "fractal_dimension_se",
        "radius_worst",
        "texture_worst",
        "perimeter_worst",
        "area_worst",
        "smoothness_worst",
        "compactness_worst",
        "concavity_worst",
        "concave points_worst",
        "symmetry_worst",
        "fractal_dimension_worst",
    ],
)

def graphs():
    # Radius distribution
    sb.displot(breast_dataset.filter((pl.col("radius_mean") != 0))["radius_mean"], kde=True)
    plt.show()

    sb.jointplot(breast_dataset, x="area_mean", y="concavity_mean", hue="diagnosis")
    plt.show()

    sb.violinplot(breast_dataset, x="diagnosis", y="concavity_mean")
    plt.show()

    # Compute the correlation matrix
    corr = breast_dataset.corr()

    # Generate a mask for the upper triangle
    mask = np.triu(np.ones_like(corr, dtype=bool))
    f, ax = plt.subplots(figsize=(11, 9))
    cmap = sb.diverging_palette(230, 20, as_cmap=True)

    sb.heatmap(corr, mask=mask, cmap=cmap, vmax=.3, center=0,
                square=True, linewidths=.5, cbar_kws={"shrink": .5})
    plt.show()
    
    

# graphs()
    

zscore = StandardScaler()

X = breast_dataset.drop("diagnosis")
y = breast_dataset["diagnosis"]



X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.3, random_state=42)
X_validate, X_test, y_validate, y_test = train_test_split(X_test, y_test, test_size=2/3.0, random_state=42)


mlp = MLPClassifier(
    hidden_layer_sizes=(200, 200),
    max_iter=2000,
    activation='relu',
    random_state=42,
)

mlp.fit(X_train, y_train)

y_pred_valid = mlp.predict(X_validate)
accuracy_valid = accuracy_score(y_validate, y_pred_valid)

y_pred_test = mlp.predict(X_test)
accuracy_test = accuracy_score(y_test, y_pred_test)

print(f"Accuracy for MLP is [validate: {accuracy_valid}] [test: {accuracy_test * 100}]")

class_report = classification_report(y_test, y_pred_test)
print("Classification Report:\n", class_report)


disp = ConfusionMatrixDisplay.from_estimator(
    mlp,
    X_test,
    y_test,
    display_labels=y.unique(maintain_order=True),
    normalize="true",
)
disp.ax_.set_title("Confusion matrix")

plt.show()



minmax = MinMaxScaler()
breast_dataset_minmax = minmax.fit_transform(X)
lr = linear_model.LogisticRegression()

skf = StratifiedKFold(n_splits=10, shuffle=True, random_state=1)
lst_accu_stratified = []

best_y_pred: np.ndarray
best_accuracy = 0

for train_index, test_index in skf.split(X, y):
    x_train_fold, x_test_fold = breast_dataset_minmax[train_index], breast_dataset_minmax[test_index]
    y_train_fold, y_test_fold = y[train_index], y[test_index]
    lr.fit(x_train_fold, y_train_fold)
    accuracy = lr.score(x_test_fold, y_test_fold)
    lst_accu_stratified.append(accuracy)
    
    if accuracy != 1:
        lr.predict(x_test_fold)


print('List of possible accuracies:', lst_accu_stratified)
print(f'Maximum Accuracy That can be obtained from this model is: {max(lst_accu_stratified)*100}%')
print(f'Minimum Accuracy: {min(lst_accu_stratified)*100} %')
print(f'Overall Accuracy: {mean(lst_accu_stratified)*100}%')
print(f'Standard Deviation is: {stdev(lst_accu_stratified)}')

class_report = classification_report(y_test, y_pred_test)
print("Classification Report:\n", class_report)