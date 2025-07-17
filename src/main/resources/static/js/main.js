$(document).ready(function(){
    $('.tooltips-general').tooltip('hide');
    $('.mobile-menu-button').on('click', function(){
        var mobileMenu=$('.navbar-lateral');	
        if(mobileMenu.css('display')=='none'){
            mobileMenu.fadeIn(300);
        }else{
            mobileMenu.fadeOut(300);
        }
    });
    $('.desktop-menu-button').on('click', function(e){
        e.preventDefault();
        var NavLateral=$('.navbar-lateral'); 
        var ContentPage=$('.content-page-container');   
        if(NavLateral.hasClass('desktopMenu')){
            NavLateral.removeClass('desktopMenu');
            ContentPage.removeClass('desktopMenu');
        }else{
            NavLateral.addClass('desktopMenu');
            ContentPage.addClass('desktopMenu');
        }
    });
    $('.dropdown-menu-button').on('click', function(e){
        e.preventDefault();
        var icon=$(this).children('.icon-sub-menu');
        if(icon.hasClass('zmdi-chevron-down')){
            icon.removeClass('zmdi-chevron-down').addClass('zmdi-chevron-up');
            $(this).addClass('dropdown-menu-button-active');
        }else{
            icon.removeClass('zmdi-chevron-up').addClass('zmdi-chevron-down');
            $(this).removeClass('dropdown-menu-button-active');
        }
        
        var dropMenu=$(this).next('ul');
        dropMenu.slideToggle('slow');
    });
    $('.exit-system-button').on('click', function(e){
        e.preventDefault();
        var LinkExitSystem=$(this).attr("data-href");
        swal({
            title: "¿Estás seguro?",
            text: "Quieres salir del sistema y cerrar la sesión actual",
            type: "warning",
            showCancelButton: true,
            confirmButtonColor: "#5cb85c",
            confirmButtonText: "Si, salir",
            cancelButtonText: "No, cancelar",
            animation: "slide-from-top",
            closeOnConfirm: false 
        },function(){
            window.location=LinkExitSystem; 
        });  
    });

   //------------------------SWEATALERT PARA LA BUSQUEDA-----------------------//

   /*
    $('.search-book-button').click(function(e){
     e.preventDefault();

     swal({
       title: "¿Qué cliente estás buscando?",
       text: "Por favor escribe el nombre del cliente",
       type: "input",
       showCancelButton: true,
       closeOnConfirm: false,
       animation: "slide-from-top",
       cancelButtonText: "Cancelar",
       confirmButtonText: "Buscar",
       confirmButtonColor: "#3598D9",
       inputPlaceholder: "Escribe aquí el nombre de cliente"
     }, function(inputValue){
       if (inputValue === false) return false;

       if (inputValue === "") {
         swal.showInputError("Debes escribir el nombre del cliente");
         return false;
       }

       swal.close();

       let filtro = inputValue.toLowerCase();
       let filas = document.querySelectorAll('#tabla-clientes tbody tr');

       let encontrados = 0;
       filas.forEach(fila => {
         let nombre = fila.cells[6].textContent.toLowerCase(); // Cliente en columna 7
         if (nombre.includes(filtro)) {
           fila.style.display = '';
           encontrados++;
         } else {
           fila.style.display = 'none';
         }
       });

       // Mostrar el botón para restaurar la tabla
       document.getElementById('mostrarTodosBtn').style.display = 'inline-block';

       if (encontrados === 0) {
         swal("Sin resultados", "No se encontraron clientes con ese nombre", "warning");
       }
     });
   });*/
    $('.btn-help').on('click', function(){
        $('#ModalHelp').modal({
            show: true,
            backdrop: "static"
        });
    });
});
 document.querySelectorAll('.toggle-container input[type="checkbox"]').forEach((checkbox, index) => {
        checkbox.addEventListener('change', function() {
            document.getElementById('field' + (index + 1)).style.display = this.checked ? 'block' : 'none';
        });
    });
(function($){
    $(window).load(function(){
        $(".nav-lateral-scroll").mCustomScrollbar({
            theme:"light-thin",
            scrollbarPosition: "inside",
            autoHideScrollbar: true,
            scrollButtons:{ enable: true }
        });
        $(".custom-scroll-containers").mCustomScrollbar({
            theme:"dark-thin",
            scrollbarPosition: "inside",
            autoHideScrollbar: true,
            scrollButtons:{ enable: true }
        });
    });
})(jQuery);