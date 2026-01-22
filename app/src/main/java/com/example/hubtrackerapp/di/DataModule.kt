package com.example.hubtrackerapp.di

import android.content.Context
import com.example.hubtrackerapp.data.HabitsRepositoryImpl
import com.example.hubtrackerapp.data.db.HabitsDatabase
import com.example.hubtrackerapp.data.db.dao.HabitDao
import com.example.hubtrackerapp.data.db.dao.HabitProgressDao
import com.example.hubtrackerapp.data.db.dao.UserDao
import com.example.hubtrackerapp.data.predefined.PredefinedHabitData
import com.example.hubtrackerapp.data.predefined.PredefinedHabitRepositoryImpl
import com.example.hubtrackerapp.domain.auth.AuthRepository
import com.example.hubtrackerapp.domain.hubbit.HabitRepository
import com.example.hubtrackerapp.domain.hubbit.models.PredefinedHabit
import com.example.hubtrackerapp.domain.predefined.GetAllPredefinedHabitsUseCase
import com.example.hubtrackerapp.domain.predefined.PredefinedHabitRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent:: class)
interface DataModule {

    @Singleton
    @Binds
    fun bindHabitRepository(
        impl: HabitsRepositoryImpl
    ): HabitRepository

    @Singleton
    @Binds
    fun bindAuthRepository(
        impl: HabitsRepositoryImpl
    ): AuthRepository

    @Binds
    @Singleton
    fun bindPredefinedHabitRepository(
        impl: PredefinedHabitRepositoryImpl
    ): PredefinedHabitRepository

    companion object{

        @Singleton
        @Provides
        fun provideDatabase(
           @ApplicationContext context: Context
        ): HabitsDatabase {
            return HabitsDatabase.getInstance(context)
        }

        @Singleton
        @Provides
        fun provideHabitDao(
            database: HabitsDatabase
        ): HabitDao {
            return database.habitDao()
        }
        @Singleton
        @Provides
        fun provideHabitProgressDao(
            database: HabitsDatabase
        ): HabitProgressDao {
            return database.habitProgressDao()
        }
        @Singleton
        @Provides
        fun provideUserDao(
            database: HabitsDatabase
        ): UserDao {
            return database.userDao()
        }

        @Singleton
        @Provides
        fun provideGetAllPredefinedHabitsUseCase(
            repository: PredefinedHabitRepository
        ): GetAllPredefinedHabitsUseCase {
            return GetAllPredefinedHabitsUseCase(repository)
        }

        @Singleton
        @Provides
        fun providePredefinedHabits(): List<PredefinedHabit> {
            return PredefinedHabitData.habits
        }
    }
}