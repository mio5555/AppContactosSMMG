package com.dam2at12.agendacontactoproyecto.navigation 

sealed class Screen (val ruta: String) {
    object HomeScreen: Screen("home")
    object LoginScreen: Screen("login")
    object DetailScreen: Screen("detail")
    object AddScreen: Screen("add")
}