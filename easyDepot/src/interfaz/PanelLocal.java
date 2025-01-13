package interfaz;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import logica.Sistema;
import modelo.Local;

public class PanelLocal extends JPanel {

	private static final long serialVersionUID = 1L;
	private JLabel lblReserva;

	/**
	 * Create the panel.
	 */
	public PanelLocal(JPanel panel,Sistema s,Local l) {
		setBackground(new Color(255, 255, 255));
		this.setBounds(0, 0, 511, 503);
		panel.add(this);
		setLayout(null);

		lblReserva = new JLabel("Id de local:", SwingConstants.LEFT);
		lblReserva.setBackground(new Color(255, 255, 255));
		lblReserva.setFont(new Font("Verdana", Font.BOLD, 18));
		lblReserva.setBounds(0, 35, 511, 45);
		add(lblReserva);
		
		JPanel panelDatos = new JPanel();
		panelDatos.setBounds(0, 90, 511, 295);
		panelDatos.setBackground(new Color(255,255,255));
		add(panelDatos);
		panelDatos.setLayout(new GridLayout(5, 2, 0, 0));
	}

}
