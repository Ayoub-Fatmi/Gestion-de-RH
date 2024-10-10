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
import javax.servlet.http.HttpSession;

import gestion_RH.Conge;

@WebServlet("/DemandeCongeServlet")
public class DemandeCongeServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String dateDebut = request.getParameter("dateDebut");
        String dateFin = request.getParameter("dateFin");
        String motif = request.getParameter("motif");

        String jdbcUrl = "jdbc:mysql://localhost:3306/db_prj_rh";
        String dbUser = "root";
        String dbPassword = "";

        try {
            try (Connection connection = DriverManager.getConnection(jdbcUrl, dbUser, dbPassword)) {
                String sql = "INSERT INTO conge (ID_Employe, Date_Debut, Date_Fin, Motif, Statut) VALUES (?, ?, ?, ?, 'En attente')";
                try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                    preparedStatement.setInt(1,Integer.parseInt((String) request.getSession().getAttribute("idEmploye")));
                    preparedStatement.setString(2, dateDebut);
                    preparedStatement.setString(3, dateFin);
                    preparedStatement.setString(4, motif);

                    int rowsInserted = preparedStatement.executeUpdate();

                    if (rowsInserted > 0) {
                        response.sendRedirect("/gestion_RH/ShowCongeEmp");
                    } else {
                    	HttpSession maSession = request.getSession();
        	            maSession.setAttribute("demandeFailed", "Adding conge failed");
        	            response.sendRedirect("/gestion_RH/ShowCongeEmp");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
