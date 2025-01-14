package interfaz;

import java.awt.Color;

import javax.swing.JPanel;

import logica.Sistema;
import modelo.Cliente;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

import excepciones.LogicaException;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;
import javax.swing.JButton;

public class PanelCliente extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField txtNombre;
	private JTextField txtApellidos;
	private JTextField txtPassword;
	private JLabel lblReservas;
	private JLabel lblPuntosTienda;
	private JLabel lblCliente;

	/**
	 * Create the panel.
	 */
	public PanelCliente(JPanel panel,Sistema s,Cliente c) {
		setBackground(new Color(255, 255, 255));
		this.setBounds(0, 0, 511, 503);
		panel.add(this);
		setLayout(null);
		
		lblCliente = new JLabel("Cliente :", SwingConstants.LEFT);
		lblCliente.setBackground(new Color(255, 255, 255));
		lblCliente.setFont(new Font("Verdana", Font.BOLD, 18));
		lblCliente.setBounds(0, 35, 511, 45);
		add(lblCliente);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(0, 90, 511, 293);
		panel_1.setBackground(new Color(255,255,255));
		add(panel_1);
		panel_1.setLayout(new GridLayout(4, 2, 0, 0));
		
		JLabel lblNombre = new JLabel("Nombre: ");
		lblNombre.setBackground(new Color(255, 255, 255));
		lblNombre.setFont(new Font("Verdana", Font.BOLD, 16));
		panel_1.add(lblNombre);
		
		txtNombre = new JTextField();
		txtNombre.setBackground(new Color(255, 255, 255));
		txtNombre.setEditable(false);
		txtNombre.setFont(new Font("Verdana", Font.BOLD, 16));
		panel_1.add(txtNombre);
		txtNombre.setColumns(10);
		
		JLabel lblApellidos = new JLabel("Apellidos: ");
		lblApellidos.setBackground(new Color(255, 255, 255));
		lblApellidos.setFont(new Font("Verdana", Font.BOLD, 16));
		panel_1.add(lblApellidos);
		
		txtApellidos = new JTextField();
		txtApellidos.setBackground(new Color(255, 255, 255));
		txtApellidos.setEditable(false);
		txtApellidos.setFont(new Font("Verdana", Font.BOLD, 16));
		panel_1.add(txtApellidos);
		txtApellidos.setColumns(10);
		
		JLabel lblNombre_1_1 = new JLabel("Password: ");
		lblNombre_1_1.setBackground(new Color(255, 255, 255));
		lblNombre_1_1.setFont(new Font("Verdana", Font.BOLD, 16));
		panel_1.add(lblNombre_1_1);
		
		txtPassword = new JTextField();
		txtPassword.setBackground(new Color(255, 255, 255));
		txtPassword.setEditable(false);
		txtPassword.setFont(new Font("Verdana", Font.BOLD, 16));
		panel_1.add(txtPassword);
		txtPassword.setColumns(10);
		
		lblReservas = new JLabel("Reservas realizadas:");
		lblReservas.setBackground(new Color(255, 255, 255));
		lblReservas.setFont(new Font("Verdana", Font.BOLD, 16));
		panel_1.add(lblReservas);
		
		lblPuntosTienda = new JLabel("Puntos de Tienda:");
		lblPuntosTienda.setBackground(new Color(255, 255, 255));
		lblPuntosTienda.setFont(new Font("Verdana", Font.BOLD, 16));
		panel_1.add(lblPuntosTienda);
		
		JButton btnEditar = new JButton("Editar");
		btnEditar.setFont(new Font("Verdana", Font.BOLD, 16));
		btnEditar.setBounds(19, 419, 126, 45);
		add(btnEditar);
		
		JButton btnEliminar = new JButton("Eliminar");
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
	            new PanelClientes(panel,s);
	          } catch (LogicaException e1) {
	            JOptionPane.showMessageDialog(null, "Error al borrar","ERROR", JOptionPane.ERROR_MESSAGE);
	          }
	        }
	      }
	    });
	    btnEliminar.setFont(new Font("Verdana", Font.BOLD, 16));
	    btnEliminar.setBounds(192, 419, 126, 45);
	    add(btnEliminar);
		
		JButton btnVerReservas = new JButton("Ver reservas");
		btnVerReservas.setFont(new Font("Verdana", Font.BOLD, 12));
		btnVerReservas.setBounds(364, 419, 126, 45);
		add(btnVerReservas);
		mostrarCliente(c);
	}

	private void mostrarCliente(Cliente c) {
		lblCliente.setText("Cliente: " + c.getEmail());
		txtNombre.setText(c.getNombre());
		txtApellidos.setText(c.getApellidos());
		txtPassword.setText(c.getPassword());
		lblReservas.setText("Reservas: " + c.getNumeroReservas());
		lblPuntosTienda.setText("Puntos de tienda: " + c.getPuntosTienda());
		
	}
}
