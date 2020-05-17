<%-- 
    Document   : registro
    Created on : 14/05/2020, 08:10:11 PM
    Author     : Alex
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Chirruper</title>
    </head>
    <body>
        <h1>Registrate aqui</h1>
        <form action="S_Registro" method="POST">
            <table border="1">
                <tbody>
                    <tr>
                        <td>Nombre: </td>
                        <td><input type="text" name="nombre" value="" /></td>
                    </tr>
                    <tr>
                        <td>Username: </td>
                        <td><input type="text" name="username" value="" /></td>
                    </tr>
                    <tr>
                        <td>Contraseña: </td>
                        <td><input type="password" name="password1" value="" /></td>
                    </tr>
                    <tr>
                        <td>Confirmar contraseña:</td>
                        <td><input type="password" name="password2" value="" /></td>
                    </tr>
                    <tr>
                        <td><input type="reset" value="Limpiar" /></td>
                        <td><input type="submit" value="Enviar" /></td>
                    </tr>
                </tbody>
            </table>

        </form>
    </body>
</html>
