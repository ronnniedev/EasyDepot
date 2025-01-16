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

import excepciones.LogicaException;
import excepciones.PersistenciaException;
import logica.Sistema;

import java.awt.Font;
import java.awt.TextField;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
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
		setBackground(new Color(255, 255, 255));
		this.setBounds(0, 0, 511, 503);
		panel.add(this);
		setLayout(null);
		try {
			this.s = Sistema.getInstance();
		} catch (Exception e) {
			
		} 
		
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
		
		JTable tablaCabinas = new JTable(modelo);
		tablaCabinas.setBorder(new LineBorder(new Color(0, 0, 0)));
		tablaCabinas.setBackground(new Color(255, 255, 255)); 
		tablaCabinas.setBounds(185, 119, 491, 311);
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
		
		JSpinner selectorFiltro = new JSpinner();
		selectorFiltro.setBounds(292, 68, 81, 20);
		add(selectorFiltro);
		
	}

	private List<String[]> extraerCabinas(Local l) {
		List <String[]> datos = new ArrayList<String[]>();
		
		for(Cabina c: l.getCabinas()) {
			datos.add(new String[]{c.getIdCabina(),escribirEstado(c.getAbierto()), escribirReservada(c.getReservada()), 
					escribirIdReserva(c) + "" , c.getTipo()});
		}
		return datos;
	}

	

	private String escribirIdReserva(Cabina c) {
		String id = s.buscarReservaCabina(c);
		
		if(id == null) {
			return "Sin reservar";
		}
		return id;
	}

	private String escribirReservada(Boolean reservada) {
		if(reservada) {
			return "Reservada";
		}
		return "Sin reservar";
	}

	private String escribirEstado(Boolean abierto) {
		if(abierto) {
			return "Abierta";
		}
		return "Cerrada";
	}
}
