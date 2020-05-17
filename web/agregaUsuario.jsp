<%-- 
    Document   : agregaUsuario
    Created on : 15/05/2020, 02:22:58 PM
    Author     : Alex
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Chirruper</title>
    </head>
    <body >
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
                    <td>Escribe aqui para buscar un Chirrupero:</td>
                    <td><input type="text" name="buscaUsuarios" value="" onkeyup="loadNewContent('sugerencias',this.value);"/></td>
                    <td><div id = "sugerencias"><b>Sugerencias:</b></div></td>
                </tr>
                <tr>
                    <td>¿A que Chirrupero quieres seguir?</td>
                    <td><input id="usuarioASeguir" type="text" name="usuarioASeguir" value=""/></td>
                    <td><input type="button" value="Seguir" onclick="seguirUsuario('mensaje','usuarioASeguir');"/></td>
                </tr>
            </tbody>
        </table>
        <br>
        <div id="mensaje"></div>
        
        
        <script>
            function loadNewContent(id, target) 
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
                ajaxRequest.open("GET", "S_SugiereUsuarios?usuario="+target, true /*async*/);
                ajaxRequest.send();
            }
        </script>
        
        <script>
            function seguirUsuario(id, target) 
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
                var usuarioA = document.getElementById(target).value;
                ajaxRequest.open("GET", "S_SeguirUsuario?usuario="+usuarioA, true /*async*/);
                ajaxRequest.send();
            }
        </script>
    </body>
</html>
