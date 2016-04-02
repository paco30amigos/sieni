/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

             
             
function handleMessage(facesmessage) {
    
}




function notificar(count) {
    var burbuja = document.getElementById("burbuja");
    if(burbuja.textContent < count){
        document.getElementById('sound-notify').play();
        burbuja.textContent = count;
        msgnotify.severity = 'info';
        PF('growl-notify').show([msgnotify]);
        burbuja.classList.add('agrandar');
        removeAnimation();
    }    
}

function removeAnimation() {
    setTimeout(function () {
        $('.burbuja-notify').removeClass('agrandar')
    }, 500);
}

            