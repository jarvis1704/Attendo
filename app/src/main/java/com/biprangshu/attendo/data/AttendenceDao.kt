package com.biprangshu.attendo.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface AttendenceDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecord(record: AttendanceRecord)

    @Query("SELECT * FROM attendence_records WHERE subjectCode =:subjectCode")
    suspend fun getRecordsForSubject(subjectCode: String): Flow<List<AttendanceRecord>>
}