---
cssclasses:
  - wide-page
---

# Hash Collisions in Hash Functions: Implications on Security

- Table of Contents

    - [[#1.Introduction]]
    - [[#2.Theoretical Part]] 
        - [[#2.1 Hash Functions and their Properties]]
        - [[#2.2 Hash Collisions: Definition and Causes]] 
        - [[#2.3 Collision Resistance and its Importance]]
        - [[#2.4 Types of Collision Attacks]]
    - [[#Practical part]]
        - [[#3.1 Birthday Paradox and its Impact]]
        - [[#3.2 Mitigating the Risk of Collisions]]
        - Real world
            - [[#3.3 Real-World Examples of Collision Exploits]]
            - [[#3.4 Hash Table Collisions and Resolution Techniques]]
            - [[#3.5 Cryptographic Hash Functions in Different Applications]]
    - [[#Conclusion]]
    - [[#References]]

## 1.Introduction

Hash functions are very important in modern computing, serving as a *base for security protocols* and data management systems. These functions transform input data of variable length into a fixed-size string of characters - hash or digest. The strength of a hash function lies in its *ability to produce unique outputs for distinct inputs*. But, because the input space is potentially infinite, collisions will occur — situations where two different inputs produce the same hash. While collisions are a theoretical certainty, the security of systems relying on hash functions relies on the *computational infeasibility of finding these collisions*. This essay will explore the theory behind of hash collisions, delve into their practical implications for security, examine real-world examples of collision exploits, and discuss mitigation strategies.

## 2.Theoretical Part

### 2.1 Hash Functions and their Properties

A hash function, denoted as $H(x)$, takes an input value 'x' and produces a hash value 'h' ($H(x) = h$). For cryptographic purposes, ideal hash functions should exhibit *the following key properties*:

- **Determinism**: Identical inputs always yield the same hash output. This property is crucial for data integrity checks.
- **Efficiency**: Hash computation should be fast and require minimal computational resources. This is essential for practical applications.
- **Pre-image Resistance (One-wayness)**: Given a hash value $h$, it should be computationally infeasible to find an input $x$ such that $H(x) = h$. This prevents attackers from recovering the original input from its hash.
- **Second Pre-image Resistance (Weak Collision Resistance)**: Given an input $x$, it should be computationally infeasible to find a different input $x'$ such that $H(x) = H(x')$. This prevents attackers from substituting one input with another that produces the same hash (For example if file is verified with a hash). We don't care if 2 inputs produce the same hash on accident though.
- **Collision Resistance (Strong Collision Resistance)**: It should be computationally infeasible to find any two distinct inputs $x$ and $x'$ such that $H(x) = H(x')$. This is the most crucial property for many security applications, as it ensures the uniqueness of hash values. In this case, NO 2 values should produce the same hash in any circumstance  

### 2.2 Hash Collisions: Definition and Causes

A hash collision occurs when two distinct inputs, 'x' and 'x'', produce the same hash output: H(x) = H(x'). This phenomenon is a direct consequence of the pigeonhole principle. If there are more pigeons (possible inputs) than pigeonholes (possible hash outputs), then at least two pigeons must share a hole. In the context of hash functions, the input space is typically much larger than the output space, guaranteeing the existence of collisions.

### 2.3 Collision Resistance and its Importance

Collision resistance is paramount for the security of various cryptographic applications:

- **Digital Signatures**: Digital signatures rely on hash functions to create a concise representation of a message. If collisions can be found, an attacker could create two different messages with the same hash, allowing them to *forge a signature from a legitimate signer*.
- **Password Storage**: Storing passwords directly is a huge security risk. Instead, systems store the hash of the password. If a hash function is not collision-resistant, an attacker could find a different password that hashes to the same value, gaining *unauthorized access*.
- **Data Integrity Verification**: Hash functions are used to verify the integrity of data. If a file is modified, its hash will change. However, if collisions can be found, an attacker could modify the file and create a *different version with the same hash*, making the modification undetectable.
- **Message Authentication Codes (MACs)**: MACs use hash functions to generate a tag that authenticates the message. Collision resistance is essential to prevent *forgery attacks*.

### 2.4 Types of Collision Attacks

Different types of attacks exploit weaknesses in hash functions related to collisions:

- **Brute-Force Attack**: This attack attempts to find a collision by randomly generating inputs and computing their hashes until a match is found. The complexity of this attack depends on the size of the hash output space.
    Birthday Attack: This attack exploits the birthday paradox to find collisions more efficiently than a brute-force attack.
- **Cryptanalytic Attacks**: These attacks exploit specific weaknesses in the design of the hash function to find collisions more efficiently than generic attacks. Examples include differential cryptanalysis and chosen-prefix collision attacks.


## Practical part


### 3.1 Birthday Paradox and its Impact

> https://en.wikipedia.org/wiki/Birthday_problem

The birthday paradox illustrates that the probability of finding a collision in a set of randomly chosen values is surprisingly high. In the context of hash functions with an output space of 'n' bits, the birthday paradox suggests that a collision can be found with a probability of approximately 50% after evaluating approximately $2^{(n/2)}$ hashes. This means that the effective security of a hash function against collision attacks is halved compared to its output size. For instance, a *128-bit* hash function offers only *64 bits of security* against collision attacks.

### 3.2 Mitigating the Risk of Collisions

Several strategies can be employed to mitigate the risk of collisions:

- **Using Stronger Hash Functions**: Employing hash functions with larger output sizes, such as SHA-256, SHA-384, SHA-512, or SHA-3, significantly increases the computational cost of finding collisions.
- **Salting Passwords**: Adding a random "salt" to passwords before hashing them makes it much harder for attackers to use precomputed tables of hashes (rainbow tables) to crack passwords.
- **HMAC** (Hash-based Message Authentication Code): HMAC uses a secret key along with a hash function to generate a message authentication code, providing both data integrity and authentication.
- **Code Signing Certificates**: Using code signing certificates from trusted Certificate Authorities (CAs) helps to ensure the integrity and authenticity of software.

### 3.3 Real-World Examples of Collision Exploits

- **MD5 Collisions**: *MD5*, once a widely used hash function, has been shown to be highly vulnerable to collision attacks. Researchers have demonstrated the ability to create two different files with the same *MD5* hash, enabling various attacks, including *digital signature forgery* and *malware distribution*. (https://www.mscs.dal.ca/~selinger/md5collision/)

> [!example] 
> One of the famous examples is this:
> 
> ```
> d131dd02c5e6eec4693d9a0698aff95c2fcab5 8 712467eab4004583eb8fb7f89 
> 55ad340609f4b30283e4888325 7 1415a085125e8f7cdc99fd91dbd f 280373c5b 
> d8823e3156348f5bae6dacd436c919c6dd53e2 b 487da03fd02396306d248cda0 
> e99f33420f577ee8ce54b67080 a 80d1ec69821bcb6a8839396f965 2 b6ff72a70
> 
> and
> 
> d131dd02c5e6eec4693d9a0698aff95c2fcab5 0 712467eab4004583eb8fb7f89 
> 55ad340609f4b30283e4888325 f 1415a085125e8f7cdc99fd91dbd 7 280373c5b 
> d8823e3156348f5bae6dacd436c919c6dd53e2 3 487da03fd02396306d248cda0 
> e99f33420f577ee8ce54b67080 2 80d1ec69821bcb6a8839396f965 a b6ff72a70
> ```
> 
> These 2 blocks have the same MD5 hash `79054025255fb1a26e4bc422aef54eb4`

> [!example]
> Another example with executables
> Executable 1: [[uni/Cryptography/attachments/hello|hello]]
> Executable 2: [[uni/Cryptography/attachments/erase|erase]]
> 
> ![[evil-twin.gif]]

- **SHA-1 Collisions**: *SHA-1*, a successor to *MD5*, was also found to be vulnerable to collision attacks, although the attacks were more computationally expensive. This led to the *deprecation of SHA-1* in many applications and its replacement with stronger hash functions like *SHA-256* and *SHA-3*.
- **Flame Malware**: The Flame malware, discovered in 2012, exploited *MD5 collisions* to *forge digital signatures*, allowing it to spread undetected through Windows Update mechanisms. (https://en.wikipedia.org/wiki/Flame_%28malware%29)

### 3.4 Hash Table Collisions and Resolution Techniques

Hash tables, a fundamental data structure, utilize hash functions to map keys to indices in an array. Collisions are inevitable when different keys map to the same index. Several techniques exist to resolve these collisions:

- **Chaining**: Each index in the array points to a linked list of entries that hash to that index.
- **Open Addressing**: When a collision occurs, the algorithm probes for an alternative empty slot in the array using techniques like <u>linear probing</u>, <u>quadratic probing</u>, or <u>double hashing</u> 

### 3.5 Cryptographic Hash Functions in Different Applications

- **Git Version Control**: Git uses **SHA-1** (though now migrating to **SHA-256**) to identify and track changes in files and directories. (https://stackoverflow.com/questions/10434326/hash-collision-in-git)
- **Blockchain Technology**: Blockchains heavily rely on hash functions to ensure the integrity and immutability of the transaction history.
- **TLS/SSL**: These protocols use hash functions for various cryptographic operations, including *key exchange* and *message authentication*.

## Conclusion

Hash collisions are an inherent characteristic of hash functions, arising from the mapping of a *larger input space* to a *smaller output space*. While theoretically unavoidable, the security of systems relying on hash functions depends on the computational infeasibility of finding these collisions. The birthday paradox shows how important is it ti use hash functions with *sufficiently large output sizes* to provide security against collision attacks. The vulnerabilities of older hash functions like **MD5** and **SHA-1** show the need for continuous research and development of stronger cryptographic primitives. By understanding the theoretical underpinnings of hash collisions. The transition to more robust algorithms like **SHA-256**, along with best practices like salting passwords and using **HMACs**, are crucial steps in safeguarding against the risks posed by hash collisions.

## References

- Bloom, B. H. (1970). Solving problems in cryptography. American Mathematical Monthly, 77(2), 150-155. (Access may require institutional access) https://www.tandfonline.com/doi/abs/10.1080/00029890.1970.11992454
- Chacon, S., & Straub, B. (2014). Pro Git. Apress. https://git-scm.com/book/en/v2
- Knuth, D. E. (1998). The art of computer programming, volume 3: Sorting and searching. Addison-Wesley Professional.
- Menezes, A. J., van Oorschot, P. C., & Vanstone, S. A. (1996). Handbook of applied cryptography. CRC press. http://cacr.uwaterloo.ca/hac/ (Free online version available)
- Nakamoto, S. (2008). Bitcoin: A peer-to-peer electronic cash system. https://bitcoin.org/bitcoin.pdf
- National Institute of Standards and Technology (NIST). (2015). Secure Hash Standard (SHS). FIPS PUB 180-4. https://nvlpubs.nist.gov/nistpubs/FIPS/NIST.FIPS.180-4.pdf
- Ore, O. (1988). Number Theory. Courier Dover Publications.
- Rescorla, E. (2001). RFC 2246: The TLS Protocol Version 1.0. https://datatracker.ietf.org/doc/html/rfc2246 
- Stallings, W. (2018). Cryptography and network security: Principles and practice. Pearson Education Limited.
- Stevens, M., Bursztein, E., Gueron, S., & Valenta, R. (2017). The first collision for full SHA-1. In Advances in Cryptology–CRYPTO 2017: 37th Annual International Cryptology Conference, Santa Barbara, CA, USA, August 20–24, 2017, Proceedings, Part I 37 (pp. 570-596). Springer International Publishing. https://shattered.io/static/shattered.pdf