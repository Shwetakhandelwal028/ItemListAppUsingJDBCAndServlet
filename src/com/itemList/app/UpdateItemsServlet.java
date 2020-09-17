package com.itemList.app;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itemsList.connection.DBConnection;

/**
 * Servlet implementation class UpdateItemsServlet
 */
@WebServlet("/update-items")
public class UpdateItemsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateItemsServlet() { }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect("update-items.html");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
        Integer id = Integer.parseInt(request.getParameter("id"));
		String name = request.getParameter("name");
		Double price = Double.parseDouble(request.getParameter("price"));
		
		//Get Config
		InputStream ins = getServletContext().getResourceAsStream("/WEB-INF/config.properties");
		Properties props= new Properties();
		props.load(ins);

        // 1. Create aconnection		
		try {
			DBConnection conn = new DBConnection(props.getProperty("url"), props.getProperty("user"), 
					props.getProperty("password"));
			if(conn != null) {
				// 2. Create a query
				
				String query = "update items set name ='"+name+"', price ='"+price+"' where p_id ='"+id+"'";
				
				// 3. Create a statement
				Statement stmt = conn.getConnection().createStatement();
				
				// 4. Execute query
				int noOfRowsAffected = stmt.executeUpdate(query);
				out.print("<h3>No. of Product updated "+noOfRowsAffected+"</h3>");
				stmt.close();
			}
			
			conn.closeConnection();
			out.print("<h3>Connection is closed!</h3>");
		}catch(ClassNotFoundException  e) {
			e.printStackTrace();
		}catch(SQLException e) {
			e.printStackTrace();
		}	
}

	}


