package DataBase;

import Utils.SecureDatabaseConfiguration;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Backend {
	private final String url;
	private final String username;
	private final String password;
	private Connection con=null;
	private Savepoint savepoint;
	
	public Backend() {
		SecureDatabaseConfiguration SecureDatabaseConfiguration=new SecureDatabaseConfiguration();
		String port=SecureDatabaseConfiguration.getDatabasePort();
		String IP=SecureDatabaseConfiguration.getDatabaseIP();
		this.username=SecureDatabaseConfiguration.getDatabaseUsername();
		this.password=SecureDatabaseConfiguration.getDatabasePassword();
		String dbName=SecureDatabaseConfiguration.getDatabaseName();
		this.url="jdbc:mysql://"+IP+":"+port+"/"+dbName;
	}
	
	public Connection connection() {
		try {
		 	Class.forName("com.mysql.cj.jdbc.Driver") ;
            this.con = DriverManager.getConnection(this.url,this.username,this.password);
		}catch(Exception e) {
			e.getMessage();
		}
		return this.con;
	}
	
	
	public boolean addClass(String name,String description) {
		Connection conn=this.connection();
		if(conn!=null) {
			try {
				Statement stmt=conn.createStatement();
				String str ="insert into classes(class,description) values('"+name+"','"+description+"')";
				int check=stmt.executeUpdate(str);

                return check == 1;
			}catch(Exception e) {
				e.getMessage();
				return false;
			}
	
		}else {
			return false;
		}
	}
	
	public String[][] Classes_tab() {
		String[][] data;
		int count=0;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			this.con = DriverManager.getConnection(this.url,this.username,this.password);
			Statement stmt=con.createStatement();
			String str="select count(*) as count from classes;";
			ResultSet res= stmt.executeQuery(str);
			if(res.next())	count=res.getInt("count");
			
		}catch(Exception e) {
			e.getMessage();
		}
		data=new String[count][3];
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
            this.con = DriverManager.getConnection(this.url,this.username,this.password);
			Statement stmt=con.createStatement();
			String str="select * from classes;";
			ResultSet res= stmt.executeQuery(str);
			int i=0;
			while(res.next()) {
				int id=res.getInt("cls_id");
				data[i][0]=Integer.toString(id);
				data[i][1]=res.getString("class");
				data[i][2]=res.getString("description");
				i++;
			}
		}catch(Exception e) {
			e.getMessage();
		}
		return data;
	}
	
	public boolean deleteClass(int id){
		try {
		Class.forName("com.mysql.cj.jdbc.Driver");
        this.con = DriverManager.getConnection(this.url,this.username,this.password);
		String sql="delete from classes where cls_id=?;";
		PreparedStatement pstmt=con.prepareStatement(sql);
		pstmt.setInt(1,id);
		pstmt.executeUpdate();
		return true;
		}catch(Exception e){
			e.getMessage();
			return false;
		}
	}
	
	public boolean updateClass(int oldid,int newid,String newname,String newdescription) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
            this.con = DriverManager.getConnection(this.url,this.username,this.password);
			String sql="UPDATE classes"
					+ " SET cls_id=?,class=?,description=?"
					+ " WHERE cls_id=?;";
			PreparedStatement pstmt=con.prepareStatement(sql);
			pstmt.setInt(1,newid);
			pstmt.setString(2,newname);
			pstmt.setString(3,newdescription);
			pstmt.setInt(4,oldid);
			pstmt.executeUpdate();
			return true;
			}catch(Exception e){
				e.getMessage();
				return false;
			}
	}
	
	
	public String[] selectClass(){
		int count=0;
		Connection conn=this.connection();
		if(conn!=null) {
			
			try {
				Statement stmt=conn.createStatement();
				String str="select count(*) as count from classes;";
				ResultSet res= stmt.executeQuery(str);
				if(res.next())	count=res.getInt("count");
				
			}catch(Exception e) {
				e.getMessage();
			}
		}
		
		
		String[] data=new String[count];
		if(conn!=null) {
			try {
			Statement stmt=conn.createStatement();
			String str="select class from classes;";
			ResultSet res= stmt.executeQuery(str);
			int i=0;
			while(res.next()) {
				data[i]=res.getString("class");
				i++;
			}
			
			}catch(Exception e) {
				e.getMessage();
			}
		}
		
		return data;
	}
	
	
	public boolean registreStudent(String[] student, File image) throws ParseException, SQLException {
		Connection conn=this.connection();
		int cls=searchClassId(student[5]);
		if(conn!=null && cls!=0) {
			
			 try {
				 con.setAutoCommit(false); // Set auto-commit to false to control the transaction manually
		         savepoint = con.setSavepoint("savepoint");
				// Convert the date string to a java.sql.Date object
				 SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
			        Date utilDate = format.parse(student[4]);
			        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
			       
				 String sql="insert into students(number,first_name,last_name,gender,Birthday,class,email,phone,image)"
							+ " value(?,?,?,?,?,?,?,?,?);";
				
					PreparedStatement pstm=conn.prepareStatement(sql);
					
					   File selectedFile =image;
	                    Blob blob = con.createBlob();

	                    if (selectedFile != null) {
	                        try (FileInputStream fileInputStream = new FileInputStream(selectedFile)) {
	                            byte[] buffer = new byte[4096];
	                            int bytesRead;
	                            while ((bytesRead = fileInputStream.read(buffer)) != -1) {
	                                blob.setBytes(blob.length() + 1, buffer, 0, bytesRead);
	                            }
	                        } catch (IOException e) {
	                            e.printStackTrace();
	                        }
	                    }else {
	                    	blob=null;
	                    }
	                    
					pstm.setInt(1,Integer.parseInt(student[0]));
					pstm.setString(2,student[1]);
					pstm.setString(3,student[2]);
					pstm.setString(4,student[3]);
					pstm.setDate(5, sqlDate);
					pstm.setInt(6,cls);
					pstm.setString(7,student[6]);
					pstm.setString(8,student[7]);
					pstm.setBlob(9, blob);
					pstm.executeUpdate();
					
					
					sql="select std_id from students where number=? and first_name=? and last_name=? and "
							+ "gender=? and Birthday=? and class=? and email=? and phone=? ;";
							
					pstm=conn.prepareStatement(sql);
					pstm.setInt(1,Integer.parseInt(student[0]));
					pstm.setString(2,student[1]);
					pstm.setString(3,student[2]);
					pstm.setString(4,student[3]);
					pstm.setDate(5, sqlDate);
					pstm.setInt(6,cls);
					pstm.setString(7,student[6]);
					pstm.setString(8,student[7]);
					ResultSet res= pstm.executeQuery();
					
					if(res.next())	{ 
						int target = res.getInt("std_id");
						sql="insert into grades(std) value(?)";
						pstm=conn.prepareStatement(sql);
						pstm.setInt(1,target);
						pstm.executeUpdate();
					}
					
					
					
					con.commit(); 
					return true;
				 
				 
			 }catch(SQLException e) {
				 
					e.getMessage();
					con.rollback(savepoint);
					return false;
			}
		}
		
		return false;
	}
	
	public boolean modifyPhoto(int std_id,File image) {
		Connection conn=this.connection();
		try {
			 String sql="UPDATE students"
						+ " SET image=? "
						+ " WHERE std_id=?;";
			PreparedStatement pstm=conn.prepareStatement(sql);
			
			
			  File selectedFile =image;
              Blob blob = con.createBlob();

              if (selectedFile != null) {
                  try (FileInputStream fileInputStream = new FileInputStream(selectedFile)) {
                      byte[] buffer = new byte[4096];
                      int bytesRead;
                      while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                          blob.setBytes(blob.length() + 1, buffer, 0, bytesRead);
                      }
                  } catch (IOException e) {
                      e.printStackTrace();
                  }
                  
                    pstm.setBlob(1, blob);
                    pstm.setInt(2, std_id);
              
                    int b=pstm.executeUpdate();
                  return b == 1;
                    
              }
              
           return false;
              
              
		}catch(Exception e) {
			return false;
		}
	}
	
	public int searchClassId(String str) {
		Connection conn=this.connection();
		if(conn!=null) {
			try {
				
				String sql="select cls_id from classes where class='"+str+"';";
			 
				Statement stmt=conn.createStatement();
				ResultSet res=stmt.executeQuery(sql);
				
				if(res.next()) {
					
					return res.getInt("cls_id");
				}
				else {
					
					return 0;
				}
			}catch(Exception e) {
				
				e.getMessage();
				return 0;
			}
		}
		else {
			return 0;
		}
	}
	
	public String searchClassName(int id) {
		Connection conn=this.connection();
		if(conn!=null) {
			try {
				
				String sql="select class from classes where cls_id="+id;
			 
				Statement stmt=conn.createStatement();
				ResultSet res=stmt.executeQuery(sql);
				
				if(res.next()) {
					
					return res.getString("class");
				}
				else {
					
					return null;
				}
			}catch(Exception e) {
				
				e.getMessage();
				return null;
			}
		}
		else {
			return null;
		}
	}
	
	
	public String[][] Students_tab( int idClass) {
		String[][] data;
		int count=0;
		try {
			this.con=connection();
			Statement stmt=this.con.createStatement();
			String str="select count(*) as count from students where class="+idClass;
			ResultSet res= stmt.executeQuery(str);
			if(res.next())	count=res.getInt("count");
			
		}catch(Exception e) {
			e.getMessage();
		}
		data=new String[count][9];
		try {
			Statement stmt=this.con.createStatement();
			String str="select * from students where class="+idClass;
			ResultSet res= stmt.executeQuery(str);
			int i=0;
			while(res.next()) {
				int id=res.getInt("std_id");
				data[i][0]=Integer.toString(id);
				data[i][1]=res.getInt("number") +"";
				data[i][2]=res.getString("first_name");
				data[i][3]=res.getString("last_name");
				data[i][4]=res.getString("gender");
				data[i][5]=res.getDate("Birthday") +"";
                data[i][6]=this.searchClassName(res.getInt("class"));
                data[i][7]=res.getString("email");
                data[i][8]=res.getString("phone");
				i++;
			}
		}catch(Exception e) {
			e.getMessage();
		}
		return data;
	}
	
	public boolean deleteStudent(int id){
		try {
		Class.forName("com.mysql.cj.jdbc.Driver");
        this.con = DriverManager.getConnection(this.url,this.username,this.password);
		String sql="delete from students where std_id=?;";
		PreparedStatement pstmt=con.prepareStatement(sql);
		pstmt.setInt(1,id);
		pstmt.executeUpdate();
		return true;
		}catch(Exception e){
			e.getMessage();
			return false;
		}
	}
	
	public String[] importstudent(int id) {
		String[] data;
		data=new String[9];
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
            this.con = DriverManager.getConnection(this.url,this.username,this.password);
			Statement stmt=con.createStatement();
			String str="select * from students where std_id="+id;
			ResultSet res= stmt.executeQuery(str);
			
			if(res.next()) {
				int std_id=res.getInt("std_id");
				data[0]=Integer.toString(std_id);
				data[1]=res.getInt("number") +"";
				data[2]=res.getString("first_name");
				data[3]=res.getString("last_name");
				data[4]=res.getString("gender");
				data[5]=res.getDate("Birthday") +"";
                data[6]=this.searchClassName(res.getInt("class"));
                data[7]=res.getString("email");
                data[8]=res.getString("phone");
			}else return null;
		}catch(Exception e) {
			e.getMessage();
		}
		return data;
	}
	 public byte[] retrieveStudentImage(int studentId) {
	        Connection connection = null;
	        PreparedStatement pstmt = null;
	        ResultSet resultSet = null;
	        byte[] imageData = null;

	        try {
	            connection = this.connection();

	            String sql = "SELECT image FROM students WHERE std_id = ?";
	            pstmt = connection.prepareStatement(sql);
	            pstmt.setInt(1, studentId);

	            resultSet = pstmt.executeQuery();

	            if (resultSet.next()) {
	                Blob blob = resultSet.getBlob("image");
	                if(blob != null)
	                imageData = blob.getBytes(1, (int) blob.length());
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }  
	        return imageData;
	    }
	
	public boolean updateStudent(String[] student,int id )throws ParseException {
		Connection conn=this.connection();
		int cls=searchClassId(student[5]);
		if(conn!=null && cls!=0) {
			 try {
				// Convert the date string to a java.sql.Date object
				 SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
			        Date utilDate = format.parse(student[4]);
			        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
			        

				 String sql="UPDATE students"
							+ " SET number=?,first_name=?,last_name=?,gender=?,Birthday=?,class=?,email=?,phone=? "
							+ " WHERE std_id=?;";
					PreparedStatement pstm=conn.prepareStatement(sql);
					pstm.setInt(1,Integer.parseInt(student[0]));
					pstm.setString(2,student[1]);
					pstm.setString(3,student[2]);
					pstm.setString(4,student[3]);
					pstm.setDate(5, sqlDate);
					pstm.setInt(6,cls);
					pstm.setString(7,student[6]);
					pstm.setString(8,student[7]);
					pstm.setInt(9,id);
					pstm.executeUpdate();
					
					return true;
				 
				 
			 }catch(SQLException e) {
				 
					e.getMessage();
					return false;
			}
		}
		
		return false;
	}
	
	public boolean deleteGradesStudent(int id) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
            this.con = DriverManager.getConnection(this.url,this.username,this.password);
			 String sql="UPDATE grades "
			 		+ "SET grade_A = null, grade_B = null, grade_C = null, grade_D = null, grade_E = null, grade_F = null "
			 		+ "WHERE grade_id = ?;";
			PreparedStatement pstmt=con.prepareStatement(sql);
			pstmt.setInt(1,id);
			pstmt.executeUpdate();
			return true;
			}catch(Exception e){
				e.getMessage();
				return false;
			}
	}
	
	
	public String[][] Grades_tab( int idClass) {
		String[][] data;
		int count=0;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
            this.con = DriverManager.getConnection(this.url,this.username,this.password);
			Statement stmt=con.createStatement();
			String str="select count(*) as count from students where class="+idClass;
			ResultSet res= stmt.executeQuery(str);
			if(res.next())	count=res.getInt("count");
			
		}catch(Exception e) {
			e.getMessage();
		}
		data=new String[count][9];
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
            this.con = DriverManager.getConnection(this.url,this.username,this.password);
			Statement stmt=con.createStatement();
			String str = "SELECT G.grade_id AS g_id, S.std_id AS s_id, S.first_name AS f_name, S.last_name AS l_name, " +
		             "G.grade_A AS g_A, G.grade_B AS g_B, G.grade_C AS g_C, G.grade_D AS g_D, G.grade_E AS g_E, G.grade_F AS g_F " +
		             "FROM classes AS C " +
		             "JOIN students AS S ON C.cls_id = S.class " +
		             "JOIN grades AS G ON S.std_id = G.std " +
		             "WHERE C.cls_id=" + idClass;

			ResultSet res= stmt.executeQuery(str);
			int i=0;
			while(res.next()) {
				data[i][0]=Integer.toString(res.getInt("g_id"));
				data[i][1]=res.getInt("s_id") +"";
				data[i][2]=res.getString("l_name")+" "+res.getString("f_name");
				data[i][3]=res.getInt("g_A")+"";
				data[i][4]=res.getInt("g_B")+"";
				data[i][5]=res.getInt("g_C")+"";
                data[i][6]=res.getInt("g_D")+"";
                data[i][7]=res.getInt("g_E")+"";
                data[i][8]=res.getInt("g_F")+"";
				i++;
			}
			
		}catch(SQLException | ClassNotFoundException  e) {
			e.getMessage();
		}
		return data;
	}
	
	
	public int[] studentGrades(int std_id) {
		int[] data=null;
		try {
			Connection con=this.connection();
			Statement stmt=con.createStatement();
			String str = "SELECT grade_A,grade_B,grade_C,grade_D,grade_E,grade_F  " +
		             "FROM grades " +
		             "WHERE std=" + std_id;

			ResultSet res= stmt.executeQuery(str);
			data=new int[6];
			while(res.next()) {
				data[0]=res.getInt("grade_A");
				data[1]=res.getInt("grade_B");
				data[2]=res.getInt("grade_C");
				data[3]=res.getInt("grade_D");
				data[4]=res.getInt("grade_E");
				data[5]=res.getInt("grade_F");

			}
		}catch(SQLException  e) {
			e.getMessage();
		}
		return data;
	}


    public String[][] tableToExport(int classId) {
		String[][] data=null;
		int count=0;
		try {
			this.con=connection();
			Statement stmt=con.createStatement();
			String str="select count(*) as count from students where class="+classId;
			ResultSet res= stmt.executeQuery(str);
			if(res.next())	count=res.getInt("count");

			data=new String[count][13];

			str="SELECT * FROM students JOIN grades WHERE std=std_id AND class="+classId;
			res= stmt.executeQuery(str);
			int i=0;
			while(res.next()) {
				data[i][0]=res.getInt("number") +"";
				data[i][1]=res.getString("first_name");
				data[i][2]=res.getString("last_name");
				data[i][3]=res.getString("gender");
				data[i][4]=res.getDate("Birthday") +"";
				data[i][5]=res.getString("email");
				data[i][6]=res.getString("phone");
				data[i][7]=res.getDouble("grade_A")+"";
				data[i][8]=res.getDouble("grade_B")+"";
				data[i][9]=res.getDouble("grade_C")+"";
				data[i][10]=res.getDouble("grade_D")+"";
				data[i][11]=res.getDouble("grade_E")+"";
				data[i][12]=res.getDouble("grade_F")+"";
				i++;
			}
		}catch(SQLException  e) {
			e.getMessage();
		}
		return data;
    }

	public boolean updateGrades(float[] grades,int grade_Id){
		try {
			String updateQuery = "UPDATE grades SET grade_A = ? , grade_B = ?,grade_C = ?," +
					"grade_D = ?,grade_E = ?,grade_F = ? WHERE grade_id = ? ;";
			this.con = connection();
			PreparedStatement preparedStatement = this.con.prepareStatement(updateQuery);
			for(int i=1;i<7;i++){
				preparedStatement.setFloat(i,grades[i-1]);
			}
			preparedStatement.setInt(7,grade_Id);
			int test=preparedStatement.executeUpdate();
            return test == 1;
		}catch(Exception e){e.printStackTrace(); return false;}
	}
}
