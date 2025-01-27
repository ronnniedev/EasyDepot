package componentes;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;

import interfaz.VentanaPrincipal;

public class Estilos {
	
	public static ImageIcon prepararImagenBotonera(String ruta) {
		
		ImageIcon icono = new ImageIcon(VentanaPrincipal.class.getResource(ruta));
		Image imagenEscalada = icono.getImage().getScaledInstance(27, 27, Image.SCALE_SMOOTH);
		ImageIcon iconoEscalado = new ImageIcon(imagenEscalada);
		
		return iconoEscalado;
	}
	
	public static JButton prepararBotonBotonera(JButton boton,String icono) {
		
		// Creamos el borde 
		Border borde = BorderFactory.createMatteBorder(0, 0, 1, 0, Colores.getNEGRO());
		
		boton.setIcon(Estilos.prepararImagenBotonera(icono));
		boton.setHorizontalAlignment(SwingConstants.LEFT);
		boton.setBorder(borde);
		// creamos un borde compuesto para reflejarlo en los iconos
		boton.setBorder(BorderFactory.createCompoundBorder(
			    boton.getBorder(), 
			    BorderFactory.createEmptyBorder(0, 20, 0, 0) 
			));
		boton.setBackground(new Color(173, 219, 245));
		boton.setFont(new Font("Verdana", Font.BOLD, 16));
		
		boton.isBorderPainted();
		
		
		return boton;
	}
	
	public static void prepararTabla(JTable tabla) {
		
		tabla.setBorder(new LineBorder(new Color(0, 0, 0)));
		tabla.setBackground(new Color(255, 255, 255)); 
		tabla.setBounds(185, 119, 491, 311);
		
		int numeroColumnas = tabla.getColumnModel().getColumnCount();
		DefaultTableCellRenderer renderer = (DefaultTableCellRenderer) tabla.getDefaultRenderer(Object.class);
	        
		// Centramos el texto dentro de la tabla y modificamos su texto
	    renderer.setHorizontalAlignment(SwingConstants.CENTER);
	    renderer.setFont(new Font("Verdana",Font.PLAIN,18));
	    
	    tabla.setRowHeight(25);
		
	    
	    // Preparamos la cabecera
	    JTableHeader header = tabla.getTableHeader();
        Font headerFont = new Font("Arial", Font.BOLD, 14); // Cambiar el tamaño de la fuente de la cabecera
        header.setFont(headerFont);
        
        // Preparamos la altura de la cabecera
        header.setPreferredSize(new Dimension(header.getPreferredSize().width, 30));
        // Desactivamos que se puedan intercambiar las columnas
        header.setReorderingAllowed(false);
	    
        
        // Desactivamos cada columna para que no sea redimensionable a traves del raton
		for(int i = 0; i < numeroColumnas; i++) {
			TableColumn columna = tabla.getColumnModel().getColumn(i);
			
			columna.setResizable(false);
		}
		
		adjustColumnWidths(tabla);
	}
	
	private static void adjustColumnWidths(JTable table) {
		for (int column = 0; column < table.getColumnCount(); column++) {
			int maxWidth = 0;
			for (int row = 0; row < table.getRowCount(); row++) {
				Object value = table.getValueAt(row, column);
				if (value != null) {
					maxWidth = Math.max(maxWidth, value.toString().length());
				}
			}
			table.getColumnModel().getColumn(column).setPreferredWidth(maxWidth * 4);
		}
	}
	
	

}
