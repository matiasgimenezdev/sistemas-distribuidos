package com.example;

import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);  // Crear un objeto Scanner
        System.out.println("");
        System.out.println("1- Iniciar Servidor TCP");
        System.out.println("2- Iniciar Cliente TCP");
        System.out.println("");
        System.out.print("Ingrese una opcion: ");
        int option =Integer.parseInt( scanner.nextLine().trim()); 
        System.out.println("");
        scanner.close();

        switch (option) {
            case 1:
                new TCPServer().run();
                break;
            case 2:
                new TCPClient().run();
                break;
            default:
                break;
        }
    }
}
