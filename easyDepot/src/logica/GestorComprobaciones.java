package logica;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import excepciones.LogicaException;

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

	/**
	 * Comprueba que el correo esta bien escrito y entre los proovedores permitidos, si no lanza una excepcion.
	 * @param email : String
	 * @return Boolean
	 * @throws LogicaException
	 */
	public static boolean comprobarEmail(String email) throws LogicaException {
		List <String> correosCorrectos = new ArrayList();
		correosCorrectos.add("@yahoo.com");
		correosCorrectos.add("@gmail.com");
		correosCorrectos.add("@outlook.com");
		correosCorrectos.add("@hotmail.com");
		
		if(email.startsWith("@")) {
			throw new LogicaException("ERROR asegurese de que el formato del email es de xxx@email.com");
		}
		
		for(String comprobante: correosCorrectos) {
			if(email.endsWith(comprobante)) {
				return true;
			}
		}
		throw new LogicaException("ERROR asegurese de que el formato del email es de xxx@email.com");
	}
	/**
	 * Comprueba si hay campos vacios, en cuyo caso lanza excepcion
	 * @param palabras : List <String>
	 * @return Boolean
	 * @throws LogicaException
	 */
	public static boolean comprobarTextoVacio(List <String> palabras) throws LogicaException {
		
		
		for(String palabra: palabras) {
			if(palabra.isBlank()) {
				throw new LogicaException("ERROR no puede haber campos vacios");
			}
		}
		return true;
	}
	
	

}
