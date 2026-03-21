# Informe de Desarrollo - BiblioSystem

**Proyecto:** BiblioSystem  
**Asignatura:** Introducción a la Programación y Computación 1  
**Universidad:** Universidad San Carlos de Guatemala  
**Facultad:** Ingeniería en Ciencias y Sistemas  
**Estudiante:** Gabriel Sales  
**Fecha:** 20/03/2026

---

## 1. Introducción

El proyecto BiblioSystem consiste en el desarrollo de un sistema de escritorio en Java para la gestión de bibliotecas universitarias. El sistema centraliza el control de usuarios, libros, préstamos y devoluciones, así como la generación de reportes en formato HTML.  
El objetivo principal es automatizar procesos que actualmente se manejan de forma manual, evitando inconsistencias y facilitando la administración del inventario y los préstamos de libros.

---

## 2. Objetivos del Proyecto

### General

Desarrollar una aplicación en Java que funcione como sistema de inventario para una biblioteca universitaria, gestionando libros, usuarios, préstamos y reportes.

### Específicos

- Implementar operaciones de inventario: agregar, eliminar y buscar libros.
- Registrar transacciones de préstamos y devoluciones.
- Generar reportes de stock y préstamos en HTML.
- Aplicar validaciones para mantener la integridad del sistema.
- Manejar archivos de texto para almacenamiento persistente.
- Integrar buenas prácticas de codificación y control de versiones.

---

## 3. Descripción del Sistema

### Contexto General

La Biblioteca Central Universitaria necesitaba un sistema que centralizara sus operaciones de préstamo y registro de usuarios. Antes, estas tareas eran parcialmente manuales, lo que provocaba inconsistencias en la disponibilidad de libros y dificultad para controlar préstamos vencidos.

### Roles de Usuario

- **Administrador:** Gestiona operadores, libros, estudiantes, préstamos y reportes.
- **Operador:** Gestiona libros, estudiantes, préstamos y reportes, pero no puede crear administradores.
- **Estudiante:** Puede registrarse y solicitar préstamos, además de consultar su historial de préstamos.

### Reglas de Negocio

- Un libro solo puede prestarse si tiene ejemplares disponibles.
- Máximo 3 préstamos activos por estudiante.
- Plazo máximo de préstamo: 15 días.
- No se pueden eliminar libros o estudiantes con préstamos activos.

---

## 4. Estructura del Proyecto

El proyecto sigue una variante de **MVC (Modelo-Vista-Controlador)**.

### Paquetes y Contenido

| Paquete       | Contenido                                                                                                                       |
| ------------- | ------------------------------------------------------------------------------------------------------------------------------- |
| **modelo**    | Clases de datos (`Usuario.java`, `Libro.java`, `Prestamo.java`)                                                                 |
| **control**   | Lógica del sistema (`SistemaUsuarios.java`, `SistemaLibros.java`, `SistemaPrestamos.java`)                                      |
| **vista**     | Interfaces gráficas (`Login.java`, `MenuPrincipal.java`, `GestionUsuarios.java`, `GestionLibros.java`, `GestionPrestamos.java`) |
| **principal** | Clase de arranque (`Main.java`)                                                                                                 |

#### Estructura de carpetas

src  
├── modelo  
├── control  
├── vista  
└── principal

---

## 5. Implementación

### Módulo de Autenticación

- Inicio de sesión según rol.
- Registro de estudiantes directamente desde la pantalla de login.
- Validación de credenciales desde `cuentas.txt`.

### Módulo de Gestión de Libros

- Registro, modificación, eliminación y búsqueda de libros.
- Gestión de cantidad de ejemplares y disponibilidad.
- Validaciones para evitar inconsistencias con préstamos activos.

### Módulo de Gestión de Estudiantes

- Consultar, listar y eliminar estudiantes.
- Control de estado: préstamos activos y vencidos.

### Módulo de Préstamos y Devoluciones

- Registro de préstamos por operador o estudiante.
- Devoluciones con actualización de inventario.
- Listado de préstamos activos y vencidos.
- Generación de reportes HTML.

### Bitácora de Operaciones

- Registra automáticamente todas las operaciones relevantes en `bitacora.txt`.
- Incluye inicio y cierre de sesión, modificaciones, creaciones y eliminaciones.

### Reportes

- Préstamos vencidos.
- Libros disponibles.
- 5 libros más prestados.
- Estudiantes con préstamos activos.

---

## 6. Problemas Encontrados y Soluciones

1. **Sincronización de inventario con préstamos activos:**  
   *Problema:* Al registrar préstamos, los libros no siempre reflejaban correctamente la disponibilidad.  
   *Solución:* Se implementó la actualización inmediata de la cantidad disponible al registrar un préstamo o devolución.

2. **Validación de préstamos máximos:**  
   *Problema:* Los estudiantes podían solicitar más de 3 préstamos activos.  
   *Solución:* Se agregó un método que verifica la cantidad de préstamos activos antes de aceptar uno nuevo.

3. **Integración modelo-vista-controlador:**  
   *Problema:* La tabla `JTable` no se actualizaba al modificar datos en los sistemas (`SistemaUsuarios`, `SistemaLibros`).  
   *Solución:* Se implementaron métodos de recarga de tabla después de cada modificación.

---

## 7. Capturas de Pantalla

**Login**


![Menú Principal](C:\Users\Andri\Documents\Login.PNG)


**Gestión de Libros**
![Gestión de Libros](C:\Users\Andri\Documents\Libros.PNG)

**Registro de Préstamos**
![Registro de Préstamos](C:\Users\Andri\Documents\Prestamos.PNG)

**Reportes Generados**
![Reporte](C:\Users\Andri\Documents\Prestamos.PNG)

---

## 8. Conclusiones

- Se logró centralizar la gestión de libros, usuarios y préstamos.
- Se implementaron controles de negocio y validaciones para mantener la integridad de los datos.
- La interfaz gráfica permite una interacción sencilla y clara para los tres tipos de usuarios.
- Se documentó y versionó correctamente el proyecto en GitHub.
- Las pruebas realizadas demostraron que el sistema cumple con los requerimientos solicitados.

---

## 9. Referencias

- Deitel, P. & Deitel, H. (2016). *Cómo programar JAVA* (10ª edición). Pearson Education.  
- Joyanes, L. (s.f.). *JAVA 2 Manual de Programación*. McGraw-Hill.
