package com.sd.java.sobel;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import javax.imageio.ImageIO;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@SpringBootApplication
public class SplitController {

  @PostMapping(value = "/split", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public void split(
    @RequestParam("file") MultipartFile file,
    @RequestParam("parts") String parts
  ) {
    try {
      byte[] image = file.getBytes();
      BufferedImage originalImage = ImageIO.read(
        new ByteArrayInputStream(image)
      );
      Integer splitParts = Integer.parseInt(parts);
      Integer width = originalImage.getWidth();
      Integer height = originalImage.getHeight();
      Integer divisionWidth = width / splitParts;

      for (int i = 0; i < splitParts; i++) {
        int x = i * divisionWidth;
        int y = 0;
        int divisionHeight = height;

        if (i == splitParts - 1) {
          // Si es la última división
          // Ajustar el ancho si la división no es exacta
          divisionWidth = width - (divisionWidth * i);
        }

        BufferedImage division = originalImage.getSubimage(
          x,
          y,
          divisionWidth,
          divisionHeight
        );

        // Guardar cada división en un archivo separado
        Path currentPath = Paths.get("").toAbsolutePath();
        String divisionPath =
          currentPath + "/image-parts/division" + i + ".jpg";
        ImageIO.write(division, "jpg", new File(divisionPath));

        System.out.println("División " + i + " guardada en: " + divisionPath);
      }

      System.out.println("Imagen dividida en " + parts + " partes.");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
