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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.biprangshu.attendo.data.Subject
import com.biprangshu.attendo.utils.showSubjectAddModal

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun AddSubjectModal(
    modifier: Modifier = Modifier,
    onAddSubject: (Subject)-> Unit
) {
    var subject by remember { mutableStateOf("") }
    var subjectCode by remember { mutableStateOf("") }
    var classAttended by remember { mutableIntStateOf(0) }
    var totalClass by remember { mutableIntStateOf(0) }

    var animationProgress by remember { mutableFloatStateOf(0f) }
    val animatedAlpha by animateFloatAsState(
        targetValue = animationProgress,
        animationSpec = tween(durationMillis = 600),
        label = "modalAnimation"
    )
    val animatedScale by animateFloatAsState(
        targetValue = animationProgress,
        animationSpec = tween(durationMillis = 400, delayMillis = 200),
        label = "scaleAnimation"
    )

    LaunchedEffect(showSubjectAddModal) {
        animationProgress = if (showSubjectAddModal) 1f else 0f
    }

    val isFormValid = subject.isNotBlank() &&
            subjectCode.isNotBlank() &&
            totalClass > 0 &&
            classAttended >= 0 &&
            classAttended <= totalClass

    if (showSubjectAddModal) {
        ModalBottomSheet(
            onDismissRequest = { showSubjectAddModal = false },
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface,
            dragHandle = {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(vertical = 12.dp)
                ) {
                    Spacer(
                        modifier = Modifier
                            .width(40.dp)
                            .height(4.dp)
                            .graphicsLayer {
                                alpha = 0.6f
                            }
                    )
                }
            }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 16.dp)
                    .alpha(animatedAlpha)
                    .graphicsLayer {
                        scaleX = 0.8f + (0.2f * animatedScale)
                        scaleY = 0.8f + (0.2f * animatedScale)
                    },
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.School,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.graphicsLayer {
                            scaleX = 1.2f
                            scaleY = 1.2f
                        }
                    )
                    Text(
                        text = "Add New Subject",
                        style = MaterialTheme.typography.headlineMediumEmphasized,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold
                    )
                }

                Column(
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    OutlinedTextField(
                        value = subject,
                        onValueChange = { subject = it },
                        label = {
                            Text(
                                "Subject Name",
                                style = MaterialTheme.typography.bodyLarge
                            )
                        },
                        placeholder = {
                            Text(
                                "e.g., Mathematics",
                                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
                            )
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = MaterialTheme.shapes.extraLarge,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.6f),
                            focusedLabelColor = MaterialTheme.colorScheme.primary
                        ),
                        singleLine = true
                    )

                    OutlinedTextField(
                        value = subjectCode,
                        onValueChange = { subjectCode = it },
                        label = {
                            Text(
                                "Subject Code",
                                style = MaterialTheme.typography.bodyLarge
                            )
                        },
                        placeholder = {
                            Text(
                                "e.g., MATH101",
                                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
                            )
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = MaterialTheme.shapes.extraLarge,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.6f),
                            focusedLabelColor = MaterialTheme.colorScheme.primary
                        ),
                        singleLine = true
                    )

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        OutlinedTextField(
                            value = if (classAttended == 0) "" else classAttended.toString(),
                            onValueChange = {
                                classAttended = it.toIntOrNull() ?: 0
                            },
                            label = {
                                Text(
                                    "Classes Attended",
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            },
                            placeholder = {
                                Text(
                                    "0",
                                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
                                )
                            },
                            modifier = Modifier.weight(1f),
                            shape = MaterialTheme.shapes.extraLarge,
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = MaterialTheme.colorScheme.primary,
                                unfocusedBorderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.6f),
                                focusedLabelColor = MaterialTheme.colorScheme.primary
                            ),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            singleLine = true
                        )

                        OutlinedTextField(
                            value = if (totalClass == 0) "" else totalClass.toString(),
                            onValueChange = {
                                totalClass = it.toIntOrNull() ?: 0
                            },
                            label = {
                                Text(
                                    "Total Classes",
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            },
                            placeholder = {
                                Text(
                                    "0",
                                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
                                )
                            },
                            modifier = Modifier.weight(1f),
                            shape = MaterialTheme.shapes.extraLarge,
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = MaterialTheme.colorScheme.primary,
                                unfocusedBorderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.6f),
                                focusedLabelColor = MaterialTheme.colorScheme.primary
                            ),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            singleLine = true
                        )
                    }

                    if (totalClass > 0) {
                        val percentage = (classAttended.toFloat() / totalClass.toFloat()) * 100
                        Text(
                            text = "Current Attendance: ${percentage.toInt()}%",
                            style = MaterialTheme.typography.bodyMedium,
                            color = when {
                                percentage >= 75 -> MaterialTheme.colorScheme.primary
                                percentage >= 65 -> MaterialTheme.colorScheme.tertiary
                                else -> MaterialTheme.colorScheme.error
                            },
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.padding(start = 4.dp)
                        )
                    }
                }

                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp)
                ) {
                    TextButton(
                        onClick = {
                            subject = ""
                            subjectCode = ""
                            classAttended = 0
                            totalClass = 0
                            showSubjectAddModal = false
                        },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.textButtonColors(
                            contentColor = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    ) {
                        Text(
                            "Cancel",
                            style = MaterialTheme.typography.labelLarge,
                            fontWeight = FontWeight.Medium
                        )
                    }

                    Button(
                        onClick = {
                            onAddSubject(
                                Subject(
                                    subjectCode = subjectCode,
                                    subjectName = subject,
                                    classAttended = classAttended,
                                    totalClasses = totalClass,
                                )
                            )
                            subject = ""
                            subjectCode = ""
                            classAttended = 0
                            totalClass = 0
                            showSubjectAddModal = false
                        },
                        enabled = isFormValid,
                        modifier = Modifier.weight(1f),
                        shape = MaterialTheme.shapes.extraLarge,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = MaterialTheme.colorScheme.onPrimary,
                            disabledContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                            disabledContentColor = MaterialTheme.colorScheme.onSurfaceVariant
                        ),
                        elevation = ButtonDefaults.buttonElevation(
                            defaultElevation = 4.dp,
                            pressedElevation = 8.dp
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = null,
                            modifier = Modifier.padding(end = 8.dp)
                        )
                        Text(
                            "Add Subject",
                            style = MaterialTheme.typography.labelLarge,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}