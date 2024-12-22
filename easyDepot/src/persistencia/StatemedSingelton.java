package persistencia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import apykeys.Apykeys;
import excepciones.PersistenciaException;

public class StatemedSingelton {

	private static StatemedSingelton state;
	private static Connection con;
	private static Statement st;
	private static PreparedStatement ps;
	private static String password = Apykeys.getPassword();
	private static String direccion = "jdbc:mysql://localhost/easydepot";
	
	private StatemedSingelton() {
		
	}
	
	public static Statement getInstance() throws PersistenciaException {
		if(state == null) {
			state = new StatemedSingelton();
		}
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection(direccion, "root", password);
			st = con.createStatement();
		} catch (ClassNotFoundException e) {
			throw new PersistenciaException("ERROR en la conexion");
		} catch (SQLException e) {
			throw new PersistenciaException("ERROR al conectar");
		}
		return st;
	}
	
	public static PreparedStatement getInstance(String consulta) throws PersistenciaException {
		if(state == null) {
			state = new StatemedSingelton();
		}
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection(direccion, "root", password);
			ps = con.prepareStatement(consulta);
		} catch (ClassNotFoundException e) {
			throw new PersistenciaException("ERROR en la conexion");
		} catch (SQLException e) {
			throw new PersistenciaException("ERROR en el driver");
		}
		return ps;
	}
	
	public static Connection getConnection() throws PersistenciaException {
		if(state == null) {
			state = new StatemedSingelton();
		}
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection(direccion, "root", password);
		} catch (ClassNotFoundException e) {
			throw new PersistenciaException("ERROR en la conexion");
		} catch (SQLException e) {
			throw new PersistenciaException("ERROR en el driver");
		}
		return con;
	}
	
	public static void close() throws PersistenciaException {
		if(st != null) {
			try {
				if(st != null) {
					st.close();
				}
				if(ps != null) {
					ps.close();
				}
				if(con != null) {
					con.close();
				}
				
			} catch (SQLException e) {
				throw new PersistenciaException("ERROR en el cierre");
			}
		}
		
	}

}
