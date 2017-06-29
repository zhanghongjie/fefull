$(document).ready(function(){

    //响应式导航
    $('#menu').mmenu({dragOpen: true});
	
    //回到头部
	var $goTop = $('#goTop');
    $goTop.click(function() {
        $("html, body").animate({
            scrollTop: 0
        },
        300);
    });

    $backToTopFun = function() {
        var st = $(document).scrollTop(),
        winh = $(window).height(); (st > 100) ? $('#goTop').fadeIn(300) : $('#goTop').fadeOut(300);
    };
    $(window).bind("scroll", $backToTopFun);
    $backToTopFun();

})