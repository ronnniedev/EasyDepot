package interfaz;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import logica.Sistema;
import modelo.Local;

import java.awt.GridLayout;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class VentanaEditarLocal extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private Sistema s;
	private JTextField txtDireccion;
	private JTextField txtCoordenadas;

	/**
	 * Una ventana que permite editar la informacion de un local determinado.
	 */
	public VentanaEditarLocal(Local l,PanelLocal panelLocal) {
		// Establecemos las dimensiones de la ventana
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(600, 300, 394, 290);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		try {
			s = Sistema.getInstance();
		} catch (Exception e) {
			
		} 
		
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		// Establecemos el panel que contiene los componentes donde se mostrara la informacion a editar del local
		JPanel panel = new JPanel();
		panel.setBackground(new Color(255, 255, 255));
		panel.setBounds(0, 46, 380, 142);
		contentPane.add(panel);
		panel.setLayout(new GridLayout(2, 2, 0, 0));
		
		JLabel lblDireccion = new JLabel("Direccion: ");
		lblDireccion.setFont(new Font("Verdana", Font.BOLD, 14));
		panel.add(lblDireccion);
		
		txtDireccion = new JTextField(l.getDireccion());
		txtDireccion.setFont(new Font("Verdana", Font.PLAIN, 12));
		txtDireccion.setColumns(10);
		panel.add(txtDireccion);
		
		JLabel lblCoordenadas = new JLabel("Coordenadas: ");
		lblCoordenadas.setBackground(new Color(255, 255, 255));
		lblCoordenadas.setFont(new Font("Verdana", Font.BOLD, 14));
		panel.add(lblCoordenadas);
		
		txtCoordenadas = new JTextField(l.getCoordenadas());
		txtCoordenadas.setFont(new Font("Verdana", Font.PLAIN, 12));
		txtCoordenadas.setColumns(10);
		panel.add(txtCoordenadas);
		
		JLabel lblNewLabel = new JLabel("Editar Cliente");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Verdana", Font.BOLD, 16));
		lblNewLabel.setBounds(0, 0, 380, 36);
		contentPane.add(lblNewLabel);
		
		// Guarda los datos con el texto introducido en los campos , vuelve a la ventana anterior y activar la ventana
		// del local actualizando lso datos del mismo
		JButton btnGuardar = new JButton("Guardar");
		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// actualizamos en sistema
				l.setDireccion(txtDireccion.getText());
				l.setCoordenadas(txtCoordenadas.getText());
				//Actualizamos en base de datos
				s.actualizarLocal(l);
				VentanaPrincipal.getVentana().setEnabled(true);
				panelLocal.mostrarLocal(l);
				dispose();
			}
		});
		btnGuardar.setFont(new Font("Verdana", Font.BOLD, 12));
		btnGuardar.setBounds(41, 198, 126, 45);
		contentPane.add(btnGuardar);
		
		// Habilita la ventana anterior y cierra esta
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VentanaPrincipal.getVentana().setEnabled(true);
				dispose();
			}
		});
		btnCancelar.setFont(new Font("Verdana", Font.BOLD, 12));
		btnCancelar.setBounds(212, 198, 126, 45);
		contentPane.add(btnCancelar);
		this.setVisible(true);
	}
}
