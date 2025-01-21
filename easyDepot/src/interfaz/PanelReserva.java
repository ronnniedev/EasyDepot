package interfaz;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import excepciones.LogicaException;
import logica.GestorComprobaciones;
import logica.Sistema;
import modelo.Email;
import modelo.Local;
import modelo.Reserva;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class PanelReserva extends JPanel {

	private static final long serialVersionUID = 1L;
	private JLabel lblReserva;
	private JTextField txtEmail;
	private JTextField txtCabina;
	private JTextField txtFechaInicio;
	private JTextField txtFechaSalida;
	private JLabel lblIncidencia;
	private JButton btnVerIncidencia;
	private JButton btnModificarReserva;

	/**
	 * Create the panel.
	 */
	public PanelReserva(JPanel panel, Sistema s, Reserva r) {
		setBackground(new Color(255, 255, 255));
		this.setBounds(0, 0, 511, 503);
		panel.add(this);
		setLayout(null);

		lblReserva = new JLabel("Id de Reserva :", SwingConstants.LEFT);
		lblReserva.setBackground(new Color(255, 255, 255));
		lblReserva.setFont(new Font("Verdana", Font.BOLD, 18));
		lblReserva.setBounds(0, 35, 511, 45);
		add(lblReserva);

		JPanel panelDatos = new JPanel();
		panelDatos.setBounds(0, 90, 511, 293);
		panelDatos.setBackground(new Color(255, 255, 255));
		add(panelDatos);
		panelDatos.setLayout(new GridLayout(5, 2, 0, 0));

		JLabel lblEmail = new JLabel("Email cliente:");
		lblEmail.setFont(new Font("Verdana", Font.BOLD, 16));
		panelDatos.add(lblEmail);

		txtEmail = new JTextField();
		txtEmail.setBackground(new Color(255, 255, 255));
		txtEmail.setEditable(false);
		txtEmail.setFont(new Font("Verdana", Font.BOLD, 12));
		panelDatos.add(txtEmail);
		txtEmail.setColumns(10);

		JLabel lblCabina = new JLabel("Cabina (Local-Cabina):");
		lblCabina.setFont(new Font("Verdana", Font.BOLD, 16));
		panelDatos.add(lblCabina);

		txtCabina = new JTextField();
		txtCabina.setBackground(new Color(255, 255, 255));
		txtCabina.setEditable(false);
		txtCabina.setFont(new Font("Verdana", Font.BOLD, 16));
		txtCabina.setColumns(10);
		panelDatos.add(txtCabina);

		JLabel lblFechaInicio = new JLabel("Dia Deposito:");
		lblFechaInicio.setFont(new Font("Verdana", Font.BOLD, 16));
		panelDatos.add(lblFechaInicio);

		txtFechaInicio = new JTextField();
		txtFechaInicio.setBackground(new Color(255, 255, 255));
		txtFechaInicio.setEditable(false);
		txtFechaInicio.setFont(new Font("Verdana", Font.BOLD, 16));
		txtFechaInicio.setColumns(10);
		panelDatos.add(txtFechaInicio);

		JLabel lblFechaSalida = new JLabel("Dia extraccion:");
		lblFechaSalida.setFont(new Font("Verdana", Font.BOLD, 16));
		panelDatos.add(lblFechaSalida);

		txtFechaSalida = new JTextField();
		txtFechaSalida.setBackground(new Color(255, 255, 255));
		txtFechaSalida.setEditable(false);
		txtFechaSalida.setText("Aun no ha sido extraido");
		txtFechaSalida.setFont(new Font("Verdana", Font.BOLD, 16));
		txtFechaSalida.setColumns(10);
		panelDatos.add(txtFechaSalida);

		lblIncidencia = new JLabel("Incidencia: ");
		lblIncidencia.setFont(new Font("Verdana", Font.BOLD, 16));
		panelDatos.add(lblIncidencia);

		btnVerIncidencia = new JButton("Ver Incidencia");
		btnVerIncidencia.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				removeAll();
				repaint();
				revalidate();
				add(new PanelTicket(panel,r));
			}
		});
		btnVerIncidencia.setFont(new Font("Verdana", Font.BOLD, 16));
		panelDatos.add(btnVerIncidencia);

		btnModificarReserva = new JButton("TEXT");
		btnModificarReserva.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int respuesta = JOptionPane.showConfirmDialog(null,
						"Cerraras la reserva si confirmas ahora ¿Estas seguro?", "Advertencia",
						JOptionPane.YES_NO_OPTION);
				if (respuesta == JOptionPane.YES_OPTION) {
					try {
						s.cerrarReserva(r.getIdReserva());
						btnModificarReserva.setEnabled(false);
						txtFechaSalida.setText(r.getFechaSalida().toString());
					} catch (LogicaException e1) {
						JOptionPane.showMessageDialog(null, "ERROR AL CERRAR LA RESERVA", "ERRROR",
								JOptionPane.ERROR_MESSAGE);
					}
				}

			}
		});
		btnModificarReserva.setFont(new Font("Verdana", Font.BOLD, 10));
		btnModificarReserva.setBounds(10, 428, 118, 47);
		add(btnModificarReserva);

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
		btnCliente.setBounds(132, 428, 118, 47);
		add(btnCliente);

		JButton btnLocal = new JButton("Ver local");
		btnLocal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				removeAll();
				repaint();
				revalidate();
				String trozos[] = r.getIdCabina().split("-");
				add(new PanelLocal(panel, s, s.buscarLocal(Integer.parseInt(trozos[0]))));
			}
		});
		btnLocal.setFont(new Font("Verdana", Font.BOLD, 12));
		btnLocal.setBounds(260, 428, 118, 47);
		add(btnLocal);

		JButton btnVerCabina = new JButton("Ver cabina");
		btnVerCabina.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				removeAll();
				repaint();
				revalidate();
				String trozos[] = r.getIdCabina().split("-");
				Local l = s.buscarLocal(Integer.parseInt(trozos[0]));
				add(new PanelCabina(panel, s, s.buscarCabina(r.getIdCabina(), l), l));
			}
		});
		btnVerCabina.setFont(new Font("Verdana", Font.BOLD, 12));
		btnVerCabina.setBounds(388, 428, 118, 47);
		add(btnVerCabina);
		mostrarReserva(r);
	}

	/**
	 * Carga todos los datos referentes a la reserva, ademas hace las distinciones
	 * necesarias para poder activar el boton de incidencia en caso de ser necesario
	 * 
	 * @param r : Reserva
	 */
	public void mostrarReserva(Reserva r) {
		lblReserva.setText("Id Reserva: " + r.getIdReserva());
		txtEmail.setText(r.getEmailCliente());
		txtCabina.setText(r.getIdCabina());
		txtFechaInicio.setText(r.getFechaInicio().toString());

		if (GestorComprobaciones.comprobarFechaSalida(r.getFechaSalida())) {
			txtFechaSalida.setText(r.getFechaSalida().toString());
			btnModificarReserva.setEnabled(false);
		} else {
			btnModificarReserva.setText("Cerrar reserva");
		}

		if (!r.isIncidencia()) {
			lblIncidencia.setText("Incidencia : Sin Incidencias");
			btnVerIncidencia.setEnabled(false);
		}

	}
}
