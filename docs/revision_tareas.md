# Revisión del código base y tareas propuestas

## 1) Tarea para corregir un error tipográfico
**Hallazgo:** En la barra superior de la interfaz principal aparece el texto **"Sistema Gestión de Producción"**, que omite la preposición "de" y se lee como un error de redacción visible para el usuario.

**Evidencia:** `src/main/resources/view/main.fxml`.

**Tarea propuesta:**
- Cambiar la etiqueta a **"Sistema de Gestión de Producción"** en `main.fxml`.
- Revisar el resto de textos visibles para detectar errores similares de redacción/acentuación.

---

## 2) Tarea para corregir una falla funcional
**Hallazgo:** En `MaterialesController.agregarMaterial()`, los campos de stock se convierten con `Integer.parseInt(...)` sin validación. Si el usuario escribe texto no numérico, se lanza `NumberFormatException` y la operación falla.

**Evidencia:** `src/main/java/com/ciber/controller/MaterialesController.java`.

**Tarea propuesta:**
- Validar que los valores ingresados sean numéricos antes de parsear.
- Mostrar un `Alert` de error cuando el valor sea inválido.
- Repetir el diálogo o cancelar de forma segura sin romper el flujo de la UI.

---

## 3) Tarea para corregir discrepancia en comentarios/documentación
**Hallazgo:** El botón de menú lateral se llama **"Dashboard"** (`main.fxml`), pero la pantalla destino muestra el título **"Dashboard Producción"** (`dashboard.fxml`). Esta inconsistencia de nomenclatura genera una discrepancia entre textos de navegación y contenido.

**Evidencia:** `src/main/resources/view/main.fxml` y `src/main/resources/view/dashboard.fxml`.

**Tarea propuesta:**
- Unificar nomenclatura (por ejemplo, usar **"Dashboard de Producción"** tanto en menú como en encabezado).
- Definir una guía breve de copy UI para nombres de módulos y encabezados.

---

## 4) Tarea para mejorar una prueba
**Hallazgo:** El repositorio no contiene pruebas automatizadas para los flujos críticos detectados (validación de entrada y reglas de stock).

**Evidencia:** estructura actual de `src/main/java` y ausencia de directorio de pruebas.

**Tarea propuesta:**
- Añadir pruebas unitarias para `Material`:
  - `sumarStock` incrementa correctamente.
  - `restarStock` no permite valores negativos (si se define esa regla).
- Añadir prueba de controlador (o de lógica extraída) para validar entradas numéricas en alta de material.
- Criterio de aceptación: falla si hay entrada no numérica y pasa con entradas válidas.
