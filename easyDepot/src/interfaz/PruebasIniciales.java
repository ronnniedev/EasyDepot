package interfaz;

import java.sql.SQLException;

import excepciones.LogicaException;
import excepciones.PersistenciaException;
import logica.Sistema;
import modelo.Cliente;
import modelo.Local;
import persistencia.GestorJDBC;

public class PruebasIniciales {

	public static void main(String[] args) throws PersistenciaException{
		
		Sistema s = null;
		
		try {
			// GestorJDBC.reiniciarPersistencia(); // (borrar estas lineas reinicia la persistencia)
			s = new Sistema();
		} catch (PersistenciaException e) {
			System.out.println(e.getMessage());
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} catch (LogicaException e) {
			System.out.println(e.getMessage());
		}
		
		
		
		System.out.println("----------------ERRORES SALEN AQUI---------------------------- \n");
		// cargaDatos(s);
		// cerrarReserva(7,s);
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
		
		comprobarGestorEmail(new Cliente("veronicapersonal1995@gmail.com","Veronica","Gonzalez","miau"),s);
		
		
		
		System.out.println("-------------------------------------------- \n");
		System.out.println(s.listarDatos());
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
		
		Local l = new Local(1, "0001-304", 0, 0, "zombis magicos");
		Local l2 = new Local(2, "978594-18283", 0, 0, "Unendo");
		Local l3 = new Local(3, "978594-18283", 0, 0, "Unendo");
		
		
		try {
			
			s.addCliente(new Cliente("veronicapersonal1995@gmail.com","Veronica","Gonzalez","miau"));
			s.addCliente(new Cliente("diegoestuvoaqui@gmail.com","Diego","De los rios","bizcocho"));
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
		
	}

}
