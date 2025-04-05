package componentes;

import java.awt.BasicStroke;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JPanel;

public class PanelDatosRedondeado extends JPanel{
	private int cornerRadius;

    public PanelDatosRedondeado(int radio) {
        this.cornerRadius = radio;
        setOpaque(false); // Hace que el panel sea transparente
    }
   
   @Override
   protected void paintComponent(Graphics g) {
	   super.paintComponent(g);
       Dimension arcs = new Dimension(cornerRadius, cornerRadius);
       int width = getWidth();
       int height = getHeight();
       Graphics2D graphics = (Graphics2D) g;
       graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

       // Dibuja el panel redondeado
       graphics.setColor(getBackground());
       graphics.fillRoundRect(0, 0, width-1, height-1, arcs.width, arcs.height);
       graphics.setColor(Colores.getGRIS());
       graphics.setStroke(new BasicStroke(2));
       graphics.drawRoundRect(0, 0, width-1, height-1, arcs.width, arcs.height);
   }

}
