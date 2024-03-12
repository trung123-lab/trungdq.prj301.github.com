/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trungdq.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Map;
import java.util.Properties;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import trungdq.cart.CartObject;
import trungdq.orderDetail.OrderDetailDAO;
import trungdq.tOrder.TOrderDAO;
import trungdq.tOrder.TOrderDTO;
import trungdq.tbl_Product.Tbl_ProductDAO;
import trungdq.tbl_Product.Tbl_ProductDTO;

/**
 *
 * @author trung
 */
@WebServlet(name = "CheckOutServlet", urlPatterns = {"/CheckOutServlet"})
public class CheckOutServlet extends HttpServlet {

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
        //0. get context scope & get siteMaps attribute
        ServletContext context = this.getServletContext();
        Properties siteMaps = (Properties) context.getAttribute("SITEMAPS");
        String url = "errors.html";
        try {
            //1. cust goes to the cart place 
            HttpSession session = request.getSession();
            //2. cust takes his/her cart
            CartObject cart = (CartObject) session.getAttribute("CART");
            //Check to see if the cart already has items
            if (cart != null) {
                if (!cart.getItems().isEmpty()) {
                    TOrderDAO dAO = new TOrderDAO();
                    String tOrderid = dAO.generateOxxx();
                    String username = request.getParameter("txtUsername");
                    float grandTotal = Float.parseFloat(request.getParameter("grandTotal"));
                    boolean result = dAO.insertOrder(tOrderid, username, grandTotal);
                    if (result) {
                        for (Map.Entry<String, Tbl_ProductDTO> entry : cart.getItems().entrySet()) {
                            String productId = entry.getKey();
                            String name = entry.getValue().getName();
                            int quantity = entry.getValue().getQuantity();
                            float price = entry.getValue().getPrice();
                            float total = quantity * price;
                            OrderDetailDAO detailDAO = new OrderDetailDAO();
                            boolean result1 = detailDAO.insertOrderDetail(name, price, quantity, total, productId, tOrderid); 
                            Tbl_ProductDAO productDAO = new Tbl_ProductDAO();
                            boolean result2 = productDAO.decreaseQuantity(productId, quantity);
                        } 
                        url = "index.jsp";
                    }
                }
                cart.clear();
            }

        } catch (SQLException ex) {
            log("CheckOutServlet_SQL: " + ex.getMessage());
        } catch (NamingException ex) {
            log("CheckOutServlet_Naming: " + ex.getMessage());
        } finally {
            RequestDispatcher rd = request.getRequestDispatcher(url);
            rd.forward(request, response);
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
