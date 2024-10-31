package com.erikmobiledev.simplereminder.viewModels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.erikmobiledev.simplereminder.model.Tasks
import com.erikmobiledev.simplereminder.notifications.NotificationWorker
import com.erikmobiledev.simplereminder.repository.TasksRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class TasksViewModel @Inject constructor(private val repository: TasksRepository) : ViewModel() {

    private val _tasksList = MutableStateFlow<List<Tasks>>(emptyList())
    val tasksList = _tasksList.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getAllTasks().collect { item ->
                if (item.isNullOrEmpty()) {
                    _tasksList.value = emptyList()
                } else {
                    _tasksList.value = item
                }
            }
        }
    }

    fun addTask(task: Tasks, context: Context) = viewModelScope.launch {
        repository.addTask(task)

        // Formato de la fecha y hora de la tarea
        val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
        val taskDateTime = dateFormat.parse("${task.date} ${task.time}")

        taskDateTime?.let {
            val currentTime = System.currentTimeMillis()
            val timeDifference = taskDateTime.time - currentTime

            // Asegúrate de que el tiempo sea positivo
            if (timeDifference > 0) {
                NotificationWorker.releaseNotification(context, timeDifference)
            }
        }
    }


    fun deleteTask(task: Tasks) = viewModelScope.launch { repository.deleteTask(task) }
}