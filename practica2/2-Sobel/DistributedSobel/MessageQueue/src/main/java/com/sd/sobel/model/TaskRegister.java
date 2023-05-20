package com.sd.sobel.model;

import java.io.Serializable;

public class TaskRegister implements Serializable, Task {

  private String taskId;
  private String parts;
  private String width;
  private String height;

  public TaskRegister(
    String taskId,
    String parts,
    String width,
    String height
  ) {
    this.taskId = taskId;
    this.parts = parts;
    this.width = width;
    this.height = height;
  }

  public String getTaskId() {
    return this.taskId;
  }

  public void setTaskId(String taskId) {
    this.taskId = taskId;
  }

  public String getParts() {
    return this.parts;
  }

  public void setParts(String parts) {
    this.parts = parts;
  }

  public String getWidth() {
    return this.width;
  }

  public void setWidth(String width) {
    this.width = width;
  }

  public String getHeight() {
    return this.height;
  }

  public void setHeight(String height) {
    this.height = height;
  }
}
