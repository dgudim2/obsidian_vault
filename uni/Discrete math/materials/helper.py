from sympy.ntheory.factor_ import totient

target_num = 231525

divisors = 0
for i in range(2, int(target_num / 2) + 1):
    if target_num % i == 0:
        divisors = divisors + 1
# Print number of divisors and euler's function
print(f"divisors: {divisors + 2} | phi = {totient(target_num)}")

# Simplify 7^7436(mod 59)
num1 = pow(7, 7436) % 59
print(num1)

# Possible answers
test = [2, 7, 12, 14, 22]
# Bruteforce lol
for i in test:
    print(f"{i}: {pow(7, i) % 59}")
