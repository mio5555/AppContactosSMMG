package com.example.contactsapp.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.dam2at12.agendacontactoproyecto.data.local.entity.ContactEntity
import com.dam2at12.agendacontactoproyecto.R



//Función reutilizable para poder acceder a los detalles de cada contacto
@Composable
fun ContactItem(contactEntity: ContactEntity, modifier: Modifier) {

    val imagen = contactEntity.imagen

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


    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row {
            //Imagen del API
            AsyncImage(
                model = model,
                contentDescription = contactEntity.name,
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp)
                    .size(70.dp)
                    .clip(shape = CircleShape),
                contentScale = ContentScale.Crop
            )
            Column(modifier = Modifier
                .weight(3f)
                .padding(start = 12.dp),
                ) {
                Row {
                    Text(
                        text = contactEntity.name,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                Row {
                    Text(
                        text = contactEntity.phone,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal
                    )
                }
                Row {
                    Column {
                        Row {
                            Text(
                                text = contactEntity.email,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Normal
                            )
                        }
                    }
                    Row {
                        Text(
                            text = contactEntity.info,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Normal
                        )
                    }
                }


            }
        }

    }
}
