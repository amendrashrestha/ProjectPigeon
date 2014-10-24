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
        xAxis:{
            min: 1,
            tickInterval: 10,
            tickmarkPlacement: 'on'
        },
        
        series: [{}],
        colors: [
            '#3D96AE',
            '#89A54E',
            '#A47D7C',
            '#B5CA92',
            '#4572A7',
            '#AA4643',
            '#92A8CD',
            '#80699B',
            '#DB843D'
        ]
    };
    $.getJSON("utilities/stylo.json", function (data) {
        var result = [];
        var j = 1;
        for (var i in data)
            result.push([i, data[i]]);
        options.series[0].data = result;

        var chart = new Highcharts.Chart(options);
        chart.setTitle({text: 'Stylometry Analysis'});
        chart.series[0].update({name: "User " + j}, false);
        chart.redraw();
        j++;
    });
});
