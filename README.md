Task Reminder App
Overview
Task Reminder is an Android application that helps you manage your tasks and get timely reminders. The app allows you to create, edit, and delete tasks, set reminders with specific dates and times, and mark tasks as completed when you're done.
Features

Create new tasks with titles, descriptions, dates, and times
Edit existing tasks
Delete tasks when no longer needed
Mark tasks as completed
Receive notifications at the scheduled date and time
Store task data locally on your device

Architecture
The application follows the MVVM (Model-View-ViewModel) architecture pattern and uses several Android Architecture Components:

Room: For local database storage of tasks
LiveData: For observable data and UI updates
ViewModel: For managing UI-related data in a lifecycle-conscious way
Repository: For abstract data access layer

Project Structure
com.example/
├── data/
│   ├── Task.java                # Entity class for tasks
│   ├── TaskDao.java             # Data Access Object interface
│   └── TaskDatebase.java        # Room database configuration
├── notification/
│   ├── NotificationHelper.java  # Creates and displays notifications
│   ├── TaskAlarmReceiver.java   # BroadcastReceiver for alarms
│   └── TaskScheduler.java       # Schedules task reminders
├── repository/
│   └── TaskRepository.java      # Repository for data operations
├── ui/
│   ├── Add_Task.java            # Activity for adding/editing tasks
│   ├── HomeTask.java            # Main activity showing task list
│   └── TaskAdapter.java         # RecyclerView adapter for tasks
└── viewmodal/
    └── TaskViewModal.java       # ViewModel for tasks
How to Use the Application
First Launch

When you first launch the app, you'll see an empty list of tasks
You'll be prompted to grant notification permissions (required for reminders)
You'll also need to grant permission to schedule exact alarms

Adding a New Task

Tap the floating "+" button in the bottom right corner
Fill in the task details:

Title (required)
Description (optional)
Date (defaults to current date)
Time (defaults to current time)


Tap "Save" to create the task
A notification will be scheduled for the specified date and time

Editing a Task

Tap on any task in the list
Modify any of the fields (title, description, date, time)
Tap "Save" to update the task
The notification will be rescheduled with the new date/time

Completing a Task

Check the checkbox next to a task to mark it as completed
When marked as completed, the scheduled notification will be canceled

Deleting a Task

Tap the trash icon on a task to delete it
The task will be removed from the list and its notification will be canceled

Receiving Notifications

At the scheduled date and time, you'll receive a notification
The notification will display the task title and description
Tapping the notification will open the app

Technical Implementation Details
Local Storage

Tasks are stored locally using Room database
Each task contains: ID, title, description, date, time, and completion status

Notification System

The app uses AlarmManager to schedule notifications
TaskScheduler handles scheduling and canceling alarms
NotificationHelper creates and displays the notifications
TaskAlarmReceiver receives alarm broadcasts and triggers notifications

Requirements

Android 6.0 (Marshmallow) or higher
Exact alarm permission (for Android 12+)
Notification permission (for Android 13+)

Note
This application stores all data locally on your device. No data is sent to external servers or shared with third parties.