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
        <script type="text/javascript" src="utilities/highcharts.js"></script>
         <script src="http://code.highcharts.com/modules/exporting.js"></script>
    </head>
    <body>
        <div id='menu'>
            <nav>
                <ul>
                    <li><a href="index.jsp">Hem</a></li>
                    <li><a href="English.jsp">Engelska</a>
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
        <div id= "textBox">
            <h2>Skriv Text</h2>
            <table>
                <tr>
                    <td>
                        <div id = "txtForm">
                            <form action="rest/generic/returnStylometricJSONSwe" method="post">
                                <table>
                                    <tr>
                                        <td><textarea name="posts" ROWS="15" COLS="100" onclick="this.value = '';">Skriv gärna en text.</textarea>
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
                <script type="text/javascript" src="utilities/SweFunWordStyloChart.js"></script>
                <script>
                                            $('#tab1').click(function () {
                                                sweFunctionWordGraph();
                                            });
                </script>

            </div>
            <div class="container1" id="tab2C">Characters Graph
                <script type="text/javascript" src="utilities/SweWordCountStyloChart.js"></script>
                <script>
                                            $('#tab2').click(function () {
                                                SweWordLengthGraph();
                                            });
                </script>           
            </div>
            <div class="container1" id="tab3C">Alphabet Graph</div>
            <script type="text/javascript" src="utilities/SweAlphabetStyloChart.js"></script>
            <script>
                                        $('#tab3').click(function () {
                                            SweAlphabetGraph();
                                        });
            </script>  
            <div class="container1" id="tab4C">Digit and Characters Graph</div>
            <script type="text/javascript" src="utilities/SweDigitCharStyloChart.js"></script>
            <script>
                                        $('#tab4').click(function () {
                                            SweDigitCharGraph();
                                        });
            </script> 
        </div>
    </body>
</html>
