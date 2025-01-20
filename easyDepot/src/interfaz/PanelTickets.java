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

public class PanelTickets extends JPanel {

	private static final long serialVersionUID = 1L;
	private Sistema s;
	private JTextField buscador;

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
		lblTickets.setBounds(0, 29, 511, 45);
		this.add(lblTickets);
		
		String [] cabecera = {"Id Reserva","Id Local","Id Cabina","Email cliente"};
		List<String[]> datosLista = extraerTickets();
		String [][] datos = datosLista.toArray(new String[0][0]);
		
		
		// Creamos un modelo de tabla no editable modificando el metodo isCellEditable
				// para que no lo sea mas
			DefaultTableModel modelo = new DefaultTableModel(datos, cabecera) {
				@Override
				public boolean isCellEditable(int row, int column) {
					return false; // Hace todas las celdas no editables
				}
			};
			
		JTable tablaTickets = new JTable(modelo);
		tablaTickets.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tablaTickets.setBorder(new LineBorder(new Color(0, 0, 0)));
		tablaTickets.setBackground(new Color(255, 255, 255));
		tablaTickets.setBounds(185, 119, 491, 311);
		tablaTickets.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int row = tablaTickets.getSelectedRow();
				String emailCliente = (String) tablaTickets.getValueAt(row, 0);
				// refresca la pantalla y crea un nuevo panel para generar los datos del cliente
				// nuevo
				removeAll();
				repaint();
				revalidate();
				add(new PanelCliente(panel, s, s.getClientes().get(new Email(emailCliente))));
			}
		});
		JScrollPane tabla = new JScrollPane(tablaTickets);
		tabla.setBounds(10, 98, 491, 311);
		this.add(tabla);

		buscador = new JTextField();
		buscador.setBounds(383, 69, 118, 19);
		add(buscador);
		buscador.setColumns(10);

		JSpinner selectorFiltro = new JSpinner();
		selectorFiltro.setBounds(292, 68, 81, 20);
		add(selectorFiltro);

	}
	
	/**
	 * Extrae en un arrayList un vector de String con los datos en crudo de todos
	 * los tickets alojados en el sistema con , se puede seleccionar entre incidencias resueltas y no resueltas.
	 * 
	 * @return List <String[]>
	 */
	private List<String[]> extraerTickets() {
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

}
