/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Login extends HttpServlet {

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
       /* try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
         /*   out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet Login</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet Login at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }*/
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
         String user;
         String contra;
        
      //recuperamos los datos del formulario
          user = request.getParameter("username");
          contra = request.getParameter("pass");
          
      HttpSession sesion = request.getSession();
      sesion.setAttribute("claveSesion", user);
      sesion.setAttribute("claveSesion2", contra);

       response.setContentType("text/html");
        String titulo = null;
      conexion con = null;

      String claveSesion = (String) sesion.getAttribute("claveSesion");
      String claveSesion2 = (String) sesion.getAttribute("claveSesion2");
      try {
            //Conexion a la base de datos, utiliza la clase ConectaDB
            con = new conexion();
            con.conecta();
            ResultSet res = con.query("select * from usuarioss where Userd='" + claveSesion + "' and contra='" + claveSesion2 + "';");

            if (res.next()) {
                titulo = "Validado";
            }else{
                titulo = "No validado";
            }

            con.cierra();
            
        } catch (SQLException ex) {
            
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
      //Mostramos los  valores en el cliente
      PrintWriter out = response.getWriter();
      
        out.println("<h1>¿Continua la Sesion y es valida?: </h1>" + titulo);
        out.println("<br>");
        out.println("<h3>ID de la sesi&oacute;n JSESSIONID: </h3>" + sesion.getId());
             
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
