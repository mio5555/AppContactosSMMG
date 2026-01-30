package com.dam2at12.agendacontactoproyecto

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.dam2at12.agendacontactoproyecto.ui.screens.ContactDetailScreen
import com.dam2at12.agendacontactoproyecto.ui.screens.HomeScreen
import com.dam2at12.agendacontactoproyecto.ui.screens.LoginScreen


@Composable
fun NavigationHostController() {

    val navController = rememberNavController()

    NavHost(navController = navController,
        startDestination = "login") {
        composable ("login") { LoginScreen(navController) }
        composable ("home") { HomeScreen(navController) }
        composable ("contact") { ContactDetailScreen(navController) }


    }

}