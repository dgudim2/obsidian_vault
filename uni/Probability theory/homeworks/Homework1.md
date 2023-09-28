
# Task 1

> [!info] 
> 
> A six-card hand is dealt from an ordinary deck of cards. Find the probability that:
> - All **Six** cards are *hearts*
> - There are *three* aces, *two* kings, and *one* queen
> - There are *three* cards of **one** suit and three of **another** suit

## All six cards are hearts

- Total number of hearts: **13**
- All Combinations of **6 hearts** from all the hearts: $C_{13}^{6}$ 
- All combinations of **6 cards**: $C_{52}^{6}$
- _Probability_: $$\frac{C_{13}^{6}}{C_{52}^{6}}=\frac{1716}{20358520} \approx 8.4289*10^{-5}$$
## There are three aces, two kings, and one queen

- Total number of aces: **4**
- Total number of queens: **4**
- Total number of kings: **4**
- *Probability*: $$\frac{C_{4}^3*C_{4}^2*C_{4}^1}{C_{52}^6}=\frac{4*6*4}{20358520}\approx 4.71547*10^{-6}$$

## There are three cards of one suit and three of another suit

- Number of cards in a suit: **13**
- _Probability_: $$\frac{C_{13}^3*C_{13}^3}{C_{52}^6}=\frac{286*286}{20358520}=0.00401778$$

# Task 2

> [!info]
> In how many ways can a cricket eleven be chosen out of a *batch of 15 players* if:
> - There is **no restriction** on the selection
> - A Particular Player is **always chosen**
> - A Particular Player is **never chosen**

## No restrictions

- We need to select **11** players from a batch of **15**, order does not matter, we will use **combinations**

$$C_{15}^{11}=1365$$

## A particular player is always chosen

- In this case we only need to pick **10** people from 14

$$C_{14}^{10}=1001$$

## A particular player is never chosen

- In this case we only have **14** players to choose from

$$C_{14}^{11}=364$$

# Task 3

> [!info] 
> Count the number of anagrams of the following words:
> - error
> - father
> - allele
> - Mississippi

- Basically **permutations with repetition** for all of them
- error: $P_{1,3,1}=\frac{(1+3+1)!}{1!3!1!}=20$
- father: $P_{1,1,1,1,1,1}=6! = 720$
- allele: $P_{1,3,2}=\frac{6!}{1!3!2!}=60$
- Mississippi: $P_{1,4,4,2}=\frac{11!}{1!4!4!2!}=34650$

# Task 4

> [!info] 
> 
> Find the number of possible <u>10 character passwords</u> under the following restrictions: (Note there are 26 letters in the alphabet):
> - **All** characters must be *lower case* letters
> - **All** characters must be *lower case* letters and *distinct*
> - *Letters* and *digits* must *alternate* and be *distinct* (as in ”1w2x9c4u5s” or ”a1b2c3d4e5”)
> - The word can *only contain the upper case* letters **A** and **B**
> - The word can *only contain the upper case* letters **A** and **B**, and must contain each of these
> letters
> - The word can *only contain the upper case* letters **A** and **B**, and must contain an **equal number of each**

## All characters must be lower case letters

- Order *does* matter, there are *10* characters in the password, *26* in the alphabet, there can be *repetition* we choose **Variations with repetition**

$$26^{10}=141167095653376$$

## All characters must be lower case letters and distinct

- Same as the last one, but *without repetition*, we choose **Variations without repetition**

$$\frac{26!}{(26-10)!} = 19275223968000$$

## Letters and digits must alternate and be distinct

- We basically need *2* strings, one from **5** distinct letters, another from **5** distinct numbers. Order does matter, we will use **Variations without repetition**

$$V_{26}^5*V_{10}^5=65780*252=5012751744000$$

## The word can only contain the upper case letters A and B

- There can be **repetition**, order does matter, so we use **Variations with repetition**

$$2^{10}=1024$$

## The word can only contain the upper case letters A and B, and must contain each of these letters

- Just as the previous one, but *AAAAAAAAAA* and *BBBBBBBBBB* are invalid

Answer is **1022**

## The word can only contain the upper case letters A and B, and must contain an equal number of each

- We can rephrase this to "In how many ways we can arrange **5 letters A** and **5 letters B**"
- We will use **Permutations with repetition**

$$\frac{(5+5)!}{5!*5!}=252$$

# Task 5

> [!info]
> **8** students on a student council are assigned **8 seats** around a U-shaped table:
> - How many different ways can the students be *assigned seats* at the table?
> - How many ways can a *president and a vice-president be elected* from the 8 students?

## How many different ways can the students be assigned seats at the table?

- Basically **permutations without repetition**

$$P_{8}=8! = 40320$$

## How many ways can a president and a vice-president be elected from the 8 students?

- **8** ways to select a **president**, then **7** ways to select a **vice-president** or *vice-versa*

$$8*7*2=112$$