const taskServices = require('../services/task')
const task = require('../model/task')

const createTask = async (req, res) => {
    try {
        const { name, description, dueDate, priority, tags } = req.body;
        const result = await taskServices.createTask({
            name,
            description,
            dueDate,
            priority,
            tags
        });
        if (!result.success) {
            return res.status(400).json({ error: result.error });
        }
        return res.status(201)
            .location(`/tasks${result.data._id}`)
            .json(result.data)
    }
    catch (error) {
        console.error('Create task error:', error);
        return res.status(500).json({ error: 'Internal server error' });
    }
}
const getTask = async (req, res) => {
    try {
        const result = await taskServices.getTask(req.params.id)
        if (!result.success) {
            return res.status(404).json({ error: 'Task not found' });
        }
        return res.status(200)
            .json(result.data)
    }
    catch (error) {
        return res.status(404).json({ error: result.error })
    }
}
const getAllTasks = async (req, res) => {
    try {
        const page = parseInt(req.query.page) || 1;
        const limit = parseInt(req.query.limit) || 10;

        const result = await taskServices.getAllTasks(page, limit);

        if (!result.success) {
            return res.status(400).json({ error: result.error });
        }

        return res.status(200).json(result);
    }
    catch (error) {
        console.error('Get all tasks error:', error);
        return res.status(500).json({ error: 'Internal server error' });
    }
}
const putTask = async (req, res) => {
    try {
        const result = await taskServices.putTask(req.params, req.body)
        if (!result.success) {
            return res.status(404).json({ error: result.error });
        }
        return res.status(204).send()
    }
    catch (error) {
        console.error('Delete task error:', error);
        return res.status(500).json({ error: 'Internal server error' });
    }
}
const deleteTask = async (req, res) => {
    try {
        const result = await taskServices.deleteTask(req.params.id)
        if (!result.success) {
            return res.status(404).json({ error: result.error });
        }

        return res.status(204).send();
    }
    catch (error) {
        console.error('Delete task error:', error);
        return res.status(500).json({ error: 'Internal server error' });
    }
}
const completeTask = async (req, res) => {
    try {
        const result = await taskServices.completeTask(req.params.id);

        if (!result.success) {
            return res.status(404).json({ error: result.error });
        }

        return res.status(200).json(result.data);
    }
    catch (error) {
        console.error('Complete task error:', error);
        return res.status(500).json({ error: 'Internal server error' });
    }
}
const searchTasks = async (req, res) => {
    try {
        const { query } = req.query;
        const result = await taskServices.searchTasks(query);

        return res.status(200).json(result.data);
    }
    catch (error) {
        console.error('Search tasks error:', error);
        return res.status(500).json({ error: 'Internal server error' });
    }
};

module.exports = (
    getAllTasks, getTask,
    createTask, deleteTask,
    putTask, searchTasks,
    completeTask)