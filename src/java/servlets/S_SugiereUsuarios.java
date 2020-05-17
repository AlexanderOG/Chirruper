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
import java.sql.Statement;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Alex
 */
public class S_SugiereUsuarios extends HttpServlet {

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
            try
            {
                ArrayList<String> posiblesUsuarios = new ArrayList<String>();
                Class.forName("org.apache.derby.jdbc.ClientDriver");                
                Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/ChirruperDB/","root","root");
                Statement query = con.createStatement();
                ResultSet rs = query.executeQuery("SELECT * FROM USUARIOS");                
                while(rs.next())
                {
                    posiblesUsuarios.add(rs.getString("USERNAME"));
                }                
                con.close();
                String resp = "<b>Sugerencias: </b><br>";
                String param = request.getParameter("usuario");
                if(param.length() >= 1)
                {
                    for(int i = 0; i < posiblesUsuarios.size(); i++)
                    {
                        if(posiblesUsuarios.get(i).toLowerCase().contains(param.toLowerCase()))
                            resp += "<t>" + posiblesUsuarios.get(i)+"<br>";
                    }
                }
                out.println(resp);
            }
            catch(Exception e)
            {
                System.out.println(e.getMessage());
                out.print("ERROR: " + e.getMessage());
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
