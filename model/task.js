const mongoose = require('mongoose')
const taskScheme = new mongoose.Schema({
    name: {
        type: String,
        required: true
    },
    description: {
        type: String,
        required: true
    },
    dueDate: {
        type: Date,
        required: true
    },
    priority: {
        type: String,
        enum: ['Low', 'Medium', 'High'],
        default: 'Medium'
    },
    status: {
        type: String,
        enum: ['Pending', 'In Progress', 'Completed'],
        default: 'Pending'
    },
    reminderTime: {
        type: Date
    },
    createdAt: {
        type: Date,
        default: Date.now
    },
    tags: [{
        type: String
    }]
}, {
    timestamps: true 
})
const Task = mongoose.model('Task', taskScheme)
module.exports = Task