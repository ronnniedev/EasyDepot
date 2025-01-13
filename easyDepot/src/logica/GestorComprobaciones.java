package logica;

import java.sql.Timestamp;

public class GestorComprobaciones {
	
	/**
	 * Comprueba si la fecha de salida no es nula, en cuyo caso devuelve un boolean como true.
	 * @param fecha : Tiemstamp
	 * @return boolean
	 */
	public static boolean comprobarFechaSalida(Timestamp fecha) {
		
		if(fecha != null) {
			return true;
		}
		return false;
	}

}
