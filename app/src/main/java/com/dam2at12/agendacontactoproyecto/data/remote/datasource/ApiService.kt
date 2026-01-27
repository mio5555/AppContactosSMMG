package com.dam2at12.agendacontactoproyecto.data.remote.datasource

import com.dam2at12.agendacontactoproyecto.data.remote.model.ApiResponse
import retrofit2.http.GET

//Interfaz para llamar a nuestra API REST (model/ApiResponse.kt)

interface ApiService {
    @GET(value = "?inc=picture")
    //Indicamos qué campos queremos recibir de la API.
    //En nuestro caso, solo tomamos la imagen (model/ContactPicture)

    suspend fun getContact(): ApiResponse
}