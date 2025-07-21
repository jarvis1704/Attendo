package com.biprangshu.attendo.screens

import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialExpressiveTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.biprangshu.attendo.Material3ExpressiveTest
import com.biprangshu.attendo.data.Subject
import com.biprangshu.attendo.uicomponents.AddSubjectModal
import com.biprangshu.attendo.uicomponents.StatusCard
import com.biprangshu.attendo.uicomponents.SubjectCard
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    subjects: List<Subject>,
    onSubjectAdd: (Subject) -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .statusBarsPadding()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Attendo",
                    style = MaterialTheme.typography.displayMediumEmphasized,
                    color = MaterialTheme.colorScheme.primary
                )
            }
            Spacer(Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth()
                    .padding(horizontal = 16.dp),

            ) {
                StatusCard(
                    minAttendance = "75%",
                    totalAttendance = "81%",
                    date = SimpleDateFormat("EEEE, dd MMMM", Locale.getDefault()).format(Date())
                )
            }
//            SubjectCard(
//                modifier = Modifier.padding(16.dp),
//                subject = "Physics",
//                classAttended = 25,
//                classTotal = 30,
//                requiredPercentage = 75f
//            )
//            SubjectCard(
//                modifier = Modifier.padding(16.dp),
//                subject = "Chemistry",
//                classAttended = 22,
//                classTotal = 30,
//                requiredPercentage = 75f
//            )
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(
                    items= subjects,
                    key= {subject -> subject.subjectCode}
                ){
                    subject->
                    SubjectCard(
                        modifier = Modifier.padding(16.dp),
                        subject = subject.subjectName,
                        classAttended = subject.classAttended,
                        classTotal = subject.totalClasses,
                        requiredPercentage = 75f //todo: add datastore to change
                    )
                }
            }
        }
        AddSubjectModal(
            onAddSubject = onSubjectAdd
        )
    }
}