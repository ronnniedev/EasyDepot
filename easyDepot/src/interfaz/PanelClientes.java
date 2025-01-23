package interfaz;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import logica.Sistema;
import modelo.Cliente;
import modelo.Email;
import modelo.Local;
import javax.swing.JSpinner;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ListSelectionModel;

public class PanelClientes extends JPanel {

	private static final long serialVersionUID = 1L;
	private Sistema s;
	private JTextField buscador;

	/**
	 * Muestra los clientes guardados dentro del sistema en formato de tabla, en caso de seleccionar una fila abre un
	 * panel con la informacion del cliente. Recogemos el origen desde el que viene para determinar para determinar
	 * las dimensiones del panel dependiendo de ello
	 */
	public PanelClientes(JPanel panel, Sistema s,String origen) {
		// Instanciamos el sistema
		this.s = s;
		// Establece las dimensiones del panel
		comprobarOrigen(origen);
		setBackground(new Color(255, 255, 255));
		setLayout(null);
		panel.add(this);
		setLayout(null);

		// Muestra el titulo d ela taba
		JLabel lblClientes = new JLabel("Clientes", SwingConstants.CENTER);
		lblClientes.setFont(new Font("Verdana", Font.BOLD, 24));
		lblClientes.setBounds(0, 29, 511, 45);
		this.add(lblClientes);

		String[] cabecera = { "Email", "Nombre", "Apellidos", "Puntos Tienda", "Reservas realizadas" };
		List<String[]> datosLista = extraerClientes();
		String[][] datos = datosLista.toArray(new String[0][0]);

		// Creamos un modelo de tabla no editable modificando el metodo isCellEditable
		// para que no lo sea mas
		DefaultTableModel modelo = new DefaultTableModel(datos, cabecera) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false; // Hace todas las celdas no editables
			}
		};

		// Establece un
		JTable tablaClientes = new JTable(modelo);
		tablaClientes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tablaClientes.setBorder(new LineBorder(new Color(0, 0, 0)));
		tablaClientes.setBackground(new Color(255, 255, 255));
		tablaClientes.setBounds(185, 119, 491, 311);
		tablaClientes.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int row = tablaClientes.getSelectedRow();
				String emailCliente = (String) tablaClientes.getValueAt(row, 0);
				// refresca la pantalla y crea un nuevo panel para generar los datos del cliente
				// nuevo
				removeAll();
				repaint();
				revalidate();
				add(new PanelCliente(panel, s, s.getClientes().get(new Email(emailCliente))));
			}
		});
		JScrollPane tabla = new JScrollPane(tablaClientes);
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
	 * Comrpueba si el usuario viene desde la ventana principal o una auxiliar en funcion de ello
	 * crea el panel con dimensiones en la X distintas
	 * @param comprobador : String
	 */
	private void comprobarOrigen(String comprobador) {
		switch (comprobador) {  
        case "principal":
        	this.setBounds(175, 0, 511, 503);
            break;
        case "auxiliar":
        	this.setBounds(0, 0, 511, 503);
            break;
    }
		
	}

	/**
	 * Extrae en un arrayList un vector de String con los datos en crudo de todos
	 * los clientes alojados en el sistema con excepcion de Eliminado que es la
	 * cuenta auxiliar para los clientes eliminados
	 * 
	 * @return List <String[]>
	 */
	private List<String[]> extraerClientes() {
		List<String[]> datos = new ArrayList<String[]>();

		for (Cliente c : s.getClientes().values()) {
			if (c.getEmail().compareTo("Eliminado") != 0) {
				datos.add(new String[] { c.getEmail(), c.getNombre(), c.getApellidos(), c.getPuntosTienda() + "",
						c.getNumeroReservas() + "" });
			}

		}

		return datos;
	}
}
