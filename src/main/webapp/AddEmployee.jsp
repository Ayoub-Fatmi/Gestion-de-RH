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
    <title>Ajout d'un employé</title>
    <style>
        body {
            font-family: Arial, sans-serif;
		    background-color: #f4f4f4;
		    margin: 0;
		    padding: 0;
		    display: flex;
		    justify-content: center;
		    align-content: center;
		    flex-direction: column;
		    align-items: center;
        }
        h2 {
            color: #333;
            margin-bottom: 20px;
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

        input, select {
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
        }

        input[type="submit"]:hover {
            background-color: #45a049;
        }

        script {
            margin-top: 10px;
        }
    </style>
</head>
<body>
    <h2>Ajouter les informations de l'employé :</h2>

    <form action="/gestion_RH/AddEmployee" method="post" onsubmit="return confirmUpdate()">
        <label for="prenom">Prénom:</label>
        <input type="text" id="prenom" name="prenom" required><br>

        <label for="nom">Nom:</label>
        <input type="text" id="nom" name="nom" required><br>

        <label for="dateDeNaissance">Date de Naissance:</label>
        <input type="date" id="dateDeNaissance" name="dateDeNaissance" required><br>

        <label for="email">Email:</label>
        <input type="email" id="email" name="email" required><br>

        <label for="salaire">Salaire:</label>
        <input type="number" id="salaire" name="salaire" step="0.01" min="0" max="9999999.99" required><br>

        <label for="dateEmbauche">Date d'embauche:</label>
        <input type="date" id="dateEmbauche" name="dateEmbauche" required><br>

        <label for="adresse">Adresse:</label>
        <input type="text" id="adresse" name="adresse" required><br>

        <label for="telephone">Téléphone:</label>
        <input type="tel" id="telephone" name="telephone" required><br>

        <label for="genre">Genre:</label>
        <select id="genre" name="genre" required>
            <option value="Male">Male</option>
            <option value="Female">Female</option>
        </select><br>

        <input type="submit" value="Ajouter">
    </form>

    <script>
        function confirmUpdate() {
            return confirm("Êtes-vous sûr de vouloir ajouter un employé ?");
        }
    </script>
</body>
</html>
