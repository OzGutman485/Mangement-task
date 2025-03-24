const task = require('../controllers/task');
const task = require('../model/task')

const createTask = async (name, description, time) => {
    try {
        const tasks = await task.find({ name: name });
        if (tasks.length > 0) {
            return { success: false, error: "name movie already exist!" };
        }
        if (!name || !description || !time) {
            return { success: false, error: "Missing required fields" };
        }
        const task = new task({
            name: name,
            description: description,
            time: time
        });

        const savedtask = await task.save();
        if (!savedtask) {
            return { success: false, error: "Could not create task" };
        }
        return { success: true, data: savedMovie };
    }
    catch (error) {
        return { success: false, error: error.message };
    }
}
const deleteTask = async (id) => {
    try {
        const task = await task.findById(id)
        if (!task) {
            return { error: "task not found" }
        }
        await task.deleteOne()
        return { data: null }
    }
    catch (error) {
        return { data: error }
    }
}
const getTask = async (id) => {
    try {
        const task = await task.findById(id)
        return { data: task }
    }
    catch (error) {
        return { data: error }
    }
}
const putTask = async (id, taskData) => {
    try {
        const updateTask = await task.findByIdAndUpdate(
            id,
            {
                name: taskData.name,
                description: taskData.description,
                time: task.time
            }, { new: true, runValidators: true }
        )
        return { data: updateTask }
    }
    catch (error) {
        return { data: error }
    }
}
const getAlltasks = async () => {
    try {
        const result = await task.find({})
        return { date: result }
    }
    catch (error) {
        return { data: error }
    }
}
module.export = { createTask, getTask, deleteTask, putTask, getAlltasks }