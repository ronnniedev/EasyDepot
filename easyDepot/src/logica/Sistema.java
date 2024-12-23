package logica;

import java.sql.SQLException;
import java.sql.Timestamp;
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
	
	/**
	 * Constructor de 0 parametros de la clase Sistema
	 * prepara los elementos necesarios para el funcionamiento del sistema siendo este el gestor
	 * y las listas de datos que prepararn todos los objetos a utilizar
	 * @throws PersistenciaException
	 * @throws SQLException
	 */
	public Sistema() throws PersistenciaException, SQLException {
		this.gestor = new GestorJDBC();
		this.clientes = gestor.leerClientes();
		this.locales = gestor.leerLocales();
		rellenarCabinas();
		this.reservas = gestor.leerReservas();
		
		// calculamos el numero de reservas que ha habido en el sistema
		this.numeroReservas = calcularNumeroReservas();
	}
	
	/**
	 * Lee todas las cabinas alojadas en la base de datos y las asigna a sus locales pertinentes
	 * @throws PersistenciaException
	 */
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

	/**
	 * Agrega un cliente al sistema y lo inserta dentro de la base de datos
	 * @param c : Cliente
	 * @return Boolean
	 * @throws LogicaException
	 */
	public Boolean addCliente(Cliente c) throws LogicaException {
		if(clientes.containsKey(new Email(c.getEmail()))) {
			throw new LogicaException("Error email ya registrado");
		}
		
		clientes.put(new Email(c.getEmail()), c);
		gestor.insertarCliente(c);
		return true;
	}	
	
	/**
	 * Mete un local dentro del sistema y lo inserta dentro de la base de datos
	 * @param l : Local
	 * @return boolean
	 * @throws LogicaException
	 */
	public Boolean addLocal(Local l) throws LogicaException {
		
		if(buscarLocal(l.getLocalId()) != null) {
			throw new LogicaException("ERROR este local ya existe");
		}
		
		gestor.insertarLocal(l);
		gestor.insertarCabinas(l.rellenarCabinas());
		return locales.add(l);
	}
	
	/**
	 * Mete una reserva en el sistema y la inserta dentro de la base de datos
	 * actualiza los valores asociado al cliente, aumentado su numero de reservas, y dandole 10 puntos
	 * en la tienda.
	 * Tambien actualiza el valor de reservas en el local y en los ingresos del sistema
	 * @param email : String
	 * @param idLocal : int
	 * @param tipoCabina : String
	 * @return boolean
	 * @throws LogicaException
	 */
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
		
		// Actualizamos valores en sistema
		l.setNumeroReservas(l.getNumeroReservas() + 1);
		l.setIngresos(l.getIngresos() + 8.10);
		c.setNumeroReservas(c.getNumeroReservas() + 1);
		c.setPuntosTienda(c.getPuntosTienda()+10);
		cab.setReservada(true);
		
		// Actualizamos valores en base de datos
		gestor.actualizarCliente(c);
		gestor.actualizarLocal(l);
		gestor.actualizarCabina(cab);
		
		numeroReservas++;
		return true;
	}
	/**
	 * Busca una cabina en el sistema y devuelve el objeto pertinente, si no devuelve valor nulo
	 * @param idCabina : String
	 * @param l : Local
	 * @return c : cabina
	 */
	private Cabina buscarCabina(String idCabina, Local l) {
		
		List <Cabina> cabinas = l.getCabinas();
		
		for(Cabina c: cabinas) {
			if(c.getIdCabina().compareTo(idCabina) == 0) {
				return c;
			}
		}
		return null;
	}
	
	/**
	 * Devuelve una cabina disponible del tipo proporcionado, siendo este "Pequeña" "Mediana" y "Grande"
	 * @param l : Local
	 * @param tipoCabina : String
	 * @return c : Cabina
	 * @throws LogicaException
	 */
	private Cabina buscarCabinaDisponible(Local l, String tipoCabina) throws LogicaException {
		for(Cabina c: l.getCabinas()) {
			if(c.getTipo().compareTo(tipoCabina) == 0 && !c.getReservada()) {
				return c;
			}
		}
		throw new LogicaException("No hay cabina disponible de ese tipo");
	}

	/**
	 * Busca una reserva en el sistema devolviendolo con forma de objeto, en caso de no encontrar devuelve valor nulo
	 * @param idReserva : int
	 * @return
	 */
	private Reserva buscarReserva(int idReserva) {
		
		for(Reserva r: reservas) {
			if(r.getIdReserva() == idReserva) {
				return r;
			}
		}
		
		return null;
	}

	/**
	 * Busca un objeto Local dentro del sistema y lo devuelve, en caso de no encontrarlo devuelve nulo
	 * @param localId : int
	 * @return
	 */
	private Local buscarLocal(int localId) {
		
		for (Local l: locales) {
			if(l.getLocalId() == localId) {
				return l;
			}
		}
		return null;
	}


	/**
	 * Lista todos los datos del sistema y lo devuelve en formato de texto
	 * @return texto : String
	 */
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

	/**
	 * Cierra una reserva en el sistema actualizando el valor de la Fecha salida de la reserva proporcionada
	 * tambien actualiza la cabina para que vuelva a estar disponible 
	 * @param idReserva : int
	 * @throws LogicaException
	 */
	public void cerrarReserva(int idReserva) throws LogicaException {
		Reserva r = buscarReserva(idReserva);
		
		if (r == null) {
			throw new LogicaException("ERROR reserva no figura en el sistema");
		}
		if(r.getFechaSalida() != null) {
			throw new LogicaException("ERROR Esta reserva ya esta cerrada");
		}
		// extraemos las ids alojadas en la id de la cabina siendo la 0 la de el local
		String ids[] = r.getIdCabina().split("-");
		
		Local l = buscarLocal(Integer.parseInt(ids[0]));
		
		if(l == null) {
			throw new LogicaException("ERROR local no encontrado en el sistema");
		}
		
		Cabina c = buscarCabina(r.getIdCabina(),l);
		
		if(c == null) {
			throw new LogicaException("ERROR cabina no encontrada en el sistema");
		}
		
		
		
		r.setFechaSalida(new Timestamp(System.currentTimeMillis()));
		c.setReservada(false);
		gestor.actualizarCabina(c);
		gestor.actualizarReserva(r);
	}

	
	

}
