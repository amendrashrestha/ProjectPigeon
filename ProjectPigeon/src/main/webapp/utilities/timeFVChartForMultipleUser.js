/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
$(document).ready(function () {

    var options = {
        chart: {
            renderTo: 'timeFVContainer',
            type: 'spline'
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


    $.getJSON("utilities/timeFVSeries1.json", function (data) {
        var result = [];
        var color = '#0073F7';

        for (var i in data)
            result.push([data[i]]);
        
        var user = "User " + result[0];
        var result1 = result.splice(1, 39);
        
        drawChart(result1, user, color);
    });

    $.getJSON("utilities/timeFVSeries2.json", function (data) {
        var result = [];
        var color = "#1DAB0A";

        for (var i in data)
            result.push([data[i]]);
        
        var user = "User " + result[0];
        var result1 = result.splice(1, 39);
        drawChart(result1, user, color);
    });
});
