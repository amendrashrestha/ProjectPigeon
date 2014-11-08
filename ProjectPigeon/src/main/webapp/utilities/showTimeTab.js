$(document).ready(function () {

    $('#timetabs li a:not(:first)').addClass('inactive');
    $('.container2').hide();
    $('.container2:first').show();

    $('#timetabs li a').click(function () {
        var t = $(this).attr('id');
        if ($(this).hasClass('inactive')) { //this is the start of our condition 
            $('#timetabs li a').addClass('inactive');
            $(this).removeClass('inactive');

            $('.container2').hide();
            $('#' + t + 'C').fadeIn('slow');
        }
    });
});