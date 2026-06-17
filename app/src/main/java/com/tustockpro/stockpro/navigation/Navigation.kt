package com.tustockpro.stockpro.navigation

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Catalogo : Screen("catalogo/{nombreOperario}") {
        fun passNombre(nombre: String) = "catalogo/$nombre"
    }
    object Edicion : Screen("edicion/{productoId}") {
        fun passId(id: Int) = "edicion/$id"
    }
    object Reporte : Screen("reporte")
}
