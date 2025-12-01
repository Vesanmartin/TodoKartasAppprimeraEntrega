# TodoKartas App (Android)

Aplicación móvil desarrollada en **Kotlin + Jetpack Compose** para la tienda de cartas coleccionables **TodoKartas**.  
Permite explorar un catálogo de cartas, gestionar un carrito de compras y realizar autenticación básica de usuarios.

## 1. Nombre del proyecto

**TodoKartas App – Tienda de cartas coleccionables (Android)**

---

## 2. Integrantes

- Veronica San Martin
- Samuel Parra
- Sebastian Riquelme

> Asignatura: **DSY1105 – Desarrollo de Aplicaciones Móviles**  
Evaluación Parcial 4

## 3. Funcionalidades principales

- **Autenticación de usuarios**
  - Pantalla de login.
  - Pantalla de registro.
  - Validación de campos (formato de correo, contraseñas, usuario repetido).
  - Persistencia básica de sesión.

- **Catálogo de cartas**
  - Listado de cartas / productos con imagen, nombre y precio.
  - Búsqueda y filtrado básico (según la versión actual del proyecto).
  - Navegación a pantalla de detalle.

- **Carrito de compras**
  - Agregar productos desde el catálogo.
  - Aumentar / disminuir cantidades.
  - Eliminar productos del carrito.
  - Cálculo automático del total.

- **Consumo de API externa (Retrofit)**
  - Consumo de una API externa para mostrar información en una pantalla tipo “cards”.
  - Integrado en el flujo visual de la app usando Jetpack Compose.
    
- **Interfaz construida con Jetpack Compose**
  - Uso de `Scaffold`, `TopAppBar`, `Drawer`, `LazyColumn`, etc.
  - Navegación declarativa con `NavHost`.

- **Arquitectura**
  - Patrón **MVVM**:
    - Capa UI (Compose)
    - ViewModels (estado y lógica de presentación)
    - Data (repositorios locales / remotos)


## 4. Endpoints utilizados

### 4.1 API externa

- **Descripción:**  
  API pública que entrega datos para poblar la pantalla de cards (listado externo).

- **Ejemplo (reemplazar por el que realmente usan en el proyecto):**
  - Base URL: `https://TU_API_EXTERNA.com/`
  - Endpoint principal:  
    `GET /items` → retorna listado de elementos (título, imagen, descripción, etc.)

- **Uso en la app:**
  - Se consume a través de **Retrofit**.
  - Los datos se muestran en una pantalla de listado (por ejemplo `CardScreen`), integrada al flujo de navegación.


