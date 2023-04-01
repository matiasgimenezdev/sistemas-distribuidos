package com.client;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.json.JSONObject;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try {
            Scanner sc = new Scanner(System.in);

            System.out.print("# Ingrese un valor mínimo: ");
            Double min = sc.nextDouble();
            System.out.print("# Ingrese un valor máximo: ");
            Double max = sc.nextDouble();
            System.out.println("");

            JSONObject body = new JSONObject();
            body.put("dockerImage", "mgimenezdev/task-service:v1");
            body.put("dockerImageTag", "v1");
            body.put("dockerContainerName", "task-service");
            body.put("port", 5000);
            body.put("path", "/task");
            body.put("min", min);
            body.put("max", max);

            HttpClient client = HttpClient.newHttpClient();
			HttpRequest request = HttpRequest.newBuilder()
									.uri(URI.create("http://localhost:8080/remotetask"))
									.POST(HttpRequest.BodyPublishers.ofString(body.toString()))
									.build();

            System.out.println("# Enviando datos al servidor: ");
            System.out.println("- Tarea: Calcular numero aleatorio");
            System.out.println("- Imagen Docker: mgimenezdev/task-service:v1");
            System.out.println("- Tag: v1");
            System.out.println("- Nombre de Contenedor: task-service");
            System.out.println("- Puerto : 5000");
            System.out.println("- Mínimo: " + min);
            System.out.println("- Máximo: " + max);
            System.out.println("");
            System.out.println("# Resolviendo la tarea...");
            System.out.println("");

            HttpResponse<String> response = null;
			response = client.send(request,
                        HttpResponse.BodyHandlers.ofString());
            JSONObject responseAsJson = new JSONObject(response.body().toString());
            Double result = responseAsJson.getDouble("result");
            System.out.println("Numero aleatorio: " + result.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}