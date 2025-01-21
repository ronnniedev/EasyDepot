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
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import logica.Sistema;
import modelo.Cliente;
import modelo.Email;
import modelo.Reserva;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

public class PanelTickets extends JPanel {

	private static final long serialVersionUID = 1L;
	private Sistema s;
	private JTextField buscador;
	private JTable tablaTickets;
	private DefaultTableModel modelo;
	
	/**
	 * Create the panel.
	 */
	public PanelTickets(JPanel panel) {
		setBackground(new Color(255, 255, 255));
		setLayout(null);
		
		try {
			s = Sistema.getInstance();
		} catch (Exception e) {
			
		}
		this.setBounds(175, 0, 511, 503);
		panel.add(this);
		
		JLabel lblTickets = new JLabel("Tickets", SwingConstants.CENTER);
		lblTickets.setFont(new Font("Verdana", Font.BOLD, 24));
		lblTickets.setBounds(0, 14, 511, 45);
		this.add(lblTickets);
		
		String [] cabecera = {"Id Reserva","Id Local","Id Cabina","Email cliente"};
		List<String[]> datosLista = extraerTicketsAbiertos();
		String [][] datos = datosLista.toArray(new String[0][0]);
		
		
		// Creamos un modelo de tabla no editable modificando el metodo isCellEditable
				// para que no lo sea mas
			modelo = new DefaultTableModel(datos, cabecera) {
				@Override
				public boolean isCellEditable(int row, int column) {
					return false; // Hace todas las celdas no editables
				}
			};
			
		tablaTickets = new JTable(modelo);
		tablaTickets.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tablaTickets.setBorder(new LineBorder(new Color(0, 0, 0)));
		tablaTickets.setBackground(new Color(255, 255, 255));
		tablaTickets.setBounds(185, 119, 491, 311);
		tablaTickets.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int row = tablaTickets.getSelectedRow();
				String reservaId = (String) tablaTickets.getValueAt(row, 0);
				// refresca la pantalla y crea un nuevo panel para generar los datos del cliente
				// nuevo
				removeAll();
				repaint();
				revalidate();
				add(new PanelTicket(panel,s.buscarReserva(Integer.parseInt(reservaId))));
			}
		});
		JScrollPane tabla = new JScrollPane(tablaTickets);
		tabla.setBounds(10, 98, 491, 311);
		this.add(tabla);

		buscador = new JTextField();
		buscador.setBounds(383, 69, 118, 19);
		add(buscador);
		buscador.setColumns(10);
		
		JComboBox<String> comboBox = new JComboBox();
		comboBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				String seleccionado = e.getItem().toString();
				cargarTabla(seleccionado);
			}

			
		});
		comboBox.setModel(new DefaultComboBoxModel<String>(new String[] {"Abiertas", "Cerradas"}));
		comboBox.setFont(new Font("Verdana", Font.PLAIN, 12));
		comboBox.setBounds(277, 67, 96, 21);
		add(comboBox);

	}
	
	/**
	 * Extrae en un arrayList un vector de String con los datos en crudo de todos
	 * los tickets alojados en el sistema con , se puede seleccionar entre incidencias resueltas y no resueltas.
	 * 
	 * @return List <String[]>
	 */
	private List<String[]> extraerTicketsAbiertos() {
		List<String[]> datos = new ArrayList<String[]>();

		for(Reserva r: s.getReservas()) {
				if(r.isIncidencia()) {
					String idCabina = r.getIdCabina();
					datos.add(new String[] {r.getIdReserva() + "",idCabina.charAt(0) + "", r.getIdCabina()
											, r.getEmailCliente(),});
				}
			}
		
		return datos;
	}
	
	/**
	 * Extrae los tickets cerrados y con informes escritos
	 * @return List <String>
	 */
	private List<String[]> extraerTicketsCerrados() {
		List<String[]> datos = new ArrayList<String[]>();

		for(Reserva r: s.getReservas()) {
				if(!r.isIncidencia() && r.getDescripcionIncidencia().compareTo("Sin Incidencias") != 0) {
					String idCabina = r.getIdCabina();
					datos.add(new String[] {r.getIdReserva() + "",idCabina.charAt(0) + "", r.getIdCabina()
											, r.getEmailCliente(),});
				}
			}
		
		return datos;
	}
	/**
	 * Carga la tabla determinada dependiendo de la seleccion del comboBox, estando esta entre las opciones de
	 * "Abiertas" y "Cerradas"
	 * @param seleccionado : String
	 */
	private void cargarTabla(String seleccionado) {
		List<String[]> datosLista = null;
		modelo.setNumRows(0);
		
		if(seleccionado.compareTo("Abiertas") == 0) {
			datosLista = extraerTicketsAbiertos();
		}else {
			datosLista = extraerTicketsCerrados();
		}
		
		
		for(String [] fila: datosLista) {
			modelo.addRow(fila);
		}
	}

	
}
