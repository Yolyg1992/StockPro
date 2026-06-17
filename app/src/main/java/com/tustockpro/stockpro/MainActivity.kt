package com.tustockpro.stockpro

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.tustockpro.stockpro.ui.theme.StockProTheme
import com.tustockpro.stockpro.viewmodel.StockViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StockProTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    StockProApp()
                }
            }
        }
    }
}

@Composable
fun StockProApp() {
    val navController = rememberNavController()
    val viewModel: StockViewModel = viewModel()

    NavHost(navController = navController, startDestination = "login") {
        
        // Pantalla 1: Login
        composable("login") {
            PantallaLogin(
                navController = navController,
                viewModel = viewModel
            )
        }
        
        // Pantalla 2: Catalogo
        composable(
            route = "catalogo/{nombreOperario}",
            arguments = listOf(navArgument("nombreOperario") { type = NavType.StringType })
        ) { backStackEntry ->
            val nombre = backStackEntry.arguments?.getString("nombreOperario") ?: "Invitado"
            PantallaCatalogo(
                navController = navController,
                viewModel = viewModel,
                nombreOperario = nombre
            )
        }
        
        // Pantalla 3: Edicion
        composable(
            route = "edicion/{productoId}",
            arguments = listOf(navArgument("productoId") { type = NavType.IntType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("productoId") ?: 0
            PantallaEdicion(
                navController = navController,
                viewModel = viewModel,
                productoId = id
            )
        }
        
        // Pantalla 4: Reporte
        composable("reporte") {
            PantallaReporte(
                navController = navController,
                viewModel = viewModel
            )
        }
    }
}
