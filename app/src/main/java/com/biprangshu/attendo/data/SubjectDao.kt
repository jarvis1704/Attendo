package com.biprangshu.attendo.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface SubjectDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSubject(subject: Subject)

    //delete subject
    @Delete
    suspend fun deleteSubject(subject: Subject)

    //update subject
    @Update
    suspend fun updateSubject(subject: Subject)

    //get all subject
    @Query("SELECT * FROM subjects ORDER BY subjectName ASC")
    fun getAllSubject(): Flow<List<Subject>>

    //get subject by code
    @Query("SELECT * FROM subjects WHERE subjectCode = :subjectCode")
    fun getSubjectByCode(subjectCode: String): Flow<Subject?>


}