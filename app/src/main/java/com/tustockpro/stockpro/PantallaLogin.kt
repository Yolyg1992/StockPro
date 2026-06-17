package com.tustockpro.stockpro

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.tustockpro.stockpro.viewmodel.StockViewModel

/**
 * Pantalla 1: Login/Entrada al Sistema
 *
 * Funcionalidades:
 * - Solicita nombre del operario (mínimo 3 caracteres)
 * - Valida el nombre para habilitar el botón de ingreso
 * - Navega al catálogo pasando el nombre como argumento
 */
@Composable
fun PantallaLogin(
    navController: NavController,
    viewModel: StockViewModel
) {
    val nombreState = remember { mutableStateOf("") }

    // La validación se realiza usando el ViewModel
    val isNombreValido = viewModel.validarNombre(nombreState.value)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "=== PANTALLA LOGIN ===",
            style = MaterialTheme.typography.headlineMedium
        )
        
        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "Bienvenido a StockPro",
            style = MaterialTheme.typography.headlineLarge,
            fontSize = 32.sp
        )

        Text(
            text = "Sistema de Gestión de Inventario",
            style = MaterialTheme.typography.bodyLarge
        )
        
        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "Ingrese su nombre de operario",
            style = MaterialTheme.typography.bodyLarge
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = nombreState.value,
            onValueChange = { nombreState.value = it },
            label = { Text("Nombre del operario") },
            placeholder = { Text("Mínimo 3 caracteres") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )
        
        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Caracteres ingresados: ${nombreState.value.length}/3",
            style = MaterialTheme.typography.bodySmall,
            color = if (isNombreValido)
                MaterialTheme.colorScheme.primary
            else
                MaterialTheme.colorScheme.error
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {
                // Navegación con el nombre del operario como argumento
                navController.navigate("catalogo/${nombreState.value}")
            },
            enabled = isNombreValido,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Ingresar al Sistema", fontSize = 16.sp)
        }
    }
}
