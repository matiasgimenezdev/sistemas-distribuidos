package com.example.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.json.JSONException;
import org.json.JSONObject;
import com.google.gson.Gson;
import java.util.List;
import java.util.concurrent.TimeUnit;
import com.github.dockerjava.core.DockerClientBuilder;
import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.model.Image;
import com.github.dockerjava.core.command.PullImageResultCallback;

@RestController
@SpringBootApplication
public class Service {
	private DockerClient dockerClient;

	public static void main(String[] args) {
		SpringApplication.run(Service.class, args);
	}

	@PostMapping("/task")
	public ResponseEntity<String> ejecutarTarea(@RequestBody String body) {
		try{
			Gson gson = new Gson();
			JSONObject parameters = new JSONObject(body);
			JSONObject response = new JSONObject();
			Tarea genericTask = gson.fromJson(parameters.get("genericTask").toString(), TareaGenerica.class); 
			response.put("result", genericTask.ejecutar());
			return ResponseEntity.ok(response.toString());
		} catch(JSONException e){
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("JSON Error: " + e.getMessage());
		} catch (Exception e){
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor: " + e.getMessage());
		}
	}

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

	private JSONObject ejecutarTarea(JSONObject parameters) {


	}
}


