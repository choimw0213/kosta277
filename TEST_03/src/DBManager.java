import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBManager {
	private static DBManager instance;
	private Connection connection;
	
	private DBManager() throws ClassNotFoundException, SQLException{
		//1. driver loading / new String()
		Class.forName("oracle.jdbc.OracleDriver");
//		System.out.println("1 loading ok");
		//2. connection
		String url = "jdbc:oracle:thin:@127.0.0.1:1521:XE";
		connection = DriverManager.getConnection(url, "hr", "hr");
//		System.out.println("2 connection ok");
	}
	
	public static DBManager getInstance() throws ClassNotFoundException, SQLException{
		if(instance == null){
			instance = new DBManager();
		}
		return instance;
	}
	
	public Connection getConnection(){
		return connection;
	}
}
