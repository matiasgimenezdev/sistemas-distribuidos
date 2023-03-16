package com.example;

import java.util.Scanner;

public class App {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in); // Crear un objeto Scanner
        System.out.println("");
        System.out.println("1- Iniciar Servidor de Clima");
        System.out.println("2- Iniciar Cliente");
        System.out.println("");
        System.out.print("Ingrese una opcion: ");
        int option = Integer.parseInt(scanner.nextLine().trim());
        System.out.println("");

        switch (option) {
            case 1:
                try {
                    // código que lanza la excepción
                    new WeatherServer().run();
                } catch (Exception e) {
                    // manejo de la excepción
                    e.toString();
                }
                break;
            case 2:
                try {
                    // código que lanza la excepción
                    new WeatherClient().run();
                } catch (Exception e) {
                    e.toString();
                }
                break;
            default:
                break;
        }
    }

}
