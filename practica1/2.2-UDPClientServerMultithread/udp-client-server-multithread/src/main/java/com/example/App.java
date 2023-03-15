package com.example;

import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);  // Crear un objeto Scanner
        System.out.println("");
        System.out.println("1- Iniciar Servidor UDP");
        System.out.println("2- Iniciar Cliente UDP");
        System.out.println("");
        System.out.print("Ingrese una opcion: ");
        int option =Integer.parseInt( scanner.nextLine().trim()); 
        System.out.println("");

        switch (option) {
            case 1:
                new UDPServerMultithread().run();
                break;
            case 2:
                new UDPClient().run();
                break;
            default:
                break;
        }
    }
}