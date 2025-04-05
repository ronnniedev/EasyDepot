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
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import componentes.Button;
import componentes.Colores;
import componentes.Estilos;
import componentes.PanelDatosRedondeado;
import logica.Sistema;
import modelo.Articulo;
import modelo.Local;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class PanelArticulos extends JPanel {

	private static final long serialVersionUID = 1L;
	private Sistema s;
	private List <Articulo> articulos;
	private JTextField buscador;

	/**
	 * Panel que muestra todos los articulos dentro del sistema y dentro de una tabla
	 */
	public PanelArticulos(JPanel panel,Local l) {
		// establece las dimensiones de la tabla
		setBackground(new Color(255, 255, 255));
		this.setBounds(0, 0, 511, 503);
		panel.add(this);
		setLayout(null);
		
		try {
			this.s = Sistema.getInstance();
			articulos = l.getArticulos();
		} catch (Exception e) {
			
		} 
		
		
		// preparamos la tabla y el modelo
		String [] cabecera = {"Id articulo","Nombre","Stock","Precio"};
		List <String[]> datosLista = extraerArticulos("");
		String [][] datos = datosLista.toArray(new String[0][0]);
		
		// Creamos un modelo de tabla no editable modificando el metodo isCellEditable para que no lo sea mas
				DefaultTableModel modelo = new DefaultTableModel(datos, cabecera) {
					@Override
					public boolean isCellEditable(int row, int column) {
						return false; // Hace todas las celdas no editables
					}
				};
		
		// Creamos la tabla de articulos
		JTable tablaArticulos = new JTable(modelo);
		Estilos.prepararTabla(tablaArticulos,modelo,4);
		tablaArticulos.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int row = tablaArticulos.getSelectedRow();
				String idArticulo = (String) tablaArticulos.getValueAt(row, 0);
				// refresca la pantalla y crea un nuevo panel para generar los datos del cliente nuevo
				if(idArticulo != null) {
					removeAll();
					repaint();
					revalidate();  
					add(new PanelCarruselArticulo(panel,l,s.buscarArticulo(l,idArticulo)));
				}
				
			}
		});
		
		JScrollPane scrollPane = new JScrollPane(tablaArticulos);
		Estilos.estiloBarra(scrollPane);
		scrollPane.setBounds(10, 120, 491, 311);
		this.add(scrollPane);
		
		JButton btnVista = new Button("Modo Vista");
		btnVista.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				removeAll();
				repaint();
				revalidate();  
				add(new PanelCarruselArticulo(panel,l,l.getArticulos().get(0)));
			}
		});
		btnVista.setFont(new Font("Verdana", Font.BOLD, 10));
		btnVista.setBounds(205, 78, 100, 30);
		add(btnVista);
		
		buscador = new JTextField("buscar por nombre...");
		buscador.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				buscador.setText("");
				List<String[]> datosLista = null;
				modelo.setNumRows(0);
				
				datosLista = extraerArticulos(buscador.getText());
				
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
				
				datosLista = extraerArticulos(buscador.getText());
				
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
		
		JPanel panelTitulo = new PanelDatosRedondeado(30);
		panelTitulo.setBounds(130, 31, 250, 30);
		panelTitulo.setBackground(Colores.getAZUL_CLARO());
		add(panelTitulo);
		
		// establece el titulo de lel panel
		JLabel lblCabinasDeLocal = new JLabel("Articulos de local: " + l.getLocalId(), SwingConstants.CENTER);
		panelTitulo.add(lblCabinasDeLocal);
		lblCabinasDeLocal.setFont(new Font("Verdana", Font.BOLD, 18));
		lblCabinasDeLocal.setBackground(Color.WHITE);

	}

	/**
	 * Extrae los articulos que se encuentran dentro del sistema y lo devuelve en formato de listas de Strings
	 * @param List : articulos
	 * @return List <String[]>
	 */
	private List<String[]> extraerArticulos(String texto) {
		List <String[]> datos = new ArrayList<String[]>();
		
		for(Articulo a: articulos) {
			if(a.getNombre().toLowerCase().startsWith(texto.toLowerCase())) {
				datos.add(new String[]{a.getIdArticulo(),a.getNombre(),a.getStock() + "",a.getPrecio() + ""});
			}
		}
		return datos;
	}
}
