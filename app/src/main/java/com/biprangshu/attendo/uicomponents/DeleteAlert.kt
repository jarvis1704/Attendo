package com.biprangshu.attendo.uicomponents

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.biprangshu.attendo.R
import com.biprangshu.attendo.data.Subject
import com.biprangshu.attendo.utils.deleteAlert
import com.biprangshu.attendo.utils.subjectToBeDeleted


@Composable
fun DeleteAlert(
    deleteSubject: (Subject)-> Unit,
){
    if (deleteAlert){
        AlertDialog(
            onDismissRequest = {
                deleteAlert = false
            },
            containerColor = lerp(
                MaterialTheme.colorScheme.background,
                MaterialTheme.colorScheme.onBackground,
                0.05f
            ),
            modifier = Modifier.shadow(
                elevation = 24.dp,
                ambientColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                spotColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
            ),
            shape = RoundedCornerShape(20.dp),
            title = {
                Box(){
                    Column (
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ){
                        Text(
                            "Are you sure you want to delete the Subject?",
                            modifier = Modifier
                                .padding(bottom = 30.dp),
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        Image(
                            painter = painterResource(R.drawable.erroralert_vector),
                            contentDescription = "album cover",
                            modifier = Modifier.Companion
                                .fillMaxWidth(0.6f)
                                .padding(bottom = 30.dp)
                        )
                    }
                }
            },
            text = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "This action cannot be undone.",
                        color = MaterialTheme.colorScheme.onBackground.copy(0.7f),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier
                    )
                }
            },
            confirmButton = {
                Column (
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    Row(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Button(
                            shape = RoundedCornerShape(18.dp),
                            modifier = Modifier
                                .weight(1f)
                                .padding(vertical = 10.dp)
                                .padding(top = 6.dp)
                                .background(
                                    brush = Brush.horizontalGradient(
                                        colors = listOf(
                                            MaterialTheme.colorScheme.error.copy(alpha = 0.8f),
                                            MaterialTheme.colorScheme.error
                                        )
                                    ),
                                    shape = RoundedCornerShape(18.dp)
                                ),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Transparent,
                                contentColor = Color.White
                            ),
                            onClick = {
                                deleteAlert = false
                            }) {
                            Text(
                                text = "No",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                            )
                        }
                        Spacer(Modifier.width(8.dp))
                        Button(
                            shape = RoundedCornerShape(18.dp),
                            modifier = Modifier
                                .weight(1f)
                                .padding(vertical = 10.dp)
                                .padding(top = 6.dp)
                                .background(
                                    brush = Brush.horizontalGradient(
                                        colors = listOf(
                                            MaterialTheme.colorScheme.primary.copy(alpha = 0.8f),
                                            MaterialTheme.colorScheme.primary
                                        )
                                    ),
                                    shape = RoundedCornerShape(18.dp)
                                ),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Transparent,
                                contentColor = Color.White
                            ),
                            onClick = {
                                deleteAlert = false
                                subjectToBeDeleted?.let {
                                    deleteSubject(it)
                                }
                                subjectToBeDeleted=null
                            }) {
                            Text(
                                text = "Okay",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                            )
                        }
                    }
                }
            }
        )
    }
}