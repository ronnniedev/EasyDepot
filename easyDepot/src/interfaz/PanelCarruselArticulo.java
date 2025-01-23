package interfaz;

import java.awt.Color;

import javax.swing.JPanel;

import logica.Sistema;
import modelo.Articulo;
import modelo.Local;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.GridLayout;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JTextField;
import javax.swing.SwingConstants;

import excepciones.LogicaException;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class PanelCarruselArticulo extends JPanel {

	private static final long serialVersionUID = 1L;
	private Local l;
	private Sistema s;
	private JTextField txtNombre;
	private JTextField txtStock;
	private JTextField txtPrecio;
	private List <Articulo> articulos;
	private Articulo articuloSeleccionado;
	private int indexSeleccionado;
	private JLabel lblArticulo;
	private boolean modoCreacion;
	private JPanel panel;
	private JButton btnAnterior;
	private JButton btnPosterior;
	private JButton btnTablas;
	private JButton btnEliminar;
	

	/**
	 * Muestra la informacion de los articulos en formato de carrusel, muestra los datos del articulo, tiene una
	 * vista de tabla y permite editar y eliminar el articulo pertinente.
	 */
	public PanelCarruselArticulo(JPanel panel,Local l,Articulo a) {
		// Establece dimensiones del panel
		setBackground(new Color(255, 255, 255));
		this.setBounds(0, 0, 511, 503);
		panel.add(this);
		setLayout(null);
		
		// Instanciamos las diferentes variables
		this.articulos = l.getArticulos();
		this.l = l;
		this.panel = panel;
		
		
		try {
			this.s = Sistema.getInstance();
		} catch (Exception e) {
			
		}
		
		// Cambia al panel Articulos
		btnTablas = new JButton("Modo Tabla");
		btnTablas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				removeAll();
				repaint();
				revalidate();  
				add(new PanelArticulos(panel,l));
			}
		});
		btnTablas.setFont(new Font("Verdana", Font.BOLD, 10));
		btnTablas.setBounds(205, 87, 100, 21);
		add(btnTablas);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(35, 117, 436, 326);
		add(panel_1);
		panel_1.setLayout(null);
		
		// Pregunta antes de eliminar el articulo
		btnEliminar = new JButton("Eliminar");
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int respuesta = 
			            JOptionPane.showConfirmDialog(null,"¿Vas a eliminar este articulo, estas seguro de ello?"
			                ,"Advertencia",JOptionPane.YES_NO_OPTION);
				eliminarArticulo(respuesta);
			}
		});
		btnEliminar.setFont(new Font("Verdana", Font.BOLD, 16));
		btnEliminar.setBounds(17, 276, 190, 29);
		panel_1.add(btnEliminar);
		
		// Va al articulo anterior en la lista, en caso de no haber mas retorna al ultimo articulo de la lista
		btnAnterior = new JButton("Anterior");
		btnAnterior.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if((indexSeleccionado - 1) < 0) {
					indexSeleccionado = articulos.size() - 1;
				}else {
					indexSeleccionado  = indexSeleccionado -1;
				}
				articuloSeleccionado = articulos.get(indexSeleccionado);
				actualizarDatos(articuloSeleccionado);
			}
		});
		btnAnterior.setFont(new Font("Verdana", Font.BOLD, 11));
		btnAnterior.setBounds(25, 86, 85, 21);
		add(btnAnterior);
		
		// Va al articulo inmediatamente posterior en la lista de articulos, en caso de no haber mas retorna al primer
		// articulo de la lista
		btnPosterior = new JButton("Posterior");
		btnPosterior.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if((indexSeleccionado + 1)  == articulos.size()) {
					indexSeleccionado = 0;
				}else {
					indexSeleccionado  = indexSeleccionado  + 1;
				}
				articuloSeleccionado = articulos.get(indexSeleccionado);
				actualizarDatos(articuloSeleccionado);
			}

			
		});
		btnPosterior.setFont(new Font("Verdana", Font.BOLD, 10));
		btnPosterior.setBounds(401, 87, 85, 21);
		add(btnPosterior);
		
		// Analiza si debe ponerse el panel en modo creacion o no, en caso de que no haya ningun articulo en la lista
		// se activa , en caso contrario no
		if(articulos.size() == 0) {
			activarModoCreacion();
			this.articuloSeleccionado = new Articulo("Nuevo Articulo",l.getLocalId(),"",0,0,"");
			this.indexSeleccionado = 0;
		}else {
			desactivarModoCreacion();
			this.articuloSeleccionado = a;
			this.indexSeleccionado = obtenerIndexInicial(a);
		}
		
		// Labels que establecen la informacion de los articulos
		lblArticulo = new JLabel("Articulo: " + articuloSeleccionado.getIdArticulo());
		lblArticulo.setHorizontalAlignment(SwingConstants.CENTER);
		lblArticulo.setFont(new Font("Verdana", Font.BOLD, 16));
		lblArticulo.setBounds(0, 27, 511, 35);
		add(lblArticulo);
		
		JLabel lblImagen = new JLabel("");
		lblImagen.setIcon(new ImageIcon(PanelCarruselArticulo.class.getResource("/iconos/logoLogin.png")));
		lblImagen.setBackground(new Color(255, 255, 255));
		lblImagen.setBounds(17, 10, 200, 187);
		panel_1.add(lblImagen);
		
		JPanel panelDatos = new JPanel();
		panelDatos.setBounds(227, 0, 209, 326);
		panel_1.add(panelDatos);
		panelDatos.setLayout(new GridLayout(0, 2, 0, 0));
		
		JLabel lblNombre = new JLabel("Nombre:");
		lblNombre.setFont(new Font("Verdana", Font.BOLD, 16));
		panelDatos.add(lblNombre);
		
		JPanel panelNombre = new JPanel();
		panelDatos.add(panelNombre);
		panelNombre.setLayout(new GridLayout(3, 1, 0, 0));
		
		JPanel panel_2 = new JPanel();
		panelNombre.add(panel_2);
		
		txtNombre = new JTextField(articuloSeleccionado.getNombre());
		panelNombre.add(txtNombre);
		txtNombre.setColumns(10);
		
		JLabel lblStock = new JLabel("Stock:");
		lblStock.setFont(new Font("Verdana", Font.BOLD, 16));
		panelDatos.add(lblStock);
		
		JPanel panel_3 = new JPanel();
		panelDatos.add(panel_3);
		panel_3.setLayout(new GridLayout(1, 0, 0, 0));
		
		JPanel panelNombre_1 = new JPanel();
		panel_3.add(panelNombre_1);
		panelNombre_1.setLayout(new GridLayout(3, 1, 0, 0));
		
		JPanel panel_2_1 = new JPanel();
		panelNombre_1.add(panel_2_1);
		
		txtStock = new JTextField();
		txtStock.setColumns(10);
		txtStock.setText(articuloSeleccionado.getStock() + "");
		panelNombre_1.add(txtStock);
		
		JLabel lblPrecio = new JLabel("Precio:");
		lblPrecio.setFont(new Font("Verdana", Font.BOLD, 16));
		panelDatos.add(lblPrecio);
		
		JPanel panel_4 = new JPanel();
		panelDatos.add(panel_4);
		panel_4.setLayout(new GridLayout(1, 0, 0, 0));
		
		JPanel panelNombre_1_1 = new JPanel();
		panel_4.add(panelNombre_1_1);
		panelNombre_1_1.setLayout(new GridLayout(3, 1, 0, 0));
		
		JPanel panel_2_1_1 = new JPanel();
		panelNombre_1_1.add(panel_2_1_1);
		
		txtPrecio = new JTextField(articuloSeleccionado.getPrecio() + "");
		txtPrecio.setColumns(10);
		panelNombre_1_1.add(txtPrecio);
		
		
		// Guarda los datos de el articulo seleccionado, en caso de estar el modo creacion activado registra el
		// nuevo articulo en el sistema, en caso contrario actualiza los valores del articulo en el sistema y
		// la base de datos
		JButton btnGuardar = new JButton("Guardar cambios");
		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!modoCreacion) {
					// TODO meter control de errores
					articuloSeleccionado.setNombre(txtNombre.getText());
					articuloSeleccionado.setPrecio(Double.parseDouble(txtPrecio.getText()));
					articuloSeleccionado.setStock(Integer.parseInt(txtStock.getText()));
					s.actualizarArticulo(articuloSeleccionado);
					JOptionPane.showMessageDialog(null, "Exito al guardar","Guardado con exito"
													, JOptionPane.INFORMATION_MESSAGE);
				}else {
					try {
						s.addArticulo(l.getLocalId(),txtNombre.getText(),Integer.parseInt(txtStock.getText())
								,Double.parseDouble(txtPrecio.getText()),"Texto ejemplo");
						indexSeleccionado = articulos.size() - 1;
						articuloSeleccionado = articulos.get(indexSeleccionado);
						actualizarDatos(articuloSeleccionado);
						desactivarModoCreacion();
						JOptionPane.showMessageDialog(null, "Exito al crear","Creado con exito"
								, JOptionPane.INFORMATION_MESSAGE);
					} catch (NumberFormatException e1) {
						// TODO definir que hacen estas excepciones
					} catch (LogicaException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		btnGuardar.setFont(new Font("Verdana", Font.BOLD, 16));
		btnGuardar.setBounds(17, 237, 190, 29);
		panel_1.add(btnGuardar);
		
		// A futuro permitira cargar una imagen nueva para el articulo asociado TODO
		JButton btnSeleccionarImagen = new JButton("Subir Imagen");
		btnSeleccionarImagen.setFont(new Font("Verdana", Font.BOLD, 16));
		btnSeleccionarImagen.setBounds(17, 199, 190, 29);
		panel_1.add(btnSeleccionarImagen);
		
		// Activa el modo creacion y guarda los datos de una nuevo articulo que se utilizara como plantilla
		JButton btnInsertar = new JButton("Crear articulo");
		btnInsertar.setFont(new Font("Verdana", Font.BOLD, 16));
		btnInsertar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				activarModoCreacion();
				Articulo a = new Articulo("Nuevo Articulo",l.getLocalId(),"",0,0,"");
				actualizarDatos(a);
			}
		});
		btnInsertar.setBounds(167, 455, 176, 38);
		add(btnInsertar);
		
		
	}
	/**
	 * El modo creacio deshabilita los botones de anterior posterios la vista de tablas y el de eliminar
	 */
	private void activarModoCreacion() {
		this.modoCreacion = true;
		btnAnterior.setEnabled(false);
		btnPosterior.setEnabled(false);
		btnTablas.setEnabled(false);
		btnEliminar.setEnabled(false);
	}
	
	/**
	 * Desactiva el modo creacion dejando los botones de posterior y anterior, la vista de tabalas y eliminar activados
	 */
	private void desactivarModoCreacion() {
		this.modoCreacion = false;
		btnAnterior.setEnabled(true);
		btnPosterior.setEnabled(true);
		btnTablas.setEnabled(true);
		btnEliminar.setEnabled(true);
	}

	/**
	 * Buscamos el articulo seleccionado dentro de la lista para establecer la posicion dentro de la lista de articulos
	 * y poder tener el carrusel de articulos sincronizados con la lista
	 * @param a : Articulo
	 * @return Int
	 */
	private int obtenerIndexInicial(Articulo a) {
		for(int i = 0; i < articulos.size(); i++) {
			Articulo art = articulos.get(i);
			if(art.getIdArticulo().compareTo(a.getIdArticulo()) == 0) {
				return i;
			}
		}
		return 0;
	}
	
	/**
	 * Actualiza los textFields relacionados con la informacion del articulo seleccionado
	 * @param articuloSeleccionado : Articulo
	 */
	private void actualizarDatos(Articulo articuloSeleccionado) {
		lblArticulo.setText("Articulo: " + articuloSeleccionado.getIdArticulo());
		txtNombre.setText(articuloSeleccionado.getNombre());
		txtPrecio.setText(articuloSeleccionado.getPrecio() + "");
		txtStock.setText(articuloSeleccionado.getStock() + "");
	}
	
	/**
	 * Elimina el articulo dentro del sistema y de la base de datos, en caso de ya no queden articulo se vuelve a 
	 * cargar el panel de local y si hay otro retorna al primer articulo en la lista
	 * @param int :  respuesta
	 */
	private void eliminarArticulo(int respuesta) {
		if(respuesta == JOptionPane.YES_OPTION) {
			try {
				s.eliminarArticulo(articuloSeleccionado.getIdArticulo());
				JOptionPane.showMessageDialog(null,"Eliminado con exito","Eliminado con exito"
						, JOptionPane.ERROR_MESSAGE);
				if(articulos.size() == 0) {
					removeAll();
					repaint();
					revalidate();  
					add(new PanelLocal(panel,s,l));
				}else {
					articuloSeleccionado = articulos.get(0);
					actualizarDatos(articuloSeleccionado);
				}
			} catch (LogicaException e1) {
				JOptionPane.showMessageDialog(null,e1.getMessage(),"Guardado con exito"
						, JOptionPane.ERROR_MESSAGE);
			}
		}
	}
}
