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
import java.util.List;
import java.util.concurrent.TimeUnit;
import com.github.dockerjava.core.DockerClientBuilder;
import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.model.Image;
import org.springframework.web.bind.annotation.GetMapping;
import com.github.dockerjava.core.command.PullImageResultCallback;

import org.json.JSONException;
import org.json.JSONObject;

@RestController
@SpringBootApplication
public class Server {
	private DockerClient dockerClient;
	public static void main(String[] args) {
		SpringApplication.run(Server.class, args);
	}

	@PostMapping("/remotetask")
	public ResponseEntity<String> ejecutarTareaRemota(@RequestBody String body) {
		try{
			JSONObject requestData = new JSONObject(body);
			String image = requestData.getString("dockerImage");
			pullImageAndRunContainer(image);

			JSONObject params = new JSONObject();
			params.put("genericTask", requestData.get("genericTask"));
			params.put("min", requestData.getDouble("min"));
			params.put("max", requestData.getDouble("max"));

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
			return ResponseEntity.ok(response.toString());
		} catch(JSONException e){
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("JSON Error: " + e.getMessage());
		} catch (Exception e){
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor: " + e.getMessage());
		}
	}




	//Codigo que hace pull de una imagen docker existente.
	@GetMapping("test")
	public ResponseEntity<String> test(){
		try{
			this.dockerClient = DockerClientBuilder.getInstance().build();
			JSONObject response = new JSONObject();
			List<Image> imageListBefore = dockerClient.listImagesCmd().exec();
			response.put("before", imageListBefore);
			boolean bool = dockerClient.pullImageCmd("hello-world")
			.withTag("latest")
			.exec(new PullImageResultCallback())
			.awaitCompletion(100, TimeUnit.SECONDS);
			
			List<Image> imageListAfter = dockerClient.listImagesCmd().exec();
			response.put("after", imageListAfter);

			return ResponseEntity.ok(response.toString());
		} catch (Exception e){
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor: " + e.getMessage());
		}
	}

	private static void pullImageAndRunContainer(String image) {
		System.out.println("Hace un pull de: "+ image);
		System.out.println("Crea el contenedor a partir de: " + image);
	}
}
