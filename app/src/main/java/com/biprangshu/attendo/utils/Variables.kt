package com.biprangshu.attendo.utils

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.biprangshu.attendo.data.Subject

var selectedScreen by mutableStateOf("")
var showSubjectAddModal by mutableStateOf(false)
var showSubjectDetailModal by mutableStateOf(false)
var showFirstOpenAlert by mutableStateOf(false)
var requiredPercentage by mutableStateOf(0f)

var selectedSubject by mutableStateOf<Subject?>(null)
var selectedSubjectForEdit by mutableStateOf<Subject?>(null)

var showEditSubjectModal by mutableStateOf(false)

fun showSubjectDetail(subject: Subject) {
    selectedSubject = subject
    showSubjectDetailModal = true
}

fun editSubjectDetail(subject: Subject){
    selectedSubjectForEdit= subject
    showEditSubjectModal=true
}
