package cn.easyproject.easyee.auto.generator;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


public class DBUtils {
	private static Properties prop = new Properties();
	static{
		ClassLoader loader = DBUtils.class.getClassLoader();
		InputStream in = loader.getResourceAsStream("db.properties");
		try {
			prop.load(in);
			Class.forName(prop.getProperty("db.driver"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	 
	public static Connection getConnection() throws Exception{
		return DriverManager.getConnection(prop.getProperty("db.url"),prop.getProperty("db.user"),prop.getProperty("db.pwd"));
	}
	
	public static void close(Connection conn){
		if(conn != null){
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) throws Exception{
		System.out.println(getConnection());
	}
}