package com.example;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class Client {
    public void run() throws IOException {
        int[] vector1 = {1, 2, 3};
        int[] vector2 = {4, 5, 6};
        String requestUrl = "http://localhost:8080/sum";

        URL url = new URL(requestUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        conn.setDoOutput(true);

        String requestBody = "vector1=" + Arrays.toString(vector1) + "&vector2=" + Arrays.toString(vector2);
        byte[] requestBodyBytes = requestBody.getBytes(StandardCharsets.UTF_8);

        OutputStream os = conn.getOutputStream();
        os.write(requestBodyBytes);
        os.flush();
        os.close();

        int responseCode = conn.getResponseCode();
        if (responseCode == 200) {
            String responseBody = Utils.convertStreamToString(conn.getInputStream());
            String[] resultAsString = responseBody.split(" ");
            int[] result = new int[resultAsString.length];
            for (int i = 0; i < resultAsString.length; i++) {
                result[i] = Integer.parseInt(resultAsString[i].trim());
            }
            System.out.println("La suma de los vectores " + Arrays.toString(vector1) + " y " +
                Arrays.toString(vector2) + " es " + Arrays.toString(result));
        } else {
            System.out.println("Error al hacer la solicitud: " + responseCode);
        }
    }

}
