package com.biprangshu.attendo.uicomponents

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.biprangshu.attendo.utils.showFirstOpenAlert

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun FirstAlertDialog(
    modifier: Modifier = Modifier,
    onSaveRequiredPercentage: (Float) -> Unit,
    changeFirstAppOpen: (Boolean) -> Unit
) {
    var requiredPercentage by remember { mutableStateOf("75") }
    var animationProgress by remember { mutableFloatStateOf(0f) }

    val animatedAlpha by animateFloatAsState(
        targetValue = animationProgress,
        animationSpec = tween(durationMillis = 800),
        label = "dialogAnimation"
    )
    val animatedScale by animateFloatAsState(
        targetValue = animationProgress,
        animationSpec = tween(durationMillis = 600, delayMillis = 200),
        label = "scaleAnimation"
    )

    LaunchedEffect(showFirstOpenAlert) {
        animationProgress = if (showFirstOpenAlert) 1f else 0f
    }

    val isValidPercentage = requiredPercentage.toFloatOrNull()?.let {
        it in 1f..100f
    } ?: false

    if (showFirstOpenAlert) {
        AlertDialog(
            onDismissRequest = { },
            containerColor = MaterialTheme.colorScheme.surface,
            tonalElevation = 12.dp,
            modifier = Modifier
                .fillMaxWidth()
                .alpha(animatedAlpha)
                .graphicsLayer {
                    scaleX = 0.8f + (0.2f * animatedScale)
                    scaleY = 0.8f + (0.2f * animatedScale)
                },
            title = {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(
                        imageVector = Icons.Default.School,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.graphicsLayer {
                            scaleX = 1.3f
                            scaleY = 1.3f
                        }
                    )
                    Text(
                        text = "Welcome to Attendo!",
                        style = MaterialTheme.typography.headlineMediumEmphasized,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold
                    )
                }
            },
            text = {
                Column(
                    verticalArrangement = Arrangement.spacedBy(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Let's set up your attendance tracking. What's your required attendance percentage?",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurface,
                        textAlign = TextAlign.Center,
                        lineHeight = MaterialTheme.typography.bodyLarge.lineHeight.times(1.2f)
                    )

                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f)
                        ),
                        shape = MaterialTheme.shapes.large
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                imageVector = Icons.Default.Settings,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary
                            )
                            Text(
                                text = "This will be used to calculate how many classes you need to attend to maintain your target.",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onPrimaryContainer,
                                textAlign = TextAlign.Center,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }

                    OutlinedTextField(
                        value = requiredPercentage,
                        onValueChange = {
                            if (it.length <= 3) requiredPercentage = it
                        },
                        label = {
                            Text(
                                "Required Percentage",
                                style = MaterialTheme.typography.bodyLarge
                            )
                        },
                        placeholder = {
                            Text(
                                "e.g., 75",
                                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
                            )
                        },
                        suffix = {
                            Text(
                                "%",
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.primary,
                                fontWeight = FontWeight.Bold
                            )
                        },
                        modifier = Modifier.width(160.dp),
                        shape = MaterialTheme.shapes.extraLarge,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.6f),
                            focusedLabelColor = MaterialTheme.colorScheme.primary
                        ),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        singleLine = true,
                        isError = requiredPercentage.isNotEmpty() && !isValidPercentage
                    )

                    if (requiredPercentage.isNotEmpty() && !isValidPercentage) {
                        Text(
                            text = "Please enter a valid percentage (1-100)",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.error,
                            fontWeight = FontWeight.Medium
                        )
                    }

                    if (isValidPercentage) {
                        val percentage = requiredPercentage.toFloat()
                        Card(
                            colors = CardDefaults.cardColors(
                                containerColor = when {
                                    percentage >= 80 -> MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.4f)
                                    percentage >= 70 -> MaterialTheme.colorScheme.tertiaryContainer.copy(alpha = 0.4f)
                                    else -> MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.4f)
                                }
                            ),
                            shape = MaterialTheme.shapes.medium
                        ) {
                            Text(
                                text = when {
                                    percentage >= 80 -> "High standard - You'll need consistent attendance!"
                                    percentage >= 70 -> "Good balance between flexibility and commitment"
                                    else -> "Relaxed requirement - Easy to maintain"
                                },
                                style = MaterialTheme.typography.bodySmall,
                                color = when {
                                    percentage >= 80 -> MaterialTheme.colorScheme.onErrorContainer
                                    percentage >= 70 -> MaterialTheme.colorScheme.onTertiaryContainer
                                    else -> MaterialTheme.colorScheme.onSecondaryContainer
                                },
                                fontWeight = FontWeight.Medium,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.padding(12.dp)
                            )
                        }
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        requiredPercentage.toFloatOrNull()?.let { percentage ->
                            onSaveRequiredPercentage(percentage)
                            showFirstOpenAlert = false
                        }
                        changeFirstAppOpen(false)
                    },
                    enabled = isValidPercentage,
                    shape = MaterialTheme.shapes.extraLarge,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary,
                        disabledContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                        disabledContentColor = MaterialTheme.colorScheme.onSurfaceVariant
                    ),
                    elevation = ButtonDefaults.buttonElevation(
                        defaultElevation = 6.dp,
                        pressedElevation = 12.dp
                    ),
                    modifier = Modifier.padding(horizontal = 8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Settings,
                        contentDescription = null,
                        modifier = Modifier.padding(end = 8.dp)
                    )
                    Text(
                        "Get Started",
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        )
    }
}