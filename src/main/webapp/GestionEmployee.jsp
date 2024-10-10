<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ page import="gestion_RH.Employee"%>
<%@ page import="java.util.List"%>
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
    <title>Liste des employés</title>
    <style>
    
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            margin: 0;
            margin-top: 2%;
            padding: 0;
            display: flex;
            flex-direction: column;
            align-items: center;
            height: 100vh;
        }

        h2 {
            color: #333;
        }

        a {
            text-decoration: none;
            margin: 10px;
        }

        button {
            background-color: #3498db;
            color: #fff;
            border: none;
            padding: 10px 20px;
            font-size: 16px;
            cursor: pointer;
            border-radius: 4px;
            transition: background-color 0.3s ease;
        }

        button:hover {
            background-color: #2980b9;
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

        .error-message {
            color: #f00;
            margin-top: 10px;
        }
    </style>
</head>
<body>

    <h2>Liste des employés :</h2>
    <a href="/gestion_RH/AddEmployee.jsp"><button>Ajouter</button></a>
    <table>
        <thead>
            <tr>
                <th>ID</th>
                <th>Prénom</th>
                <th>Nom</th>
                <th>Date de Naissance</th>
                <th>Email</th>
                <th>Salaire</th>
                <th>Date d'embauche</th>
                <th>Adresse</th>
                <th>Téléphone</th>
                <th>Genre</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody>
            <% List<Employee> employes = (List<Employee>) request.getSession().getAttribute("employees");
            if (employes != null && !employes.isEmpty()) {
                for (Employee employe : employes) { %>
                    <tr>
                        <td><%= employe.getId() %></td>
                        <td><%= employe.getPrenom() %></td>
                        <td><%= employe.getNom() %></td>
                        <td><%= employe.getDateDeNaissance() %></td>
                        <td><%= employe.getEmail() %></td>
                        <td><%= employe.getSalaire() %></td>
                        <td><%= employe.getDateEmbauche() %></td>
                        <td><%= employe.getAdresse() %></td>
                        <td><%= employe.getTelephone() %></td>
                        <td><%= employe.getGenre() %></td>
                        <td>
                            <a href="/gestion_RH/EditEmployee?id=<%= employe.getId()%>"><button>Modifier</button></a>
                            <a href="/gestion_RH/DeleteEmployee?id=<%= employe.getId()%>" onclick="return confirmUpdate()"><button>Supprimer</button></a>
                        </td>
                    </tr>
                <% }
            } %>
        </tbody>
    </table>

    <% if (request.getSession().getAttribute("EmpDelFailed") != null) { %>
        <p class="error-message"><%= request.getSession().getAttribute("EmpDelFailed") %></p>
    <% } %>

    <% if (request.getSession().getAttribute("EmpUpdateFailed") != null) { %>
        <p class="error-message"><%= request.getSession().getAttribute("EmpUpdateFailed") %></p>
    <% } %>

    <% if (request.getSession().getAttribute("EmpAddFailed") != null) { %>
        <p class="error-message"><%= request.getSession().getAttribute("EmpAddFailed") %></p>
    <% } %>

    <script>
        function confirmUpdate() {
            return confirm("Êtes-vous sûr de vouloir supprimer l'employé ?");
        }
    </script>

</body>
</html>
