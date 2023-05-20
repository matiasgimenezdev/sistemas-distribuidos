package com.sd.sobel.services;

import com.sd.sobel.model.*;
import java.util.Map;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@UtilityClass
public class MessageLogger {

  public static void logReceivedMessage(String queue, Task message) {
    log.info("Listener on queue [{}] received message [{}]", queue, message);
  }

  public static void logSendMessage(
    String exchange,
    String routingKey,
    Task message
  ) {
    log.info(
      "Message [{}] send to exchange [{}] with routing key [{}]",
      message.getTaskId(),
      exchange,
      routingKey
    );
  }

  public static void logSendMessage(
    String exchange,
    String routingKey,
    Task message,
    Map<String, Object> headers
  ) {
    log.info(
      "Message [{}] send to exchange [{}] with routing key [{}] and headers [{}]",
      message.getTaskId(),
      exchange,
      routingKey,
      headers
    );
  }
}
