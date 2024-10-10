<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
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
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Accueil Admin</title>
    <style>
    
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            margin: 0;
            padding: 0;
            display: flex;
            align-items: center;
            justify-content: center;
            height: 100vh;
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
    </style>
</head>
<body>
    <a href="/gestion_RH/SelectEmployee"><button>Gestion des employés</button></a>
    <a href="/gestion_RH/ShowCongeAdmin"><button>Gestion des congés</button></a>
</body>
</html>
