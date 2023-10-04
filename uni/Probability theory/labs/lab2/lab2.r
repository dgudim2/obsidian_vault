######################################################
# Laboratory work no.2
######################################################

library(purrr)

# Tasks related with topic "Finding probabilities"

# TASK1. There are 3 girls and 5 boys in the student group.
# In how many ways we can select 4 persons from this group? - choose()

a <- choose(8, 4)
print(a)

# TASK2. There are 3 girls and 5 boys in the student group. We selected 4 persons.
# What probability that two girls and 2 boys were selected? - round()

a <- (choose(3, 2) * choose(5, 2)) / choose(8, 4)
print(round(a, digit = 2))

# Task. 3
# Groupe consists of 29 Boys and 18 Girls. Lets make committee from 11 members.
# What probability that less than 6 Girls are on the committee?

nominator <- 0
for (x in 0:5) {
    nominator <- nominator + choose(18, x) * choose(29, 11 - x)
}

a <- nominator / choose(29 + 18, 11)
print(a)

###################################################

# TASK4. a) Groupe consists of B Boys and G Girls. Lets make committee from n members. # nolint
# 	    Create function which would be intended to calculate the probability of event A. # nolint
#           Random event A - k girls are selected for the committee.
#        b) Calculate probability that at least 2 boys are on the committee using the function. # nolint
#        c) Draw probabilities barplot for k girls, if k = 0,1,2,.., n and n <= G. # nolint
# 	 d) Draw probabilities plot for k girls and for n-k (k = 0,1,2,.., n) boys on separate graphs: # nolint
# 		a) both graphs are on one sheet;
# 		b) both graphs are in one coordinate system;
# 		c) the graphs are on separate sheets.

A.task4 <- function(B, G, n, k) { # nolint
    k_variants <- choose(G, k) * choose(B, n-k)
    return(k_variants / choose(B+G, n))
}

print(A.task4(10, 10, 7, 5))
print(1 - A.task4(10, 10, 7, 7) - A.task4(10, 10, 7, 6))

P <- map_vec((0:10), \(x) A.task4(10, 10, 10, x), .progress = FALSE) 
plot(P, type = "o", col = "red")

# TASK5. There are n fish in the lake, n = (50, 51, ..., 2000), 50 of them are tagged.
# 	 You randomly select 50 fishes.
# 	 a) Draw a probability, that out of 50 selected fishes 3 are tagged.
# 	 b) What should be n, that the probability to catch 3 tagged fishes from 50 selected was
# 	 the maximum likelihood. What is this probability?
