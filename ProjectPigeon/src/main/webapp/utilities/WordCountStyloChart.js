/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
function wordLengthGraph(){
    var options = {
        chart: {
            renderTo: 'tab2C',
            type: 'spline'
        },
        yAxis: {
            min: 0
        },
        xAxis: {
            categories: ['1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12', '13', '14',
             '15', '16', '17', '18', '19', '20'],
            min: 0,
            tickInterval: 1,
            tickmarkPlacement: 'on'
        },
       credits: {
            enabled: false
        },
        series: []
    };

    var drawChart = function (data, name, color) {

        var newSeriesData = {
            name: name,
            data: data,
            color: color
        };

        options.series.push(newSeriesData);

        var chart = new Highcharts.Chart(options);
        chart.setTitle({text: 'Word Length Analysis'});
        chart.yAxis[0].setTitle({text: "Percentage of Post"});
        chart.xAxis[0].setTitle({text: "Feature Vector"});
    };

    $.getJSON("utilities/stylo1.json", function (data) {
        var result = [];
        var color = 'green';

        for (var i in data)
            result.push([data[i]]);

        var user = "User " + result[0];
        var result1 = result.splice(294, 20);
        drawChart(result1, user, color);
    });
}
