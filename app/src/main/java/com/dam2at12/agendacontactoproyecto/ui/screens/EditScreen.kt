package com.dam2at12.agendacontactoproyecto.ui.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.dam2at12.agendacontactoproyecto.navigation.Screen
import com.dam2at12.agendacontactoproyecto.ui.viewmodel.ContactViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditScreen(navController: NavHostController, id: Int) {

    Log.d("ContactoViewModel", "Hemos entrado a detallescreen y el id es $id")
    val viewModel: ContactViewModel = hiltViewModel()

    val contactoSeleccionado by viewModel.contactoSeleccionado.collectAsState(initial = null)


    Log.d("ContactoViewModel", "Hemos recogido el contactoSeleccionado desde edit screen")

    //LaunchedEffect se va a ejecutar solo una vez al entrar en la pantalla para obtener el contacto seleccionado por id
    LaunchedEffect(Unit) {

        Log.d("ContactoViewModel", "Entramos en launchedeffect antes de buscar contacto")
        viewModel.buscarContact(id) //Obtendrá  el contacto a través del viewModel
        Log.d("ContactoViewModel", "Entramos en launchedeffect despues de buscar contacto")
    }

    //Mostramos los detalles del contacto selecionado
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Editar el contacto") })
        }
    ) { paddingValues ->

        contactoSeleccionado?.let {
            //Si no es nulo recogera los campos

            var name by rememberSaveable { mutableStateOf(contactoSeleccionado!!.name) }
            var phone by rememberSaveable { mutableStateOf(contactoSeleccionado!!.phone) }
            var email by rememberSaveable { mutableStateOf(contactoSeleccionado!!.email) }
            var info by rememberSaveable { mutableStateOf(contactoSeleccionado!!.info) }

            val context = LocalContext.current


            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(24.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = "Introduce los nuevos datos.",
                    fontSize = 24.sp,
                    modifier = Modifier.padding(bottom = 32.dp)
                )

                //TextField para el nombre
                OutlinedTextField(
                    value = name,
                    onValueChange = { newText: String ->
                        name = newText
                    },
                    label = { Text("Nombre") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = phone,
                    onValueChange = { newText: String ->
                        phone = newText
                    },
                    label = { Text("Telefono") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = email,
                    onValueChange = { newText: String ->
                        email = newText
                    },
                    label = { Text("Correo") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = info,
                    onValueChange = { newText: String ->
                        info = newText
                    },
                    label = { Text("Descripción") },
                    singleLine = false,
                    modifier = Modifier.fillMaxWidth()
                )


                Spacer(modifier = Modifier.height(24.dp))

                //Botón de añadir contacto
                Button(
                    onClick = {
                                viewModel.actualizarContact(
                                    contactoSeleccionado!!.copy(
                                        name= name,
                                        phone = phone,
                                        email = email,
                                        info = info,
                                    )
                                )

                        Toast.makeText(
                            context, //Contexto donde mostrar el Toast. En este caso: pantalla para agregar tarea
                            "Contacto actualizado correctamente", //Mensaje del toast
                            Toast.LENGTH_SHORT, //Duración del toast (corta aprox. 2s o larga aprox. 4 s)
                        ).show() //Muestra el toast en pantalla
                        navController.navigate("${Screen.DetailScreen.ruta}/${contactoSeleccionado!!.id}")


                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Actualizar contacto")
                }
            }
                ?: Box( //Si el contacto es null, entraremos por aquí y se mostrará un círculo de carga
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
        }
    }
}