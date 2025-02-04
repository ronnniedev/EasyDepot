package componentes;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class CargadorImagenes{

    public static BufferedImage selectAndLoadImage() {
        FileDialog fileDialog = new FileDialog((Frame) null, "Selecciona una imagen", FileDialog.LOAD);
        fileDialog.setFilenameFilter((dir, name) -> {
            // Formatos aceptados
            return name.endsWith(".jpg") || name.endsWith(".jpeg") || 
                   name.endsWith(".png") || name.endsWith(".gif") || 
                   name.endsWith(".bmp");
        });

        
        fileDialog.setVisible(true);

        
        String selectedFile = fileDialog.getFile();
        if (selectedFile != null) {
            File file = new File(fileDialog.getDirectory(), selectedFile); 
            try {
            	// devolvemos la imagen
                BufferedImage image = ImageIO.read(file);
                return image; 
            } catch (IOException e) {
                System.err.println("Error al cargar la imagen: " + e.getMessage());
            }
        } else {
            System.out.println("No se seleccionó ninguna imagen.");
        }

        return null; 
    }

    public static void main(String[] args) {
        BufferedImage image = selectAndLoadImage();

        if (image != null) {
            System.out.println("Imagen cargada correctamente. Dimensiones: " + image.getWidth() + "x" + image.getHeight());
        } else {
            System.out.println("No se cargó ninguna imagen.");
        }
    }
}
