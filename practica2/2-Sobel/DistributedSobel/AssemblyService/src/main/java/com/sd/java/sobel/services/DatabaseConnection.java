package com.sd.java.sobel.services;

import java.sql.*;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
public class DatabaseConnection {

  private String url;

  private String username;

  private String password;

  private String dbname;

  private Boolean tableExists;

  public DatabaseConnection(Environment environment) {
    this.url = environment.getProperty("postgres.url", "URL not found");
    this.username =
      environment.getProperty("postgres.username", "USERNAME not found");
    this.password =
      environment.getProperty("postgres.password", "PASSWORD not found");
    this.dbname =
      environment.getProperty("postgres.dbname", "DBNAME not found");
    this.tableExists = false;
    try (
      Connection connection = DriverManager.getConnection(
        this.url + "postgres",
        this.username,
        this.password
      )
    ) {
      this.url = url + dbname;
      Statement statement = connection.createStatement();

      // Verificar si la base de datos existe
      ResultSet resultSet = statement.executeQuery(
        "SELECT 1 FROM pg_database WHERE datname='" + this.dbname + "'"
      );
      if (!resultSet.next()) {
        // Crea la BDD 'task-db' si no existe
        statement.executeUpdate("CREATE DATABASE " + this.dbname);
        System.out.println("Se creó la base de datos '" + dbname + "'");
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public Connection getConnection() {
    try {
      return DriverManager.getConnection(url, username, password);
    } catch (SQLException e) {
      e.printStackTrace();
      return null;
    }
  }

  public void createTableIfNotExists() {
    try (
      Connection connection = DriverManager.getConnection(
        url,
        username,
        password
      )
    ) {
      Statement statement = connection.createStatement();

      // TODO -> DEBUGUEAR ESTA FUNCION PORQUE ESTA LANZANDO EXCEPCION CUANDO LA TABLA EXISTE.
      ResultSet resultSet = statement.executeQuery(
        "SELECT EXISTS (SELECT 1 FROM information_schema.tables WHERE table_name = 'TASKS')"
      );
      resultSet.next();
      boolean tableExists = resultSet.getBoolean(1);

      if (!tableExists) {
        // Crea la tabla 'TASKS' si no existe
        statement.executeUpdate(
          "CREATE TABLE TASKS (TASK_ID char(60) PRIMARY KEY, PARTS VARCHAR(50), WIDTH VARCHAR(10), HEIGHT VARCHAR(10), SOURCE VARCHAR(255), DESTINATION VARCHAR(255))"
        );
        System.out.println("Se creó la tabla 'TASKS'");
      } else {
        System.out.println("La tabla 'TASKS' ya existe");
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}
