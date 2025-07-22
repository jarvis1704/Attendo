package com.biprangshu.attendo.di

import android.content.Context
import androidx.room.Room
import com.biprangshu.attendo.data.AttendoDatabase
import com.biprangshu.attendo.data.SubjectDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule{


    @Provides
    @Singleton
    fun provideAttendoDatabase(@ApplicationContext context: Context): AttendoDatabase{
        return Room.databaseBuilder(
            context,
            AttendoDatabase::class.java,
            "attendo_database"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideSubjectDao(database: AttendoDatabase): SubjectDao{
        return database.subjectDao()
    }
}
