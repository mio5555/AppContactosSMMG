package com.dam2at12.agendacontactoproyecto

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.dam2at12.agendacontactoproyecto.data.Screens
import com.dam2at12.agendacontactoproyecto.data.SelectedContact
import com.dam2at12.agendacontactoproyecto.screens.ContactDetailScreen
import com.dam2at12.agendacontactoproyecto.screens.HomeScreen
import com.dam2at12.agendacontactoproyecto.screens.LoginScreen


@Composable
fun NavigationHostController() {

    val navController = rememberNavController()

    NavHost(navController = navController,
        startDestination = "home") {
        composable ("login") { LoginScreen(navController) }
        composable ("home") { HomeScreen(navController) }
        composable ("contact") { ContactDetailScreen(navController) }


    }

}