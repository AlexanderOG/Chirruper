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
public class S_DejaDeSeguir extends HttpServlet {

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
            String sigue = request.getParameter("quien");
            System.out.println("SERVLET DEJA" + sigue);
            Connection con = null;
            try
            {
                Class.forName("org.apache.derby.jdbc.ClientDriver");                
                con = DriverManager.getConnection("jdbc:derby://localhost:1527/ChirruperDB/","root","root");
                Statement query = con.createStatement();
                ResultSet rs = query.executeQuery("SELECT SIGUE_A FROM SEGUIDORES WHERE USUARIO='"+usuario+"'");
                Boolean existe = false;
                String resp = "";
                while(rs.next())
                {
                    if(rs.getString("SIGUE_A").equals(sigue))
                        existe = true;
                }
                if(existe)
                {
                    query.executeUpdate("DELETE FROM SEGUIDORES where USUARIO='"+usuario+"' and SIGUE_A='"+sigue+"'");
                    resp = "Has dejado de seguir al Chirrupero con nombre de usuario '"+sigue+"'.";
                }
                else
                    resp = "No sigues a ningun Chirrupero nombre de usuario '"+sigue+"'. Verifica que lo hayas escrito bien.";
                out.print(resp);
            }catch(Exception e)
            {
                out.print("ERROR: " + e.getMessage());
                System.out.println(e.getMessage());
            }
            finally
            {
                try 
                {
                    con.commit();
                    con.close();
                } catch (SQLException ex) {
                    Logger.getLogger(S_DejaDeSeguir.class.getName()).log(Level.SEVERE, null, ex);
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
