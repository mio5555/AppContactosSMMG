package com.dam2at12.agendacontactoproyecto.data

/*Creamos una variable compartida para guardar el contacto al que queramos ir.
El valor tiene que ser nullable, ya que no hay valor por defecto al iniciar la app.
 */
object SelectedContact {
    var contact: Contact? = null
}