package com.biprangshu.attendo.uicomponents

import android.content.res.Configuration
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.CircularWavyProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.WavyProgressIndicatorDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.biprangshu.attendo.ui.theme.AttendoTheme
import kotlin.math.ceil

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun SubjectCard(
    modifier: Modifier = Modifier,
    subject: String,
    classAttended: Int,
    classTotal: Int,
    requiredPercentage: Float
) {
    val density = LocalDensity.current

    val currentPercentage = if (classTotal > 0) {
        (classAttended.toFloat() / classTotal.toFloat()) * 100
    } else {
        0f
    }

    val statusColor by animateColorAsState(
        targetValue = when {
            currentPercentage >= requiredPercentage -> Color(0xFF2E7D32)
            currentPercentage >= requiredPercentage - 10 -> Color(0xFFF9A825)
            else -> MaterialTheme.colorScheme.error
        },
        animationSpec = tween(500), label = "statusColorAnimation"
    )

    val message = when {
        currentPercentage >= requiredPercentage -> "You're on track!"
        else -> {
            val needed = ceil((requiredPercentage * classTotal - 100 * classAttended) / (100 - requiredPercentage))
            if(needed > 0) "Attend the next ${needed.toInt()} classes" else "Attendance is low"
        }
    }

    var progress by remember { mutableFloatStateOf(0f) }
    val animatedProgress by animateFloatAsState(
        targetValue = progress,
        animationSpec = tween(durationMillis = 1200, delayMillis = 300),
        label = "progressAnimation"
    )

    LaunchedEffect(currentPercentage) {
        progress = currentPercentage / 100
    }

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.extraLarge,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = subject,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(Modifier.height(12.dp))
                Text(
                    text = "Attendance: $classAttended / $classTotal",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text = message,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = statusColor
                )
            }

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.size(100.dp)
            ) {
                CircularProgressIndicator(
                    progress = { 1f },
                    modifier = Modifier.size(100.dp),
                    color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f),
                    strokeWidth = 4.dp,
                    strokeCap = StrokeCap.Round
                )
                CircularWavyProgressIndicator(
                    progress = { animatedProgress },
                    modifier = Modifier.size(100.dp),
                    color = statusColor,
                    trackColor = Color.Transparent,
                    stroke = Stroke(width = with(density) { 4.dp.toPx() }, cap = StrokeCap.Round),
                    wavelength = WavyProgressIndicatorDefaults.CircularWavelength * 0.8f,
                )
                Text(
                    text = "${currentPercentage.toInt()}%",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.ExtraBold,
                    color = statusColor
                )
            }
        }
    }
}
