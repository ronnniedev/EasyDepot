package interfaz;

import java.awt.Color;
import java.awt.Font;
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
import modelo.Reserva;

public class PanelReservas extends JPanel {

	private static final long serialVersionUID = 1L;
	private Sistema s;
	private JTextField buscador;

	/**
	 * Panel que muestra todas las reservas en el sistema, tambien tiene un filtro para poder buscarlas a placer
	 */
	public PanelReservas(JPanel panel, Sistema s) {
		setBackground(new Color(255, 255, 255));
		setLayout(null);
		this.s = s;

		
		
		this.setBounds(175, 0, 511, 503);
		panel.add(this);
		
		JLabel lblRendimiento = new JLabel("Locales",SwingConstants.CENTER);
		lblRendimiento.setFont(new Font("Verdana", Font.BOLD, 24));
		lblRendimiento.setBounds(175, 29, 511, 45);
		
		JLabel lblReservas = new JLabel("Reservas",SwingConstants.CENTER);
		lblReservas.setFont(new Font("Verdana", Font.BOLD, 24));
		lblReservas.setBounds(0, 33, 511, 45);
		this.add(lblReservas);
		
		String [] cabecera = {"Id","Cliente email","Cabina","Fecha inicio","Fecha Salida","Incidencia"};
		List <String[]> datosLista = extraerReservas();
		String [][] datos = datosLista.toArray(new String[0][0]);
		
		// Creamos un modelo de tabla no editable modificando el metodo isCellEditable para que no lo sea mas
		DefaultTableModel modelo = new DefaultTableModel(datos, cabecera) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false; // Hace todas las celdas no editables
			}
		};
		
		JTable tablaReservas = new JTable(modelo);
		tablaReservas.setBorder(new LineBorder(new Color(0, 0, 0)));
		tablaReservas.setBackground(new Color(255, 255, 255)); 
		tablaReservas.setBounds(185, 119, 491, 311);
		
		JScrollPane scrollPane = new JScrollPane(tablaReservas);
		scrollPane.setBounds(10, 98, 491, 311);
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
	private List<String[]> extraerReservas() {
		List <String[]> datos = new ArrayList<String[]>();
		
		for(Reserva r: s.getReservas()) {
			datos.add(new String[]{r.getIdReserva() + "",r.getEmailCliente(),r.getIdCabina(),r.getFechaInicio().toString()
					,comprobarFechaSalida(r),r.isIncidencia() + ""});
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
