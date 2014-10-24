/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
$(document).ready(function () {

    var options = {
        chart: {
            renderTo: 'timeContainer',
            type: 'spline'
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

        options.series.push(newSeriesData);

        var chart = new Highcharts.Chart(options);
        chart.setTitle({text: 'Time Analysis'});
        chart.yAxis[0].setTitle({text: "Percentage of Post"});
        chart.xAxis[0].setTitle({text: "Hour of Day"});

    };


    $.getJSON("utilities/timeSeries1.json", function (data) {
        var result = [];
        var user = "User1";
        var color = 'blue';

        for (var i in data)
            result.push([i, data[i]]);
        drawChart(result, user, color);
    });
});
