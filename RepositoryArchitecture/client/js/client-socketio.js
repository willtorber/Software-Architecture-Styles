var socket = null;

function makeSocketIoObject(domain, port) {
    socket = io('http://' + domain + ':' + port);
}

function configureObjectSocketIO() {
    // Cuando el servidor emita el evento new message lo recibimos y lo mostramos
    socket.on('result_query', function(data) {        
        var tabla = '';
        if(data.result == -1){
            tabla += '<table class="table"><thead><tr><th>Columna</th><th>Valor</th></tr></thead><tbody>';
            data.data.forEach(function(info){
                Object.keys(info).forEach(function(col) {
                    if(col=="id"){
                        tabla += '<tr style="background: black">';    
                    }else{
                        tabla += '<tr>';
                    }                    
                    tabla += '<td>'+col+'</td>';
                    tabla += '<td>'+info[col]+'</td>';
                    tabla += '</tr>';
                });
            });
            tabla += '</tbody></table>';            
        }else{
            tabla = data.result+ ' filas fueron afectadas';            
        }
        console.log(tabla);
        $('#result').html(tabla);
    });
}

function conexion(data) {
    // Emitimos el evento add user
    socket.emit('start_database', data);
}

function sendQuery(query) {
    // Emitimos el evento user logout
    socket.emit('query', query);
}