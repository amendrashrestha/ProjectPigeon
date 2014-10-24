<%-- 
    Document   : JsonTest
    Created on : Oct 21, 2014, 5:05:06 PM
    Author     : amendrashrestha
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.8.2/jquery.min.js"></script>
        <script type="text/javascript" src="utilities/highcharts.js"></script>
        <script src="http://code.highcharts.com/modules/exporting.js"></script>

    </head>
    <body>
        <h1>Hello World!</h1>
        <div id="Container1" style="width:100%; height:400px;">
            <script>

                $("#loadChart").ready(function () {

                    var options = {
                        chart: {
                            renderTo: 'Container1',
                            type: 'spline'
                        },
                        rangeSelector: {
                            selected: 1
                        },
                        yAxis: {
                            min: 0
                        },
                        xAxis: {
                            categories: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24],
                            tickInterval: 1,
                            tickmarkPlacement: 'on'
                        },
                        series: []
                    };

                    var drawChart = function (data, name, color) {

                        var newSeriesData = {
                            name: name,
                            data: data,
                            color: color,
                            dashStyle: 'longdash'
                        };

                        // Add the new data to the series array
                        options.series.push(newSeriesData);

                        // If you want to remove old series data, you can do that here too

                        // Render the chart
                        var chart = new Highcharts.Chart(options);
                        chart.setTitle({text: 'Time Analysis'});
                        chart.yAxis[0].setTitle({text: "Percentage of Post"});
                        chart.xAxis[0].setTitle({text: "Hour of Day"});
                    };

                    $.getJSON("utilities/timeSeries.json", function (data) {
                        var result = [];
                        var user = "User1";
                        var j = 1;
                        for (var i in data)
                            result.push([i, data[i]]);

                        drawChart(result, user, '#80699B');
                    });

                    $.getJSON("utilities/timeSeries1.json", function (data) {
                        var result = [];
                        var user = "User2";
                        for (var i in data)
                            result.push([i, data[i]]);

                        drawChart(result, user, '#DB843D');
                    });
                });
            </script>
        </div>
    </body>
</html>
