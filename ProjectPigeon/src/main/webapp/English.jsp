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
        <script type="text/javascript" src="utilities/json2.js"></script>
        <script type="text/javascript" src="utilities/highcharts.js"></script>
    </head>
    <body>
        <div>
            <nav>
                <ul>
                    <li><a href="index.jsp">Home</a></li>
                    <li><a href="English.jsp">English</a></li>
                    <li><a href="Swedish.jsp">Swedish</a></li>
                </ul>
            </nav>
        </div>
        <div>
            <table>
                <tr>
                    <td>
                        <div id = "txtForm">
                            <form action="rest/generic/returnStylometricJSON" method="post">
                                <textarea name="posts" ROWS="15" COLS="100" onclick="this.value = '';">This is a little test.</textarea>
                                <input type="submit" value="Submit">                   
                            </form>
                        </div>
                    </td>
                    <td>
                        <form method="POST" action="rest/generic/returnStylometricJSONForUser">
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
                                        String query = "select distinct User_id from twitter_data_view order by 1 limit 1000";
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
                                    window.location.replace("English.jsp?user=" + dropdownIndex);
                                }
                            </script>
                            <input type="submit" value="Submit" id="submit">
                        </form>
                    </td>
                </tr>
            </table>

            <%-- String userID = request.getParameter("user");
                out.println("value=" + userID);
            --%>
        </div>

        <div id = "styloContainer" style= "height: 400px">
            <script type="text/javascript" src="utilities/styloChart.js"></script>
        </div>
        <div id = "timeContainer" style= "height: 400px">
            <script type="text/javascript" src="utilities/timeChart.js"></script>
        </div>
    </body>
</html>
