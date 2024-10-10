

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/rejeterConge")
public class rejeterConge extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int congeId = Integer.parseInt(request.getParameter("id"));

        String jdbcUrl = "jdbc:mysql://localhost:3306/db_prj_rh";
        String dbUser = "root";
        String dbPassword = "";

        try {
            try (Connection connection = DriverManager.getConnection(jdbcUrl, dbUser, dbPassword)) {
                String sql = "UPDATE conge SET Statut = ? WHERE ID_Conge = ?";
                try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                    preparedStatement.setString(1, "rejet");
                    preparedStatement.setInt(2, congeId);
                    preparedStatement.executeUpdate();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        response.sendRedirect("/gestion_RH/ShowCongeAdmin");
    }
}
