

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

import gestion_RH.Employee;


@WebServlet("/SelectEmployee")
public class SelectEmployee extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public SelectEmployee() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String jdbcUrl = "jdbc:mysql://localhost:3306/db_prj_rh";
        String dbUser = "root";
        String dbPassword = "";
        try {
            try (Connection connection = DriverManager.getConnection(jdbcUrl, dbUser, dbPassword)) {
                String sql = "SELECT * FROM Employe e INNER JOIN compte c ON e.ID_Employe = c.ID_Employe WHERE c.Role = 'Employe'";
                try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                    try (ResultSet resultSet = preparedStatement.executeQuery()) {
                        List<Employee> employees = new ArrayList<>();

                        while (resultSet.next()) {
                            Employee employe = new Employee();
                            employe.setId(resultSet.getInt("ID_Employe"));
                            employe.setPrenom(resultSet.getString("Prenom"));
                            employe.setNom(resultSet.getString("Nom"));
                            employe.setDateDeNaissance(resultSet.getString("Date_De_Naissance"));
                            employe.setEmail(resultSet.getString("Email"));
                            employe.setSalaire(resultSet.getDouble("Salaire"));
                            employe.setDateEmbauche(resultSet.getString("Date_Embauche"));
                            employe.setAdresse(resultSet.getString("Adresse"));
                            employe.setTelephone(resultSet.getString("Telephone"));
                            employe.setGenre(resultSet.getString("Genre"));

                            employees.add(employe);
                        }
                        
                        HttpSession session = request.getSession();
                        session.setAttribute("employees", employees);

                        response.sendRedirect("/gestion_RH/GestionEmployee.jsp");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
