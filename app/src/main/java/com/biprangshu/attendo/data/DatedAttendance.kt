package com.biprangshu.attendo.data

import kotlinx.serialization.Serializable

enum class AttendanceStatus {
    PRESENT, ABSENT
}

@Serializable
data class DatedAttendance(
    val date: String,
    val status: AttendanceStatus
)