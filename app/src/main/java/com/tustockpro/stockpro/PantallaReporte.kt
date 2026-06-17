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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
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

/**
 * Pantalla 4: Reporte Financiero
 */
@Composable
fun PantallaReporte(
    navController: NavController,
    viewModel: StockViewModel
) {
    // Obtener métricas del ViewModel
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

        // Métricas
        Row(modifier = Modifier.fillMaxWidth()) {
            CardMetrica("❌\nCero", productosStockCero.toString(), Color(0xFFFFEBEE))
            Spacer(modifier = Modifier.width(12.dp))
            CardMetrica("⚠\nCrítico", productosEnRiesgo.toString(), Color(0xFFFFF9C4))
        }

        Spacer(modifier = Modifier.height(12.dp))

        Row(modifier = Modifier.fillMaxWidth()) {
            CardMetrica("📦\nStock", stockTotal.toString(), Color(0xFFC8E6C9))
            Spacer(modifier = Modifier.width(12.dp))
            CardMetrica("📊\nTotal", totalProductos.toString(), Color(0xFFBBDEFB))
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Info adicional
        Card(modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = "INFORMACIÓN ADICIONAL", style = MaterialTheme.typography.labelLarge)
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

        Button(onClick = { navController.popBackStack() }, modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)) {
            Text("← Volver")
        }
    }
}

@Composable
fun CardMetrica(titulo: String, valor: String, color: Color) {
    Card(modifier = Modifier
        .weight(1f)
        .height(140.dp),
        colors = CardDefaults.cardColors(containerColor = color),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = androidx.compose.foundation.layout.Arrangement.Center
        ) {
            Text(text = titulo, style = MaterialTheme.typography.labelSmall,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center, fontSize = 14.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = valor, style = MaterialTheme.typography.headlineSmall,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center)
        }
    }
}
