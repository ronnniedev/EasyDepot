package interfaz;

import java.awt.Color;

import javax.swing.JPanel;

import logica.Sistema;
import modelo.Cabina;
import modelo.Local;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.JTextField;
import javax.swing.Icon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class PanelCabina extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField txtLocalId;
	private JTextField txtIdReserva;
	private JTextField txtTipo;
	private JTextField txtAbierto;
	private JButton btnAbrir;
	private JLabel lblAbierto;
	private Sistema s;

	/**
	 * Un panel que muetsra la informacion de una cabina determinada, abrir la cabina, ver una reserva asociada y 
	 * ver un local asociado
	 * @param cabina : Cabina
	 * @param s : Sistema
	 * @param panel : JPanel
	 * @param l : Local
	 */
	public PanelCabina(JPanel panel, Sistema s, Cabina c,Local l) {
		// Establecemos las dimensiones del panel
		setBackground(new Color(255, 255, 255));
		this.setBounds(0, 0, 511, 503);
		panel.add(this);
		setLayout(null);
		this.s = s;
		
		// Establece el titulo del panel
		JLabel lblCabina = new JLabel("Cabina: " + c.getIdCabina(), SwingConstants.LEFT);
		lblCabina.setFont(new Font("Verdana", Font.BOLD, 18));
		lblCabina.setBackground(Color.WHITE);
		lblCabina.setBounds(0, 25, 511, 45);
		add(lblCabina);
		
		// Establece un panel para englobar datos de la cabina
		JPanel panelDatos = new JPanel();
		panelDatos.setBounds(0, 90, 511, 293);
		panelDatos.setBackground(new Color(255,255,255));
		add(panelDatos);
		panelDatos.setLayout(new GridLayout(4, 2, 0, 0));
		
		// Se muestran los datos del cliente
		JLabel lblLocalId = new JLabel("Id local:");
		lblLocalId.setFont(new Font("Verdana", Font.BOLD, 16));
		panelDatos.add(lblLocalId);
		
		txtLocalId = new JTextField();
		txtLocalId.setHorizontalAlignment(SwingConstants.CENTER);
		txtLocalId.setText(l.getLocalId()+ "");
		txtLocalId.setFont(new Font("Verdana", Font.BOLD, 16));
		txtLocalId.setEditable(false);
		txtLocalId.setColumns(10);
		txtLocalId.setBackground(Color.WHITE);
		panelDatos.add(txtLocalId);
		
		JLabel lblIdReserva = new JLabel("Id reserva:");
		lblIdReserva.setFont(new Font("Verdana", Font.BOLD, 16));
		panelDatos.add(lblIdReserva);
		
		txtIdReserva = new JTextField();
		txtIdReserva.setHorizontalAlignment(SwingConstants.CENTER);
		txtIdReserva.setText(escribirIdReserva(c));
		txtIdReserva.setFont(new Font("Verdana", Font.BOLD, 16));
		txtIdReserva.setEditable(false);
		txtIdReserva.setColumns(10);
		txtIdReserva.setBackground(Color.WHITE);
		panelDatos.add(txtIdReserva);
		
		lblAbierto = new JLabel("Abierto :");
		lblAbierto.setFont(new Font("Verdana", Font.BOLD, 16));
		panelDatos.add(lblAbierto);
		
		txtAbierto = new JTextField(escribirEstado(c.getAbierto()));
		txtAbierto.setHorizontalAlignment(SwingConstants.CENTER);
		txtAbierto.setFont(new Font("Verdana", Font.BOLD, 16));
		txtAbierto.setEditable(false);
		txtAbierto.setColumns(10);
		txtAbierto.setBackground(Color.WHITE);
		panelDatos.add(txtAbierto);
		
		JLabel lblTipo = new JLabel("Tipo: ");
		lblTipo.setFont(new Font("Verdana", Font.BOLD, 16));
		panelDatos.add(lblTipo);
		
		txtTipo = new JTextField(c.getTipo());
		txtTipo.setHorizontalAlignment(SwingConstants.CENTER);
		txtTipo.setText(c.getTipo());
		txtTipo.setFont(new Font("Verdana", Font.BOLD, 16));
		txtTipo.setEditable(false);
		txtTipo.setColumns(10);
		txtTipo.setBackground(Color.WHITE);
		panelDatos.add(txtTipo);
		
		// Este boton abre o cierra la cabian dependiendo de su estado anterior
		btnAbrir = new JButton(escribirBotonAbrir(c));
		btnAbrir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(c.getAbierto()) {
					c.setAbierto(false);
				}else {
					c.setAbierto(true);
				}
				btnAbrir.setText(escribirBotonAbrir(c));
				txtAbierto.setText(escribirEstado(c.getAbierto()));
				s.actualizarCabina(c);
			}
		});
		btnAbrir.setFont(new Font("Verdana", Font.BOLD, 10));
		btnAbrir.setBounds(22, 421, 118, 47);
		add(btnAbrir);
		
		// Cambia de panel y muestra la reserva asociada a la cabina en caso de haber reserva, si no el boton esta 
		// inactivo
		JButton btnVerReserva = new JButton("Ver reserva");
		
		btnVerReserva.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				removeAll();
				repaint();
				revalidate();  
				add(new PanelReserva(panel,s,s.buscarReserva(Integer.parseInt(txtIdReserva.getText()))));
			}
		});
		btnVerReserva.setFont(new Font("Verdana", Font.BOLD, 10));
		btnVerReserva.setBounds(197, 421, 118, 47);
		add(btnVerReserva);
		btnVerReserva.setEnabled(c.getReservada());
		
		// Muestra el panel con los datos del local asociado a la cabina
		JButton btnVerLocal = new JButton("Ver local");
		btnVerLocal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				removeAll();
				repaint();
				revalidate();  
				add(new PanelLocal(panel,s,l));
			}
		});
		btnVerLocal.setFont(new Font("Verdana", Font.BOLD, 10));
		btnVerLocal.setBounds(371, 421, 118, 47);
		add(btnVerLocal);
		
	}

	/**
	 * Escribe el texto del boton abrir dependiendo de si esta la cabina abierta o no
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
	 * Escribe la id de la reserva si esta se encuentra reserva, si no es asi, pone Sin reservar
	 * @param c : Cabina
	 * @return String
	 */
	private String escribirIdReserva(Cabina c) {
		String id = s.buscarReservaCabina(c);
		
		if(id == null) {
			return "Sin reservar";
		}
		return id;
	}
	
	/**
	 * Escribe el estado de la cabina dependiendo de si esta esta abierta o no
	 * @param abierto : Boolean
	 * @return String
	 */
	private String escribirEstado(Boolean abierto) {
		if(abierto) {
			return "Abierta";
		}
		return "Cerrada";
	}
}
