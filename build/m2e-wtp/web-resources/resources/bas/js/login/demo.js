/**
 * Particleground demo
 * @author Jonathan Nicol - @mrjnicol
 */

$(document).ready(function() {
  $('#particles').particleground({
    dotColor: '#eceeef',
    lineColor: '#f2f2f2'
  });
  $('.intro').css({
    'margin-top': -($('.intro').height() / 2)
  });
});