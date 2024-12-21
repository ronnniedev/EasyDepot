package modelo;

public class Cliente {

	private String email;
	private String nombre;
	private String apellidos;
	private String password;
	private int puntosTienda;
	private int numeroReservas;
	
	
	public Cliente(String email, String nombre, String apellidos, String password) {
		this.email = email;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.password = password;
		this.puntosTienda = 0;
		this.numeroReservas = 0;
	}


	public Cliente(String email, String nombre, String apellidos, String password, int puntosTienda,
			int numeroReservas) {
		super();
		this.email = email;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.password = password;
		this.puntosTienda = puntosTienda;
		this.numeroReservas = numeroReservas;
	}


	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}


	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}


	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}


	/**
	 * @param nombre the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	/**
	 * @return the apellidos
	 */
	public String getApellidos() {
		return apellidos;
	}


	/**
	 * @param apellidos the apellidos to set
	 */
	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}


	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}


	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}


	/**
	 * @return the puntosTienda
	 */
	public int getPuntosTienda() {
		return puntosTienda;
	}


	/**
	 * @param puntosTienda the puntosTienda to set
	 */
	public void setPuntosTienda(int puntosTienda) {
		this.puntosTienda = puntosTienda;
	}


	/**
	 * @return the numeroReservas
	 */
	public int getNumeroReservas() {
		return numeroReservas;
	}


	/**
	 * @param numeroReservas the numeroReservas to set
	 */
	public void setNumeroReservas(int numeroReservas) {
		this.numeroReservas = numeroReservas;
	}


	@Override
	public String toString() {
		return "Cliente [email=" + email + ", nombre=" + nombre + ", apellidos=" + apellidos + ", password=" + password
				+ ", puntosTienda=" + puntosTienda + ", numeroReservas=" + numeroReservas + "]";
	}
	
	
	
}
