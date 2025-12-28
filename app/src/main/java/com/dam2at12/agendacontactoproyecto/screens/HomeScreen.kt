package com.dam2at12.agendacontactoproyecto.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.dam2at12.agendacontactoproyecto.R
import com.dam2at12.agendacontactoproyecto.data.Contact
import com.dam2at12.agendacontactoproyecto.data.Screens
import com.dam2at12.agendacontactoproyecto.data.SelectedContact
import com.example.contactsapp.ui.components.ContactItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavHostController) {

    //Guardamos los contactos en una variable tipo lista
    val contacts = listOf(
        Contact(1, "Juan Pérez", "601 234 567", "juan@email.com", "Mi jefe", R.drawable.juan),
        Contact(2, "María López", "607 654 321", "maria@email.com", "Compañera trabajo", R.drawable.maria),
        Contact(3, "Carlos (Abuelo)", "666 777 888", "carlos@email.com", "Mi abuelo", R.drawable.carlos)
    )
    Scaffold(
        topBar = { TopAppBar(title = { Text("Contactos") }) }
    ) { paddingValues ->
        LazyColumn(modifier = Modifier.padding(paddingValues)) {
            //La función items de LazyColumn actúa como un forEach para los elementos de nuestra lista
            items(contacts) { contact ->
                ContactItem(
                    contact = contact,
                    modifier = Modifier.clickable {
                        SelectedContact.contact = contact
                        navController.navigate("contact")
                    }
                )
            }
        }
    }
}