package persistencia;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import excepciones.PersistenciaException;
import modelo.Cabina;
import modelo.Cliente;
import modelo.Email;
import modelo.Local;
import modelo.Reserva;

public class GestorJDBC {

	public GestorJDBC() throws PersistenciaException, SQLException {
		Statement st = StatemedSingelton.getInstance();
		String consultaClientes = "CREATE TABLE IF NOT EXISTS clientes ("
				+ "    emailCliente VARCHAR(100) PRIMARY KEY,"
				+ "    nombre VARCHAR(100) NOT NULL,"
				+ "    apellidos VARCHAR(100) NOT NULL,"
				+ "    password VARCHAR(100) NOT NULL,"
				+ "	   puntosTienda INT,"
				+ "    numeroReservas INT"
				+ ");";
		String consultaLocales = "CREATE TABLE IF NOT EXISTS locales ("
				+ "    idLocal INT PRIMARY KEY,"
				+ "    coordenadas VARCHAR(100) NOT NULL,"
				+ "    numeroReservas INT NOT NULL,"
				+ "    ingresos NUMERIC(10,2) NOT NULL,"
				+ "	   direccion VARCHAR(100)"
				+ ");";
		String consultaCabinas = "CREATE TABLE IF NOT EXISTS cabinas ("
				+ "    idCabina VARCHAR(10) PRIMARY KEY,"
				+ "    idLocal INT NOT NULL,"
				+ "    abierto BOOLEAN,"
				+ "    reservada BOOLEAN,"
				+ "	   tipo VARCHAR(100) NOT NULL,"
				+ "    FOREIGN KEY (idLocal) REFERENCES locales(idLocal)"
				+ ");";
		String consultaReservas = "CREATE TABLE IF NOT EXISTS reservas ("
				+ "    idReserva INT PRIMARY KEY,"
				+ "    emailCliente VARCHAR(100) NOT NULL,"
				+ "    idCabina VARCHAR(10) NOT NULL,"
				+ "    fechaInicio DATE NOT NULL,"
				+ "    fechaSalida DATE,"
				+ "	   incidencia BOOLEAN,"
				+ "    descripcionIncidencia VARCHAR(1000),"
				+ "    FOREIGN KEY (emailCliente) REFERENCES clientes(emailCliente),"
				+ "    FOREIGN KEY (idCabina) REFERENCES cabinas(idCabina)"
				+ ");";
		
		st.executeUpdate(consultaClientes);
		st.executeUpdate(consultaLocales);
		st.executeUpdate(consultaCabinas);
		st.executeUpdate(consultaReservas);
		StatemedSingelton.close();
	}

	public Map<Email, Cliente> leerClientes() throws PersistenciaException {
		Statement st = null;
		ResultSet rs = null;
		Map <Email,Cliente> clientes = new HashMap<Email,Cliente>();
		
		try {
			st = StatemedSingelton.getInstance();
			String consulta = "SELECT * FROM clientes";
			rs = st.executeQuery(consulta);
			while(rs.next()) {
				Cliente c = prepararCliente(rs);
				clientes.put(new Email(c.getEmail()), c);
			}
		} catch (PersistenciaException e) {
			System.out.println(e.getMessage());
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}finally {
			try {
				rs.close();
				StatemedSingelton.close();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
			
		}
		return clientes;
	}

	
	public List<Local> leerLocales() throws PersistenciaException {
		Statement st = null;
		ResultSet rs = null;
		List <Local> listaLocales = new LinkedList<Local>();
		
		try {
			st = StatemedSingelton.getInstance();
			String consulta = "SELECT * FROM locales";
			rs = st.executeQuery(consulta);
			while(rs.next()) {
				listaLocales.add(prepararLocal(rs));
			}
		} catch (PersistenciaException e) {
			System.out.println(e.getMessage());
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}finally {
			try {
				rs.close();
				StatemedSingelton.close();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
			
		}
		return listaLocales;
	}
	public List<Reserva> leerReservas() throws PersistenciaException {
		Statement st = null;
		ResultSet rs = null;
		List <Reserva> listaReservas = new LinkedList<Reserva>();
		
		try {
			st = StatemedSingelton.getInstance();
			String consulta = "SELECT * FROM reservas";
			rs = st.executeQuery(consulta);
			while(rs.next()) {
				listaReservas.add(prepararReserva(rs));
			}
		} catch (PersistenciaException e) {
			System.out.println(e.getMessage());
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}finally {
			try {
				rs.close();
				StatemedSingelton.close();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
			
		}
		return listaReservas;
		
	}
	
	public List<Cabina> leerCabinas() throws PersistenciaException {
		Statement st = null;
		ResultSet rs = null;
		List <Cabina> listaCabinas = new LinkedList<Cabina>();
		
		try {
			st = StatemedSingelton.getInstance();
			String consulta = "SELECT * FROM cabinas";
			rs = st.executeQuery(consulta);
			while(rs.next()) {
				listaCabinas.add(prepararCabina(rs));
			}
		} catch (PersistenciaException e) {
			System.out.println(e.getMessage());
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}finally {
			try {
				rs.close();
				StatemedSingelton.close();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
			
		}
		return listaCabinas;
		
	}
	
	public void insertarCliente(Cliente c) {
		PreparedStatement ps = null;
		try {
			String insertCliente = "INSERT INTO clientes (emailCliente,nombre,apellidos,password"
									+ ",puntosTienda,numeroReservas)"
									+ " VALUES (?,?,?,?,?,?)";
			ps = StatemedSingelton.getInstance(insertCliente);
			ps.setString(1, c.getEmail());
			ps.setString(2, c.getNombre());
			ps.setString(3, c.getApellidos());
			ps.setString(4, c.getPassword());
			ps.setInt(5, c.getPuntosTienda());
			ps.setInt(6, c.getNumeroReservas());
			ps.executeUpdate();
		} catch (PersistenciaException e1) {
			System.out.println(e1.getMessage());
		} catch (SQLException e1) {
			System.out.println(e1.getMessage());
		}finally {
			try {
				StatemedSingelton.close();
			} catch (PersistenciaException e) {
				System.out.println(e.getMessage());
			}
		}
	}
	
	public void insertarLocal(Local l) {
		PreparedStatement ps = null;
		try {
			String insertLocal = "INSERT INTO locales (idLocal,coordenadas,numeroReservas,ingresos,direccion)"
									+ " VALUES (?,?,?,?,?)";
			ps = StatemedSingelton.getInstance(insertLocal);
			ps.setInt(1, l.getLocalId());
			ps.setString(2, l.getCoordenadas());
			ps.setInt(3, l.getNumeroReservas());
			ps.setDouble(4,l.getIngresos());
			ps.setString(5, l.getDireccion());
			ps.executeUpdate();
		} catch (PersistenciaException e1) {
			System.out.println(e1.getMessage());
		} catch (SQLException e1) {
			System.out.println(e1.getMessage());
		}finally {
			try {
				StatemedSingelton.close();
			} catch (PersistenciaException e) {
				System.out.println(e.getMessage());
			}
		}
	}
	
	public void insertarCabinas(List<Cabina> cabinas) {
		PreparedStatement ps = null;
		try {
			for(Cabina c: cabinas) {
				String insertCabina = "INSERT INTO cabinas (idCabina,idLocal,abierto,reservada,tipo)"
						+ " VALUES (?,?,?,?,?)";
				ps = StatemedSingelton.getInstance(insertCabina);
				ps.setString(1, c.getIdCabina());
				ps.setInt(2, c.getIdLocal());
				ps.setBoolean(3,c.getAbierto());
				ps.setBoolean(4,c.getReservada());
				ps.setString(5, c.getTipo());
				ps.executeUpdate();
			}
		} catch (PersistenciaException e1) {
			System.out.println(e1.getMessage());
		} catch (SQLException e1) {
			System.out.println(e1.getMessage());
		}finally {
			try {
				StatemedSingelton.close();
			} catch (PersistenciaException e) {
				System.out.println(e.getMessage());
			}
		}
		
	}
	
	public void insertarReserva(Reserva r) {
		PreparedStatement ps = null;
		try {
			String insertReserva = "INSERT INTO reservas (idReserva,emailCliente,idCabina,fechaInicio,fechaSalida"
								+ ",incidencia,descripcionIncidencia)"
					+ " VALUES (?,?,?,?,?,?,?)";
			ps = StatemedSingelton.getInstance(insertReserva);
			ps.setInt(1, r.getIdReserva());
			ps.setString(2, r.getEmailCliente());
			ps.setString(3,r.getIdCabina());
			ps.setDate(4,r.getFechaInicio());
			ps.setDate(5, r.getFechaSalida());
			ps.setBoolean(6, r.isIncidencia());
			ps.setString(7, r.getDescripcionIncidencia());
			ps.executeUpdate();
			
		} catch (PersistenciaException e1) {
			System.out.println(e1.getMessage());
		} catch (SQLException e1) {
			System.out.println(e1.getMessage());
		}finally {
			try {
				StatemedSingelton.close();
			} catch (PersistenciaException e) {
				System.out.println(e.getMessage());
			}
		}
		
	}
	
	private Cliente prepararCliente(ResultSet rs) throws SQLException {
		
		String email = rs.getString(1);
		String nombre = rs.getString(2);
		String apellidos = rs.getString(3);
		String password = rs.getString(4);
		int puntosTienda = rs.getInt(5);
		int numeroReservas = rs.getInt(6);
		return new Cliente(email,nombre,apellidos,password,puntosTienda,numeroReservas);
	}

	private Local prepararLocal(ResultSet rs) throws SQLException {
		
		int idLocal = rs.getInt(1);
		String coordenadas = rs.getString(2);
		int numeroReservas = rs.getInt(3);
		double ingresos = rs.getDouble(4);
		String direccion = rs.getString(5);
		return new Local(idLocal,coordenadas,numeroReservas,ingresos,direccion);
	}
	
	private Reserva prepararReserva(ResultSet rs) throws SQLException {
		
		int idReserva = rs.getInt(1);
		String emailCliente = rs.getString(2);
		String idCabina = rs.getString(3);
		Date fechaInicio = rs.getDate(4);
		Date fechaSalida = rs.getDate(5);
		boolean incidencia = rs.getBoolean(6);
		String descripcionIncidencia = rs.getString(7);
		
		return new Reserva(idReserva,emailCliente,idCabina,fechaInicio,fechaSalida,incidencia,descripcionIncidencia);
	}
	
	private Cabina prepararCabina(ResultSet rs) throws SQLException {
		
		String idCabina = rs.getString(1);
		int idLocal = rs.getInt(2);
		Boolean abierto = rs.getBoolean(3);
		Boolean reservada = rs.getBoolean(4);
		String tipo = rs.getString(5);
		
		return new Cabina(idCabina,idLocal,abierto,reservada,tipo);
	}

	public static void reiniciarPersistencia() throws PersistenciaException, SQLException {
		Statement st = StatemedSingelton.getInstance();
		String consultaClientes = "DROP TABLE IF EXISTS clientes";
		String consultaLocales = "DROP TABLE IF EXISTS locales";
		String consultaCabinas = "DROP TABLE IF EXISTS cabinas";
		String consultaReservas = "DROP TABLE IF EXISTS reservas";
		
		st.executeUpdate(consultaReservas);
		st.executeUpdate(consultaCabinas);
		st.executeUpdate(consultaClientes);
		st.executeUpdate(consultaLocales);
		StatemedSingelton.close();
		
	}

	

}
