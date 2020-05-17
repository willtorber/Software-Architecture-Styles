// Cuando el documento esté listo
$(document).ready(function() {
    // Open modal
    $("#m_modal_4").modal('show');

    // Al hacer click en el botón de guardar se guardan el ip y el puerto
    $("#btn_set_domain").on("click", function() {
        establecerSocket(socket);
    });

    // Al hacer submit del formulario de login
    $("form").on("submit", function(e) {
        e.preventDefault();
        var conection = {
            "database":$(".repository").val(),
            "domain":$(".domain").val(),
            "port":$(".port_bd").val(),
            "name_database":$(".name_database").val(),
            "username":$(".username").val(),
            "passwd":$(".passwd").val()
        };
        var data= JSON.stringify(conection);
        $("#home").show();
        $("#index").hide();        
        conexion(data);
    });

    //al hacer click en el botón de logout emitimos el evento user logout
    $("#ejecutar").on("click", function() {
        var query = $('#sql').val();
        sendQuery(query);
    });

});

// Crea el objeto SocketIO y establece la configuración de eventos a escuchar
function establecerSocket() {
    var domain = $('#domain').val();
    var port = $('#port').val();
    if (domain === '' || port === '') {
        $("#msn").append('<div class="alert alert-danger ">' + 'Todos los valores se deben llenar' + '</div>');
    } else {
        $("#m_modal_4").modal("hide");
        // Establecemos el socket
        makeSocketIoObject(domain, port);
        configureObjectSocketIO();
    }
}