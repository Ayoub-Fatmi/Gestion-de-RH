

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import gestion_RH.Employee;

@WebServlet("/EditEmployee")
public class EditEmployee extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public EditEmployee() {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String jdbcUrl = "jdbc:mysql://localhost:3306/db_prj_rh";
        String dbUser = "root";
        String dbPassword = "";
        try {
            try (Connection connection = DriverManager.getConnection(jdbcUrl, dbUser, dbPassword)) {
                String sql = "SELECT * FROM employe WHERE ID_Employe=?";
                try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                	preparedStatement.setString(1, request.getParameter("id"));
                    try (ResultSet resultSet = preparedStatement.executeQuery()) {
                        if (resultSet.next()) {
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
                            HttpSession session = request.getSession();
                            session.setAttribute("employee", employe);
                        }
                        response.sendRedirect("/gestion_RH/EditEmployee.jsp");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
		Employee employee = (Employee) request.getSession().getAttribute("employee");
        int employeeId = employee.getId();
        String prenom = request.getParameter("prenom");
        String nom = request.getParameter("nom");
        String dateDeNaissance = request.getParameter("dateDeNaissance");
        String email = request.getParameter("email");
        double salaire = Double.parseDouble(request.getParameter("salaire"));
        String dateEmbauche = request.getParameter("dateEmbauche");
        String adresse = request.getParameter("adresse");
        String telephone = request.getParameter("telephone");
        String genre = request.getParameter("genre");

        String jdbcUrl = "jdbc:mysql://localhost:3306/db_prj_rh";
        String dbUser = "root";
        String dbPassword = "";

        try {
            try (Connection connection = DriverManager.getConnection(jdbcUrl, dbUser, dbPassword)) {
                String sql = "UPDATE employe SET Prenom=?, Nom=?, Date_De_Naissance=?, "
                        + "Email=?, Salaire=?, Date_Embauche=?, Adresse=?, "
                        + "Telephone=?, Genre=? WHERE ID_Employe=?";
                try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                    preparedStatement.setString(1, prenom);
                    preparedStatement.setString(2, nom);
                    preparedStatement.setString(3, dateDeNaissance);
                    preparedStatement.setString(4, email);
                    preparedStatement.setDouble(5, salaire);
                    preparedStatement.setString(6, dateEmbauche);
                    preparedStatement.setString(7, adresse);
                    preparedStatement.setString(8, telephone);
                    preparedStatement.setString(9, genre);

                    preparedStatement.setInt(10, employeeId);

                    int rowsUpdated = preparedStatement.executeUpdate();

                    if (rowsUpdated > 0) {
                        response.sendRedirect("/gestion_RH/SelectEmployee");
                    } else {
        	            HttpSession maSession = request.getSession();
        	            maSession.setAttribute("EmpUpdateFailed", "Update failed");
        	            response.sendRedirect("/gestion_RH/SelectEmployee");                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Gérer l'exception de manière appropriée dans une application réelle
        }
    }
}
