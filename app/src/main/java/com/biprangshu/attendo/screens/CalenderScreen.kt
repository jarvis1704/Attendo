package com.biprangshu.attendo.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.biprangshu.attendo.data.AttendanceStatus
import com.biprangshu.attendo.data.DatedAttendance
import com.biprangshu.attendo.viewmodel.CalendarViewModel
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarScreen(
    onNavigateBack: () -> Unit,
    viewModel: CalendarViewModel = hiltViewModel()
) {
    val attendanceRecords by viewModel.attendanceRecords.collectAsState()

    val hapticFeedback = LocalHapticFeedback.current

    var currentMonth by remember { mutableStateOf(YearMonth.now()) }

    val recordsMap = remember(attendanceRecords) {
        attendanceRecords.associateBy { LocalDate.parse(it.date) }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = viewModel.subjectName, style = MaterialTheme.typography.headlineSmall, color= MaterialTheme.colorScheme.primary) },
                navigationIcon = {
                    IconButton(onClick = {
                        onNavigateBack()
                        hapticFeedback.performHapticFeedback(HapticFeedbackType.Confirm)
                    }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(8.dp))
            }

            item {
                MonthNavigationHeader(
                    currentMonth = currentMonth,
                    onPreviousMonth = {
                        currentMonth = currentMonth.minusMonths(1)
                        hapticFeedback.performHapticFeedback(HapticFeedbackType.Confirm)
                                      },
                    onNextMonth = {
                        currentMonth = currentMonth.plusMonths(1)
                        hapticFeedback.performHapticFeedback(HapticFeedbackType.Confirm)
                    }
                )
            }


            item {
                DaysOfWeekHeader()
            }


            item {
                CalendarGrid(
                    month = currentMonth,
                    recordsMap = recordsMap
                )
            }


            item {
                AttendanceLegend()
            }


            item {
                MonthlyAttendanceSummary(
                    month = currentMonth,
                    records = attendanceRecords
                )
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun MonthNavigationHeader(
    currentMonth: YearMonth,
    onPreviousMonth: () -> Unit,
    onNextMonth: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onPreviousMonth) {
                Icon(Icons.Default.ChevronLeft, "Previous month")
            }

            Text(
                text = "${currentMonth.month.getDisplayName(TextStyle.FULL, Locale.getDefault())} ${currentMonth.year}",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )

            IconButton(onClick = onNextMonth) {
                Icon(Icons.Default.ChevronRight, "Next month")
            }
        }
    }
}

@Composable
private fun DaysOfWeekHeader() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp)
        ) {
            val daysOfWeek = listOf("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat")
            daysOfWeek.forEach { day ->
                Text(
                    text = day,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun CalendarGrid(
    month: YearMonth,
    recordsMap: Map<LocalDate, DatedAttendance>
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            val firstDayOfMonth = month.atDay(1)
            val lastDayOfMonth = month.atEndOfMonth()
            val startOfCalendar = firstDayOfMonth.minusDays(firstDayOfMonth.dayOfWeek.value % 7.toLong())
            val endOfCalendar = lastDayOfMonth.plusDays((6 - lastDayOfMonth.dayOfWeek.value % 7).toLong())

            var currentDate = startOfCalendar

            while (!currentDate.isAfter(endOfCalendar)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    repeat(7) { dayIndex ->
                        CalendarDay(
                            date = currentDate,
                            isCurrentMonth = currentDate.month == month.month,
                            attendanceStatus = recordsMap[currentDate]?.status,
                            modifier = Modifier.weight(1f)
                        )
                        currentDate = currentDate.plusDays(1)
                    }
                }
                Spacer(modifier = Modifier.height(4.dp))
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun CalendarDay(
    date: LocalDate,
    isCurrentMonth: Boolean,
    attendanceStatus: AttendanceStatus?,
    modifier: Modifier = Modifier
) {
    val isToday = date == LocalDate.now()

    val backgroundColor = when {
        attendanceStatus == AttendanceStatus.PRESENT -> Color(0xFF4CAF50).copy(alpha = 0.8f)
        attendanceStatus == AttendanceStatus.ABSENT -> Color(0xFFF44336).copy(alpha = 0.8f)
        isToday -> MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)
        else -> Color.Transparent
    }

    val textColor = when {
        attendanceStatus != null -> Color.White
        isToday -> MaterialTheme.colorScheme.primary
        !isCurrentMonth -> MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f)
        else -> MaterialTheme.colorScheme.onSurface
    }

    Box(
        modifier = modifier
            .aspectRatio(1f)
            .padding(2.dp)
            .clip(CircleShape)
            .background(backgroundColor)
            .clickable(enabled = isCurrentMonth && attendanceStatus != null) {
                //day click for future feature idea
            },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = date.dayOfMonth.toString(),
            color = textColor,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = if (isToday) FontWeight.Bold else FontWeight.Normal
        )
    }
}

@Composable
private fun AttendanceLegend() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "Legend",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            Row(
                horizontalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                LegendItem(
                    color = Color(0xFF4CAF50),
                    text = "Present"
                )
                LegendItem(
                    color = Color(0xFFF44336),
                    text = "Absent"
                )
                LegendItem(
                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f),
                    text = "Today"
                )
            }
        }
    }
}

@Composable
private fun LegendItem(
    color: Color,
    text: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Box(
            modifier = Modifier
                .size(16.dp)
                .clip(CircleShape)
                .background(color)
        )
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun MonthlyAttendanceSummary(
    month: YearMonth,
    records: List<DatedAttendance>
) {
    val monthlyRecords = records.filter {
        val recordDate = LocalDate.parse(it.date)
        recordDate.month == month.month && recordDate.year == month.year
    }

    val presentCount = monthlyRecords.count { it.status == AttendanceStatus.PRESENT }
    val absentCount = monthlyRecords.count { it.status == AttendanceStatus.ABSENT }
    val totalClasses = monthlyRecords.size
    val attendancePercentage = if (totalClasses > 0) {
        (presentCount.toFloat() / totalClasses.toFloat()) * 100
    } else 0f

    if (totalClasses > 0) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            shape = RoundedCornerShape(12.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = "Monthly Summary - ${month.month.getDisplayName(TextStyle.FULL, Locale.getDefault())} ${month.year}",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    SummaryItem(
                        label = "Present",
                        value = presentCount.toString(),
                        color = Color(0xFF4CAF50)
                    )
                    SummaryItem(
                        label = "Absent",
                        value = absentCount.toString(),
                        color = Color(0xFFF44336)
                    )
                    SummaryItem(
                        label = "Total",
                        value = totalClasses.toString(),
                        color = MaterialTheme.colorScheme.primary
                    )
                    SummaryItem(
                        label = "Percentage",
                        value = "${attendancePercentage.toInt()}%",
                        color = if (attendancePercentage >= 75) Color(0xFF4CAF50) else Color(0xFFF44336)
                    )
                }
            }
        }
    }
}

@Composable
private fun SummaryItem(
    label: String,
    value: String,
    color: Color
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = value,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = color
        )
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}