package com.biprangshu.attendo.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.biprangshu.attendo.data.Subject
import com.biprangshu.attendo.repository.DatabaseRepository
import com.biprangshu.attendo.repository.UserPreferencesRepository
import com.biprangshu.attendo.utils.requiredPercentage
import com.biprangshu.attendo.utils.showFirstOpenAlert
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: DatabaseRepository,
    private val userPreferencesRepository: UserPreferencesRepository,
    application: Application
): AndroidViewModel(application) {

    init {
        showFirstDialog()
        updateRequiredPercentage()
    }

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

    fun markClassPresent(subject: Subject){
        viewModelScope.launch {
            val updatedSubject = subject.copy(
                classAttended = subject.classAttended + 1,
                totalClasses = subject.totalClasses + 1
            )
            repository.updateSubject(updatedSubject)
        }
    }

    fun markClassAbsent(subject: Subject){
        viewModelScope.launch {
            val updatedSubject = subject.copy(
                totalClasses = subject.totalClasses + 1
            )
            repository.updateSubject(updatedSubject)
        }
    }

    fun showFirstDialog(){
        viewModelScope.launch {
            showFirstOpenAlert = if(userPreferencesRepository.isFirstAppOpen.first()){
                true
            }else{
                false
            }
        }
    }

    fun updateRequiredPercentage(percentage: Float){
        viewModelScope.launch {
            userPreferencesRepository.changeRequiredPercentage(percentage)
        }
    }

    fun updateFirstAppOpen(appOpen: Boolean){
        viewModelScope.launch {
            userPreferencesRepository.firstAppOpen(appOpen)
        }
    }

    fun updateRequiredPercentage(){
        viewModelScope.launch {
            requiredPercentage= userPreferencesRepository.requiredPercentage.first()
        }
    }

}