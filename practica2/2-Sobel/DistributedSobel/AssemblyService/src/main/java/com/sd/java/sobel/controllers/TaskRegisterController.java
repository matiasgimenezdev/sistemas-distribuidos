package com.sd.java.sobel.controllers;

import com.sd.java.sobel.services.DatabaseConnection;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TaskRegisterController {

  private DatabaseConnection databaseConnection;

  @Autowired
  public TaskRegisterController(DatabaseConnection databaseConnection) {
    this.databaseConnection = databaseConnection;
    databaseConnection.createTableIfNotExists();
  }

  @PostMapping("/register")
  public ResponseEntity<String> taskRegister(@RequestBody String body) {
    JSONObject taskData = new JSONObject(body);
    Connection connection = null;
    try {
      connection = this.databaseConnection.getConnection();
      Statement statement = connection.createStatement();
      String query =
        "INSERT INTO TASKS (TASK_ID, PARTS, WIDTH, HEIGHT, SOURCE, DESTINATION) values (" +
        taskData.getString("taskId") +
        ", " +
        taskData.getString("parts") +
        ", " +
        taskData.getString("width") +
        ", " +
        taskData.getString("height") +
        ", " +
        taskData.getString("source") +
        ", " +
        taskData.getString("destination") +
        ")";
      int rowsAffected = statement.executeUpdate(query);
      if (rowsAffected > 0) {
        System.out.println("Tarea registrada en la base de datos.");
      } else {
        System.out.println("Error al registrar la tarea en la base de datos");
      }
      return new ResponseEntity<>(HttpStatus.OK);
    } catch (Exception e) {
      e.printStackTrace();
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    } finally {
      // Asegurarse de cerrar la conexión en caso de excepción
      if (connection != null) {
        try {
          connection.close();
        } catch (SQLException e) {
          e.printStackTrace();
        }
      }
    }
  }
}
