package com.example.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

@RestController
@SpringBootApplication
public class Server {

	public static void main(String[] args) {
		SpringApplication.run(Server.class, args);
	}

	@PostMapping("/remotetask")
	public ResponseEntity<String> postRequest(@RequestBody String body) {
		try{
			JSONObject bodyAsjson = new JSONObject(body);
			String resultado = ejecutarTareaRemota(bodyAsjson);
			return ResponseEntity.ok(resultado);

		} catch(JSONException e){
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("JSON Error: " + e.getMessage());
		} catch (Exception e){
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor: " + e.getMessage());
		}
	}

	private String ejecutarTareaRemota(JSONObject data) throws Exception, JSONException{
		String dockerImage = data.getString("dockerImage");
		System.out.println("Hace un pull de: "+ dockerImage);
		System.out.println("Crea el contenedor a partir de: " + dockerImage);
		
		JSONObject params = new JSONObject();
		params.put("genericTask", data.get("genericTask"));
		StringBuffer response = new StringBuffer();

		URL url = new URL("http://localhost:5000/task");
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("POST");
		connection.setRequestProperty("Content-Type", "application/json");
		connection.setDoOutput(true);

		OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
		out.write(params.toString());
		out.flush();
		out.close();

		BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		String inputLine;
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		return response.toString();
    }
}
