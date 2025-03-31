package interfaz;

import java.awt.Color;

import javax.swing.JPanel;

import logica.GestorComprobaciones;
import logica.Sistema;
import modelo.Articulo;
import modelo.Local;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.ImageIcon;
import java.awt.GridLayout;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Timer;

import javax.swing.JTextField;
import javax.swing.SwingConstants;

import componentes.Button;
import componentes.CargadorImagenes;
import componentes.Colores;
import componentes.Estilos;
import componentes.PanelDatosRedondeado;
import excepciones.LogicaException;

import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PanelCarruselArticulo extends JPanel {

	private static final long serialVersionUID = 1L;
	private Local l;
	private Sistema s;
	private JTextField txtNombre;
	private JTextField txtStock;
	private JTextField txtPrecio;
	private List<Articulo> articulos;
	private Articulo articuloSeleccionado;
	private int indexSeleccionado;
	private JLabel lblArticulo, lblInfoNombre, lblInfoStock, lblInfoPrecio;
	private boolean modoCreacion;
	private JPanel panel;
	private Button btnAnterior;
	private Button btnPosterior;
	private Button btnTablas;
	private Button btnEliminar;
	private JPanel panelArticulo, panelPrincipal;
	private int aceleracion;
	private JLabel lblError;

	/**
	 * Muestra la informacion de los articulos en formato de carrusel, muestra los
	 * datos del articulo, tiene una vista de tabla y permite editar y eliminar el
	 * articulo pertinente.
	 */
	public PanelCarruselArticulo(JPanel panel, Local l, Articulo a) {
		// Establece dimensiones del panel
		setBackground(new Color(255, 255, 255));
		this.setBounds(0, 0, 511, 503);
		panel.add(this);
		setLayout(null);

		this.panelPrincipal = this;

		// Instanciamos las diferentes variables
		this.articulos = l.getArticulos();
		this.l = l;
		this.panel = panel;

		try {
			this.s = Sistema.getInstance();
		} catch (Exception e) {

		}

		// Cambia al panel Articulos
		btnTablas = new Button("Modo Tabla");
		btnTablas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				removeAll();
				repaint();
				revalidate();
				add(new PanelArticulos(panel, l));
			}
		});
		btnTablas.setFont(new Font("Verdana", Font.BOLD, 10));
		btnTablas.setBounds(205, 78, 100, 30);
		add(btnTablas);

		panelArticulo = new PanelDatosRedondeado(50);
		panelArticulo.setBounds(35, 117, 436, 326);
		add(panelArticulo);
		panelArticulo.setLayout(null);

		// Pregunta antes de eliminar el articulo
		btnEliminar = new Button("Eliminar");
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int respuesta = JOptionPane.showConfirmDialog(null,
						"¿Vas a eliminar este articulo, estas seguro de ello?", "Advertencia",
						JOptionPane.YES_NO_OPTION);
				eliminarArticulo(respuesta);
			}
		});
		btnEliminar.setFont(new Font("Verdana", Font.BOLD, 16));
		btnEliminar.setBounds(17, 276, 190, 29);
		panelArticulo.add(btnEliminar);

		// Va al articulo anterior en la lista, en caso de no haber mas retorna al
		// ultimo articulo de la lista
		btnAnterior = new Button("");
		btnAnterior.setIcon(Estilos.prepararImagenFlecha("/iconos/flechaIzquierda.png"));
		btnAnterior.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if ((indexSeleccionado - 1) < 0) {
					indexSeleccionado = articulos.size() - 1;
				} else {
					indexSeleccionado = indexSeleccionado - 1;
				}

				Timer retorno = new Timer(10, new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						int x = panelArticulo.getX();
						if (x > 35) {
							if (x + aceleracion < 35) {
								x = 35;
							} else {
								x += aceleracion;
							}
							panelArticulo.setBounds(x, 117, 436, 326);
							panelArticulo.repaint();
							panelArticulo.revalidate();
						} else {
							((Timer) e.getSource()).stop();
						}
					}
				});

				Timer avanze = new Timer(10, new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						int x = panelArticulo.getX();
						if (x > -500) {
							x += aceleracion;
							aceleracion -= 1;
							panelArticulo.setBounds(x, 117, 436, 326);
							panelArticulo.repaint();
							panelArticulo.revalidate();
						} else {
							articuloSeleccionado = articulos.get(indexSeleccionado);
							panelArticulo.setBounds(600, 117, 436, 326);
							actualizarDatos(articuloSeleccionado);
							retorno.start();
							((Timer) e.getSource()).stop();
						}
					}
				});
				aceleracion = -1;
				avanze.start();
				lblError.setVisible(false);
			}
		});
		btnAnterior.setFont(new Font("Verdana", Font.BOLD, 11));
		btnAnterior.setBounds(24, 78, 85, 30);
		add(btnAnterior);

		// Va al articulo inmediatamente posterior en la lista de articulos, en caso de
		// no haber mas retorna al primer
		// articulo de la lista
		btnPosterior = new Button("");
		btnPosterior.setIcon(Estilos.prepararImagenFlecha("/iconos/flechaDerecha.png"));
		btnPosterior.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if ((indexSeleccionado + 1) == articulos.size()) {
					indexSeleccionado = 0;
				} else {
					indexSeleccionado = indexSeleccionado + 1;
				}
				Timer retorno = new Timer(10, new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						int x = panelArticulo.getX();
						if (x < 27) {
							if (x + aceleracion > 35) {
								x = 35;
							} else {
								x += aceleracion;
							}
							panelArticulo.setBounds(x, 117, 436, 326);
							panelArticulo.repaint();
							panelArticulo.revalidate();
						} else {
							((Timer) e.getSource()).stop();
						}
					}
				});

				Timer avanze = new Timer(10, new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						int x = panelArticulo.getX();
						if (x < 600) {
							x += aceleracion;
							aceleracion += 1;
							panelArticulo.setBounds(x, 117, 436, 326);
							panelArticulo.repaint();
							panelArticulo.revalidate();
						} else {
							articuloSeleccionado = articulos.get(indexSeleccionado);
							panelArticulo.setBounds(-500, 117, 436, 326);
							actualizarDatos(articuloSeleccionado);
							retorno.start();
							((Timer) e.getSource()).stop();
						}
					}
				});
				aceleracion = 1;
				avanze.start();
				lblError.setVisible(false);
			}

		});
		btnPosterior.setFont(new Font("Verdana", Font.BOLD, 10));
		btnPosterior.setBounds(400, 78, 85, 30);
		add(btnPosterior);

		// Analiza si debe ponerse el panel en modo creacion o no, en caso de que no
		// haya ningun articulo en la lista
		// se activa , en caso contrario no
		if (articulos.size() == 0) {
			activarModoCreacion();
			this.articuloSeleccionado = new Articulo("Nuevo Articulo", l.getLocalId(), "", 0, 0, "");
			this.indexSeleccionado = 0;
		} else {
			desactivarModoCreacion();
			this.articuloSeleccionado = a;
			this.indexSeleccionado = obtenerIndexInicial(a);
		}

		JLabel lblImagen = new JLabel("");
		lblImagen.setBounds(17, 29, 200, 147);
		ImageIcon imageIcon = new ImageIcon(PanelCarruselArticulo.class.getResource("/iconos/localStat.png"));
		Image image = imageIcon.getImage().getScaledInstance(lblImagen.getWidth(), lblImagen.getHeight(), 
				Image.SCALE_SMOOTH);
		lblImagen.setIcon(new ImageIcon(image));
		lblImagen.setBackground(new Color(255, 255, 255));
		panelArticulo.add(lblImagen);

		// Guarda los datos de el articulo seleccionado, en caso de estar el modo
		// creacion activado registra el
		// nuevo articulo en el sistema, en caso contrario actualiza los valores del
		// articulo en el sistema y
		// la base de datos
		Button btnGuardar = new Button("Guardar cambios");
		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String nombre = txtNombre.getText();
					String precio = txtPrecio.getText();
					String stock = txtStock.getText();

					List<String> palabras = new ArrayList<String>();
					palabras.add(nombre);
					palabras.add(precio);
					palabras.add(stock);
					GestorComprobaciones.comprobarTextoVacio(palabras);

					if (precio.contains(",")) {
						String resul = "";
						for (int i = 0; i < precio.length(); i++) {
							if (precio.charAt(i) == ',') {
								resul += '.';
							} else {
								resul += precio.charAt(i);
							}
						}
						precio = resul;
					}

					int precioDouble = Integer.parseInt(precio);
					int stockInt = Integer.parseInt(stock);

					// Comprobamos si los valores son positivos
					if (stockInt < 0 || precioDouble < 0) {
						throw new LogicaException("Los valores numericos deben ser positivos");
					}
					if (!modoCreacion) {
						articuloSeleccionado.setNombre(nombre);
						articuloSeleccionado.setPrecio(precioDouble);
						articuloSeleccionado.setStock(stockInt);
						s.actualizarArticulo(articuloSeleccionado);
						JOptionPane.showMessageDialog(null, "Exito al guardar", "Guardado con exito",
								JOptionPane.INFORMATION_MESSAGE);
						lblError.setVisible(false);
					} else {
						try {
							s.addArticulo(l.getLocalId(), txtNombre.getText(), stockInt,
									precioDouble, "Texto ejemplo");
							indexSeleccionado = articulos.size() - 1;
							articuloSeleccionado = articulos.get(indexSeleccionado);
							actualizarDatos(articuloSeleccionado);
							desactivarModoCreacion();
							JOptionPane.showMessageDialog(null, "Exito al crear", "Creado con exito",
									JOptionPane.INFORMATION_MESSAGE);
							lblError.setVisible(false);
						} catch (NumberFormatException e1) {
							// TODO definir que hacen estas excepciones
						} catch (LogicaException e1) {
							lblError.setVisible(true);
							lblError.setText(e1.getMessage());
						}
					}
				} catch (NumberFormatException e1) {
					lblError.setText("ERROR stock y precio deben estar escritos en formato numerico entero");
					lblError.setVisible(true);
				} catch (LogicaException e1) {
					lblError.setText(e1.getMessage());
					lblError.setVisible(true);
				}
			}
		});
		btnGuardar.setFont(new Font("Verdana", Font.BOLD, 16));
		btnGuardar.setBounds(17, 237, 190, 29);
		panelArticulo.add(btnGuardar);

		// A futuro permitira cargar una imagen nueva para el articulo asociado TODO
		Button btnSeleccionarImagen = new Button("Subir Imagen");
		btnSeleccionarImagen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BufferedImage imagen = CargadorImagenes.selectAndLoadImage();
			}
		});
		btnSeleccionarImagen.setFont(new Font("Verdana", Font.BOLD, 16));
		btnSeleccionarImagen.setBounds(17, 199, 190, 29);
		panelArticulo.add(btnSeleccionarImagen);

		JLabel lblNombre = new JLabel("Nombre:");
		lblNombre.setBounds(227, 32, 104, 35);
		panelArticulo.add(lblNombre);
		lblNombre.setFont(new Font("Verdana", Font.BOLD, 16));

		txtNombre = new JTextField(articuloSeleccionado.getNombre());
		txtNombre.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				lblInfoNombre.setVisible(true);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				lblInfoNombre.setVisible(false);
			}
		});
		txtNombre.setBounds(310, 32, 116, 36);
		panelArticulo.add(txtNombre);
		txtNombre.setColumns(10);

		txtStock = new JTextField();
		txtStock.setBounds(310, 133, 116, 36);
		panelArticulo.add(txtStock);
		txtStock.setColumns(10);
		txtStock.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				lblInfoStock.setVisible(true);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				lblInfoStock.setVisible(false);
			}
		});
		txtStock.setText(articuloSeleccionado.getStock() + "");

		txtPrecio = new JTextField(articuloSeleccionado.getPrecio() + "");
		txtPrecio.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				lblInfoPrecio.setVisible(true);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				lblInfoPrecio.setVisible(false);
			}
		});
		txtPrecio.setBounds(310, 237, 116, 36);
		panelArticulo.add(txtPrecio);
		txtPrecio.setColumns(10);

		JLabel lblPrecio = new JLabel("Precio:");
		lblPrecio.setBounds(221, 232, 89, 38);
		panelArticulo.add(lblPrecio);
		lblPrecio.setFont(new Font("Verdana", Font.BOLD, 16));

		JLabel lblStock = new JLabel("Stock:");
		lblStock.setBounds(227, 134, 83, 35);
		panelArticulo.add(lblStock);
		lblStock.setFont(new Font("Verdana", Font.BOLD, 16));

		lblInfoNombre = new JLabel("Nombre con letras");
		lblInfoNombre.setFont(new Font("Verdana", Font.BOLD, 8));
		lblInfoNombre.setBounds(310, 70, 116, 13);
		lblInfoNombre.setVisible(false);
		panelArticulo.add(lblInfoNombre);

		lblInfoStock = new JLabel("Numeros enteros");
		lblInfoStock.setFont(new Font("Verdana", Font.BOLD, 8));
		lblInfoStock.setBounds(310, 172, 116, 13);
		lblInfoStock.setVisible(false);
		panelArticulo.add(lblInfoStock);

		lblInfoPrecio = new JLabel("Precio con decimales");
		lblInfoPrecio.setFont(new Font("Verdana", Font.BOLD, 8));
		lblInfoPrecio.setBounds(310, 276, 116, 13);
		lblInfoPrecio.setVisible(false);
		panelArticulo.add(lblInfoPrecio);

		lblError = new JLabel("");
		lblError.setForeground(new Color(255, 0, 0));
		lblError.setBackground(new Color(255, 0, 0));
		lblError.setFont(new Font("Verdana", Font.BOLD, 8));
		lblError.setHorizontalAlignment(SwingConstants.CENTER);
		lblError.setBounds(29, 10, 397, 19);
		panelArticulo.add(lblError);

		// Activa el modo creacion y guarda los datos de una nuevo articulo que se
		// utilizara como plantilla
		Button btnInsertar = new Button("Crear articulo");
		btnInsertar.setFont(new Font("Verdana", Font.BOLD, 16));
		btnInsertar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				activarModoCreacion();
				Articulo a = new Articulo("Nuevo Articulo", l.getLocalId(), "", 0, 0, "");
				actualizarDatos(a);
				lblError.setVisible(false);
			}
		});
		btnInsertar.setBounds(167, 455, 176, 38);
		add(btnInsertar);

		JPanel panelTitulo = new PanelDatosRedondeado(30);
		panelTitulo.setBounds(155, 31, 200, 33);
		panelTitulo.setBackground(Colores.getAZUL_CLARO());
		add(panelTitulo);

		// Labels que establecen la informacion de los articulos
		lblArticulo = new JLabel("Articulo: " + articuloSeleccionado.getIdArticulo());
		panelTitulo.add(lblArticulo);
		lblArticulo.setHorizontalAlignment(SwingConstants.CENTER);
		lblArticulo.setFont(new Font("Verdana", Font.BOLD, 16));

	}

	/**
	 * El modo creacio deshabilita los botones de anterior posterios la vista de
	 * tablas y el de eliminar
	 */
	private void activarModoCreacion() {
		this.modoCreacion = true;
		btnAnterior.setEnabled(false);
		btnPosterior.setEnabled(false);
		btnTablas.setEnabled(false);
		btnEliminar.setEnabled(false);
	}

	/**
	 * Desactiva el modo creacion dejando los botones de posterior y anterior, la
	 * vista de tabalas y eliminar activados
	 */
	private void desactivarModoCreacion() {
		this.modoCreacion = false;
		btnAnterior.setEnabled(true);
		btnPosterior.setEnabled(true);
		btnTablas.setEnabled(true);
		btnEliminar.setEnabled(true);
	}

	/**
	 * Buscamos el articulo seleccionado dentro de la lista para establecer la
	 * posicion dentro de la lista de articulos y poder tener el carrusel de
	 * articulos sincronizados con la lista
	 * 
	 * @param a : Articulo
	 * @return Int
	 */
	private int obtenerIndexInicial(Articulo a) {
		for (int i = 0; i < articulos.size(); i++) {
			Articulo art = articulos.get(i);
			if (art.getIdArticulo().compareTo(a.getIdArticulo()) == 0) {
				return i;
			}
		}
		return 0;
	}

	/**
	 * Actualiza los textFields relacionados con la informacion del articulo
	 * seleccionado
	 * 
	 * @param articuloSeleccionado : Articulo
	 */
	private void actualizarDatos(Articulo articuloSeleccionado) {
		lblArticulo.setText("Articulo: " + articuloSeleccionado.getIdArticulo());
		txtNombre.setText(articuloSeleccionado.getNombre());
		txtPrecio.setText(articuloSeleccionado.getPrecio() + "");
		txtStock.setText(articuloSeleccionado.getStock() + "");
	}

	/**
	 * Elimina el articulo dentro del sistema y de la base de datos, en caso de ya
	 * no queden articulo se vuelve a cargar el panel de local y si hay otro retorna
	 * al primer articulo en la lista
	 * 
	 * @param int : respuesta
	 */
	private void eliminarArticulo(int respuesta) {
		if (respuesta == JOptionPane.YES_OPTION) {
			try {
				s.eliminarArticulo(articuloSeleccionado.getIdArticulo());
				JOptionPane.showMessageDialog(null, "Eliminado con exito", "Eliminado con exito",
						JOptionPane.ERROR_MESSAGE);
				if (articulos.size() == 0) {
					removeAll();
					repaint();
					revalidate();
					add(new PanelLocal(panel, s, l));
				} else {
					articuloSeleccionado = articulos.get(0);
					indexSeleccionado = 0;
					actualizarDatos(articuloSeleccionado);
				}
				lblError.setVisible(false);
			} catch (LogicaException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "Guardado con exito", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
}
