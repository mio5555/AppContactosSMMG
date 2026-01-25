package com.dam2at12.agendacontactoproyecto.data

data class Contact(
    val id: Int,
    val name: String,
    val phone: String,
    val email: String,
    val info: String,
    val imagen: Int
)

/*La variable de la  imagen es tipo int ya que no guarda la imagen en si,
si no una referencia al ID que asigna Android a cada recurso drawable.
 */