package interfaz;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import componentes.Estilos;
import excepciones.LogicaException;
import excepciones.PersistenciaException;
import logica.Sistema;
import modelo.Articulo;
import modelo.Cabina;
import modelo.Local;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class PanelArticulos extends JPanel {

	private static final long serialVersionUID = 1L;
	private Sistema s;

	/**
	 * Panel que muestra todos los articulos dentro del sistema y dentro de una tabla
	 */
	public PanelArticulos(JPanel panel,Local l) {
		// establece las dimensiones de la tabla
		setBackground(new Color(255, 255, 255));
		this.setBounds(0, 0, 511, 503);
		panel.add(this);
		setLayout(null);
		
		try {
			this.s = Sistema.getInstance();
		} catch (Exception e) {
			
		} 
		
		// establece el titulo de lel panel
		JLabel lblCabinasDeLocal = new JLabel("Articulos de local: " + l.getLocalId(), SwingConstants.CENTER);
		lblCabinasDeLocal.setFont(new Font("Verdana", Font.BOLD, 18));
		lblCabinasDeLocal.setBackground(Color.WHITE);
		lblCabinasDeLocal.setBounds(0, 32, 511, 45);
		add(lblCabinasDeLocal);
		
		
		// preparamos la tabla y el modelo
		String [] cabecera = {"Id articulo","Nombre","Stock","Precio"};
		List <String[]> datosLista = extraerArticulos(l.getArticulos());
		String [][] datos = datosLista.toArray(new String[0][0]);
		
		// Creamos un modelo de tabla no editable modificando el metodo isCellEditable para que no lo sea mas
				DefaultTableModel modelo = new DefaultTableModel(datos, cabecera) {
					@Override
					public boolean isCellEditable(int row, int column) {
						return false; // Hace todas las celdas no editables
					}
				};
		
		// Creamos la tabla de articulos
		JTable tablaArticulos = new JTable(modelo);
		Estilos.prepararTabla(tablaArticulos);
		tablaArticulos.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int row = tablaArticulos.getSelectedRow();
				String idArticulo = (String) tablaArticulos.getValueAt(row, 0);
				// refresca la pantalla y crea un nuevo panel para generar los datos del cliente nuevo
				removeAll();
				repaint();
				revalidate();  
				add(new PanelCarruselArticulo(panel,l,s.buscarArticulo(l,idArticulo)));
			}
		});
		
		JScrollPane scrollPane = new JScrollPane(tablaArticulos);
		scrollPane.setBounds(10, 120, 491, 311);
		this.add(scrollPane);
		
		JButton btnVista = new JButton("Modo Vista");
		btnVista.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				removeAll();
				repaint();
				revalidate();  
				add(new PanelCarruselArticulo(panel,l,l.getArticulos().get(0)));
			}
		});
		btnVista.setFont(new Font("Verdana", Font.BOLD, 10));
		btnVista.setBounds(205, 87, 100, 21);
		add(btnVista);

	}

	/**
	 * Extrae los articulos que se encuentran dentro del sistema y lo devuelve en formato de listas de Strings
	 * @param List : articulos
	 * @return List <String[]>
	 */
	private List<String[]> extraerArticulos(List<Articulo> articulos) {
		List <String[]> datos = new ArrayList<String[]>();
		
		for(Articulo a: articulos) {
			datos.add(new String[]{a.getIdArticulo(),a.getNombre(),a.getStock() + "",a.getPrecio() + ""});
		}
		return datos;
	}

}
