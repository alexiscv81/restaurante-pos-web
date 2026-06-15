<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Login - Restaurante POS Web</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/estilos.css">
</head>
<body>
<div class="contenedor">
    <h2>Iniciar sesión</h2>
    <% if (request.getAttribute("error") != null) { %>
        <div class="error"><%= request.getAttribute("error") %></div>
    <% } %>
    <form method="post" action="${pageContext.request.contextPath}/login" class="formulario">
        <label>Correo</label>
        <input type="email" name="correo" required>
        <label>Contraseña</label>
        <input type="password" name="password" required>
        <br><br>
        <button type="submit" class="btn">Entrar</button>
    </form>
    <p>Usuario demo: admin@restaurante.com / admin123</p>
</div>
</body>
</html>