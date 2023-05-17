package com.sd.java.csobel;

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
    //TODO: Implementar aplicacion filtro escala de grises
    return image;
  }

  private BufferedImage applySobelFilter(BufferedImage image) {
    //TODO: Implementar aplicacion filtro sobel
    return image;
  }
}
