package com.sd.java.sobel.controllers;

import com.sd.java.sobel.services.HttpRequests;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.http.HttpResponse;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import javax.imageio.ImageIO;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@SpringBootApplication
public class SplitController {

  @PostMapping(value = "/split", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<String> split(
    @RequestParam("file") MultipartFile file,
    @RequestParam("parts") String parts
  ) {
    try {
      byte[] image = file.getBytes();
      BufferedImage originalImage = ImageIO.read(
        new ByteArrayInputStream(image)
      );
      Integer splitParts = Integer.parseInt(parts);
      Integer width = originalImage.getWidth();
      Integer height = originalImage.getHeight();
      Integer divisionWidth = width / splitParts;

      // El task ID deberia ser generado de alguna forma aleatoria.
      String taskId = "task8585";
      for (int i = 0; i < splitParts; i++) {
        int x = i * divisionWidth;
        int y = 0;
        int divisionHeight = height;

        if (i == splitParts - 1) {
          // Ajustar el ancho si la división no da exacta
          divisionWidth = width - (divisionWidth * i);
        }

        BufferedImage division = originalImage.getSubimage(
          x,
          y,
          divisionWidth,
          divisionHeight
        );

        // Guardar cada división en un archivo separado

        // Path currentPath = Paths.get("").toAbsolutePath();
        // String divisionPath =
        //   currentPath + "/image-parts/division" + i + ".jpg";
        // ImageIO.write(division, "jpg", new File(divisionPath));

        // Genera la tarea para los workers y la envia al servicio de cola de mensajes en una peticion.
        HttpRequests httpRequests = new HttpRequests();
        String url = "http://task-queue-service/taskmanager/todo";
        HashMap<String, String> parameters = new HashMap<>();
        parameters.put("taskId", taskId + "-" + i);
        parameters.put("source", "source-bucket");
        parameters.put("destination", "destination-bucket");
        httpRequests.PostHttpRequest(url, parameters);

        HttpResponse<String> response = httpRequests.PostHttpRequest(
          url,
          parameters
        );
        if (response.statusCode() != HttpStatus.OK.value()) {
          return new ResponseEntity<>(
            "TASK_TODO_ERROR",
            HttpStatus.INTERNAL_SERVER_ERROR
          );
        }
      }

      // Genera la tarea para el servicio de unificacion y la envia al servicio de cola de mensajes en una peticion.
      HttpRequests httpRequests = new HttpRequests();
      String url = "http://task-queue-service/taskmanager/register";
      HashMap<String, String> parameters = new HashMap<>();
      parameters.put("taskId", taskId);
      parameters.put("parts", splitParts.toString());
      parameters.put("width", width.toString());
      parameters.put("height", height.toString());
      try {
        HttpResponse<String> response = httpRequests.PostHttpRequest(
          url,
          parameters
        );
        if (response.statusCode() == HttpStatus.OK.value()) {
          return new ResponseEntity<>("TASK_TOTALLY_REGISTERED", HttpStatus.OK);
        } else {
          return new ResponseEntity<>(
            "TASK_REGISTER_ERROR",
            HttpStatus.INTERNAL_SERVER_ERROR
          );
        }
      } catch (IOException | InterruptedException e) {
        e.printStackTrace();
        return new ResponseEntity<>(
          "TASK_REGISTER_ERROR",
          HttpStatus.INTERNAL_SERVER_ERROR
        );
      }
    } catch (Exception e) {
      return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
  }
}
