var app = require('express');
var server = require('http').Server(app);
var io = require('socket.io')(server);
var rooms = []
var players = [];
let ID = 0;
server.listen(8080, () => {
    console.log("Server is now running");
})

io.on('connection', (socket) => {
    console.log("Player Connected");
    socket.emit('socketID', { id: socket.id });
//    socket.emit('getPlayers', players);
    socket.broadcast.emit('newPlayer', { id: socket.id });
    socket.on('disconnect', () => {

        console.log("Player Disconnected");
        socket.broadcast.emit("playerDisconnected", {id: socket.id});
        rooms.forEach((room) => {
            if (room.p1ID == socket.id) {
                room.p1ID = ''
                room.p1Name = ''
            }
            if (room.p2ID == socket.id) {
                room.p2ID = ''
                room.p2Name = ''
            }
            if (room.p1ID == '' && room.p2ID == '')
                rooms.splice(room);
            console.log('Room left: ', rooms)
        })
    });
    socket.on('create_room', (player) => {
        console.log(player)
        let newRoom = {
            roomID: ID,
            p1ID: player.ID,
            p1Name: player.name,
            p2ID: '',
            p2Name: ''
        }
        ID++;
        rooms.push(newRoom);
        console.log(rooms);
    })
  //  players.push(new player(socket.id, 0, 0));
})

//player = (id, x, y) => {
//    this.id = id;
//    this.x = x;
//    this.y = y;
//}