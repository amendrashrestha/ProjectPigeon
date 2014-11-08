<%-- 
    Document   : Swedish
    Created on : Oct 3, 2014, 4:28:07 PM
    Author     : amendrashrestha
--%>

<%@page import="com.stylometry.model.User"%>
<%@page import="java.util.List"%>
<%@page import="com.stylometry.IOHandler.IOReadWrite"%>
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
        <!--<script type="text/javascript" src="utilities/highcharts.js"></script>-->
        <script src="http://code.highcharts.com/stock/highstock.js"></script>
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
            <h2>Compare User</h2>

            <form method="POST" action="rest/generic/returnStylometricJSONForTwoUser">
                <table>
                    <tr>
                        <td>
                            Select User1 :
                            <select id="UserID1" name="user1">
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
                            Select User2 :
                            <select id="UserID2" name="user2">
                                <%
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
                </table>
                <input type="submit" value="Compare" id="submit">
            </form>
        </div>
                            
         <div id = "styloContainer">
            <script type="text/javascript" src="utilities/showTab.js"></script>
            <ul id="tabs">
                <li><a id="tab1">Function Words</a></li>
                <li><a id="tab2">Word Length</a></li>
                <li><a id="tab3">Alphabet</a></li>
                <li><a id="tab4" >Digit & Characters</a></li>
            </ul>

            <div class="container1" id="tab1C">Function Words Graph
                <script type="text/javascript" src="utilities/FunWordForMultipleUserChart.js"></script>
                <script>
                                            $('#tab1').click(function () {
                                                funWordMultipleUserGraph();
                                            });
                </script>

            </div>
            <div class="container1" id="tab2C">Characters Graph
                <script type="text/javascript" src="utilities/WordCountStyloMultipleUserChart.js"></script>
                <script>
                                            $('#tab2').click(function () {
                                                wordLengthMultipleUserGraph();
                                            });
                </script>           
            </div>
            <div class="container1" id="tab3C">Alphabet Graph</div>
            <script type="text/javascript" src="utilities/AlphabetStyloMultipleUserChart.js"></script>
            <script>
                                        $('#tab3').click(function () {
                                            AlphabetMultipleUserGraph();
                                        });
            </script>  
            <div class="container1" id="tab4C">Digit and Characters Graph</div>
            <script type="text/javascript" src="utilities/DigitCharStyloMultipleUserChart.js"></script>
            <script>
                                        $('#tab4').click(function () {
                                            DigitCharMultipleUserGraph();
                                        });
            </script> 
        </div>                   
        <div id = "timeContainer">
            <script type="text/javascript" src="utilities/showTimeTab.js"></script>
            <ul id="timetabs">
                <li><a id="timetab1">Hour of Day</a></li>
                <li><a id="timetab2">Period of Day</a></li>
                <li><a id="timetab3">Week of Day</a></li>
                <li><a id="timetab4">Type of Week</a></li>
            </ul>

            <div class="container2" id="timetab1C">24 hour of day
                <script type="text/javascript" src="utilities/HourofDayForMultipleUserChart.js"></script>
                <script>
                                            $('#timetab1').click(function () {
                                                hourofDayMultipleUserGraph();
                                            });
                </script>

            </div>
            <div class="container2" id="timetab2C">A single day is divided into 6 period with 4 hour interval.
                <script type="text/javascript" src="utilities/PeriodofDayForMultipleUserChart.js"></script>
                <script>
                                            $('#timetab2').click(function () {
                                                periodofDayMultipleUserGraph();
                                            });
                </script>           
            </div>
            <div class="container2" id="timetab3C">7 days of a week.</div>
            <script type="text/javascript" src="utilities/WeekofDayMultipleUserChart.js"></script>
            <script>
                                        $('#timetab3').click(function () {
                                            weekofDayMultipleUserGraph();
                                        });
            </script>  
            <div class="container2" id="timetab4C">Week Days and Week End</div>
            <script type="text/javascript" src="utilities/TypeofWeekMultipleUserChart.js"></script>
            <script>
                                        $('#timetab4').click(function () {
                                            typeofWeekMultipleUserGraph();
                                        });
            </script> 
        </div>
    </body>
</html>
