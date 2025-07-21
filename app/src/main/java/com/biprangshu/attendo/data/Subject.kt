package com.biprangshu.attendo.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "subjects")
data class Subject(
    @PrimaryKey
    val subjectCode: String,
    val subjectName: String,
    val classAttended: Int,
    val totalClasses: Int,
    val requiredPercentage: Float
){

    val currentPercentage: Float
        get() = if(totalClasses>0){
            (classAttended.toFloat()/totalClasses.toFloat()) * 100
        }else{
            0.0f
        }
}