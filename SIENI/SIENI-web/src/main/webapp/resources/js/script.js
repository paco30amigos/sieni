/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


$(document).ready(function(){

    // obtenemos el valor actual de la burbuja
    var valor = parseInt($('.burbuja').html());
    var $burbuja = $('.burbuja');

    // creamos un manejador de eventos único para todos los botones
    // al presionar algún botón del div "botones"
    $('#noticia').on('click',function(event){

        // almacenamos el valor que tenía la burbuja antes del click
        var valorPrevio = valor;

        // obtenemos el nombre del botón presionado
        var boton = $(event.target).attr('id');

        if (boton == 'botonNoticia') {
            valor++;    
        } else{

            // no permitimos decrementar si ya está el valor en 0
            if (valor > 0) {
                if (boton == 'decrementar') {
                    valor--;
                } else{
                    valor = 0;
                };
            }
        };

        // si hubo un cambio en el valor
        if (valor != valorPrevio) {
            agrandar($burbuja);         
        } 
    });

    // función que pasado un tiempo, quita la clase "agrandar" del elemento
    function removeAnimation(){
        setTimeout(function() {
            $burbuja.removeClass('agrandar')
        }, 1000);
    }

    // función que modifica el valor de la burbuja y la agranda
    function agrandar ($elemento) {
        $elemento.html(valor).addClass('agrandar');
        removeAnimation();
    }
    
    
     function sendNotify(){

        // almacenamos el valor que tenía la burbuja antes del click
        var valorPrevio = valor;
        agrandar($burbuja);   
    }
    
    
function handleMessageNotify(facesmessage) {
            facesmessage.severity = 'info';
 
            PF('growl').show([facesmessage]);
        }
});
