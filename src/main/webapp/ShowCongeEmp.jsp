<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="gestion_RH.Conge"%>
<%@ page import="java.util.List"%>
<!DOCTYPE html>
<html lang="en">
<%
    // Récupérer la session existante ou créer une nouvelle
    HttpSession maSession = request.getSession(false);

    // Vérifier si la session existe et si l'attribut "role" est défini à "admin"
    if (maSession == null || maSession.getAttribute("role") == null || !maSession.getAttribute("role").equals("employee")) {
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

        button {
            background-color: #3498db;
            color: #fff;
            border: none;
            padding: 5px 10px;
            font-size: 14px;
            cursor: pointer;
            border-radius: 4px;
            transition: background-color 0.3s ease;
        }

        button:hover {
            background-color: #2980b9;
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

    <a href="/gestion_RH/DemandeConge.jsp"><button>Demande Un congé</button></a>

    <table>
        <thead>
            <tr>
                <th>ID</th>
                <th>Date de Début</th>
                <th>Date de Fin</th>
                <th>Motif</th>
                <th>Statut</th>
            </tr>
        </thead>
        <% List<Conge> conges = (List<Conge>) request.getSession().getAttribute("conges");
        if (conges != null && !conges.isEmpty()) { %>
            <tbody>
                <% for (Conge conge : conges) { %>
                    <tr>
                        <td><%= conge.getId() %></td>
                        <td><%= conge.getDateDebut() %></td>
                        <td><%= conge.getDateFin() %></td>
                        <td><%= conge.getRaison() %></td>
						<td class="<%= conge.getStatut().replace(" ", "").toLowerCase() %>"><%= conge.getStatut() %></td>
                    </tr>
                <% } %>
            </tbody>
        </table>
    <% } %>

    <% if(request.getSession().getAttribute("demandeFailed")!=null){ %>
        <p><%= request.getSession().getAttribute("demandeFailed") %></p>
    <% } %>
</body>
</html>
