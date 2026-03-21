# Manual de Usuario - BiblioSystem

## 1. Introducción

BiblioSystem es una aplicación de escritorio desarrollada en **Java** que permite gestionar una biblioteca universitaria. A través de la interfaz gráfica, los usuarios pueden:

- Gestionar libros.
- Gestionar cuentas de estudiantes y operadores.
- Registrar préstamos y devoluciones.
- Generar reportes en formato HTML.

El sistema soporta tres tipos de usuarios con distintos niveles de acceso:

1. Administrador
2. Operador de biblioteca
3. Estudiante

---

## 2. Requisitos del sistema

- Java JDK 8 o superior.
- Sistema operativo compatible con Java (Windows, Linux, MacOS).
- No requiere base de datos; utiliza archivos de texto (`cuentas.txt`, `prestamos.txt`) para persistencia.
- IDE recomendado: NetBeans (opcional).

---

## 3. Inicio de sesión

### 3.1 Pantalla de login

Al abrir el programa, se presenta la ventana de **Inicio de sesión**, con las siguientes opciones:

- **Iniciar sesión:** Introduce usuario y contraseña.
- **Crear cuenta de estudiante:** Permite a nuevos estudiantes registrarse proporcionando:
  - Carné universitario
  - Nombre completo
  - Carrera
  - Contraseña

>  Las contraseñas se almacenan en texto plano en el archivo `cuentas.txt`.

---

## 4. Roles y permisos

### 4.1 Administrador

- Usuario: `admin`
- Contraseña: `admin`
- Funciones:
  - Crear y eliminar cuentas de operadores.
  - Visualizar listado de operadores.
  - Acceder a todos los módulos: libros, estudiantes, préstamos, reportes.

### 4.2 Operador de biblioteca

- Cuenta creada por el Administrador.
- Funciones:
  - Gestionar catálogo de libros: registrar, modificar, eliminar, buscar.
  - Gestionar estudiantes: consultar, listar, eliminar.
  - Registrar préstamos y devoluciones.
  - Generar reportes HTML.
- Restricciones:
  - No puede crear ni eliminar otros operadores.

### 4.3 Estudiante

- Registro propio desde la pantalla de login.
- Funciones:
  - Solicitar préstamos de libros.
  - Consultar historial de préstamos.
- Restricciones:
  - Solo puede ver su propia información.
  - No puede acceder a módulos administrativos ni ver información de otros usuarios.

---

## 5. Gestión de libros

### 5.1 Registrar libro

Se requiere la siguiente información:

- Código interno (único)
- ISBN (10 o 13 dígitos, único)
- Título
- Autor
- Género
- Año de publicación
- Cantidad de ejemplares (mínimo 1)

### 5.2 Modificar libro

- Búsqueda por código o ISBN.
- Se puede modificar toda la información excepto el código interno.
- La cantidad total de ejemplares no puede ser menor que los libros actualmente prestados.

### 5.3 Eliminar libro

- Solo se puede eliminar si no tiene préstamos activos.

### 5.4 Buscar y listar libros

- Búsqueda por ISBN, título o autor.
- Listado completo del catálogo con disponibilidad.

---

## 6. Gestión de estudiantes

### 6.1 Consultar estudiante

- Información visible: nombre, carrera, préstamos activos, préstamos vencidos.

### 6.2 Listar todos los estudiantes

- Muestra estado actual de todos los estudiantes.

### 6.3 Eliminar estudiante

- Solo si no tiene préstamos activos o vencidos pendientes.

---

## 7. Préstamos y devoluciones

### 7.1 Registrar préstamo (Operador/Admin)

- Introducir carné del estudiante y código/ISBN del libro.
- Validaciones automáticas:
  - Máximo 3 préstamos activos por estudiante.
  - Libro disponible.
  - Sin préstamos vencidos.
- Fecha límite: 15 días desde la fecha de préstamo.
- Estado: `ACTIVO`

### 7.2 Solicitud de préstamo (Estudiante)

- Funciona igual que el registro de operador, con confirmación de fecha límite de devolución.

### 7.3 Registrar devolución

- Buscar préstamo por código o carné.
- Estado actualizado a `DEVUELTO`.
- Incrementa la disponibilidad del libro.
- Actualiza `prestamos.txt`.

### 7.4 Listado de préstamos

- Muestra todos los préstamos activos, resaltando los vencidos.
- Acceso a historial del estudiante, ordenado de más reciente a más antiguo.

---

## 8. Bitácora de operaciones

- Archivo: `bitacora.txt`
- Registra todas las acciones relevantes:
  - Inicio/cierre de sesión
  - Creación, modificación o eliminación de datos
  - Intentos fallidos por reglas de negocio
- Formato:
