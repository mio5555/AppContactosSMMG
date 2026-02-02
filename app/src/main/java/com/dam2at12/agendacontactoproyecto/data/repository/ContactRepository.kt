package com.dam2at12.agendacontactoproyecto.data.repository

import com.dam2at12.agendacontactoproyecto.data.local.dao.ContactDao
import com.dam2at12.agendacontactoproyecto.data.local.entity.ContactEntity
import com.dam2at12.agendacontactoproyecto.data.remote.datasource.ApiService
import com.dam2at12.agendacontactoproyecto.data.remote.model.ContactPicture
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import kotlin.collections.first

class ContactRepository @Inject constructor(
    // Inyectamos la fuente de datos remota (API)
    // El repositorio NO crea la API, solo la usa
    private val apiService: ApiService,
    private val contactDao: ContactDao
)  {

    // Obtiene una imagen de la Api. La llamaremos desde el viewmodel cuando creemos la entidad
    suspend fun getNewPicture(): ContactPicture {

        // Llamamos a la API para obtener la imagen aleatoria y la devolvemos

        val picture = apiService.getContact().results.first()

        return picture
    }

    //Función para insertar un contacto en la BD
    suspend fun insertarContact(contactEntity: ContactEntity){
        contactDao.insertarContacto(contactEntity)
    }

    //Función para eliminar un contacto de la BD
    suspend fun eliminarContact(contactEntity: ContactEntity) {
        contactDao.eliminarContacto(contactEntity)
    }

    //Función para actualizar un contacto de la BD
    suspend fun actualizarContacto(contactEntity: ContactEntity)
    {
        return contactDao.actualizarContacto(contactEntity)
    }

    //Función para obtener todos los contactos
    fun obtenerTodosLosContactos(): Flow<List<ContactEntity>> {
        return contactDao.obtenerTodosLosContactos()
    }

    //Función para obtener un contacto mediante su id
    fun obtenerUnContacto(id: Int): ContactEntity?
    {
        return contactDao.obtenerUnContacto(id)
    }
}