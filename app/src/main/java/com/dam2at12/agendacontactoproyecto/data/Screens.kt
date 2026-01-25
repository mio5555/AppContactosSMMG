package com.dam2at12.agendacontactoproyecto.data

sealed class Screens (val name: String) {
    object LoginScreen: Screens("login")
    object HomeScreen: Screens("home")
    object ContactDetailScreen: Screens("contact")
}