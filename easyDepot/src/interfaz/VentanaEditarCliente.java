package interfaz;


import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import componentes.Button;
import componentes.ButtonGris;
import componentes.Colores;
import componentes.PanelDatosRedondeado;
import excepciones.LogicaException;
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
	private Sistema s;
	private JLabel lblError;

	/**
	 * Esta ventana administra lso diferentes campos que se usaran para editar los datos de un cliente
	 */
	public VentanaEditarCliente(Cliente c,PanelCliente panelCliente) {
		// Establecemos las dimensiones de la ventana
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(550, 280, 394, 333);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		try {
			s = Sistema.getInstance();
		} catch (Exception e) {
			
		} 
		
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new PanelDatosRedondeado(30);
		panel.setBounds(53, 62, 291, 172);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblNombre = new JLabel("Nombre: ");
		lblNombre.setBounds(20, 17, 93, 26);
		lblNombre.setFont(new Font("Verdana", Font.BOLD, 14));
		panel.add(lblNombre);
		
		txtNombre = new JTextField(c.getNombre());
		txtNombre.setBounds(125, 18, 145, 26);
		txtNombre.setFont(new Font("Verdana", Font.PLAIN, 12));
		panel.add(txtNombre);
		txtNombre.setColumns(10);
		
		JLabel lblApellidos = new JLabel("Apellidos:");
		lblApellidos.setBounds(20, 54, 93, 26);
		lblApellidos.setFont(new Font("Verdana", Font.BOLD, 14));
		panel.add(lblApellidos);
		
		txtApellidos = new JTextField(c.getApellidos());
		txtApellidos.setBounds(125, 54, 145, 26);
		txtApellidos.setFont(new Font("Verdana", Font.PLAIN, 12));
		txtApellidos.setHorizontalAlignment(SwingConstants.LEFT);
		panel.add(txtApellidos);
		txtApellidos.setColumns(10);
		
		JLabel lblEmail = new JLabel("Email:");
		lblEmail.setBounds(20, 90, 93, 26);
		lblEmail.setFont(new Font("Verdana", Font.BOLD, 14));
		panel.add(lblEmail);
		
		txtEmail = new JTextField(c.getEmail());
		txtEmail.setBounds(125, 91, 145, 26);
		txtEmail.setFont(new Font("Verdana", Font.PLAIN, 12));
		panel.add(txtEmail);
		txtEmail.setColumns(10);
		
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setBounds(20, 126, 93, 36);
		lblPassword.setFont(new Font("Verdana", Font.BOLD, 14));
		panel.add(lblPassword);
		
		txtPassword = new JTextField(c.getPassword());
		txtPassword.setBounds(125, 132, 145, 26);
		txtPassword.setFont(new Font("Verdana", Font.PLAIN, 12));
		panel.add(txtPassword);
		txtPassword.setColumns(10);
		
		// Boton que al darle actualiza el objeto del cliente con la informacion introducida en lso contenedores
		// Comprueba que estan bien escritos y guarda el objeto en el sistema
		Button btnGuardar = new Button("Guardar");
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
						c.setEmail(email);
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
		
		// Vuelve al panel anterior, siendo esta el panel del cliente, activamos la ventana del cliente y cerramos
		// esta
		ButtonGris btnCancelar = new ButtonGris("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VentanaPrincipal.getVentana().setEnabled(true);
				dispose();
			}
		});
		btnCancelar.setFont(new Font("Verdana", Font.BOLD, 16));
		btnCancelar.setBounds(218, 244, 126, 45);
		contentPane.add(btnCancelar);
		
		JPanel panelTitulo = new PanelDatosRedondeado(30);
		panelTitulo.setBounds(115, 6, 150, 36);
		panelTitulo.setBackground(Colores.getAZUL_CLARO());
		contentPane.add(panelTitulo);
		
		// Establece los contenedores que muestran la informacion del cliente a editar
		JLabel lblNewLabel = new JLabel("Editar Cliente");
		panelTitulo.add(lblNewLabel);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Verdana", Font.BOLD, 16));
		
		lblError = new JLabel("");
		lblError.setHorizontalAlignment(SwingConstants.CENTER);
		lblError.setForeground(Color.RED);
		lblError.setFont(new Font("Verdana", Font.PLAIN, 12));
		lblError.setBounds(0, 41, 380, 22);
		contentPane.add(lblError);
		this.setVisible(true);
	}
}
