package com.biprangshu.attendo.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.biprangshu.attendo.data.Subject
import com.biprangshu.attendo.repository.DatabaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: DatabaseRepository,
    application: Application
): AndroidViewModel(application) {

    val allSubjects: Flow<List<Subject>> = repository.allSubjects

    fun insertSubject(subject: Subject) {
        viewModelScope.launch {
            repository.insert(subject)
        }
    }

    fun deleteSubject(subject: Subject) {
        viewModelScope.launch {
            repository.deleteSubject(subject)
        }
    }

    fun updateSubject(subject: Subject) {
        viewModelScope.launch {
            repository.updateSubject(subject)
        }
    }

    suspend fun getSubjectById(subjectCode: String): Flow<Subject?>? {
        return repository.getSubjectById(subjectCode)
    }

}