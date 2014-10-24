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
        <div id='selectUser'>
            <h2>Split User</h2>
            <form method="POST" action="rest/generic/returnStylometricJSONForUser">
                <table>
                    <tr>
                        <td>
                            Select User :
                            <select id="UserID" name="user" onChange="GetSelectedUser()">
                                <%
                                    Connection conn = null;
                                    ResultSet result = null;
                                    Statement stmt = null;

                                    try {
                                        Class c = Class.forName("com.mysql.jdbc.Driver");
                                        conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:8889/twitter_stream",
                                                "root", "root");
                                        out.write("Connected!");
                                        String query = "select distinct User_id from twitter_data_view order by 1 limit 100";
                                        stmt = conn.createStatement();
                                        result = stmt.executeQuery(query);

                                        while (result.next()) {
                                            String userID = result.getString("User_id");
                                %>
                                <option value="<%=userID%>"><%=userID%></option>
                                <%
                                        }
                                    } catch (Exception e) {
                                        System.out.println("Error!!!!!!" + e);
                                    }
                                %>
                            </select>  
                            <script language = "javascript">
                                function GetSelectedUser() {
                                    var dropdownIndex = document.getElementById('UserID').value;
                                    window.location.replace("SelectUser.jsp?user=" + dropdownIndex);
                                }
                            </script> 
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <input type="submit" value="Split" id="submit">
                        </td>
                    </tr>

                </table>
            </form>
        </div>

        <div id = "styloContainer" style= "height: 400px">
            <script type="text/javascript" src="utilities/styloChart.js"></script>
        </div>
        <div id = "timeContainer" style= "height: 400px">
            <script type="text/javascript" src="utilities/timeChart.js"></script>
        </div>
    </body>
</html>
