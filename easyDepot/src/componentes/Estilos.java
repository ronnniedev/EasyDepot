package componentes;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
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
	
	public static ImageIcon prepararImagenCandado(String ruta) {
		
		ImageIcon icono = new ImageIcon(VentanaPrincipal.class.getResource(ruta));
		Image imagenEscalada = icono.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
		ImageIcon iconoEscalado = new ImageIcon(imagenEscalada);
		
		return iconoEscalado;
	}
	
	public static Icon prepararImagenFlecha(String ruta) {
		ImageIcon icono = new ImageIcon(VentanaPrincipal.class.getResource(ruta));
		Image imagenEscalada = icono.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
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
	
	/**
	 * Formato general para las tablas del proyecto, recogemos la tabal que vamos a modificar, su modelo y finalmente
	 * el ajuste de adaptación de las columnas
	 * @param tabla : JTable
	 * @param modelo : DefaulTableModel
	 * @param ajuste : int
	 */
	public static void prepararTabla(JTable tabla, DefaultTableModel modelo,int ajuste) {
		
		tabla.setBorder(new LineBorder(new Color(0, 0, 0)));
		tabla.setBackground(new Color(255, 255, 255)); 
		tabla.setBounds(185, 119, 491, 311);
		
		// formatear texto al gusto para las celdas
		tabla.setFont(new Font("Verdana",Font.PLAIN,15));
		
		DefaultTableCellRenderer renderer = (DefaultTableCellRenderer) tabla.getDefaultRenderer(Object.class);
	        
		// Centramos el texto dentro de la tabla y modificamos su texto
	    renderer.setHorizontalAlignment(SwingConstants.CENTER);
	    tabla.setRowHeight(25);
		
	    
	    // Preparamos la cabecera
	    JTableHeader header = tabla.getTableHeader();
	    header.setBackground(Colores.getAZUL_CLARO());
        Font headerFont = new Font("Verdana", Font.BOLD, 14); // Cambiar el tamaño de la fuente de la cabecera
        header.setFont(headerFont);
        
        // Preparamos la altura de la cabecera
        header.setPreferredSize(new Dimension(header.getPreferredSize().width, 30));
        // Desactivamos que se puedan intercambiar las columnas
        header.setReorderingAllowed(false);
	    
        int numeroColumnas = tabla.getColumnModel().getColumnCount();
        
        // Desactivamos cada columna para que no sea redimensionable a traves del raton
		for(int i = 0; i < numeroColumnas; i++) {
			TableColumn columna = tabla.getColumnModel().getColumn(i);
			columna.setResizable(false);
		}
		
		adjustColumnWidths(tabla,ajuste);
		cargarTablaCompleta(modelo);
		
	}
	/**
	 * Carga filas hasta la fila 12 , teniendo en cuenta las fila que ya tienen contenido, de tal manera que la
	 * tabla siempre se renderize al completo
	 * @param modelo
	 */
	public static void cargarTablaCompleta(DefaultTableModel modelo) {
		// Mete columnas vacias hasta que hace tope con el fondo para que no quede una tabla asimetrica
		for(int i = modelo.getRowCount(); i < 12;i++) {
			modelo.addRow(new String[]{});
		}
	}
	
	private static void adjustColumnWidths(JTable table,int ajuste) {
		for (int column = 0; column < table.getColumnCount(); column++) {
			int maxWidth = 10;
			for (int row = 0; row < table.getRowCount(); row++) {
				Object value = table.getValueAt(row, column);
				if (value != null) {
					maxWidth = Math.max(maxWidth, value.toString().length());
				}
			}
			table.getColumnModel().getColumn(column).setPreferredWidth(maxWidth * ajuste);
		}
	}
	
	public static void estiloBarra(JScrollPane panelBarra) {
		panelBarra.setBounds(10, 98, 491, 311);
		panelBarra.setVerticalScrollBar(new ScrollBarCustom());
	}

	
	
	
	
	
	
	

}
