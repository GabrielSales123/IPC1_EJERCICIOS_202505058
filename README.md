BiblioSystem - Proyecto 1

BiblioSystem es un sistema de escritorio desarrollado en Java para la gestión de una biblioteca universitaria. Permite manejar libros, usuarios, préstamos y devoluciones, y generar reportes en HTML. La aplicación tiene interfaz gráfica con Swing y utiliza archivos de texto para persistencia de datos.

Estructura del proyecto:

src/
├── modelo (clases que representan datos: Usuario, Estudiante, Operador, Administrador, Libro, Prestamo)
├── control (lógica de negocio: SistemaUsuarios, SistemaLibros, SistemaPrestamos)
├── vista (interfaz gráfica: Login, MenuPrincipal, GestionUsuarios, GestionLibros, GestionPrestamos, Reportes)
└── principal (Main.java que inicia la aplicación)

Roles de usuario:

Administrador: usuario admin / contraseña admin. Gestiona operadores y tiene acceso completo a todos los módulos.

Operador: creado por administrador. Gestiona libros y estudiantes, registra préstamos y devoluciones, genera reportes.

Estudiante: crea su cuenta, solicita préstamos y consulta su historial. Acceso solo a sus propios datos.

Funcionalidades principales:

Gestión de libros: registrar, modificar, eliminar, buscar y listar.

Gestión de usuarios: registrar estudiantes y operadores, consultar y eliminar según permisos.

Préstamos y devoluciones: registro con validaciones de negocio (máximo 3 préstamos activos, 15 días de plazo, bloqueo si hay vencidos).

Bitácora: registro automático de acciones relevantes en bitacora.txt.

Reportes HTML: préstamos vencidos, libros disponibles, 5 libros más prestados, estudiantes con préstamos activos.

Archivos de soporte:

cuentas.txt: credenciales y datos de usuarios.

prestamos.txt: registro de préstamos.

bitacora.txt: historial de operaciones.

Los datos de libros y estudiantes se manejan en memoria y no se persisten.

Ejecución:

Abrir proyecto en NetBeans o IDE Java compatible.

Ejecutar la clase principal.Main.

Iniciar sesión con credenciales del rol.

Navegar por el menú y utilizar las funciones disponibles.

Consideraciones:

Código estructurado siguiendo buenas prácticas de Java.

No se utilizan librerías externas como ArrayList o HashMap.

La aplicación funciona únicamente con archivos de texto.

Reportes HTML se guardan en la carpeta de ejecución.

El administrador es único y definido en el código.
