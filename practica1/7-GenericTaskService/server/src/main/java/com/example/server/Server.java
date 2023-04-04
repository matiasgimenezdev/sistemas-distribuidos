package com.example.server;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.List;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientConfig;
import com.github.dockerjava.core.DockerClientImpl;
import com.github.dockerjava.httpclient5.ApacheDockerHttpClient;
import com.github.dockerjava.transport.DockerHttpClient;
import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.model.Container;
import com.github.dockerjava.api.model.HostConfig;
import com.github.dockerjava.api.model.PortBinding;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.command.PullImageResultCallback;
import com.github.dockerjava.api.command.WaitContainerResultCallback;

import org.json.JSONException;
import org.json.JSONObject;

@RestController
@SpringBootApplication
public class Server {
	private static Printer printer;
	private static DockerClient dockerClient;
	private static String containerId = "";

	public Server() {
		DockerClientConfig customConfig = DefaultDockerClientConfig.createDefaultConfigBuilder()
		.withDockerHost("unix:///var/run/docker.sock")
		.withDockerConfig("/home/$USER/.docker")
		.withDockerTlsVerify(false)
		.build();
		
		DockerHttpClient httpClient = new ApacheDockerHttpClient.Builder()
		.dockerHost(customConfig.getDockerHost())
		.maxConnections(100)
		.connectionTimeout(Duration.ofSeconds(30))
		.responseTimeout(Duration.ofSeconds(45))
		.build();

		dockerClient = DockerClientImpl.getInstance(customConfig, httpClient);
		printer = new Printer();
	}

	@PostMapping("/remotetask")
	public ResponseEntity<String> ejecutarTareaRemota(@RequestBody String body) {
		try {
			JSONObject requestData = new JSONObject(body);

			// Obtiene los parammetros (para levantar el contenedor) del JSON.
			String image = requestData.getString("dockerImage");
			String tag = requestData.getString("dockerImageTag");
			String containerName = requestData.getString("dockerContainerName");
			Integer port = requestData.getInt("port");
			String path = requestData.getString("path");

			pullImageAndRunContainer(image, tag, containerName, port);

			// Arma el JSON con los parametros para el servicio
			JSONObject params = new JSONObject();
			params.put("min", requestData.getDouble("min"));
			params.put("max", requestData.getDouble("max"));

			
			HttpClient client = HttpClient.newHttpClient();
			HttpRequest request = HttpRequest.newBuilder()
									.uri(URI.create("http://localhost:" + port + path))
									.POST(HttpRequest.BodyPublishers.ofString(params.toString()))
									.build();
			
			HttpResponse<String> response = null;
			response = client.send(request,
                        HttpResponse.BodyHandlers.ofString());
			
			stopContainer(containerName);

			return ResponseEntity.ok(response.body());
		} catch (JSONException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}


	private static void stopContainer(String containerName) {
		dockerClient.stopContainerCmd(containerId).exec();
		dockerClient.waitContainerCmd(containerId).exec(new WaitContainerResultCallback()).awaitStatusCode();
		printer.showMessage("# Contenedor detenido.");
	}



	private static void pullImageAndRunContainer(String image, String tag, String containerName, Integer port) throws Exception {
		
		try {
			dockerClient
			.inspectImageCmd(image)
			.exec();
		} catch(Exception e) {
			printer.showMessage("# Descargando la imagen...");
			dockerClient.pullImageCmd(image)
				.exec(new PullImageResultCallback())
				.awaitCompletion();
			printer.showMessage("# Imagen descargada.");
			
		}

		List<Container> containerList = dockerClient.listContainersCmd()
				.withShowAll(true)
				.exec();
		JSONObject containers = new JSONObject(containerList);

		if (!containers.getBoolean("empty")) {
			for (Container container : containerList) {
				if (container.getNames()[0].equals("/" + containerName)) {
					containerId = container.getId();
				}
			}
		}

		if (containerId.equals("")) {
			printer.showMessage("# Creando el contenedor...");
			HostConfig hostConfig = HostConfig.newHostConfig().withPortBindings(PortBinding.parse(port.toString()+":" + port.toString()));
			CreateContainerResponse containerResponse = dockerClient
			.createContainerCmd(image)
			.withName(containerName)
			.withHostConfig(hostConfig)
			.exec();
			containerId = containerResponse.getId();
			dockerClient.startContainerCmd(containerId).exec();
			dockerClient.waitContainerCmd(containerId).exec(new WaitContainerResultCallback()).awaitStarted();
			Thread.sleep(7000);
			printer.showMessage("# Contenedor iniciado.");
		} else {
			Boolean isRunning = dockerClient
					.inspectContainerCmd(containerId)
					.exec()
					.getState()
					.getRunning();
			if (!isRunning) {
				System.out.println("");
				System.out.println("# Iniciado el contenedor...");
				dockerClient.startContainerCmd(containerId).exec();
				dockerClient.waitContainerCmd(containerId).exec(new WaitContainerResultCallback()).awaitStarted();
				Thread.sleep(7000);
				printer.showMessage("# Contenedor iniciado.");
			}
		}
	}
}
