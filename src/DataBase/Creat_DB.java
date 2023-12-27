package DataBase;

import Utils.SecureDatabaseConfiguration;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class Creat_DB {
	private String table_etu,table_nts,table_classe,database;
	private final String url;
    private final String username;
    private final String password;
    private final String dbName;
	private Connection con;
	private Savepoint savepoint;
	public Creat_DB() throws HeadlessException, ClassNotFoundException {
		SecureDatabaseConfiguration SecureDatabaseConfiguration=new SecureDatabaseConfiguration();
		String port=SecureDatabaseConfiguration.getDatabasePort();
		String IP=SecureDatabaseConfiguration.getDatabaseIP();
		this.username=SecureDatabaseConfiguration.getDatabaseUsername();
		this.password=SecureDatabaseConfiguration.getDatabasePassword();
		this.dbName=SecureDatabaseConfiguration.getDatabaseName();
		this.url="jdbc:mysql://"+IP+":"+port+"/";
		
		if(create_db())
		{
		 try {
			 	Class.forName("com.mysql.cj.jdbc.Driver") ;
	            this.con = DriverManager.getConnection(this.url+this.dbName,this.username,this.password);
	            con.setAutoCommit(false); // Set auto-commit to false to control the transaction manually
	            savepoint = con.setSavepoint("savepoint");
	            
	            

	            this.table_etu = "CREATE TABLE IF NOT EXISTS students ("
	                    + "std_id BIGINT AUTO_INCREMENT PRIMARY KEY,"
	                    + "number INT,"
	                    + "first_name VARCHAR(30),"//prenom
	                    + "last_name VARCHAR(30),"//nom
	                    + "gender VARCHAR(6),"
	                    + "Birthday DATE,"
	                    + "class INT,"
	                    + "email VARCHAR(100),"
	                    + "phone VARCHAR(15),"
	                    + "image longblob NULL,"
	                    + "FOREIGN KEY (class) REFERENCES classes (cls_id) ON UPDATE CASCADE ON DELETE CASCADE"
	                    + ")";

	            this.table_nts = "CREATE TABLE IF NOT EXISTS grades ("
	                    + "grade_id BIGINT AUTO_INCREMENT PRIMARY KEY,"
	                    + "std BIGINT, "
	                    + "grade_A DOUBLE NULL,"
	                    + "grade_B DOUBLE NULL,"
	                    + "grade_C DOUBLE NULL,"
	                    + "grade_D DOUBLE NULL,"
	                    + "grade_E DOUBLE NULL,"
	                    + "grade_F DOUBLE NULL,"
	                    + "FOREIGN KEY (std) REFERENCES students (std_id) ON UPDATE CASCADE ON DELETE CASCADE,"
	                    + "CHECK ((grade_A >= 0 AND grade_A <= 20) OR grade_A IS NULL),"
	                    + "CHECK ((grade_B >= 0 AND grade_B <= 20) OR grade_B IS NULL),"
	                    + "CHECK ((grade_C >= 0 AND grade_C <= 20) OR grade_C IS NULL),"
	                    + "CHECK ((grade_D >= 0 AND grade_D <= 20) OR grade_D IS NULL),"
	                    + "CHECK ((grade_E >= 0 AND grade_E <= 20) OR grade_E IS NULL),"
	                    + "CHECK ((grade_F >= 0 AND grade_F <= 20) OR grade_F IS NULL)"
	                    + ")";

	            this.table_classe = "CREATE TABLE IF NOT EXISTS classes ("
	                    + "cls_id INT AUTO_INCREMENT PRIMARY KEY,"
	                    + "class VARCHAR(10) UNIQUE,"
	                    + "description VARCHAR(100)"
	                    + ")";

	            Statement stmt = con.createStatement();
	            stmt.execute(this.table_classe);
	            stmt.execute(this.table_etu);
	            stmt.execute(this.table_nts);
	            JOptionPane.showMessageDialog(null, "Database Creation Success", "Create DataBase",JOptionPane.INFORMATION_MESSAGE);
	            JOptionPane.showMessageDialog(null, "All done , please run the application again", "INFORMATION_MESSAGE" ,
            			JOptionPane.INFORMATION_MESSAGE);
	            con.commit(); 
		            try {
	                    Thread.sleep(1000);
	                    System.exit(0);
	                    
	                } catch (Exception ex) {
	                    JOptionPane.showMessageDialog(null,ex.getMessage() , "WARNING_MESSAGE" ,
	                			JOptionPane.WARNING_MESSAGE);
	                }
	        } catch (Exception e) {
	            e.printStackTrace();
	            JOptionPane.showMessageDialog(null, "Database Creation Error", "Create DataBase", JOptionPane.ERROR_MESSAGE);
	            try {
	                if (con != null && savepoint != null) {
	                    con.rollback(savepoint);
	                }
	            } catch (SQLException rollbackEx) {
	                rollbackEx.printStackTrace();
	            }
	        } finally {
	           
	            try {
	                if (con != null) {
	                    con.close();
	                }
	            } catch (SQLException closeEx) {
	                closeEx.printStackTrace();
	            }
	        }
		}
		
		
		
	}
	
	public boolean  create_db() throws ClassNotFoundException {
		this.database="CREATE DATABASE IF NOT EXISTS  "+this.dbName+";";
		 try {
	            Class.forName("com.mysql.cj.jdbc.Driver");
	            try (Connection con = DriverManager.getConnection(this.url, this.username, this.password);
	                 Statement stmt = con.createStatement()) {
	                stmt.execute(database);
	                return true;
	            }
	        } catch (ClassNotFoundException | SQLException e) {
	            e.printStackTrace();
	            JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage()+ this.url, "Create Database", JOptionPane.ERROR_MESSAGE);
	            return false;
	        }
	}

}
