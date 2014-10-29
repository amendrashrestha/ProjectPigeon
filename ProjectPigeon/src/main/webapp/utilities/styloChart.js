/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
$(document).ready(function () {

    var options = {
        chart: {
            renderTo: 'styloContainer',
            type: 'spline'
        },
        yAxis: {
            min: 0
        },
        xAxis: {
            min: 0,
            tickInterval: 10,
            tickmarkPlacement: 'on'
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
        chart.setTitle({text: 'Stylometry Analysis'});
        chart.yAxis[0].setTitle({text: "Percentage of Post"});
        chart.xAxis[0].setTitle({text: "Types of features"});
    };

    $.getJSON("utilities/stylo1.json", function (data) {
        var result = [];
        var color = 'green';

        for (var i in data)
            result.push([data[i]]);

        var user = "User " + result[0];
        var result1 = result.splice(1, 361);
        drawChart(result1, user, color);
    });
});
