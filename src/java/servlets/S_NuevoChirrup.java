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
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.time.format.DateTimeFormatter;  
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

/**
 *
 * @author Alex
 */
public class S_NuevoChirrup extends HttpServlet {

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
            String chirrup = request.getParameter("chirrup");
            HttpSession mySession = request.getSession();
            String usuario = mySession.getAttribute("username").toString();
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");  
            LocalDateTime now = LocalDateTime.now();  
            String fechaYHora = dtf.format(now);  
            Connection con = null;
            Session session = null;
            javax.jms.Connection connection = null;
            try
            {
                Class.forName("org.apache.derby.jdbc.ClientDriver");                
                con = DriverManager.getConnection("jdbc:derby://localhost:1527/ChirruperDB/","root","root");
                Statement query = con.createStatement();
                ResultSet rs = query.executeQuery("SELECT USUARIO FROM SEGUIDORES WHERE SIGUE_A='"+usuario+"'");
                String resp = "Lo sentimos, no tienes Chirruperos seguidores y por lo tanto nadie vera ese Chirrup :(";
                
                ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_BROKER_URL);
                connectionFactory.setTrustAllPackages(true);
                connection = connectionFactory.createConnection();
                connection.start();
                session = connection.createSession(false /*Transacter*/, Session.AUTO_ACKNOWLEDGE);
                
                while(rs.next())
                {
                    resp = "Chirrup enviado.";
                    String escribe = rs.getString("USUARIO");
                    Destination dondeEscribe = session.createQueue(escribe);
                    MessageProducer messageProducer = session.createProducer(dondeEscribe);
                    Chirrup miChirrup = new Chirrup(usuario, fechaYHora, chirrup);
                    ObjectMessage aEnviar = session.createObjectMessage();
                    aEnviar.setObject(miChirrup);
                    messageProducer.send(aEnviar);
                    messageProducer.close();
                }
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
                    session.close();
                    connection.close();
                    con.commit();
                    con.close();
                } catch (JMSException ex) {
                    Logger.getLogger(S_NuevoChirrup.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SQLException ex) {
                    Logger.getLogger(S_NuevoChirrup.class.getName()).log(Level.SEVERE, null, ex);
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
