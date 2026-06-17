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
import androidx.navigation.NavController
import com.tustockpro.stockpro.viewmodel.StockViewModel

@Composable
fun PantallaLogin(
    navController: NavController,
    viewModel: StockViewModel
) {
    val nombreState = remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "=== PANTALLA LOGIN ===",
            style = MaterialTheme.typography.headlineMedium
        )
        
        Text(
            text = "Bienvenido a StockPro",
            style = MaterialTheme.typography.headlineLarge
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        OutlinedTextField(
            value = nombreState.value,
            onValueChange = { nombreState.value = it },
            label = { Text("Nombre del operario") },
            modifier = Modifier.fillMaxWidth()
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Button(
            onClick = {
                // Navegación con nombre del operario
                navController.navigate("catalogo/${nombreState.value}")
            },
            enabled = nombreState.value.length >= 3
        ) {
            Text("Ingresar al Sistema")
        }
    }
}
