package business;

import java.sql.Connection;
import java.sql.DriverManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConnectionFactory {
	
	private static final String URL="jdbc:postgresql://localhost:5432/bancadb";
	private static final String USER="postgres";
	private static final String PASS="ok98";
	private static Logger log=LoggerFactory.getLogger(ConnectionFactory.class);
	
	public static Connection getConnection() {
		Connection conn=null;
		try {
			conn=DriverManager.getConnection(URL,USER,PASS);
			log.info("connesso");
		} catch (Exception e) {
			log.info("errore nella connessione");
		}
		return conn;
	}

}
