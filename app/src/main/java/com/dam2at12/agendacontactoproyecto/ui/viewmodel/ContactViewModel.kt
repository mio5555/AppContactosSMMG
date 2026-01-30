package com.dam2at12.agendacontactoproyecto.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dam2at12.agendacontactoproyecto.data.local.entity.ContactEntity
import com.dam2at12.agendacontactoproyecto.data.repository.ContactRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ContactViewModel @Inject constructor(
    private val contactRepository: ContactRepository
) : ViewModel() {

    //Estado mutable con la lista de contactos
    private val contactosMutables = MutableStateFlow<List<ContactEntity>>(emptyList())

    //Estado observable no modificable
    val contactos: StateFlow<List<ContactEntity>> = contactosMutables

    //Inicializamos el ViewModel y guardamos todos los contactos en contactosMutables
    init {
        viewModelScope.launch {
            contactRepository.obtenerTodosLosContactos().collect {
                contactosMutables.value = it
            }
        }
    }

    //Carga de contacto por API
    fun addContact(nombre: String, telefono: String, correo: String, informacion: String) {

        viewModelScope.launch {
            try {
                //Obtener la imagen de la API
                val foto = contactRepository.getNewPicture()

                //el id se genera solo y la imagen no es la contactPicture sino su thumbnail(la cadena)
                val contacto= ContactEntity(
                    name= nombre,
                    phone = telefono,
                    email = correo,
                    info = informacion,
                    imagen = foto.thumbnail)//

                //Escribimos un log con los atributos del contacto y la etiqueta del ViewModel
                Log.d("ContactoViewModel", contacto.toString())

                //Añadimos el contacto a la base de datos
                insertarContact(contacto)
            } catch (e: Exception) {
                // Si algo falla, el estado sigue siendo el anterior

                Log.e("ContactoViewModel", "Error al crear el contacto: $e")
            }
        }

    }

    //Insertamos contacto en la BBDD a través del repositorio
    fun insertarContact(contacto: ContactEntity) {
        viewModelScope.launch {
            contactRepository.insertarContact(contacto)
        }
    }

    //Eliminamos contacto en la BBDD a través del repositorio
    fun eliminarContact(contacto: ContactEntity) {
        viewModelScope.launch {
            contactRepository.eliminarContact(contacto)
        }
    }
    //Actualiza un contacto
    fun actualizarContact(contacto: ContactEntity)
    {
        viewModelScope.launch {
            contactRepository.actualizarContacto(contacto)
        }

    }
    //Busca un contacto por id - NO FUNCIONA

    //IDEA PARA FUTURO - CREAR VAL contactoSeleccionado y que la funcion de buscarContacto actualice ese contacto como StateFlow<ContactEntity>
//      si lo encuentra. Luego en la vista se recoge como ContactEntity

//    fun buscarContact(id: Int): ContactEntity?
//    {
//        viewModelScope.launch {
//            //No se si lo de abajo funciona asi que lo comento
//            // val contacto : StateFlow<ContactEntity> = contactRepository.obtenerUnContacto(id) as StateFlow<ContactEntity>
//            val contactoMutable = MutableStateFlow<ContactEntity?>(null)
//            val contacto : StateFlow<ContactEntity?> = contactoMutable
//            contactRepository.obtenerUnContacto(id).collect {
//                val contacto= it
//            }
//            return contacto
//
//        }

}