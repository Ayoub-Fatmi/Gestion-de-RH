

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet("/LoginServ")
public class LoginServ extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public LoginServ() {
        super();
    }
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("uname");
        String password = request.getParameter("pass");
        // Your database connection details
        String jdbcUrl = "jdbc:mysql://localhost:3306/db_prj_rh";
        String dbUser = "root";
        String dbPassword = "";

        try {
            Connection connection = DriverManager.getConnection(jdbcUrl, dbUser, dbPassword);
            
            String sql = "SELECT * FROM compte WHERE Username=? AND Password=?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, username);
                statement.setString(2, password);

                ResultSet resultSet = statement.executeQuery();

                if (resultSet.next()) {
                	
                	String userRole = resultSet.getString("Role");
                	String IdEmploye = resultSet.getString("ID_Employe");
                    if (userRole.equals("Admin")) {
                        HttpSession maSession = request.getSession();
                        maSession.setAttribute("idAdmin", IdEmploye );
                        maSession.setAttribute("role", "admin" );
                        response.sendRedirect("/gestion_RH/Admin.jsp");
                    } else if(userRole.equals("Employe"))  {
                        HttpSession maSession = request.getSession();
                        maSession.setAttribute("idEmploye", IdEmploye);
                        maSession.setAttribute("role", "employee" );
                        response.sendRedirect("/gestion_RH/ShowCongeEmp");
                    }else {
                    	HttpSession maSession = request.getSession();
        			    maSession.setAttribute("LogFailed", "Login failed");
                        response.sendRedirect("/gestion_RH/Login.jsp");
                    }
                } else {
                	HttpSession maSession = request.getSession();
    			    maSession.setAttribute("LogFailed", "Login failed");
                    response.sendRedirect("/gestion_RH/Login.jsp"); // Redirect to an error page
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            HttpSession maSession = request.getSession();
		    maSession.setAttribute("LogFailed", "Login failed");
            response.sendRedirect("/gestion_RH/Login.jsp");
        }
	}

}
