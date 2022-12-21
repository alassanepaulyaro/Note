package com.example.todoappcompose.di

import android.content.Context
import androidx.room.Room
import com.example.todoappcompose.data.ToDoDataBase
import com.example.todoappcompose.utils.Constants.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Database Module
 */
@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext context: Context
    ) =  Room.databaseBuilder(
        context,
        ToDoDataBase::class.java,
        DATABASE_NAME
    ).build()

    @Singleton
    @Provides
    fun provideDao(dataBase: ToDoDataBase) = dataBase.toDoDao()
}