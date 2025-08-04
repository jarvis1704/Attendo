package com.biprangshu.attendo.di

import android.content.Context
import androidx.room.Room
import androidx.room.driver.SupportSQLiteConnection
import androidx.room.migration.Migration
import androidx.sqlite.SQLiteConnection
import androidx.sqlite.db.SupportSQLiteDatabase
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

    //migration for database
    private val MIGRATION_1_2 = object : Migration(1,2){
        override fun migrate(db: SupportSQLiteDatabase) {
            super.migrate(db)
            //sql command to create the new attendance_records table
            db.execSQL("""
                CREATE TABLE IF NOT EXISTS `attendance_records` (
                    `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, 
                    `subjectCode` TEXT NOT NULL, 
                    `date` INTEGER NOT NULL, 
                    `status` TEXT NOT NULL, 
                    FOREIGN KEY(`subjectCode`) REFERENCES `subjects`(`subjectCode`) ON UPDATE NO ACTION ON DELETE CASCADE
                )
            """.trimIndent())
        }
    }


    @Provides
    @Singleton
    fun provideAttendoDatabase(@ApplicationContext context: Context): AttendoDatabase{
        return Room.databaseBuilder(
            context,
            AttendoDatabase::class.java,
            "attendo_database"
        )
            .addMigrations(MIGRATION_1_2)
            .build()
    }

    @Provides
    fun provideSubjectDao(database: AttendoDatabase): SubjectDao{
        return database.subjectDao()
    }
}
