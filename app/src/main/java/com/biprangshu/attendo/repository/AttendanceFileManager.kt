package com.biprangshu.attendo.repository

import android.content.Context
import com.biprangshu.attendo.data.DatedAttendance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File

class AttendanceFileManager(private val context: Context) {

    private val json = Json { prettyPrint = true }

    private fun getFileForSubject(subjectCode: String): File {
        return File(context.filesDir, "${subjectCode}_attendance.json")
    }

    suspend fun getAttendanceForSubject(subjectCode: String): List<DatedAttendance> = withContext(Dispatchers.IO) {
        val file = getFileForSubject(subjectCode)
        if (!file.exists()) {
            return@withContext emptyList()
        }
        try {
            val fileText = file.readText()
            json.decodeFromString<List<DatedAttendance>>(fileText)
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    suspend fun addOrUpdateAttendanceRecord(subjectCode: String, record: DatedAttendance) = withContext(Dispatchers.IO) {
        val records = getAttendanceForSubject(subjectCode).toMutableList()
        //checking if record already exists
        val existingRecordIndex = records.indexOfFirst { it.date == record.date }

        if (existingRecordIndex != -1) {
            //updating existing record
            records[existingRecordIndex] = record
        } else {
            //adding new record
            records.add(record)
        }

        val file = getFileForSubject(subjectCode)
        val jsonString = json.encodeToString(records.sortedBy { it.date }) // Keep it sorted
        file.writeText(jsonString)
    }
}