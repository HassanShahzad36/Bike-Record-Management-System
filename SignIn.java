

import java.io.*;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.sql.*;

/**
 * Servlet implementation class SignIn
 */
@WebServlet("/SignIn")
public class SignIn extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SignIn() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		 response.setContentType("text/html");
	        // Get PrintWriter object
	     PrintWriter out = response.getWriter();
	     
	     String email = request.getParameter("email");
	     String password = request.getParameter("password");
	     String role = request.getParameter("usertype");
	    try {
	     Class.forName("com.mysql.jdbc.Driver");
	     String url = "jdbc:mysql://127.0.0.1:3306/mysql";
	     Connection con = DriverManager.getConnection(url, "root", "root");
         String checkQuery = "SELECT * FROM userdata WHERE email=? and password=? and role=?";
         PreparedStatement checkStatement = con.prepareStatement(checkQuery);
         checkStatement.setString(1, email);
         checkStatement.setString(2, password);
         checkStatement.setString(3, role);
         ResultSet rs = checkStatement.executeQuery();
         
         if(rs.next()) {
        	 HttpSession session = request.getSession(true);
        	 session.setAttribute("type",(String)role);
        	 session.setMaxInactiveInterval(60*60*24);
        	 
        	 String type = (String)session.getAttribute("type");
        
        	 if(type==null) {
        		 response.sendRedirect("SignIn.html");
        	 }
        	 if(type.equals("Admin")) {
        		 response.sendRedirect("adminHomePage.html");
        	 }
        	 else if(type.equals("Regular User")) {
        		 response.sendRedirect("regularUser.html"); 
        	 }
        	 out.println("<h1>You Can Log in in</h1>");
         }
         else {
        	 out.println("<h1>You Cannot Log in in</h1>");
         }
         con.close();
         checkStatement.close();
	}
	    catch(Exception e) {
	    	out.println("<h1>Error: " + e.getMessage() + "</h1>");
	    }
}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	
}
