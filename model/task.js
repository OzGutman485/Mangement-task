const mongoose = require('mongoose')
const taskScheme = new mongoose.Schema({
    name: {
        type: name,
        require: true
    },
    description: {
        type: String,
        require: true
    },
    time: {

    }
})
const Task = mongoose.model('Task', taskScheme)
module.exports = Task