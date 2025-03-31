package pruebas;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import apykeys.Apykeys;
import excepciones.LogicaException;
import excepciones.PersistenciaException;
import logica.Sistema;
import modelo.Articulo;
import modelo.Cliente;
import modelo.Local;
import persistencia.GestorJDBC;
import persistencia.StatemedSingelton;

public class PruebasIniciales {

	public static void main(String[] args) throws PersistenciaException, SQLException, LogicaException{
		// IMPORTANTE HAY QUE DETERMINAR QUE BASE DE DATOS VAMOS A USAR, EL 1 REPRESENTA LA BASE DE DATOS ESTANDAR
		// ESTO EN VERSION FINAL DEBERIA ESTAR ENCRIPTADO
		Apykeys.setBaseDatosFinal(3);
		
		GestorJDBC.reiniciarPersistencia();
		//System.out.println("Llega");
		Sistema s = Sistema.getInstance();
		 /*
		try {
			Class.forName("org.postgresql.Driver");
			System.out.println(Apykeys.getBaseDatosFinal());
			System.out.println(Apykeys.getPassword());
			Connection con = DriverManager.getConnection(Apykeys.getBaseDatosFinal(), "u0xwhahreely5fup7nq3", Apykeys.getPassword());
			Statement st = con.createStatement();
			st.close();
			System.out.println("Exito");
		} catch (ClassNotFoundException e) {
			throw new PersistenciaException("ERROR en la conexion");
		}
		*/
		
		
		System.out.println("----------------ERRORES SALEN AQUI---------------------------- \n");
		cargaDatos(s);
		//cerrarReserva(7,s);
		/*
		 * try {
		 * 	s.eliminarCliente("veronicapersonal1995@gmail.com");
		} catch (LogicaException e) {
			System.out.println("Error Eliminar");
			System.out.println(e.getMessage());
		}
		 */
		/*
		comprobacionLoginsDesktop("admin","adm1n",s);
		comprobacionLoginsDesktop("adm1n","admin",s);
		comprobacionLoginsDesktop("adm1n","adm1n",s);
		comprobacionLoginsDesktop("admin","admin",s);
		comprobacionLoginsMovil("veroonicapersonal1995@gmail.com","miau",s);
		comprobacionLoginsMovil("veronicapersonal1995@gmail.com","miaau",s);
		comprobacionLoginsMovil("veroonicapersonal1995@gmail.com","miaau",s);
		comprobacionLoginsMovil("veronicapersonal1995@gmail.com","miau",s);
		 */
		
		// comprobarGestorEmail(new Cliente("diego-delosrios@hotmail.com","Diegito","Gonzalez","miau2"),s);
		s.abrirIncidencia(s.buscarReserva(1),"Informe de incidencia en consignariado\r\n"
				+ "\r\n"
				+ "Fecha: 20 de enero de 2025\r\n"
				+ "Hora: 10:30 AM\r\n"
				+ "Ubicación: Almacén Central\r\n"
				+ "\r\n"
				+ "Descripción de la incidencia:\r\n"
				+ "Durante el proceso de recepción de mercancías, se detectó un error en la asignación de paquetes. Este fallo generó un desajuste en los registros del inventario, lo que provocó retrasos en las operaciones y confusión en la ubicación de las unidades.\r\n"
				+ "\r\n"
				+ "Impacto:\r\n"
				+ "El error afectó a 12 operaciones, generando demoras en la entrega de mercancías y la necesidad de realizar ajustes manuales para identificar los paquetes afectados.\r\n"
				+ "\r\n"
				+ "Acciones tomadas:\r\n"
				+ "Se detuvieron temporalmente las operaciones, se corrigieron los registros de forma manual y se informó al equipo técnico para revisar el sistema de gestión.\r\n"
				+ "\r\n"
				+ "Responsable del informe:\r\n"
				+ "María López, Supervisora de Logística");
		
		System.out.println("Fin");
	}

	private static void comprobarGestorEmail(Cliente c,Sistema s) {
		try {
			int token = s.generarToken(c);
			s.comprobarToken(c, token, token);
		} catch (LogicaException e) {
			System.out.println(e.getMessage());
		}
	}

	private static void comprobacionLoginsDesktop(String user,String password,Sistema s) {
		try {
			System.out.println(s.loginDesktop(user, password));
		} catch (LogicaException e) {
			System.out.println(e.getMessage());
		}
		
	}

	private static void comprobacionLoginsMovil(String user,String password, Sistema s) {
		try {
			System.out.println(s.loginMovil(user, password));
		} catch (LogicaException e) {
			System.out.println(e.getMessage());
		}
		
	}

	private static void cerrarReserva(int idReserva,Sistema s) {
		try {
			s.cerrarReserva(idReserva);
		} catch (LogicaException e) {
			System.out.println(e.getMessage());
		}
	}

	private static void cargaDatos(Sistema s) {
		
		Local l = new Local("0001-304", 0, 0, "zombis magicos");
		Local l2 = new Local("978594-18283", 0, 0, "Unendo");
		Local l3 = new Local("978594-18283", 0, 0, "Unendo");
		
		try {
			s.addCliente(new Cliente("veronicapersonal1995@gmail.com","Veronica","Gonzalez","miau"));
			s.addCliente(new Cliente("diegoestuvoaqui@gmail.com","Diego","De los rios","bizcocho"));
			s.addCliente(new Cliente("laura_smith@hotmail.com","Laura","Smith","gatito123"));
			s.addCliente(new Cliente("juan_perez@yahoo.com","Juan","Perez","contraseña1"));
			s.addCliente(new Cliente("maria.jimenez@outlook.com","Maria","Jimenez","dulcecafe"));
			s.addCliente(new Cliente("carlos89@gmail.com","Carlos","Ruiz","montaña456"));
			s.addCliente(new Cliente("sandra_vega@hotmail.com","Sandra","Vega","solyluna"));
			s.addCliente(new Cliente("andres.lopez@yahoo.com","Andres","Lopez","estrella789"));
			s.addCliente(new Cliente("patricia_fernandez@outlook.com","Patricia","Fernandez","rivera2024"));
			s.addCliente(new Cliente("david.romero@gmail.com","David","Romero","bosqueverde"));
			s.addCliente(new Cliente("cristina_morales@hotmail.com","Cristina","Morales","nubegris"));
			s.addCliente(new Cliente("alejandro_garcia@yahoo.com","Alejandro","Garcia","azulmarino"));
			s.addCliente(new Cliente("sofia_martinez@outlook.com","Sofia","Martinez","flor1234"));
			s.addCliente(new Cliente("javier_rodriguez@gmail.com","Javier","Rodriguez","cieloabierto"));
			s.addCliente(new Cliente("paula_gomez@hotmail.com","Paula","Gomez","viento2023"));
			s.addCliente(new Cliente("ricardo_navarro@yahoo.com","Ricardo","Navarro","oceanoazul"));
			s.addCliente(new Cliente("natalia_diaz@outlook.com","Natalia","Diaz","nocheclara"));
			s.addCliente(new Cliente("francisco_mendez@gmail.com","Francisco","Mendez","lluviasuave"));
			s.addCliente(new Cliente("lucia_ramos@hotmail.com","Lucia","Ramos","hojasverdes"));
			s.addCliente(new Cliente("adrian_castillo@yahoo.com","Adrian","Castillo","rocadura"));
			s.addLocal(l);
			s.addLocal(l2);
			s.addLocal(l3);
		} catch (LogicaException e) {
			System.out.println(e.getMessage());
		}
		
		// System.out.println(s.listarDatos());
		
		try {
			s.addReserva("veronicapersonal1995@gmail.com", 2,"Mediana");
			s.addReserva("veronicapersonal1995@gmail.com", 1,"Pequeña");
			s.addReserva("diegoestuvoaqui@gmail.com", 3,"Pequeña");
			s.addReserva("veronicapersonal1995@gmail.com", 1,"Grande");
		} catch (LogicaException e) {
			System.out.println(e.getMessage());
		}
		
		try {
			s.addArticulo(1, "Coca cola", 60, 115, "botecoca");
			s.addArticulo(1, "Moca cola", 50, 100, "botecoca2");
			s.addArticulo(3, "Foca cola", 50, 100, "botecoca2");
			// s.eliminarArticulo("3-1");
			s.actualizarArticulo(new Articulo("1-2",1,"Moca cola",67,8989,"monthyPithon"));
		} catch (LogicaException e) {
			System.out.println(e.getMessage());
		}
		
	}

}
