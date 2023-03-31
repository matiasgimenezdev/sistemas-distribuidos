package com.example.server;

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
import com.github.dockerjava.api.model.Container;
import com.github.dockerjava.core.command.PullImageResultCallback;

import org.json.JSONException;
import org.json.JSONObject;

@RestController
@SpringBootApplication
public class Server {
	private static DockerClient dockerClient;
	private static String containerId = "";

	public Server() {
		dockerClient = DockerClientBuilder.getInstance().build();
	}

	@PostMapping("/remotetask")
	public ResponseEntity<String> ejecutarTareaRemota(@RequestBody String body) {
		try {
			JSONObject requestData = new JSONObject(body);

			// Obtiene los parammetros (para levantar el contenedor) del JSON enviado por el
			// cliente,
			String image = requestData.getString("dockerImage");
			String tag = requestData.getString("dockerImageTag");
			String containerName = requestData.getString("dockerContainerName");
			Integer port = requestData.getInt("port");
			pullImageAndRunContainer(image, tag, containerName, port);

			// Arma el JSON con los parametros para el servicio
			JSONObject params = new JSONObject();
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

			// Detiene el contenedor
			stopContainer(containerName);
			// Enviar el resultado de la ejecucion de la tarea al cliente.
			return ResponseEntity.ok(response.toString());

		} catch (JSONException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("JSON Error: " + e.getMessage());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error interno del servidor: " + e.getMessage());
		}
	}

	private static void pullImageAndRunContainer(String image, String tag, String containerName, Integer portNumber)
			throws Exception {
		try {
			dockerClient.pullImageCmd(image)
					.exec(new PullImageResultCallback())
					.awaitCompletion();
		} catch (Exception e) {
			e.printStackTrace();
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

		// // Crea el contenedor si todavia no existe
		if (containerId.equals("")) {
			String cmd = "docker container run -d -p 5000:5000 --name " + containerName + " " + image;
			Runtime.getRuntime().exec(cmd);
			System.out.println("Container started: " + containerName);
		} else {
			String cmd = "docker container start " + containerName;
			Runtime.getRuntime().exec(cmd);
			System.out.println("Container started: " + containerId);
		}
	}

	private static void stopContainer(String containerName) throws Exception {
		String cmd = "docker container stop " + containerName;
		Runtime.getRuntime().exec(cmd);
	}
}
