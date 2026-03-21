# Diagrama de Flujo del Sistema BiblioSystem

Este documento muestra los diagramas de flujo simplificados del proyecto **BiblioSystem**, incluyendo el inicio del programa, el login y el menú principal con las operaciones según el rol del usuario.  

---  

## Flujo de Inicialización del Sistema (Principal)

```mermaid
flowchart TD
    A[Inicio del programa] --> B[Crear sistema de libros]
    B --> C[Crear sistema de prestamos]
    C --> D[Crear sistema de usuarios]
    D --> E[Crear sistema de reportes]
    E --> F[Cargar usuarios desde archivo]
    F --> G[Mostrar usuarios en consola]
    G --> H[Cargar prestamos desde archivo]
    H --> I[Mostrar mensaje inicio interfaz]
    I --> J[Abrir ventana de login]

    J --> K{Usuario existe}
    K -- Si --> L[Validar contraseña]
    K -- No --> M[Usuario crea cuenta estudiante]
    M --> N[Registrar estudiante en sistema]
    N --> L

    L --> O{Contraseña correcta}
    O -- Si --> P[Redirigir a menu principal segun rol]
    O -- No --> Q[Mostrar error usuario o contraseña]

    P --> R[Configurar permisos segun rol]
    R --> S{Rol del usuario}
    S -- Administrador --> T[Mostrar todas las opciones]
    S -- Operador --> U[Ocultar opciones de administracion de operadores]
    S -- Estudiante --> V[Ocultar opciones de operadores y libros]

    T --> W[Operaciones disponibles]
    U --> W
    V --> W

    W --> W1[Agregar modificar eliminar usuarios]
    W --> W2[Agregar modificar eliminar libros]
    W --> W3[Registrar prestamos y devoluciones]
    W --> W4[Generar reportes libros prestamos top5 estudiantes]
    W --> W5[Buscar en tablas usuarios libros operadores]

    W1 --> X[Actualizar tablas y datos]
    W2 --> X
    W3 --> X
    W4 --> X
    W5 --> X

    X --> Y[Registrar accion en bitacora]
```

# 

## Flujo Simplificado del Menú Principal

```mermaid
flowchart TD
    A[Inicio MenuPrincipal] --> B[Configurar permisos según rol]
    B --> C{Rol del usuario}
    C -- ADMIN --> D[Mostrar todas las opciones]
    C -- OPERADOR --> E[Ocultar opciones de administración de operadores]
    C -- ESTUDIANTE --> F[Ocultar opciones de operadores y libros]

    D --> G[Operaciones posibles]
    E --> G
    F --> G

    G --> G1[Agregar, modificar, eliminar usuarios/estudiantes]
    G --> G2[Agregar, modificar, eliminar libros]
    G --> G3[Registrar préstamos y devoluciones]
    G --> G4[Generar reportes: libros, préstamos, top 5, estudiantes]
    G --> G5[Buscar en tablas de usuarios, libros y operadores]

    G1 --> H[Actualizar tabla y datos]
    G2 --> H
    G3 --> H
    G4 --> H
    G5 --> H

    H --> I[Registrar acción en Bitácora]

    style A fill:#f9f,stroke:#333,stroke-width:2px
    style I fill:#9f9,stroke:#333,stroke-width:2px
```
