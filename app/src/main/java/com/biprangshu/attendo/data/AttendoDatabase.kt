package com.biprangshu.attendo.data

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [Subject::class], version = 1, exportSchema = false)
abstract class AttendoDatabase: RoomDatabase() {

    abstract fun subjectDao(): SubjectDao
}