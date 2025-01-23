package interfaz;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;

import logica.Sistema;
import modelo.Local;
import modelo.Reserva;

import javax.swing.SwingConstants;

public class VentanaInformeCierre extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private Sistema s;

	/**
	 * Muestra una panel dodne el usuario podra escribir el informe de cierre de la incidencia asociada
	 */
	public VentanaInformeCierre(Reserva r ,Local l,PanelTicket panelTicket) {
		// Establecemos las dimensiones de la ventana
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(550,300, 536, 417);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		try {
			s = s.getInstance();
		} catch (Exception e) {
			
		} 

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		// Establecemos el scrollpane y  textarea donde se escribir el informe de cierre
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setBounds(10, 74, 502, 246);
		contentPane.add(scrollPane);
		
		JTextArea txtInforme = new JTextArea("--Escribe aqui tu texto--");
		txtInforme.setWrapStyleWord(true);
		txtInforme.setLineWrap(true);
		txtInforme.setFont(new Font("Verdana", Font.PLAIN, 12));
		txtInforme.setBackground(new Color(220, 220, 220));
		scrollPane.setViewportView(txtInforme);
		
		// Establece el titulo de la ventana 
		JLabel lblInformeTitulo = new JLabel("Informe de cierre");
		lblInformeTitulo.setHorizontalAlignment(SwingConstants.CENTER);
		lblInformeTitulo.setFont(new Font("Verdana", Font.BOLD, 20));
		lblInformeTitulo.setBounds(0, 20, 522, 33);
		contentPane.add(lblInformeTitulo);
		
		// Guarda el informe de cierre y escribe una cabecera par amostarr dentro del informe general
		// Actualiza la informacion de la incidencia asoaciado a la reserva pertinente
		JButton btnSalir = new JButton("Cerrar Incidencia");
		btnSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VentanaPrincipal.getVentana().setEnabled(true);
				// Le mete a el informe anterior una cabecer ay el nuevo informe cerrando la incidencia
				r.setDescripcionIncidencia(r.getDescripcionIncidencia() + prepararCabecera(l) +txtInforme.getText());
				r.setIncidencia(false);
				s.actualizarReserva(r);
				panelTicket.habilitarBotonCierre(r);
				dispose();
			}
		});
		btnSalir.setFont(new Font("Verdana", Font.BOLD, 12));
		btnSalir.setBounds(68, 330, 158, 45);
		contentPane.add(btnSalir);
		
		// Habilita la ventana anterior y cierra la actual sin guardar ningun dato
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VentanaPrincipal.getVentana().setEnabled(true);
				dispose();
			}
		});
		btnCancelar.setFont(new Font("Verdana", Font.BOLD, 12));
		btnCancelar.setBounds(295, 330, 158, 45);
		contentPane.add(btnCancelar);
		this.setVisible(true);
	}
	
	/**
	 * Formatea una cabecera para el informe guardndo el dia mes y anio ademas de la hora, devuelve el informe con 
	 * formato de String
	 * @param l : Local
	 * @return String
	 */
	private String prepararCabecera(Local l) {
		// Extraemos la fecha del sistema
		LocalDateTime fechaActual = LocalDateTime.now();
		// Establecemos la franja horaria
		Locale localeEspañol = Locale.of("es", "ES");
		// implementamos dos formato distintos para la cabecera del informe
		DateTimeFormatter diaFormato = DateTimeFormatter.ofPattern("d 'de' MMMM 'de' yyyy",localeEspañol);
		DateTimeFormatter horaFormato = DateTimeFormatter.ofPattern("HH:mm",localeEspañol);
		
		
		return "\n--------------------------------------------------------------------------------------\n"
				+ "Informe de cierre de incidencia en local\r\n"
				+ "\r\n"
				+ "Fecha:" + fechaActual.format(diaFormato) + " \r\n"
				+ "Hora: "+ fechaActual.format(horaFormato)+ " \r\n"
				+ "Local: "+ l.getLocalId() + " \r\n"
				+ "Ubicación: " +  l.getDireccion() +" \r\n"
				+ "\r\n"
				+ "Descripción de resolucion de incidencia:\n";
	}

}
