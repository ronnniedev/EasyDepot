package interfaz;

import java.awt.Color;

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
import modelo.Local;

import java.awt.GridLayout;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;
import java.awt.event.ActionEvent;

public class VentanaEditarLocal extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private Sistema s;
	private JTextField txtDireccion;
	private JTextField txtCoordenadas;
	private JLabel lblError;

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
		JPanel panelDatos = new PanelDatosRedondeado(30);
		panelDatos.setBounds(45, 50, 293, 142);
		contentPane.add(panelDatos);
		panelDatos.setLayout(null);
		
		JLabel lblDireccion = new JLabel("Direccion: ");
		lblDireccion.setBounds(10, 15, 119, 38);
		lblDireccion.setFont(new Font("Verdana", Font.BOLD, 14));
		panelDatos.add(lblDireccion);
		
		txtDireccion = new JTextField(l.getDireccion());
		txtDireccion.setBounds(119, 16, 164, 38);
		txtDireccion.setFont(new Font("Verdana", Font.PLAIN, 12));
		txtDireccion.setColumns(10);
		panelDatos.add(txtDireccion);
		
		JLabel lblCoordenadas = new JLabel("Coordenadas: ");
		lblCoordenadas.setBounds(10, 71, 141, 38);
		lblCoordenadas.setBackground(new Color(255, 255, 255));
		lblCoordenadas.setFont(new Font("Verdana", Font.BOLD, 14));
		panelDatos.add(lblCoordenadas);
		
		txtCoordenadas = new JTextField(l.getCoordenadas());
		txtCoordenadas.setBounds(119, 72, 164, 38);
		txtCoordenadas.setFont(new Font("Verdana", Font.PLAIN, 12));
		txtCoordenadas.setColumns(10);
		panelDatos.add(txtCoordenadas);
		
		// Guarda los datos con el texto introducido en los campos , vuelve a la ventana anterior y activar la ventana
		// del local actualizando lso datos del mismo
		Button btnGuardar = new Button("Guardar");
		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				List <String> entradasTexto = new LinkedList <String>(); 
				String direccion = txtDireccion.getText();
				String coordenadas = txtCoordenadas.getText();
				entradasTexto.add(direccion);
				entradasTexto.add(coordenadas);
				try {
					GestorComprobaciones.comprobarTextoVacio(entradasTexto);
					// actualizamos en sistema
					
					l.setDireccion(direccion);
					l.setCoordenadas(coordenadas);
					//Actualizamos en base de datos
					s.actualizarLocal(l);
					VentanaPrincipal.getVentana().setEnabled(true);
					panelLocal.mostrarLocal(l);
					dispose();
				} catch (LogicaException e1) {
					lblError.setText(e1.getMessage());
				}
				
			}
		});
		btnGuardar.setFont(new Font("Verdana", Font.BOLD, 12));
		btnGuardar.setBounds(41, 198, 126, 45);
		contentPane.add(btnGuardar);
		
		// Habilita la ventana anterior y cierra esta
		ButtonGris btnCancelar = new ButtonGris("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VentanaPrincipal.getVentana().setEnabled(true);
				dispose();
			}
		});
		btnCancelar.setFont(new Font("Verdana", Font.BOLD, 12));
		btnCancelar.setBounds(212, 198, 126, 45);
		contentPane.add(btnCancelar);
		
		JPanel panelTitulo = new PanelDatosRedondeado(30);
		panelTitulo.setBounds(130, 5, 119, 25);
		panelTitulo.setBackground(Colores.getAZUL_CLARO());
		contentPane.add(panelTitulo);
		
		JLabel lblTitulo = new JLabel("Editar local");
		panelTitulo.add(lblTitulo);
		lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitulo.setFont(new Font("Verdana", Font.BOLD, 16));
		
		lblError = new JLabel("");
		lblError.setHorizontalAlignment(SwingConstants.CENTER);
		lblError.setFont(new Font("Verdana", Font.BOLD, 10));
		lblError.setBounds(0, 34, 380, 13);
		contentPane.add(lblError);
		this.setVisible(true);
	}
}
