const task = require('../controllers/task');
const task = require('../model/task')

const createTask = async (taskData) => {
    try {
        if (!taskData.name || !taskData.description) {
            return { 
                success: false, 
                error: "Missing required fields" 
            };
        }
        const existingTask = await task.findOne({ name: taskData.name });
        if (existingTask) {
            return { 
                success: false, 
                error: "Task with this name already exists" 
            };
        }
        const newTask = new task({
            name: taskData.name,
            description: taskData.description,
            dueDate: taskData.dueDate,
            priority: taskData.priority || 'Medium',
            status: 'Pending',
            tags: taskData.tags || []
        });

        const savedTask = await newTask.save();
        return { 
            success: true, 
            data: savedTask 
        };
    }
    catch (error) {
        console.error('Create task error:', error);
        return { 
            success: false, 
            error: error.message 
        };
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
        const task = await task.findById(id);
        if (!task) {
            return { 
                success: false, 
                error: "Task not found" 
            };
        }
        return { 
            success: true, 
            data: task 
        };
    }
    catch (error) {
        console.error('Get task error:', error);
        return { 
            success: false, 
            error: error.message 
        };
    }
}
const putTask = async (id, updateData) => {
    try {
        const task = await task.findById(id);
        if (!task) {
            return { 
                success: false, 
                error: "Task not found" 
            };
        }
        const allowedUpdates = ['name', 'description', 'dueDate', 'priority', 'tags'];
        const updates = {};

        allowedUpdates.forEach(field => {
            if (updateData[field] !== undefined) {
                updates[field] = updateData[field];
            }
        });

        const updatedTask = await task.findByIdAndUpdate(
            id, 
            updates, 
            { new: true, runValidators: true }
        );

        return { 
            success: true, 
            data: updatedTask 
        };
    }
    catch (error) {
        console.error('Update task error:', error);
        return { 
            success: false, 
            error: error.message 
        };
    }
}
const getAllTasks = async (page = 1, limit = 10) => {
    try {
        const startIndex = (page - 1) * limit;

        const tasks = await task.find()
            .sort({ createdAt: -1 })
            .skip(startIndex)
            .limit(limit);

        const totalTasks = await task.countDocuments();

        return { 
            success: true, 
            data: tasks,
            total: totalTasks,
            page,
            totalPages: Math.ceil(totalTasks / limit)
        };
    }
    catch (error) {
        console.error('Get all tasks error:', error);
        return { 
            success: false, 
            error: error.message 
        };
    }
};
const completeTask = async (id) => {
    try {
        const task = await task.findById(id);
        if (!task) {
            return { 
                success: false, 
                error: "Task not found" 
            };
        }

        task.status = 'Completed';
        task.completedAt = new Date();

        const updatedTask = await task.save();
        return { 
            success: true, 
            data: updatedTask 
        };
    }
    catch (error) {
        console.error('Complete task error:', error);
        return { 
            success: false, 
            error: error.message 
        };
    }
};

const searchTasks = async (query) => {
    try {
        if (!query) {
            return { 
                success: false, 
                error: "Search query is required" 
            };
        }

        const tasks = await task.find({
            $or: [
                { name: { $regex: query, $options: 'i' } },
                { description: { $regex: query, $options: 'i' } },
                { tags: { $regex: query, $options: 'i' } }
            ]
        });

        return { 
            success: true, 
            data: tasks 
        };
    }
    catch (error) {
        console.error('Search tasks error:', error);
        return { 
            success: false, 
            error: error.message 
        };
    }
};
module.export = { createTask, getTask, deleteTask, putTask, getAllTasks ,searchTasks,completeTask}