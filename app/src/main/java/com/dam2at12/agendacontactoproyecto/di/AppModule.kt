package com.dam2at12.agendacontactoproyecto.di

import android.app.Application
import androidx.room.Room
import com.dam2at12.agendacontactoproyecto.data.local.dao.ContactDao
import com.dam2at12.agendacontactoproyecto.data.local.database.ContactDatabase
import com.dam2at12.agendacontactoproyecto.data.remote.datasource.ApiService
import com.dam2at12.agendacontactoproyecto.networkhelper.NetworkHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton
import kotlin.jvm.java

// Este archivo es un MÓDULO de Hilt: aquí le decimos a Hilt cómo crear objetos
// que vamos a reutilizar en la app (por ejemplo Retrofit y la API).
@Module //Modulo de Hilt
@InstallIn(SingletonComponent::class) //Contenedor para installar las dependencias único en toda la aplicación
object AppModule { //Instancia de módulo de Hilt único en la aplicación

    // Proveedor de la URL base de la API.
    @Provides
    @Singleton //Instancia única durante toda la aplicación
    fun provideBaseURL(): String = "https://randomuser.me/api/" //URL base de la API

    // Proveedor de Retrofit (la “herramienta” que hace peticiones HTTP).
    // Recibe la BaseURL por inyección de dependencias.
    // GsonConverterFactory sirve para convertir el JSON recibido en objetos Kotlin automáticamente.
    @Provides
    @Singleton
    fun provideRetrofit(baseURL: String) : Retrofit { //proveo la URL mediante inyeccion de dependencias
        return Retrofit.Builder()
            .baseUrl(baseURL) // Dirección base a la que Retrofit hará las llamadas
            .addConverterFactory(GsonConverterFactory.create()) // JSON -> objetos Kotlin
            .build()
    }

    // Proveedor de la interfaz de la API (RestDataSource).
    // Retrofit crea una implementación automática de esta interfaz usando sus anotaciones
    // (@GET, @Query, etc.).
    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit) : ApiService {
        return retrofit.create(ApiService::class.java)
    }

    //Módulo que construye la instancia de la BBDD
    @Provides
    @Singleton
    fun provideContactDatabase(context: Application): ContactDatabase {
        return Room.databaseBuilder(
            context,
            ContactDatabase::class.java,
            "contactos_database" //Nombre que almacenará la BBDD
        ).build()
    }

    //Módulo que provee el DAO
    @Provides
    @Singleton
    fun provideContactDao(db: ContactDatabase) : ContactDao {
        return db.contactDao()
    }

    //Módulo que provee el NetworkHelper
    @Provides
    @Singleton
    fun provideNetworkHelper(context: Application): NetworkHelper {
        return NetworkHelper(context)
    }
}