package com.dam2at12.agendacontactoproyecto.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.dam2at12.agendacontactoproyecto.data.SelectedContact

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactDetailScreen(navController: NavHostController) {

    //Cogemos el contacto seleccionado de la variable compartida
    val contact = SelectedContact.contactEntity

    /* Mensaje de error por si no hay contacto seleccionado.
       (Esto no debería poder pasar, pero cubrimos el error por si acaso).
     */
    if (contact == null) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Detalle del contacto") },
                    navigationIcon = {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                        }
                    }
                )
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "No se ha seleccionado ningún contacto.",
                    fontSize = 16.sp
                )
            }
        }
        return
    }

    //Mostramos los detalles del contacto selecionado
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalle del contacto") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            //Imagen del contacto
            Image(
                painter = painterResource(contact.imagen),
                contentDescription = contact.name,
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
            )

            // Información principal
            Text(text = "Nombre: ${contact.name}", fontSize = 18.sp)
            Text(text = "Teléfono: ${contact.phone}", fontSize = 16.sp)
            Text(text = "Email: ${contact.email}", fontSize = 16.sp)
            Text(text = "Información del contacto: ${contact.info}", fontSize = 16.sp)
        }
    }

    /* Mensaje de error por si no hay contacto seleccionado.
       (Esto no debería poder pasar, pero cubrimos el error por si acaso).
     */
    if (contact == null) {
        Scaffold(
            topBar = { TopAppBar(title = { Text("Detalle del contacto")}) }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .padding(24.dp)
            ) {
                Text(
                    text = "No se ha seleccionado ningún contacto.",
                    fontSize = 16.sp
                )
            }
        }
        return
    }
}
