package com.dam2at12.agendacontactoproyecto.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.dam2at12.agendacontactoproyecto.ui.screens.AddScreen
import com.dam2at12.agendacontactoproyecto.ui.screens.ContactDetailScreen
import com.dam2at12.agendacontactoproyecto.ui.screens.EditScreen
import com.dam2at12.agendacontactoproyecto.ui.screens.HomeScreen
import com.dam2at12.agendacontactoproyecto.ui.screens.LoginScreen


@Composable
fun AppNavigation() {

    val navController = rememberNavController()

    NavHost(navController = navController,
        startDestination = Screen.LoginScreen.ruta) {

        composable (Screen.LoginScreen.ruta) { LoginScreen(navController) }
        composable (Screen.HomeScreen.ruta) { HomeScreen(navController) }

        composable (
            route = "${Screen.DetailScreen.ruta}/{contactId}", //contactId es un elemento dinámico porque va cambiando
            arguments = listOf(navArgument("contactId") { type = NavType.IntType })
        ){navBackStackEntry -> //navBackStackEntry es un objeto que contiene la información de la navegación hacia la pantalla
            val contactId = navBackStackEntry.arguments?.getInt("contactId") ?: 0 //Operador Elvis. En caso de venir vacío el id es = 0

            ContactDetailScreen(navController, contactId) //Es obligatorio nombrar el argumento navController porque es el segundo
        }


//        composable( route = Screen.DetailScreen.ruta, // "detail/{id}"
//             arguments = listOf( navArgument("id")
//             { type = NavType.IntType
//                 defaultValue = 0 }
//             )
//        )
//        { backStackEntry ->
//            val id = backStackEntry.arguments?.getInt("id") ?: 0
//             }ContactDetailScreen(navController, id)
        composable (Screen.AddScreen.ruta){ AddScreen(navController) }


        composable (
            route = "${Screen.EditScreen.ruta}/{contactId}", //contactId es un elemento dinámico porque va cambiando
            arguments = listOf(navArgument("contactId") { type = NavType.IntType })
        ){navBackStackEntry -> //navBackStackEntry es un objeto que contiene la información de la navegación hacia la pantalla
            val contactId = navBackStackEntry.arguments?.getInt("contactId") ?: 0 //Operador Elvis. En caso de venir vacío el id es = 0

            EditScreen(navController, contactId) //Es obligatorio nombrar el argumento navController porque es el segundo
        }


        }

    }

