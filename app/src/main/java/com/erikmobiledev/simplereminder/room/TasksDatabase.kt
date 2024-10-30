package com.erikmobiledev.simplereminder.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.erikmobiledev.simplereminder.model.Tasks

@Database(entities = [Tasks::class], version = 1, exportSchema = false)
abstract class TasksDatabase: RoomDatabase() {
    abstract fun tasksDao(): TasksDatabaseDao
}