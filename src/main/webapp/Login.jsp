<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login</title>
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

        .login-container {
            max-width: 400px;
            width: 100%;
        }

        form {
            background-color: #ffffff;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }

        label {
            display: block;
            margin-bottom: 8px;
        }

        input {
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

        .error-message {
            color: #f00;
            margin-top: 10px;
        }
    </style>
</head>
<body>
    <div class="login-container">
        <form action="/gestion_RH/LoginServ" method="post">
            <label for="uname">Username:</label><br>
            <input type="text" id="uname" name="uname" required><br>
            <label for="pass">Password:</label><br>
            <input type="password" id="pass" name="pass" required><br><br>
            <input type="submit" value="Submit">
        </form>

        <% if (request.getSession().getAttribute("LogFailed") != null) { %>
            <p class="error-message"><%= request.getSession().getAttribute("LogFailed") %></p>
        <% } %>
    </div>
</body>
</html>
