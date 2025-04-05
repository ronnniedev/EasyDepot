package interfaz;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import componentes.Estilos;
import logica.Sistema;
import modelo.Cliente;
import modelo.Email;
import javax.swing.JSpinner;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class PanelClientes extends JPanel {

	private static final long serialVersionUID = 1L;
	private Sistema s;
	private JTextField buscador;
	private Map <Email,Cliente> clientes;

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
		this.clientes = s.getClientes();
		panel.add(this);
		setLayout(null);

		// Muestra el titulo d ela taba
		JLabel lblClientes = new JLabel("Clientes", SwingConstants.CENTER);
		lblClientes.setFont(new Font("Verdana", Font.BOLD, 24));
		lblClientes.setBounds(0, 29, 511, 45);
		this.add(lblClientes);

		String[] cabecera = { "Email", "Nombre", "Apellidos"};
		List<String[]> datosLista = extraerClientes("");
		String[][] datos = datosLista.toArray(new String[0][0]);

		// Creamos un modelo de tabla no editable modificando el metodo isCellEditable
		// para que no lo sea mas
		DefaultTableModel modelo = new DefaultTableModel(datos, cabecera) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return false; // Hace todas las celdas no editables
			}
		};

		// Establece un
		JTable tablaClientes = new JTable(modelo);
		Estilos.prepararTabla(tablaClientes,modelo,4);
		
		
		tablaClientes.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int row = tablaClientes.getSelectedRow();
				String emailCliente = (String) tablaClientes.getValueAt(row, 0);
				// refresca la pantalla y crea un nuevo panel para generar los datos del cliente
				// nuevo
				if(emailCliente != null) {
					removeAll();
					repaint();
					revalidate();
					add(new PanelCliente(panel, s, s.getClientes().get(new Email(emailCliente))));	
				}
				
			}
		});
		
		
		JScrollPane scrollPane = new JScrollPane(tablaClientes);
		Estilos.estiloBarra(scrollPane);
		this.add(scrollPane);

		buscador = new JTextField();
		buscador.setText("escribe email...");
		buscador.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				buscador.setText("");
				List<String[]> datosLista = null;
				modelo.setNumRows(0);
				
				datosLista = extraerClientes(buscador.getText());
				
				for(String [] fila: datosLista) {
					modelo.addRow(fila);
				}
				
				// Cargamos la tabla entera
				Estilos.cargarTablaCompleta(modelo);
			}
		});
		buscador.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				List<String[]> datosLista = null;
				modelo.setNumRows(0);
				
				datosLista = extraerClientes(buscador.getText());
				
				for(String [] fila: datosLista) {
					modelo.addRow(fila);
				}
				
				// Cargamos la tabla entera
				Estilos.cargarTablaCompleta(modelo);
			}
		});
		Estilos.prepararBuscador(buscador);
		add(buscador);
		buscador.setColumns(10);
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
	private List<String[]> extraerClientes(String texto) {
		List<String[]> datos = new ArrayList<String[]>();

		for (Cliente c : s.getClientes().values()) {
			if (c.getEmail().compareTo("Eliminado") != 0 && c.getEmail().startsWith(texto)) {
				datos.add(new String[] { c.getEmail(), c.getNombre(), c.getApellidos()});
			}

		}

		return datos;
	}
	
}
