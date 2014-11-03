<%-- 
    Document   : newTest
    Created on : Oct 24, 2014, 5:57:47 PM
    Author     : amendrashrestha
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title></title>
        <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.8.2/jquery.min.js"></script>
        <script type="text/javascript" src="utilities/highcharts.js"></script>
        <script type="text/javascript" src="utilities/multicolor_series.js"></script>
        <script src="http://code.highcharts.com/modules/exporting.js"></script>


    </head>

    <div id="container">
        <script>
            $(document).ready(function () {
                var result = new Array();
                $.getJSON("utilities/timeFVSeries1.json", function (data) {
                    for (var i in data)
                        result.push(data[i]);
                    var result1 = result.splice(1,39);

                    var colors = Highcharts.getOptions().colors;

                    function color(i) {
                        if (i < colors.length)
                            return colors[i];
                        else {
                            var idx = i % colors.length;
                            return colors[idx];
                        }
                    }

                    var chart = new Highcharts.Chart({
                        chart: {
                            renderTo: 'container',
                            type: 'coloredline'
                        },
                        yAxis: {
                            min: 0
                        },
                        xAxis: {
                            categories: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 
                17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 
            33, 34, 35, 36, 37, 38, 39],
                            tickInterval: 1,
                            tickmarkPlacement: 'on'
                        },
                        series: [{
                                data: genData()
                            }]
                    });

                    function genData() {
                        var d = [];
                        i = 0;
                        var result2 = result1;
                        while (i < result2.length) {
                            var v = Math.round((i + 1) / 4);
                            d.push({
                                y: result2[i],
                                segmentColor: color(v)
                            });
                            i++;
                        }
                        return d;
                    }
                });
            });
        </script>
    </div>
</html>
