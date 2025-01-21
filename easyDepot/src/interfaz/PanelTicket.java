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

import excepciones.LogicaException;
import excepciones.PersistenciaException;
import logica.Sistema;
import modelo.Cabina;
import modelo.Email;
import modelo.Local;
import modelo.Reserva;
import javax.swing.JTextField;
import javax.swing.JButton;

public class PanelTicket extends JPanel {

	private static final long serialVersionUID = 1L;
	private Sistema s;
	private JTextField txtFechaDeposito;
	private JTextField txtCliente;
	private JTextField txtCabina;
	private JTextField txtEstadoCabina;
	private JButton btnAbrir;
	private Cabina c;
	private Local l;
	private PanelTicket panelTicket;
	private JButton btnCerrarIncidencia;

	/**
	 * Create the panel.
	 * @throws LogicaException 
	 * @throws SQLException 
	 * @throws PersistenciaException 
	 */
	public PanelTicket(JPanel panel, Reserva r) {
		setBackground(new Color(255, 255, 255));
		this.setBounds(0, 0, 511, 503);
		
		this.panelTicket = this;
		
		panel.add(this);
		setLayout(null);
		try {
			this.s = Sistema.getInstance();
			c = s.buscarCabina(r.getIdCabina(), s.buscarLocal(Integer.parseInt(r.getIdCabina().charAt(0)+"")));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		String trozos[] = r.getIdCabina().split("-");
		this.l = s.buscarLocal(Integer.parseInt(trozos[0]));
		
		
		JLabel lblReserva = new JLabel("Incidencia de reserva: " + r.getIdReserva(), SwingConstants.LEFT);
		lblReserva.setFont(new Font("Verdana", Font.BOLD, 18));
		lblReserva.setBackground(Color.WHITE);
		lblReserva.setBounds(0, 25, 511, 45);
		add(lblReserva);
		
		JPanel panelDatos = new JPanel();
		panelDatos.setBounds(0, 90, 511, 293);
		panelDatos.setBackground(new Color(255,255,255));
		add(panelDatos);
		panelDatos.setLayout(new GridLayout(5, 2, 0, 0));
		
		JLabel lblCliente = new JLabel("Email Cliente:");
		lblCliente.setFont(new Font("Verdana", Font.BOLD, 16));
		panelDatos.add(lblCliente);
		
		txtCliente = new JTextField(r.getEmailCliente());
		txtCliente.setBackground(new Color(255, 255, 255));
		txtCliente.setEditable(false);
		txtCliente.setFont(new Font("Verdana", Font.BOLD, 16));
		txtCliente.setColumns(10);
		panelDatos.add(txtCliente);
		
		JLabel lblFechaDeposito = new JLabel("Fecha Deposito:");
		lblFechaDeposito.setFont(new Font("Verdana", Font.BOLD, 16));
		panelDatos.add(lblFechaDeposito);
		
		txtFechaDeposito = new JTextField(r.getFechaInicio().toString());
		txtFechaDeposito.setBackground(new Color(255, 255, 255));
		txtFechaDeposito.setEditable(false);
		txtFechaDeposito.setFont(new Font("Verdana", Font.BOLD, 16));
		panelDatos.add(txtFechaDeposito);
		txtFechaDeposito.setColumns(10);
		
		JLabel lblCabina = new JLabel("Id Cabina:");
		lblCabina.setFont(new Font("Verdana", Font.BOLD, 16));
		panelDatos.add(lblCabina);
		
		txtCabina = new JTextField(r.getIdCabina());
		txtCabina.setBackground(new Color(255, 255, 255));
		txtCabina.setEditable(false);
		txtCabina.setFont(new Font("Verdana", Font.BOLD, 16));
		txtCabina.setColumns(10);
		panelDatos.add(txtCabina);
		
		JLabel lblMensaje = new JLabel("Informe:");
		lblMensaje.setFont(new Font("Verdana", Font.BOLD, 16));
		panelDatos.add(lblMensaje);
		
		JButton btnVerInforme = new JButton("Ver Informe");
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
		lblApertura.setFont(new Font("Verdana", Font.BOLD, 16));
		panelDatos.add(lblApertura);
		
		txtEstadoCabina = new JTextField(escribirEstado(c.getAbierto()));
		txtEstadoCabina.setBackground(new Color(255, 255, 255));
		txtEstadoCabina.setEditable(false);
		txtEstadoCabina.setFont(new Font("Verdana", Font.BOLD, 16));
		txtEstadoCabina.setColumns(10);
		panelDatos.add(txtEstadoCabina);
		
		btnAbrir = new JButton(escribirBotonAbrir(c));
		btnAbrir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(c.getAbierto()) {
					c.setAbierto(false);
				}else {
					c.setAbierto(true);
				}
				btnAbrir.setText(escribirBotonAbrir(c));
				txtEstadoCabina.setText(escribirEstado(c.getAbierto()));
				s.actualizarCabina(c);
			}
		});
		btnAbrir.setFont(new Font("Verdana", Font.BOLD, 10));
		btnAbrir.setBounds(34, 395, 126, 47);
		add(btnAbrir);
		
		JButton btnCliente = new JButton("Ver cliente");
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
		
		JButton btnLocal = new JButton("Ver local");
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
		
		btnCerrarIncidencia = new JButton("Cerrar Incidencia");
		btnCerrarIncidencia.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VentanaPrincipal.getVentana().setEnabled(false);
				new VentanaInformeCierre(r,l,panelTicket);
			}
		});
		btnCerrarIncidencia.setFont(new Font("Verdana", Font.BOLD, 9));
		btnCerrarIncidencia.setBounds(263, 447, 126, 47);
		habilitarBotonCierre(r);
		add(btnCerrarIncidencia);
		
		JButton btnReserva = new JButton("Ver reserva");
		btnReserva.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				removeAll();
				repaint();
				revalidate();
				add(new PanelReserva(panel,s,r));
			}
		});
		btnReserva.setFont(new Font("Verdana", Font.BOLD, 12));
		btnReserva.setBounds(111, 446, 126, 47);
		add(btnReserva);
		

	}
	
	private String escribirBotonAbrir(Cabina c) {
		if(c.getAbierto()) {
			return "Cerrar";
		}
		return "Abrir";
	}
	
	private String escribirEstado(Boolean abierto) {
		if(abierto) {
			return "Abierta";
		}
		return "Cerrada";
	}
	
	public void habilitarBotonCierre(Reserva r) {
		btnCerrarIncidencia.setEnabled(r.isIncidencia());
	}
}
