package com.example.todoapp.di

import android.app.Application
import androidx.room.Room
import com.example.todoapp.data.TodoDao
import com.example.todoapp.data.TodoDb
import com.example.todoapp.data.TodoRepo
import com.example.todoapp.data.TodoRepoImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideTodoDatabase(app:  Application): TodoDb{
        return Room.databaseBuilder(
            app,
            TodoDb::class.java,
            "todo_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideTodoRepo(db: TodoDb):TodoRepo{
        return TodoRepoImpl(db.dao)
    }
}