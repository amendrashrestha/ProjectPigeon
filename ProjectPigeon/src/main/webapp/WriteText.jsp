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
        <!--<script type="text/javascript" src="utilities/highcharts.js"></script>-->
        <script src="http://code.highcharts.com/stock/highstock.js"></script>
        <script src="http://code.highcharts.com/modules/exporting.js"></script>
    </head>
    <body>
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

        <div id= "textBox">
            <h2>Write Text</h2>
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

        <div id = "styloContainer">
            <script type="text/javascript" src="utilities/showTab.js"></script>
            <ul id="tabs">
                <li><a id="tab1">Function Words</a></li>
                <li><a id="tab2">Word Length</a></li>
                <li><a id="tab3">Alphabet</a></li>
                <li><a id="tab4" >Digit & Characters</a></li>
            </ul>

            <div class="container1" id="tab1C">Function Words Graph
                <script type="text/javascript" src="utilities/FunWordStyloChart.js"></script>
                <script>
                                            $('#tab1').click(function () {
                                                functionWordGraph();
                                            });
                </script>

            </div>
            <div class="container1" id="tab2C">Characters Graph
                <script type="text/javascript" src="utilities/WordCountStyloChart.js"></script>
                <script>
                                            $('#tab2').click(function () {
                                                wordLengthGraph();
                                            });
                </script>           
            </div>
            <div class="container1" id="tab3C">Alphabet Graph</div>
            <script type="text/javascript" src="utilities/AlphabetStyloChart.js"></script>
            <script>
                                        $('#tab3').click(function () {
                                            AlphabetGraph();
                                        });
            </script>  
            <div class="container1" id="tab4C">Digit and Characters Graph</div>
            <script type="text/javascript" src="utilities/DigitCharStyloChart.js"></script>
            <script>
                                        $('#tab4').click(function () {
                                            DigitCharGraph();
                                        });
            </script> 
        </div>
    </body>
</html>
