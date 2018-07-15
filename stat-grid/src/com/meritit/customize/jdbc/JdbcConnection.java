package com.meritit.customize.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;    
    
public class JdbcConnection {    
    // 创建静态全局变量     
    static Connection conn;    
    
    static Statement st;    
    
 
	
	
	/**
	 * 获取jdbc连接
	 */
	public static JdbcAttribute getJdbcAttribute() {
		JdbcAttribute jdbcAttribute = new JdbcAttribute();
		//oracle.jdbc.driver.OracleDriver   jjdbc:oracle:thin:@192.168.90.10:1521:orcl
		String driver  =  "com.mysql.jdbc.Driver"; 
		String url =  "jdbc:mysql:///temperature";
		String username =   "root";
		String password = "root";
		jdbcAttribute.setDriver(driver);
		jdbcAttribute.setUrl(url);
		jdbcAttribute.setUsername(username);
		jdbcAttribute.setPassword(password);
		return jdbcAttribute;
	}
	
	
    public static void main(String[] args) {    
    	
    	// conn = getConnection(); //同样先要获取连接，即连接到数据库    
    	 try {
         		
    		 insert();
         //    conn.close();   //关闭数据库连接     
                 
         } catch (SQLException e) {    
             System.out.println("查询数据失败");    
         }
    	
    	//queryArticleCount();    //查询记录并显示     
    }    
        
    
    
    public static void insert() throws SQLException{
    	
    	 conn = getConnection(); //同样先要获取连接，即连接到数据库   
    	
    	PreparedStatement pre = null;// 创建预编译语句对象，一般都是用这个而不用Statement
	    ResultSet result = null;// 创建一个结果集对象
	    
	    
	    String sql = "insert into pop_total(Id) values(?)";
	    
	    pre = conn.prepareStatement(sql);// 实例化预编译语句
	    
	   
	    
	    for(int i=0;i<2;i++){
	    	 pre.setString(0, "22"+i);
	    	 pre.addBatch();
	   }
	    
	    
	    
	    
	    pre.executeBatch();
	    conn.commit();   
	     conn.close(); 
    }
    
	private static int getArticleCount(Connection conn,String filePath,String date) throws SQLException {
		PreparedStatement pre = null;// 创建预编译语句对象，一般都是用这个而不用Statement
	    ResultSet result = null;// 创建一个结果集对象
//		String sql = "select count(1) from file_up_new where instr(file_path,?,1,1)>0 and to_char(updatetime ,'yyyy-MM-dd') = ? ";     // 查询数据的sql语句     
		String sql = "SELECT * FROM city c "
				+ "LEFT JOIN temp t ON UPPER(t.tagName) = UPPER(c.cityPinYin) "
				+ "WHERE href IS NOT NULL ORDER BY c.provinceHZ,tagName";
		
		
		pre = conn.prepareStatement(sql);// 实例化预编译语句
		pre.setString(1, filePath);// 设置参数，前面的1表示参数的索引，而不是表中列名的索引
		pre.setString(2, date);
   //        pre.setString(2, DateUtil.getStringDateShort());
	//	pre.setString(2, DateUtil.getSpecifiedDayBefore(DateUtil.getStringDateShort()));
		
		result = pre.executeQuery();// 执行查询，注意括号中不需要再加参数
         // ResultSet rs = st.executeQuery(sql);    //执行sql查询语句，返回查询数据的结果集     
		System.out.println("最后的查询结果为：");    
		
		result.next();
		
		int count = result.getInt(1);
		
		return count;
	}    
    
	public static List getResult(Connection conn) throws SQLException {
		PreparedStatement pre = null;// 创建预编译语句对象，一般都是用这个而不用Statement
	    ResultSet result = null;// 创建一个结果集对象
		String sql = "SELECT * FROM city c "
				+ "LEFT JOIN temp t ON UPPER(t.tagName) = UPPER(c.cityPinYin) "
				+ "WHERE href IS NOT NULL ORDER BY c.provinceHZ,tagName";
		
		pre = conn.prepareStatement(sql);// 实例化预编译语句
		
		result = pre.executeQuery();// 执行查询，注意括号中不需要再加参数
		
		List<Map> list = new ArrayList<Map>();
		while(result.next()){
			Map<String,String> map = new HashMap<>();
			String tagName = result.getString("tagName");
			String href = result.getString("href");
			String provinceHZ = result.getString("provinceHZ");
			String  cityHZ= result.getString("cityHZ");
			System.out.println(provinceHZ+"--"+cityHZ);
			map.put("tagName", tagName);
			map.put("href", href);
			map.put("provinceHZ", provinceHZ);
			map.put("cityHZ", cityHZ);
			list.add(map);
		}
		
		System.out.println(result);
		return list;
	}
	
        
    /* 获取数据库连接的函数*/    
    public static Connection getConnection() {    
        Connection con = null;  //创建用于连接数据库的Connection对象     
        try {    
        	
        	JdbcAttribute jdbcAttribute = JdbcConnection.getJdbcAttribute();
        	String driver = jdbcAttribute.getDriver();
          
                
            String url = jdbcAttribute.getUrl();// 127.0.0.1是本机地址，XE是精简版Oracle的默认数据库名
            String user = jdbcAttribute.getUsername();// 用户名,系统默认的账户名
            String password = jdbcAttribute.getPassword();// 你安装时选设置的密码
            
            Class.forName(driver);// 加载Mysql数据驱动     
            con = DriverManager.getConnection(url, user, password);// 创建数据连接    
            System.out.println("连接成功！");  
                
        } catch (Exception e) {    
            System.out.println("数据库连接失败" + e.getMessage());    
        }    
        return con; //返回所建立的数据库连接     
    }    
}    