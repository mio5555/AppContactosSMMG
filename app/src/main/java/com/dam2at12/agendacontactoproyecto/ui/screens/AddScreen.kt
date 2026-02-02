package com.dam2at12.agendacontactoproyecto.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.dam2at12.agendacontactoproyecto.navigation.Screen
import com.dam2at12.agendacontactoproyecto.ui.viewmodel.ContactViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddScreen(navController: NavHostController) {

    val viewModel: ContactViewModel = hiltViewModel()

    /*Creamos variables para las credenciales y del toggle de visibilidad de la contraseña
    y guardamos sus estados con rememberSaveable*/
    var name by rememberSaveable { mutableStateOf("") }
    var phone by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    var info by rememberSaveable { mutableStateOf("") }


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
                onValueChange = { newText:String ->
                    name = newText
                },
                label = { Text("Nombre") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = phone,
                onValueChange = { newText:String ->
                    phone = newText
                },
                label = { Text("Telefono") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = email,
                onValueChange = { newText:String ->
                    email = newText
                },
                label = { Text("Correo") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = info,
                onValueChange = { newText:String ->
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
                    viewModel.addContact(name,phone,email,info)

                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Añadir contacto")
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