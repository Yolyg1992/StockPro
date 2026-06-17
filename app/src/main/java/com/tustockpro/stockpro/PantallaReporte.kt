package com.tustockpro.stockpro

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.tustockpro.stockpro.viewmodel.StockViewModel

// Pantalla de Reporte Financiero
@Composable
fun PantallaReporte(
    navController: NavController,
    viewModel: StockViewModel
) {
    // Obtener metricas del ViewModel
    val totalInventario = viewModel.calcularValorTotalInventario()
    val productosStockCero = viewModel.obtenerProductosConStockCero()
    val productosEnRiesgo = viewModel.obtenerCantidadProductosEnRiesgo()
    val stockTotal = viewModel.obtenerStockTotal()
    val precioPromedio = viewModel.obtenerPrecioPromedio()
    val totalProductos = viewModel.getProductos().size

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Text(text = "Reporte Financiero", style = MaterialTheme.typography.headlineMedium, fontSize = 24.sp)

        Spacer(modifier = Modifier.height(24.dp))

        // Capital invertido
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color(0xFF1976D2), shape = MaterialTheme.shapes.large),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF1976D2)),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "CAPITAL INVERTIDO", style = MaterialTheme.typography.labelLarge,
                    color = Color.White, fontSize = 12.sp)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "$${String.format("%.2f", totalInventario)}",
                    style = MaterialTheme.typography.displayLarge, color = Color.White, fontSize = 48.sp)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Metricas
        Row(modifier = Modifier.fillMaxWidth()) {
            CardMetrica(modifier = Modifier.fillMaxWidth(0.5f).padding(end = 6.dp), titulo = "Cero", valor = productosStockCero.toString(), color = Color(0xFFFFEBEE))
            CardMetrica(modifier = Modifier.fillMaxWidth().padding(start = 6.dp), titulo = "Critico", valor = productosEnRiesgo.toString(), color = Color(0xFFFFF9C4))
        }

        Spacer(modifier = Modifier.height(12.dp))

        Row(modifier = Modifier.fillMaxWidth()) {
            CardMetrica(modifier = Modifier.fillMaxWidth(0.5f).padding(end = 6.dp), titulo = "Stock Total", valor = stockTotal.toString(), color = Color(0xFFC8E6C9))
            CardMetrica(modifier = Modifier.fillMaxWidth().padding(start = 6.dp), titulo = "Total Productos", valor = totalProductos.toString(), color = Color(0xFFBBDEFB))
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Info adicional
        Card(modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = "INFORMACION ADICIONAL", style = MaterialTheme.typography.labelLarge)
                Spacer(modifier = Modifier.height(12.dp))

                Row(modifier = Modifier.fillMaxWidth()) {
                    Text("Precio Promedio:")
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "$${String.format("%.2f", precioPromedio)}",
                        style = MaterialTheme.typography.titleSmall)
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Botones: Volver y Salir
        Row(modifier = Modifier.fillMaxWidth()) {
            Button(
                onClick = { navController.popBackStack() },
                modifier = Modifier
                    .weight(1f)
                    .height(48.dp)
                    .padding(end = 4.dp)
            ) {
                Text("Volver")
            }

            Button(
                onClick = {
                    // Cerrar la aplicacion
                    android.os.Process.killProcess(android.os.Process.myPid())
                },
                modifier = Modifier
                    .weight(1f)
                    .height(48.dp)
                    .padding(start = 4.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFF44336)
                )
            ) {
                Text("Salir")
            }
        }
    }
}

// Componente reutilizable para metricas
@Composable
fun CardMetrica(modifier: Modifier, titulo: String, valor: String, color: Color) {
    Card(modifier = modifier
        .height(140.dp),
        colors = CardDefaults.cardColors(containerColor = color),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = titulo, style = MaterialTheme.typography.labelSmall,
                textAlign = TextAlign.Center, fontSize = 14.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = valor, style = MaterialTheme.typography.headlineSmall,
                textAlign = TextAlign.Center)
        }
    }
}