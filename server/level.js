// --level  score  name  // rank
// row1
// row2
// row3
// row4

// postgreSQL ===> heroku in stock
// mySQL ==> remotedatabase

// sequelize

// rank {
    // level,
    // score,
    // name    
//}
const {rank} = require('./rank')
// define rank ===> sequelize

exports.createRank = (rank) => {
    return rank.create(rank);
}

exports.getRankLevel = (level) => {
    return rank.findAll({
        where: {
            level: level,
        },
        limit: 10,
        order: [
            [score, DESC]
        ],
        raw: true
    })
    
}