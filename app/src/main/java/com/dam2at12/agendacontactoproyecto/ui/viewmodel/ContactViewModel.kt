package com.dam2at12.agendacontactoproyecto.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dam2at12.agendacontactoproyecto.data.local.entity.ContactEntity
import com.dam2at12.agendacontactoproyecto.data.repository.ContactRepository
import com.dam2at12.agendacontactoproyecto.networkhelper.NetworkHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ContactViewModel @Inject constructor(
    private val contactRepository: ContactRepository,
    private val networkHelper: NetworkHelper
) : ViewModel() {

    //Estado mutable con la lista de contactos
    private val contactosMutables = MutableStateFlow<List<ContactEntity>>(emptyList())

    //Estado mutable con el contacto seleccionado
    private val contactoSeleccionadoMutable = MutableStateFlow<ContactEntity?>(null)

    //Estado mutable para saber si hay conexión a internet
    private val _isConnected = MutableStateFlow(false)

    //Estados observables para la UI (solo lectura)
    val contactos: StateFlow<List<ContactEntity>> = contactosMutables
    val contactoSeleccionado: StateFlow<ContactEntity?> = contactoSeleccionadoMutable
    val isConnected: StateFlow<Boolean> = _isConnected

    //Inicializamos el ViewModel y cargamos los contactos
    init {
        viewModelScope.launch {

            //Comprobamos si hay conexión a internet
            _isConnected.value = networkHelper.isConnectedNow()

            //Si no hay conexión, no hacemos la llamada
            if (!_isConnected.value) {
                Log.d("ContactoViewModel", "No hay conexión a internet")
                return@launch
            }

            //Obtenemos todos los contactos desde el repositorio
            try {
                contactRepository.obtenerTodosLosContactos().collect {
                    contactosMutables.value = it
                }
            } catch (e: Exception) {
                Log.e("ContactoViewModel", "Error al obtener los contactos", e)
            }
        }
    }

    //Carga de contacto por API y lo inserta en la base de datos
    fun addContact(nombre: String, telefono: String, correo: String, informacion: String) {
        viewModelScope.launch {
            try {

                //Variable donde se guardará la imagen
                val picture: String

                //Intentamos obtener la imagen desde la API
                picture = try {
                    val foto = contactRepository.getNewPicture()

                    //Si la API no devuelve imagen usamos la por defecto
                    foto.thumbnail ?: "res/drawable/avatardefault.jpg"
                } catch (e: Exception) {
                    //Error si se cae la API
                    "res/drawable/avatardefault.jpg"
                }

                //El id se genera solo y la imagen es la cadena del thumbnail
                val contacto = ContactEntity(
                    name = nombre,
                    phone = telefono,
                    email = correo,
                    info = informacion,
                    imagen = picture
                )

                //Escribimos un log con los datos del contacto
                Log.d("ContactoViewModel", contacto.toString())

                //Insertamos el contacto en la base de datos
                contactRepository.insertarContact(contacto)

            } catch (e: Exception) {
                //Si ocurre algún error, no se crea el contacto
                Log.e("ContactoViewModel", "Error al crear el contacto", e)
            }
        }
    }

    //Eliminamos contacto de la BBDD a través del repositorio
    fun eliminarContact(contacto: ContactEntity) {
        viewModelScope.launch {
            contactRepository.eliminarContact(contacto)
        }
    }

    //Actualizamos un contacto en la base de datos
    fun actualizarContact(contacto: ContactEntity) {
        viewModelScope.launch {
            contactRepository.actualizarContacto(contacto)
        }
    }

    //Busca un único contacto por id y lo guarda en una variable de estado
    fun buscarContact(id: Int) {
        viewModelScope.launch {
            Log.d("ContactoViewModel", "Buscando contacto con id $id")

            val contacto = contactRepository.obtenerUnContacto(id)

            Log.d(
                "ContactoViewModel",
                "Contacto encontrado: ${contacto?.name}"
            )

            contactoSeleccionadoMutable.value = contacto
        }
    }
}
