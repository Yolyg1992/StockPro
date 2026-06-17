package com.tustockpro.stockpro.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.tustockpro.stockpro.model.Producto

/**
 * ViewModel compartido que gestiona el estado reactivo de todos los productos.
 *
 * Utiliza mutableStateListOf para mantener una lista reactiva que se sincroniza
 * automáticamente con todas las pantallas que la observan.
 *
 * Todos los cálculos y lógica de negocio se centralizan aquí, nunca en las interfaces.
 */
class StockViewModel : ViewModel() {
    
    /**
     * Lista reactiva de productos inicializados con 6 productos.
     * mutableStateListOf proporciona reactividad automática en Compose.
     */
    private val _productos = mutableStateListOf(
        Producto(1, "Laptop Gamer", "Laptop con RTX 4060", 1200.0, 5),
        Producto(2, "Mouse Inalámbrico", "Mouse Bluetooth ergonómico", 25.0, 3),
        Producto(3, "Teclado Mecánico", "Teclado RGB con switches azules", 45.0, 0),
        Producto(4, "Monitor 24\"", "Monitor Full HD 75Hz", 200.0, 2),
        Producto(5, "Audífonos Bluetooth", "Audífonos con cancelación de ruido", 80.0, 8),
        Producto(6, "Cargador Rápido", "Cargador USB-C 65W", 15.0, 1)
    )

    /**
     * Obtiene la lista reactiva de productos.
     * Los cambios en esta lista se reflejarán automáticamente en la UI.
     */
    fun getProductos() = _productos

    /**
     * Obtiene un producto específico por su ID.
     * @param id ID del producto a buscar
     * @return El producto si existe, null en caso contrario
     */
    fun obtenerProducto(id: Int): Producto? {
        return _productos.find { it.id == id }
    }

    /**
     * Actualiza el stock de un producto.
     * Los cambios se reflejan inmediatamente en todas las pantallas.
     *
     * @param id ID del producto
     * @param nuevaCantidad Nueva cantidad de stock
     */
    fun actualizarStock(id: Int, nuevaCantidad: Int) {
        val producto = _productos.find { it.id == id }
        producto?.let {
            if (nuevaCantidad >= 0) {
                it.stockActual = nuevaCantidad
                // Notificar cambios (mutableStateListOf lo hace automáticamente)
            }
        }
    }

    /**
     * Calcula el valor total del inventario (suma de precio × stock).
     * Esta lógica de negocio siempre está en el ViewModel, no en la UI.
     *
     * @return El valor total en dinero del inventario actual
     */
    fun calcularValorTotalInventario(): Double {
        return _productos.sumOf { it.precio * it.stockActual }
    }

    /**
     * Obtiene la cantidad de productos con stock en cero.
     * Lógica de negocio centralizada en el ViewModel.
     *
     * @return Cantidad de productos con stock = 0
     */
    fun obtenerProductosConStockCero(): Int {
        return _productos.count { it.stockActual == 0 }
    }

    /**
     * Obtiene los productos en riesgo crítico (stock < 5).
     * Este filtro se aplica aquí, no en la UI.
     *
     * @return Lista de productos con stock crítico
     */
    fun obtenerProductosEnRiesgo(): List<Producto> {
        return _productos.filter { it.stockActual < 5 }
    }

    /**
     * Obtiene el conteo de productos en riesgo crítico.
     *
     * @return Cantidad de productos con stock < 5
     */
    fun obtenerCantidadProductosEnRiesgo(): Int {
        return obtenerProductosEnRiesgo().count()
    }

    /**
     * Verifica si el nombre es válido (mínimo 3 caracteres).
     * Validación de negocio centralizada en el ViewModel.
     *
     * @param nombre Nombre a validar
     * @return true si el nombre es válido, false en caso contrario
     */
    fun validarNombre(nombre: String): Boolean {
        return nombre.trim().length >= 3
    }

    /**
     * Obtiene el precio promedio de los productos en inventario.
     *
     * @return Precio promedio de todos los productos
     */
    fun obtenerPrecioPromedio(): Double {
        return if (_productos.isNotEmpty()) {
            _productos.sumOf { it.precio } / _productos.size
        } else {
            0.0
        }
    }

    /**
     * Obtiene la cantidad total de artículos en inventario.
     *
     * @return Suma de todos los stocks
     */
    fun obtenerStockTotal(): Int {
        return _productos.sumOf { it.stockActual }
    }
}
