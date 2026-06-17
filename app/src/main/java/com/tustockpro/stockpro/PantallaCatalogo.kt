package com.tustockpro.stockpro

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
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
import androidx.navigation.NavController
import com.tustockpro.stockpro.model.Producto
import com.tustockpro.stockpro.viewmodel.StockViewModel

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
                title = { Text("=== CATALOGO === Operario: $nombreOperario") }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate("reporte")
                }
            ) {
                Text("📊")
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
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    onClick = { mostrarSoloCritico.value = false },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Ver Todo")
                }
                Button(
                    onClick = { mostrarSoloCritico.value = true },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Stock Critico")
                }
            }
            
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(listaFiltrada) { producto ->
                    ProductoCard(
                        producto = producto,
                        onCardClick = {
                            navController.navigate("edicion/${producto.id}")
                        }
                    )
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
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable { onCardClick() }
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = producto.nombre,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = "Precio: $${String.format("%.2f", producto.precio)}",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Stock: ${producto.stockActual}",
                style = MaterialTheme.typography.bodyMedium,
                color = if (producto.stockActual < 5) Color.Red else Color.Unspecified
            )
        }
    }
}
