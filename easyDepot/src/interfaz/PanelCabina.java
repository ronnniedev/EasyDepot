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
	 * Create the panel.
	 * @param cabina 
	 * @param s 
	 * @param panel 
	 */
	public PanelCabina(JPanel panel, Sistema s, Cabina c,Local l) {
		setBackground(new Color(255, 255, 255));
		this.setBounds(0, 0, 511, 503);
		panel.add(this);
		setLayout(null);
		this.s = s;
		
		JLabel lblCabina = new JLabel("Cabina: " + c.getIdCabina(), SwingConstants.LEFT);
		lblCabina.setFont(new Font("Verdana", Font.BOLD, 18));
		lblCabina.setBackground(Color.WHITE);
		lblCabina.setBounds(0, 25, 511, 45);
		add(lblCabina);
		
		JPanel panelDatos = new JPanel();
		panelDatos.setBounds(0, 90, 511, 293);
		panelDatos.setBackground(new Color(255,255,255));
		add(panelDatos);
		panelDatos.setLayout(new GridLayout(4, 2, 0, 0));
		
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
		
		txtAbierto = new JTextField();
		txtAbierto.setHorizontalAlignment(SwingConstants.CENTER);
		txtAbierto.setText(escribirEstado(c.getAbierto()));
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
		
		JButton btnVerReserva = new JButton("Ver reserva");
		
		btnVerReserva.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				removeAll();
				repaint();
				revalidate();  
				add(new PanelReserva(panel,s,s.buscarReserva(c.getIdLocal())));
			}
		});
		btnVerReserva.setFont(new Font("Verdana", Font.BOLD, 10));
		btnVerReserva.setBounds(197, 421, 118, 47);
		add(btnVerReserva);
		btnVerReserva.setEnabled(c.getReservada());
		
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

	private String escribirBotonAbrir(Cabina c) {
		if(c.getAbierto()) {
			return "Cerrar";
		}
		return "Abrir";
	}
	
	private String escribirIdReserva(Cabina c) {
		String id = s.buscarReservaCabina(c);
		
		if(id == null) {
			return "Sin reservar";
		}
		return id;
	}
	
	private String escribirEstado(Boolean abierto) {
		if(abierto) {
			return "Abierta";
		}
		return "Cerrada";
	}
}
