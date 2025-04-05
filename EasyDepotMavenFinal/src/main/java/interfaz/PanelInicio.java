package interfaz;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import componentes.Colores;
import componentes.Estilos;
import componentes.PanelDatosRedondeado;
import logica.Sistema;

import java.awt.Color;
import java.awt.Font;
import javax.swing.ImageIcon;

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
		
		JLabel lbReservas = new JLabel("Reservas: " + s.calcularReservas());
		lbReservas.setFont(new Font("Verdana", Font.BOLD, 16));
		lbReservas.setBounds(84, 237, 167, 45);
		this.add(lbReservas);
		
		JLabel lbIngresos = new JLabel("Ingresos: " + String.format("%.2f", s.calcularIngresos()));
		lbIngresos.setFont(new Font("Verdana", Font.BOLD, 16));
		lbIngresos.setBounds(289, 237, 190, 45);
		this.add(lbIngresos);
		
		JLabel lbLocales = new JLabel("Locales: " + s.getLocales().size());
		lbLocales.setFont(new Font("Verdana", Font.BOLD, 16));
		lbLocales.setBounds(289, 401, 310, 45);
		this.add(lbLocales);
		
		JLabel lbClientes = new JLabel("Clientes: " + s.calcularClientes());
		lbClientes.setFont(new Font("Verdana", Font.BOLD, 16));
		lbClientes.setBounds(84, 406, 235, 35);
		this.add(lbClientes);
		
		JLabel lblCliente = new JLabel("");
		lblCliente.setIcon(new ImageIcon(Estilos.calcularRuta("cliente.png").toString()));
		lblCliente.setBounds(84, 292, 124, 124);
		add(lblCliente);
		
		JLabel lblLocales = new JLabel("");
		lblLocales.setIcon(new ImageIcon(Estilos.calcularRuta("localStat.png").toString()));
		lblLocales.setBounds(289, 280, 128, 128);
		add(lblLocales);
		
		JLabel lblIngresos = new JLabel("");
		lblIngresos.setIcon(new ImageIcon(Estilos.calcularRuta("ingresos.png").toString()));
		lblIngresos.setBounds(289, 115, 128, 128);
		add(lblIngresos);
		
		JLabel lblReservas = new JLabel("");
                lblReservas.setBounds(84, 115, 128, 128);
		lblReservas.setIcon(Estilos.crearImagenEscalada(lblReservas, "reservas.png"));
		add(lblReservas);
		
		JPanel panelDatos = new PanelDatosRedondeado(30);
		panelDatos.setBounds(52, 94, 407, 364);
		add(panelDatos);
		
		JPanel panelTitulo = new PanelDatosRedondeado(30);
		panelTitulo.setBounds(71, 21, 369, 51);
		panelTitulo.setBackground(Colores.getAZUL_CLARO());
		add(panelTitulo);
		panelTitulo.setLayout(null);
		
		JLabel lblRendimiento = new JLabel("Rendimiento Global",SwingConstants.CENTER);
		lblRendimiento.setBounds(53, 10, 263, 30);
		panelTitulo.add(lblRendimiento);
		lblRendimiento.setFont(new Font("Verdana", Font.BOLD, 24));
	}
}
