package com.biprangshu.attendo.data

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [Subject::class, AttendanceRecord::class], version = 2, exportSchema = false)
abstract class AttendoDatabase: RoomDatabase() {

    abstract fun subjectDao(): SubjectDao
    abstract fun attendenceDao(): AttendenceDao
}