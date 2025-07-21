package com.biprangshu.attendo.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.biprangshu.attendo.data.Subject
import com.biprangshu.attendo.screens.HomeScreen
import com.biprangshu.attendo.screens.SettingsScreen
import com.biprangshu.attendo.utils.selectedScreen
import com.biprangshu.attendo.viewmodel.MainViewModel

@Composable
fun Navigation(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    mainViewModel: MainViewModel = hiltViewModel()
) {

    val subjectList by mainViewModel.allSubjects.collectAsState(initial = emptyList())

    NavHost(
        navController = navController,
        startDestination = NavScreenObject.HOMESCREEN
    ){
        composable(NavScreenObject.HOMESCREEN) {
            selectedScreen = NavScreenObject.HOMESCREEN
            HomeScreen(
                subjects = subjectList,
                onSubjectAdd = {
                    subject ->
                    mainViewModel.insertSubject(subject)
                }
            )
        }

        composable(NavScreenObject.SETTINGSSCREEN) {
            selectedScreen = NavScreenObject.SETTINGSSCREEN
            SettingsScreen()
        }
    }

}