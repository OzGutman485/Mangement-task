const express = require('express')
const mongoose = require('mongoose')
const taskRoutes = require('../mangement-task/routes/task')
const app = express()
app.use(express.json());  // Parse JSON bodies
app.use(express.urlencoded({ extended: true }));  // Parse URL-encoded bodies

// CORS middleware
app.use((req, res, next) => {
    res.header('Access-Control-Allow-Origin', '*');
    res.header('Access-Control-Allow-Headers', 'Origin, X-Requested-With, Content-Type, Accept, Authorization');
    res.header('Access-Control-Allow-Methods', 'GET, POST, PUT, DELETE, PATCH');
    if (req.method === 'OPTIONS') {
        return res.sendStatus(200);
    }
    next();
});

// Connect to MongoDB
mongoose.connect('mongodb://host.docker.internal:27017/netflix-server', {
    serverSelectionTimeoutMS: 5000
})
    .then(() => {
        //console.log('Connected to MongoDB');
    })
    .catch(err => {
        console.error('MongoDB connection error:', err);
        process.exit(1);
    });
app.use('/task', taskRoutes)
const port = process.argv[2] || process.env.PORT || 3000;
app.listen(port, '0.0.0.0')

process.on('uncaughtException', (err) => {
    console.error('Uncaught Exception:', err);
    process.exit(1);
});

// Unhandled rejection handler
process.on('unhandledRejection', (err) => {
    console.error('Unhandled Rejection:', err);
    process.exit(1);
});