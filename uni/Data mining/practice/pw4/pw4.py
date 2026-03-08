#!/usr/bin/env -S uv run --script

# /// script
# dependencies = [
#   "polars",
#   "seaborn",
#   "scikit-learn",
#   "numpy"
# ]
# ///

from pathlib import Path
from typing import cast

import polars as pl
import seaborn as sb
import matplotlib.pyplot as plt

from sklearn.preprocessing import MinMaxScaler, StandardScaler
from sklearn.decomposition import PCA

import numpy as np

from sklearn.neural_network import MLPClassifier
from sklearn.model_selection import train_test_split
from sklearn.metrics import accuracy_score, classification_report, mean_squared_error

from sklearn.metrics import ConfusionMatrixDisplay

from typing import Any

adult_dataset = pl.read_csv(Path("./adult.csv"))
titanic_dataset = pl.read_csv(Path("./titanic.csv"))


def clean_df(df: pl.DataFrame, columns: list[str]):
    len_before = len(df)

    df = df.with_columns(pl.col(pl.String).replace("?", None))
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


adult_dataset = (
    clean_df(adult_dataset, ["age", "fnlwgt", "education", "race", "sex", "hours.per.week", "native.country", "income"])
    .with_columns(pl.col("race").cast(pl.Categorical).to_physical())
    .with_columns(pl.col("sex").cast(pl.Categorical).to_physical())
    .with_columns(pl.col("native.country").cast(pl.Categorical).to_physical())
    .with_columns(pl.col("income").cast(pl.Categorical).to_physical())
    .with_columns(pl.col("education").cast(pl.Categorical).to_physical())
)
titanic_dataset = clean_df(titanic_dataset, ["Sex", "Pclass", "SibSp", "Parch", "Survived"]).with_columns(
    pl.col("Sex").cast(pl.Categorical).to_physical()
)


minmax = MinMaxScaler()
zscore = StandardScaler()

df_minmax_adult = pl.DataFrame(minmax.fit_transform(adult_dataset), schema=adult_dataset.columns)
df_zscore_adult = pl.DataFrame(zscore.fit_transform(adult_dataset), schema=adult_dataset.columns)

df_minmax_titanic = pl.DataFrame(minmax.fit_transform(titanic_dataset), schema=titanic_dataset.columns)
df_zscore_titanic = pl.DataFrame(zscore.fit_transform(titanic_dataset), schema=titanic_dataset.columns)


def display_features(orig: pl.DataFrame, minmax: pl.DataFrame, zscore: pl.DataFrame, features: list[str]):
    for f in features:
        print(f)
        plt.figure(figsize=(12, 4))

        plt.subplot(1, 3, 1)
        sb.histplot(orig[f], bins=30)
        plt.title(f"{f} before")

        plt.subplot(1, 3, 2)
        sb.histplot(minmax[f], bins=30)
        plt.title(f"{f} MinMax")

        plt.subplot(1, 3, 3)
        sb.histplot(zscore[f], bins=30)
        plt.title(f"{f} Z-score")

        plt.show()


display_features(adult_dataset, df_minmax_adult, df_zscore_adult, ["age", "fnlwgt", "hours.per.week"])
display_features(titanic_dataset, df_minmax_titanic, df_zscore_titanic, ["Parch", "SibSp"])


def pca(df: pl.DataFrame):
    pca = PCA()
    pca.fit(df)

    cum_var = np.cumsum(pca.explained_variance_ratio_)

    plt.figure(figsize=(8, 5))
    plt.plot(range(1, len(cum_var) + 1), cum_var, marker="o")
    plt.xlabel("Number of principal components")
    plt.ylabel("Cumulative explained variance")
    plt.title("PCA: Cumulative Explained Variance")
    plt.grid(True)
    plt.show()


pca(df_zscore_adult)
pca(df_zscore_titanic)


def test_mlps_for_dataset(dataset: pl.DataFrame, prediction_column: str):
    X = dataset.drop(prediction_column)
    y = dataset[prediction_column]

    X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.2, random_state=42)

    scaler = StandardScaler()
    X_train = scaler.fit_transform(X_train)
    X_test = scaler.transform(X_test)

    accuracies = []
    activations = ["relu", "logistic", "tanh"]

    best_params: dict[str, Any] = {}
    best_accuracy = 0

    for activation in activations:
        accuracies_for_activation = []
        for max_iterations in [300, 1000]:
            accuracies_for_iter = []
            for hidden_layers in [20, 50, 100]:
                for hidden_layer_size in [25, 50]:
                    mlp = MLPClassifier(
                        hidden_layer_sizes=(hidden_layers, hidden_layer_size),
                        max_iter=max_iterations,
                        activation=activation,  # type: ignore
                        random_state=42,
                    )

                    mlp.fit(X_train, y_train)
                    y_pred = mlp.predict(X_test)

                    accuracy = accuracy_score(y_test, y_pred)
                    if accuracy > best_accuracy:
                        best_params["activation"] = activation
                        best_params["hidden_layer_sizes"] = (hidden_layers, hidden_layer_size)
                        best_params["max_iter"] = max_iterations

                    print(
                        f"[{activation}] Accuracy for ({hidden_layers, hidden_layer_size}) iters: {max_iterations}: {accuracy * 100:.2f}%"
                    )

                    accuracies_for_iter.append(accuracy)
            accuracies_for_activation.append(accuracies_for_iter)
        accuracies.append(accuracies_for_activation)

    def multiplot(axis, index: int):
        axis.plot(range(0, len(accuracies[index][0])), accuracies[index][0], color="red", marker="o")
        axis.plot(range(0, len(accuracies[index][1])), accuracies[index][1], color="green", marker="o")
        axis.set(xlabel=activations[index], ylabel="Accuracy")

    fig, ((ax1, ax2), (ax3, ax4)) = plt.subplots(2, 2)
    fig.suptitle("Accuracies")
    fig.tight_layout()

    multiplot(ax1, 0)
    multiplot(ax2, 1)
    multiplot(ax3, 2)

    plt.show()

    mlp = MLPClassifier(
        hidden_layer_sizes=best_params["hidden_layer_sizes"],
        max_iter=best_params["max_iter"],
        activation=best_params["activation"],
        random_state=42,
    )

    mlp.fit(X_train, y_train)
    y_pred = mlp.predict(X_test)

    class_report = classification_report(y_test, y_pred)
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

    mlp = MLPClassifier(
        hidden_layer_sizes=best_params["hidden_layer_sizes"],
        max_iter=best_params["max_iter"],
        activation=best_params["activation"],
        random_state=42
    )
    
    sb.lineplot(mlp.fit(X_train, y_train).loss_curve_)
    sb.lineplot(mlp.fit(X_test, y_test).loss_curve_)
    plt.legend()
    plt.show()


#test_mlps_for_dataset(adult_dataset, "income")
test_mlps_for_dataset(titanic_dataset, "Survived")


