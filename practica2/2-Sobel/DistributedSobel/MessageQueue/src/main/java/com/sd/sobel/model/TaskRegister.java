package com.sd.sobel.model;

import java.io.Serializable;

public class TaskRegister implements Serializable, Task {

  private String taskId;
  private String parts;
  private String width;
  private String height;
  private String source;
  private String destination;

  public TaskRegister(
    String taskId,
    String parts,
    String width,
    String height,
    String source,
    String destination
  ) {
    this.taskId = taskId;
    this.parts = parts;
    this.width = width;
    this.height = height;
    this.source = source;
    this.destination = destination;
  }

  @Override
  public String getTaskId() {
    return this.taskId;
  }

  @Override
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

  public String getSource() {
    return this.source;
  }

  public void setSource(String source) {
    this.source = source;
  }

  public String getDestination() {
    return this.destination;
  }

  public void setDestination(String destination) {
    this.destination = destination;
  }
}
