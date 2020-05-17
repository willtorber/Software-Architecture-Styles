package ufps.architecture.database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public final class DataBase {
    
    private static DataBase database;
    private Connection conecction;
    private Statement statement;
    
    private DataBase(String sgbd, String user, String password) {
        
    	try {
    		Class.forName("com.mysql.jdbc.Driver");
            this.conecction = DriverManager.getConnection(sgbd, user, password);
        } catch (SQLException | ClassNotFoundException e) { 
            System.out.println("Error al conectarse con la base de datos");
            e.printStackTrace();
        }
    }
    
    public static DataBase getInstance(String sgbd, String user, String password) {
        
        if (DataBase.database == null) {
            DataBase.database = new DataBase(sgbd, user, password);
        }        
        return DataBase.database;
    }
    
    public ResultSet query(String query) {
        try {
            this.statement = this.conecction.createStatement();
            ResultSet result = this.statement.executeQuery(query);
            return result;
        } catch (SQLException e) { 
        	System.out.println(e.getMessage());
        	return null;
        }
    }

}