package com.itemList.app;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.ResultSet;
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
 * Servlet implementation class ShowItemsServlet
 */
@WebServlet("/list-items")
public class ShowItemsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ShowItemsServlet() {}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		// Get Config
				InputStream ins = getServletContext().getResourceAsStream("/WEB-INF/config.properties");	
				Properties props = new Properties();
				props.load(ins);
				
				// 1. Create a connection
				try {
					DBConnection conn= new DBConnection(props.getProperty("url"),props.getProperty("user"),props.getProperty("password"));
					if(conn != null) {
						// 2. Create a query 
						String query  = "select * from items";
						// 3. Create a statement
						Statement stm = conn.getConnection().createStatement();
						// 4. Execute Query 
						ResultSet rstm = stm.executeQuery(query);
						out.println("<table border='1'>");
						out.println("<tr>");
						out.println("<th>ID </th>");
						out.println("<th>Items Name </th>");
						out.println("<th>Items Price </th>");
						out.println("</tr>");
						while(rstm.next()) {
							
							out.println("<tr>");
							out.println("<td>"+rstm.getInt(1) +"</td>");
							out.println("<td>"+rstm.getString(2)+"</td>");
							out.println("<td>"+rstm.getDouble(3)+"</td>");
							out.println("</tr>");
						}
						out.println("</table>");
						rstm.close();
						stm.close();
						
					}
					conn.closeConnection();
				//	out.print("<h3>DB Connection is closed !</h3>");
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}
