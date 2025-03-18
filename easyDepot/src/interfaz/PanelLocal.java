package interfaz;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import componentes.Button;
import componentes.ButtonGris;
import componentes.Colores;
import componentes.PanelDatosRedondeado;
import logica.Sistema;
import modelo.Local;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class PanelLocal extends JPanel {

	private static final long serialVersionUID = 1L;
	private PanelLocal panelLocal;
	private JLabel lblReserva;
	private JTextField txtDireccion;
	private JTextField txtCoordenadas;
	private JLabel lblDireccion;
	private JLabel lblCoordenadas;
	private JLabel lblIngresos;
	private JLabel lblReservas;
	private Button btnVerArticulos;
	private JTextField txtIngresos;
	private JTextField txtReservas;
	private JPanel panelTitulo;

	/**
	 * Muestra la informacion de un local y permite su edicion, ver las reservas del mismo y sus articulos
	 */
	public PanelLocal(JPanel panel, Sistema s, Local l) {
		// Establecemos las dimensiones del panel
		setBackground(new Color(255, 255, 255));
		this.setBounds(0, 0, 511, 503);
		panel.add(this);
		setLayout(null);
		
		this.panelLocal = this;

		// Panel que contiene todos los textfields y labels de la reserva asociada al panel
		// Establece un panel para englobar datos de la cabina
		PanelDatosRedondeado panelDatos = new PanelDatosRedondeado(30);
		panelDatos.setBounds(42, 90, 426, 293);
		add(panelDatos);
		panelDatos.setLayout(null);
		add(panelDatos);

		txtDireccion = new JTextField();
		txtDireccion.setHorizontalAlignment(SwingConstants.LEFT);
		txtDireccion.setBounds(234, 29, 168, 40);
		txtDireccion.setBackground(new Color(255, 255, 255));
		txtDireccion.setEditable(false);
		txtDireccion.setFont(new Font("Verdana", Font.BOLD, 14));
		panelDatos.add(txtDireccion);
		txtDireccion.setColumns(10);

		lblCoordenadas = new JLabel("Coordenadas : ");
		lblCoordenadas.setBounds(41, 98, 147, 27);
		lblCoordenadas.setFont(new Font("Verdana", Font.BOLD, 16));
		panelDatos.add(lblCoordenadas);

		txtCoordenadas = new JTextField();
		txtCoordenadas.setHorizontalAlignment(SwingConstants.LEFT);
		txtCoordenadas.setBounds(234, 92, 168, 40);
		txtCoordenadas.setBackground(new Color(255, 255, 255));
		txtCoordenadas.setEditable(false);
		txtCoordenadas.setFont(new Font("Verdana", Font.BOLD, 14));
		txtCoordenadas.setColumns(10);
		panelDatos.add(txtCoordenadas);

		lblIngresos = new JLabel("Ingresos : ");
		lblIngresos.setBounds(41, 163, 147, 27);
		lblIngresos.setFont(new Font("Verdana", Font.BOLD, 16));
		panelDatos.add(lblIngresos);

		lblReservas = new JLabel("Reservas : ");
		lblReservas.setBounds(41, 227, 214, 27);
		lblReservas.setFont(new Font("Verdana", Font.BOLD, 16));
		panelDatos.add(lblReservas);
		

		
		// Instanciamos los componentes
		lblDireccion = new JLabel("Direccion : ");
		lblDireccion.setBounds(41, 29, 123, 40);
		panelDatos.add(lblDireccion);
		lblDireccion.setFont(new Font("Verdana", Font.BOLD, 16));
		
		txtIngresos = new JTextField();
		txtIngresos.setHorizontalAlignment(SwingConstants.LEFT);
		txtIngresos.setText((String) null);
		txtIngresos.setFont(new Font("Verdana", Font.BOLD, 14));
		txtIngresos.setEditable(false);
		txtIngresos.setColumns(10);
		txtIngresos.setBackground(Color.WHITE);
		txtIngresos.setBounds(234, 157, 168, 40);
		panelDatos.add(txtIngresos);
		
		txtReservas = new JTextField();
		txtReservas.setHorizontalAlignment(SwingConstants.LEFT);
		txtReservas.setText((String) null);
		txtReservas.setFont(new Font("Verdana", Font.BOLD, 14));
		txtReservas.setEditable(false);
		txtReservas.setColumns(10);
		txtReservas.setBackground(Color.WHITE);
		txtReservas.setBounds(234, 221, 168, 40);
		panelDatos.add(txtReservas);

		// Abre el panel de las cabinas mostrando todas las cabinas asoaciadas a un local
		Button btnCabinas = new Button("Ver cabinas");
		btnCabinas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// refresca la pantalla y crea un nuevo panel para generar los datos del cliente nuevo
				removeAll();
				repaint();
				revalidate();  
				add(new PanelCabinas(l,panel));
				
			}
		});
		btnCabinas.setFont(new Font("Verdana", Font.BOLD, 12));
		btnCabinas.setBounds(34, 395, 126, 45);
		add(btnCabinas);

		/*
		 * Abre un panel mostrando un carrusel con los artciulos asoaciado a un local concreot, en caso de que el local
		 * no tenga articulos este pregunta al usuario si quiere crear uno, en caso contrario solo abre el panel
		 * mostrando los articulos
		 */
		btnVerArticulos = new Button("Ver articulos");
		btnVerArticulos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!comprobarContenido(l)) {
					int respuesta = 
				            JOptionPane.showConfirmDialog(null,"No hay articulos en este local ¿Quieres crear uno nuevo?"
				                ,"Advertencia",JOptionPane.YES_NO_OPTION);
				        if(respuesta == JOptionPane.YES_OPTION) {
				        	removeAll();
							repaint();
							revalidate(); 
				        	add(new PanelCarruselArticulo(panel,l,null));
				        }
				}else {
					removeAll();
					repaint();
					revalidate();  
					add(new PanelCarruselArticulo(panel,l,l.getArticulos().get(0)));
				}
				
			}
		});
		btnVerArticulos.setFont(new Font("Verdana", Font.BOLD, 12));
		btnVerArticulos.setBounds(189, 395, 126, 45);
		add(btnVerArticulos);

		// Muestra las reservas asociadas a un local.
		Button btnVerReservas = new Button("Ver reservas");
		btnVerReservas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				removeAll();
				repaint();
				revalidate();  
				
				JPanel panelPrueba = new PanelReservas(panel,"Reservas de local: " + l.getLocalId()
				,s.buscarReservasLocal(l.getLocalId()));
				panelPrueba.setBounds(0, 0, 511, 503);
				add(panelPrueba);
			}
		});
		btnVerReservas.setFont(new Font("Verdana", Font.BOLD, 12));
		btnVerReservas.setBounds(344, 395, 126, 45);
		add(btnVerReservas);

		// Abre una ventana de editar local , tambien bloquea el poder manipular la ventana del local
		ButtonGris btnEditarLocal = new ButtonGris("Editar local");
		btnEditarLocal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VentanaPrincipal.getVentana().setEnabled(false);
				
				new VentanaEditarLocal(l,panelLocal);
			}
		});
		btnEditarLocal.setFont(new Font("Verdana", Font.BOLD, 12));
		btnEditarLocal.setBounds(189, 448, 126, 45);
		add(btnEditarLocal);
		
		panelTitulo = new PanelDatosRedondeado(30);
		panelTitulo.setBounds(135, 31, 240, 33);
		panelTitulo.setBackground(Colores.getAZUL_CLARO());
		add(panelTitulo);
		
		// Establecemos el titulo del panel
		lblReserva = new JLabel("Id de local:", SwingConstants.CENTER);
		panelTitulo.add(lblReserva);
		lblReserva.setBackground(new Color(255, 255, 255));
		lblReserva.setFont(new Font("Verdana", Font.BOLD, 18));
		mostrarLocal(l);
	}

	/**
	 * Conmprueba si este local tiene articulo, en caso negativo devuelve false
	 * @param l : Local
	 * @return Boolean
	 */
	private boolean comprobarContenido(Local l) {
		if(l.getArticulos().size() != 0) {
			return true;
		}
		return false;
	}

	/**
	 * Actualiza los labels y textfields para mostrar la informacion del local asoaciado al panel
	 * @param l : Local
	 */
	public void mostrarLocal(Local l) {
		lblReserva.setText("Local: " + l.getLocalId());
		txtDireccion.setText(l.getDireccion());
		txtCoordenadas.setText(l.getCoordenadas());
		txtIngresos.setText(l.getIngresos() + "");
		txtReservas.setText(l.getNumeroReservas() + "");
	}
}
