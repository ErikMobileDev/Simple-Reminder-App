package com.erikmobiledev.simplereminder.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.erikmobiledev.simplereminder.model.Tasks
import com.erikmobiledev.simplereminder.repository.TasksRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
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

    fun addTask(task: Tasks) = viewModelScope.launch { repository.addTask(task) }
    fun deleteTask(task: Tasks) = viewModelScope.launch { repository.deleteTask(task) }
}