package com.dam2at12.agendacontactoproyecto.data.remote.model

//En esta clase estructuro la respuesta de la API.
//Configuro solo lo que me voy a traer para esta aplicación y la inicializo como vacía
data class ApiResponse (
    val results: List<ContactPicture> = emptyList()
)
