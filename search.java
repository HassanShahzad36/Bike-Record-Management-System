

import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.sql.*;
/**
 * Servlet implementation class search
 */
@WebServlet("/search")
public class search extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public search() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out = response.getWriter();
		
		try {
        	Class.forName("com.mysql.jdbc.Driver");
        	response.setContentType("text/html");
        	String url = "jdbc:mysql://127.0.0.1:3306/mysql";
            Connection conn = DriverManager.getConnection(url, "root", "root");
            String checkQuery;
            PreparedStatement ps;
            
            String name = request.getParameter("name");
            if(!(name).equals("All")) {
	            checkQuery = "SELECT id,name,model,capacity,owner,phone FROM bike WHERE name=?";
	            ps = conn.prepareStatement(checkQuery);
	            ps.setString(1, name);
            }
            else {
            	checkQuery = "SELECT id,name,model,capacity,owner,phone FROM bike";
                ps = conn.prepareStatement(checkQuery);
            }
            ResultSet rs = ps.executeQuery();
            out.println("<html><body>");
            out.println("<h2>Bike Records</h2>");
            out.println("<table border='1'>");
            out.println("<tr><th>ID</th><th>Name</th><th>Model</th><th>Capacity</th><th>Owner</th><th>Phone</th></tr>");
            
            while(rs.next()) {
            	 String id = rs.getString("id");
                 String bikeName = rs.getString("name");
                 String model = rs.getString("model");
                 String capacity = rs.getString("capacity");
                 String owner = rs.getString("owner");
                 String phone = rs.getString("phone");

                 // Print or process each record
                 out.println("<tr>");
                 out.println("<td>" + id + "</td>");
                 out.println("<td>" + bikeName + "</td>");
                 out.println("<td>" + model + "</td>");
                 out.println("<td>" + capacity + "</td>");
                 out.println("<td>" + owner + "</td>");
                 out.println("<td>" + phone + "</td>");
                 out.println("</tr>");
             }
             
             out.println("</table>");
             out.println("</body></html>");
             
             rs.close();
             ps.close();
             conn.close();
            }
		catch(Exception e) {
			out.println(e.getMessage());
		}
        
	}

}
