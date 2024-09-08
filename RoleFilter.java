import java.io.IOException;
import javax.servlet.*;
import javax.servlet.http.*;

public class RoleFilter implements Filter {

    public void init(FilterConfig filterConfig) throws ServletException {}

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        HttpSession session = req.getSession(false);

            // Check the user role
            String role = (String) session.getAttribute("type");

            // Get the requested URL
            String requestURI = req.getRequestURI();

            // Check access control for admin pages
            if (requestURI.contains("adminHomePage.html") && !"Admin".equals(role)) {
            	// session.setAttribute("lastVisitedPage", "adminhomepage.html");
                res.sendRedirect("SignIn.html"); // Redirect to index if not admin
            } 
            else if (requestURI.contains("regularUser.html") && (!"Admin".equals(role) && !"Regular User".equals(role))) 
            {
//            else if (!"admin".equals(role) && !"regular".equals(role)) {
            	 //session.setAttribute("lastVisitedPage", requestURI);
                res.sendRedirect("SignIn.html"); // Redirect to index if role is neither admin nor regular
            }else {
                // Valid session and role, proceed to the requested page
                chain.doFilter(request, response);
            }
        }
    
    public void destroy() {}
}
