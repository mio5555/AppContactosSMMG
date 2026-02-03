package com.dam2at12.agendacontactoproyecto.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.dam2at12.agendacontactoproyecto.R
import com.dam2at12.agendacontactoproyecto.data.local.entity.ContactEntity
import com.dam2at12.agendacontactoproyecto.data.SelectedContact
import com.dam2at12.agendacontactoproyecto.navigation.Screen
import com.dam2at12.agendacontactoproyecto.ui.viewmodel.ContactViewModel
import com.example.contactsapp.ui.components.ContactItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavHostController) {

    val viewModel: ContactViewModel = hiltViewModel()

    val contactos by viewModel.contactos.collectAsState(initial = emptyList())

//    //Guardamos los contactos en una variable tipo lista
//    val contactEntities = listOf(
//        ContactEntity(1, "Juan Pérez", "601 234 567", "juan@email.com", "Mi jefe", R.drawable.juan),
//        ContactEntity(2, "María López", "607 654 321", "maria@email.com", "Compañera trabajo", R.drawable.maria),
//        ContactEntity(3, "Carlos (Abuelo)", "666 777 888", "carlos@email.com", "Mi abuelo", R.drawable.carlos)
//    )
    Scaffold(
        topBar = { TopAppBar(title = { Text("Agenda de contactos") },
            /*Botón para "cerrar sesión" y poder volver a loggear.
            (Actualmente este botón da error a veces, si navegamos muy rápido o cosas así, pero normalmente funciona.
            Parece un error del emulador más que del código.)
             */
            navigationIcon = {
                IconButton(onClick = {
                    //Limpiamos cualquier contacto que podamos haber selecionado al entrar a ver detalles.
                    //SelectedContact.contactEntity = null - ya no usamos SelectedContact
                    /*Navegamos a la pantalla de login y limpiamos el backstack de la memoria para no permitir volver
                     al home dándole a la flecha de atrás del móvil, teniendo si o si que "iniciar sesión" de nuevo.
                     Al igual que en la loginscreen, usamos el popUpTo(0) para asegurarnos de que se limpia entero.
                    */
                    navController.navigate(Screen.LoginScreen.ruta){ popUpTo(0) { inclusive = true }}
                }) {
                    Icon(Icons.Default.ExitToApp, contentDescription = "Cerrar sesión")
                }
            }) },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(Screen.AddScreen.ruta)
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Agregar Contacto"
                )
            }
        }
    ) { paddingValues ->
        LazyColumn(modifier = Modifier.padding(paddingValues)) {
            //La función items de LazyColumn actúa como un forEach para los elementos de nuestra lista
            items(contactos) { contact ->
                ContactItem(
                    contactEntity = contact,
                    modifier = Modifier.clickable {
                        SelectedContact.contactEntity = contact
                        navController.navigate("contact")
                    }
                )
            }
        }
    }
}