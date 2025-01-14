package interfaz;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import logica.Sistema;
import modelo.Local;

public class PanelLocales extends JPanel {

	private static final long serialVersionUID = 1L;
	private Sistema s;
	private JTextField buscador;

	/**
	 * Panel que muestra el numero de locales alojados en el sistema, listandolos en una tabla con un filtro 
	 * donde pueden buscarse los locales de distinta manera
	 */
	public PanelLocales(JPanel panel, Sistema s) {
		setBackground(new Color(255, 255, 255));
		setLayout(null);
		this.s = s;
		
		
		this.setBounds(175, 0, 511, 503);
		panel.add(this);
		
		JLabel lblLocales = new JLabel("Locales",SwingConstants.CENTER);
		lblLocales.setFont(new Font("Verdana", Font.BOLD, 24));
		lblLocales.setBounds(0, 33, 511, 45);
		this.add(lblLocales);
		
		String [] cabecera = {"Id","Coordenadas","Direccion","Reservas realizadas"};
		List <String[]> datosLista = extraerLocales();
		String [][] datos = datosLista.toArray(new String[0][0]);
		
		// Creamos un modelo de tabla no editable modificando el metodo isCellEditable para que no lo sea mas
		DefaultTableModel modelo = new DefaultTableModel(datos, cabecera) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false; // Hace todas las celdas no editables
			}
		};
				
		JTable tablaLocales = new JTable(modelo);
		tablaLocales.setBorder(new LineBorder(new Color(0, 0, 0)));
		tablaLocales.setBackground(new Color(255, 255, 255)); 
		tablaLocales.setBounds(185, 119, 491, 311);
		tablaLocales.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int row = tablaLocales.getSelectedRow();
				String idLocal = (String) tablaLocales.getValueAt(row, 0);
				// refresca la pantalla y crea un nuevo panel para generar los datos del cliente nuevo
				removeAll();
				repaint();
				revalidate();  
				add(new PanelLocal(panel,s,s.buscarLocal(Integer.parseInt(idLocal))));
			}
		});
		
		JScrollPane scrollPane = new JScrollPane(tablaLocales);
		scrollPane.setBounds(10, 98, 491, 311);
		this.add(scrollPane);
		
		buscador = new JTextField();
		buscador.setBounds(383, 69, 118, 19);
		this.add(buscador);
		buscador.setColumns(10);
		
		JSpinner selectorFiltro = new JSpinner();
		selectorFiltro.setBounds(292, 68, 81, 20);
		add(selectorFiltro);
	}

	/**
	 * Extrae en un arrayList un vector de String con los datos en crudo de todos los locales alojados en el sistema
	 * @return List <String[]>
	 */
	private List<String[]> extraerLocales() {
		List <String[]> datos = new ArrayList<String[]>();
		
		for(Local l: s.getLocales()) {
			datos.add(new String[]{l.getLocalId() +"",l.getCoordenadas(), l.getDireccion(), l.getNumeroReservas() + ""});
		}
		
		return datos;
	}

}
