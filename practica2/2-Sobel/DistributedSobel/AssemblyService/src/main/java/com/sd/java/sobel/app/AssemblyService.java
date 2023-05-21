package com.sd.java.sobel.app;

import com.sd.java.sobel.services.HttpRequests;
import java.net.http.HttpResponse;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@SpringBootApplication
@Service
public class AssemblyService {

  public AssemblyService() {
    this.processTasks();
  }

  private void processTasks() {
    // Realiza peticion HTTP al servicio de cola de mensajes para obtener TASKS_REGISTER
    taskRegister();

    // Realiza peticion HTTP al servicio de cola de mensajes para obtener TASKS_TODO
    // taskStatusUpdate();
    // 1 segundo de delay entre tareas.
    try {
      Thread.sleep(1000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  private void taskRegister() {
    HttpRequests httpRequests = new HttpRequests();
    String url = "http//task-queue-service:8080/taskmanager/get/register";
    try {
      HttpResponse<String> response = httpRequests.GetHttpRequest(url);
      if (response.statusCode() == HttpStatus.OK.value()) {
        String task = response.body();
        //TODO: Debe registrar la tarea en la BDD. Crearla si no existe.
        System.out.println("Realizando tarea de la primera cola: " + task);
      } else {
        System.out.println("No se pudo obtener una tarea de la cola");
        // Manejar el caso en el que no se obtiene una tarea de la cola o se produce un error
      }
    } catch (Exception e) {
      e.printStackTrace();
      System.out.println("No se pudo obtener una tarea de la cola");
    }
  }
  // private void taskStatusUpdate() {
  //   HttpRequests httpRequests = new HttpRequests();
  //   String url = "http//task-queue-service:8080/taskmanager/get/completed";
  //   try {
  //     HttpResponse<String> response = httpRequests.GetHttpRequest(url);
  //     if (response.statusCode() == HttpStatus.OK.value()) {
  //       String task = response.body();
  //       // Debe actualizar el progreso de la tarea en la BDD.
  //       System.out.println("Realizando tarea de la segunda cola: " + task);
  //       // Si se completo el progreso, unificar imagen.
  //       // Obtiene los datos de la BDD y unifica la imagen.
  //       // this.assemble(taskData);
  //     } else {
  //       System.out.println("No se pudo obtener una tarea de la cola");
  //       // Manejar el caso en el que no se obtiene una tarea de la cola o se produce un error
  //     }
  //   } catch (Exception e) {
  //     e.printStackTrace();
  //   }
  // }
  // private void assembleImage(JSONObject taskData) {}
}
