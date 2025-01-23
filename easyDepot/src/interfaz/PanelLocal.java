package interfaz;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import logica.Sistema;
import modelo.Local;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class PanelLocal extends JPanel {

	private static final long serialVersionUID = 1L;
	private PanelLocal panelLocal;
	private JLabel lblReserva;
	private JTextField txtDireccion;
	private JTextField txtCoordenadas;
	private JLabel lblDireccion;
	private JLabel lblCoordenadas;
	private JLabel lblIngresos;
	private JLabel lblReservas;
	private JButton btnVolver;
	private JButton btnVerArticulos;

	/**
	 * Muestra la informacion de un local y permite su edicion, ver las reservas del mismo y sus articulos
	 */
	public PanelLocal(JPanel panel, Sistema s, Local l) {
		// Establecemos las dimensiones del panel
		setBackground(new Color(255, 255, 255));
		this.setBounds(0, 0, 511, 503);
		panel.add(this);
		setLayout(null);
		
		this.panelLocal = this;

		// Establecemos el titulo del panel
		lblReserva = new JLabel("Id de local:", SwingConstants.LEFT);
		lblReserva.setBackground(new Color(255, 255, 255));
		lblReserva.setFont(new Font("Verdana", Font.BOLD, 18));
		lblReserva.setBounds(0, 35, 511, 45);
		add(lblReserva);

		// Panel que contiene todos los textfields y labels de la reserva asociada al panel
		JPanel panelDatos = new JPanel();
		panelDatos.setBounds(0, 90, 511, 295);
		panelDatos.setBackground(new Color(255, 255, 255));
		add(panelDatos);
		panelDatos.setLayout(new GridLayout(3, 2, 0, 0));

		
		// Instanciamos los componentes
		lblDireccion = new JLabel("Direccion : ");
		lblDireccion.setFont(new Font("Verdana", Font.BOLD, 16));
		panelDatos.add(lblDireccion);

		txtDireccion = new JTextField();
		txtDireccion.setBackground(new Color(255, 255, 255));
		txtDireccion.setEditable(false);
		txtDireccion.setFont(new Font("Verdana", Font.BOLD, 16));
		panelDatos.add(txtDireccion);
		txtDireccion.setColumns(10);

		lblCoordenadas = new JLabel("Coordenadas : ");
		lblCoordenadas.setFont(new Font("Verdana", Font.BOLD, 16));
		panelDatos.add(lblCoordenadas);

		txtCoordenadas = new JTextField();
		txtCoordenadas.setBackground(new Color(255, 255, 255));
		txtCoordenadas.setEditable(false);
		txtCoordenadas.setFont(new Font("Verdana", Font.BOLD, 16));
		txtCoordenadas.setColumns(10);
		panelDatos.add(txtCoordenadas);

		lblIngresos = new JLabel("Ingresos : ");
		lblIngresos.setFont(new Font("Verdana", Font.BOLD, 16));
		panelDatos.add(lblIngresos);

		lblReservas = new JLabel("Reservas : ");
		lblReservas.setFont(new Font("Verdana", Font.BOLD, 16));
		panelDatos.add(lblReservas);

		// Abre el panel de las cabinas mostrando todas las cabinas asoaciadas a un local
		JButton btnCabinas = new JButton("Ver cabinas");
		btnCabinas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// refresca la pantalla y crea un nuevo panel para generar los datos del cliente nuevo
				removeAll();
				repaint();
				revalidate();  
				add(new PanelCabinas(l,panel));
				
			}
		});
		btnCabinas.setFont(new Font("Verdana", Font.BOLD, 12));
		btnCabinas.setBounds(34, 395, 126, 45);
		add(btnCabinas);

		/*
		 * Abre un panel mostrando un carrusel con los artciulos asoaciado a un local concreot, en caso de que el local
		 * no tenga articulos este pregunta al usuario si quiere crear uno, en caso contrario solo abre el panel
		 * mostrando los articulos
		 */
		btnVerArticulos = new JButton("Ver articulos");
		btnVerArticulos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!comprobarContenido(l)) {
					int respuesta = 
				            JOptionPane.showConfirmDialog(null,"No hay articulos en este local ¿Quieres crear uno nuevo?"
				                ,"Advertencia",JOptionPane.YES_NO_OPTION);
				        if(respuesta == JOptionPane.YES_OPTION) {
				        	removeAll();
							repaint();
							revalidate(); 
				        	add(new PanelCarruselArticulo(panel,l,null));
				        }
				}else {
					removeAll();
					repaint();
					revalidate();  
					add(new PanelCarruselArticulo(panel,l,l.getArticulos().get(0)));
				}
				
			}
		});
		btnVerArticulos.setFont(new Font("Verdana", Font.BOLD, 12));
		btnVerArticulos.setBounds(189, 395, 126, 45);
		add(btnVerArticulos);

		// Muestra las reservas asociadas a un local.
		JButton btnVerReservas = new JButton("Ver reservas");
		btnVerReservas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				removeAll();
				repaint();
				revalidate();  
				
				JPanel panelPrueba = new PanelReservas(panel,"Reservas de local: " + l.getLocalId()
				,s.buscarReservasLocal(l.getLocalId()));
				panelPrueba.setBounds(0, 0, 511, 503);
				add(panelPrueba);
			}
		});
		btnVerReservas.setFont(new Font("Verdana", Font.BOLD, 12));
		btnVerReservas.setBounds(344, 395, 126, 45);
		add(btnVerReservas);

		// Abre una ventana de editar local , tambien bloquea el poder manipular la ventana del local
		JButton btnEditarLocal = new JButton("Editar local");
		btnEditarLocal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VentanaPrincipal.getVentana().setEnabled(false);
				
				new VentanaEditarLocal(l,panelLocal);
			}
		});
		btnEditarLocal.setFont(new Font("Verdana", Font.BOLD, 12));
		btnEditarLocal.setBounds(189, 448, 126, 45);
		add(btnEditarLocal);

		btnVolver = new JButton("Volver");
		btnVolver.setFont(new Font("Verdana", Font.BOLD, 12));
		btnVolver.setBounds(385, 10, 116, 38);
		add(btnVolver);
		mostrarLocal(l);
	}

	/**
	 * Conmprueba si este local tiene articulo, en caso negativo devuelve false
	 * @param l : Local
	 * @return Boolean
	 */
	private boolean comprobarContenido(Local l) {
		if(l.getArticulos().size() != 0) {
			return true;
		}
		return false;
	}

	/**
	 * Actualiza los labels y textfields para mostrar la informacion del local asoaciado al panel
	 * @param l : Local
	 */
	public void mostrarLocal(Local l) {
		lblReserva.setText(lblReserva.getText().toString() + " " + l.getLocalId());
		txtDireccion.setText(l.getDireccion());
		txtCoordenadas.setText(l.getCoordenadas());
		lblIngresos.setText("Ingresos :" + l.getIngresos());
		lblReservas.setText("Reservas realizadas : " + l.getNumeroReservas());
	}
}
