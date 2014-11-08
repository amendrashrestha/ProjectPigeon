<!doctype html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <title>Daily Sales Summary</title>
        <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.8.2/jquery.min.js"></script>
        <!--<script type="text/javascript" src="utilities/highcharts.js"></script>-->
        <script src="http://code.highcharts.com/stock/highstock.js"></script>
        <script src="http://code.highcharts.com/modules/exporting.js"></script>

        <script>

            $(function () {
                $("#tabs").tabs();
            });


            $(function () {
                $('#container').highcharts({
                    chart: {
                        zoomType: 'xy'
                    },
                    title: {
                        text: 'Average Monthly Temperature and Rainfall in Tokyo'
                    },
                    subtitle: {
                        text: 'Source: WorldClimate.com'
                    },
                    xAxis: [{
                            categories: ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun',
                                'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec']
                        }],
                    yAxis: [{// Primary yAxis
                            labels: {
                                format: '{value}°C',
                                style: {
                                    color: '#89A54E'
                                }
                            },
                            title: {
                                text: 'Temperature',
                                style: {
                                    color: '#89A54E'
                                }
                            }
                        }, {// Secondary yAxis
                            title: {
                                text: 'Rainfall',
                                style: {
                                    color: '#4572A7'
                                }
                            },
                            labels: {
                                format: '{value} mm',
                                style: {
                                    color: '#4572A7'
                                }
                            },
                            opposite: true
                        }],
                    tooltip: {
                        shared: true
                    },
                    legend: {
                        layout: 'vertical',
                        align: 'left',
                        x: 120,
                        verticalAlign: 'top',
                        y: 100,
                        floating: true,
                        backgroundColor: '#FFFFFF'
                    },
                    series: [{
                            name: 'Rainfall',
                            color: '#4572A7',
                            type: 'column',
                            yAxis: 1,
                            data: [49.9, 71.5, 106.4, 129.2, 144.0, 176.0, 135.6, 148.5, 216.4, 194.1, 95.6, 54.4],
                            tooltip: {
                                valueSuffix: ' mm'
                            }

                        }, {
                            name: 'Temperature',
                            color: '#89A54E',
                            type: 'spline',
                            data: [7.0, 6.9, 9.5, 14.5, 18.2, 21.5, 25.2, 26.5, 23.3, 18.3, 13.9, 9.6],
                            tooltip: {
                                valueSuffix: '°C'
                            }
                        }]
                });
            });
        </script>
    </head>
    <body>

        <div id="tabs">
            <ul>
                <li><a href="#tabs-1">Sales By Country View</a></li>
                <li><a href="#tabs-2">Dealer Cost by Car Maker View</a></li>
                <li><a href="#tabs-3">HighChart Graph</a></li>

            </ul>
            <div id="tabs-1">

            </div>
            <div id="tabs-2">
            </div>

            <div id="tabs-3">
                <div id ="container" style="min-width: 310px; height: 400px; margin: 0 auto">
                </div>

            </div>
        </div>

    </body>
</html>