/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
function hourofDayMultipleUserGraph() {

    var options = {
        chart: {
            renderTo: 'timetab1C',
            type: 'spline'
        },
        credits: {
            enabled: false
        },
        yAxis: {
            min: 0
        },
        xAxis: {
            categories: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 
                17, 18, 19, 20, 21, 22, 23, 24],
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
        chart.setTitle({text: 'Hour of Day Analysis'});
        chart.yAxis[0].setTitle({text: "Percentage of Post"});
        chart.xAxis[0].setTitle({text: "Feature Vector"});

    };


    $.getJSON("utilities/timeFVSeries1.json", function (data) {
        var result = [];
        var color = '#0073F7';

        for (var i in data)
            result.push([data[i]]);
        
        var user = "User " + result[0];
        var result1 = result.splice(1, 24);
        
        drawChart(result1, user, color);
    });

    $.getJSON("utilities/timeFVSeries2.json", function (data) {
        var result = [];
        var color = "orange";

        for (var i in data)
            result.push([data[i]]);
        
        var user = "User " + result[0];
        var result1 = result.splice(1, 24);
        drawChart(result1, user, color);
    });
}
