package interfaz;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import apykeys.Apykeys;
import componentes.Button;
import componentes.ButtonGris;
import componentes.Colores;
import componentes.Estilos;
import componentes.TxtRedondeado;
import excepciones.LogicaException;
import excepciones.PersistenciaException;
import logica.Sistema;

import javax.swing.JButton;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JTabbedPane;
import javax.swing.JLabel;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.Desktop;

public class VentanaPrincipal extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel panelPrincipal;
	private JTextField textUser;
	private JPasswordField passwordField;
	private Sistema s;
	private static VentanaPrincipal ventana;

	/**
	 * Inicia la aplicaion
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Apykeys.setBaseDatosFinal(1);
					VentanaPrincipal frame = new VentanaPrincipal("EasyDepot 0.51");
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Ventana principal del sistema, desde aqui cargaremos los diferentes paneles
	 * que definiran el funcionamiento de la app
	 * 
	 * @throws LogicaException
	 * @throws SQLException
	 * @throws PersistenciaException
	 */
	public VentanaPrincipal(String titulo) throws PersistenciaException, SQLException, LogicaException {
		// Establecemos el titulo de la ventana principal
		super(titulo);
		setResizable(false);

		ventana = this;
		this.s = Sistema.getInstance();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(400, 150, 700, 540);
		panelPrincipal = new JPanel();
		panelPrincipal.setBackground(new Color(255, 255, 255));
		panelPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(panelPrincipal);
		panelPrincipal.setLayout(null);

		// Abre el email asociado al correo que se muestra, para dar soporte
		ButtonGris btnContacto = new ButtonGris("Contacto");
		btnContacto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Desktop.getDesktop().mail(new URI("mailto:veronicapersonal1995@gmail.com"));
				} catch (IOException e1) {
					System.out.println(e1.getMessage());
				} catch (URISyntaxException e1) {
					System.out.println(e1.getMessage());
				}
			}
		});
		btnContacto.setFont(new Font("Verdana", Font.BOLD, 16));
		btnContacto.setBounds(369, 395, 163, 54);
		panelPrincipal.add(btnContacto);

		// Establecemos los contenedores de user y password
		textUser = new JTextField(40);
		textUser.setBorder(BorderFactory.createLineBorder(Colores.getAZUL_OSCURO(), 3));
		textUser.setFont(new Font("Verdana", Font.PLAIN, 16));
		textUser.setColumns(10);
		textUser.setBounds(193, 232, 305, 54);
		panelPrincipal.add(textUser);

		passwordField = new JPasswordField();
		passwordField.setBorder(BorderFactory.createLineBorder(Colores.getAZUL_OSCURO(), 3));
		passwordField.setFont(new Font("Verdana", Font.PLAIN, 16));
		passwordField.setToolTipText("password");
		passwordField.setBounds(193, 309, 305, 54);
		panelPrincipal.add(passwordField);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(227, 245, 5, 5);
		panelPrincipal.add(tabbedPane);

		// Instanciamos el logo
		JLabel lbLogo = new JLabel();
		lbLogo.setIcon(new ImageIcon(VentanaPrincipal.class.getResource("/iconos/logoLogin.png")));
		lbLogo.setBounds(124, 32, 451, 122);
		panelPrincipal.add(lbLogo);

		// Se muestra en caso de que haya un error
		JLabel lblErrorLogin = new JLabel("Clave o usuario incorrecto, introduzcalo de nuevo.");
		lblErrorLogin.setForeground(new Color(255, 0, 0));
		lblErrorLogin.setFont(new Font("Verdana", Font.BOLD, 16));
		lblErrorLogin.setBounds(124, 176, 510, 46);
		panelPrincipal.add(lblErrorLogin);
		lblErrorLogin.setVisible(false);

		/**
		 * Comprueba si el usuario y la password es correcta, en caso afirmativo logea y
		 * cargamos la informacion de inicio
		 */
		Button btnLogin = new Button("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String user = textUser.getText();
				String password = String.valueOf(passwordField.getPassword());
				try {
					s.loginDesktop(user, password);
					panelPrincipal.removeAll();
					cargarBotonera(panelPrincipal);
					new PanelInicio(panelPrincipal, s);
				} catch (LogicaException e1) {
					lblErrorLogin.setVisible(true);
				}
			}
		});
		btnLogin.setFont(new Font("Verdana", Font.BOLD, 16));
		btnLogin.setBounds(159, 395, 163, 54);
		panelPrincipal.add(btnLogin);

	}

	/**
	 * Carga la botonera una vez se ha realizado el logeo con exito, tiene la
	 * configuracion de los 5 botones principales
	 * 
	 * @param panel
	 */
	public void cargarBotonera(JPanel panel) {
		// Establecemos las dimensiones de la botonera
		panel.setBackground(new Color(255, 255, 255));
		panel.setBorder(new EmptyBorder(5, 5, 5, 5));

		// Creamos un borde vacio
		Border emptyBorder = BorderFactory.createEmptyBorder();

		// Instanciamos los labels y botones que estaran asociados a la botonera
		JPanel panelAvatar = new JPanel();
		panelAvatar.setBackground(new Color(173, 219, 245));
		panelAvatar.setBounds(0, 0, 175, 98);
		panel.add(panelAvatar);
		panelAvatar.setLayout(null);

		// Instanciamos el icono de administrador
		JLabel lblAvatarAdmin = new JLabel();
		lblAvatarAdmin.setIcon(new ImageIcon(VentanaPrincipal.class.getResource("/iconos/adminmod.png")));
		lblAvatarAdmin.setBounds(10, 23, 60, 54);
		panelAvatar.add(lblAvatarAdmin);

		JLabel lblNombreAdmin = new JLabel("Ronnie");
		lblNombreAdmin.setFont(new Font("Verdana", Font.BOLD, 14));
		lblNombreAdmin.setBounds(80, 23, 85, 27);
		panelAvatar.add(lblNombreAdmin);

		JLabel lblCategoriaAdmin = new JLabel("Admin");
		lblCategoriaAdmin.setFont(new Font("Verdana", Font.PLAIN, 12));
		lblCategoriaAdmin.setBounds(80, 50, 85, 27);
		panelAvatar.add(lblCategoriaAdmin);
		setContentPane(panel);
		panel.setLayout(null);

		JPanel panelBotonera = new JPanel();
		panelBotonera.setBackground(new Color(173, 219, 245));
		panelBotonera.setBounds(0, 98, 175, 417);
		panel.add(panelBotonera);
		panelBotonera.setLayout(new GridLayout(8, 0, 0, 0));

		
		Border bordeSuperiorInferior = BorderFactory.createMatteBorder(1,0,1,0,Color.BLACK);
		
		// Boton que deriva al usuario a la seccion de Inicio
		JButton btnInicio = new JButton("Inicio");
		btnInicio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelPrincipal.removeAll();
				cargarBotonera(panel);
				new PanelInicio(panelPrincipal, s);
			}
		});
		// Maneja el hover del boton inicio
		btnInicio.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				cambiarFuenteEntrada(btnInicio);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				cambiarFuenteSalida(btnInicio);
			}
		});
		btnInicio.setFont(new Font("Verdana", Font.BOLD, 16));
		btnInicio.setBackground(new Color(173, 219, 245));
		btnInicio.setBorder(bordeSuperiorInferior);
		btnInicio.revalidate();
		btnInicio.repaint();
		panelBotonera.add(btnInicio);

		// Boton que deriva al usuario a la seccion de locales
		JButton btnLocales = new JButton("Locales");
		btnLocales.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelPrincipal.removeAll();
				cargarBotonera(panel);
				new PanelLocales(panelPrincipal, s);
			}
		});
		// Maneja el hover del boton Local
		btnLocales.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				cambiarFuenteEntrada(btnLocales);
				btnLocales.setIcon(Estilos.prepararImagenBotonera("/iconos/localBlanco.png"));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				cambiarFuenteSalida(btnLocales);
				btnLocales.setIcon(Estilos.prepararImagenBotonera("/iconos/local.png"));
			}
		});
		panelBotonera.add(Estilos.prepararBotonBotonera(btnLocales, "/iconos/local.png"));

		// Boton que deriva al usuario a la seccion de Reservas
		JButton btnReservas = new JButton("Reservas");
		btnReservas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelPrincipal.removeAll();
				cargarBotonera(panel);
				new PanelReservas(panelPrincipal, "Reservas", s.getReservas());
			}
		});
		// Maneja el hover del boton Reservas
		btnReservas.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				cambiarFuenteEntrada(btnReservas);
				btnReservas.setIcon(Estilos.prepararImagenBotonera("/iconos/calendarioBlanco.png"));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				cambiarFuenteSalida(btnReservas);
				btnReservas.setIcon(Estilos.prepararImagenBotonera("/iconos/calendario.png"));
			}
		});
		panelBotonera.add(Estilos.prepararBotonBotonera(btnReservas, "/iconos/calendario.png"));

		// Boton que deriva al usuario a la seccion de Clientes
		JButton btnClientes = new JButton("Clientes");
		btnClientes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelPrincipal.removeAll();
				cargarBotonera(panel);
				new PanelClientes(panelPrincipal, s, "principal");
			}
		});
		// Maneja el hover del boton Cliente
		btnClientes.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				cambiarFuenteEntrada(btnClientes);
				btnClientes.setIcon(Estilos.prepararImagenBotonera("/iconos/personaBlanco.png"));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				cambiarFuenteSalida(btnClientes);
				btnClientes.setIcon(Estilos.prepararImagenBotonera("/iconos/persona.png"));
			}
		});
		panelBotonera.add(Estilos.prepararBotonBotonera(btnClientes,"/iconos/persona.png"));

		// Boton que deriva al usuario a la seccion de Tickets
		JButton btnTickets = new JButton("Tickets");
		btnTickets.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelPrincipal.removeAll();
				cargarBotonera(panel);
				new PanelTickets(panelPrincipal);
			}
		});
		// Maneja el hover del boton Tickets dependiendo de si sale el raton o no
		btnTickets.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				cambiarFuenteEntrada(btnTickets);
				btnTickets.setIcon(Estilos.prepararImagenBotonera("/iconos/ticketBlanco.png"));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				cambiarFuenteSalida(btnTickets);
				btnTickets.setIcon(Estilos.prepararImagenBotonera("/iconos/ticket.png"));
			}
		});
		panelBotonera.add(Estilos.prepararBotonBotonera(btnTickets,"/iconos/ticket.png"));
		// Creamos el borde 
		Border bordeInferior = BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK);
		btnTickets.setBorder(emptyBorder);
		btnTickets.setBorder(bordeInferior);
		btnTickets.setBorder(BorderFactory.createCompoundBorder(
			    btnTickets.getBorder(), 
			    BorderFactory.createEmptyBorder(0, 20, 0, 0) 
			));
		
		panel.add(panelBotonera);
	}

	/**
	 * Cambia el color y fondo de un boton asociado cuando el raton se le pone por
	 * encima
	 * 
	 * @param boton : JButton
	 */
	private void cambiarFuenteEntrada(JButton boton) {
		boton.setForeground(new Color(255, 255, 255));
		boton.setBackground(Colores.getAZUL_OSCURO());
	}

	/**
	 * Cambia el color y fondo de un boton asociado cuando sale del area de un boton
	 * 
	 * @param boton : JButton
	 */
	private void cambiarFuenteSalida(JButton boton) {
		boton.setForeground(new Color(0, 0, 0));
		boton.setBackground(new Color(173, 219, 245));
	}

	/**
	 * Devuelve la ventana Principal
	 * 
	 * @return
	 */
	public static VentanaPrincipal getVentana() {
		return ventana;
	}

	/**
	 * Devuelve el panel principal
	 * 
	 * @return
	 */
	public JPanel getPanelPrincipal() {
		return panelPrincipal;
	}

}
