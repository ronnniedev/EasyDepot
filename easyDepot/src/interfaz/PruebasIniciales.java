package interfaz;

import java.sql.SQLException;

import excepciones.LogicaException;
import excepciones.PersistenciaException;
import logica.Sistema;
import modelo.Cliente;
import modelo.Local;
import persistencia.GestorJDBC;

public class PruebasIniciales {

	public static void main(String[] args) throws PersistenciaException {
		
		Sistema s = null;
		
		try {
			GestorJDBC.reiniciarPersistencia(); // (borrar estas lineas reinicia la persistencia)
			s = new Sistema();
		} catch (PersistenciaException e) {
			System.out.println(e.getMessage());
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		
		
		
		System.out.println("----------------ERRORES SALEN AQUI---------------------------- \n");
		cargaDatos(s);
		cerrarReserva(7,s);
		
		System.out.println("-------------------------------------------- \n");
		System.out.println(s.listarDatos());
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
			s.addReserva("veronicapersonal1995@gmail.com", 1,"Pequeña");
			s.addReserva("veronicapersonal1995@gmail.com", 1,"Grande");
		} catch (LogicaException e) {
			System.out.println(e.getMessage());
		}
		
	}

}
