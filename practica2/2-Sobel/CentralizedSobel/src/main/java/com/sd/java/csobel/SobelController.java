package com.sd.java.csobel;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.UUID;
import javax.imageio.ImageIO;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class SobelController {

  @PostMapping("/filter")
  public ResponseEntity<byte[]> filter(@RequestBody byte[] imageData) {
    try {
      String filename = UUID.randomUUID().toString();

      // Convertir los datos de la imagen en un objeto BufferedImage
      ByteArrayInputStream bis = new ByteArrayInputStream(imageData);
      BufferedImage image = ImageIO.read(bis);

      // Aplicar el filtro Sobel a la imagen
      BufferedImage grayImage = applyGrayScaleFilter(image);
      BufferedImage sobelImage = applySobelFilter(grayImage);

      ByteArrayOutputStream bos = new ByteArrayOutputStream();
      ImageIO.write(sobelImage, "png", bos);
      byte[] filteredImageData = bos.toByteArray();

      // Configurar las cabeceras de la respuesta
      HttpHeaders headers = new HttpHeaders();
      headers.setContentType(MediaType.IMAGE_PNG);
      headers.setContentDispositionFormData(
        "attachment",
        filename + "-sobel.png"
      );

      return ResponseEntity.ok().headers(headers).body(filteredImageData);
    } catch (IOException e) {
      e.printStackTrace();
      return ResponseEntity.badRequest().build();
    }
  }

  private BufferedImage applyGrayScaleFilter(BufferedImage image) {
    int width = image.getWidth();
    int height = image.getHeight();

    BufferedImage grayImage = new BufferedImage(
      width,
      height,
      BufferedImage.TYPE_BYTE_GRAY
    );

    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        Color pixelColor = new Color(image.getRGB(x, y));
        int grayValue = (int) (
          0.2989 *
          pixelColor.getRed() +
          0.5870 *
          pixelColor.getGreen() +
          0.1140 *
          pixelColor.getBlue()
        );
        Color grayColor = new Color(grayValue, grayValue, grayValue);
        grayImage.setRGB(x, y, grayColor.getRGB());
      }
    }

    return grayImage;
  }

  private BufferedImage applySobelFilter(BufferedImage image) {
    long startTime = System.currentTimeMillis(); // Tiempo de inicio
    int width = image.getWidth();
    int height = image.getHeight();

    BufferedImage filteredImage = new BufferedImage(
      width,
      height,
      BufferedImage.TYPE_BYTE_GRAY
    );

    int[][] sobelMatrixX = { { -1, 0, 1 }, { -2, 0, 2 }, { -1, 0, 1 } };
    int[][] sobelMatrixY = { { -1, -2, -1 }, { 0, 0, 0 }, { 1, 2, 1 } };

    for (int y = 1; y < height - 1; y++) {
      for (int x = 1; x < width - 1; x++) {
        int topLeft = getGrayValue(image.getRGB(x - 1, y - 1));
        int top = getGrayValue(image.getRGB(x, y - 1));
        int topRight = getGrayValue(image.getRGB(x + 1, y - 1));
        int left = getGrayValue(image.getRGB(x - 1, y));
        int center = getGrayValue(image.getRGB(x, y));
        int right = getGrayValue(image.getRGB(x + 1, y));
        int bottomLeft = getGrayValue(image.getRGB(x - 1, y + 1));
        int bottom = getGrayValue(image.getRGB(x, y + 1));
        int bottomRight = getGrayValue(image.getRGB(x + 1, y + 1));

        int gradientX = applyMatrix(
          sobelMatrixX,
          topLeft,
          top,
          topRight,
          left,
          center,
          right,
          bottomLeft,
          bottom,
          bottomRight
        );
        int gradientY = applyMatrix(
          sobelMatrixY,
          topLeft,
          top,
          topRight,
          left,
          center,
          right,
          bottomLeft,
          bottom,
          bottomRight
        );

        int gradient = (int) Math.sqrt(
          gradientX * gradientX + gradientY * gradientY
        );
        gradient = Math.min(Math.max(gradient, 0), 255);

        Color filteredColor = new Color(gradient, gradient, gradient);
        filteredImage.setRGB(x, y, filteredColor.getRGB());
      }
    }

    long endTime = System.currentTimeMillis(); // Tiempo de fin
    long duration = endTime - startTime; // Duración del proceso en milisegundos

    System.out.println("Tiempo de procesamiento: " + duration + " ms");

    return filteredImage;
  }

  private int getGrayValue(int rgb) {
    Color color = new Color(rgb);
    return color.getRed();
  }

  private int applyMatrix(int[][] matrix, int... values) {
    int result = 0;
    int index = 0;
    for (int i = 0; i < matrix.length; i++) {
      for (int j = 0; j < matrix[i].length; j++) {
        result += matrix[i][j] * values[index];
        index++;
      }
    }
    return result;
  }
}
