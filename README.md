# README - StockPro

## 📱 Aplicación Android de Gestión de Inventario

StockPro es una aplicación nativa Android desarrollada con **Jetpack Compose** que demuestra cómo:
- Implementar 4 pantallas interconectadas con navegación reactiva
- Usar un ViewModel compartido para sincronización de datos
- Mantener datos sincronizados entre pantallas sin librerías adicionales
- Centralizar lógica de negocio en el ViewModel

## 🎯 Características

### 4 Pantallas Integradas

1. **Pantalla Login**
   - Validación de nombre (mínimo 3 caracteres)
   - Paso de argumentos a la siguiente pantalla

2. **Pantalla Catálogo**
   - Lista de 6 productos en tarjetas
   - Filtros: "Ver Todo" y "Stock Crítico" (< 5)
   - Stock mostrado en rojo si es crítico
   - Navega a edición al tocar producto

3. **Pantalla Edición**
   - Modificar stock con botones +1/-1
   - -1 deshabilitado cuando stock = 0
   - Cambios actualizados directamente en ViewModel
   - Sincronización instantánea

4. **Pantalla Reporte**
   - Valor total del inventario (precio × stock)
   - Conteo de productos sin stock
   - Métricas adicionales
   - Todo calculado en el ViewModel

## 🏗️ Estructura del Proyecto

```
app/src/main/java/com/tustockpro/stockpro/
├── MainActivity.kt              # Punto de entrada y navegación
├── PantallaLogin.kt            # Pantalla 1
├── PantallaCatalogo.kt         # Pantalla 2
├── PantallaEdicion.kt          # Pantalla 3
├── PantallaReporte.kt          # Pantalla 4
├── model/Producto.kt           # Modelo de datos
├── viewmodel/StockViewModel.kt # ViewModel reactivo
└── ui/theme/                   # Tema Material 3
```

## 🔄 Sincronización Reactiva

### El Secreto: mutableStateListOf

El ViewModel usa `mutableStateListOf` para que los cambios se propagues automáticamente:

```kotlin
private val _productos = mutableStateListOf(...)  // Lista reactiva

fun actualizarStock(id: Int, nuevaCantidad: Int) {
    val producto = _productos.find { it.id == id }
    producto?.stockActual = nuevaCantidad  // Cambio se propaga automáticamente
}
```

**¿Por qué funciona?**
- Compose observa automáticamente `mutableStateListOf`
- Cuando el contenido cambia, se notifica a todos los observadores
- Todas las pantallas se recomponen instantáneamente
- Sin necesidad de LiveData, Flow, o observadores manuales

**Ejemplo de flujo:**
1. Pantalla Edición: Usuario toca "+1"
2. ViewModel: `producto.stockActual = 4`
3. mutableStateListOf: "¡Cambio detectado!"
4. Compose: "¡Recomponer Catálogo y Reporte!"
5. Todas las pantallas: Datos sincronizados ✓

## 📊 Datos Iniciales

6 productos pre-cargados:
- Laptop Gamer: $1200.00 (5 unidades)
- Mouse Inalámbrico: $25.00 (3 unidades - crítico)
- Teclado Mecánico: $45.00 (0 unidades - agotado)
- Monitor 24": $200.00 (2 unidades - crítico)
- Audífonos Bluetooth: $80.00 (8 unidades)
- Cargador Rápido: $15.00 (1 unidad - crítico)

**Capital Total: $3,320.00**

## 🚀 Cómo Compilar y Ejecutar

### Pre-requisitos
- Android Studio Arctic Fox o posterior
- JDK 11 o posterior
- Android SDK level 34

### Pasos
```bash
# Clonar o entrar al directorio
cd C:\Users\Usuario\Desktop\StockPro

# Compilar
./gradlew clean build

# Ejecutar en emulador o dispositivo
./gradlew installDebug
```

O directamente en Android Studio:
1. Abrir proyecto
2. Seleccionar emulador/dispositivo
3. Presionar "Run"

## 📚 Documentación

- **DOCUMENTACION.md**: Guía técnica completa
- **SINCRONIZACION_REACTIVA.md**: Explicación detallada del sistema reactivo
- **Este README**: Resumen ejecutivo

## ✅ Casos de Prueba Recomendados

1. **Login**: Ingresa nombres de 1, 2, 3, y 4 caracteres
2. **Catálogo**: Alterna entre "Ver Todo" y "Stock Crítico"
3. **Edición**: 
   - Aumenta stock de un producto crítico hasta > 5
   - Reduce stock a 0 y verifica deshabilitación de -1
4. **Sincronización**: Modifica stock en Edición, vuelve a Catálogo
5. **Reporte**: Verifica que cálculos coincidan con cambios reales

## 🎨 Características de Diseño

- **Material Design 3**: Tema moderno y consistente
- **Composables**: Reutilizables y tipados
- **Indicadores visuales**: Colores para estados de stock
- **Responsive**: Adapta a diferentes tamaños de pantalla
- **Dark mode**: Compatible con tema oscuro

## 🏛️ Arquitectura de Mejores Prácticas

✅ **ViewModel centralizado**: Toda lógica de negocio aquí
✅ **Composables sin lógica**: Solo presentan datos
✅ **Reactividad automática**: Sin código de sincronización manual
✅ **Separación de capas**: Model, ViewModel, UI
✅ **Validación centralizada**: En el ViewModel
✅ **Cálculos en ViewModel**: Nunca en interfaces

## 📝 Commits en GitHub

El proyecto incluye 6 commits documentando el desarrollo:

1. Configuración inicial de proyecto
2. Mejoras en ViewModel
3. Mejora visual de pantallas
4. Documentación e implementación
5. Ajustes finales
6. Preparación para defensa

## 🧪 Funcionalidades Avanzadas

- Listado reactivo con `mutableStateListOf`
- Filtrado en el ViewModel
- Cálculos complejos (suma, promedio, conteo)
- Indicadores de estado con colores
- Navegación con argumentos
- Persistencia de datos entre pantallas

## 🎓 Conceptos Enseñados

- Jetpack Compose
- ViewModel y estado
- Navegación Compose
- mutableStateListOf vs LiveData
- Reactividad en Compose
- Arquitectura limpia
- Lógica centralizada

## 💡 Extensiones Posibles

- Agregar persistencia con Room
- Integrar con API remota
- Agregar autenticación
- Historial de cambios
- Exportar reportes
- Gráficos de tendencias

## 📞 Contacto

Desarrollado como demostración de mejores prácticas en Android con Compose.

---

**Versión**: 1.0  
**Fecha**: Junio 2026  
**Lenguaje**: Kotlin  
**Mínimo SDK**: 24  
**Target SDK**: 36

