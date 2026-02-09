package com.dam2at12.agendacontactoproyecto.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.dam2at12.agendacontactoproyecto.navigation.Screen
import com.dam2at12.agendacontactoproyecto.ui.viewmodel.ContactViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddScreen(navController: NavHostController) {

    val viewModel: ContactViewModel = hiltViewModel()

    //Recogemos el estado de conexión desde el ViewModel (aseguramos que sea Boolean)
    val isConnected by viewModel.isConnected.collectAsState(initial = false)

    //Variables para los datos del contacto
    var name by rememberSaveable { mutableStateOf("") }
    var phone by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    var info by rememberSaveable { mutableStateOf("") }

    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Añadir contacto a la agenda") })
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "Introduce los datos del contacto a añadir.",
                fontSize = 24.sp,
                modifier = Modifier.padding(bottom = 32.dp)
            )

            //TextField para el nombre
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Nombre") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            //TextField para el teléfono
            OutlinedTextField(
                value = phone,
                onValueChange = { phone = it },
                label = { Text("Telefono") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            //TextField para el correo
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Correo") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            //TextField para la descripción
            OutlinedTextField(
                value = info,
                onValueChange = { info = it },
                label = { Text("Descripción") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))

            //Botón de añadir contacto
            Button(
                //El botón se desactiva automáticamente si no hay conexión
                enabled = isConnected,
                onClick = {

                    //Si no hay conexión, mostramos un mensaje y no hacemos nada
                    if (!isConnected) {
                        Toast.makeText(
                            context,
                            "No hay conexión a internet",
                            Toast.LENGTH_SHORT
                        ).show()
                        return@Button
                    }

                    //Si hay conexión, añadimos el contacto
                    scope.launch {
                        viewModel.addContact(name, phone, email, info)

                        Toast.makeText(
                            context,
                            "Contacto agregado correctamente",
                            Toast.LENGTH_SHORT
                        ).show()

                        //Volvemos a la pantalla principal
                        navController.navigate(Screen.HomeScreen.ruta)
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Añadir contacto")
            }

            //Mensaje informativo si no hay conexión
            if (!isConnected) {
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "No hay conexión a internet. No se pueden añadir contactos.",
                    color = MaterialTheme.colorScheme.error,
                    fontSize = 14.sp
                )
            }
        }
    }
}

@Preview
@Composable
private fun AddScreenPreview() {
    val navController = rememberNavController()
    AddScreen(navController)
}
