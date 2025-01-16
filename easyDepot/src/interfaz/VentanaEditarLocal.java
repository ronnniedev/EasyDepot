package interfaz;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import logica.Sistema;

public class VentanaEditarLocal extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private Sistema s;

	/**
	 * Create the frame.
	 */
	public VentanaEditarLocal() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 394, 333);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		try {
			s = Sistema.getInstance();
		} catch (Exception e) {
			
		} 
		
		setContentPane(contentPane);
		contentPane.setLayout(null);
		this.setVisible(true);
	}

}
