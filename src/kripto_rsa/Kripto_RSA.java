/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kripto_rsa;

import java.util.Scanner;

/**
 *
 * @author Yohanes Dwi Listio
 */
public class Kripto_RSA {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        int p, q;
        Scanner input = new Scanner(System.in);
        RSA rsa = new RSA();
        System.out.print("Enter message : ");
        rsa.setMessage(input.nextLine());
        do {
            System.out.print("Enter value of p : ");
            p = input.nextInt();
            System.out.print("Enter value of q : ");
            q = input.nextInt();
        } while (rsa.setPQ(p, q));
        do {
            System.out.print("Enter public key : ");
        } while (rsa.setPub(input.nextInt()));
        rsa.process();
    }
    
}
