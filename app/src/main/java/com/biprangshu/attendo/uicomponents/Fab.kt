package com.biprangshu.attendo.uicomponents

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.unit.dp
import com.biprangshu.attendo.utils.showSubjectAddModal

@Composable
fun Fab(modifier: Modifier = Modifier) {
    val hapticFeedback = LocalHapticFeedback.current
    LargeFloatingActionButton(
        onClick = {
            showSubjectAddModal = true
            hapticFeedback.performHapticFeedback(HapticFeedbackType.ContextClick)
        },
        elevation = FloatingActionButtonDefaults.elevation(
            4.dp
        )
    ){
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = null,
            modifier = Modifier.size(32.dp)
        )
    }

}