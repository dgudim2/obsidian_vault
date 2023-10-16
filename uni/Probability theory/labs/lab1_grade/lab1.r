# laboratory work no.1
# nl – number of letters in your name.
# sl – number of letters in your surname.
# month – your birthday month.
month <- 4
nl <- 6
sl <- 5
# mood(5/2)<-> 5%%2

#*****************************
#*     Task 1
#*****************************
p1 <- (sl %% 4) * 0.1 + 0.5
p2 <- nl %% 4 * 0.1 + 0.05
h <- ((nl + sl) %% 6 + 3) * 10

# 1. You ask your neighbor to water a sickly plant while you are on vacation. Without water it   # nolint: line_length_linter.
# will die with probability p1; with water it will die with probability p2. You are h percent    # nolint: line_length_linter.
# certain that your neighbor will remember to water the plant. What is the probability           # nolint: line_length_linter.
# that the plant will be alive when you return? If it is dead when you return, what is the       # nolint: line_length_linter.
# probability your neighbor forgot to water it?

# PA - plant will be alive;
# PD - plant will die;
# H1 - neighbor will remember to water the plant
# H2 - neighbor will forget to water the plant
h <- h / 100
is_alive <- function(p1, p2, h) {
  alive <- h * (1 - p2) + (1 - h) * (1 - p1)
  return(alive)
}
print(is_alive(p1, p2, h))

forgot_to_water <- function(p1, p2, h) {
  prob_dead_no_water <- (1 - h) * p1
  prob_dead_with_water <- h * p2
  return(prob_dead_no_water / (prob_dead_no_water + prob_dead_with_water))
}
print(forgot_to_water(p1, p2, h))


#*****************************
#*     Task 2
#*****************************

# 2.There are unknown number of moose on Isle Royale. To estimate the number of moose,
# M moose are captured and tagged. Six months later n moose are captured and it is found
# that m of these are tagged. Draw a graph for probability, that out of n selected moose
# m are tagged. Estimate the maximum likelihood number of moose on Isle Royale from
# these data. What is this probability?
month <- 2
nl <- 4
sl <- 13
M <- (sl %% 4 + 1) * 100
n <- (nl %% 4) + 60
m <- month + 20
M
n
m

x <- m:M
x # Because x is randome variable
# Draw a graph for probability, that out of n selected moose m are tagged.
plot(x, dhyper(m, x, M - x, n), type = "h", ylab = "Probabilities")
# Estimate the maximum likelihood number of moose on Isle Royale from these data.
k0 <- which.max(dhyper(m, x, M - x, n))
x[k0]

# result using function(x)
x <- m:M
moose <- function(x) dhyper(m, x, M - x, n)
plot(x, moose(x), type = "h")

#*****************************
#*     Task 3
#*****************************
# 3. Using the letters from your name and surname, model experiment of getting
# a sequence of 2000 elements with unequal probabilities (probabilities should
# show the frequency of the same letters occurrence in your name and surname.)
# Using a histogram compare the probabilities before and after experiment. NOTE.
# The letters in the population should be written in alphabetical order

let1 <- c("D", "A", "N", "I", "L", "A", "G", "U", "D", "I", "M") # DANILA GUDIM
table(let1)
let2 <- c("A", "D", "G", "I", "L", "M", "N", "U")
pr <- c(2, 2, 1, 2, 1, 1, 1, 1) / 11 # Probabilities before exp.
# table(letters) #prob of each letter
n <- 1000000
# prop.table(table(sample(let1, size=n, replace=TRUE)))
# all letters can repeat k times;

x <- sample(let2, n, prob = pr, replace = TRUE)

tab <- table(x) / length(x) # probabilities after exp.
print(tab)
par(mfrow = c(1, 2))
barplot(pr, names.arg = let2, main = "Before experiment") # histogram for probabilities before experiment
barplot(tab, main = "After experiment") # histogram for probabilities after experiment

#*****************************
#*     Task 4
#*****************************
# 4. Using sample procedure, model experiment of tossing k dices. Do this
# experiment n times. Draw an empirical and theoretical density function of
# number of ”more than 4” occurred. Calculate the mean and variance. Compare
# with theoretical mean and variance. NOTE. Mean, sum and variance for
# the x vector you can find using mean(x), sum(x), var(x) functions.
k_dices <- nl %% 3 + 4
n_times <- month * 500

# more than 4 occurred {5,6}
prob_ge_4 <- 2 / 6
k_dices_vec <- 0:k_dices
k_dices_prob <- dbinom(k_dices_vec, k_dices, prob_ge_4)
rbind(k_dices_vec, k_dices_prob)

# Sample:
y <- sample(k_dices_vec, n_times, replace = TRUE, prob = k_dices_prob)
table(y) / n_times
# GRAPHS
par(mfrow = c(1, 2))
barplot(k_dices_prob)
barplot(table(y) / n_times)
# MEAN and Variance from exp.
print(mean(y))
print(var(y))
# MEAN and Variance before exp.
n4 <- k_dices
n4 * prob_ge_4
n4 * prob_ge_4 * (1 - prob_ge_4)

#*****************************
#*     Task 5
#*****************************
# 5.Let X is a random variable distributed by normal distribution with two
# parameters m and s. Using density function find out how these parameters
# change density function (draw 4 graphs, where m is fixed and s is increasing,
# to find out what s does to the graph. Then draw 4 graphs, where s is fixed
# and m is increasing, to find out what m does to the graph).
# ...
#*****************************
#*     Task 6
#*****************************
# Let X is a random variable distributed by x distribution, with parameter (parameter’s)
# par. Generate this random variable N times. Calculate the mean and variance. Compare
# with theoretical mean and variance. Draw an empirical density function.
# if i = 2 then: X ∼ Poisson(λ); N = month · 1000; par: λ = (mod(month/3) + 1).
