package com.biprangshu.attendo.navigation

object NavScreenObject {
     const val HOMESCREEN = "homescreen"
    const val SETTINGSSCREEN = "settingscreen"

    const val CALENDAR_SCREEN = "calendarscreen/{subjectCode}/{subjectName}"

    fun createCalendarRoute(subjectCode: String, subjectName: String): String {
        return "calendarscreen/$subjectCode/$subjectName"
    }
}