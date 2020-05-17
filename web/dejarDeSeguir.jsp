<%-- 
    Document   : dejarDeSeguir
    Created on : 15/05/2020, 09:10:15 PM
    Author     : Alex
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Chirruper</title>
    </head>
    <body onload="loadNewContent('siguiendo');">
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
                    <td><b>Estos son los Chirruperos a los que sigues actualmente:</b></td>
                    <td colspan="2"><b>¿A quien quieres dejar de seguir?</b></td>
                </tr>
                <tr>
                    <td><div id = "siguiendo"></div></td>
                    <td><input type="text" id="dejarDeSeguir" value="" /></td>
                    <td><input type="button" value="Confirmar" onclick = "dejaDeSeguir('mensaje','dejarDeSeguir');"/></td>
                </tr>
            </tbody>
        </table>
        <div id = 'mensaje'></div>
        
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
            function dejaDeSeguir(id, info) 
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
                var quien = document.getElementById(info).value;
                var timer = setTimeout(loadNewContent, 300, 'siguiendo');   
                ajaxRequest.open("GET", "S_DejaDeSeguir?quien="+quien, true /*async*/);
                ajaxRequest.send();
            }
        </script>
        
    </body>
</html>
