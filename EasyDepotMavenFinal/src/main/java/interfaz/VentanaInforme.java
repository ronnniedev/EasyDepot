package interfaz;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import componentes.Button;
import componentes.ButtonGris;
import modelo.Reserva;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.ScrollPaneConstants;

public class VentanaInforme extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;


	/**
	 * Una ventana a traves de la cual se muestra el informe de la incidencia
	 */
	public VentanaInforme(Reserva r) {
		// Establece las dimensiones de la ventana
		super("Informe de reserva: " + r.getIdReserva());
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(550,300, 536, 417);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		// Creamos el scrollPane y el textArea que mostrara el informe en texto plano del informe de la incidencia
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setBounds(10, 74, 502, 246);
		contentPane.add(scrollPane);
		
		JTextArea txtInforme = new JTextArea(r.getDescripcionIncidencia());
		txtInforme.setWrapStyleWord(true);
		txtInforme.setLineWrap(true);
		txtInforme.setFont(new Font("Verdana", Font.PLAIN, 12));
		txtInforme.setBackground(new Color(220, 220, 220));
		txtInforme.setEditable(false);
		scrollPane.setViewportView(txtInforme);
		
		JLabel lblInformeTitulo = new JLabel("Informe de incidencia de la reserva numero " + r.getIdReserva());
		lblInformeTitulo.setFont(new Font("Verdana", Font.BOLD, 14));
		lblInformeTitulo.setBounds(0, 20, 522, 33);
		contentPane.add(lblInformeTitulo);
		
		// Habilita la ventana anterior y cierra esta ventana 
		Button btnSalir = new Button("Cerrar");
		btnSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VentanaPrincipal.getVentana().setEnabled(true);
				dispose();
			}
		});
		btnSalir.setFont(new Font("Verdana", Font.BOLD, 12));
		btnSalir.setBounds(198, 330, 126, 45);
		contentPane.add(btnSalir);
		this.setVisible(true);
	}
}
