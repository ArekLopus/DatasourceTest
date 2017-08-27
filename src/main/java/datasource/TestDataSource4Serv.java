package datasource;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/DataSourceDefServ")
public class TestDataSource4Serv extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	@Inject
	TestDataSource4 td;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		response.getWriter().println("<h2>@DataSourceDefinition test</h2>");
		
		try {
			td.testDS();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
}
