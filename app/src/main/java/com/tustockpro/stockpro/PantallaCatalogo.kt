package com.tustockpro.stockpro

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.tustockpro.stockpro.model.Producto
import com.tustockpro.stockpro.viewmodel.StockViewModel

/**
 * Pantalla 2: Catálogo de Productos
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaCatalogo(
    navController: NavController,
    viewModel: StockViewModel,
    nombreOperario: String
) {
    val mostrarSoloCritico = remember { mutableStateOf(false) }
    
    val productos = viewModel.getProductos()
    
    val listaFiltrada = if (mostrarSoloCritico.value) {
        viewModel.obtenerProductosEnRiesgo()
    } else {
        productos
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text("Catálogo de Productos")
                        Text("Operario: $nombreOperario", fontSize = 12.sp)
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate("reporte") },
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Text("📊", fontSize = 24.sp)
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            ) {
                Button(
                    onClick = { mostrarSoloCritico.value = false },
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (!mostrarSoloCritico.value)
                            MaterialTheme.colorScheme.primary
                        else
                            MaterialTheme.colorScheme.surfaceVariant
                    )
                ) {
                    Text("✓ Todo (${productos.size})")
                }

                Spacer(modifier = Modifier.width(8.dp))

                Button(
                    onClick = { mostrarSoloCritico.value = true },
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (mostrarSoloCritico.value)
                            Color.Red
                        else
                            MaterialTheme.colorScheme.surfaceVariant
                    )
                ) {
                    Text("⚠ Crítico (${viewModel.obtenerCantidadProductosEnRiesgo()})")
                }
            }
            
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(listaFiltrada) { producto ->
                    ProductoCard(
                        producto = producto,
                        onCardClick = { navController.navigate("edicion/${producto.id}") }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}

@Composable
fun ProductoCard(
    producto: Producto,
    onCardClick: () -> Unit
) {
    val esStockCritico = producto.stockActual < 5

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onCardClick() },
        colors = CardDefaults.cardColors(
            containerColor = if (esStockCritico)
                Color(0xFFFFEBEE)
            else
                MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "ID: ${producto.id}",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.outline
            )

            Text(
                text = producto.nombre,
                style = MaterialTheme.typography.titleMedium,
                fontSize = 18.sp
            )

            Text(
                text = producto.descripcion,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.weight(1f)) {
                    Text("Precio", style = MaterialTheme.typography.labelSmall)
                    Text("$${String.format("%.2f", producto.precio)}",
                        style = MaterialTheme.typography.titleSmall)
                }

                Column(modifier = Modifier.weight(1f)) {
                    Text("Stock", style = MaterialTheme.typography.labelSmall)
                    Text("${producto.stockActual}",
                        style = MaterialTheme.typography.titleSmall,
                        color = if (esStockCritico) Color.Red else Color.Unspecified,
                        fontSize = 18.sp
                    )
                }
            }

            if (esStockCritico) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "⚠ CRÍTICO",
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.Red,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFFFFCDD2), shape = MaterialTheme.shapes.small)
                        .padding(8.dp)
                )
            }
        }
    }
}
