package com.biprangshu.attendo.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.biprangshu.attendo.data.Subject
import com.biprangshu.attendo.repository.UserPreferencesRepository
import com.biprangshu.attendo.screens.CalendarScreen
import com.biprangshu.attendo.screens.HomeScreen
import com.biprangshu.attendo.screens.SettingsScreen
import com.biprangshu.attendo.utils.requiredPercentage
import com.biprangshu.attendo.utils.selectedScreen
import com.biprangshu.attendo.viewmodel.MainViewModel

@RequiresApi(Build.VERSION_CODES.O)
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
                onSubjectAdd = { subject ->
                    mainViewModel.insertSubject(subject)
                },
                onClassPresent = {
                    subject ->
                    mainViewModel.markClassPresent(subject)
                },
                onClassAbsent = {
                    subject ->
                    mainViewModel.markClassAbsent(subject)
                },
                deleteSubject = {
                    subject ->
                    mainViewModel.deleteSubject(subject)
                },
                editSubject = {
                    subject ->
                    mainViewModel.updateSubject(subject)
                },
                navController = navController

            )
        }

        composable(NavScreenObject.SETTINGSSCREEN) {
            selectedScreen = NavScreenObject.SETTINGSSCREEN
            SettingsScreen(
                currentRequiredPercentage = requiredPercentage,
                onRequiredPercentageChange = {
                    percentage->
                    mainViewModel.updateRequiredPercentage(
                        percentage = percentage
                    )
                }
            )
        }

        composable(
            route = NavScreenObject.CALENDAR_SCREEN,
            arguments = listOf(
                navArgument("subjectCode") { type = NavType.StringType },
                navArgument("subjectName") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val subjectCode = backStackEntry.arguments?.getString("subjectCode") ?: ""
            val subjectName = backStackEntry.arguments?.getString("subjectName") ?: ""

            CalendarScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }

}