import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.sql.*;

@WebServlet("/update")
public class update extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public update() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        session.setMaxInactiveInterval(60 * 60 * 24);
        PrintWriter out = response.getWriter();
        try {
            Class.forName("com.mysql.jdbc.Driver");
            response.setContentType("text/html");
            String url = "jdbc:mysql://127.0.0.1:3306/mysql";
            Connection conn = DriverManager.getConnection(url, "root", "root");

            if (session != null) {
                if (((String) session.getAttribute("type")).equals("Admin")) {
                    String id = request.getParameter("id");
                    String name = request.getParameter("name");
                    String model = request.getParameter("model");
                    String ownerName = request.getParameter("ownerName");
                    String ownerId = request.getParameter("ownerId");
                    String phone = request.getParameter("phone");

                    String checkQuery = "UPDATE bike SET owner = ?, cnic = ?, phone = ? WHERE id = ? AND name = ? AND model = ?";

                    PreparedStatement ps = conn.prepareStatement(checkQuery);
                    ps.setString(1, name);
                    ps.setString(2, ownerId);
                    ps.setString(3, phone);
                    ps.setString(4, id);
                    ps.setString(5, name);
                    ps.setString(6, model);

                    int updateCheck = ps.executeUpdate();

                    if (updateCheck > 0) {
                        out.println("<h1>Record Updated Successfully</h1>");
                    } else {
                        out.println("<h1>No record found with the provided bike name, model and number plate .</h1>");
                    }

                } else {
                    out.println("<h1>As a regular user you cannot access this functionality</h1>");
                }
            } else {
                out.println("Error, you are not signed in");
            }
        } catch (Exception e) {
            // Handle any SQL errors
            out.println("Error: " + e.getMessage());
        }
    }
}
