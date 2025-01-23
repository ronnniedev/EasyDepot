package interfaz;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import logica.Sistema;

import java.awt.Color;
import java.awt.Font;

public class PanelInicio extends JPanel {

	private static final long serialVersionUID = 1L;

	/**
	 * Panel donde se muestran las diferentes estadisticas referentes al sistema, desdes ingresos
	 * a reservas, pasando tambien por el numero de clientes registrados y de locales en el sistema
	 */
	public PanelInicio(JPanel panel,Sistema s) {
		// Establecemos las dimensiones del sistema
		setBackground(new Color(255, 255, 255));
		setLayout(null);
		this.setBounds(175, 0, 511, 503);
		panel.add(this);
		
		JLabel lblRendimiento = new JLabel("Rendimiento Global",SwingConstants.CENTER);
		lblRendimiento.setFont(new Font("Verdana", Font.BOLD, 24));
		lblRendimiento.setBounds(0, 35, 511, 45);
		this.add(lblRendimiento);
		
		JLabel lbReservas = new JLabel("Reservas: " + s.calcularReservas());
		lbReservas.setFont(new Font("Verdana", Font.BOLD, 16));
		lbReservas.setBounds(24, 103, 310, 45);
		this.add(lbReservas);
		
		JLabel lbIngresos = new JLabel("Ingresos: " + s.calcularIngresos());
		lbIngresos.setFont(new Font("Verdana", Font.BOLD, 16));
		lbIngresos.setBounds(24, 158, 310, 45);
		this.add(lbIngresos);
		
		JLabel lbLocales = new JLabel("Consignas: " + s.calcularLocales());
		lbLocales.setFont(new Font("Verdana", Font.BOLD, 16));
		lbLocales.setBounds(24, 213, 310, 45);
		this.add(lbLocales);
		
		JLabel lbClientes = new JLabel("Clientes: " + s.calcularClientes());
		lbClientes.setFont(new Font("Verdana", Font.BOLD, 16));
		lbClientes.setBounds(24, 268, 310, 45);
		this.add(lbClientes);
	}

}
