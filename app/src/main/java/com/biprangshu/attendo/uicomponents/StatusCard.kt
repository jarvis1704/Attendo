package com.biprangshu.attendo.uicomponents

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.TrendingUp
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.CheckCircleOutline
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.biprangshu.attendo.ui.theme.AttendoTheme
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

//@Composable
//fun StatusCard(
//    modifier: Modifier = Modifier,
//    subjectName: String,
//    classesAttended: Int,
//    totalClasses: Int,
//    requiredPercentage: Float,
//) {
//    // --- Calculations ---
//    val currentPercentage = if (totalClasses > 0) {
//        (classesAttended.toFloat() / totalClasses) * 100
//    } else {
//        0f
//    }
//
//    val isOnTrack = currentPercentage >= requiredPercentage
//    val classesToAttend = if (!isOnTrack) {
//        // Simple formula to calculate classes needed to reach the target
//        val needed = (requiredPercentage * totalClasses - 100 * classesAttended) / (100 - requiredPercentage)
//        kotlin.math.ceil(needed).toInt()
//    } else {
//        0
//    }
//
//    // --- Dynamic UI State ---
//    val statusMessage = if (isOnTrack) "You are on track!" else "Need to attend $classesToAttend more classes."
//    val statusColor by animateColorAsState(
//        targetValue = if (isOnTrack) Color(0xFF2E7D32) else MaterialTheme.colorScheme.error,
//        animationSpec = tween(500), label = "statusColorAnimation"
//    )
//    val statusIcon = if (isOnTrack) Icons.Default.CheckCircle else Icons.Default.Warning
//
//    // --- Animation State ---
//    var progress by remember { mutableFloatStateOf(0f) }
//    val animatedProgress by animateFloatAsState(
//        targetValue = progress,
//        animationSpec = tween(durationMillis = 1000),
//        label = "progressAnimation"
//    )
//
//    LaunchedEffect(currentPercentage) {
//        progress = currentPercentage / 100
//    }
//
//    ElevatedCard(
//        modifier = modifier.fillMaxWidth(),
//        shape = MaterialTheme.shapes.extraLarge, // Using M3 shape tokens
//        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 6.dp)
//    ) {
//        Column(
//            modifier = Modifier.padding(24.dp),
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//            Text(
//                text = subjectName.uppercase(Locale.getDefault()),
//                style = MaterialTheme.typography.titleMedium,
//                color = MaterialTheme.colorScheme.onSurface,
//                fontWeight = FontWeight.Bold
//            )
//
//            Spacer(Modifier.height(24.dp))
//
//            // --- Progress Indicator ---
//            Box(contentAlignment = Alignment.Center) {
//                CircularProgressIndicator(
//                    progress = { 1f },
//                    modifier = Modifier.size(160.dp),
//                    color = MaterialTheme.colorScheme.surfaceVariant,
//                    strokeWidth = 12.dp,
//                    strokeCap = StrokeCap.Round
//                )
//                CircularProgressIndicator(
//                    progress = { animatedProgress },
//                    modifier = Modifier.size(160.dp),
//                    color = statusColor,
//                    strokeWidth = 12.dp,
//                    strokeCap = StrokeCap.Round,
//                    trackColor = Color.Transparent
//                )
//                Text(
//                    text = "${currentPercentage.toInt()}%",
//                    style = MaterialTheme.typography.displaySmall,
//                    fontWeight = FontWeight.Bold,
//                    color = statusColor
//                )
//            }
//
//            Spacer(Modifier.height(24.dp))
//
//            // --- Status Message ---
//            Row(
//                verticalAlignment = Alignment.CenterVertically,
//                horizontalArrangement = Arrangement.spacedBy(8.dp)
//            ) {
//                Icon(
//                    imageVector = statusIcon,
//                    contentDescription = "Status Icon",
//                    tint = statusColor,
//                    modifier = Modifier.size(20.dp)
//                )
//                Text(
//                    text = statusMessage,
//                    style = MaterialTheme.typography.bodyLarge,
//                    fontWeight = FontWeight.SemiBold,
//                    color = statusColor
//                )
//            }
//
//            Spacer(Modifier.height(16.dp))
//
//            // --- Detailed Stats ---
//            Row(
//                modifier = Modifier.fillMaxWidth(),
//                horizontalArrangement = Arrangement.SpaceEvenly,
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                StatColumn("Attended", "$classesAttended")
//                StatColumn("Total", "$totalClasses")
//                StatColumn("Required", "${requiredPercentage.toInt()}%")
//            }
//        }
//    }
//}
//
//@Composable
//fun StatColumn(label: String, value: String) {
//    Column(horizontalAlignment = Alignment.CenterHorizontally) {
//        Text(
//            text = label.uppercase(Locale.getDefault()),
//            style = MaterialTheme.typography.labelMedium,
//            color = MaterialTheme.colorScheme.onSurfaceVariant
//        )
//        Text(
//            text = value,
//            style = MaterialTheme.typography.titleLarge,
//            fontWeight = FontWeight.SemiBold,
//            color = MaterialTheme.colorScheme.onSurface
//        )
//    }
//}
//
//// --- Previews ---
//@Preview(showBackground = true, name = "On Track Status")
//@Composable
//fun StatusCardPreview_OnTrack() {
//    AttendoTheme {
//        Box(modifier = Modifier.padding(16.dp)) {
//            StatusCard(
//                subjectName = "Mobile Computing",
//                classesAttended = 24,
//                totalClasses = 30,
//                requiredPercentage = 75f
//            )
//        }
//    }
//}
//
//@Preview(showBackground = true, name = "Danger Status")
//@Composable
//fun StatusCardPreview_Danger() {
//    AttendoTheme {
//        Box(modifier = Modifier.padding(16.dp)) {
//            StatusCard(
//                subjectName = "Compiler Design",
//                classesAttended = 15,
//                totalClasses = 25,
//                requiredPercentage = 75f
//            )
//        }
//    }
//}

@Composable
fun StatusCard(
    modifier: Modifier = Modifier,
    minAttendance: String,
    totalAttendance: String,
    date: String
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.extraLarge,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
        )
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 24.dp, vertical = 20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            InfoRow(
                icon = Icons.Default.CheckCircleOutline,
                label = "Minimum Attendance",
                value = minAttendance
            )
            InfoRow(
                icon = Icons.AutoMirrored.Filled.TrendingUp,
                label = "Total Attendance",
                value = totalAttendance
            )
            InfoRow(
                icon = Icons.Default.CalendarToday,
                label = "Date",
                value = date
            )
        }
    }
}

@Composable
private fun InfoRow(
    icon: ImageVector,
    label: String,
    value: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                modifier = Modifier.size(20.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            Spacer(Modifier.size(12.dp))
            Text(
                text = "$label:",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}


@Preview(showBackground = true, uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES)
@Composable
fun StatusCardPreview() {
    AttendoTheme {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Attendo",
                style = MaterialTheme.typography.displaySmall,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            StatusCard(
                minAttendance = "75%",
                totalAttendance = "80%",
                date = SimpleDateFormat("EEEE, dd MMMM", Locale.getDefault()).format(Date())
            )
        }
    }
}

