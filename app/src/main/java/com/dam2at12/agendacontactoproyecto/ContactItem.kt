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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dam2at12.agendacontactoproyecto.data.local.entity.ContactEntity


//Función reutilizable para poder acceder a los detalles de cada contacto
@Composable
fun ContactItem(contactEntity: ContactEntity, modifier: Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row {
            Image(
                painter = painterResource(contactEntity.imagen),
                contentDescription = contactEntity.name,
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
            )
            Column(modifier = Modifier.padding(start = 12.dp)) {
                Text(
                    text = contactEntity.name,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = contactEntity.info,
                    fontSize = 14.sp
                )
            }
        }
        Box {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = "Ver contacto"
            )
        }
    }
}
