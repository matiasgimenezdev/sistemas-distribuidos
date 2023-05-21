import org.springframework.annotation.Service;
import org.springframework.boot.SpringApplication;

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
    taskStatusUpdate();

    // 1 segundo de delay entre tareas.
    try {
      Thread.sleep(1000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  private void taskRegister() {
    ResponseEntity<String> response1 = restTemplate.getForEntity(
      "http://servicio-intermediario/cola1",
      String.class
    );
    if (response1.getStatusCode().is2xxSuccessful()) {
      String task1 = response1.getBody();
      //TODO: Debe registrar la tarea en la BDD
      System.out.println("Realizando tarea de la primera cola: " + task1);
    } else {
      // Manejar el caso en el que no se obtiene una tarea de la primera cola o se produce un error
      System.out.println("No se pudo obtener una tarea de la primera cola");
    }
  }

  private void taskStatusUpdate() {
    ResponseEntity<String> response2 = restTemplate.getForEntity(
      "http://servicio-intermediario/cola2",
      String.class
    );
    if (response2.getStatusCode().is2xxSuccessful()) {
      String task2 = response2.getBody();
      //TODO: Debe actualizar el progreso de la tarea en la BDD.
      System.out.println("Realizando tarea de la segunda cola: " + task2);
      //TODO: Si se completo el progreso, unificar imagen.

    } else {
      // Manejar el caso en el que no se obtiene una tarea de la segunda cola o se produce un error
      System.out.println("No se pudo obtener una tarea de la segunda cola");
    }
  }
}
