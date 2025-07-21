package com.biprangshu.attendo.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.biprangshu.attendo.screens.HomeScreen
import com.biprangshu.attendo.screens.SettingsScreen
import com.biprangshu.attendo.utils.selectedScreen

@Composable
fun Navigation(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {

    NavHost(
        navController = navController,
        startDestination = NavScreenObject.HOMESCREEN
    ){
        composable(NavScreenObject.HOMESCREEN) {
            selectedScreen = NavScreenObject.HOMESCREEN
            HomeScreen()
        }

        composable(NavScreenObject.SETTINGSSCREEN) {
            selectedScreen = NavScreenObject.SETTINGSSCREEN
            SettingsScreen()
        }
    }

}