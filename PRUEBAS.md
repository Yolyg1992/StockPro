# Guía de Pruebas - StockPro

## 🧪 Plan de Pruebas Completo

Este documento guía las pruebas manuales para verificar que todas las funcionalidades funcionan correctamente.

---

## 1️⃣ PRUEBA: Validación de Login

### Objetivo
Verificar que el botón de ingreso solo se habilita con nombre ≥ 3 caracteres.

### Pasos
1. Lanza la app
2. Ves la pantalla de Login
3. Campo vacío → Botón DESHABILITADO ✓
4. Escribe "A" → Botón DESHABILITADO ✓
5. Escribe "AB" → Botón DESHABILITADO ✓
6. Escribe "ABC" → Botón HABILITADO ✓
7. Continúa escribiendo caracteres → Botón permanece HABILITADO ✓
8. Borra a "AB" → Botón DESHABILITADO ✓

### Resultado Esperado
- Contador muestra caracteres/3
- Color del contador cambia: rojo (inválido) ↔ verde (válido)
- Botón se hablita solo con ≥ 3 caracteres

---

## 2️⃣ PRUEBA: Navegación Login → Catálogo

### Objetivo
Verificar que el nombre se pasa correctamente entre pantallas.

### Pasos
1. En Login, ingresa nombre: "Juan"
2. Toca "Ingresar al Sistema"
3. Se abre Catálogo
4. Verifica que el título muestre "Operario: Juan" ✓

### Resultado Esperado
- Nombre ingresado aparece en el título del Catálogo
- Navegación ocurre inmediatamente

---

## 3️⃣ PRUEBA: Catálogo - Filtro "Ver Todo"

### Objetivo
Verificar que el filtro "Ver Todo" muestra todos los 6 productos.

### Pasos
1. En Catálogo, toca "Ver Todo"
2. Cuenta los productos visibles
3. Verifica estar presente:
   - Laptop Gamer (stock 5) - verde
   - Mouse Inalámbrico (stock 3) - ROJO
   - Teclado Mecánico (stock 0) - ROJO
   - Monitor 24" (stock 2) - ROJO
   - Audífonos Bluetooth (stock 8) - verde
   - Cargador Rápido (stock 1) - ROJO

### Resultado Esperado
- 6 productos visibles
- 4 demanda rojo (stock < 5)
- 2 demanda verde (stock ≥ 5)
- Botón "Ver Todo" está destacado

---

## 4️⃣ PRUEBA: Catálogo - Filtro "Stock Crítico"

### Objetivo
Verificar que muestra solo productos con stock < 5.

### Pasos
1. En Catálogo, toca "Stock Crítico"
2. Cuenta los productos visibles
3. Verifica que solo 4 aparezcan:
   - Mouse (3)
   - Teclado (0)
   - Monitor (2)
   - Cargador (1)

### Resultado Esperado
- Solo 4 productos (los que tienen stock < 5)
- Todos demanda fondo rojo
- El contador en el botón muestra "4"
- Botón debe estar destacado

---

## 5️⃣ PRUEBA: Sincronización - Aumentar Stock Crítico

### Objetivo
Verificar que cambios en Edición se reflejan en Catálogo.

### Pasos
1. En Catálogo con filtro "Ver Todo"
2. Identifica "Mouse Inalámbrico" (stock 3, ROJO)
3. Toca la tarjeta del Mouse para editar
4. Se abre Pantalla de Edición
5. Verifica mostrar:
   - Nombre: "Mouse Inalámbrico"
   - Stock: 3
   - Botón "-1": HABILITADO (porque stock > 0)
6. Toca "+1" dos veces
7. Stock cambia: 3 → 4 → 5
8. Verifica visualización actualizada
9. Toca "Guardar y Volver"
10. Regresa a Catálogo

### Resultado Esperado
- **Sincronización**: Mouse ahora muestra stock=5
- Color cambió de ROJO a VERDE
- En Catálogo "Stock Crítico" ahora cuenta 3 (no 4)
- En "Ver Todo" el Mouse se vuelve verde

### Esto Demuestra
✅ mutableStateListOf propaga cambios
✅ Todas las pantallas leen el mismo ViewModel
✅ Sincronización instantánea

---

## 6️⃣ PRUEBA: Edición - Reducir Stock a Cero

### Objetivo
Verificar comportamiento cuando stock = 0.

### Pasos
1. En Catálogo, encuentra "Cargador Rápido" (stock 1)
2. Toca para editar
3. Pantalla de Edición:
   - Stock muestra: 1
   - Botón "-1": HABILITADO
4. Toca "-1"
5. Stock cambiaría a: 0
6. Verifica:
   - Stock muestra: 0
   - Botón "-1": DESHABILITADO 🔒
   - Indicador demanda: "❌ Stock agotado"
   - Fondo de la tarjeta: ROJO intenso

### Resultado Esperado
- Stock = 0 deshabilita el botón "-1"
- Indicador visual cambia a "agotado"
- No se puede bajar más del 0

---

## 7️⃣ PRUEBA: Edición - Stock Crítico vs Normal

### Objetivo
Verificar indicadores visuales según rango de stock.

### Pasos
Edita varios productos y observa los indicadores:

| Stock | Indicador | Color | Botón -1 |
|-------|-----------|-------|----------|
| 0 | ❌ Agotado | Rojo Intenso | DESABILITADO |
| 1-4 | ⚠ Crítico | Amarillo | Habilitado |
| 5+ | ✓ Disponible | Verde | Habilitado |

### Resultado Esperado
- Indicadores correctos para cada rango
- Colores de fondo coherentes
- Botón -1 se deshabilita solo en 0

---

## 8️⃣ PRUEBA: Reporte - Cálculos Correctos

### Objetivo
Verificar que el reporte muestra cálculos correctos del ViewModel.

### Pasos
1. En Catálogo, toca el botón flotante 📊
2. Se abre Pantalla de Reporte
3. Verifica la métrica "Capital Invertido Total":

**Cálculo Manual:**
```
Laptop Gamer:     1200.00 × 5 = 6000.00
Mouse:              25.00 × 3 =    75.00
Teclado:            45.00 × 0 =     0.00
Monitor:           200.00 × 2 =   400.00
Audífonos:          80.00 × 8 =   640.00
Cargador:           15.00 × 1 =    15.00
                                  --------
TOTAL                           = 7130.00
```

### Resultado Esperado
- Capital mostrado: $7,130.00 (o ajustado si editaron)
- Productos con stock 0: 1
- Productos en riesgo: 4 (aunque pueden cambiar si editaron)
- Stock total: 19 (suma de todos los stocks)

---

## 9️⃣ PRUEBA: Sincronización Reporte

### Objetivo
Verificar que cambios se reflejan en el Reporte.

### Pasos
1. Anota el Capital Investido Total del Reporte
2. Vuelve a Catálogo
3. Edita un producto: aumenta stock en +1
4. Vuelve al Reporte
5. El Capital debe haber cambiado

**Ejemplo:**
- Capital Antes: $7,130.00
- Edita Laptop (5 unidades): +1 = 6 unidades
- Capital Después: $7,130.00 + $1,200.00 = $8,330.00

### Resultado Esperado
- Capital se actualiza automáticamente
- Conteo de productos en riesgo se actualiza
- Metrics se recalculan correctamente

---

## 🔟 PRUEBA: Navegación Completa

### Objetivo
Verificar que la navegación funciona en ambas direcciones.

### Pasos
1. Login → Catálogo → Edición → Volver a Catálogo ✓
2. Catálogo → Reporte → Volver a Catálogo ✓
3. Catálogo → Edición → Edición → Volver a Catálogo ✓

### Resultado Esperado
- Todas las transiciones son suaves
- Datos persisten durante navegación
- Cambios se reflejan al volver

---

## 1️⃣1️⃣ PRUEBA: Persistencia de Datos

### Objetivo
Verificar que datos persisten durante la sesión.

### Pasos
1. Edita stock de "Laptop": 5 → 7
2. Ve a Reporte (capital se actualiza)
3. Vuelve a Catálogo (Laptop aún muestra 7)
4. Ve a Edición de Laptop (stock sigue siendo 7)

### Resultado Esperado
- ViewModel mantiene datos entre transiciones
- No hay pérdida de información
- Cambios persisten durante la sesión

---

## 1️⃣2️⃣ PRUEBA: Validaciones Complejas

### Objetivo
Verificar todas las validaciones funcionan correctamente.

### Checklist
- [ ] Login: No puedes ingresar con < 3 caracteres
- [ ] Edición: No puedes bajar stock < 0
- [ ] Catálogo: Filtro muestra el número correcto
- [ ] Reporte: Cálculos son correctos
- [ ] Valores: El dinero se formatea con 2 decimales

### Resultado Esperado
- Todas las validaciones funcionan
- Mensajes de error son claros
- App es robusta ante entrada inválida

---

## 🎯 Resumen de Checklist

### Pantalla 1: Login
- [ ] Validación de nombre (3+ caracteres)
- [ ] Contador de caracteres
- [ ] Botón se habilita/deshabilita
- [ ] Navegación con nombre

### Pantalla 2: Catálogo
- [ ] 6 productos visibles
- [ ] Stock crítico en rojo
- [ ] Filtro "Ver Todo"
- [ ] Filtro "Stock Crítico"
- [ ] Tocar producto navega a Edición
- [ ] Botón flotante accede a Reporte

### Pantalla 3: Edición
- [ ] Stock se muestra correctamente
- [ ] +1 incrementa stock
- [ ] -1 decrementa stock
- [ ] -1 se deshabilita en stock 0
- [ ] Indicadores visuales correctos
- [ ] Cambios persisten al volver

### Pantalla 4: Reporte
- [ ] Capital Total calculado correctamente
- [ ] Conteo de stock 0
- [ ] Métricas adicionales mostradas
- [ ] Se actualiza al volver del Catálogo

### Sincronización
- [ ] Edición → Catálogo sincroniza
- [ ] Edición → Reporte sincroniza
- [ ] Cambios son instantáneos
- [ ] Datos persisten entre pantallas

---

## 📊 Datos de Prueba Recomendados

### Escenario 1: Aumento de Stock Crítico a Normal
- Mouse: 3 → 5 (sale de crítico)

### Escenario 2: Reducción a Agotado
- Teclado: 0 (ya agotado, verifica que -1 está deshabilitado)

### Escenario 3: Stock Alto
- Audífonos: 8 (ya normal, verifica color verde)

### Escenario 4: Cambio de Capital Total
- Edita múltiples productos y verifica reporte

---

## ✅ Criterios de Éxito

La app debe:
1. ✅ Validar entrada correctamente
2. ✅ Sincronizar datos automáticamente
3. ✅ Calcular valores correctamente
4. ✅ Mostrar indicadores visuales adecuados
5. ✅ Mantener datos entre pantallas
6. ✅ Ser responsive y rápida
7. ✅ No mostrar errores o crashes

Si todos pasan, ¡la app funciona correctamente! 🎉

