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
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import com.github.dockerjava.core.DockerClientBuilder;
import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.model.Container;
import com.github.dockerjava.api.model.ExposedPort;

import org.springframework.web.bind.annotation.GetMapping;
import com.github.dockerjava.core.command.PullImageResultCallback;

import org.json.JSONException;
import org.json.JSONObject;

@RestController
@SpringBootApplication
public class Server {
	private static DockerClient dockerClient;
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
			
			dockerClient.pullImageCmd("hello-world")
			.withTag("latest")
			.exec(new PullImageResultCallback())
			.awaitCompletion(100, TimeUnit.SECONDS);
			
			// ExposedPort tcp01 = ExposedPort.tcp(5000);
			// ExposedPort tcp02 = ExposedPort.tcp(5000);
			List<Container> containerList = dockerClient.listContainersCmd()
			.withShowAll(true)
			.withShowSize(true)
			.exec();

			String containerId = "";
			if(containerList.size() > 0){
				for(Container container: containerList) {
					if(container.getNames()[0].equals("/testing-container")){
						System.out.println("Existe el contenedor");
						containerId = container.getId();
					}
				}
			}
			System.out.println(containerId);
			if(containerId.equals("")) {
				System.out.println("Creando el contenedor");
				CreateContainerResponse containerResponse = dockerClient
				.createContainerCmd("hello-world")
				.withName("testing-container")
				.withHostName("localhost")
				// .withExposedPorts(tcp01, tcp02)
				.exec();
			}
						
			dockerClient.startContainerCmd(containerId).exec();
			response.put("containers", containerList);
			return ResponseEntity.ok(response.toString());
		} catch (Exception e){
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor: " + e.getMessage());
		}
	}

	private static void pullImageAndRunContainer(String image) throws Exception {		
		dockerClient = DockerClientBuilder.getInstance().build();
		
		dockerClient.pullImageCmd(image)
		.withTag("latest")
		.exec(new PullImageResultCallback())
		.awaitCompletion(100, TimeUnit.SECONDS);
		

		List<Container> containerList = dockerClient.listContainersCmd()
		.withShowAll(true)
		.withShowSize(true)
		.exec();

		String containerId = "";
		if(containerList.size() > 0){
			for(Container container: containerList) {
				if(container.getNames()[0].equals("/"+image)){
					containerId = container.getId();
				}
			}
		}


		System.out.println(containerId);
		// ExposedPort tcp01 = ExposedPort.tcp(5000);
		// ExposedPort tcp02 = ExposedPort.tcp(5000);
		if(containerId.equals("")) {
			CreateContainerResponse containerResponse = dockerClient
			.createContainerCmd(image)
			.withName("testing-container")
			.withHostName("localhost")
			// .withExposedPorts(tcp01, tcp02)
			.exec();
		}
					
		dockerClient.startContainerCmd(containerId).exec();

	}
}
