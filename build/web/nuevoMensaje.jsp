<%-- 
    Document   : nuevoMensaje
    Created on : 16/05/2020, 07:27:53 PM
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
        <%
            HttpSession mySession = request.getSession();
            if(mySession.getAttribute("nombre")!= null)
            {
                String nombre = mySession.getAttribute("nombre").toString();
                out.print("<h1>¡Bienvenid@ "+nombre+"!</h1>");
            }
            else
                response.sendRedirect("index.jsp");
        %>

        <table border=".5">
            <tbody>
                <tr>
                    <td><a href='bienvenido.jsp'>Inicio</a></td>
                    <td><a href='nuevoMensaje.jsp'>Nuevo Chirrup</a></td>
                    <td><a href='agregaUsuario.jsp'>Buscar Chirrupero</a></td>
                    <td><a href='dejarDeSeguir.jsp'>Dejar de seguir Chirrupero</a></td>
                    <td><a href='verMisSeguidores.jsp'>Ver mis seguidores</a></td>
                    <td><a href='cerrarSesion.jsp'>Cerrar sesion</a></td>
                </tr>
            </tbody>
        </table>
        <br>
        
        
        <table border="1">
            <tbody>
                <tr>
                    <td colspan = "2"><b>Escribe aqui tu nuevo Chirrup:</b></td>
                </tr>
                <tr>
                    <td><textarea id="chirrup" name="" rows="5" cols="40"></textarea></td>
                    <td><input type="button" value="Chirrupear" onclick="aChirrupear('mensaje','chirrup')"/></td>
                </tr>
            </tbody>
        </table>
        <div id = 'mensaje'></div>
        
        <script>
            function aChirrupear(id, info) 
            {
                var ajaxRequest;
                if (window.XMLHttpRequest)
                {
                    ajaxRequest=new XMLHttpRequest(); // IE7+, Firefox, Chrome, Opera, Safari
                } 
                else 
                {
                    ajaxRequest=new ActiveXObject("Microsoft.XMLHTTP"); // IE6, IE5
                }
                ajaxRequest.onreadystatechange = function()
                {
                    if (ajaxRequest.readyState == 4 && ajaxRequest.status == 200) 
                    {
                        document.getElementById(id).innerHTML = ajaxRequest.responseText;
                    }
                }
                var chirrup = document.getElementById(info).value;
                ajaxRequest.open("GET", "S_NuevoChirrup?chirrup="+chirrup, true /*async*/);
                ajaxRequest.send();
            }
        </script>
    </body>
</html>
