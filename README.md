# RSA Program
Program Java console based to implement an encryption and decryption using RSA algorithm.

## RSA Encryption Overview
RSA algorithms are the most popular, because they are considered "safe":
* Do the factoring of very large numbers.
* To generate 2 keys, two large random primes are selected

To perform RSA encryption, the original text is arranged into blocks $x_1, x_2$, ..... such that each block represents a value in the range of $r-1$. Each block $x_i$ is encrypted into a $y_i$ block with the formula:
$C = P^e mod n$
