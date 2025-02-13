package interfaz;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
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
import modelo.Reserva;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class PanelReservas extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField buscador;
	private Sistema s;
	private JTable tablaReservas;
	private DefaultTableModel modelo;
	private List <Reserva> reservas;
	private List <Reserva> reservasSeleccionadas;

	/**
	 * Panel que muestra todas las reservas en el sistema, tambien tiene un filtro para poder buscarlas a placer
	 */
	public PanelReservas(JPanel panel,String titulo,List<Reserva> reservas) {
		// Establece las dimensiones del panel
		setBackground(new Color(255, 255, 255));
		setLayout(null);
		
		try {
			s = Sistema.getInstance();
			this.reservas = reservas;
		} catch (Exception e) {
			
		}
		this.setBounds(175, 0, 511, 503);
		panel.add(this);
		
		//Muestra el titulo de la tabla
		JLabel lblReservas = new JLabel(titulo,SwingConstants.CENTER);
		lblReservas.setFont(new Font("Verdana", Font.BOLD, 24));
		lblReservas.setBounds(0, 33, 511, 45);
		this.add(lblReservas);
		
		// Establecemos la cabecera y lso datos
		String [] cabecera = {"Id","Cliente email","Cab","Fecha inicio"};
		List <String[]> datosLista = extraerReservas();
		String [][] datos = datosLista.toArray(new String[0][0]);
		
		// Creamos un modelo de tabla no editable modificando el metodo isCellEditable para que no lo sea mas
		modelo = new DefaultTableModel(datos, cabecera) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false; // Hace todas las celdas no editables
			}
		};
		
		// Creamos la tabla, en caso de seleccionar una fila abrirmo un panel con la informacion de la reserva 
		// pertinente
		tablaReservas = new JTable(modelo);
		Estilos.prepararTabla(tablaReservas,modelo,10);
		tablaReservas.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int row = tablaReservas.getSelectedRow();
				String idReserva = (String) tablaReservas.getValueAt(row, 0);
				if(idReserva != null) {
				// refresca la pantalla y crea un nuevo panel para generar los datos del cliente nuevo
					removeAll();
					repaint();
					revalidate();  
					add(new PanelReserva(panel,s,s.buscarReserva(Integer.parseInt(idReserva))));
				}
			}
		});
		JScrollPane scrollPane = new JScrollPane(tablaReservas);
		Estilos.estiloBarra(scrollPane);
		this.add(scrollPane);
		
		
		JComboBox<String> cBSelector = new JComboBox<String>();
		cBSelector.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				String seleccionado = e.getItem().toString();
				cargarTabla(seleccionado);
			}

			
		});
		cBSelector.setModel(new DefaultComboBoxModel<String>(new String[] {"Todas","Abiertas", "Cerradas"}));
		cBSelector.setFont(new Font("Verdana", Font.PLAIN, 12));
		cBSelector.setBounds(277, 67, 96, 21);
		add(cBSelector);
		
		buscador = new JTextField();
		buscador.setText("buscar por cliente...");
		buscador.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				buscador.setText("");
				cargarTabla(buscador.getText());
			}
			
		});
		buscador.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				cargarTabla(buscador.getText());
			}
		});
		Estilos.prepararBuscador(buscador);
		add(buscador);
		buscador.setColumns(10);
	}
	/**
	 * Carga la tabla determinada dependiendo de la seleccion del comboBox, estando esta entre las opciones de
	 * "Reservas abiertas" ,"Cerradas" y "Todas"
	 * @param seleccionado : String
	 */
	private void cargarTabla(String seleccionado) {
		List<String[]> datosLista = null;
		modelo.setNumRows(0);
		
		if(seleccionado.compareTo("Abiertas") == 0) {
			datosLista = extraerReservasAbiertas();
		}else if(seleccionado.compareTo("Cerradas") == 0){
			datosLista = extraerReservasCerradas();
		}else if(seleccionado.compareTo("Todas") == 0){
			datosLista = extraerReservas();
		}else {
			datosLista = extraerReservasBuscadas(seleccionado);
		}
		
		for(String [] fila: datosLista) {
			modelo.addRow(fila);
		}
		
		// Cargamos la tabla entera
		Estilos.cargarTablaCompleta(modelo);
	}
	
	private List<String[]> extraerReservasBuscadas(String texto) {
		List <String[]> datos = new ArrayList<String[]>();
		
		for(Reserva r: reservasSeleccionadas) {
			if(r.getEmailCliente().toLowerCase().startsWith(texto.toLowerCase())) {
				datos.add(new String[]{r.getIdReserva() 
						+ "",r.getEmailCliente(),r.getIdCabina(),r.getFechaInicio().toString()});
			}
		}
		
		return datos;
	}

	private List<String[]> extraerReservasCerradas() {
		List <String[]> datos = new ArrayList<String[]>();
		this.reservasSeleccionadas = new ArrayList<Reserva>();
		
		for(Reserva r: reservas) {
			if(r.getFechaSalida() != null) {
				datos.add(new String[]{r.getIdReserva() 
						+ "",r.getEmailCliente(),r.getIdCabina(),r.getFechaInicio().toString()});
				reservasSeleccionadas.add(r);
			}
		}
		
		return datos;
	}

	private List<String[]> extraerReservasAbiertas() {
		List <String[]> datos = new ArrayList<String[]>();
		this.reservasSeleccionadas = new ArrayList<Reserva>();
		
		for(Reserva r: reservas) {
			if(r.getFechaSalida() == null) {
				datos.add(new String[]{r.getIdReserva() 
						+ "",r.getEmailCliente(),r.getIdCabina(),r.getFechaInicio().toString()});
				reservasSeleccionadas.add(r);
			}
		}
		
		return datos;
	}

	/**
	 * Extrae en un arrayList un vector de String con los datos en crudo de todas as reservas alojados en el sistema
	 * @return List <String[]>
	 */
	private List<String[]> extraerReservas() {
		List <String[]> datos = new ArrayList<String[]>();
		this.reservasSeleccionadas = new ArrayList<Reserva>();
		
		for(Reserva r: reservas) {
			datos.add(new String[]{r.getIdReserva() + "",r.getEmailCliente(),r.getIdCabina(),r.getFechaInicio().toString()});
			reservasSeleccionadas.add(r);
		}
		
		return datos;
	}
	
	/**
	 * Comprueba la fecha de salida para que devuelva un texto especidifo si la reserva no la tiene
	 * @param r : Reserva
	 * @return String
	 */
	private String comprobarFechaSalida(Reserva r) {
		if(r.getFechaSalida() == null) {
			return "Sin fecha";
		}
		
		return r.getFechaSalida().toString();
	}

}
