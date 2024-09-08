
import java.io.*;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;

/**
 * Servlet implementation class SignUp
 */
@WebServlet("/SignUp")
public class SignUp extends HttpServlet {
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SignUp() {
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

        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        
        try {
            Class.forName("com.mysql.jdbc.Driver");

            String url = "jdbc:mysql://127.0.0.1:3306/mysql";
            Connection con = DriverManager.getConnection(url, "root", "root");
            String checkQuery = "SELECT * FROM userdata WHERE email=?";
            PreparedStatement checkStatement = con.prepareStatement(checkQuery);
            checkStatement.setString(1, email); //means we are setting argument 1 ie ? in our selectQuery
            ResultSet rs = checkStatement.executeQuery();
            		
            if (rs.next()) {//means if email is already registered, ie we are getting the resultSet of those email that is already registered.
                out.println("<h1>Email is already registered, try a new one</h1>");
            } else {
                String insertQuery = "INSERT INTO userdata (firstName, lastName, email, password, role) VALUES (?, ?, ?, ?, ?)";
                PreparedStatement insertStatement = con.prepareStatement(insertQuery);
                insertStatement.setString(1, firstName);
                insertStatement.setString(2, lastName);
                insertStatement.setString(3, email);
                insertStatement.setString(4, password);
                insertStatement.setString(5, "Regular User"); //means every new user is registered as regular user.

                int rowAffected = insertStatement.executeUpdate();

                if (rowAffected == 1) {
                    out.println("<h1>Sign up successful</h1>");
                } else {
                    out.println("<h1>Record could not be inserted.</h1>");
                }
                insertStatement.close();
            }

            checkStatement.close();
            con.close();

        } catch (Exception e) {
            out.println("<h1>Error: " + e.getMessage() + "</h1>");
        }
	}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */


}
