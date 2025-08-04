package com.biprangshu.attendo.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.time.LocalDate

enum class AttendenceStatus{
    PRESENT, ABSENT
}

@Entity(
    tableName = "attendence_records",
    foreignKeys = [
        ForeignKey(
            entity = Subject::class,
            parentColumns = ["subjectCode"],
            childColumns = ["subjectCode"],
            onDelete = ForeignKey.CASCADE //delete records if subject is delete
        )
    ]
)
data class AttendanceRecord(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val subjectCode: String,
    val date: LocalDate,
    val status: AttendenceStatus
)