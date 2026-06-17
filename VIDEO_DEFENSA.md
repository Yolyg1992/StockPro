# Guía de Video de Defensa - StockPro

## 📹 Registro de Video de Defensa

Este documento proporciona una guía para grabar un video mostrando y explicando la aplicación StockPro.

---

## 📋 Estructura del Video Recomendada

**Duración total**: ~5-10 minutos  
**Formato**: MP4 o similar  
**Resolución**: 1080p o superior  
**Audio**: Claro y bien audible  

---

## 🎬 Desglose del Video

### Segmento 1: Introducción (0:00 - 0:30)

**Qué decir:**
- "Buenas, soy [Tu nombre]"
- "Les presento StockPro, una aplicación Android de gestión de inventario"
- "Desarrollada con Jetpack Compose, demostrando reactividad automática"

**Qué mostrar:**
- Pantalla de inicio de la app
- Logo/nombre de la aplicación

---

### Segmento 2: Arquitectura (0:30 - 1:30)

**Qué decir:**
- "La app tiene 4 pantallas interconectadas:"
- "Pantalla 1: Login con validación"
- "Pantalla 2: Catálogo con filtros"
- "Pantalla 3: Edición de stock"
- "Pantalla 4: Reporte financiero"
- "Todas coordinadas por un ViewModel compartido con mutableStateListOf"

**Qué mostrar:**
- Diagrama o dibujo de la arquitectura
- O navega rápidamente entre las 4 pantallas

---

### Segmento 3: Pantalla Login (1:30 - 2:00)

**Qué decir:**
- "La Pantalla 1 valida el nombre del operario"
- "Mínimo 3 caracteres para ingresar"
- "Aquí vemos la validación en tiempo real"

**Qué hacer:**
- Escribe 1 carácter → Botón deshabilitado
- Escribe 2 caracteres → Botón deshabilitado
- Completa a 3+ caracteres → Botón habilitado
- Ingresa con un nombre válido (ej: "Juan")

---

### Segmento 4: Pantalla Catálogo - Vista General (2:00 - 2:30)

**Qué decir:**
- "La Pantalla 2 muestra 6 productos en catálogo"
- "Cada tarjeta muestra: ID, nombre, descripción, precio y stock"
- "El stock se muestra en rojo si es crítico (menor a 5)"

**Qué hacer:**
- Muestra la lista completa
- Señala los productos con stock en rojo
- Señala los productos con stock en verde

---

### Segmento 5: Filtros (2:30 - 3:00)

**Qué decir:**
- "Tiene dos filtros: 'Ver Todo' y 'Stock Crítico'"
- "Ver Todo muestra todos los 6 productos"
- "Stock Crítico muestra solo los que tienen menos de 5 unidades"

**Qué hacer:**
- Toca "Ver Todo" → Muestra cuenta total
- Toca "Stock Crítico" → Muestra solo 4 productos
- Alterna entre filtros

---

### Segmento 6: Sincronización Reactiva Parte 1 (3:00 - 5:00)

**Parte A: Preparación (3:00 - 3:30)**

**Qué decir:**
- "Ahora demostraré el corazón de la app: la sincronización reactiva"
- "Tenemos el Mouse con 3 unidades (stock crítico, en rojo)"
- "Y estos 4 productos están en riesgo"

**Qué hacer:**
- Muestra el Catálogo con filtro "Stock Crítico"
- Señala el Mouse en rojo
- Anota mentalmente el contador: "4 productos en riesgo"

---

**Parte B: Edición (3:30 - 4:00)**

**Qué decir:**
- "Voy a tocar la tarjeta del Mouse para editar su stock"
- "La Pantalla 3 me permite modificar el stock con +1 y -1"

**Qué hacer:**
- Toca la tarjeta del Mouse
- Se abre la Pantalla de Edición
- Muestra el stock actual: 3
- Verifica que -1 está habilitado

---

**Parte C: Cambio Reactivo (4:00 - 4:45)**

**Qué decir:**
- "Ahora aumento el stock del Mouse a 5 tocando '+1' dos veces"
- "Observen cómo se actualiza automáticamente"
- "Stock pasa de 3 a 4 a 5"
- "El indicador de estado CAMBIA de Crítico a Disponible"
- "El color del fondo cambia de amarillo a verde"

**Qué hacer:**
- Toca "+1" → Stock 4 (aún el indicador dice crítico)
- Toca "+1" → Stock 5 (indicador dice disponible, color verde)
- Anota el cambio en la UI

---

**Parte D: Volver y Mostrar Sincronización (4:45 - 5:00)**

**Qué decir:**
- "Ahora toco 'Guardar y Volver'"
- "Regreso al Catálogo"
- "OBSERVEN: **Sin hacer nada**, el Mouse ya muestra stock=5"
- "El color ya está en verde"
- "El contador de críticos bajó de 4 a 3"
- "TODO sincronizó automáticamente"

**Qué hacer:**
- Toca "Guardar y Volver"
- Regresa al Catálogo
- Muestra que el Mouse ahora está en verde
- Muestra que "Stock Crítico" ahora cuenta 3

---

### Segmento 7: Explicación de Sincronización (5:00 - 6:00)

**Qué decir:**
```
"¿Cómo funciona esto? Utilizamos mutableStateListOf,
una estructura especial de Jetpack Compose que:

1. Es una lista reactiva
2. Notifica automáticamente cuando cambia
3. Compose observa estos cambios sin código manual
4. Se recomponen TODAS las pantallas que leen estos datos

En la Pantalla 3, cuando modificamos el stock:
producto.stockActual = 4
O producto.stockActual = 5

mutableStateListOf DETECTA este cambio automáticamente
Y notifica a todas las pantallas:
Catálogo se recompone, Reporte se recalcula, todo sincronizado.

No usamos LiveData ni Flow, Compose lo maneja nativamente.
Es minimalista, eficiente y elegante."
```

**Qué mostrar (opcional):**
- Puedes mostrar el código del ViewModel en Android Studio
- Señala la estructura `mutableStateListOf`
- Señala el método `actualizarStock`

---

### Segmento 8: Reporte Financiero (6:00 - 7:00)

**Qué decir:**
- "La Pantalla 4 muestra el reporte financiero"
- "Calcula el capital invertido total: suma de precio × stock"
- "Muestra cuántos productos están agotados"
- "Muestra cuántos productos tienen stock bajo"
- "Todas estas métricas se calculan en el ViewModel"
- "Nunca en la UI"

**Qué hacer:**
- Toca el botón flotante 📊
- Se abre el Reporte
- Señala el Capital Total
- Señala las mezclasricas:
  - Stock Cero
  - Stock Crítico
  - Stock Total
  - Total de Productos
  - Precio Promedio

---

### Segmento 9: Verificación de Cambios (7:00 - 7:45)

**Qué decir:**
- "Si modifico otro producto, el Reporte se actualiza automáticamente"
- "Voy a editar otro producto y regreso al Reporte"

**Qué hacer:**
- Vuelve al Catálogo
- Edita Audífonos: 8 → 10 (aumenta)
- Vuelve al Reporte
- Verifica que Capital Total aumentó
- Verifica que Stock Total aumentó

---

### Segmento 10: Conclusión (7:45 - 8:30)

**Qué decir:**
```
"StockPro demuestra cómo construir aplicaciones Android modernas:

✓ Usando Jetpack Compose para UI reactiva
✓ Un ViewModel centralizado que gestiona todo el estado
✓ mutableStateListOf para sincronización automática
✓ Sin necesidad de observers manuales
✓ Validación centralizada en el ViewModel
✓ Cálculos en el ViewModel, nunca en la UI
✓ 4 pantallas trabajando juntas perfectamente

La sincronización es instantánea y automática.
Los cambios en una pantalla se reflejan en todas las demás.
Código limpio, arquitectura clara, y best practices de Android.

Gracias por ver. ¿Preguntas?"
```

---

## 🎥 Consejos de Grabación

### Herramientas Recomendadas
- **Android Studio**: Grabador de pantalla integrado (Shift + Ctrl + R)
- **OBS Studio**: Para grabar con audio/webcam superpuesta
- **Screencastify**: Extensión de Chrome si usas emulador en web
- **Dispositivo Android**: Graba directamente con grabador nativo

### Calidad de Grabación
- Usa emulador o dispositivo con pantalla HD (al menos 1080p)
- Frena la grabación si los clics son muy rápidos
- Deja 1-2 segundos entre acciones para que el usuario vea
- Aumenta velocidad de reproducción si es necesario

### Audio
- Habla claro y lentamente
- Usa micrófono de buena calidad
- Evita ruidos de fondo
- Prueba audio antes de grabar

### Edición (Opcional)
- Añade título y créditos
- Resalta áreas importantes
- Añade anotaciones/flechas si necesario
- Música de fondo suave (opcional)

---

## 📝 Guión Resumido

```
0:00 - Intro: "Soy [Tu nombre], les presento StockPro"
0:30 - Arquitectura: 4 pantallas, ViewModel compartido
1:30 - Login: Validación de nombre
2:00 - Catálogo: 6 productos, stock crítico en rojo
2:30 - Filtros: Ver Todo vs Stock Crítico
3:00 - Começar demo: Mouse con stock 3 (crítico)
3:30 - Editar: Abridor Edición, muestra stock 3
4:00 - Cambio: +1 dos veces hasta stock 5
4:45 - Sincronización: Vuelve a Catálogo, ¡automático!
5:00 - Explicación: mutableStateListOf, cómo funciona
6:00 - Reporte: Capital Total, métricas
7:00 - Otro cambio: Edita Audífonos, Reporte actualiza
7:45 - Conclusión: Best practices, arquitectura limpia
8:30 - Fin: "Gracias"
```

---

## ✅ Checklist Antes de Grabar

- [ ] App compilada y funcionando
- [ ] Emulador/dispositivo con resolución HD
- [ ] Audio configurado y probado
- [ ] Internet estable (si es en web)
- [ ] Guión preparado
- [ ] Datos en estado inicial
- [ ] Micrófono prueba 3 min antes
- [ ] Espacio disco suficiente
- [ ] Video procesador de videos limpio

---

## 🎯 Duración Objetivo

- **Mínimo**: 5 minutos (básico)
- **Recomendado**: 7-10 minutos (completo)
- **Máximo**: 15 minutos (muy detallado)

**Distribución:**
- Intro + Arquitectura: 2 min
- Demo Catálogo: 2 min
- Demo Sincronización: 3 min
- Demo Reporte: 2 min
- Conclusión: 1 min

---

## 📤 Entrega

Después de grabar:
1. Exportar video en MP4
2. Nombrar: `StockPro_Defensa_[TuNombre].mp4`
3. Enviar o compartir según instrucciones
4. Conservar guión para referencia

---

## 💡 Puntos Clave a Enfatizar

✅ **Validación**: Mostrar que el login valida correctamente
✅ **Sincronización**: Demostrar que cambios se reflejan automáticamente
✅ **Reactividad**: Explicar cómo mutableStateListOf funciona
✅ **Arquitectura**: Mostrar que la lógica está centralizada
✅ **Experiencia**: La app es responsiva y fluida

---

## 🎓 Conclusión

Este video debe demostrar:
1. Que entiendes cómo funciona la app
2. Que sabes explicar la arquitectura
3. Que comprendes reactividad
4. Que puedes comunicar técnicamente

¡Mucho éxito en tu defensa! 🚀

