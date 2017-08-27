package datasource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.ThreadLocalRandom;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;


@Stateless
public class TestDataSource4 {
	
	@Resource(lookup="java:app/jdbc/MyDS")
	DataSource ds;
	
	public void testDS() throws SQLException {
		if(ds != null) {

			Connection conn = ds.getConnection();
			Statement stmt = conn.createStatement();
			
			stmt.execute("CREATE TABLE IF NOT EXISTS test(id INT PRIMARY KEY, name VARCHAR(255));");
			stmt.execute("insert into test values ("+ getNumber(10000) +", 'John');");
			stmt.execute("insert into test values ("+ getNumber(10000) +", 'Doe');");
			
			System.out.println("---------------------------------------");
			ResultSet rs = stmt.executeQuery("select * from test");
			while (rs.next()) {
	            int id = rs.getInt("id");
	            String name = rs.getString("name");
	            System.out.println("test id="+id+", name="+name);
	        }
			
			stmt.close();
			conn.close();
		} else {
			System.out.println("DS NULL!");
		}
	}
	
	@PostConstruct
	private void checkDSs() {
		if(ds != null) {
			System.out.println("---------------------------------------");
			System.out.println("--- @Resource DataSource not null: "+ds);
			DataSource lookedDS = null;
			try {
				lookedDS = InitialContext.doLookup("java:app/jdbc/MyDS");
			} catch (NamingException e) {
				e.printStackTrace();
			}
			System.out.println("--- InitialContext.lookup DataSource not null: "+lookedDS);
		} else {
			System.out.println("DS NULL!");
		}
	}
	
	private int getNumber(int seed) {
		return ThreadLocalRandom.current().nextInt(seed)+1;
	}
    
}
