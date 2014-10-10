<%-- 
    Document   : Swedish
    Created on : Oct 3, 2014, 4:28:07 PM
    Author     : amendrashrestha
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Swedish Alias Sensor</title>
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
                                <textarea name="posts" ROWS="15" COLS="100" onclick="this.value = '';">Den här lite pröva.</textarea>
                                <input type="submit" value="Submit" id="submit">
                            </form>
                        </div>
                    </td>
                </tr>
            </table>
        </div>

        <div id = "container" style= "height: 400px">
            <script type="text/javascript" src="utilities/styloChart.js"></script>
        </div>
    </body>
</html>
