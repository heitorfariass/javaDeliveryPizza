package javaDeliveryPizza.util;

import java.util.Scanner;

public class EntradaDados {
    public static int lerInteiro(Scanner sc){
        while(true){
            String linha = sc.nextLine();
            try{
                return Integer.parseInt(linha.trim());
            }catch(NumberFormatException e){
                System.out.println("Valor inválido");
            }
        }
    }

    public static double lerDouble(Scanner sc){
        while(true){
            String linha = sc.nextLine();
            try{
                return Double.parseDouble(linha.trim());
            }catch(NumberFormatException e){
                System.out.println("Valor inválido");
            }
        }
    }

    public static String lerLinha(Scanner sc){
        return sc.nextLine();
    }
}
