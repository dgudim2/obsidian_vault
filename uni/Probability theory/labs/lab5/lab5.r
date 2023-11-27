# Task 1: read sample data
print(getwd())
data <- read.table("mydata.txt")
print(data)

# Task 5: read real dataset
dataset <- read.table("data_LW2.txt")
random_sel <- dataset[sample(nrow(dataset), 250, 0), ]
print(random_sel)
## Print some data
print(summary(random_sel))
print(var(random_sel$year))
print(var(random_sel$area))
print(var(random_sel$price))

# Task 4: load orange dataset
data("Orange")

print(str(Orange))
print(quantile(Orange$age, 0.5))
pie(table(Orange$Tree), col = rainbow(5, alpha = 0.3))
hist(Orange$circumference)