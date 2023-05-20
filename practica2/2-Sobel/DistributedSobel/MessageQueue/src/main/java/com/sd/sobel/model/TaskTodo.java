package com.sd.sobel.model;

import java.io.Serializable;

public class TaskTodo implements Serializable, Task {

  private String taskId;
  private String imageSource;
  private String imageDestination;

  public TaskTodo(String taskId, String imageSource, String imageDestination) {
    this.taskId = taskId;
    this.imageSource = imageSource;
    this.imageDestination = imageDestination;
  }

  @Override
  public String getTaskId() {
    return this.taskId;
  }

  @Override
  public void setTaskId(String taskId) {
    this.taskId = taskId;
  }

  public String getImageSource() {
    return this.imageSource;
  }

  public void setImageSource(String imageSource) {
    this.imageSource = imageSource;
  }

  public String getImageDestination(String imageDestination) {
    return this.imageDestination;
  }

  public void setImageDestination(String imageDestination) {
    this.imageDestination = imageDestination;
  }
}
