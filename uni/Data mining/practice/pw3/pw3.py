#!/usr/bin/env -S uv run --script

# /// script
# dependencies = [
#   "requests",
#   "polars",
#   "seaborn",
#   "pyarrow",
#   "narwhals"
# ]
# ///


from pathlib import Path
import polars as pl
import narwhals as nw
import seaborn as sb
import matplotlib as mpl
import matplotlib.pyplot as plt

from pandas.plotting import parallel_coordinates

diabetes_df = pl.read_csv(Path("./diabetes.csv"))
print(diabetes_df)

wine_df = pl.read_csv(Path("./WineQT.csv"))
print(wine_df)


# Blood pressure distribution
sb.displot(diabetes_df.filter((pl.col("BloodPressure") != 0))["BloodPressure"], kde=True)
plt.show()

# Quality distribution (discrete values)
sb.displot(wine_df["quality"])
plt.show()

# Parallel plot or features based on quality
parallel_coordinates(
    nw.from_native(wine_df.filter((pl.col("quality") <= 3) | (pl.col("quality") >= 8)).drop("Id")).to_pandas(),
    "quality",
    colormap=mpl.colormaps["viridis"],
)
plt.show()

sb.jointplot(diabetes_df, x="BMI", y="Age", hue="Outcome")
plt.show()

sb.jointplot(wine_df.filter((pl.col("quality") >= 5)), x="alcohol", y="pH", hue="quality", kind='scatter')
plt.show()

fig = plt.figure()
ax = plt.axes(projection='3d')

x=wine_df['alcohol']
y=wine_df['density']
z=wine_df['pH']

ax.scatter(x, y, z)
plt.show()


sb.boxplot(wine_df, x="quality", y="alcohol")
plt.show()

sb.violinplot(wine_df, x="quality", y="alcohol")
plt.show()