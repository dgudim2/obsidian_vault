library(purrr)

d <- 34 - 6 + 1




v <- map_vec((2:7), \(x) (x + 1)^2 - 1, .progress = FALSE)
print(v)



v2 <- map_vec((1:10), \(i) log(exp(i + 3)), .progress = FALSE)
print(v2)



sum_squares <- sum(c(1:100)^2)
print(sum_squares)



m <- matrix(c(-9, -3, 3, 5, 6, -8), nrow = 3, ncol = 2, byrow = TRUE)
print(m)



A <- rbind(
    c(2, -1, 4),
    c(3, 2, 0),
    c(1, 0, 2)
)
B <- c(-9, 8, -4)
res <- solve(A, B)
print(res)



dataframe <- data.frame(
    Day = c("Monday", "Tuesday", "Wednesday", "Thursday", "Friday"),
    Boys = c(4, 6, 6, 3, 4),
    Girls = c(1, 4, 3, 5, 4),
    Total = c(5, 10, 9, 8, 8)
)
print(dataframe)




plot(c(dataframe[,4]),
    type = "o", col = "red",
    xlab = "Day", ylab = "Score", main = "Score chart", ylim=c(0, 10)
)
lines(c(dataframe[,3]), type = "o", col = "blue")
lines(c(dataframe[,2]), type = "o", col = "darkmagenta")
