<%-- 
    Document   : Swedish
    Created on : Oct 3, 2014, 4:28:07 PM
    Author     : amendrashrestha
--%>

<%@page import="java.sql.SQLException"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.ResultSetMetaData"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Connection"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>English Alias Sensor</title>
        <link rel="stylesheet" type="text/css" href="utilities/reset.css">
        <link rel="stylesheet" type="text/css" href="utilities/body.css">
        <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.8.2/jquery.min.js"></script>
        <script type="text/javascript" src="utilities/highcharts.js"></script>
        <script src="http://code.highcharts.com/modules/exporting.js"></script>
    </head>
    <body>
        <div id='menu'>
            <nav>
                <ul>
                    <li><a href="index.jsp">Home</a></li>
                    <li><a href="English.jsp">English</a>
                        <ul>
                            <li><a href="WriteText.jsp">Write Text</a></li>
                            <li><a href="SelectUser.jsp">Select User</a></li>
                            <li><a href="CompareUser.jsp">Compare Users</a></li>
                            <li><a href="SplitUser.jsp">Split User</a></li>
                        </ul>
                    </li>
                    <li><a href="Swedish.jsp">Svenska</a>
                        <ul>
                            <li><a href="#">Skriv Text</a></li>
                            <li><a href="#">Välja User</a></li>
                            <li><a href="#">Jämfor Users</a></li>
                            <li><a href="#">Sträck User</a></li>
                        </ul>
                    </li>
                </ul>
            </nav>
        </div>

        <div id= "textBox">
            <table>
                <tr>
                    <td>
                        <div id = "txtForm">
                            <form action="rest/generic/returnStylometricJSON" method="post">
                                <table>
                                    <tr>
                                        <td><textarea name="posts" ROWS="15" COLS="100" onclick="this.value = '';">This is a little test.</textarea>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>
                                            <input type="submit" value="Submit" id = "submit">  
                                        </td>
                                    </tr>

                                </table>
                            </form>
                        </div>
                    </td>                
                </tr>
            </table>
        </div>

        <div id = "styloContainer" style= "height: 400px">
            <script type="text/javascript" src="utilities/styloChart.js"></script>
        </div>
    </body>
</html>
