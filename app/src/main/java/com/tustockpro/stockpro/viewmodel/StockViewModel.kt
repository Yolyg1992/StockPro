package com.tustockpro.stockpro.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.tustockpro.stockpro.model.Producto

class StockViewModel : ViewModel() {
    
    private val _productos = mutableStateListOf(
        Producto(1, "Laptop Gamer", "Laptop con RTX 4060", 1200.0, 5),
        Producto(2, "Mouse Inalámbrico", "Mouse Bluetooth ergonómico", 25.0, 3),
        Producto(3, "Teclado Mecánico", "Teclado RGB con switches azules", 45.0, 0),
        Producto(4, "Monitor 24\"", "Monitor Full HD 75Hz", 200.0, 2),
        Producto(5, "Audífonos Bluetooth", "Audífonos con cancelación de ruido", 80.0, 8),
        Producto(6, "Cargador Rápido", "Cargador USB-C 65W", 15.0, 1)
    )

    fun getProductos() = _productos

    fun obtenerProducto(id: Int): Producto? {
        return _productos.find { it.id == id }
    }

    fun actualizarStock(id: Int, nuevaCantidad: Int) {
        val producto = _productos.find { it.id == id }
        producto?.let {
            if (nuevaCantidad >= 0) {
                it.stockActual = nuevaCantidad
            }
        }
    }

    fun calcularValorTotalInventario(): Double {
        return _productos.sumOf { it.precio * it.stockActual }
    }

    fun obtenerProductosEnRiesgo(): List<Producto> {
        return _productos.filter { it.stockActual < 5 }
    }
}
