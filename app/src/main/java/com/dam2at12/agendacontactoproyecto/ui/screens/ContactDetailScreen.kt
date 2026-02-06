package com.dam2at12.agendacontactoproyecto.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.dam2at12.agendacontactoproyecto.data.SelectedContact
import com.dam2at12.agendacontactoproyecto.ui.viewmodel.ContactViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactDetailScreen(navController: NavHostController, id: Int) {

    val viewModel: ContactViewModel = hiltViewModel()


    val contactoSeleccionado by viewModel.contactoSeleccionado.collectAsState(initial = null)

    //LaunchedEffect se va a ejecutar solo una vez al entrar en la pantalla para obtener el contacto seleccionado por id
    LaunchedEffect(Unit) {
        viewModel.buscarContact(id) //Obtendrá  el contacto a través del viewModel
    }

    //Cogemos el contacto seleccionado de la variable compartida
    val contact = SelectedContact.contactEntity

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
    ) {
        paddingValues ->


        contactoSeleccionado?.let { //Si el contacto no es nulo, se mostrará una columna
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                AsyncImage(
                    model = contactoSeleccionado!!.imagen, //!! lanza error si contactoSeleccionado es null, pero si estamos dentro del let nunca será null
                    contentDescription = contactoSeleccionado!!.name,
                    modifier = Modifier
                        .weight(1f)
                        .padding(8.dp)
                        .size(120.dp)
                        .clip(shape = CircleShape),
                    contentScale = ContentScale.Crop
                )
                Text(text = "Nombre: ${contactoSeleccionado!!.name}", fontSize = 18.sp)
                Text(text = "Teléfono: ${contactoSeleccionado!!.phone}", fontSize = 16.sp)
                Text(text = "Email: ${contactoSeleccionado!!.email}", fontSize = 16.sp)
                Text(text = "Información del contacto: ${contactoSeleccionado!!.info}", fontSize = 16.sp)
            }
        } ?: Box( //Si el contacto es null, entraremos por aquí y se mostrará un círculo de carga
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }
}
