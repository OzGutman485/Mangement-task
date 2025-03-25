const taskController = require('../controllers/task')
const express = require('express')
var router = express.Router()

router.route('/')
    .get(taskController.getAllTasks)
    .post(taskController.createTask)
router.route('/:id')
    .delete(taskController.deleteTask)
    .put(taskController.putTask)
    .get(taskController.getTask)
module.exports = router