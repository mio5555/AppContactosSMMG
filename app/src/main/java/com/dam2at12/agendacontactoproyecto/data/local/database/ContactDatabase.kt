package com.dam2at12.agendacontactoproyecto.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dam2at12.agendacontactoproyecto.data.local.dao.ContactDao
import com.dam2at12.agendacontactoproyecto.data.local.entity.ContactEntity

//Clase ContactDatabase - Base de datos con Room

@Database(
    entities = [ContactEntity::class],
    version = 1,
    exportSchema = false //Dejar en falso de momento
)

//Fun abstracta contactar DAO y base de datos

abstract class ContactDatabase : RoomDatabase() {
    abstract fun contactDao(): ContactDao
}