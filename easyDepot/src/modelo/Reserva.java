package modelo;

import java.sql.Date;

public class Reserva {
	
	private int idReserva;
	private String emailCliente;
	private String idCabina;
	private Date fechaInicio;
	private Date fechaSalida;
	private boolean incidencia;
	private String descripcionIncidencia;
	
	public Reserva(int idReserva, String emailCliente, String idCabina, Date fechaInicio, Date fechaSalida,
			boolean incidencia, String descripcionIncidencia) {
		this.idReserva = idReserva;
		this.emailCliente = emailCliente;
		this.idCabina = idCabina;
		this.fechaInicio = fechaInicio;
		this.fechaSalida = fechaSalida;
		this.incidencia = incidencia;
		this.descripcionIncidencia = descripcionIncidencia;
	}

	public Reserva(Cliente c, Local l,int idReserva, Cabina cab) {
		this.idReserva = idReserva;
		this.emailCliente = c.getEmail();
		this.idCabina = cab.getIdCabina();
		this.fechaInicio = new Date(System.currentTimeMillis());
		this.fechaSalida = null;
		this.incidencia = false;
		this.descripcionIncidencia = "Sin Incidencias";
		
	}


	/**
	 * @return the idReserva
	 */
	public int getIdReserva() {
		return idReserva;
	}

	/**
	 * @param idReserva the idReserva to set
	 */
	public void setIdReserva(int idReserva) {
		this.idReserva = idReserva;
	}

	/**
	 * @return the emailCliente
	 */
	public String getEmailCliente() {
		return emailCliente;
	}

	/**
	 * @param emailCliente the emailCliente to set
	 */
	public void setEmailCliente(String emailCliente) {
		this.emailCliente = emailCliente;
	}

	/**
	 * @return the idCabina
	 */
	public String getIdCabina() {
		return idCabina;
	}

	/**
	 * @param idCabina the idCabina to set
	 */
	public void setIdCabina(String idCabina) {
		this.idCabina = idCabina;
	}

	/**
	 * @return the fechaInicio
	 */
	public Date getFechaInicio() {
		return fechaInicio;
	}

	/**
	 * @param fechaInicio the fechaInicio to set
	 */
	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	/**
	 * @return the fechaSalida
	 */
	public Date getFechaSalida() {
		return fechaSalida;
	}

	/**
	 * @param fechaSalida the fechaSalida to set
	 */
	public void setFechaSalida(Date fechaSalida) {
		this.fechaSalida = fechaSalida;
	}

	/**
	 * @return the incidencia
	 */
	public boolean isIncidencia() {
		return incidencia;
	}

	/**
	 * @param incidencia the incidencia to set
	 */
	public void setIncidencia(boolean incidencia) {
		this.incidencia = incidencia;
	}

	/**
	 * @return the descripcionIncidencia
	 */
	public String getDescripcionIncidencia() {
		return descripcionIncidencia;
	}

	/**
	 * @param descripcionIncidencia the descripcionIncidencia to set
	 */
	public void setDescripcionIncidencia(String descripcionIncidencia) {
		this.descripcionIncidencia = descripcionIncidencia;
	}

	@Override
	public String toString() {
		return "Reserva [idReserva=" + idReserva + ", emailCliente=" + emailCliente + ", idCabina=" + idCabina
				+ ", fechaInicio=" + fechaInicio + ", fechaSalida=" + fechaSalida + ", incidencia=" + incidencia
				+ ", descripcionIncidencia=" + descripcionIncidencia + "]";
	}
	
	
	
	
	

}
