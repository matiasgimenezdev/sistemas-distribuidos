package com.example.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;

@RestController
@SpringBootApplication
public class Server {

	public static void main(String[] args) {
		SpringApplication.run(Server.class, args);
	}

	@PostMapping("/remotetask")
	public String postRequest(@RequestBody String body) {
		JSONObject bodyAsjson = new JSONObject(body);
		String resultado = ejecutarTareaRemota(bodyAsjson);
		return(resultado);
	}

	private String ejecutarTareaRemota(JSONObject data) {
		JSONObject params = new JSONObject();
		params.put("min", data.get("min"));
		params.put("max", data.get("max"));
		StringBuffer response = new StringBuffer();
		try{
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
		} catch(Exception e){
			e.printStackTrace();
		}
		
		return response.toString();
    }
}
