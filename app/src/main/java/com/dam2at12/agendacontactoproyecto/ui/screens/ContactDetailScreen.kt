package com.dam2at12.agendacontactoproyecto.ui.screens

import android.util.Log
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.dam2at12.agendacontactoproyecto.R
import com.dam2at12.agendacontactoproyecto.ui.viewmodel.ContactViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactDetailScreen(navController: NavHostController, id: Int) {

    Log.d("ContactoViewModel", "Hemos entrado a detallescreen y el id es $id")
    val viewModel: ContactViewModel = hiltViewModel()

    val contactoSeleccionado by viewModel.contactoSeleccionado.collectAsState(initial = null)
    Log.d("ContactoViewModel", "Hemos recogido el contactoSeleccionado")

    //LaunchedEffect se va a ejecutar solo una vez al entrar en la pantalla para obtener el contacto seleccionado por id
    LaunchedEffect(Unit) {

        Log.d("ContactoViewModel", "Entramos en launchedeffect antes de buscar contacto")
        viewModel.buscarContact(id) //Obtendrá  el contacto a través del viewModel
        Log.d("ContactoViewModel", "Entramos en launchedeffect despues de buscar contacto")
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
    ) {
        paddingValues ->


        contactoSeleccionado?.let { //Si el contacto no es nulo, se mostrará una columna
            //Comprobamos que la imagen sea una url de la api y le asignamos una por defecto si es null
            val imagen = contactoSeleccionado!!.imagen

            val model = if (imagen.startsWith("http")) {
                // Es una URL → Coil la carga directamente
                imagen
            } else {
                // Es un drawable local → convertir nombre a ID
                val resId = LocalContext.current.resources.getIdentifier(
                    imagen,
                    "drawable",
                    LocalContext.current.packageName
                )
                if (resId != 0) resId else R.drawable.avatardefault
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                AsyncImage(
                    model = model, //!! lanza error si contactoSeleccionado es null, pero si estamos dentro del let nunca será null
                    contentDescription = contactoSeleccionado!!.name,
                    modifier = Modifier
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
