<%-- 
    Document   : product
    Created on : Mar 3, 2024, 7:08:41 PM
    Author     : trung
--%>

<%--<%@page import="trungdq.tbl_Product.tbl_ProductDAO"%>
<%@page import="java.util.List"%>
<%@page import="trungdq.tbl_Product.tbl_ProductDTO"%>--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Product</title>
    </head>
    <body>
        <h1>Book Store</h1>
        <form action="DispatchServlet">
            <select name="cboBook">
                <c:forEach var="product" items="${requestScope.PRODUCT_LIST}">
                    <option value="${product.id}">${product.name}</option>
                </c:forEach>
            </select><br/>
            <input type="submit" value="Add Book to Your Cart" name="btAction" />
            <input type="submit" value="View Your Cart" name="btAction" />
        </form>
    </body>
    <%--<body>
        <h1>Book Store</h1>
        <% 
        tbl_ProductDAO productDAO = new tbl_ProductDAO();
        productDAO.addBook();
        List<tbl_ProductDTO> productList = productDAO.getProducts();
        %>
        <form name="ComboBook" action="DispatchServlet">
            <select name="cboBook">
        <%
             for (tbl_ProductDTO product : productList) { 
        %>    
                <option><%= product.getName() %></option>
            <% } %>
        </select><br/>
        <input type="submit" value="Add Book to Your Cart" name="btAction" />
        <input type="submit" value="View Your Cart" name="btAction" />
        </form>
    </body>--%>
</html>
