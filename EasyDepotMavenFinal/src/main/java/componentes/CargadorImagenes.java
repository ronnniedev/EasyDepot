package componentes;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class CargadorImagenes{

    public static String selectAndLoadImage() {
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
            return file.getPath(); 
        } else {
            System.out.println("No se seleccionó ninguna imagen.");
        }

        return null; 
    }

    public static void main(String[] args) {
        String ruta= selectAndLoadImage();

        if (ruta != null) {
            System.out.println("Imagen cargada correctamente");
        } else {
            System.out.println("No se cargó ninguna imagen.");
        }
    }
}
