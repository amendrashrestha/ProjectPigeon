<%-- 
    Document   : connect
    Created on : Oct 8, 2014, 2:56:44 PM
    Author     : amendrashrestha
--%>

<%@page import="java.sql.Connection"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ page import = "java.sql.*"%>
<html>
<head>
<title>Obtaining a Connection </title>
</head>
<body>
<%
Connection conn=null;
//ResultSet result=null;
//Statement stmt=null;
//ResultSetMetaData rsmd=null;
try {
  Class c=Class.forName("com.mysql.jdbc.Driver");
  conn=DriverManager.getConnection("jdbc:mysql://127.0.0.1:8889/twitter_stream",
   "root","root");
  out.write("Connected!");
}
catch(SQLException e) {
  System.out.println("Error!!!!!!" + e);
}
%>
</body>
</html>
