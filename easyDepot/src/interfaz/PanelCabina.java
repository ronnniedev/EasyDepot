package interfaz;

import java.awt.Color;

import javax.swing.JPanel;

import logica.Sistema;
import modelo.Cabina;
import modelo.Local;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import componentes.Button;
import componentes.Colores;
import componentes.Estilos;
import componentes.PanelDatosRedondeado;

import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;

public class PanelCabina extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField txtLocalId;
	private JTextField txtIdReserva;
	private JTextField txtTipo;
	private Button btnAbrir;
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
		
		// Establece un panel para englobar datos de la cabina
		PanelDatosRedondeado panelDatos = new PanelDatosRedondeado(30);
		panelDatos.setBounds(42, 90, 426, 293);
		add(panelDatos);
		panelDatos.setLayout(null);
		
		// Se muestran los datos del cliente
		JLabel lblLocalId = new JLabel("Id local:");
		lblLocalId.setBounds(41, 28, 123, 40);
		lblLocalId.setFont(new Font("Verdana", Font.BOLD, 16));
		panelDatos.add(lblLocalId);
		
		txtLocalId = new JTextField();
		txtLocalId.setBounds(234, 29, 168, 40);
		txtLocalId.setHorizontalAlignment(SwingConstants.CENTER);
		txtLocalId.setText(l.getLocalId()+ "");
		txtLocalId.setFont(new Font("Verdana", Font.BOLD, 16));
		txtLocalId.setEditable(false);
		txtLocalId.setColumns(10);
		txtLocalId.setBackground(Color.WHITE);
		panelDatos.add(txtLocalId);
		
		JLabel lblIdReserva = new JLabel("Id reserva:");
		lblIdReserva.setBounds(41, 89, 123, 40);
		lblIdReserva.setFont(new Font("Verdana", Font.BOLD, 16));
		panelDatos.add(lblIdReserva);
		
		txtIdReserva = new JTextField();
		txtIdReserva.setBounds(234, 90, 168, 40);
		txtIdReserva.setHorizontalAlignment(SwingConstants.CENTER);
		txtIdReserva.setText(escribirIdReserva(c));
		txtIdReserva.setFont(new Font("Verdana", Font.BOLD, 16));
		txtIdReserva.setEditable(false);
		txtIdReserva.setColumns(10);
		txtIdReserva.setBackground(Color.WHITE);
		panelDatos.add(txtIdReserva);
		
		lblAbierto = new JLabel("Abierto :");
		lblAbierto.setHorizontalTextPosition(SwingConstants.LEFT);
		lblAbierto.setIcon(Estilos.prepararImagenCandado(mostrarEstado(c.getAbierto())));
		lblAbierto.setBounds(108, 219, 209, 64);
		lblAbierto.setFont(new Font("Verdana", Font.BOLD, 24));
		panelDatos.add(lblAbierto);
		
		JLabel lblTipo = new JLabel("Tipo: ");
		lblTipo.setBounds(41, 139, 102, 50);
		lblTipo.setFont(new Font("Verdana", Font.BOLD, 16));
		panelDatos.add(lblTipo);
		
		txtTipo = new JTextField(c.getTipo());
		txtTipo.setBounds(234, 149, 168, 40);
		txtTipo.setHorizontalAlignment(SwingConstants.CENTER);
		txtTipo.setText(c.getTipo());
		txtTipo.setFont(new Font("Verdana", Font.BOLD, 16));
		txtTipo.setEditable(false);
		txtTipo.setColumns(10);
		txtTipo.setBackground(Color.WHITE);
		panelDatos.add(txtTipo);
		
		// Este boton abre o cierra la cabian dependiendo de su estado anterior
		btnAbrir = new Button(escribirBotonAbrir(c));
		btnAbrir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(c.getAbierto()) {
					c.setAbierto(false);
				}else {
					c.setAbierto(true);
				}
				btnAbrir.setText(escribirBotonAbrir(c));
				s.actualizarCabina(c);
				lblAbierto.setIcon(Estilos.prepararImagenCandado(mostrarEstado(c.getAbierto())));
			}
		});
		btnAbrir.setFont(new Font("Verdana", Font.BOLD, 10));
		btnAbrir.setBounds(22, 421, 118, 47);
		add(btnAbrir);
		
		// Cambia de panel y muestra la reserva asociada a la cabina en caso de haber reserva, si no el boton esta 
		// inactivo
		Button btnVerReserva = new Button("Ver reserva");
		
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
		Button btnVerLocal = new Button("Ver local");
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
		
		PanelDatosRedondeado panelTitulo = new PanelDatosRedondeado(30);
		panelTitulo.setBounds(88, 31, 334, 33);
		panelTitulo.setBackground(Colores.getAZUL_CLARO());
		add(panelTitulo);
		
		// Establece el titulo del panel
		JLabel lblCabina = new JLabel("Cabina: " + c.getIdCabina(), SwingConstants.CENTER);
		panelTitulo.add(lblCabina);
		lblCabina.setFont(new Font("Verdana", Font.BOLD, 18));
		lblCabina.setBackground(Color.WHITE);
		
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
	private String mostrarEstado(Boolean abierto) {
		if(abierto) {
			return "/iconos/candadoAbierto.png";
		}
		return "/iconos/candadoCerrado.png";
	}
}
