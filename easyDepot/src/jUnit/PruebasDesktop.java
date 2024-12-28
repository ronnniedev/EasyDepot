package jUnit;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import excepciones.LogicaException;
import excepciones.PersistenciaException;
import logica.Sistema;
import modelo.Cliente;
import modelo.Local;
import modelo.Reserva;
import persistencia.GestorJDBC;

class PruebasDesktop {
	
	private static Sistema s;

	@Test
	void testLoginDesktop() throws PersistenciaException, SQLException, LogicaException {
	
		// password mal metido
		assertThrows(LogicaException.class,()->s.loginDesktop("admin", "adm1n"));
		// usuario mal metido
		assertThrows(LogicaException.class,()->s.loginDesktop("adm1n", "admin"));
		// usuario y password mal introducidos
		assertThrows(LogicaException.class,()->s.loginDesktop("adm1n", "adm1n"));
		// Datos correctos
		assertTrue(s.loginDesktop("admin", "admin"));
	}
	
	@Test
	void testLoginMovil() throws PersistenciaException, SQLException, LogicaException {
		
		// password mal introducida
		assertThrows(LogicaException.class,()->s.loginMovil("veronicapersonal1995@gmail.com", "wrongpassword"));
		// email mal introducido
		assertThrows(LogicaException.class,()->s.loginMovil("wrongemail", "miau"));
		// email y password mal introducidos
		assertThrows(LogicaException.class,()->s.loginMovil("wrongemail", "wrongpassword"));
		// datos correctos
		assertTrue(s.loginMovil("veronicapersonal1995@gmail.com", "miau"));
		
	}
	
	@Test
	void testBuscarCabina() throws PersistenciaException, SQLException, LogicaException {
		// buscando una cabina en un local que existe y tiene esta cabina
		assertTrue(s.buscarCabina("1-1", s.buscarLocal(1)) != null);
		// buscando una cabina que existe en un local que no tiene esa cabina
		assertTrue(s.buscarCabina("1-1", s.buscarLocal(3)) == null);
		// buscando una cabina que no existe en un local que existe
		assertTrue(s.buscarCabina("1-7", s.buscarLocal(1)) == null);
		
	}
	
	@Test
	void testBuscarLocal() throws PersistenciaException, SQLException, LogicaException {
		// Buscar un local que existe
		assertTrue(s.buscarLocal(1) != null);
		// Buscar un local que no existe
		assertTrue(s.buscarLocal(4) == null);
		
	}
	
	@Test
	void testBuscarReserva() throws PersistenciaException, SQLException, LogicaException {
		
		// Buscar una reserva que existe
		assertTrue(s.buscarReserva(1) != null);
		// Buscar una reserva que no existe
		assertTrue(s.buscarReserva(12) == null);
	}
	
	@Test
	void testbuscarReservasCliente() throws PersistenciaException, SQLException, LogicaException {
		List <Reserva> test1 = s.buscarReservasCliente("veronicapersonal1995@gmail.com");
		List <Reserva> test2 = s.buscarReservasCliente("diegoestuvoaqui@gmail.com");
		List <Reserva> test3 = s.buscarReservasCliente("wrongemail");
		
		
		// Cliente con 3 reservas
		assertTrue(test1.size()==3);
		// Cliente con 1 reserva
		assertTrue(test2.size()==1);
		// Cliente con 0 reservas
		assertTrue(test3.size()==0);
		
	}
	
	@Test
	void testaddReserva() throws PersistenciaException, SQLException, LogicaException {
		
		// Correco incorrecto
		assertThrows(LogicaException.class,() -> s.addReserva("wrongemail", 2, "Pequeña"));
		// Local inexistente
		assertThrows(LogicaException.class,() -> s.addReserva("veronicapersonal1995@gmail.com", 4, "Pequeña"));
		// Tipo de cabina inexistente
		assertThrows(LogicaException.class,() -> s.addReserva("veronicapersonal1995@gmail.com", 2, "Mini"));
		
		// Reserva correcta
		assertTrue(s.addReserva("veronicapersonal1995@gmail.com", 2, "Pequeña"));
	}
	
	@Test
	void testaddLocal() throws PersistenciaException, SQLException, LogicaException {
		
		// Local ya con id ya existente
		assertThrows(LogicaException.class,() -> s.addLocal(new Local(1,"3924o34",0,0,"Unendo2")));
		
		// Local no existente por lo tanto se crea
		assertTrue(s.addLocal(new Local(4,"3924o34",0,0,"Unendo2")));
		
	}
	
	@Test
	void testaddCliente() throws PersistenciaException, SQLException, LogicaException {
		
		// Cliente ya figura en el sistema
		assertThrows(LogicaException.class,() -> s.addCliente
				(new Cliente("veronicapersonal1995@gmail.com","Veronica","Gonzalez","miau")) );
		
		// Cliente nuevo
		assertTrue(s.addCliente(new Cliente("test@gmail.com","Veronica","Gonzalez","miau")));
		
	}
	
	@Test
	void testeliminarCliente() throws PersistenciaException, SQLException, LogicaException {
		
		// Cliente no existe
		assertThrows(LogicaException.class,() -> s.eliminarCliente("test@gmail.com"));
		
		// Cliente se elimina
		assertTrue(s.eliminarCliente("veronicapersonal1995@gmail.com"));
		
	}
	
	@BeforeEach
	private void cargaDatos() throws PersistenciaException, SQLException, LogicaException {
		GestorJDBC.reiniciarPersistencia();
		s = new Sistema();
		GestorJDBC.crearTablas();
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
