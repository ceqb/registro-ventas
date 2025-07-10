// Asegúrate de que jQuery esté cargado antes de este script
    $(document).ready(function() { // Usa jQuery para asegurar que el DOM esté listo
        const modal = $('#modalEstado'); // Selecciona el modal con jQuery

        if (modal.length === 0) { // Verifica si el modal existe
            console.error("DEBUG JS: Modal #modalEstado no encontrado.");
            return;
        }

        // Escucha el evento 'show.bs.modal' de Bootstrap 3 usando jQuery
        modal.on('show.bs.modal', function (event) {
            const button = $(event.relatedTarget); // El botón que disparó el modal (jQuery object)
            const id = button.data('id_ventas'); // Usa .data() para leer data-atributos con jQuery
            const estado = button.data('estado');

            console.log("DEBUG JS: --- Inicio Depuración Modal ---");
            console.log("DEBUG JS: Botón que abrió el modal:", button[0]); // Muestra el elemento DOM
            console.log("DEBUG JS: ID obtenido del botón (data-id_ventas):", id);
            console.log("DEBUG JS: Estado obtenido del botón (data-estado):", estado);

            const inputIdVenta = modal.find('#inputIdVenta'); // Encuentra el input dentro del modal
            const selectEstado = modal.find('#selectEstado'); // Encuentra el select dentro del modal

            if (inputIdVenta.length > 0) {
                inputIdVenta.val(id); // Asigna el ID al campo oculto con .val()
                console.log("DEBUG JS: Valor asignado a inputIdVenta (campo oculto):", inputIdVenta.val());
            } else {
                console.error("DEBUG JS: Elemento #inputIdVenta no encontrado en el modal.");
            }

            if (selectEstado.length > 0) {
                const normalizedEstado = estado ? estado.replace(/\s/g, '_').toUpperCase() : '';
                selectEstado.val(normalizedEstado); // Asigna el estado con .val()
                console.log("DEBUG JS: Estado actual seleccionado en el dropdown:", selectEstado.val());
            } else {
                console.error("DEBUG JS: Elemento #selectEstado no encontrado en el modal.");
            }
            console.log("DEBUG JS: --- Fin Depuración Modal ---");
        });
    });