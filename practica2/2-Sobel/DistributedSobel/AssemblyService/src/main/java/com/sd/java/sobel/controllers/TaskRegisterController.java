package com.sd.java.sobel.controllers;

import com.sd.java.sobel.services.DatabaseConnection;
import java.sql.*;
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

      // Verifica si ya existe la tarea.
      String query = "SELECT * FROM TASKS WHERE TASK_ID = ?";
      PreparedStatement preparedStatement = connection.prepareStatement(query);
      preparedStatement.setString(1, taskData.getString("taskId"));
      ResultSet resultSet = preparedStatement.executeQuery();
      if (!resultSet.next()) {
        query =
          "INSERT INTO TASKS (TASK_ID, PARTS, WIDTH, HEIGHT, SOURCE, DESTINATION) VALUES (?, ?, ?, ?, ?, ?)";

        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, taskData.getString("taskId"));
        preparedStatement.setString(2, taskData.getString("parts"));
        preparedStatement.setString(3, taskData.getString("width"));
        preparedStatement.setString(4, taskData.getString("height"));
        preparedStatement.setString(5, taskData.getString("source"));
        preparedStatement.setString(6, taskData.getString("destination"));

        int rowsAffected = preparedStatement.executeUpdate();
        if (rowsAffected > 0) {
          System.out.println("Tarea registrada en la base de datos.");
          System.out.println("TASK_ID: " + taskData.getString("taskId"));
          System.out.println("PARTS: " + taskData.getString("parts"));
          System.out.println("WIDTH: " + taskData.getString("width"));
          System.out.println("HEIGHT: " + taskData.getString("height"));
          System.out.println("SOURCE: " + taskData.getString("source"));
          System.out.println(
            "DESTINATION: " + taskData.getString("destination")
          );
        } else {
          System.out.println("Error al registrar la tarea en la base de datos");
        }
      } else {
        System.out.println("Ya existe un registro con esa clave primaria");
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
