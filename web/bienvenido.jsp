<%-- 
    Document   : bienvenido
    Created on : 14/05/2020, 10:21:33 PM
    Author     : Alex
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Chirruper</title>
    </head>
    <body onload="loadNewContent('siguiendo');leeChirrups('chirrups');">
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
        
        <p><b>Estos son los Chirruperos a los que sigues actualmente:</b></p>
        <div id = "siguiendo"></div>
     
        <p><b>Estos son los ultimos Chirrups de los Chirruperos que sigues:</b></p>

        <div id = "chirrups"></div><br>
        
        <input type="button" value="Actualizar Chirrups" onclick="leeChirrups('chirrups');" />
        <script>
            function loadNewContent(id) 
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
                ajaxRequest.open("GET", "S_ObtenQuienesSigo", true /*async*/);
                ajaxRequest.send();
            }
        </script>
        
        <script>
            function leeChirrups(id) 
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
                ajaxRequest.open("GET", "S_LeeChirrups", true /*async*/);
                ajaxRequest.send();
            }
        </script>
        
    </body>
</html>
