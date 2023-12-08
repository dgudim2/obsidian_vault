setwd("/home/kloud/Documents/obsidian_vault/uni/Probability theory/labs/lab5/")
getwd()
dir()

A <- read.table("data_LW2.txt", header = TRUE, sep = "") # nolint
str(A)

######################################
# 1 task
######################################
# Calculate main characteristics for each variable;
summary(A)
print(paste(paste("Variance for year:", var(A$year)), paste("| Standard deviation for year:", sd(A$year)))) # nolint
print(paste(paste("Variance for area:", var(A$area)), paste("| Standard deviation for area:", sd(A$area)))) # nolint
print(paste(paste("Variance for price:", var(A$price)), paste("| Standard deviation for price:", sd(A$price)))) # nolint

######################################
# 2 task
######################################
# Set seed as your student ID. Select simple random sample
# of 250 flats and calculate main characteristics;
set.seed(20222136)

S <- A[sample(nrow(A), 250, replace = FALSE), ] # nolint
summary(S)
print(paste(paste("Variance for year:", var(S$year)), paste("| Standard deviation for year:", sd(S$year)))) # nolint
print(paste(paste("Variance for area:", var(S$area)), paste("| Standard deviation for area:", sd(S$area)))) # nolint
print(paste(paste("Variance for price:", var(S$price)), paste("| Standard deviation for price:", sd(S$price)))) # nolint

boxplot(A$area, S$area)

######################################
# 3 task
######################################
# Using sample data test hypothesis about the mean of year, area and price.
# (a value you have to choose by yourself, but it can’t be x¯).
mean(S$year) # a = 14 # nolint
mean(S$area) # a = 50 # nolint
mean(S$price) # a = 35500 # nolint
help("t.test")

t.test(S$year, mu = 14, conf.level = 0.95)
t.test(S$area, mu = 50, alternative = "greater")
t.test(S$price, mu = 35000, alternative = "two.sided")
# t.test(S$price, mu = 35000, alternative = "less") # nolint
# t.test(S$price, mu = 35000, alternative = "greater") # nolint

######################################
# 4 task
######################################
# Using sample data draw empirical density functions for all variables.

par(mfrow = c(3, 1))
hist(S$year, freq = FALSE, main = "EDF for Year")
hist(S$area, freq = FALSE, main = "EDF for Area")
hist(S$price, freq = FALSE, main = "EDF for Price")


######################################
# 5 task
######################################
# Using sample data test hypothesis that year is distributed by uniform
# distribution and area is distributed by exponential distribution.


# Uniform distribution
year <- S$year
t <- 10 # number of intervals
step <- (max(year) - min(year)) / t
intervals <- seq(min(year), max(year), step)

y_gr <- table(cut(year, intervals, labels = intervals[-1], include.lowest = TRUE, ordered_result = TRUE)) # nolint
barplot(y_gr / 250)

chisq.test(y_gr / 250, p = rep(1 / t, t)) # compare with uniform distribution


# Exponential distribution
y <- S$area
hist(y)
max(y)

lamda <- 1 / mean(y) # parameter

# calculation of theoretical probabilities
intervals <- seq(min(y), max(y) + 10, 10)
# because an exponential distribution has a long upper tail.
m <- length(intervals)

start_point <- pexp(c(0, intervals[2:(m - 1)]), lamda)
end_point <- pexp(c(intervals[2:(m - 1)], Inf), lamda)

prob <- end_point - start_point

sum(prob)
y_gr <- table(cut(y, intervals, labels = intervals[-1], include.lowest = TRUE, ordered_result = TRUE)) # nolint

chisq.test(y_gr / 250, prob) # compare with exponential distribution
barplot(y_gr / 250, prob, beside = TRUE, col = c("red", "green"))


######################################
# 6 task
######################################
price <- S$price
year <- S$year
cor(year, price) # correlation coefficient

lrg <- lm(price ~ year)
print(lrg)
summary(lrg)

par(mfrow = c(1, 1))
plot(price ~ year)
abline(lrg, col = "red")

lrg$coefficients %*% c(1, 35)
lrg$coefficients[1] + lrg$coefficients[2] * 35

######################################
# 7 task
######################################
price <- S$price
area <- S$area
cor(area, price) # correlation coefficient

lrg <- lm(price ~ area)
print(lrg)
summary(lrg)

par(mfrow = c(1, 1))
plot(price ~ area)
abline(lrg, col = "red")

lrg$coefficients %*% c(1, 160)
lrg$coefficients[1] + lrg$coefficients[2] * 160