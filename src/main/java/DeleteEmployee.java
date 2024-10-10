

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


@WebServlet("/DeleteEmployee")
public class DeleteEmployee extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String jdbcUrl = "jdbc:mysql://localhost:3306/db_prj_rh";
        String dbUser = "root";
        String dbPassword = "";

        try {
        	try (Connection connection = DriverManager.getConnection(jdbcUrl, dbUser, dbPassword)) {
        	    // Delete from 'conge' table
        	    String deleteCongeSql = "DELETE FROM conge WHERE ID_Employe=?";
        	    try (PreparedStatement deleteCongeStatement = connection.prepareStatement(deleteCongeSql)) {
        	        deleteCongeStatement.setString(1, request.getParameter("id"));
        	        int rowsAffectedConge = deleteCongeStatement.executeUpdate();
        	        
        	        
        	     // Delete from 'compte' table
            	    String deleteAccountSql = "DELETE FROM compte WHERE ID_Employe=?";
            	    try (PreparedStatement deleteAccountStatement = connection.prepareStatement(deleteAccountSql)) {
            	        deleteAccountStatement.setString(1, request.getParameter("id"));
            	        int rowsAffectedAccount = deleteAccountStatement.executeUpdate();
            	        
            	        
            	     // Delete from 'employe' table
                	    String deleteEmployeeSql = "DELETE FROM employe WHERE ID_Employe=?";
                	    try (PreparedStatement deleteEmployeeStatement = connection.prepareStatement(deleteEmployeeSql)) {
                	        deleteEmployeeStatement.setString(1, request.getParameter("id"));
                	        int rowsAffectedEmployee = deleteEmployeeStatement.executeUpdate();

                	        if (rowsAffectedEmployee > 0 && rowsAffectedAccount > 0) {
                	            response.sendRedirect("/gestion_RH/SelectEmployee");
                	        } else {
                	            HttpSession maSession = request.getSession();
                	            maSession.setAttribute("EmpDelFailed", "Delete failed");
                	            response.sendRedirect("/gestion_RH/SelectEmployee");
                	        }
                	    }
            	    }
        	    } 
        	}
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
