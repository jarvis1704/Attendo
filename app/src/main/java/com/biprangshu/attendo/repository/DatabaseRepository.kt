package com.biprangshu.attendo.repository

import com.biprangshu.attendo.data.Subject
import com.biprangshu.attendo.data.SubjectDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DatabaseRepository @Inject constructor(
    private val subjectDao: SubjectDao
) {

    //get all subject
    val allSubjects: Flow<List<Subject>> = subjectDao.getAllSubject()

    //insert subject
    suspend fun insert(subject: Subject){
        subjectDao.insertSubject(subject)
    }


    //delete subject
    suspend fun deleteSubject(subject: Subject){
        subjectDao.deleteSubject(subject)
    }

    //update subject
    suspend fun updateSubject(subject: Subject){
        subjectDao.updateSubject(subject)
    }

    //get subject by Id
    suspend fun getSubjectById(subjectCode: String): Flow<Subject?>?{
        val subjectDetails = subjectDao.getSubjectByCode(subjectCode)

        return if(subjectDetails.first()?.subjectCode == subjectCode){
            subjectDetails
        }else{
            null
        }
    }


}