const app = require('express');
const server = require('http').Server(app);
const io = require('socket.io')(server);
const sequelize = require('socket.io-sequelize');

io.use(sequelize('db', 'user', 'pass', { host: 'localhost' }, 'app/models'));

const rooms = []
const players = [];
let ID = 0;
const PORT = process.env.PORT || 8080
server.listen(PORT, () => {
    console.log("Server is now running");
    console.log(PORT)
})

remove_player = (id) => {
    rooms.forEach(room => {
        if (room.p1ID == id) {
            room.p1ID = ''
            room.p1Name = ''
        }
        if (room.p2ID == id) {
            room.p2ID = ''
            room.p2Name = ''
        }
        if (room.p1ID == '' && room.p2ID == '')
            rooms.splice(room);
    })
}


io.on('connection', (socket) => {
    console.log("Player Connected");
    socket.emit('socketID', { id: socket.id });
    socket.broadcast.emit('newPlayer', { id: socket.id });
    socket.broadcast.emit('room_list', rooms);
    socket.on('disconnect', () => {

        console.log("Player Disconnected");
        socket.broadcast.emit("playerDisconnected", {id: socket.id});
        remove_player(socket.id)
        console.log('Room left: ', rooms)
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
    socket.on('find_room', () => {
        socket.emit('room_list', rooms)
        console.log('FIND_ROOM, ROOM_LIST');
    })
    socket.on('p2_join', (player2) => {
        console.log('P2 JOIN', player2)
        rooms.forEach((room) => {
            if (room.roomID == player2.roomID) {
                console.log('FIND ROOM')
                room.p2ID = player2.id
                room.p2Name = player2.name
                console.log(player2.name, 'join. Emitting to ', room.p1Name, 'at ', room.p1ID)
                socket.to(room.p1ID).emit('p2_join', room)
                return
            }
        })
    })
    socket.on('get_p1', (player2) => {
        console.log('get_p1', player2.roomID)
        rooms.forEach(room => {
            if (room.roomID == player2.roomID) {
                console.log(player2.p2ID, 'join. Emitting to ', room.p2ID)
                socket.to(room.p2ID).emit('p1_join', room)
                io.to(room.p2ID).emit('p1_join', room)
                return;
            }
        })
    })
    socket.on('leave_room', (player) => {
        console.log(player, "leaves room");
        remove_player(player)
        console.log('Room left: ', rooms)
    })
    socket.on('start_game', roomID => {
       rooms.forEach(room=>{
           if (room.roomID = roomID) 
            io.to(room.p2ID).emit("start_game")
       })
    })
    socket.on('level_pass', lvl => {

    })
})

//player = (id, x, y) => {
//    this.id = id;
//    this.x = x;
//    this.y = y;
//}