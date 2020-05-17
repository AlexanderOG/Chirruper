<%-- 
    Document   : cerrarSesion
    Created on : 15/05/2020, 12:03:00 PM
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
            if(mySession.getAttribute("nombre")!=null)
            {
                out.print("<h1>¡Gracias por utilizar Chirruper "+ mySession.getAttribute("nombre") + "!</h1>");
                mySession.invalidate();
                out.print("<p>Has cerrado tu sesion con exito.</p>");
                out.println("<a href = 'index.jsp'>Regresar a inicio</a>");
            }
            else
                response.sendRedirect("index.jsp");
        %>
    </body>
</html>
