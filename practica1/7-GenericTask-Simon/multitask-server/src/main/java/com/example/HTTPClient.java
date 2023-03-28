package com.example;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;

public class HTTPClient {

  public void run() {
    try {
      // URL del servidor
      String url = "http://localhost:8080/ejecutarTareaRemota";

      // Crear objeto JSON con los parámetros
      JSONObject parametros = new JSONObject();
      parametros.put("parametro1", "valor1");
      parametros.put("parametro2", "valor2");

      // Crear solicitud HTTP POST
      HttpURLConnection conexion = (HttpURLConnection) new URL(url).openConnection();
      conexion.setRequestMethod("POST");
      conexion.setRequestProperty("Content-Type", "application/json");
      conexion.setDoOutput(true);

      // Enviar parámetros al servidor
      Writer escritor = new OutputStreamWriter(conexion.getOutputStream());
      escritor.write(parametros.toString());
      escritor.flush();
      escritor.close();

      // Leer respuesta del servidor
      BufferedReader lector = new BufferedReader(new InputStreamReader(conexion.getInputStream()));
      StringBuilder respuesta = new StringBuilder();
      String linea;
      while ((linea = lector.readLine()) != null) {
        respuesta.append(linea);
      }
      lector.close();

      // Mostrar respuesta del servidor
      System.out.println("Respuesta del servidor: " + respuesta.toString());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}