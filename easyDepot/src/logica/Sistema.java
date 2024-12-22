package logica;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import excepciones.LogicaException;
import excepciones.PersistenciaException;
import modelo.Cabina;
import modelo.Cliente;
import modelo.Email;
import modelo.Local;
import modelo.Reserva;
import persistencia.GestorJDBC;

public class Sistema {
	
	private Map <Email,Cliente> clientes;
	private List <Local> locales;
	private List <Reserva> reservas;
	private int numeroReservas;
	private GestorJDBC gestor;
	
	
	public Sistema() throws PersistenciaException, SQLException {
		this.gestor = new GestorJDBC();
		this.clientes = gestor.leerClientes();
		this.locales = gestor.leerLocales();
		rellenarCabinas();
		this.reservas = gestor.leerReservas();
		
		// calculamos el numero de reservas que ha habido en el sistema
		this.numeroReservas = calcularNumeroReservas();
	}
	
	private void rellenarCabinas() throws PersistenciaException {
		List <Cabina> cabinas = gestor.leerCabinas();
		for(Local l: locales) {
			for(Cabina c: cabinas) {
				
				String trozos[] = c.getIdCabina().split("-");
				
				if(trozos[0].compareTo(l.getLocalId() + "") == 0) {
					l.getCabinas().add(c);
				}
				
			}
		}
	}

	/**
	 * Calcula el numero de reservas que ha habido en el sistema, para poder
	 * asignar de manera correcta las ids de las reservas
	 * @return int : numero
	 */
	private int calcularNumeroReservas() {
		int max = Integer.MIN_VALUE;
		int numero = 0;
		
		for(Reserva r: reservas) {
			if(r.getIdReserva() > max) {
				max = r.getIdReserva();
				numero = r.getIdReserva();
			}
		}
		
		return numero;
	}


	public Boolean addCliente(Cliente c) throws LogicaException {
		if(clientes.containsKey(new Email(c.getEmail()))) {
			throw new LogicaException("Error email ya registrado");
		}
		
		clientes.put(new Email(c.getEmail()), c);
		gestor.insertarCliente(c);
		return true;
	}	
	
	public Boolean addLocal(Local l) throws LogicaException {
		
		if(buscarLocal(l.getLocalId()) != null) {
			throw new LogicaException("ERROR este local ya existe");
		}
		
		gestor.insertarLocal(l);
		gestor.insertarCabinas(l.rellenarCabinas());
		return locales.add(l);
	}
	
	public Boolean addReserva(String email,int idLocal,String tipoCabina) throws LogicaException {
		Local l = buscarLocal(idLocal);
		
		if(l == null) {
			throw new LogicaException("ERROR Local no encontrado");
		}
		
		Cliente c = clientes.get(new Email(email));
		
		if(c == null) {
			throw new LogicaException("ERROR Cliente no encontrado");
		}
		Cabina cab = buscarCabinaDisponible(l,tipoCabina);
		
		Reserva r = new Reserva(c,l, numeroReservas + 1,cab);
		
		gestor.insertarReserva(r);
		reservas.add(r);
		
		// Incrementamos en 1 el numero de reservas en el cliente y en el local
		l.setNumeroReservas(l.getNumeroReservas() + 1);
		c.setNumeroReservas(c.getNumeroReservas() + 1);
		c.setPuntosTienda(c.getPuntosTienda()+10);
		cab.setReservada(true);
		
		numeroReservas++;
		return true;
	}
	

	private Cabina buscarCabinaDisponible(Local l, String tipoCabina) throws LogicaException {
		for(Cabina c: l.getCabinas()) {
			if(c.getTipo().compareTo(tipoCabina) == 0 && !c.getReservada()) {
				return c;
			}
		}
		throw new LogicaException("No hay cabina disponible de ese tipo");
	}

	private Object buscarReserva(int idReserva) {
		
		for(Reserva r: reservas) {
			if(r.getIdReserva() == idReserva) {
				return r;
			}
		}
		
		return null;
	}


	private Local buscarLocal(int localId) {
		
		for (Local l: locales) {
			if(l.getLocalId() == localId) {
				return l;
			}
		}
		return null;
	}


	public String listarDatos() {
		String texto = "------------Datos en Sistema-------------\n";
		texto += "--------------Clientes-----------------\n";
		
		for(Cliente c: clientes.values()) {
			texto += c.toString() + "\n";
		}
		
		texto += "----------------Locales---------------\n";
		
		for(Local l: locales) {
			texto += l.toString() + "\n";
			texto += l.visualizarCabinas();
		}
		
		texto += "--------------------Reservas-------------\n";
		
		for(Reserva r: reservas) {
			texto += r.toString() + "\n";
		}
		
		return texto;
	}
	

}
