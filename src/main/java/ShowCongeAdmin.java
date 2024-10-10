import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import gestion_RH.Conge;
import gestion_RH.Employee;

@WebServlet("/ShowCongeAdmin")
public class ShowCongeAdmin extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public ShowCongeAdmin() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String jdbcUrl = "jdbc:mysql://localhost:3306/db_prj_rh";
        String dbUser = "root";
        String dbPassword = "";
        try {
            try (Connection connection = DriverManager.getConnection(jdbcUrl, dbUser, dbPassword)) {
                String sql = "SELECT * FROM conge";
                try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

                    try (ResultSet resultSet = preparedStatement.executeQuery()) {
                        List<Conge> conges = new ArrayList<>();
                        HashMap<Integer, Employee> employeeMap = new HashMap<>();

                        while (resultSet.next()) {
                            Conge conge = new Conge();
                            conge.setId(resultSet.getInt("ID_Conge"));
                            conge.setIdEmploye(resultSet.getInt("ID_Employe"));
                            conge.setDateDebut(resultSet.getDate("Date_Debut"));
                            conge.setDateFin(resultSet.getDate("Date_Fin"));
                            conge.setRaison(resultSet.getString("Motif"));
                            conge.setStatut(resultSet.getString("Statut"));
                            conges.add(conge);

                            int idEmployee = resultSet.getInt("ID_Employe");
                            if (!employeeMap.containsKey(idEmployee)) {
                                Employee employee = fetchEmployeeDetails(connection, idEmployee);
                                employeeMap.put(idEmployee, employee);
                            }
                        }

                        HttpSession session = request.getSession();
                        session.setAttribute("congesAdmin", conges);
                        session.setAttribute("employeeMap", employeeMap);

                        response.sendRedirect("/gestion_RH/ShowCongeAdmin.jsp");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Employee fetchEmployeeDetails(Connection connection, int idEmployee) throws SQLException {
        String employeeSql = "SELECT * FROM employe WHERE ID_Employe = ?";
        try (PreparedStatement employeeStatement = connection.prepareStatement(employeeSql)) {
            employeeStatement.setInt(1, idEmployee);
            try (ResultSet employeeResult = employeeStatement.executeQuery()) {
                if (employeeResult.next()) {
                    Employee employee = new Employee();
                    employee.setPrenom(employeeResult.getString("Prenom"));
                    employee.setNom(employeeResult.getString("Nom"));
                    return employee;
                }
            }
        }
        return null;
    }
}
