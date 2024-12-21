package interfaz;

import excepciones.LogicaException;
import logica.Sistema;
import modelo.Cliente;
import modelo.Local;

public class PruebasIniciales {

	public static void main(String[] args) {
		
		Sistema s = new Sistema();
		
		Local l = new Local(1, null, 0, 0, null);
		Local l2 = new Local(2, null, 0, 0, null);
		
		
		try {
			s.addCliente(new Cliente("veronicapersonal1995@gmail.com","Veronica","Gonzalez","miau"));
			s.addLocal(l);
			s.addLocal(l2);
		} catch (LogicaException e) {
			System.out.println(e.getMessage());
		}
		
		System.out.println(s.listarDatos());
		
		try {
			s.addReserva("veronicapersonal1995@gmail.com", 1,"Pequeña");
			s.addReserva("veronicapersonal1995@gmail.com", 1,"Pequeña");
			s.addReserva("veronicapersonal1995@gmail.com", 2,"Mediana");
			s.addReserva("veronicapersonal1995@gmail.com", 1,"Grande");
		} catch (LogicaException e) {
			System.out.println(e.getMessage());
		}
		
		System.out.println(s.listarDatos());

		
	}

}
