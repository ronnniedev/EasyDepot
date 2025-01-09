package logica;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import apykeys.Apykeys;
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
	private GestorCorreo gestorCorreo;
	private static Sistema s;
	
	/**
	 * Constructor de 0 parametros de la clase Sistema
	 * prepara los elementos necesarios para el funcionamiento del sistema siendo este el gestor
	 * y las listas de datos que prepararn todos los objetos a utilizar
	 * @throws PersistenciaException
	 * @throws SQLException
	 * @throws LogicaException 
	 */
	public Sistema() throws PersistenciaException, SQLException, LogicaException {
		this.gestor = new GestorJDBC();
		this.gestorCorreo = new GestorCorreo();
		this.clientes = gestor.leerClientes();
		this.locales = gestor.leerLocales();
		rellenarCabinas();
		this.reservas = gestor.leerReservas();
		// calculamos el numero de reservas que ha habido en el sistema
		this.numeroReservas = calcularNumeroReservas();
		comprobarCuentaEliminado();
	}
	/**
	 * Comprueba si existe la cuenta "Eliminado" en el sistema, si no es asi , la crea
	 * @throws  LogicaException
	 */
	private void comprobarCuentaEliminado() throws LogicaException {
		if(clientes.get(new Email("Eliminado")) == null) {
			addCliente(new Cliente("Eliminado","Cuenta eliminada","NO","BORRAR"));
		}
	}
	
	
	/**
	 * Devuelve el contexto del sistema, en caso de no existir el mismo se crea, usado para proporcionar contexto a las
	 * diferentes interfaces de movil como las de desktop
	 * @return Sistema : s
	 * @throws PersistenciaException
	 * @throws SQLException
	 * @throws LogicaException
	 */
	public static Sistema getInstance() throws PersistenciaException, SQLException, LogicaException {
		if(s == null) {
			s = new Sistema();
		}
		return s;
	}
	/**
	 * Genera un token que el cliente debera introducir para confirmar el registro de la cuenta
	 * @param c : Cliente
	 * @return int token
	 * @throws LogicaException
	 */
	public int generarToken(Cliente c) throws LogicaException {
		if(clientes.containsKey(new Email(c.getEmail()))) {
			throw new LogicaException("Este email ya esta registrado");
		}
		
		return gestorCorreo.createEmail(c.getEmail());
	}
	
	/**
	 * Comprueba que el token introducido por el cliente es correcto
	 * @param c : Cliente
	 * @param token : Int
	 * @param tokenIntroducido : Int
	 * @throws LogicaException
	 */
	public void comprobarToken(Cliente c,int token,int tokenIntroducido) throws LogicaException {
		if(token == tokenIntroducido) {
			addCliente(c);
			return;
		}
		throw new LogicaException("ERROR al introducir el token, intentelo de nuevo");
	}
	
	/**
	 * Sistema de login basico para la app de Desktop, contraseña y usuario basicos admin
	 * @param user : String
	 * @param password : String
	 * @return boolean 
	 * @throws LogicaException
	 */
	public Boolean loginDesktop(String user,String password) throws LogicaException {
		if(user.compareTo("admin") == 0 && password.compareTo("admin") == 0) {
			return true;
		}
		throw new LogicaException("ERROR usuario o contraseña incorrectos");
	}
	
	/**
	 * Sistema de login para aplicación movil, a traves de el el usuario podra acceder al sistema proporcionando un 
	 * usuario en forma de email y una contraseña
	 * @param userEmail : String
	 * @param password : String
	 * @return Boolean
	 * @throws LogicaException
	 */
	public Boolean loginMovil(String userEmail,String password) throws LogicaException {
		Cliente user = clientes.get(new Email(userEmail));
		
		if(user != null && user.getPassword().compareTo(password) == 0) {
			return true;
		}
		throw new LogicaException("ERROR usuario o contraseña incorrectos");
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
	 * Elimina el cliente mediante el email proporcionado, al hacerlo asigna todas las reservas a la cuenta de 
	 * eliminacion para que las reservas sigan funcionando, tambien actualiza los valores pertinentes antes del 
	 * borrado del cliente. Devuelve true si todo se ha realizado correctamente.
	 * @param email : String
	 * @return boolean
	 * @throws LogicaException
	 */
	public boolean eliminarCliente(String email) throws LogicaException {
		Cliente c = clientes.get(new Email(email));
		
		if(c == null) {
			throw new LogicaException("ERROR cliente no figura en base de datos");
		}
		
		Cliente cuentaParaEliminados = clientes.get(new Email("Eliminado"));
		
		List <Reserva> reservasClientes = buscarReservasCliente(c.getEmail());
		gestor.eliminarCliente(c, reservasClientes);
		
		// actualizamos los valores de la cuenta para Eliminados
		cuentaParaEliminados.setNumeroReservas(cuentaParaEliminados.getNumeroReservas() + c.getNumeroReservas());
		cuentaParaEliminados.setPuntosTienda(cuentaParaEliminados.getPuntosTienda() + c.getPuntosTienda());
		gestor.actualizarCliente(cuentaParaEliminados);
		
		// Actualizamos todos los valores en sistema a Eliminado de este cliente
		for(Reserva r: reservasClientes) {
			r.setEmailCliente("Eliminado");
			
		}
		
		clientes.remove(new Email(email));
		return true;
	}
	
	/**
	 * Devuelve una lista asociada al cliente proporcionado, se supone que el email del mismo esta verificado
	 * previamente
	 * @param email : String
	 * @return
	 */
	public List<Reserva> buscarReservasCliente(String email) {
		List <Reserva> reservasClientes = new LinkedList<Reserva>();
		for(Reserva r: reservas) {
			if(r.getEmailCliente().compareTo(email) == 0) {
				reservasClientes.add(r);
			}
		}
		return reservasClientes;
	}

	/**
	 * Busca una cabina en el sistema y devuelve el objeto pertinente, si no devuelve valor nulo
	 * @param idCabina : String
	 * @param l : Local
	 * @return c : cabina
	 */
	public Cabina buscarCabina(String idCabina, Local l) {
		
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
	public Cabina buscarCabinaDisponible(Local l, String tipoCabina) throws LogicaException {
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
	public Reserva buscarReserva(int idReserva) {
		
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
	public Local buscarLocal(int localId) {
		
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
	/**
	 * Calcula las reservas del sistema
	 * @return int 
	 */
	public int calcularReservas() {
		return reservas.size();
	}
	/**
	 * Calcula los ingresos del sistema
	 * @return Double
	 */
	public Double calcularIngresos() {
		Double calculo = 0.0;
		for(Local l: locales) {
			calculo += l.getIngresos();
		}
		return calculo;
	}
	/**
	 * Calcula los locales del sistema
	 * @return int
	 */
	public int calcularLocales() {
		return locales.size();
	}
	/**
	 * Calcula los clientes del sistema restando el usuario de eliminacion
	 * @return int
	 */
	public int calcularClientes() {
		return clientes.size() - 1;
	}
	public Map<Email, Cliente> getClientes() {
		return clientes;
	}
	public void setClientes(Map<Email, Cliente> clientes) {
		this.clientes = clientes;
	}
	public List<Local> getLocales() {
		return locales;
	}
	public void setLocales(List<Local> locales) {
		this.locales = locales;
	}
	public List<Reserva> getReservas() {
		return reservas;
	}
	public void setReservas(List<Reserva> reservas) {
		this.reservas = reservas;
	}

	
	

}
