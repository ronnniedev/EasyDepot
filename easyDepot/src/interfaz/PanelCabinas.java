package interfaz;

import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;

import modelo.Cabina;
import modelo.Local;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import componentes.Estilos;
import logica.Sistema;

import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class PanelCabinas extends JPanel {

	private static final long serialVersionUID = 1L;
	private Sistema s;
	private JTextField buscador;

	/**
	 * Create the panel.
	 */
	public PanelCabinas(Local l,JPanel panel) {
		// Establece las dimensiones de el panel
		setBackground(new Color(255, 255, 255));
		this.setBounds(0, 0, 511, 503);
		panel.add(this);
		setLayout(null);
		try {
			this.s = Sistema.getInstance();
		} catch (Exception e) {
			
		} 
		// Establece el titulo del panel
		JLabel lblCabinasDeLocal = new JLabel("Cabinas de local: " + l.getLocalId(), SwingConstants.LEFT);
		lblCabinasDeLocal.setFont(new Font("Verdana", Font.BOLD, 18));
		lblCabinasDeLocal.setBackground(Color.WHITE);
		lblCabinasDeLocal.setBounds(0, 35, 511, 45);
		add(lblCabinasDeLocal);
		
		String [] cabecera = {"Id Cabina(L-C)","Abierto","Reservada","idReserva","Tipo"};
		List <String[]> datosLista = extraerCabinas(l);
		String [][] datos = datosLista.toArray(new String[0][0]);
		
		// Creamos un modelo de tabla no editable modificando el metodo isCellEditable para que no lo sea mas
				DefaultTableModel modelo = new DefaultTableModel(datos, cabecera) {
					@Override
					public boolean isCellEditable(int row, int column) {
						return false; // Hace todas las celdas no editables
					}
				};
		
		// Tabla donde escoges cada row y muestra la informacion de la cabina establecida
		JTable tablaCabinas = new JTable(modelo);
		Estilos.prepararTabla(tablaCabinas,modelo,4);
		tablaCabinas.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int row = tablaCabinas.getSelectedRow();
				String idCabina = (String) tablaCabinas.getValueAt(row, 0);
				// refresca la pantalla y crea un nuevo panel para generar los datos del cliente nuevo
				removeAll();
				repaint();
				revalidate();  
				add(new PanelCabina(panel,s,s.buscarCabina(idCabina,l),l));
			}
		});
		
		JScrollPane scrollPane = new JScrollPane(tablaCabinas);
		scrollPane.setBounds(10, 98, 491, 311);
		this.add(scrollPane);
		
		buscador = new JTextField();
		buscador.setBounds(383, 69, 118, 19);
		this.add(buscador);
		buscador.setColumns(10);
		
		// Permite escpger el filtro que determina el filtro de la tabla
		JSpinner selectorFiltro = new JSpinner();
		selectorFiltro.setBounds(292, 68, 81, 20);
		add(selectorFiltro);
		
	}

	/**
	 * Extrae las cabinas dentro de la base de datos y devuelve una lista de String para escribir la tabla de cabinas
	 * @param l : Local
	 * @return List <String[]>
	 */
	private List<String[]> extraerCabinas(Local l) {
		List <String[]> datos = new ArrayList<String[]>();
		
		for(Cabina c: l.getCabinas()) {
			datos.add(new String[]{c.getIdCabina(),escribirEstado(c.getAbierto()), escribirReservada(c.getReservada()), 
					escribirIdReserva(c) + "" , c.getTipo()});
		}
		return datos;
	}
	/**
	 * Escribe la id de la reserva si esta se encuentra reserva, si no es asi, pone Sin reservar
	 * @param c : Cabina
	 * @return String
	 */
	private String escribirIdReserva(Cabina c) {
		String id = s.buscarReservaCabina(c);
		
		if(id == null) {
			return "Sin reservar";
		}
		return id;
	}

	/**
	 * Dependdiendo de si esta reservada la canina o no se muestra un String u otro
	 * @param reservada : Boolean
	 * @return String
	 */
	private String escribirReservada(Boolean reservada) {
		if(reservada) {
			return "Reservada";
		}
		return "Sin reservar";
	}
	
	/**
	 * Escribe el estado de la cabina dependiendo de si esta esta abierta o no
	 * @param abierto : Boolean
	 * @return String
	 */
	private String escribirEstado(Boolean abierto) {
		if(abierto) {
			return "Abierta";
		}
		return "Cerrada";
	}
}
