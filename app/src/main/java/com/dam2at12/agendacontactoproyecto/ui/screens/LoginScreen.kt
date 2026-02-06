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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.dam2at12.agendacontactoproyecto.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(navController: NavHostController) {

    /*Creamos variables para las credenciales y del toggle de visibilidad de la contraseña
    y guardamos sus estados con rememberSaveable*/
    var username by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var showPassword by rememberSaveable { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Login") })
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
                text = "Bienvenido/a, introduce tus datos para continuar.",
                fontSize = 24.sp,
                modifier = Modifier.padding(bottom = 32.dp)
            )

            //TextField para el username (admite cualquier cosa)
            OutlinedTextField(
                value = username,
                onValueChange = { newText:String ->
                    username = newText
                },
                label = { Text("Usuario") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            //Textfield para la password (admite cualquier cosa)
            OutlinedTextField(
                value = password,
                onValueChange = { newText:String ->
                    password = newText
                },
                label = { Text("Contraseña") },
                singleLine = true,
                visualTransformation = if (showPassword) VisualTransformation.None
                else PasswordVisualTransformation(),
                /*Icono para alternar la visibilidad de la contraseña, usando la variable
                showPassword que hemos establecido anteriormente.
                 */
                trailingIcon = {
                    IconButton(onClick = { showPassword = !showPassword }) {
                        if (showPassword) {
                            Icon(Icons.Default.Visibility, contentDescription = "Mostrar contraseña")
                        } else {
                            Icon(Icons.Default.VisibilityOff, contentDescription = "Ocultar contraseña")
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))

            //Botón de login (funciona poniendo cualquier valor o incluso sin poner nada)
            Button(
                onClick = {
                    navController.navigate(Screen.HomeScreen.ruta) {
                        /*Con popUpTo limpiamos el backstack para no poder volver a loginscreen con la flecha de atrás
                        del teléfono (Hemos incluido un botón en la topbar de la homescreen para "cerrar sesión",
                        que será la forma de volver a la loginscreen). Usamos popUpTo 0 para asegurarnos de que se limpia entero.
                        */
                        popUpTo(0) { inclusive = true }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Iniciar sesión")
            }
        }
    }
}
