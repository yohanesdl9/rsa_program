/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kripto_rsa;

import java.math.BigInteger;

/**
 *
 * @author Yohanes Dwi Listio
 */
public class RSA {

    private int n, p, q, pub, priv;
    private BigInteger theta;
    private String message;
    private char[] txt, decryptText;
    private int[] cipher, plain;

    public void setMessage(String message) {
        this.message = message;
        txt = this.message.toCharArray();
    }

    public void process() {
        System.out.println("Encryption process");
        enkripsiRSA();
        System.out.print("Ciphertext : ");
        display(cipher, true);
        System.out.println("\nDecryption process");
        dekripsiRSA();
        System.out.print("Plaintext : ");
        System.out.println(String.valueOf(decryptText));
    }

    public boolean setPub(int pub) {
        if (theta.compareTo(BigInteger.valueOf(pub)) <= 0 || pub <= 1) {
            System.out.println("Public key value must between 1 to (p-1)*(q-1). Try again");
            System.out.println("(p-1)*(q-1) = " + theta);
            return true;
        } else if (theta.gcd(BigInteger.valueOf(pub)).equals(BigInteger.ONE)) {
            this.pub = pub;
            return false;
        } else {
            System.out.println("Public key value is not co-prime to (p-1)*(q-1). Try again");
            return true;
        }
    }
    
    public boolean setPQ(int p, int q){
        /* Check if one of p or q values is 1 */
        if (p == 1 || q == 1){
            System.out.println("Value of p or q should not be 1. Try again.");
            return true;
        } else {
            /* Check the multiplication product p and q is more than 127 or not. */
            if (p * q <= 127){
                System.out.println("The multiplication product p and q must be more than 127. Try again.");
                return true;
            } else if (p == q) {
                System.out.println("Value of p and q should be distinct. Try again.");
                return true;
            } else {
                BigInteger pBig = BigInteger.valueOf(p);
                BigInteger qBig = BigInteger.valueOf(q);
                if (pBig.isProbablePrime(100) && qBig.isProbablePrime(100)){
                    this.p = p;
                    this.q = q;
                    n = p * q;
                    theta = qBig.subtract(BigInteger.ONE).multiply(pBig.subtract(BigInteger.ONE));
                    return false;
                } else {
                    System.out.println("One of value of p or q is not a prime number. Try again.");
                    return true;
                }
            }
        }
    }

    public boolean setN(int N) {
        /* Prevent the value of N is too low */
        if (N <= 128) {
            System.out.println("Choose positive N > 128. Try again.");
            return true;
        }
        BigInteger nBig = BigInteger.valueOf(N);
        BigInteger initNumber = new BigInteger("2");
        BigInteger pBig = initNumber;
        /* Process to find p and q that if multiplicated equals N.
        p and q must be a prime number (and each other are distinct too). */
        while (pBig.compareTo(nBig.divide(initNumber)) <= 0) {
            /* pBig < (nBig / 2) */
            if (nBig.mod(pBig).equals(BigInteger.ZERO)) {
                /* nBig is divisible by pBig */
                BigInteger qBig = nBig.divide(pBig); /* division result of nBig and pBig */
                if (qBig.isProbablePrime(100) && pBig.compareTo(qBig) != 0) {
                    /* check if qBig is a prime number and not same with pBig */
                    q = qBig.intValueExact();
                    p = pBig.intValueExact();
                    this.n = N;
                    theta = qBig.subtract(BigInteger.ONE).multiply(pBig.subtract(BigInteger.ONE));
                    return false;
                } else {
                    System.out.println("Wrong value of N. Try again.");
                    return true;
                }
            }
            pBig = pBig.nextProbablePrime();
        }
        System.out.println("N is prime number. Try again.");
        return true;
    }

    private void getPrivateKey() {
        /* Function to find private key from given public key and theta 
        that are result from multiplication of (p - 1) and (q - 1) */
        int i = 1;
        while (true) {
            BigInteger temp = theta.multiply(BigInteger.valueOf(i)).add(BigInteger.ONE);
            if (temp.mod(BigInteger.valueOf(pub)) == BigInteger.ZERO) {
                priv = temp.divide(BigInteger.valueOf(pub)).intValue();
                break;
            }
            i++;
        }
    }

    private void enkripsiRSA() {
        /* Encryption process with RSA algorithm */
        cipher = new int[txt.length];
        for (int j = 0; j < txt.length; j++) {
            int message = (int) txt[j];
            BigInteger chip = BigInteger.valueOf(message).pow(pub).mod(BigInteger.valueOf(n));
            cipher[j] = chip.intValue();
            /* Displays the calculation process to get chipertext of each character */
            System.out.println((int) txt[j] + " ^ " + pub + " mod " + n + " = " + cipher[j]);
        }
    }

    private void dekripsiRSA() {
        /* Decryption process with RSA algorithm */
        plain = new int[cipher.length];
        getPrivateKey();
        for (int j = 0; j < cipher.length; j++) {
            BigInteger plainT = BigInteger.valueOf(cipher[j]).pow(priv).mod(BigInteger.valueOf(n));
            plain[j] = plainT.intValue();
            /* Displays the calculation process to regain plaintext of each character */
            System.out.println(cipher[j] + " ^ " + priv + " mod " + n + " = " + plain[j]);
        }
        decryptText = new char[plain.length];
        for (int i = 0; i < decryptText.length; i++) {
            decryptText[i] = (char) plain[i];
        }
    }

    private void display(int[] array, boolean display_char) {
        /* display all array elements */
        for (int j : array) {
            if (display_char){
                System.out.print((char) j);
            } else {
                System.out.print(j + " ");
            }
        }
        System.out.println();
    }
}
