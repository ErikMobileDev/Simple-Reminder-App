package com.erikmobiledev.simplereminder.di

import android.content.Context
import androidx.room.Room
import com.erikmobiledev.simplereminder.room.TasksDatabase
import com.erikmobiledev.simplereminder.room.TasksDatabaseDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun providesTaskDao(cronosDatabase: TasksDatabase) : TasksDatabaseDao {
        return cronosDatabase.tasksDao()
    }

    @Singleton
    @Provides
    fun providesTasksDatabase(@ApplicationContext context: Context): TasksDatabase {
        return Room.databaseBuilder(
            context,
            TasksDatabase::class.java, "tasks_db"
        ).fallbackToDestructiveMigration().build()
    }
}