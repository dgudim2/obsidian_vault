# Task 1: read sample data
print(getwd())
data <- read.table("mydata.txt")
print(data)

# Task 2: 500 binomial values
values <- rbinom(500, 10, 0.75)
print(table(values))
barplot(table(values))

# Task 3: 500 normal values
n_values <- rnorm(500, 1, 5)
hist(n_values)
print(cut(n_values, 10))

# Task 4: load orange dataset
data("Orange")

print(str(Orange))
print(quantile(Orange$age, 0.5))
pie(table(Orange$Tree), col = rainbow(5, alpha = 0.3))
hist(Orange$circumference)

# Task 5: read real dataset
dataset <- read.table("data_LW2.txt")
random_sel <- dataset[sample(nrow(dataset), 250, 0), ]
print(random_sel)
## Print some data
print(summary(random_sel))
print(var(random_sel$year))
print(var(random_sel$area))
print(var(random_sel$price))



