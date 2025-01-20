package pruebas;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import apykeys.Apykeys;
import logica.Sistema;

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.font.TextAttribute;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class VentanaGeneral extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private Sistema s;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Apykeys.setBaseDatosFinal(1);
					new VentanaGeneral("EasyDepot",Sistema.getInstance());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public VentanaGeneral(String titulo, Sistema s) {
		super(titulo);
		this.s = s;
		
		// Creamos un borde vacion
		Border emptyBorder = BorderFactory.createEmptyBorder();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(400, 150, 700, 540);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panelBotonera = new JPanel();
		panelBotonera.setBackground(new Color(173, 219, 245));
		panelBotonera.setBounds(0, 98, 175, 417);
		contentPane.add(panelBotonera);
		panelBotonera.setLayout(new GridLayout(8, 0, 0, 0));
		
		JButton btnLocales = new JButton("Locales");
		btnLocales.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new VentanaLocales("EasyDepot",s);
				dispose();
			}
		});
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
		
		JButton btnInicio = new JButton("Inicio");
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
		
		JButton btnReservas = new JButton("Reservas");
		btnReservas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
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
		
		JButton btnClientes = new JButton("Clientes");
		btnClientes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
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
		
		JButton btnTickets = new JButton("Tickets");
		btnTickets.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
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
		
		JPanel panelAvatar = new JPanel();
		panelAvatar.setBackground(new Color(173, 219, 245));
		panelAvatar.setBounds(0, 0, 175, 100);
		contentPane.add(panelAvatar);
		panelAvatar.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setIcon(new ImageIcon("C:\\Users\\mario\\OneDrive\\Desktop\\EasyDepot\\easyDepot\\img\\adminmod.png"));
		lblNewLabel.setBounds(10, 23, 60, 54);
		panelAvatar.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Ronnie");
		lblNewLabel_1.setFont(new Font("Verdana", Font.BOLD, 14));
		lblNewLabel_1.setBounds(80, 23, 85, 27);
		panelAvatar.add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("Admin");
		lblNewLabel_1_1.setFont(new Font("Verdana", Font.PLAIN, 12));
		lblNewLabel_1_1.setBounds(80, 50, 85, 27);
		panelAvatar.add(lblNewLabel_1_1);
		
		JLabel lblRendimiento = new JLabel("Rendimiento Global",SwingConstants.CENTER);
		lblRendimiento.setFont(new Font("Verdana", Font.BOLD, 24));
		lblRendimiento.setBounds(175, 29, 511, 45);
		contentPane.add(lblRendimiento);
		
		JLabel lbReservas = new JLabel("Reservas: " + s.calcularReservas());
		lbReservas.setFont(new Font("Verdana", Font.BOLD, 16));
		lbReservas.setBounds(228, 98, 310, 45);
		contentPane.add(lbReservas);
		
		JLabel lbIngresos = new JLabel("Ingresos: " + s.calcularIngresos());
		lbIngresos.setFont(new Font("Verdana", Font.BOLD, 16));
		lbIngresos.setBounds(228, 153, 310, 45);
		contentPane.add(lbIngresos);
		
		JLabel lbLocales = new JLabel("Consignas: " + s.calcularLocales());
		lbLocales.setFont(new Font("Verdana", Font.BOLD, 16));
		lbLocales.setBounds(228, 208, 310, 45);
		contentPane.add(lbLocales);
		
		JLabel lbClientes = new JLabel("Clientes: " + s.calcularClientes());
		lbClientes.setFont(new Font("Verdana", Font.BOLD, 16));
		lbClientes.setBounds(228, 263, 310, 45);
		contentPane.add(lbClientes);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(255, 255, 255));
		panel.setBounds(175, 0, 511, 503);
		contentPane.add(panel);
		this.setVisible(true);
	}
	
	private void cambiarFuenteEntrada(JButton boton) {
		boton.setForeground(new Color(255, 255, 255));
		boton.setBackground(new Color(0, 0, 160));
	}
	
	private void cambiarFuenteSalida(JButton boton) {
		boton.setForeground(new Color(0, 0, 0));
		boton.setBackground(new Color(173, 219, 245));
	}
}
