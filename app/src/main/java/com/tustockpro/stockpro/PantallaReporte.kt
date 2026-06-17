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
 *
 * Funcionalidades:
 * - Calcula y muestra el valor total del inventario (suma de precio × stock)
 * - Muestra el conteo de productos con stock cero
 * - Muestra información adicional derivada del ViewModel
 * - Toda la lógica de negocio está centralizada en el ViewModel
 */
@Composable
fun PantallaReporte(
    navController: NavController,
    viewModel: StockViewModel
) {
    // Todos estos valores se calculan en el ViewModel
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
        Text(
            text = "=== REPORTE FINANCIERO ===",
            style = MaterialTheme.typography.headlineMedium,
            fontSize = 24.sp
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Tarjeta de capital invertido
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = Color(0xFF1976D2),
                    shape = MaterialTheme.shapes.large
                ),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFF1976D2)
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "CAPITAL INVERTIDO TOTAL",
                    style = MaterialTheme.typography.labelLarge,
                    color = Color.White,
                    fontSize = 12.sp
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "$${String.format("%.2f", totalInventario)}",
                    style = MaterialTheme.typography.displayLarge,
                    color = Color.White,
                    fontSize = 48.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Grid de métricas
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            // Productos con stock cero
            Card(
                modifier = Modifier
                    .weight(1f)
                    .height(140.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFFFEBEE)
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = androidx.compose.foundation.layout.Arrangement.Center
                ) {
                    Text(
                        text = "❌",
                        fontSize = 32.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Stock Cero",
                        style = MaterialTheme.typography.labelSmall,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = "$productosStockCero",
                        style = MaterialTheme.typography.headlineSmall,
                        color = Color.Red,
                        textAlign = TextAlign.Center
                    )
                }
            }

            Spacer(modifier = Modifier.width(12.dp))

            // Productos en riesgo crítico
            Card(
                modifier = Modifier
                    .weight(1f)
                    .height(140.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFFFF9C4)
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = androidx.compose.foundation.layout.Arrangement.Center
                ) {
                    Text(
                        text = "⚠",
                        fontSize = 32.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Stock Crítico",
                        style = MaterialTheme.typography.labelSmall,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = "$productosEnRiesgo",
                        style = MaterialTheme.typography.headlineSmall,
                        color = Color(0xFFF57F17),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            // Total de artículos en inventario
            Card(
                modifier = Modifier
                    .weight(1f)
                    .height(140.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFC8E6C9)
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = androidx.compose.foundation.layout.Arrangement.Center
                ) {
                    Text(
                        text = "📦",
                        fontSize = 32.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Stock Total",
                        style = MaterialTheme.typography.labelSmall,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = "$stockTotal",
                        style = MaterialTheme.typography.headlineSmall,
                        textAlign = TextAlign.Center
                    )
                }
            }

            Spacer(modifier = Modifier.width(12.dp))

            // Total de productos
            Card(
                modifier = Modifier
                    .weight(1f)
                    .height(140.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFBBDEFB)
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = androidx.compose.foundation.layout.Arrangement.Center
                ) {
                    Text(
                        text = "📊",
                        fontSize = 32.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Productos",
                        style = MaterialTheme.typography.labelSmall,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = "$totalProductos",
                        style = MaterialTheme.typography.headlineSmall,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Tarjeta informativa
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            )
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "INFORMACIÓN ADICIONAL",
                    style = MaterialTheme.typography.labelLarge
                )

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Precio Promedio:")
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "$${String.format("%.2f", precioPromedio)}",
                        style = MaterialTheme.typography.titleSmall
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Valor Promedio/Artículo:")
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "$${String.format("%.2f", if (stockTotal > 0) totalInventario / stockTotal else 0.0)}",
                        style = MaterialTheme.typography.titleSmall
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))
        
        Button(
            onClick = {
                navController.popBackStack()
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
        ) {
            Text("← Volver al Catálogo")
        }
    }
}
