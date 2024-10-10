

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import gestion_RH.Conge;
import gestion_RH.Employee;

@WebServlet("/ShowCongeEmp")
public class ShowCongeEmp extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public ShowCongeEmp() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String jdbcUrl = "jdbc:mysql://localhost:3306/db_prj_rh";
        String dbUser = "root";
        String dbPassword = "";
        try {
            try (Connection connection = DriverManager.getConnection(jdbcUrl, dbUser, dbPassword)) {
                String sql = "SELECT * FROM conge WHERE ID_Employe = ?";
                try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                	System.out.println(Integer.parseInt((String) request.getSession().getAttribute("idEmploye"))+" azr");
                	preparedStatement.setInt(1, Integer.parseInt((String) request.getSession().getAttribute("idEmploye")));
                	
                    try (ResultSet resultSet = preparedStatement.executeQuery()) {
                        List<Conge> conges = new ArrayList<>();

                        while (resultSet.next()) {
                            Conge conge = new Conge();
                            conge.setId(resultSet.getInt("ID_Conge"));
                            conge.setIdEmploye(resultSet.getInt("ID_Employe"));
                            conge.setDateDebut(resultSet.getDate("Date_Debut"));
                            conge.setDateFin(resultSet.getDate("Date_Fin"));
                            conge.setRaison(resultSet.getString("Motif"));
                            conge.setStatut(resultSet.getString("Statut"));

                            conges.add(conge);
                        }
                        HttpSession session = request.getSession();
                        session.setAttribute("conges", conges);
                        response.sendRedirect("/gestion_RH/ShowCongeEmp.jsp");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
