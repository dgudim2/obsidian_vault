setwd("/home/kloud/Downloads/")
A <- read.table("Population.txt", header = TRUE, sep = "") # nolint

# Calculate the main characteristics for each variable:
# mean, median, minimum, maximum, quartile Q1 and Q3, variance, and standard deviation.

print("Mean:")
mean(A$age)
mean(A$mileage)
mean(A$price)

print("Median:")
median(A$age)
median(A$mileage)
median(A$price)

print("Min:")
min(A$age)
min(A$mileage)
min(A$price)

print("Max:")
max(A$age)
max(A$mileage)
max(A$price)

print("Variance:")
var(A$age)
var(A$mileage)
var(A$price)

print("Standard deviation:")
sd(A$age)
sd(A$mileage)
sd(A$price)

print("Q1:")
quantile(A$age, 0.25)
quantile(A$mileage, 0.25)
quantile(A$price, 0.25)

print("Q3:")
quantile(A$age, 0.75)
quantile(A$mileage, 0.75)
quantile(A$price, 0.75)

# Select a simple random sample of 300 cars.
# What is the standard deviation of age in your sample?

set.seed(1214)
S <- A[sample(nrow(A), 300, replace = FALSE), ] # nolint
sd(S$age)

# Select a simple random sample of 300 cars.
# What is the difference between the age population standard deviation and the age sample standard deviation?

set.seed(1214)
S <- A[sample(nrow(A), 300, replace = FALSE), ] # nolint
sd(A$age) - sd(S$age)

# Select a simple random sample of 340 cars. Using sample data test the hypothesis that the mean of the population price equals 14000,
# using a significance level α=0,05.

set.seed(1214)
S <- A[sample(nrow(A), 340, replace = FALSE), ] # nolint

mean(A$price) # a = 14770.38 # nolint

t.test(S$price, mu = 14000, conf.level = 0.95)

# Select a simple random sample of 340 cars.
# Using sample data draw empirical density functions for variable age

set.seed(1214)
S <- A[sample(nrow(A), 340, replace = FALSE), ] # nolint
hist(S$age, freq = FALSE, main = "Age")

# 1) Select a simple random sample of 340 cars.
# 2) Using sample data write the regression line for the price depending on the age.
# The regression equation has to be written as comments in the Rscript.
# Equation: price = 25801 - 1347 * age

set.seed(1214)
S <- A[sample(nrow(A), 340, replace = FALSE), ] # nolint

price <- S$price
age <- S$age
cor(age, price) # correlation coefficient

lrg <- lm(price ~ age)
print(lrg)
summary(lrg)

par(mfrow = c(1, 1))
plot(price ~ age)
abline(lrg, col = "red")
