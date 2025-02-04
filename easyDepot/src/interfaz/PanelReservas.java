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
import modelo.Reserva;

public class PanelReservas extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField buscador;
	private Sistema s;
	private JTable tablaReservas;
	private DefaultTableModel modelo;

	/**
	 * Panel que muestra todas las reservas en el sistema, tambien tiene un filtro para poder buscarlas a placer
	 */
	public PanelReservas(JPanel panel,String titulo,List<Reserva> reservas) {
		// Establece las dimensiones del panel
		setBackground(new Color(255, 255, 255));
		setLayout(null);
		
		try {
			s = Sistema.getInstance();
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
		List <String[]> datosLista = extraerReservas(reservas);
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
		tablaReservas.setBackground(new Color(0, 255, 255));
		Estilos.prepararTabla(tablaReservas,modelo,4);
		
		
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
		
		buscador = new JTextField();
		buscador.setBounds(383, 69, 118, 19);
		add(buscador);
		buscador.setColumns(10);
		
		JSpinner selectorFiltro = new JSpinner();
		selectorFiltro.setBounds(292, 68, 81, 20);
		add(selectorFiltro);
	}
	

	/**
	 * Extrae en un arrayList un vector de String con los datos en crudo de todas as reservas alojados en el sistema
	 * @return List <String[]>
	 */
	private List<String[]> extraerReservas(List<Reserva> reservas) {
		List <String[]> datos = new ArrayList<String[]>();
		
		for(Reserva r: reservas) {
			datos.add(new String[]{r.getIdReserva() + "",r.getEmailCliente(),r.getIdCabina(),r.getFechaInicio().toString()});
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
