

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

import java.sql.Statement;


@WebServlet("/AddEmployee")
public class AddEmployee extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public AddEmployee() {
        super();
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String prenom = handleNullOrEmpty(request.getParameter("prenom"));
        String nom = handleNullOrEmpty(request.getParameter("nom"));
        String dateDeNaissance = handleNullOrEmpty(request.getParameter("dateDeNaissance"));
        String email = handleNullOrEmpty(request.getParameter("email"));
        String dateEmbauche = handleNullOrEmpty(request.getParameter("dateEmbauche"));
        String adresse = handleNullOrEmpty(request.getParameter("adresse"));
        String telephone = handleNullOrEmpty(request.getParameter("telephone"));
        String genre = handleNullOrEmpty(request.getParameter("genre"));
        Double salaire=0.0;
        if (!request.getParameter("salaire").isEmpty()) {
            salaire = Double.parseDouble(request.getParameter("salaire"));
        }

        String jdbcUrl = "jdbc:mysql://localhost:3306/db_prj_rh";
        String dbUser = "root";
        String dbPassword = "";

        try {
            try (Connection connection = DriverManager.getConnection(jdbcUrl, dbUser, dbPassword)) {
                String sql = "INSERT INTO employe (Prenom, Nom, Date_De_Naissance, Email, Salaire, Date_Embauche, Adresse, Telephone, Genre) "
                        + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
                try (PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                    preparedStatement.setString(1, prenom);
                    preparedStatement.setString(2, nom);
                    preparedStatement.setString(3, dateDeNaissance);
                    preparedStatement.setString(4, email);
                    preparedStatement.setDouble(5, salaire);
                    preparedStatement.setString(6, dateEmbauche);
                    preparedStatement.setString(7, adresse);
                    preparedStatement.setString(8, telephone);
                    preparedStatement.setString(9, genre);

                    int rowsInserted = preparedStatement.executeUpdate();

                    if (rowsInserted > 0) {
                    	int newEmployeeId= -1;
                        try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                            if (generatedKeys.next()) {
                                newEmployeeId = generatedKeys.getInt(1);
                            }
                        }
                    	sql = "INSERT INTO `compte`(`Username`, `Password`, `Role`, `ID_Employe`) "
                        		+ "VALUES (?,?,?,?)";
                        try (PreparedStatement preparedStatementc = connection.prepareStatement(sql)) {
                            preparedStatementc.setString(1, nom+"."+prenom);
                            preparedStatementc.setString(2, nom+"."+prenom+"123");
                            preparedStatementc.setString(3, "Employe");
                            preparedStatementc.setInt(4, newEmployeeId);
                            rowsInserted = preparedStatementc.executeUpdate();
                        }
                        response.sendRedirect("/gestion_RH/SelectEmployee");
                    } else {                        
                    	HttpSession maSession = request.getSession();
        	            maSession.setAttribute("EmpAddFailed", "Adding failed");
        	            response.sendRedirect("/gestion_RH/SelectEmployee");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); 
        }
	}

    private String handleNullOrEmpty(String value) {
        return (value != null && !value.isEmpty()) ? value : null;
    }
}
