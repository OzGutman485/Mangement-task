const taskServices = require('../services/task')
const task = require('../model/task')

const createTask = async (req, res) => {
    try {
        const result = await taskServices.createTask(
            req.body.name,
            req.body.description
        )
        return res.status(201)
            .location(`/tasks${result.data._id}`)
            .json(result.data)
    }
    catch (error) {
        return res.status(400).json({ error: result.error })
    }
}
const getTask = async (req, res) => {
    try {
        const result = await taskServices.getTask(req.params.id)
        return res.status(200)
            .json(result.data)
    }
    catch (error) {
        return res.status(404).json({ error: result.error })
    }
}
const getAlltasks = async (req, res) => {
    try {
        const result = await taskServices.getAlltasks()
        return res.status(200)
            .json(result.data)
    }
    catch (error) {
        return res.status(400).json({ error: result.error })
    }
}
const putTask = async (req, res) => {
    try {
        const result = await taskServices.putTask(req.params.id, req.body)
        return res.status(204).send()
    }
    catch (error) {
        return res.status(404).json({ error: result.error })
    }
}
const deleteTask = async (req, res) => {
    try {
        const result = await taskServices.deleteTask(req.params.id)
        return res.status(204).send()
    }
    catch (error) {
        return res.status(404).json({ error: result.error })
    }
}

module.exports = (getAlltasks, getTask, createTask, deleteTask, putTask)