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
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Alex
 */
public class S_ObtenQuienesSigo extends HttpServlet {

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
            HttpSession mySession = request.getSession();
            String usuario = mySession.getAttribute("username").toString();
            Connection con = null;
            try
            {
                Class.forName("org.apache.derby.jdbc.ClientDriver");                
                con = DriverManager.getConnection("jdbc:derby://localhost:1527/ChirruperDB/","root","root");
                Statement query = con.createStatement();
                ResultSet rs = query.executeQuery("SELECT SIGUE_A FROM SEGUIDORES WHERE USUARIO='"+usuario+"'");
                String resp = "Actualmente no sigues a ningun Chirrupero.";
                Boolean primera = true;
                while(rs.next())
                {
                    if(primera)
                    {
                        primera = false;
                        resp = "<table border='1'><thead><tr><th>Chirrupero</th></thead><tbody>";
                    }
                    resp += "<tr><td>" + rs.getString("SIGUE_A") + "</td></tr>";
                }
                resp += "</tbody></table>";
                out.print(resp);
                con.commit();
                con.close();
            }catch(Exception e)
            {
                try 
                {
                    out.print("ERROR: " + e.getMessage());
                    System.out.println(e.getMessage());
                    con.commit();
                    con.close();
                } catch (SQLException ex) {
                    Logger.getLogger(S_ObtenQuienesSigo.class.getName()).log(Level.SEVERE, null, ex);
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
