package com.dam2at12.agendacontactoproyecto.networkhelper

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import androidx.core.content.ContextCompat.getSystemService
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow

// 1) CONSULTA RÁPIDA: “¿AHORA MISMO HAY INTERNET?”
// Usamos una extensión de la clase Context que es como tener una función: fun getConnectivityState(context: Context): ConnectionStatus{ ... }
// pero más cómodo de usar (por ejemplo: Scontext.currentConnectivityState)
// Cada vez que lo usas, get() vuelve a calcular el estado.
val Context.currentConnectivityState: ConnectionStatus
    get() {
// Pedimos a Android el "servicio de conectividad". Es el objeto que sabe qué redes están activas.
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
// Calculamos y devolvemos el estado actual usando la función auxiliar definida abajo
                return getCurrentConnectivityStatus(connectivityManager)
    }

// 2) FUNCIÓN AUXILIAR: comprueba si la red activa tiene internet
private fun getCurrentConnectivityStatus(
    connectivityManager: ConnectivityManager
): ConnectionStatus {
// 1. Obtenemos la red activa (la que se está usando ahora)
    val activeNetwork = connectivityManager.activeNetwork
// 2. Obtenemos las “capacidades” de esa red (si tiene internet, etc.)
    val capabilities =
        connectivityManager.getNetworkCapabilities(activeNetwork)
// 3. Verificamos si tiene internet (true/false). Usamos ?. porque a veces Android no puede
// darnos las capacidades: or ejemplo si no hay red activa (activeNetwork es null) o si la red
// está cambiando justo en ese momento (Wi-Fi ↔ datos) y todavía no se ha actualizado la info.
    val connected = capabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true
    return if (connected) ConnectionStatus.Available else
        ConnectionStatus.Unavailable
}

// 3) OBSERVAR CAMBIOS: emitir Available / Unavailable cuando cambia la conexión
fun Context.observeConnectivityAsFlow() = callbackFlow {
    val connectivityManager =
        getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

// 1) Creamos el callback que Android necesita para avisarnos  de cambios.
// Android quiere un OBJETO o instancia de tipo NetworkCallback (no una función suelta), por eso
// usamos: object : ConnectivityManager.NetworkCallback() { ...} (es como crear una miniclase
// al vuelo sin ponerle nombre)
    val callback = object : ConnectivityManager.NetworkCallback() {
// Android llama al metodo onAvailable cuando vuelve la conexión.
    override fun onAvailable(network: Network) {
        // trySend = intenta enviar el valor al Flow sin quedarse esperando.
        trySend(ConnectionStatus.Available)
    }
//Android llama al metodo Unavailable cuando se pierde la conexión.
    override fun onLost(network: Network) {
        trySend(ConnectionStatus.Unavailable)
    }
}
// 2) Registramos el callback para la red “por defecto” (la principal que usa Android).
// Al cambiar Wi-Fi ↔ datos, así suele haber menos “saltos” y menos parpadeo en la UI.
    connectivityManager.registerDefaultNetworkCallback(callback)
// 3) Enviamos el estado inicial (para no esperar al primer cambio).
    trySend(currentConnectivityState)
// 4) Limpieza: cuando se deja de escuchar el Flow, quitamos el callback.
// Así Android no sigue avisando cuando ya no hace falta.
    awaitClose {
    connectivityManager.unregisterNetworkCallback(callback)
    }
}
