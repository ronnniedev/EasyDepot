package interfaz;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import apykeys.Apykeys;
import excepciones.LogicaException;
import excepciones.PersistenciaException;
import logica.Sistema;

import javax.swing.JButton;
import java.awt.Font;
import java.awt.Frame;
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
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Apykeys.setBaseDatosFinal(1);
					VentanaPrincipal frame = new VentanaPrincipal("EasyDepot 0.49");
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Ventana principal del sistema, desde aqui cargaremos los diferentes paneles que definiran el funcionamiento de 
	 * la app
	 * @throws LogicaException 
	 * @throws SQLException 
	 * @throws PersistenciaException 
	 */
	public VentanaPrincipal(String titulo)throws PersistenciaException, SQLException, LogicaException {
		super(titulo);
		setResizable(false);
		this.ventana = this;
		this.s = Sistema.getInstance();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(400, 150, 700, 540);
		panelPrincipal = new JPanel();
		panelPrincipal.setBackground(new Color(255, 255, 255));
		panelPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(panelPrincipal);
		panelPrincipal.setLayout(null);
		
		JButton btnContacto = new JButton("Contacto");
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
		btnContacto.setBounds(369, 395, 129, 54);
		panelPrincipal.add(btnContacto);
		
		textUser = new JTextField();
		textUser.setFont(new Font("Verdana", Font.PLAIN, 16));
		textUser.setColumns(10);
		textUser.setBounds(193, 232, 305, 54);
		panelPrincipal.add(textUser);
		
		passwordField = new JPasswordField();
		passwordField.setFont(new Font("Verdana", Font.PLAIN, 16));
		passwordField.setToolTipText("password");
		passwordField.setBounds(193, 309, 305, 54);
		panelPrincipal.add(passwordField);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(227, 245, 5, 5);
		panelPrincipal.add(tabbedPane);
		
		JLabel lbLogo = new JLabel();
		lbLogo.setIcon(new ImageIcon(VentanaPrincipal.class.getResource("/iconos/logoLogin.png")));
		lbLogo.setBounds(124, 32, 451, 122);
		panelPrincipal.add(lbLogo);
		
		JLabel lblErrorLogin = new JLabel("Clave o usuario incorrecto, introduzcalo de nuevo.");
		lblErrorLogin.setForeground(new Color(255, 0, 0));
		lblErrorLogin.setFont(new Font("Verdana", Font.BOLD, 16));
		lblErrorLogin.setBounds(124, 176, 510, 46);
		panelPrincipal.add(lblErrorLogin);
		lblErrorLogin.setVisible(false);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String user = textUser.getText();
				String password = String.valueOf(passwordField.getPassword());
				try {
					s.loginDesktop(user, password);
					panelPrincipal.removeAll();
					cargarBotonera(panelPrincipal);
					new PanelInicio(panelPrincipal,s);
				} catch (LogicaException e1) {
					lblErrorLogin.setVisible(true);
				}
			}
		});
		btnLogin.setFont(new Font("Verdana", Font.BOLD, 16));
		btnLogin.setBounds(193, 395, 129, 54);
		panelPrincipal.add(btnLogin);
		
	}
	
	/**
	 * Carga la botonera una vez se ha realizado el logeo con exito, tiene la configuracion de los 5 botones principales
	 * @param panel
	 */
	public void cargarBotonera(JPanel panel) {
		panel.setBackground(new Color(255, 255, 255));
		panel.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		// Creamos un borde vacion
		Border emptyBorder = BorderFactory.createEmptyBorder();

		JPanel panelAvatar = new JPanel();
		panelAvatar.setBackground(new Color(173, 219, 245));
		panelAvatar.setBounds(0, 0, 175, 100);
		panel.add(panelAvatar);
		panelAvatar.setLayout(null);
		
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
		
		
		// Boton que deriva al usuario a la seccion de locales
		JButton btnLocales = new JButton("Locales");
		btnLocales.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelPrincipal.removeAll();
				cargarBotonera(panel);
				new PanelLocales(panelPrincipal,s);
			}
		});
		// Maneja el hover del boton Local
		btnLocales.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				cambiarFuenteEntrada(btnLocales);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				cambiarFuenteSalida(btnLocales);
			}
		});
		btnLocales.setBackground(new Color(173, 219, 245));
		btnLocales.setFont(new Font("Verdana", Font.BOLD, 16));
		btnLocales.isBorderPainted();
		panelBotonera.add(btnLocales);
		
		
		// Boton que deriva al usuario a la seccion de Inicio
		JButton btnInicio = new JButton("Inicio");
		btnInicio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelPrincipal.removeAll();
				cargarBotonera(panel);
				new PanelInicio(panelPrincipal,s);
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
		btnInicio.setBorder(emptyBorder);
		panelBotonera.add(btnInicio);
		
		btnLocales.setBorder(emptyBorder);
		panelBotonera.add(btnLocales);
		
		
		// Boton que deriva al usuario a la seccion de Reservas
		JButton btnReservas = new JButton("Reservas");
		btnReservas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelPrincipal.removeAll();
				cargarBotonera(panel);
				new PanelReservas(panelPrincipal,"Locales",s.getReservas());
			}
		});
		// Maneja el hover del boton Reservas
		btnReservas.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				cambiarFuenteEntrada(btnReservas);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				cambiarFuenteSalida(btnReservas);
			}
		});
		btnReservas.setBackground(new Color(173, 219, 245));
		btnReservas.setFont(new Font("Verdana", Font.BOLD, 16));
		btnReservas.setBorder(emptyBorder);
		panelBotonera.add(btnReservas);
		
		
		// Boton que deriva al usuario a la seccion de Clientes
		JButton btnClientes = new JButton("Clientes");
		btnClientes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelPrincipal.removeAll();
				cargarBotonera(panel);
				new PanelClientes(panelPrincipal,s,"principal");
			}
		});
		// Maneja el hover del boton Cliente
		btnClientes.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				cambiarFuenteEntrada(btnClientes);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				cambiarFuenteSalida(btnClientes);
			}
		});
		btnClientes.setForeground(new Color(0, 0, 0));
		btnClientes.setBackground(new Color(173, 219, 245));
		btnClientes.setFont(new Font("Verdana", Font.BOLD, 16));
		btnClientes.setBorder(emptyBorder);
		panelBotonera.add(btnClientes);
		
		
		// Boton que deriva al usuario a la seccion de Tickets
		JButton btnTickets = new JButton("Tickets");
		btnTickets.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelPrincipal.removeAll();
				cargarBotonera(panel);
				new PanelTickets(panelPrincipal);
			}
		});
		// Maneja el hover del boton Tickets
		btnTickets.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				cambiarFuenteEntrada(btnTickets);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				cambiarFuenteSalida(btnTickets);
			}
		});
		btnTickets.setBackground(new Color(173, 219, 245));
		btnTickets.setFont(new Font("Verdana", Font.BOLD, 16));
		btnTickets.setBorder(emptyBorder);
		panelBotonera.add(btnTickets);
		panel.add(panelBotonera);
	}
	
	/**
	 * Cambia el color y fondo de un boton asociado cuando el raton se le pone por encima
	 * @param boton : JButton
	 */
	private void cambiarFuenteEntrada(JButton boton) {
		boton.setForeground(new Color(255, 255, 255));
		boton.setBackground(new Color(0, 0, 160));
	}
	
	/**
	 * Cambia el color y fondo de un boton asociado cuando sale del area de un boton
	 * @param boton : JButton
	 */
	private void cambiarFuenteSalida(JButton boton) {
		boton.setForeground(new Color(0, 0, 0));
		boton.setBackground(new Color(173, 219, 245));
	}

	public static VentanaPrincipal getVentana() {
		return ventana;
	}

	public JPanel getPanelPrincipal() {
		return panelPrincipal;
	}

	

	
	
}
