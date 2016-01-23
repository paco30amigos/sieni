/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



                    
function handleMessage(facesmessage) {
    facesmessage.severity = 'info';

    PF('growl-notify').show([facesmessage]);
}

function agrandar(count) {
    document.getElementById('sound-notify').play();
    var anterior = $('.burbuja').data();
    if(anterior < count){
        $('.burbuja').data(count);
        $('.burbuja').html().addClass('agrandar');
        removeAnimation();
    }
    
}

function removeAnimation() {
    setTimeout(function () {
        $('.burbuja').removeClass('agrandar')
    }, 500);
}