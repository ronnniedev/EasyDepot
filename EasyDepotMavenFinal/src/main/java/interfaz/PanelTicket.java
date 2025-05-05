package interfaz;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import componentes.Button;
import componentes.ButtonGris;
import componentes.Colores;
import componentes.Estilos;
import componentes.PanelDatosRedondeado;
import excepciones.LogicaException;
import excepciones.PersistenciaException;
import java.net.URL;
import logica.Sistema;
import modelo.Cabina;
import modelo.Email;
import modelo.Local;
import modelo.Reserva;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.ImageIcon;

public class PanelTicket extends JPanel {

	private static final long serialVersionUID = 1L;
	private Sistema s;
	private JTextField txtFechaDeposito;
	private JTextField txtCliente;
	private JTextField txtCabina;
	private JButton btnAbrir;
	private Cabina c;
	private Local l;
	private PanelTicket panelTicket;
	private JButton btnCerrarIncidencia;
	private JLabel lblEstado; 
	private Reserva r;

	/**
	 * Muestra la informacion asoaciada a una reserva asoaciada al panel, permite abrir y cerrar una cabina asociada,
	 * ver un cliente asociado, un local, ver la reserva asoaciada y finalmente cerrar la incidencia.
	 * @throws LogicaException 
	 * @throws SQLException 
	 * @throws PersistenciaException 
	 */
	public PanelTicket(JPanel panel, Reserva r) {
		// Establecemos las dimensiones del panel
		setBackground(new Color(255, 255, 255));
		this.setBounds(0, 0, 511, 503);
		
		this.r = r;
		this.panelTicket = this;
		
		panel.add(this);
		setLayout(null);
		
		// Instanciamos el sistema
		try {
			this.s = Sistema.getInstance();
			c = s.buscarCabina(r.getIdCabina(), s.buscarLocal(Integer.parseInt(r.getIdCabina().charAt(0)+"")));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		// Instanciamos el local buscandolo en el sistema
		String trozos[] = r.getIdCabina().split("-");
		this.l = s.buscarLocal(Integer.parseInt(trozos[0]));
		
		PanelDatosRedondeado panelDatos = new PanelDatosRedondeado(30);
		panelDatos.setBounds(42, 90, 426, 293);
		add(panelDatos);
		panelDatos.setLayout(null);
		
		JLabel lblCliente = new JLabel("Email Cliente:");
		lblCliente.setBounds(34, 9, 151, 41);
		lblCliente.setFont(new Font("Verdana", Font.BOLD, 16));
		panelDatos.add(lblCliente);
		
		// Labels definitorios
		
		txtCliente = new JTextField(r.getEmailCliente());
		txtCliente.setBounds(164, 9, 252, 41);
		txtCliente.setBackground(new Color(255, 255, 255));
		txtCliente.setEditable(false);
		txtCliente.setFont(new Font("Verdana", Font.BOLD, 10));
		txtCliente.setColumns(10);
		panelDatos.add(txtCliente);
		
		JLabel lblFechaDeposito = new JLabel("Fecha Deposito:");
		lblFechaDeposito.setBounds(34, 60, 172, 48);
		lblFechaDeposito.setFont(new Font("Verdana", Font.BOLD, 16));
		panelDatos.add(lblFechaDeposito);
		
		txtFechaDeposito = new JTextField(r.getFechaInicio().toString());
		txtFechaDeposito.setBounds(195, 67, 221, 41);
		txtFechaDeposito.setBackground(new Color(255, 255, 255));
		txtFechaDeposito.setEditable(false);
		txtFechaDeposito.setFont(new Font("Verdana", Font.BOLD, 16));
		panelDatos.add(txtFechaDeposito);
		txtFechaDeposito.setColumns(10);
		
		JLabel lblCabina = new JLabel("Id Cabina:");
		lblCabina.setBounds(34, 118, 172, 47);
		lblCabina.setFont(new Font("Verdana", Font.BOLD, 16));
		panelDatos.add(lblCabina);
		
		txtCabina = new JTextField(r.getIdCabina());
		txtCabina.setBounds(195, 121, 221, 41);
		txtCabina.setBackground(new Color(255, 255, 255));
		txtCabina.setEditable(false);
		txtCabina.setFont(new Font("Verdana", Font.BOLD, 16));
		txtCabina.setColumns(10);
		panelDatos.add(txtCabina);
		
		JLabel lblMensaje = new JLabel("Informe:");
		lblMensaje.setBounds(34, 175, 172, 48);
		lblMensaje.setFont(new Font("Verdana", Font.BOLD, 16));
		panelDatos.add(lblMensaje);
		
		// Abre la ventana del informe
		JButton btnVerInforme = new Button("Ver Informe");
		btnVerInforme.setBounds(195, 179, 221, 41);
		btnVerInforme.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// inhabilita la ventana
				VentanaPrincipal.getVentana().setEnabled(false);
				// pasamos el contexto de la ventana para que se actualizen los datos de la aplicacion
				new VentanaInforme(r);
			}
		});
		btnVerInforme.setFont(new Font("Verdana", Font.BOLD, 16));
		panelDatos.add(btnVerInforme);
		
		JLabel lblApertura = new JLabel("Estado cabina:");
		lblApertura.setIcon(Estilos.prepararImagenBotonera(mostrarEstado(c.getAbierto())));
		lblApertura.setHorizontalTextPosition(SwingConstants.LEFT);
		lblApertura.setBounds(34, 242, 181, 41);
		lblApertura.setFont(new Font("Verdana", Font.BOLD, 16));
		panelDatos.add(lblApertura);
		
		lblEstado = new JLabel();
		actualizarEstado();
		lblEstado.setHorizontalTextPosition(SwingConstants.LEFT);
		lblEstado.setFont(new Font("Verdana", Font.BOLD, 16));
		lblEstado.setBounds(195, 242, 221, 41);
		panelDatos.add(lblEstado);
		
		// ABre o cierra la cabina dependiendo de su estado
		btnAbrir = new ButtonGris(escribirBotonAbrir(c));
		btnAbrir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(c.getAbierto()) {
					c.setAbierto(false);
				}else {
					c.setAbierto(true);
				}
				btnAbrir.setText(escribirBotonAbrir(c));
				// TODO txtEstadoCabina.setText(escribirEstado(c.getAbierto()));
				s.actualizarCabina(c);
				lblApertura.setIcon(Estilos.prepararImagenBotonera(mostrarEstado(c.getAbierto())));
			}
		});
		btnAbrir.setFont(new Font("Verdana", Font.BOLD, 10));
		btnAbrir.setBounds(111, 446, 126, 47);
		add(btnAbrir);
		
		JButton btnCliente = new Button("Ver cliente");
		btnCliente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				removeAll();
				repaint();
				revalidate();
				add(new PanelCliente(panel, s, s.getClientes().get(new Email(r.getEmailCliente()))));
			}
		});
		btnCliente.setFont(new Font("Verdana", Font.BOLD, 12));
		btnCliente.setBounds(189, 395, 126, 47);
		add(btnCliente);
		
		// Muetsra el panel del local mostrando la informacion asociada al mismo
		JButton btnLocal = new Button("Ver local");
		btnLocal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				removeAll();
				repaint();
				revalidate();
				add(new PanelLocal(panel, s, l));
			}
		});
		btnLocal.setFont(new Font("Verdana", Font.BOLD, 12));
		btnLocal.setBounds(344, 395, 126, 47);
		add(btnLocal);
		
		// Abre el informe de cierre de incidencia
		btnCerrarIncidencia = new ButtonGris("Cerrar Incidencia");
		btnCerrarIncidencia.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VentanaPrincipal.getVentana().setEnabled(false);
				new VentanaInformeCierre(r,l,panelTicket);
			}
		});
		btnCerrarIncidencia.setFont(new Font("Verdana", Font.BOLD, 9));
		btnCerrarIncidencia.setBounds(263, 447, 126, 47);
		habilitarBotonCierre();
		add(btnCerrarIncidencia);
		
		// Visualiza la reserva
		JButton btnReserva = new Button("Ver reserva");
		btnReserva.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				removeAll();
				repaint();
				revalidate();
				add(new PanelReserva(panel,s,r));
			}
		});
		btnReserva.setFont(new Font("Verdana", Font.BOLD, 12));
		btnReserva.setBounds(34, 395, 126, 47);
		// comprobamos si el cliente que estamos tratando es uno eliminado
		add(btnReserva);
		btnCliente.setEnabled(comprobarEliminado());
		
		JPanel panelTitulo = new PanelDatosRedondeado(30);
		panelTitulo.setBounds(44, 31, 422, 33);
		panelTitulo.setBackground(Colores.getAZUL_CLARO());
		add(panelTitulo);
		
		//Instanciamos los labels y textfields para mostrar la informacion de la reserva y cliente
		JLabel lblIncidencia = new JLabel("Incidencia en reserva: " + r.getIdReserva(), SwingConstants.CENTER);
		panelTitulo.add(lblIncidencia);
		lblIncidencia.setFont(new Font("Verdana", Font.BOLD, 18));
		lblIncidencia.setBackground(Color.WHITE);
	}
	
	private String mostrarEstadoIncidencia(Boolean estado) {
		if(estado) {
			return "Abierta";
		}
		return "Cerrada";
	}

	/**
	 * Comprueba si el cliente que gestiona este panel es una cuenta eliminada, en cuyo caso no 
	 * puede visualizarse como cliente
	 * @return Boolean
	 */
	private boolean comprobarEliminado() {
		if(txtCliente.getText().compareTo("Eliminado") == 0) {
			return false;
		}
		return true;
	}
	
	/**
	 * Escribe el boton de abrir o cerrar dependiendo del estado de la cabina 
	 * @param c : Cabina
	 * @return String
	 */
	private String escribirBotonAbrir(Cabina c) {
		if(c.getAbierto()) {
			return "Cerrar";
		}
		return "Abrir";
	}
	
	/**
	 * Escribe el estado de la cabina dependiendo de su estado
	 * @param abierto : Boolean
	 * @return String
	 */
	private URL mostrarEstado(Boolean abierto) {
		if(abierto) {
			return Estilos.calcularRuta("candadoAbierto.png");
		}
		return Estilos.calcularRuta("candadoCerrado.png");
	}
	
	/**
	 * Comprueba si la incidencia esta abierta, en caso de que lo este activa el boton de abrir incidencia
	 * @param r : Reserva
	 */
	public void habilitarBotonCierre() {
		btnCerrarIncidencia.setEnabled(r.isIncidencia());
	}

	public void actualizarEstado() {
		lblEstado.setText("Incidencia: " + mostrarEstadoIncidencia(r.isIncidencia()));
	}
}
