package com.dam2at12.agendacontactoproyecto.networkhelper

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
class NetworkHelper @Inject constructor(
    @ApplicationContext private val context: Context
) {
// Flow (actualiza cambios en tiempo real) que emite true/false según haya internet.
fun isConnectedFlow(): Flow<Boolean> {
    return context.observeConnectivityAsFlow()
        .map { it == ConnectionStatus.Available } //Mapeo a true / false sengún estado
}
    // Consulta rápida de estado (por si alguna vez la necesitas)
    fun isConnectedNow(): Boolean {
        return context.currentConnectivityState ==
                ConnectionStatus.Available
    }
}
