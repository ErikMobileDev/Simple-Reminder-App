package com.erikmobiledev.simplereminder.repository

import com.erikmobiledev.simplereminder.model.Tasks
import com.erikmobiledev.simplereminder.room.TasksDatabaseDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class TasksRepository @Inject constructor(private val tasksDatabaseDao: TasksDatabaseDao) {
    suspend fun addTask(task: Tasks) = tasksDatabaseDao.insert(task)
    suspend fun deleteTask(task: Tasks) = tasksDatabaseDao.delete(task)
    fun getAllTasks(): Flow<List<Tasks>> = tasksDatabaseDao.getTasks().flowOn(Dispatchers.IO).conflate()
}