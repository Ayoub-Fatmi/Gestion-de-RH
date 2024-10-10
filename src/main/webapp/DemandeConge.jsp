<%@ page language="java" contentType="text/html; charset=UTF-8"%>
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
    <title>Demande de Congé</title>
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

        form {
            background-color: #ffffff;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            max-width: 800px;
            width: 100%;
        }

        label {
            display: block;
            margin-bottom: 8px;
        }

        input, textarea {
            width: 100%;
            padding: 10px;
            margin-bottom: 15px;
            box-sizing: border-box;
            border: 1px solid #ccc;
            border-radius: 4px;
        }

        input[type="submit"] {
            background-color: #4caf50;
            color: #fff;
            cursor: pointer;
            border: none;
            padding: 10px;
            border-radius: 4px;
            font-size: 16px;
        }

        input[type="submit"]:hover {
            background-color: #45a049;
        }
    </style>
</head>
<body>

    <h2>Demande de Congé :</h2>

    <form action="/gestion_RH/DemandeCongeServlet" method="post">
        <label for="dateDebut">Date de Début :</label>
        <input type="date" id="dateDebut" name="dateDebut" required><br>

        <label for="dateFin">Date de Fin :</label>
        <input type="date" id="dateFin" name="dateFin" required><br>

        <label for="motif">Motif :</label>
        <textarea id="motif" name="motif" required></textarea><br>

        <input type="submit" value="Demander Congé">
    </form>
</body>
</html>
