$(document).ready(function() {    

$('#tabs li a:not(:first)').addClass('inactive');
$('.container1').hide();
$('.container1:first').show();
    
$('#tabs li a').click(function(){
    var t = $(this).attr('id');
  if($(this).hasClass('inactive')){ //this is the start of our condition 
    $('#tabs li a').addClass('inactive');           
    $(this).removeClass('inactive');
    
    $('.container1').hide();
    $('#'+ t + 'C').fadeIn('slow');
 }
});

});