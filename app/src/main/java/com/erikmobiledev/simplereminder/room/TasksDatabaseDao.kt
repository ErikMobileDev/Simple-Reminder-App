package com.erikmobiledev.simplereminder.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.erikmobiledev.simplereminder.model.Tasks
import kotlinx.coroutines.flow.Flow

@Dao
interface TasksDatabaseDao {

    @Query("SELECT * FROM tasks")
    fun getTasks(): Flow<List<Tasks>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(task: Tasks)

    @Delete
    suspend fun delete(task: Tasks)
}