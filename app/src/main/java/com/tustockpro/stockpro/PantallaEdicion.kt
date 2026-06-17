package com.tustockpro.stockpro

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.tustockpro.stockpro.viewmodel.StockViewModel

/**
 * Pantalla 3: Edición de Stock
 */
@Composable
fun PantallaEdicion(
    navController: NavController,
    viewModel: StockViewModel,
    productoId: Int
) {
    val producto = viewModel.obtenerProducto(productoId)
    
    if (producto == null) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(32.dp))
            Text(text = "❌ Producto no encontrado")
            Spacer(modifier = Modifier.height(32.dp))
            Button(onClick = { navController.popBackStack() }) {
                Text("Volver")
            }
        }
        return
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(text = "Editar Stock", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(24.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer
            )
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = "ID: ${producto.id}", style = MaterialTheme.typography.labelSmall)
                Text(text = producto.nombre, style = MaterialTheme.typography.headlineMedium, fontSize = 22.sp)
                Text(text = producto.descripcion, style = MaterialTheme.typography.bodyMedium)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Precio: $${String.format("%.2f", producto.precio)}",
                    style = MaterialTheme.typography.bodyMedium)
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        Text(text = "Stock Actual", style = MaterialTheme.typography.bodyLarge)
        Spacer(modifier = Modifier.height(16.dp))

        // Stock Display
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = when {
                        producto.stockActual == 0 -> Color(0xFFFFCDD2)
                        producto.stockActual < 5 -> Color(0xFFFFF9C4)
                        else -> Color(0xFFC8E6C9)
                    },
                    shape = MaterialTheme.shapes.medium
                ),
            colors = CardDefaults.cardColors(containerColor = Color.Transparent)
        ) {
            Text(
                text = "${producto.stockActual}",
                style = MaterialTheme.typography.displayMedium,
                fontSize = 64.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )
        }

        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)) {
            Text(
                text = when {
                    producto.stockActual == 0 -> "❌ Agotado"
                    producto.stockActual < 5 -> "⚠ Crítico"
                    else -> "✓ Disponible"
                },
                style = MaterialTheme.typography.bodyMedium,
                color = when {
                    producto.stockActual == 0 -> Color.Red
                    producto.stockActual < 5 -> Color(0xFFF57F17)
                    else -> Color(0xFF388E3C)
                },
                modifier = Modifier.weight(1f)
            )

            Text(
                text = "Total: $${String.format("%.2f", producto.precio * producto.stockActual)}",
                style = MaterialTheme.typography.bodyMedium
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Botones +1 -1
        Row(modifier = Modifier.fillMaxWidth()) {
            Button(
                onClick = {
                    viewModel.actualizarStock(productoId, producto.stockActual + 1)
                },
                modifier = Modifier
                    .weight(1f)
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50))
            ) {
                Text("+1", fontSize = 16.sp)
            }
            
            Spacer(modifier = Modifier.width(8.dp))

            Button(
                onClick = {
                    viewModel.actualizarStock(productoId, producto.stockActual - 1)
                },
                enabled = producto.stockActual > 0,
                modifier = Modifier
                    .weight(1f)
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFF44336),
                    disabledContainerColor = Color.LightGray
                )
            ) {
                Text("-1", fontSize = 16.sp)
            }
        }
        
        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { navController.popBackStack() },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
        ) {
            Text("Guardar y Volver", fontSize = 16.sp)
        }
    }
}
