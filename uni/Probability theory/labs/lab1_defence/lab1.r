# Using the letters from the "PHILOSOPHY" model experiment of getting a sequence of 600 elements with unequal probabilities
# (probabilities should show the frequency of the occurrence of the same letter in this phrase.)
# Using a bar diagram compare the probabilities before and after the experiment.
# Both diagrams have to have titles and have to be on one page. Save this graphic file as Surname2.png
# NOTE. The letters in the population should be written in alphabetical order

let1 <- c("P", "H", "I", "L", "O", "S", "O", "P", "H", "Y")

let2 <- c("H", "I", "L", "O", "P", "S", "Y")
pr <- c(2, 1, 1, 2, 2, 1, 1) / 10 # Probabilities before exp.

n <- 600 # 600 elements

x <- sample(let2, n, prob = pr, replace = TRUE)

tab <- table(x) / length(x) # probabilities after the experiment.

par(mfrow = c(1, 2))
barplot(pr, names.arg = let2, main = "Before experiment")
barplot(tab, main = "After experiment")



# First of all, you have defined some notations: nl – number of letters in your name; sl – number of letters in your surname.
# Enter the values of your constants: p1, p2, h:
# p1 = (mod(sl/4) · 0.1 + 0.5);
# p2 = mod(nl/4) · 0.1+ 0.05;
# h = mod((nl + sl)/6 + 3) · 10.
# You ask your neighbour to water a sickly plant while you are on vacation. With water, it will die with probability p1; without water, it will die with probability p2. You are h percent certain that your neighbour will remember to water the plant. When you returned, the plant was dead. Create a function (p1, p2, h) that calculates the probability that your neighbour remembered to water the plant.  Finally, calculate the probability using this function.

# The obtained probability has to be written in the comment.
nl <- 6
sl <- 5
p1 <- ((Mod(sl / 4) * 0.1 + 0.5)) / 100
p2 <- (Mod(nl / 4) * 0.1 + 0.05) / 100
h <- (Mod((nl + sl) / 6 + 3) * 10) / 100



remembered_to_water <- function(p1, p2, h) {
    prob_dead_no_water <- (1 - h) * p2
    prob_dead_with_water <- h * p1
    return(prob_dead_with_water / (prob_dead_no_water + prob_dead_with_water))
}
print(remembered_to_water(p1, p2, h))
# 0.7451182




# First of all, you have defined some notations: nl – number of letters in your name; month – your birthday month.
# Enter the values of your constants: k = mod(nl/3) + 4 and n = month · 500.
# Model experiment of tossing 3*k dices using a sample(). Do this experiment n times. Draw the empirical and theoretical density function of the number of ”at least 2” that occurred.
# Both graphs have to have titles and they have to be on one page. Save this graphic file as Surname1.png. You have to upload the graphic file here.
# Calculate the difference between the theoretical and empirical mean. The answer has to be written in the comment.

month <- 4
k_dices <- (Mod(nl / 3) + 4) * 4
n_times <- month * 500


# (at least 2 occurred (>= 2) {2, 3, 4, 5, 6}
prob_ge_2 <- 5 / 6
k_dices_vec <- 0:k_dices
k_dices_prob <- dbinom(k_dices_vec, k_dices, prob_ge_2)
rbind(k_dices_vec, k_dices_prob)

# Sample:
y <- sample(k_dices_vec, n_times, replace = TRUE, prob = k_dices_prob)
table(y) / n_times
# GRAPHS
par(mfrow = c(1, 2))
barplot(k_dices_prob, names.arg = k_dices_vec, main = "Theoretical") # Theoretical 
barplot(table(y) / n_times, main = "Empirical") # Empirical
# MEAN and Variance from exp.
print(mean(y))
print(var(y))
# MEAN and Variance before exp.
k_dices * prob_ge_2 # np
k_dices * prob_ge_2 * (1 - prob_ge_2) # npq
