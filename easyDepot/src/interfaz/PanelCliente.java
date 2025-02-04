package interfaz;

import java.awt.Color;

import javax.swing.JPanel;

import logica.Sistema;
import modelo.Cliente;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

import componentes.Button;
import componentes.ButtonGris;
import componentes.Colores;
import componentes.PanelDatosRedondeado;
import excepciones.LogicaException;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;

public class PanelCliente extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField txtNombre;
	private JTextField txtApellidos;
	private JTextField txtPassword;
	private JLabel lblReservas;
	private JLabel lblPuntosTienda;
	private JLabel lblCliente;
	private PanelCliente panelCliente;

	/**
	 * Muestra los datos de un cliente, permite la eliminacion de un cliente, las reservas asociaddas y la edicion
	 * del mismo. Tambien muestra una tabla de reservas asociado con el cliente
	 */
	public PanelCliente(JPanel panel,Sistema s,Cliente c) {
		// Establecemos dimensiones del panel
		setBackground(new Color(255, 255, 255));
		this.setBounds(0, 0, 511, 503);
		panel.add(this);
		setLayout(null);
		
		this.panelCliente = this;
		
		PanelDatosRedondeado panelDatos = new PanelDatosRedondeado(30);
		panelDatos.setBounds(42, 90, 426, 293);
		add(panelDatos);
		panelDatos.setLayout(null);
		
		
		JLabel lblNombre = new JLabel("Nombre: ");
		lblNombre.setBounds(41, 29, 123, 40);
		lblNombre.setBackground(new Color(255, 255, 255));
		lblNombre.setFont(new Font("Verdana", Font.BOLD, 16));
		panelDatos.add(lblNombre);
		
		txtNombre = new JTextField();
		txtNombre.setBounds(234, 29, 168, 40);
		txtNombre.setBackground(new Color(255, 255, 255));
		txtNombre.setEditable(false);
		txtNombre.setFont(new Font("Verdana", Font.BOLD, 16));
		panelDatos.add(txtNombre);
		txtNombre.setColumns(10);
		
		JLabel lblApellidos = new JLabel("Apellidos: ");
		lblApellidos.setBounds(41, 98, 147, 27);
		lblApellidos.setBackground(new Color(255, 255, 255));
		lblApellidos.setFont(new Font("Verdana", Font.BOLD, 16));
		panelDatos.add(lblApellidos);
		
		txtApellidos = new JTextField();
		txtApellidos.setBounds(234, 92, 168, 40);
		txtApellidos.setBackground(new Color(255, 255, 255));
		txtApellidos.setEditable(false);
		txtApellidos.setFont(new Font("Verdana", Font.BOLD, 16));
		panelDatos.add(txtApellidos);
		txtApellidos.setColumns(10);
		
		JLabel lblNombre_1_1 = new JLabel("Password: ");
		lblNombre_1_1.setBounds(41, 163, 147, 27);
		lblNombre_1_1.setBackground(new Color(255, 255, 255));
		lblNombre_1_1.setFont(new Font("Verdana", Font.BOLD, 16));
		panelDatos.add(lblNombre_1_1);
		
		txtPassword = new JTextField();
		txtPassword.setBounds(234, 157, 168, 40);
		txtPassword.setBackground(new Color(255, 255, 255));
		txtPassword.setEditable(false);
		txtPassword.setFont(new Font("Verdana", Font.BOLD, 16));
		panelDatos.add(txtPassword);
		txtPassword.setColumns(10);
		
		lblReservas = new JLabel("Reservas realizadas:");
		lblReservas.setBounds(41, 227, 157, 27);
		lblReservas.setBackground(new Color(255, 255, 255));
		lblReservas.setFont(new Font("Verdana", Font.BOLD, 16));
		panelDatos.add(lblReservas);
		
		lblPuntosTienda = new JLabel("Puntos de Tienda:");
		lblPuntosTienda.setBounds(203, 227, 213, 27);
		lblPuntosTienda.setBackground(new Color(255, 255, 255));
		lblPuntosTienda.setFont(new Font("Verdana", Font.BOLD, 16));
		panelDatos.add(lblPuntosTienda);
		
		// Despliega la ventana de edicion a traves de la cual podemos editar el cliente asociado al panel
		Button btnEditar = new Button("Editar");
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// inhabilita la ventana
				VentanaPrincipal.getVentana().setEnabled(false);
				// pasamos el contexto de la ventana para que se actualizen los datos de la aplicacion
				new VentanaEditarCliente(c,panelCliente);
				
			}
		});
		btnEditar.setFont(new Font("Verdana", Font.BOLD, 16));
		btnEditar.setBounds(19, 419, 126, 45);
		add(btnEditar);
		
		/**
		 * Da la opcion de eliminar el cliente asoaciado a este panel del sistema, pregunta si quiere ser eliminado
		 * en caso afirmativo borra el cliente y vuelve al panel de cliente, en caso contrario no pasa nada.
		 */
		ButtonGris btnEliminar = new ButtonGris("Eliminar");
	    btnEliminar.addActionListener(new ActionListener() {
	      public void actionPerformed(ActionEvent e) {
	        int respuesta = 
	            JOptionPane.showConfirmDialog(null,"¿Vas a eliminar este cliente , estas seguro de ello?"
	                ,"Advertencia",JOptionPane.YES_NO_OPTION);
	        if(respuesta == JOptionPane.YES_OPTION) {
	          try {
	            s.eliminarCliente(c.getEmail());
	            removeAll();
	            repaint();
	            revalidate();  
	            add(new PanelClientes(panel,s,"auxiliar"));
	          } catch (LogicaException e1) {
	            JOptionPane.showMessageDialog(null, "Error al borrar","ERROR", JOptionPane.ERROR_MESSAGE);
	          }
	        }
	      }
	    });
	    btnEliminar.setFont(new Font("Verdana", Font.BOLD, 16));
	    btnEliminar.setBounds(192, 419, 126, 45);
	    add(btnEliminar);
		
	    /**
	     * Muestra las reservas asociadas con el cliente en una tabla, abriendo un panel de reservas.
	     */
		Button btnVerReservas = new Button("Ver reservas");
		btnVerReservas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// para cargar una ventana desde la principal
				VentanaPrincipal v = VentanaPrincipal.getVentana();
				v.getPanelPrincipal().removeAll();
				v.cargarBotonera(v.getPanelPrincipal());
				new PanelReservas(v.getPanelPrincipal(),"Reservas de " + c.getEmail(),s.reservasDeCliente(c.getEmail()));
			}
		});
		btnVerReservas.setFont(new Font("Verdana", Font.BOLD, 12));
		btnVerReservas.setBounds(364, 419, 126, 45);
		add(btnVerReservas);
		
		JPanel panelTitulo = new PanelDatosRedondeado(30);
		panelTitulo.setBounds(44, 31, 422, 33);
		panelTitulo.setBackground(Colores.getAZUL_CLARO());
		add(panelTitulo);
		
		// Establecemos la informacion de un cliente y sus label asociados
		lblCliente = new JLabel("Cliente :", SwingConstants.LEFT);
		panelTitulo.add(lblCliente);
		lblCliente.setBackground(new Color(255, 255, 255));
		lblCliente.setFont(new Font("Verdana", Font.BOLD, 18));
		mostrarCliente(c);
	}

	/**
	 * Actualiza los datos de un cliente asociado y los muestra a traves de sus campos asociado en el paneel
	 * @param c : Cliente
	 */
	public void mostrarCliente(Cliente c) {
		lblCliente.setText("Cliente: " + c.getEmail());
		txtNombre.setText(c.getNombre());
		txtApellidos.setText(c.getApellidos());
		txtPassword.setText(c.getPassword());
		lblReservas.setText("Reservas: " + c.getNumeroReservas());
		lblPuntosTienda.setText("Puntos de tienda: " + c.getPuntosTienda());
		
	}
}
