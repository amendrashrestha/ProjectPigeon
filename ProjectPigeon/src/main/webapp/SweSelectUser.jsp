<%-- 
    Document   : Swedish
    Created on : Oct 3, 2014, 4:28:07 PM
    Author     : amendrashrestha
--%>

<%@page import="com.stylometry.model.User"%>
<%@page import="java.util.List"%>
<%@page import="com.stylometry.IOHandler.IOReadWrite"%>
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
                            <li><a href="SweWriteText.jsp">Skriv Text</a></li>
<!--                            <li><a href="SweSelectUser.jsp">Välja User</a></li>
                            <li><a href="SweCompareUser.jsp">Jämfor Users</a></li>
                            <li><a href="SweSplitUser.jsp">Sträck User</a></li>-->
                        </ul>
                    </li>
                </ul>
            </nav>
        </div>
        <div id='selectUser'>
            <h2>Select User</h2>
            <form method="POST" action="rest/generic/returnStylometricJSONForUser">
                <table>
                    <tr>
                        <td>
                            Select User :
                            <select id="userID" name="user">
                                <%
                                    IOReadWrite io = new IOReadWrite();
                                    List<User> userList = io.getAllUsersAsObject();
                                    userList = io.returnLimitedSortedUser(userList, userList.size());

                                    for (User user : userList) {
                                        int userID = user.getId();
                                %>
                                <option value="<%=userID%>"><%=userID%></option>
                                <% }
                                %>
                            </select>                             
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <input type="submit" value="Submit" id="submit">
                        </td>
                    </tr>
                </table>
            </form>
        </div>

        <div id = "styloContainer">
            <script type="text/javascript" src="utilities/styloChart.js"></script>
        </div>
        <div id = "timeContainer">
            <script type="text/javascript" src="utilities/timeChart.js"></script>
        </div>
    </body>
</html>
