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

@Composable
fun PantallaLogin(
    navController: NavController,
    viewModel: StockViewModel
) {
    val nombreState = remember { mutableStateOf("") }
    val isNombreValido = viewModel.validarNombre(nombreState.value)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Logo gráfico de inventario
        LogoInventario()

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "StockPro",
            style = MaterialTheme.typography.headlineLarge,
            fontSize = 32.sp
        )

        Text(
            text = "Gestión de Inventario",
            style = MaterialTheme.typography.bodyLarge
        )
        
        Spacer(modifier = Modifier.height(40.dp))

        Text(
            text = "Ingrese su nombre",
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
            text = "${nombreState.value.length}/3 caracteres",
            style = MaterialTheme.typography.bodySmall,
            color = if (isNombreValido)
                MaterialTheme.colorScheme.primary
            else
                MaterialTheme.colorScheme.error
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {
                navController.navigate("catalogo/${nombreState.value}")
            },
            enabled = isNombreValido,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Ingresar", fontSize = 16.sp)
        }
    }
}

// Gráfica visual del logo de inventario con estantes coloridos
@Composable
fun LogoInventario() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "📦 INVENTARIO 📦", style = MaterialTheme.typography.labelSmall)
        Spacer(modifier = Modifier.height(8.dp))

        // Estantes de inventario
        repeat(3) { row ->
            Row(
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(24.dp)
                    .padding(vertical = 2.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                repeat(4) { col ->
                    val color = when {
                        row == 0 && col == 1 -> Color(0xFF4CAF50) // Verde
                        row == 1 && col == 2 -> Color(0xFFF44336) // Rojo
                        row == 2 && col == 0 -> Color(0xFF2196F3) // Azul
                        row == 2 && col == 3 -> Color(0xFFFF9800) // Naranja
                        else -> Color(0xFFFFC107)
                    }
                    Card(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight(),
                        colors = CardDefaults.cardColors(containerColor = color)
                    ) {}
                }
            }
        }
    }
}
