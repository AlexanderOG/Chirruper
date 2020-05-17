/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.QueueBrowser;
import javax.jms.Session;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ActiveMQQueueBrowser;
import org.apache.activemq.command.ActiveMQObjectMessage;

/**
 *
 * @author Alex
 */
public class S_LeeChirrups extends HttpServlet {

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
            MessageConsumer messageConsumer = null;
            Session session = null;
            javax.jms.Connection connection = null;
            try
            {
                HttpSession mySession = request.getSession();
                String usuario = mySession.getAttribute("username").toString();
                String resp = "<table border='1'><thead><tr><th>Chirrupero</th><th>Fecha y hora</th><th>Chirrup</th></tr></thead><tbody>";
                ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_BROKER_URL);
                connectionFactory.setTrustAllPackages(true);
                connection = connectionFactory.createConnection();
                connection.start();
                session = connection.createSession(false /*Transacter*/, Session.AUTO_ACKNOWLEDGE);
                Destination dondeLee = session.createQueue(usuario);
                messageConsumer = session.createConsumer(dondeLee);
                ObjectMessage recibido = null;
                Boolean hayMensajes = true;
                Boolean primera = true;
                resp += "<tr><td colspan='3'> Actualmente no hay Chirrups que mostrar.</td></tr>";
                int i = 0;
                while(hayMensajes)
                {
                    recibido = (ActiveMQObjectMessage) messageConsumer.receive(1000);
                    System.out.println("Recibido "+i +" :"+ recibido);
                    i++;
                    if (recibido != null) 
                    {
                        if(primera)
                        {
                            resp = "<table border='1'><thead><tr><th>Chirrupero</th><th>Fecha y hora</th><th>Chirrup</th></tr></thead><tbody>";
                            primera = false;
                        }
                        Chirrup miChirrup = (Chirrup) recibido.getObject();
                        resp += "<tr>";
                        resp += "<td>" + miChirrup.getEmisor() + "</td>";
                        resp += "<td>" + miChirrup.getFechaYHora()+ "</td>";
                        resp += "<td>" + miChirrup.getChirrup()+ "</td>";
                        resp += "</tr>";
                    }
                    else
                    {
                        hayMensajes = false;
                    }
                }
                resp += "</tbody></table>";
                out.print(resp);
                System.out.println("Cerrando cliente de cola.");
            }catch(Exception e)
            {
                out.print("ERROR: " + e.getMessage());
                System.out.println(e.getMessage());
            }
            finally
            {
                try 
                {
                    messageConsumer.close();
                    session.close();
                    connection.close();
                } catch (JMSException ex) {
                    Logger.getLogger(S_LeeChirrups.class.getName()).log(Level.SEVERE, null, ex);
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
