<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="gestion_RH.Conge" %>
<%@ page import="gestion_RH.Employee" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<!DOCTYPE html>
<html lang="en">
<%
    // Récupérer la session existante ou créer une nouvelle
    HttpSession maSession = request.getSession(false);

    // Vérifier si la session existe et si l'attribut "role" est défini à "admin"
    if (maSession == null || maSession.getAttribute("role") == null || !maSession.getAttribute("role").equals("admin")) {
        // Rediriger vers la page de connexion
        response.sendRedirect("/gestion_RH/Login.jsp");
    }
%>
<head>
    <meta charset="UTF-8">
    <title>Liste des congés</title>
    <style>
        body {
            font-family: Arial, sans-serif;
		    background-color: #f4f4f4;
		    margin: 0;
		        margin-top: 2%;
	
		    padding: 0;
		    display: flex;
		    align-items: center;
		    justify-content: center;
		    flex-direction: column;
        }

        h2 {
            color: #333;
        }

        table {
            border-collapse: collapse;
            width: 100%;
            margin-top: 20px;
        }

        th, td {
            border: 1px solid #ddd;
            padding: 8px;
            text-align: left;
        }

        th {
            background-color: #f2f2f2;
        }

        tbody tr:hover {
            background-color: #f5f5f5;
        }

        a {
            text-decoration: none;
            margin-right: 10px;
        }

        .status-rejet {
            color: red;
        }

        .status-attente {
            color: orange;
        }

        .status-approuve {
            color: green;
        }

        .btn-approuve {
            background-color: green;
            color: #fff;
        }

        .btn-rejet {
            background-color: red;
            color: #fff;
        }

        button {
            border: none;
            padding: 5px 10px;
            font-size: 14px;
            cursor: pointer;
            border-radius: 4px;
            transition: background-color 0.3s ease;
        }

        button:hover {
            opacity: 0.8;
        }
		.rejet {
            color: red;
        }

        .enattente {
            color: orange;
        }

        .approuve {
            color: green;
        }
        span {
            color: #555;
        }
    </style>
</head>
<body>

    <h2>Liste des congés :</h2>
    <table>
        <thead>
            <tr>
                <th>ID</th>
                <th>Nom</th>
                <th>Prénom</th>
                <th>Date de Début</th>
                <th>Date de Fin</th>
                <th>Motif</th>
                <th>Statut</th>
                <th>Actions</th>
            </tr>
        </thead>
        
        <% List<Conge> conges = (List<Conge>) request.getSession().getAttribute("congesAdmin");
           Map<Integer, Employee> employeeMap = (Map<Integer, Employee>) request.getSession().getAttribute("employeeMap");
        if (conges != null && !conges.isEmpty() && employeeMap != null) {
        %>
            <tbody>
                <% for (Conge conge : conges) { %>
                    <tr>
                        <td><%= conge.getId() %></td>
                        <td><%= employeeMap.get(conge.getIdEmploye()).getNom() %></td>
                        <td><%= employeeMap.get(conge.getIdEmploye()).getPrenom() %></td>
                        <td><%= conge.getDateDebut() %></td>
                        <td><%= conge.getDateFin() %></td>
                        <td><%= conge.getRaison() %></td>
						<td class="<%= conge.getStatut().replace(" ", "").toLowerCase() %>"><%= conge.getStatut() %></td>
                        <td>
                            <% if ("En attente".equals(conge.getStatut())) { %>
                                <a href="/gestion_RH/approuverConge?id=<%= conge.getId()%>" onclick="return confirmApproval()" ><button class="btn-approuve">Approuver</button></a>
                                <a href="/gestion_RH/rejeterConge?id=<%= conge.getId()%>" onclick="return confirmRejet()"><button class="btn-rejet">Rejeter</button></a>
                            <% } else { %>
                                <span>Cette demande a été déjà <%= conge.getStatut() %></span>
                            <% } %>
                        </td>
                    </tr>
                <% } %>
            </tbody>
        </table>
    <% } %>
    
    <script>
        function confirmRejet() {
            return confirm("Êtes-vous sûr de vouloir rejeter cette demande ?");
        }

        function confirmApproval() {
            return confirm("Êtes-vous sûr de vouloir approuver cette demande ?");
        }
    </script>
</body>
</html>
