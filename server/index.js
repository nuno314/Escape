var app = require('express');
var server = require('http').Server(app);
var io = require('socket.io')(server);

server.listen(8080, () => {
    console.log("Server is now running");
})

io.on('connection', (socket) => {
    console.log("Player Connected");
    socket.emit('socketID', { id: socket.id });
    socket.broadcast.emit('newPlayer', { id: socket.id });
    socket.on('disconnect', () => {
        console.log("Player Disconnected");
    })
})