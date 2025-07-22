package com.biprangshu.attendo.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialExpressiveTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.biprangshu.attendo.Material3ExpressiveTest
import com.biprangshu.attendo.data.Subject
import com.biprangshu.attendo.uicomponents.AddSubjectModal
import com.biprangshu.attendo.uicomponents.EditSubjectModal
import com.biprangshu.attendo.uicomponents.ShowSubjectDetailModal
import com.biprangshu.attendo.uicomponents.StatusCard
import com.biprangshu.attendo.uicomponents.SubjectCard
import com.biprangshu.attendo.utils.editSubjectDetail
import com.biprangshu.attendo.utils.selectedSubject
import com.biprangshu.attendo.utils.selectedSubjectForEdit
import com.biprangshu.attendo.utils.showSubjectDetail
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    subjects: List<Subject>,
    onSubjectAdd: (Subject) -> Unit,
    onClassPresent: (Subject) -> Unit,
    onClassAbsent: (Subject) -> Unit,
    deleteSubject: (Subject) -> Unit,
    editSubject: (Subject) -> Unit
) {
    val overallAttendance by remember(subjects) {
        derivedStateOf {
            if (subjects.isEmpty()) return@derivedStateOf 0f
            val totalAttended = subjects.sumOf { it.classAttended }
            val totalClasses = subjects.sumOf { it.totalClasses }
            if (totalClasses > 0) (totalAttended.toFloat() / totalClasses.toFloat()) * 100 else 0f
        }
    }

    Surface(modifier = modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .statusBarsPadding()
                    .padding(start = 16.dp, end = 16.dp, top = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Attendo",
                    style = MaterialTheme.typography.displaySmall,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            Spacer(Modifier.height(16.dp))

            Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)) {
                StatusCard(
                    minAttendance = "75%",
                    totalAttendance = "${overallAttendance.toInt()}%",
                    date = SimpleDateFormat("EEEE, dd MMMM", Locale.getDefault()).format(Date())
                )
            }

            Spacer(Modifier.height(16.dp))

            LazyColumn(
                modifier = Modifier.fillMaxWidth().padding(bottom = 130.dp),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(
                    items = subjects,
                    key = { subject -> subject.subjectCode }
                ) { subject ->
                    val dismissState = rememberSwipeToDismissBoxState(
                        confirmValueChange = {
                            direction->
                            if (direction == SwipeToDismissBoxValue.EndToStart) {
                                onClassPresent(subject)
                            } else if (direction == SwipeToDismissBoxValue.StartToEnd) {
                                onClassAbsent(subject)
                            }
                            false
                        },
                        positionalThreshold = { it * .25f }
                    )

                    LaunchedEffect(dismissState.currentValue) {
                        if (dismissState.currentValue != SwipeToDismissBoxValue.Settled) {
                            if (dismissState.currentValue == SwipeToDismissBoxValue.EndToStart) {
                                onClassPresent(subject)
                            } else if (dismissState.currentValue == SwipeToDismissBoxValue.StartToEnd) {
                                onClassAbsent(subject)
                            }
                            delay(500)
                            dismissState.reset()
                        }
                    }

                    SwipeToDismissBox(
                        state = dismissState,
                        backgroundContent = {
                            val color = when (dismissState.dismissDirection) {
                                SwipeToDismissBoxValue.EndToStart -> Color(0xFF2E7D32)
                                SwipeToDismissBoxValue.StartToEnd -> Color(0xFFB71C1C)
                                SwipeToDismissBoxValue.Settled -> Color.Transparent
                            }
                            val icon = when (dismissState.dismissDirection) {
                                SwipeToDismissBoxValue.EndToStart -> Icons.Default.Check
                                SwipeToDismissBoxValue.StartToEnd -> Icons.Default.Close
                                else -> null
                            }
                            val alignment = when (dismissState.dismissDirection) {
                                SwipeToDismissBoxValue.EndToStart -> Alignment.CenterEnd
                                SwipeToDismissBoxValue.StartToEnd -> Alignment.CenterStart
                                else -> Alignment.Center
                            }
                            Box(
                                Modifier
                                    .fillMaxSize()
                                    .background(color, shape = RoundedCornerShape(100.dp))
                                    .padding(horizontal = 20.dp),
                                contentAlignment = alignment
                            ) {
                                if (icon != null) {
                                    Icon(
                                        icon,
                                        contentDescription = "Swipe Action",
                                        tint = Color.White
                                    )
                                }
                            }
                        }
                    ) {
                        SubjectCard(
                            subject = subject.subjectName,
                            classAttended = subject.classAttended,
                            classTotal = subject.totalClasses,
                            requiredPercentage = 75f,
                            onClick = {
                                showSubjectDetail(
                                    subject = subject
                                )
                            }
                        )
                    }
                }
            }
            AddSubjectModal(
                onAddSubject = onSubjectAdd
            )
            selectedSubject?.let {
                ShowSubjectDetailModal(
                    subject = it,
                    deleteSubject = deleteSubject
                )
            }

            selectedSubjectForEdit?.let {
                EditSubjectModal(
                    subject = it,
                    onUpdateSubject = editSubject
                )
            }
        }

    }
}
