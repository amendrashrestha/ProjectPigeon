<html>

    <script src="//code.jquery.com/jquery-1.10.2.js"></script>
    <!--<link rel="stylesheet" type="text/css" href="utilities/reset.css">-->
    <script src="http://code.highcharts.com/highcharts.js"></script>
    <script src="http://code.highcharts.com/modules/exporting.js"></script>

    <fieldset>
        <legend>Billing Information</legend>
        <ul>

            <li>
                <label for="firstname">First Name</label>
                <input type="text" name="textfield" id="firstname" />
            </li>

            <li>
                <label for="lastname">Last Name</label>
                <input type="text" name="textfield2" id="lastname" />
            </li>

            <li>
                <label for="address">Address</label>
                <textarea name="textarea" id="address"></textarea>
            </li>

        </ul>
    </fieldset>

    <input type="submit" value="click" id="click" onclick=showGraph()>
    <script>
        function showGraph() {
            $("#xxx").append("<div id='" + 1 + "'></div>");
            $("#xxx").highcharts({
                title: {
                    text: 'Monthly Average Temperature',
                    x: -20 //center
                },
                subtitle: {
                    text: 'Source: WorldClimate.com',
                    x: -20
                },
                xAxis: {
                    categories: ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun',
                        'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec']
                },
                yAxis: {
                    title: {
                        text: 'Temperature (°C)'
                    },
                    plotLines: [{
                            value: 0,
                            width: 1,
                            color: '#808080'
                        }]
                },
                tooltip: {
                    valueSuffix: '°C'
                },
                legend: {
                    layout: 'vertical',
                    align: 'right',
                    verticalAlign: 'middle',
                    borderWidth: 0
                },
                series: [{
                        name: 'Tokyo',
                        data: [7.0, 6.9, 9.5, 14.5, 18.2, 21.5, 25.2, 26.5, 23.3, 18.3, 13.9, 9.6]
                    }, {
                        name: 'New York',
                        data: [-0.2, 0.8, 5.7, 11.3, 17.0, 22.0, 24.8, 24.1, 20.1, 14.1, 8.6, 2.5]
                    }, {
                        name: 'Berlin',
                        data: [-0.9, 0.6, 3.5, 8.4, 13.5, 17.0, 18.6, 17.9, 14.3, 9.0, 3.9, 1.0]
                    }, {
                        name: 'London',
                        data: [3.9, 4.2, 5.7, 8.5, 11.9, 15.2, 17.0, 16.6, 14.2, 10.3, 6.6, 4.8]
                    }]
            });
        }
//            alert($("#xxx").html());

    </script>

    <div id="xxx"> </div>
</html>