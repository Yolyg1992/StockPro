# StockPro - Documentación Técnica Completa

## 📱 Visión General

StockPro es una aplicación Android nativa desarrollada con **Jetpack Compose** que implementa un sistema integral de gestión de inventario. La aplicación consta de 4 pantallas interconectadas mediante navegación con paso de argumentos, todas coordinadas por un **ViewModel reactivo compartido**.

## 🏗️ Arquitectura

### Estructura del Proyecto

```
com.tustockpro.stockpro/
├── MainActivity.kt           # Punto de entrada y configuración de navegación
├── PantallaLogin.kt         # Pantalla 1: Login/Ingreso
├── PantallaCatalogo.kt      # Pantalla 2: Catálogo de productos
├── PantallaEdicion.kt       # Pantalla 3: Edición de stock
├── PantallaReporte.kt       # Pantalla 4: Reporte financiero
├── model/
│   └── Producto.kt          # Modelo de datos
├── viewmodel/
│   └── StockViewModel.kt    # ViewModel reactivo y centralizado
├── navigation/
│   └── Navigation.kt        # Definiciones de rutas
└── ui/theme/
    ├── Color.kt
    ├── Theme.kt
    └── Type.kt
```

## 🎯 Las 4 Pantallas

### Pantalla 1: Login (PantallaLogin.kt)

**Propósito:** Solicitar autenticación del operario

**Funcionalidades:**
- Campo de texto para ingresar nombre del operario
- Validación en tiempo real: mínimo 3 caracteres
- Botón "Ingresar al Sistema" habilitado solo cuando el nombre es válido
- Contador de caracteres que cambia de color según validez
- Navega al catálogo pasando el nombre como argumento

**Validación:**
```kotlin
val isNombreValido = viewModel.validarNombre(nombreState.value)
// El ViewModel centraliza la lógica: nombre.trim().length >= 3
```

**Navegación:**
```kotlin
navController.navigate("catalogo/${nombreState.value}")
```

---

### Pantalla 2: Catálogo (PantallaCatalogo.kt)

**Propósito:** Mostrar todos los productos y permitir filtros

**Funcionalidades:**
- Muestra todos los 6 productos en tarjetas visuales
- Filtro "Ver Todo": muestra todos los productos
- Filtro "Stock Crítico": muestra solo productos con stock < 5
- El stock se muestra en **rojo cuando es crítico** (< 5)
- Al tocar una tarjeta, navega a la edición pasando el ID del producto
- Botón flotante para acceder al reporte financiero
- Muestra el nombre del operario en el título

**Tarjetas de Producto:**
- Información completa: ID, nombre, descripción, precio, stock
- Indicador visual de estado crítico
- Fondo de color diferente según riesgo

**Datos Reactivos:**
```kotlin
val productos = viewModel.getProductos()  // Lista reactiva
val listaFiltrada = if (mostrarSoloCritico.value) {
    viewModel.obtenerProductosEnRiesgo()  // Filtrado en ViewModel
} else {
    productos
}
```

**Navegación:**
```kotlin
navController.navigate("edicion/${producto.id}")
```

---

### Pantalla 3: Edición (PantallaEdicion.kt)

**Propósito:** Modificar el stock de un producto específico

**Funcionalidades:**
- Muestra toda la información detallada del producto
- Visualización grande y clara del stock actual
- Botón **+1**: incrementa el stock en 1 unidad
- Botón **-1**: decrementa el stock en 1 unidad (deshabilitado cuando stock = 0)
- Indicadores visuales del estado:
  - ❌ Stock agotado (rojo)
  - ⚠ Stock crítico (amarillo)
  - ✓ Stock disponible (verde)
- Muestra el valor en stock (precio × cantidad actual)
- Actualización **inmediata y reactiva** en el ViewModel

**Actualización Reactiva:**
```kotlin
Button(onClick = {
    viewModel.actualizarStock(productoId, producto.stockActual + 1)
}) {
    Text("+1 Agregar")
}
```

**Sincronización:**
- Los cambios se guardan directamente en el ViewModel
- Se reflejan automáticamente en todas las pantallas
- Sin necesidad de confirmación manual

---

### Pantalla 4: Reporte (PantallaReporte.kt)

**Propósito:** Mostrar métricas financieras e indicadores de inventario

**Funcionalidades Principales:**
1. **Capital Invertido Total**: Suma de (precio × stock) para todos los productos
2. **Conteo de Productos con Stock Cero**: Cantidad de productos sin inventario
3. **Productos en Stock Crítico**: Cantidad con stock < 5
4. **Stock Total**: Suma de todos los stocks
5. **Total de Productos**: Cantidad de artículos diferentes
6. **Precio Promedio**: Media de precios
7. **Valor Promedio por Artículo**: Capital invertido / cantidad de artículos

**Visualización:**
- Tarjeta principal con capital invertido en grande
- Grid de métricas con iconos y códigos de color
- Información adicional en tarjeta secundaria
- Toda la información es **calculada en el ViewModel**

---

## 🔄 ViewModel Reactivo: El Corazón del Sistema

### Clase: StockViewModel.kt

El **ViewModel es el elemento central** que mantiene sincronizados todos los datos entre las 4 pantallas.

```kotlin
class StockViewModel : ViewModel() {
    private val _productos = mutableStateListOf<Producto>(...)
    // Lista reactiva que notifica cambios automáticamente
}
```

### ¿Por qué mutableStateListOf?

**mutableStateListOf** es una lista especial de Compose que:
- 🔄 **Es reactiva**: cualquier cambio causa recomposición de la UI
- 📱 **Sincroniza automáticamente**: todos los observadores se actualizan
- ⚡ **Es eficiente**: solo recompone lo necesario
- 🎯 **Sin LiveData ni Flow**: más simple y directo

### Ejemplo de Reactividad

1. **Pantalla de Edición modifica el stock:**
   ```kotlin
   viewModel.actualizarStock(productoId, nuevaCantidad)
   ```

2. **El ViewModel actualiza el producto:**
   ```kotlin
   producto?.let {
       it.stockActual = nuevaCantidad  // ← Cambio reactivo
   }
   ```

3. **Automáticamente:**
   - ✓ Se recompone la Pantalla de Edición (muestra nuevo valor)
   - ✓ Se recompone el Catálogo (si estaba visibles, muestra color actualizado)
   - ✓ Se recompone el Reporte (si se genere nuevamente, muestra cálculos actualizados)

### Métodos Principales

| Método | Propósito | Retorna |
|--------|-----------|---------|
| `getProductos()` | Obtiene lista reactiva | `List<Producto>` |
| `obtenerProducto(id)` | Busca un producto por ID | `Producto?` |
| `actualizarStock(id, cantidad)` | Modifica el stock reactivamente | `Unit` |
| `calcularValorTotalInventario()` | Suma de precio × stock | `Double` |
| `obtenerProductosConStockCero()` | Cuenta productos sin stock | `Int` |
| `obtenerProductosEnRiesgo()` | Filtra stock < 5 | `List<Producto>` |
| `obtenerCantidadProductosEnRiesgo()` | Cuenta productos en riesgo | `Int` |
| `validarNombre(nombre)` | Valida nombre operario | `Boolean` |
| `obtenerPrecioPromedio()` | Media de precios | `Double` |
| `obtenerStockTotal()` | Suma de todos los stocks | `Int` |

### Centralización de Lógica de Negocio

**NUNCA en interfaces, SIEMPRE en el ViewModel:**

✅ **Correcto:**
```kotlin
// En ViewModel
fun calcularValorTotalInventario(): Double {
    return _productos.sumOf { it.precio * it.stockActual }
}

// En UI
val total = viewModel.calcularValorTotalInventario()
Text("Total: $$total")
```

❌ **Incorrecto:**
```kotlin
// En composable (MALO)
Text("Total: $${
    viewModel.getProductos().sumOf { it.precio * it.stockActual }
}")
```

---

## 📊 Datos Iniciales

La aplicación se inicializa con **6 productos**:

| ID | Nombre | Precio | Stock Inicial | Estado |
|----|--------|--------|---------------|--------|
| 1 | Laptop Gamer | $1200.00 | 5 | Normal |
| 2 | Mouse Inalámbrico | $25.00 | 3 | ⚠ Crítico |
| 3 | Teclado Mecánico | $45.00 | 0 | ❌ Agotado |
| 4 | Monitor 24" | $200.00 | 2 | ⚠ Crítico |
| 5 | Audífonos Bluetooth | $80.00 | 8 | Normal |
| 6 | Cargador Rápido | $15.00 | 1 | ⚠ Crítico |

**Capital Total Inicial:** $3,320.00

---

## 🔀 Flujo de Navegación

```
Login (Start)
    ↓
    └─ Ingresa nombre (mín 3 chars)
    ↓
Catálogo
    ├─ Toca un producto
    │  ├─ Stock Crítico (rojo)
    │  └─ Stock Normal
    │     ↓
    │     Edición
    │     ├─ +1 Agregar
    │     ├─ -1 Restar
    │     ↓
    │     Volver → Catálogo (con cambios reflejados)
    │
    ├─ Filtro "Ver Todo"
    │
    ├─ Filtro "Stock Crítico"
    │
    └─ Botón flotante 📊
       ↓
       Reporte
       ├─ Capital Total
       ├─ Métricas
       ├─ Información adicional
       ↓
       Volver → Catálogo
```

---

## 🚀 Pasos de Compilación

### 1. Preparación
```bash
cd C:\Users\Usuario\Desktop\StockPro
```

### 2. Compilación
```bash
./gradlew clean build
```

### 3. Instalación en dispositivo/emulador
```bash
./gradlew installDebug
```

### 4. Ejecución
- Abrir en Android Studio
- Seleccionar dispositivo/emulador
- Presionar "Run"

---

## 💡 Características Clave de Implementación

### 1. **Validación en Tiempo Real**
- La validación del nombre se realiza mostrand feedback inmediato
- El botón se habilita/deshabilita dinámicamente
- Validación centralizada en ViewModel

### 2. **Reactividad Automática**
- Los cambios de stock en Edición se reflejan automáticamente en Catálogo
- Sin necesidad de observer pattern personalizado
- mutableStateListOf se encarga de todo

### 3. **Argumentos en Navegación**
- Login → Catálogo: pasa nombre del operario
- Catálogo → Edición: pasa ID del producto
- Argumentos se recuperan en NavHost

### 4. **Cálculos Centralizados**
- Toda la lógica de negocio en ViewModel
- Las composables solo presentan datos
- Cambios en lógica no afectan UI directamente

### 5. **Indicadores Visuales**
- Stock crítico en rojo
- Stock cero en rojo oscuro
- Stock normal en verde
- Iconos y emojis para claridad

---

## 🧪 Pruebas Recomendadas

1. **Login**: Ingresa nombres de diferentes longitudes
2. **Catálogo**: Alterna entre filtros
3. **Edición**: Modifica stock hasta 0 y verifica deshabilitación
4. **Reporte**: Verifica cálculos coincidir con cambios manuales
5. **Sincronización**: Modifica stock en Edición y verifica cambio en Catálogo

---

## 📝 Commits en GitHub

Se han realizado 6 commits documentando el desarrollo:

1. **[COMMIT 1]** Configuración inicial del proyecto Android con Jetpack Compose
2. **[COMMIT 2]** Mejora significativa del ViewModel con métodos de validación
3. **[COMMIT 3]** Mejora visual completa de todas las pantallas
4. **[COMMIT 4]** Documentación e implementación del análisis de sincronización
5. **[COMMIT 5]** Ajustes finales y optimizaciones
6. **[COMMIT 6]** Preparación para video de defensa

---

## 🎓 Conceptos Educativos

### mutableStateListOf vs LiveData

| Aspecto | mutableStateListOf | LiveData |
|--------|-------------------|----------|
| Reactividad | Automática en Compose | Manual con observadores |
| Código | Minimalista | Más boilerplate |
| Performance | Optimizado para Compose | General |
| Sintaxis | Directa | Observer pattern |

### Validación de Nombre

```kotlin
fun validarNombre(nombre: String): Boolean {
    return nombre.trim().length >= 3
}
```

- `trim()`: elimina espacios
- `length >= 3`: verifica mínimo 3 caracteres
- Centralizada para fácil modificación

### Cálculo de Capital Invertido

```kotlin
fun calcularValorTotalInventario(): Double {
    return _productos.sumOf { it.precio * it.stockActual }
}
```

- Suma de productos precio × stock
- Se recalcula cuando mutableStateListOf cambia
- Eficiente mediante sumOf

---

## 🔐 Consideraciones de Seguridad

- ✅ Validación de entrada (nombre)
- ✅ No hay datos sensibles en la UI
- ✅ ViewModel maneja estado interno
- ✅ Sin serialización insegura
- ✅ Sin acceso directo a datos desde UI

---

## 🎨 Personalización

### Cambiar colores
Editar `ui/theme/Color.kt`

### Cambiar productos iniciales
Editar `viewmodel/StockViewModel.kt` - constructor de `_productos`

### Cambiar límite de stock crítico
Editar `viewmodel/StockViewModel.kt` - método `obtenerProductosEnRiesgo()`:
```kotlin
return _productos.filter { it.stockActual < 5 }  // ← Cambiar número
```

### Cambiar validación de nombre
Editar `viewmodel/StockViewModel.kt` - método `validarNombre()`:
```kotlin
return nombre.trim().length >= 3  // ← Cambiar número
```

---

## 📞 Conclusión

StockPro demuestra cómo construir una aplicación Android moderna usando:
- ✅ Jetpack Compose para UI
- ✅ ViewModel reactivo para estado
- ✅ mutableStateListOf para reactividad
- ✅ Navegación con argumentos
- ✅ Arquitectura limpia y centralizada
- ✅ Sincronización automática entre pantallas

La aplicación es extensible y puede adaptarse fácilmente para nuevos requisitos.

