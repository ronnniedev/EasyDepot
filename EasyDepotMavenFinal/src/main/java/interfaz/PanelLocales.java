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
import javax.swing.table.DefaultTableModel;

import componentes.Estilos;
import logica.Sistema;
import modelo.Local;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class PanelLocales extends JPanel {

	private static final long serialVersionUID = 1L;
	private Sistema s;
	private JTextField buscador;

	/**
	 * Panel que muestra el numero de locales alojados en el sistema, listandolos en una tabla con un filtro 
	 * donde pueden buscarse los locales de distinta manera
	 */
	public PanelLocales(JPanel panel, Sistema s) {
		// Establecemos las dimensiones del panel
		setBackground(new Color(255, 255, 255));
		setLayout(null);
		
		this.s = s;
		
		this.setBounds(175, 0, 511, 503);
		panel.add(this);
		
		// Establece el titulo del panel
		JLabel lblLocales = new JLabel("Locales",SwingConstants.CENTER);
		lblLocales.setFont(new Font("Verdana", Font.BOLD, 24));
		lblLocales.setBounds(0, 33, 511, 45);
		this.add(lblLocales);
		
		// Estbablece la cabecera y los datos de la tabla de los locales
		String [] cabecera = {"Id","Coordenadas","Direccion","Reservas"};
		List <String[]> datosLista = extraerLocalesSeleccionados("");
		String [][] datos = datosLista.toArray(new String[0][0]);
		
		// Creamos un modelo de tabla no editable modificando el metodo isCellEditable para que no lo sea mas
		DefaultTableModel modelo = new DefaultTableModel(datos, cabecera) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false; // Hace todas las celdas no editables
			}
		};
				
		// Instanciamos la tabla, en caso de seleccionar una fila se abre un panel mostrando un panel con el local 
		// seleccionado
		JTable tablaLocales = new JTable(modelo);
		Estilos.prepararTabla(tablaLocales,modelo,4);
		tablaLocales.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int row = tablaLocales.getSelectedRow();
				String idLocal = (String) tablaLocales.getValueAt(row, 0);
				// refresca la pantalla y crea un nuevo panel para generar los datos del cliente nuevo
				if(idLocal != null) {
					removeAll();
					repaint();
					revalidate();  
					add(new PanelLocal(panel,s,s.buscarLocal(Integer.parseInt(idLocal))));
				}
			}
		});
		
		JScrollPane scrollPane = new JScrollPane(tablaLocales);
		Estilos.estiloBarra(scrollPane);
		this.add(scrollPane);
		
		buscador = new JTextField();
		buscador.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				List<String[]> datosLista = null;
				modelo.setNumRows(0);
				
				datosLista = extraerLocalesSeleccionados(buscador.getText());
				
				for(String [] fila: datosLista) {
					modelo.addRow(fila);
				}
				
				// Cargamos la tabla entera
				Estilos.cargarTablaCompleta(modelo);
			}
		});
		buscador.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				buscador.setText("");
				List<String[]> datosLista = null;
				modelo.setNumRows(0);
				
				datosLista = extraerLocalesSeleccionados(buscador.getText());
				
				for(String [] fila: datosLista) {
					modelo.addRow(fila);
				}
				
				// Cargamos la tabla entera
				Estilos.cargarTablaCompleta(modelo);
			}
		});
		Estilos.prepararBuscador(buscador);
		buscador.setText("buscar direccion...");
		this.add(buscador);
		buscador.setColumns(10);
	}

	/**
	 * Extrae en un arrayList un vector de String con los datos en crudo de todos los locales alojados en el sistema
	 * @return List <String[]>
	 */
	private List<String[]> extraerLocalesSeleccionados(String direccion) {
		List <String[]> datos = new ArrayList<String[]>();
		
		for(Local l: s.getLocales()) {
			if(l.getDireccion().toLowerCase().startsWith(direccion.toLowerCase())) {
				datos.add(new String[]{l.getLocalId() +"",l.getCoordenadas(), l.getDireccion(), l.getNumeroReservas() + ""});
			}
		}
		
		return datos;
	}
	

}
