# BiblioSystem - Manual Técnico

## Información General

- **Curso:** Introducción a la Programación y Computación 1  
- **Lenguaje:** Java  
- **Autor:** Gabriel Sales  

---

## Descripción del Sistema

BiblioSystem es una aplicación de escritorio desarrollada en Java que permite gestionar una biblioteca universitaria.  

El sistema incluye funcionalidades para:

- Gestión de usuarios (estudiantes y operadores)
- Gestión de libros
- Control de préstamos y devoluciones
- Generación de reportes
- Registro de operaciones mediante bitácora

---

## Arquitectura del Sistema

El sistema sigue el patrón **MVC (Modelo - Vista - Controlador)**:

### Modelo

Contiene las clases que representan los datos:

- `Usuario`
- `Estudiante`
- `Operador`
- `Libro`
- `Prestamo`

### Controlador

Contiene la lógica del sistema:

- `SistemaUsuarios`
- `SistemaLibros`
- `SistemaPrestamos`
- `SistemaReportes`
- `Bitacora`

### Vista

Contiene la interfaz gráfica (Swing):

- `Login`
- `MenuPrincipal`
- `GestionUsuarios`
- `GestionLibros`
- `GestionPrestamos`

---

## Manejo de Archivos

El sistema utiliza archivos de texto para persistencia:

### cuentas.txt

Almacena usuarios del sistema.

**Formato:**

usuario;nombre;password;ROL;[datos adicionales]

### prestamos.txt

Almacena los préstamos realizados.

### bitacora.txt

Registra todas las operaciones del sistema.

**Formato:**

[OPERACION][USUARIO][MODULO][FECHA][HORA]

---

## Módulos del Sistema

### Autenticación

Permite iniciar sesión según el rol:

- Administrador
- Operador
- Estudiante

---

### Gestión de Usuarios

Permite:

- Crear usuarios
- Eliminar usuarios
- Consultar información

---

### Gestión de Libros

Permite:

- Registrar libros
- Modificar libros
- Eliminar libros
- Buscar libros

---

### Préstamos y Devoluciones

Permite:

- Registrar préstamos
- Registrar devoluciones
- Consultar historial

---

## Reglas de Negocio Implementadas

- Un estudiante puede tener máximo **3 préstamos activos**
- No se permite prestar libros sin stock
- No se permiten préstamos si existen vencidos
- No se pueden eliminar libros con préstamos activos

---

## Estructuras de Datos

El sistema utiliza:

- **Arreglos (vectores)** para almacenar datos
- **Ciclos for y while** para recorridos
- **Condicionales** para validaciones

---

## Validaciones Implementadas

- Validación de datos vacíos
- Validación de duplicados
- Validación de stock disponible
- Validación de préstamos activos

---

## Bitácora

El sistema registra automáticamente:

- Inicio de sesión
- Operaciones realizadas
- Errores del sistema

Ejemplo:

[PRESTAMO][202500101][PRESTAMOS][20/03/26][06:30 PM]

---  

## Generación de Reportes

El sistema genera reportes en HTML:  

- Préstamos vencidos  
- Libros disponibles  
- Top 5 libros más prestados  
- Estudiantes con préstamos activos  

---  

## Ejecución del Sistema

1. Ejecutar la clase `Principal.java`  
2. Iniciar sesión  
3. Acceder al menú según el rol  

---  

## Consideraciones Técnicas

- No se utilizan estructuras dinámicas (ArrayList, HashMap)  
- Uso exclusivo de arreglos  
- Persistencia mediante archivos de texto  
- Interfaz desarrollada con Swing  

---  

## Problemas y Soluciones

### Problema:

Error al cargar imágenes (`NullPointerException`)  

### Solución:

Se corrigió la ruta usando:  

```java  
getClass().getResource("/imagenes/Logo.jpeg")
```


