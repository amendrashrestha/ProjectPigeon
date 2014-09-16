<html>
    <head>
        <title>Stylometry Analysis</title>
        <link rel="stylesheet" type="text/css" href="utilities/body.css">
        <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.8.2/jquery.min.js"></script>
        <script type="text/javascript" src="utilities/json2.js"></script>
        <script type="text/javascript" src="utilities/highcharts.js"></script>
    </head>
    <body>
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
            </tr>
        </table>


        <div id = "container" style= "height: 400px">
            <script type="text/javascript">
                $(document).ready(function () {

                    var options = {
                        chart: {
                            renderTo: 'container',
                            type: 'spline'
                        },
                        series: [{}]
                    };

                    $.getJSON("utilities/data.json", function (data) {
                        var result = [];
                        for (var i in data)
                            result.push([i, data[i]]);

                        options.series[0].data = result;
                        var chart = new Highcharts.Chart(options);
                    });

                    // $.getJSON('utilities/data.json', function (data) {
                    //  options.series[0].data = data;
                    //  var chart = new Highcharts.Chart(options);
                    //  });
                });
            </script>
        </div>
    </body>
</html>