package interfaz;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import excepciones.LogicaException;
import excepciones.PersistenciaException;
import logica.GestorComprobaciones;
import logica.Sistema;
import modelo.Cliente;

import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import java.awt.GridLayout;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;

public class VentanaEditarCliente extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtNombre;
	private JTextField txtApellidos;
	private JTextField txtEmail;
	private JTextField txtPassword;
	private JLabel lblError;
	private Sistema s;

	/**
	 * Create the frame.
	 */
	public VentanaEditarCliente(Cliente c,PanelCliente panelCliente) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 394, 333);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		try {
			s = Sistema.getInstance();
		} catch (Exception e) {
			
		} 
		
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Editar Cliente");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Verdana", Font.BOLD, 16));
		lblNewLabel.setBounds(0, 0, 389, 36);
		contentPane.add(lblNewLabel);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(255, 255, 255));
		panel.setBounds(0, 52, 389, 182);
		contentPane.add(panel);
		panel.setLayout(new GridLayout(4, 2, 0, 0));
		
		JLabel lblNombre = new JLabel("Nombre: ");
		lblNombre.setFont(new Font("Verdana", Font.BOLD, 14));
		panel.add(lblNombre);
		
		txtNombre = new JTextField(c.getNombre());
		txtNombre.setFont(new Font("Verdana", Font.PLAIN, 12));
		panel.add(txtNombre);
		txtNombre.setColumns(10);
		
		JLabel lblApellidos = new JLabel("Apellidos:");
		lblApellidos.setFont(new Font("Verdana", Font.BOLD, 14));
		panel.add(lblApellidos);
		
		txtApellidos = new JTextField(c.getApellidos());
		txtApellidos.setFont(new Font("Verdana", Font.PLAIN, 12));
		txtApellidos.setHorizontalAlignment(SwingConstants.LEFT);
		panel.add(txtApellidos);
		txtApellidos.setColumns(10);
		
		JLabel lblEmail = new JLabel("Email:");
		lblEmail.setFont(new Font("Verdana", Font.BOLD, 14));
		panel.add(lblEmail);
		
		txtEmail = new JTextField(c.getEmail());
		txtEmail.setFont(new Font("Verdana", Font.PLAIN, 12));
		panel.add(txtEmail);
		txtEmail.setColumns(10);
		
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setFont(new Font("Verdana", Font.BOLD, 14));
		panel.add(lblPassword);
		
		txtPassword = new JTextField(c.getPassword());
		txtPassword.setFont(new Font("Verdana", Font.PLAIN, 12));
		panel.add(txtPassword);
		txtPassword.setColumns(10);
		
		JButton btnGuardar = new JButton("Guardar");
		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					// Declaracion de String
					String nombre = txtNombre.getText();
					String apellidos =txtApellidos.getText();
					String password = txtPassword.getText();
					String email = txtEmail.getText();
					
					// Comprobaciones de campos
					GestorComprobaciones.comprobarEmail(txtEmail.getText());
					List <String> palabras = new ArrayList<String>();
					palabras.add(nombre);
					palabras.add(apellidos);
					palabras.add(password);
					GestorComprobaciones.comprobarTextoVacio(palabras);
					
					// actualizacion de campos
					c.setNombre(nombre);
					c.setApellidos(apellidos);
					c.setPassword(password);
					
					//actualizamos datos en base de datos
					s.actualizarCliente(c);
					Cliente cMostrar = c;
					
					if(c.getEmail().compareTo(email) != 0) {
						cMostrar = s.cambiarEmail(c.getEmail(), email);
					}
				
					panelCliente.mostrarCliente(cMostrar);
					VentanaPrincipal.getVentana().setEnabled(true);
					dispose();
				} catch (LogicaException e1) {
					lblError.setText(e1.getMessage());
				}
				
			}
		});
		btnGuardar.setFont(new Font("Verdana", Font.BOLD, 16));
		btnGuardar.setBounds(49, 244, 126, 45);
		contentPane.add(btnGuardar);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VentanaPrincipal.getVentana().setEnabled(true);
				dispose();
			}
		});
		btnCancelar.setFont(new Font("Verdana", Font.BOLD, 16));
		btnCancelar.setBounds(218, 244, 126, 45);
		contentPane.add(btnCancelar);
		
		lblError = new JLabel("");
		lblError.setFont(new Font("Verdana", Font.PLAIN, 12));
		lblError.setForeground(new Color(255, 0, 0));
		lblError.setBounds(0, 32, 370, 21);
		contentPane.add(lblError);
		this.setVisible(true);
	}
}
