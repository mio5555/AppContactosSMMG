package com.dam2at12.agendacontactoproyecto.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.dam2at12.agendacontactoproyecto.data.local.entity.ContactEntity
import kotlinx.coroutines.flow.Flow

//Interfaz con los métodos de sql de la entidad Contact. Sos suspendidas, aka sincronizadas
@Dao
interface ContactDao {

    //Insertar un contacto
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarContacto(contacto: ContactEntity)

    //Select de todos los contactos
    @Query("SELECT * FROM contactos ORDER BY id DESC")
    fun obtenerTodosLosContactos(): Flow<List<ContactEntity>>

    //Eliminar un contacto
    @Delete
    suspend fun eliminarContacto(contacto: ContactEntity)

    //Actualizar
    @Update
    suspend fun actualizarContacto(contacto: ContactEntity)

    //Seleccionar 1 por id
    @Query("SELECT * FROM contactos WHERE id = :num")
    fun obtenerUnContacto(num: Int): ContactEntity?

}