

import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.sql.*;

/**
 * Servlet implementation class delete
 */
@WebServlet("/delete")
public class delete extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public delete() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        HttpSession session = request.getSession(false);
        session.setMaxInactiveInterval(60 * 60 * 24);
        PrintWriter out = response.getWriter();
        try {
            Class.forName("com.mysql.jdbc.Driver");
            response.setContentType("text/html");
            String url = "jdbc:mysql://127.0.0.1:3306/mysql";
            Connection conn = DriverManager.getConnection(url, "root", "root");
            if (((String) session.getAttribute("type")).equals("Admin")) {
                String id = request.getParameter("id");
                String name = request.getParameter("name");
                String model = request.getParameter("model");

                String checkQuery = "DELETE FROM bike WHERE id=? and name=? and model=?";
                PreparedStatement ps = conn.prepareStatement(checkQuery);
                ps.setString(1, id);
                ps.setString(2, name);
                ps.setString(3, model);

                int rowsDeleted = ps.executeUpdate();

                if (rowsDeleted > 0) {
                    out.println("<h1>Bike record deleted successfully!</h1>");
                } else {
                    out.println("<h1>No record found with the provided ID and Name.</h1>");
                }
            }
            else {
            	out.println("<h1>You cannot access this functionality</h1>");
            }
        } catch (Exception e) {
            out.println(e.getMessage());
        }
    }
    
}