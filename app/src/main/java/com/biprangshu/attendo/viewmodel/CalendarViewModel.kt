package com.biprangshu.attendo.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.biprangshu.attendo.data.DatedAttendance
import com.biprangshu.attendo.repository.AttendanceFileManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CalendarViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val attendanceFileManager: AttendanceFileManager
) : ViewModel() {

    val subjectCode: String = savedStateHandle.get<String>("subjectCode")!!
    val subjectName: String = savedStateHandle.get<String>("subjectName")!!

    private val _attendanceRecords = MutableStateFlow<List<DatedAttendance>>(emptyList())
    val attendanceRecords = _attendanceRecords.asStateFlow()

    init {
        loadAttendanceRecords()
    }

    private fun loadAttendanceRecords() {
        viewModelScope.launch {
            _attendanceRecords.value = attendanceFileManager.getAttendanceForSubject(subjectCode)
        }
    }
}