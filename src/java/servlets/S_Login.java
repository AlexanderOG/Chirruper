/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Alex
 */
public class S_Login extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) 
        {
            Connection con = null;
            try
            {
                String username = request.getParameter("username");
                String password = request.getParameter("password");
                
                if(username != null)
                {
                    Class.forName("org.apache.derby.jdbc.ClientDriver");                
                    con = DriverManager.getConnection("jdbc:derby://localhost:1527/ChirruperDB/","root","root");
                    Statement query = con.createStatement();
                    ResultSet rs = query.executeQuery("SELECT * FROM USUARIOS WHERE USERNAME='"+username+"'");
                    Boolean existe = false;
                    while(rs.next())
                    {
                        existe = true;
                        String auxPswd = rs.getString("PASSWORD");
                        String nombre = rs.getString("NOMBRE");
                        if(auxPswd.equals(password))
                        {                            
                            HttpSession mySession = request.getSession(); //si la sesion  ya existe me la regresa, si no existe crea una nueva
                            mySession.setAttribute("username", username);
                            mySession.setAttribute("nombre", nombre);
                            String recursoAIncluir = "/bienvenido.jsp";
                            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(recursoAIncluir);
                            dispatcher.forward(request, response);
                        }
                        else
                        {
                            String recursoAIncluir = "/iniciaSesion.jsp";
                            out.println("<p>Contraseña incorrecta.</p>");
                            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(recursoAIncluir);
                            dispatcher.include(request, response);
                        }
                    }
                    if(!existe)
                    {
                        out.println("<p>Usuario inexistente.</p>");
                        String recursoAIncluir = "/iniciaSesion.jsp";
                        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(recursoAIncluir);
                        dispatcher.include(request, response);
                    }
                }
                else
                {
                    String recursoAIncluir = "/iniciaSesion.jsp";
                    out.println("<p>Por favor ingresa tus datos.</p>");
                    RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(recursoAIncluir);
                    dispatcher.include(request, response);
                }
                
            }catch(Exception e)
            {
                System.out.println(e.getMessage());
            }
            finally
            {
                try 
                {
                    con.commit();
                    con.close();
                } catch (SQLException ex) {
                    Logger.getLogger(S_Login.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
