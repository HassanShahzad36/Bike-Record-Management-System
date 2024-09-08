

import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.sql.*;
/**
 * Servlet implementation class add
 */
@WebServlet("/add")
public class add extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public add() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        session.setMaxInactiveInterval(60*60*24);
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
                String capacity = request.getParameter("capacity");
                String ownerName = request.getParameter("ownerName");
                String ownerId = request.getParameter("ownerId");
                String phone = request.getParameter("phone");

                String checkQuery = "SELECT * FROM bike WHERE id=? and name=? and model=? and cnic=? ";
                PreparedStatement ps = conn.prepareStatement(checkQuery);
                ps.setString(1, id);
                ps.setString(2, name);
                ps.setString(3, model);
                ps.setString(4, ownerId);
                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    out.println("<h1>Record is already registered</h1>");
                } else {
                    String sql = "INSERT INTO bike(id, name, model, capacity, owner, cnic,phone) VALUES (?, ?, ?, ?, ?, ?,?)";
                    PreparedStatement statement = conn.prepareStatement(sql);
                    statement.setString(1, id);
                    statement.setString(2, name);
                    statement.setString(3, model);
                    statement.setString(4, capacity);
                    statement.setString(5, ownerName);
                    statement.setString(6, ownerId);
                    statement.setString(7, phone);

                    // Execute the insert statement
                    int rowsInserted = statement.executeUpdate();
                    if (rowsInserted > 0) {
                        out.println("<h1>Bike record added successfully!<h1>");
                    } else {
                        out.println("Failed to add bike record.");
                    }
                }
            } else {
                out.println("<h1>As a Regular User you cannot access this page.</h1>");
            }
        } catch (Exception e) {
            // Handle any SQL errors
            out.println("Error: " + e.getMessage());
        }
    }


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
}
