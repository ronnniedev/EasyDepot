package modelo;

public class Articulo {

	private int idArticulo;
	private int idLocal;
	private String nombre;
	private double precio;
	private String imagen;
	
	
	public Articulo(int idArticulo, int idLocal, String nombre, double precio, String imagen) {
		this.idArticulo = idArticulo;
		this.idLocal = idLocal;
		this.nombre = nombre;
		this.precio = precio;
		this.imagen = imagen;
	}


	/**
	 * @return the idArticulo
	 */
	public int getIdArticulo() {
		return idArticulo;
	}


	/**
	 * @param idArticulo the idArticulo to set
	 */
	public void setIdArticulo(int idArticulo) {
		this.idArticulo = idArticulo;
	}


	/**
	 * @return the idLocal
	 */
	public int getIdLocal() {
		return idLocal;
	}


	/**
	 * @param idLocal the idLocal to set
	 */
	public void setIdLocal(int idLocal) {
		this.idLocal = idLocal;
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
	 * @return the precio
	 */
	public double getPrecio() {
		return precio;
	}


	/**
	 * @param precio the precio to set
	 */
	public void setPrecio(double precio) {
		this.precio = precio;
	}


	/**
	 * @return the imagen
	 */
	public String getImagen() {
		return imagen;
	}


	/**
	 * @param imagen the imagen to set
	 */
	public void setImagen(String imagen) {
		this.imagen = imagen;
	}


	@Override
	public String toString() {
		return "Articulo [idArticulo=" + idArticulo + ", idLocal=" + idLocal + ", nombre=" + nombre + ", precio="
				+ precio + ", imagen=" + imagen + "]";
	}
	
	
	
	
	
}
