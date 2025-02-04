package pruebas;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import apykeys.Apykeys;
import logica.Sistema;
import modelo.Reserva;

public class VentanaReservasAntigua extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private Sistema s;
	private JTable table_1;
	private JTextField buscador;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Apykeys.setBaseDatosFinal(1);
					new VentanaReservasAntigua("EasyDepot",Sistema.getInstance());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public VentanaReservasAntigua(String titulo,Sistema s) {
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
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(173, 219, 245));
		panel.setBounds(0, 98, 175, 417);
		contentPane.add(panel);
		panel.setLayout(new GridLayout(8, 0, 0, 0));
		
		JButton btnInicio = new JButton("Inicio");
		btnInicio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				new VentanaGeneral("EasyDepot",s);
			}
		});
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
		panel.add(btnInicio);
		
		JButton btnLocales = new JButton("Locales");
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
		btnLocales.setBorder(emptyBorder);
		panel.add(btnLocales);
		
		JButton btnReservas = new JButton("Reservas");
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
		panel.add(btnReservas);
		
		JButton btnClientes = new JButton("Clientes");
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
		panel.add(btnClientes);
		
		JButton btnTickets = new JButton("Tickets");
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
		panel.add(btnTickets);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(173, 219, 245));
		panel_1.setBounds(0, 0, 175, 100);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setIcon(new ImageIcon("C:\\Users\\mario\\OneDrive\\Desktop\\EasyDepot\\easyDepot\\img\\adminmod.png"));
		lblNewLabel.setBounds(10, 23, 60, 54);
		panel_1.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Ronnie");
		lblNewLabel_1.setFont(new Font("Verdana", Font.BOLD, 14));
		lblNewLabel_1.setBounds(80, 23, 85, 27);
		panel_1.add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("Admin");
		lblNewLabel_1_1.setFont(new Font("Verdana", Font.PLAIN, 12));
		lblNewLabel_1_1.setBounds(80, 50, 85, 27);
		panel_1.add(lblNewLabel_1_1);
		
		JLabel lblRendimiento = new JLabel("Locales",SwingConstants.CENTER);
		lblRendimiento.setFont(new Font("Verdana", Font.BOLD, 24));
		lblRendimiento.setBounds(175, 29, 511, 45);
		contentPane.add(lblRendimiento);
		
		String [] cabecera = {"Id","Cliente email","Cabina","Fecha inicio","Fecha Salida","Incidencia"};
		List <String[]> datosLista = extraerReservas();
		String [][] datos = datosLista.toArray(new String[0][0]);
		
		table_1 = new JTable(datos,cabecera);
		table_1.setBorder(new LineBorder(new Color(0, 0, 0)));
		table_1.setBackground(new Color(255, 255, 255)); 
		table_1.setBounds(185, 119, 491, 311);
		
		JScrollPane scrollPane = new JScrollPane(table_1);
		scrollPane.setBounds(185, 119, 491, 311);
		contentPane.add(scrollPane);
		
		buscador = new JTextField();
		buscador.setBounds(551, 84, 125, 16);
		contentPane.add(buscador);
		buscador.setColumns(10);
	
		setContentPane(contentPane);
		
		this.setVisible(true);
	}
	
	/**
	 * Extrae en un arrayList un vector de String con los datos en crudo de todas as reservas alojados en el sistema
	 * @return List <String[]>
	 */
	private List<String[]> extraerReservas() {
		List <String[]> datos = new ArrayList<String[]>();
		
		for(Reserva r: s.getReservas()) {
			datos.add(new String[]{r.getIdReserva() + "",r.getEmailCliente(),r.getIdCabina(),r.getFechaInicio().toString()
					,comprobarFechaSalida(r),r.isIncidencia() + ""});
		}
		
		return datos;
	}
	
	private String comprobarFechaSalida(Reserva r) {
		if(r.getFechaSalida() == null) {
			return "Sin fecha";
		}
		
		return r.getFechaSalida().toString();
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
