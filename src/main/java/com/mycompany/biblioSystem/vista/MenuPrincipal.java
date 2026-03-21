
package com.mycompany.biblioSystem.vista;
import com.mycompany.biblioSystem.controlador.*;
import com.mycompany.biblioSystem.modelo.*;
import javax.swing.DefaultComboBoxModel;
import javax.swing.table.DefaultTableModel;


public class MenuPrincipal extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(MenuPrincipal.class.getName());
    private SistemaUsuarios sistema;
    private SistemaLibros sistemalib;
    private SistemaPrestamos sistemap;
    private SistemaReportes sistemarep;
    private Usuario usuarioActual;
    /**
     * Creates new form MenuPrincipal
     */
    public MenuPrincipal(SistemaUsuarios sistema, SistemaLibros sistemalib, 
            SistemaPrestamos sistemap, SistemaReportes sistemarep, Usuario usuarioActual) {
        this.sistema = sistema;
        this.sistemalib = sistemalib;
        this.sistemap = sistemap;
        this.sistemarep = sistemarep;
        this.usuarioActual = usuarioActual;
        initComponents();
        configurarPermisos();
        cargarTablaOperadores();
        cargarTablaUsuarios();
        cargarTablaLibros();
        cargarTablaPrestamos();
        iniciarFiltroPrestamos();
        iniciarReportes();
        mostrarLibrosDisponibles();
        cantUsuarios.setText("Usuarios: "+(sistema.getTotalUsuarios()-1)+"/50");
        cantLibros.setText("Libros: "+(sistemalib.getTotalLibros())+"/100");
        cantPrestamos.setText("Prestamos activos: "+ (sistemap.getPrestamosActivos())  +"/200");
        
    }

    private void configurarPermisos() {

        String rol = usuarioActual.getRol();

        if (rol.equals("ADMIN")) {
            tipoSesion.setText("Sesion: Administrador del Sistema");
            tipoRol.setText("Rol: Administrador");
            Bitacora.registrar("LOGIN", usuarioActual.getId(), "AUTENTICACION");
           
        } 
        else if (rol.equals("OPERADOR")) {
            menu.remove(4); 
            tipoSesion.setText("Sesion: Operador del Sistema");
            tipoRol.setText("Rol: Operador");
            Bitacora.registrar("LOGIN", usuarioActual.getId(), "AUTENTICACION");
        } 
        else if (rol.equals("ESTUDIANTE")) {
            tipoSesion.setText("Sesion: Estudiante");
            tipoRol.setText("Rol: Estudiante");
            Bitacora.registrar("LOGIN", usuarioActual.getId(), "AUTENTICACION");
            menu.remove(4); 
            menu.remove(3); 
            menu.remove(1);
        }
}
    
    
    
    public void iniciarReportes(){
    comboReportes.setModel(new javax.swing.DefaultComboBoxModel<>(
    new String[] { 
        "Libros disponibles", 
        "Préstamos vencidos", 
        "Top 5 libros", 
        "Estudiantes activos" 
    }
    ));
    }
    
    private void mostrarLibrosDisponibles() {

    DefaultTableModel modelo = new DefaultTableModel();
    modelo.setColumnIdentifiers(new String[]{"Código", "Título", "Disponibles"});

    tablaReportes.setModel(modelo);

    Libro[] lista = sistemalib.getLibros();

    for (int i = 0; i < sistemalib.getTotalLibros(); i++) {

        Libro l = lista[i];

        if (l.getDisponibles() > 0) {
            modelo.addRow(new Object[]{
                l.getCodigo(),
                l.getTitulo(),
                l.getDisponibles()
            });
        }
    }
}
    
    private void mostrarPrestamosVencidos() {

    DefaultTableModel modelo = new DefaultTableModel();
    modelo.setColumnIdentifiers(new String[]{"Código", "Carnet", "Libro", "Fecha"});

    tablaReportes.setModel(modelo);

    Prestamo[] lista = sistemap.getPrestamos();

    for (int i = 0; i < sistemap.getTotPrestamos(); i++) {

        Prestamo p = lista[i];

        if (p.getEstado().equals("VENCIDO")) {

            modelo.addRow(new Object[]{
                p.getCodigoPrestamo(),
                p.getCarnet(),
                sistemalib.getNombreLibroPorCodigo(p.getCodigoLibro()),
                p.getFechaLimite()
            });
        }
    }
}
    
    private void mostrarTop5() {

    DefaultTableModel modelo = new DefaultTableModel();
    modelo.setColumnIdentifiers(new String[]{"Título", "Veces prestado"});

    tablaReportes.setModel(modelo);

    Libro[] listaLibros = sistemalib.getLibros();
    Prestamo[] listaPrestamos = sistemap.getPrestamos();

    int n = sistemalib.getTotalLibros();
    int[] contador = new int[n];

    for (int i = 0; i < sistemap.getTotPrestamos(); i++) {

        Prestamo p = listaPrestamos[i];

        for (int j = 0; j < n; j++) {
            if (listaLibros[j].getCodigo().equals(p.getCodigoLibro())) {
                contador[j]++;
            }
        }
    }

    for (int i = 0; i < n - 1; i++) {
        for (int j = 0; j < n - i - 1; j++) {

            if (contador[j] < contador[j + 1]) {

                int temp = contador[j];
                contador[j] = contador[j + 1];
                contador[j + 1] = temp;

                Libro aux = listaLibros[j];
                listaLibros[j] = listaLibros[j + 1];
                listaLibros[j + 1] = aux;
            }
        }
    }

    for (int i = 0; i < 5 && i < n; i++) {
        modelo.addRow(new Object[]{
            listaLibros[i].getTitulo(),
            contador[i]
        });
    }
}
    
    private void mostrarEstudiantesActivos() {

    DefaultTableModel modelo = new DefaultTableModel();
    modelo.setColumnIdentifiers(new String[]{"Carnet", "Nombre", "Activos", "Vencidos"});

    tablaReportes.setModel(modelo);

    Usuario[] lista = sistema.getUsuarios();

    for (int i = 0; i < sistema.getTotalUsuarios(); i++) {

        if (lista[i] instanceof Estudiante) {

            Estudiante e = (Estudiante) lista[i];

            int activos = sistema.getPrestamosEstudiantePorCarnet(e.getCarne());
            boolean vencidos = sistema.getEstadoEstudiantePorCarnet(e.getCarne());

            if (activos > 0) {
                modelo.addRow(new Object[]{
                    e.getCarne(),
                    e.getNombre(),
                    activos,
                    (vencidos ? "Sí" : "No")
                });
            }
        }
    }
}
    
    public void cargarTablas(){
        cargarTablaOperadores();
        cargarTablaUsuarios();
        cargarTablaLibros();
        cargarTablaPrestamos();
    }
    public void cargarTablaOperadores() {
   DefaultTableModel modelo = (DefaultTableModel) tablaOperadores.getModel();
   modelo.setRowCount(0);
   Usuario[] operadores = sistema.getOperadores();
    for (int i = 0; i < operadores.length; i++) {
        modelo.addRow(new Object[]{
            operadores[i].getId(),
            operadores[i].getNombre(),
            operadores[i].getPassword()
        });
    }
    }
    
    public void buscarTablaOperadores(String busqueda) {
        DefaultTableModel modelo = (DefaultTableModel) tablaOperadores.getModel();
   modelo.setRowCount(0);
   Usuario[] operadores = sistema.getOperadores();
   
    for (int i = 0; i < operadores.length; i++) {
       
        if(operadores[i].getNombre().toLowerCase().contains(busqueda.toLowerCase()) ||
           operadores[i].getId().toLowerCase().contains(busqueda.toLowerCase()) ||
           operadores[i].getPassword().toLowerCase().contains(busqueda.toLowerCase())){
        modelo.addRow(new Object[]{
            operadores[i].getId(),
            operadores[i].getNombre(),
            operadores[i].getPassword()
        });
    }
    }
    }
    
    
    
    public void cargarTablaUsuarios() {
   DefaultTableModel modelo = (DefaultTableModel) tablaUsuarios.getModel();
   modelo.setRowCount(0);
   Usuario[] usuarios = sistema.getEstudiantes();
    for (int i = 0; i < usuarios.length; i++) {
        if (usuarios[i] instanceof Estudiante) {
        Estudiante estudiante = (Estudiante) usuarios[i];
        modelo.addRow(new Object[]{
           estudiante.getCarne(),
           estudiante.getNombre(),
           estudiante.getCarrera(),
           estudiante.getCorreo()
        });
    }
    }
    }
    
    public void buscarTablaUsuarios(String busqueda) {
        DefaultTableModel modelo = (DefaultTableModel) tablaUsuarios.getModel();
   modelo.setRowCount(0);
   Usuario[] usuarios = sistema.getEstudiantes();
   
    for (int i = 0; i < usuarios.length; i++) {
       if (usuarios[i] instanceof Estudiante) {
            Estudiante estudiante = (Estudiante) usuarios[i];
        if(estudiante.getCarne().toLowerCase().contains(busqueda.toLowerCase()) ||
           estudiante.getNombre().toLowerCase().contains(busqueda.toLowerCase()) ||
           estudiante.getCarrera().toLowerCase().contains(busqueda.toLowerCase()) ||
           estudiante.getCorreo().toLowerCase().contains(busqueda.toLowerCase())){
        modelo.addRow(new Object[]{
            estudiante.getCarne(),
            estudiante.getNombre(),
            estudiante.getCarrera(),
            estudiante.getCorreo()
        });
    }
    }
    }
    }
    
   public void cargarTablaLibros() {
   DefaultTableModel modelo = (DefaultTableModel) tablaLibros.getModel();
   modelo.setRowCount(0);
   Libro[] lib = sistemalib.getLibros();
    for (int i = 0; i < lib.length; i++) {
        modelo.addRow(new Object[]{
            lib[i].getCodigo(),
            lib[i].getISBN(),
            lib[i].getTitulo(),
            lib[i].getAutor(),
            lib[i].getGenero(),
            lib[i].getAnio(),
            lib[i].getDisponibles()
        });
    }
    cargarTablaPrestamos();
    }
    
    public void buscarTablaLibros(String busqueda) {
        DefaultTableModel modelo = (DefaultTableModel) tablaLibros.getModel();
        modelo.setRowCount(0);
        Libro[] lib = sistemalib.getLibros();
    for (int i = 0; i < lib.length; i++) {
        if(lib[i].getCodigo().toLowerCase().contains(busqueda.toLowerCase()) ||
           lib[i].getISBN().toLowerCase().contains(busqueda.toLowerCase()) ||
           lib[i].getTitulo().toLowerCase().contains(busqueda.toLowerCase()) ||
           lib[i].getAutor().toLowerCase().contains(busqueda.toLowerCase()) ||
           lib[i].getGenero().toLowerCase().contains(busqueda.toLowerCase()) ||
           lib[i].getAnio().toLowerCase().contains(busqueda.toLowerCase()) ||
           String.valueOf(lib[i].getDisponibles()).contains(busqueda.toLowerCase())){
        modelo.addRow(new Object[]{
            lib[i].getCodigo(),
            lib[i].getISBN(),
            lib[i].getTitulo(),
            lib[i].getAutor(),
            lib[i].getGenero(),
            lib[i].getAnio(),
            lib[i].getDisponibles()
        });
    }
    }
    }
    
    public void cargarTablaPrestamos() {
         DefaultTableModel modelo = (DefaultTableModel) tablaPrestamos.getModel();
    modelo.setRowCount(0);

    Prestamo[] p = sistemap.getPrestamos();

    if (!usuarioActual.getRol().equals("ESTUDIANTE")) {

        
        for (int i = 0; i < sistemap.getTotPrestamos(); i++) {

            String nombreEstudiante = sistema.getNombreEstudiantePorCarnet(p[i].getCarnet());
            String nombreLibro = sistemalib.getNombreLibroPorCodigo(p[i].getCodigoLibro());

            modelo.addRow(new Object[]{
                p[i].getCodigoPrestamo(),
                nombreEstudiante,
                nombreLibro,
                p[i].getFechaPrestamo(),
                p[i].getFechaLimite(),
                p[i].getEstado()
            });
        }

    } else {

        
        String carnet = ((Estudiante) usuarioActual).getCarne();

        for (int i = 0; i < sistemap.getTotPrestamos(); i++) {

            if (p[i].getCarnet().equals(carnet)) {

                String nombreEstudiante = sistema.getNombreEstudiantePorCarnet(p[i].getCarnet());
                String nombreLibro = sistemalib.getNombreLibroPorCodigo(p[i].getCodigoLibro());

                modelo.addRow(new Object[]{
                    p[i].getCodigoPrestamo(),
                    nombreEstudiante,
                    nombreLibro,
                    p[i].getFechaPrestamo(),
                    p[i].getFechaLimite(),
                    p[i].getEstado()
                });
            }
        }
    }
    }  
    
    public void actualizarDatos(){
        String Usuarios = String.valueOf(sistema.getTotalUsuarios()-1);
        String Libros = String.valueOf(sistemalib.getTotalLibros());
        String Prestamos = String.valueOf(sistemap.getPrestamosActivos());
        cantUsuarios.setText("Usuarios: "+Usuarios+"/50");
        cantLibros.setText("Libros: "+Libros+"/100");
        cantPrestamos.setText("Prestamos activos: "+ Prestamos +"/200");
    }
    
    public void iniciarFiltroPrestamos(){
        DefaultComboBoxModel<String> modelo = (DefaultComboBoxModel<String>) tipoDevolucion.getModel();
        modelo.removeAllElements();
        modelo.addElement("Seleccione tipo Prestamo");
        modelo.addElement("ACTIVO");
        modelo.addElement("VENCIDO");
        modelo.addElement("DEVUELTO");
        tipoDevolucion.setSelectedIndex(0);
        seleccionPrestamos();
    
    }
    
    public void seleccionPrestamos(){
        String seleccionado = (String) tipoDevolucion.getSelectedItem();
            if (seleccionado == null || seleccionado.equals("Seleccione tipo Prestamo")) {
        seleccionado = ""; 
        }
        buscarTablaPrestamos(seleccionado);
    }
    
    
    public void buscarTablaPrestamos(String busqueda) {
    DefaultTableModel modelo = (DefaultTableModel) tablaPrestamos.getModel();
    modelo.setRowCount(0);
    Prestamo[] p = sistemap.getPrestamos();

    String rol = usuarioActual.getRol();
    String carnet = usuarioActual.getId();

    for (int i = 0; i < sistemap.getTotPrestamos(); i++) {

        String estado = p[i].getEstado();

        if (estado != null && estado.toLowerCase().contains(busqueda.toLowerCase())) {

         
            if (!rol.equals("ESTUDIANTE") || 
                p[i].getCarnet().trim().equals(carnet.trim())) {

                String nombreEstudiante = sistema.getNombreEstudiantePorCarnet(p[i].getCarnet());
                String nombreLibro = sistemalib.getNombreLibroPorCodigo(p[i].getCodigoLibro());

                modelo.addRow(new Object[]{
                    p[i].getCodigoPrestamo(),
                    nombreEstudiante,
                    nombreLibro,
                    p[i].getFechaPrestamo(),
                    p[i].getFechaLimite(),
                    p[i].getEstado()
                });
            }
        }
    }

    }
    
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenu6 = new javax.swing.JMenu();
        jMenu7 = new javax.swing.JMenu();
        jPanel1 = new javax.swing.JPanel();
        tipoSesion = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        tipoRol = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        cantLibros = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        cantUsuarios = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        cantPrestamos = new javax.swing.JLabel();
        menu = new javax.swing.JTabbedPane();
        menuLibros = new javax.swing.JScrollPane();
        modificarLibro = new javax.swing.JPanel();
        nuevoLibro = new javax.swing.JToggleButton();
        editarLibro = new javax.swing.JButton();
        eliminarLibro = new javax.swing.JButton();
        buscarLibros = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        scrollLibros = new javax.swing.JScrollPane();
        tablaLibros = new javax.swing.JTable();
        menuUsuarios = new javax.swing.JScrollPane();
        jPanel3 = new javax.swing.JPanel();
        nuevoUsuario = new javax.swing.JButton();
        modificarUsuario = new javax.swing.JButton();
        eliminarUsuario = new javax.swing.JButton();
        buscarUsuario = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        scrollUsuarios = new javax.swing.JScrollPane();
        tablaUsuarios = new javax.swing.JTable();
        menuPrestamos = new javax.swing.JScrollPane();
        jPanel4 = new javax.swing.JPanel();
        nuevoPrestamo = new javax.swing.JButton();
        registrarPrestamo = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        tipoDevolucion = new javax.swing.JComboBox<>();
        jScrollPane7 = new javax.swing.JScrollPane();
        tablaPrestamos = new javax.swing.JTable();
        menuReportes = new javax.swing.JScrollPane();
        jPanel5 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        comboReportes = new javax.swing.JComboBox<>();
        btnMostrar = new javax.swing.JButton();
        btnGenerar = new javax.swing.JButton();
        jLabel15 = new javax.swing.JLabel();
        scrollReportes = new javax.swing.JScrollPane();
        tablaReportes = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        nuevoOperador = new javax.swing.JButton();
        modificarOperador = new javax.swing.JButton();
        eliminarOperador = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        buscadorOperadores = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaOperadores = new javax.swing.JTable();
        menuBar = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        edicion = new javax.swing.JMenu();
        ver = new javax.swing.JMenu();
        herramientas = new javax.swing.JMenu();
        ayuda = new javax.swing.JMenu();

        jMenu6.setText("jMenu6");

        jMenu7.setText("jMenu7");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel1.setPreferredSize(new java.awt.Dimension(678, 27));

        tipoSesion.setText("Sesion: Administrador del Sistema");

        jLabel2.setText("|");

        tipoRol.setText("Rol: Administrador");

        jLabel4.setText("|");

        cantLibros.setText("Libros: 100/100");

        jLabel6.setText("|");

        cantUsuarios.setText("Usuarios: 50/50");

        jLabel8.setText("|");

        cantPrestamos.setText("Prestamos activos: 200/200");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tipoSesion, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 7, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tipoRol, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cantLibros)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cantUsuarios)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cantPrestamos)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(tipoSesion)
                    .addComponent(tipoRol)
                    .addComponent(jLabel4)
                    .addComponent(cantLibros)
                    .addComponent(jLabel6)
                    .addComponent(cantUsuarios)
                    .addComponent(jLabel8)
                    .addComponent(cantPrestamos))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        nuevoLibro.setText("Nuevo ");
        nuevoLibro.addActionListener(this::nuevoLibroActionPerformed);

        editarLibro.setText("Modificar");
        editarLibro.addActionListener(this::editarLibroActionPerformed);

        eliminarLibro.setText("Eliminar");
        eliminarLibro.addActionListener(this::eliminarLibroActionPerformed);

        buscarLibros.addActionListener(this::buscarLibrosActionPerformed);
        buscarLibros.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                buscarLibrosKeyReleased(evt);
            }
        });

        jLabel10.setText("Buscar :");

        tablaLibros.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Codigo", "ISBN", "Título", "Autor", "Genero", "Año", "Disponibles"
            }
        ));
        scrollLibros.setViewportView(tablaLibros);

        javax.swing.GroupLayout modificarLibroLayout = new javax.swing.GroupLayout(modificarLibro);
        modificarLibro.setLayout(modificarLibroLayout);
        modificarLibroLayout.setHorizontalGroup(
            modificarLibroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(modificarLibroLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(modificarLibroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(scrollLibros)
                    .addGroup(modificarLibroLayout.createSequentialGroup()
                        .addComponent(nuevoLibro)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(editarLibro)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(eliminarLibro)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 69, Short.MAX_VALUE)
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(buscarLibros, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        modificarLibroLayout.setVerticalGroup(
            modificarLibroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(modificarLibroLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(modificarLibroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nuevoLibro)
                    .addComponent(editarLibro)
                    .addComponent(eliminarLibro)
                    .addComponent(buscarLibros, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scrollLibros, javax.swing.GroupLayout.DEFAULT_SIZE, 363, Short.MAX_VALUE)
                .addContainerGap())
        );

        menuLibros.setViewportView(modificarLibro);

        menu.addTab("Libros ", menuLibros);

        nuevoUsuario.setText("Nuevo ");
        nuevoUsuario.addActionListener(this::nuevoUsuarioActionPerformed);

        modificarUsuario.setText("Modificar");
        modificarUsuario.addActionListener(this::modificarUsuarioActionPerformed);

        eliminarUsuario.setText("Eliminar");
        eliminarUsuario.addActionListener(this::eliminarUsuarioActionPerformed);

        buscarUsuario.addActionListener(this::buscarUsuarioActionPerformed);
        buscarUsuario.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                buscarUsuarioKeyReleased(evt);
            }
        });

        jLabel11.setText("Buscar :");

        tablaUsuarios.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "No. Carné", "Nombre completo", "Carrera", "Correo electrónico"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, true, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        scrollUsuarios.setViewportView(tablaUsuarios);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(scrollUsuarios)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(nuevoUsuario)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(modificarUsuario)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(eliminarUsuario)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 31, Short.MAX_VALUE)
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(buscarUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 374, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nuevoUsuario)
                    .addComponent(modificarUsuario)
                    .addComponent(eliminarUsuario)
                    .addComponent(buscarUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scrollUsuarios, javax.swing.GroupLayout.DEFAULT_SIZE, 363, Short.MAX_VALUE)
                .addContainerGap())
        );

        menuUsuarios.setViewportView(jPanel3);

        menu.addTab("Usuarios", menuUsuarios);

        nuevoPrestamo.setText("Nuevo prestamo");
        nuevoPrestamo.addActionListener(this::nuevoPrestamoActionPerformed);

        registrarPrestamo.setText("Registrar devolución");
        registrarPrestamo.addActionListener(this::registrarPrestamoActionPerformed);

        jLabel12.setText("Filtro:");

        tipoDevolucion.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        tipoDevolucion.addActionListener(this::tipoDevolucionActionPerformed);

        tablaPrestamos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Usuario", "Libro", "Fecha de prestamo", "Fecha de devolución", "Estado"
            }
        ));
        jScrollPane7.setViewportView(tablaPrestamos);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 690, Short.MAX_VALUE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(nuevoPrestamo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(registrarPrestamo)
                        .addGap(50, 50, 50)
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tipoDevolucion, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nuevoPrestamo)
                    .addComponent(registrarPrestamo)
                    .addComponent(jLabel12)
                    .addComponent(tipoDevolucion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 363, Short.MAX_VALUE)
                .addContainerGap())
        );

        menuPrestamos.setViewportView(jPanel4);

        menu.addTab("Prestamos", menuPrestamos);

        jPanel6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel13.setText("Seleccione un reporte: ");

        jLabel14.setText("Reporte: ");

        comboReportes.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        btnMostrar.setText("Mostrar");
        btnMostrar.addActionListener(this::btnMostrarActionPerformed);

        btnGenerar.setText("Exportar");
        btnGenerar.addActionListener(this::btnGenerarActionPerformed);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel13)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel14)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(comboReportes, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnMostrar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnGenerar)))
                .addContainerGap(313, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(comboReportes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14)
                    .addComponent(btnMostrar)
                    .addComponent(btnGenerar))
                .addContainerGap())
        );

        jLabel15.setText("Libros o el reporte");

        tablaReportes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        scrollReportes.setViewportView(tablaReportes);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(scrollReportes, javax.swing.GroupLayout.DEFAULT_SIZE, 690, Short.MAX_VALUE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel15)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scrollReportes, javax.swing.GroupLayout.DEFAULT_SIZE, 306, Short.MAX_VALUE)
                .addContainerGap())
        );

        menuReportes.setViewportView(jPanel5);

        menu.addTab("Reportes", menuReportes);

        nuevoOperador.setText("Nuevo");
        nuevoOperador.addActionListener(this::nuevoOperadorActionPerformed);

        modificarOperador.setText("Modificar");
        modificarOperador.addActionListener(this::modificarOperadorActionPerformed);

        eliminarOperador.setText("Eliminar");
        eliminarOperador.addActionListener(this::eliminarOperadorActionPerformed);

        jLabel1.setText("Buscar:");

        buscadorOperadores.addActionListener(this::buscadorOperadoresActionPerformed);
        buscadorOperadores.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                buscadorOperadoresKeyReleased(evt);
            }
        });

        tablaOperadores.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Id", "Nombre", "Password"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tablaOperadores);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 692, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(nuevoOperador)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(modificarOperador)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(eliminarOperador)
                        .addGap(40, 40, 40)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(buscadorOperadores)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(buscadorOperadores, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(nuevoOperador)
                        .addComponent(modificarOperador)
                        .addComponent(eliminarOperador)
                        .addComponent(jLabel1)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 365, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        menu.addTab("Operadores", jPanel2);

        jMenu1.setText("Archivo");
        menuBar.add(jMenu1);

        edicion.setText("Edición");
        menuBar.add(edicion);

        ver.setText("Ver");
        menuBar.add(ver);

        herramientas.setText("Herramientas");
        menuBar.add(herramientas);

        ayuda.setText("Ayuda");
        menuBar.add(ayuda);

        setJMenuBar(menuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 704, Short.MAX_VALUE)
            .addComponent(menu)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(menu, javax.swing.GroupLayout.PREFERRED_SIZE, 441, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tipoDevolucionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tipoDevolucionActionPerformed
        seleccionPrestamos();
    }//GEN-LAST:event_tipoDevolucionActionPerformed

    private void registrarPrestamoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_registrarPrestamoActionPerformed
        int fila = tablaPrestamos.getSelectedRow();
        if (fila != -1) {
        String cambio = tablaPrestamos.getValueAt(fila, 0).toString();
        registroDevolucion dialog = new registroDevolucion(null, true, sistemap, cambio);
        dialog.setVisible(true);  
        Bitacora.registrar("DEVOLUCION_PRESTAMO", usuarioActual.getId(), "SISTEMAPRESTAMOS");
    }
        cargarTablas();
    }//GEN-LAST:event_registrarPrestamoActionPerformed

    private void modificarUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_modificarUsuarioActionPerformed
        int fila = tablaUsuarios.getSelectedRow();
        if (fila != -1) {
        String cambio = tablaUsuarios.getValueAt(fila, 0).toString();
        String id = sistema.buscarPorCarne(cambio);
        editarEstudiante dialog = new editarEstudiante(null, true, sistema, id);
        dialog.setVisible(true);
}
        cargarTablaUsuarios();
        Bitacora.registrar("MODIFICAR_USUARIO", usuarioActual.getId(), "SISTEMAUSUARIOS");
    }//GEN-LAST:event_modificarUsuarioActionPerformed

    private void nuevoUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nuevoUsuarioActionPerformed
        agregarEstudiante dialog = new agregarEstudiante(this, true, sistema); 
        Bitacora.registrar("AGREGAR_USUARIO", usuarioActual.getId(), "SISTEMAUSUARIOS");
        dialog.setVisible(true);
        cargarTablaUsuarios();
        actualizarDatos();                    
    }//GEN-LAST:event_nuevoUsuarioActionPerformed

    private void buscarLibrosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buscarLibrosActionPerformed
        String texto = buscarLibros.getText();
        if (texto.isEmpty()) {
        cargarTablaLibros();
    } else {
        buscarTablaLibros(texto); 
    }
    }//GEN-LAST:event_buscarLibrosActionPerformed

    private void eliminarLibroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_eliminarLibroActionPerformed
        int fila = tablaLibros.getSelectedRow();
       if (fila != -1) {
       String codigo = tablaLibros.getValueAt(fila, 0).toString();
       sistemalib.eliminarLibro(codigo);
       Bitacora.registrar("ELIMINAR_LIBRO", usuarioActual.getId(), "SISTEMALIBROS");
       }
       cargarTablaLibros();
       actualizarDatos();
                     
    }//GEN-LAST:event_eliminarLibroActionPerformed

    private void nuevoLibroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nuevoLibroActionPerformed
        agregarLibro dialog = new agregarLibro(this, true, sistemalib); 
        dialog.setVisible(true);
        cargarTablaLibros();
        actualizarDatos();
        Bitacora.registrar("AGREGAR_LIBRO", usuarioActual.getId(), "SISTEMALIBROS");
    }//GEN-LAST:event_nuevoLibroActionPerformed

    private void nuevoOperadorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nuevoOperadorActionPerformed
        agregarOperador dialog = new agregarOperador(this, true, sistema); 
        Bitacora.registrar("AGREGAR_OPERADOR", usuarioActual.getId(), "SISTEMAUSUARIOS");
        dialog.setVisible(true);
        cargarTablaOperadores();
        actualizarDatos();
    }//GEN-LAST:event_nuevoOperadorActionPerformed

    private void eliminarOperadorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_eliminarOperadorActionPerformed
       int fila = tablaOperadores.getSelectedRow();
       if (fila != -1) {
       String id = tablaOperadores.getValueAt(fila, 0).toString();
       sistema.eliminarUsuario(id);
       Bitacora.registrar("ELIMINAR_OPERADOR", usuarioActual.getId(), "SISTEMAUSUARIOS");
       }
       cargarTablaOperadores();
       actualizarDatos();
    }//GEN-LAST:event_eliminarOperadorActionPerformed

    private void modificarOperadorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_modificarOperadorActionPerformed
        int fila = tablaOperadores.getSelectedRow();
        if (fila != -1) {
        String cambio = tablaOperadores.getValueAt(fila, 0).toString();
        editarOperador dialog = new editarOperador(null, true, sistema, cambio);
        Bitacora.registrar("MODIFICAR_OPERADOR", usuarioActual.getId(), "SISTEMAUSUARIOS");
        dialog.setVisible(true);
}
        cargarTablaOperadores();
    }//GEN-LAST:event_modificarOperadorActionPerformed

    private void buscadorOperadoresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buscadorOperadoresActionPerformed
        String texto = buscadorOperadores.getText();
        if (texto.isEmpty()) {
        cargarTablaOperadores();
    } else {
        buscarTablaOperadores(texto); 
    }
    }//GEN-LAST:event_buscadorOperadoresActionPerformed

    private void buscadorOperadoresKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_buscadorOperadoresKeyReleased
        String texto = buscadorOperadores.getText();
        if (texto.isEmpty()) {
        cargarTablaOperadores();
    }   else {
        buscarTablaOperadores(texto); 
    }
    }//GEN-LAST:event_buscadorOperadoresKeyReleased

    private void eliminarUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_eliminarUsuarioActionPerformed
        int fila = tablaUsuarios.getSelectedRow();
       if (fila != -1) {
       String carne = tablaUsuarios.getValueAt(fila, 0).toString();
       String id = sistema.buscarPorCarne(carne);
       sistema.eliminarUsuario(id);
       Bitacora.registrar("ELIMINAR_USUARIO", usuarioActual.getId(), "SISTEMAUSUARIOS");
       }
       cargarTablaUsuarios();
       actualizarDatos();
    }//GEN-LAST:event_eliminarUsuarioActionPerformed

    private void buscarUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buscarUsuarioActionPerformed
        String texto = buscarUsuario.getText();
        if (texto.isEmpty()) {
        cargarTablaUsuarios();
    } else {
        buscarTablaUsuarios(texto); 
    }
    }//GEN-LAST:event_buscarUsuarioActionPerformed

    private void buscarUsuarioKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_buscarUsuarioKeyReleased
        String texto = buscarUsuario.getText();
        if (texto.isEmpty()) {
        cargarTablaUsuarios();
    } else {
        buscarTablaUsuarios(texto); 
    }
    }//GEN-LAST:event_buscarUsuarioKeyReleased

    private void editarLibroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editarLibroActionPerformed
        int fila = tablaLibros.getSelectedRow();
        if (fila != -1) {
        String cambio = tablaLibros.getValueAt(fila, 0).toString();
        editarLibro dialog = new editarLibro(null, true, sistemalib, cambio);
        dialog.setVisible(true);
        }
        cargarTablaLibros();
        Bitacora.registrar("MODIFICAR_LIBRO", usuarioActual.getId(), "SISTEMALIBROS");
        actualizarDatos();
    }//GEN-LAST:event_editarLibroActionPerformed

    private void buscarLibrosKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_buscarLibrosKeyReleased
        String texto = buscarLibros.getText();
        if (texto.isEmpty()) {
        cargarTablaLibros();
    } else {
        buscarTablaLibros(texto); 
    }
    }//GEN-LAST:event_buscarLibrosKeyReleased

    private void nuevoPrestamoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nuevoPrestamoActionPerformed
        nuevoPrestamo dialog = new nuevoPrestamo(null, true, sistemap, sistema, sistemalib);
        dialog.setVisible(true);
        cargarTablas();
        Bitacora.registrar("AGREGAR_PRESTAMO", usuarioActual.getId(), "SISTEMAPRESTAMOS");
    }//GEN-LAST:event_nuevoPrestamoActionPerformed

    private void btnMostrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMostrarActionPerformed
    String opcion = comboReportes.getSelectedItem().toString();

     if (opcion.equals("Libros disponibles")) {
        mostrarLibrosDisponibles();
    } 
    else if (opcion.equals("Préstamos vencidos")) {
        mostrarPrestamosVencidos();
    } 
    else if (opcion.equals("Top 5 libros")) {
        mostrarTop5();
    } 
    else if (opcion.equals("Estudiantes activos")) {
        mostrarEstudiantesActivos();
    }       
    }//GEN-LAST:event_btnMostrarActionPerformed

    private void btnGenerarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGenerarActionPerformed
        String opcion = comboReportes.getSelectedItem().toString();
    String html = "";
    Bitacora.registrar("GENERAR_REPORTE", usuarioActual.getId(), "SISTEMAREPORTES");

    if (opcion.equals("Libros disponibles")) {
        html = sistemarep.reporteLibrosDisponibles();
    } 
    else if (opcion.equals("Préstamos vencidos")) {
        html = sistemarep.reportePrestamosVencidos();
    } 
    else if (opcion.equals("Top 5 libros")) {
        html = sistemarep.reporteTop5Libros();
    } 
    else if (opcion.equals("Estudiantes activos")) {
        html = sistemarep.reporteEstudiantesActivos();
    }

    sistemarep.guardarReporte(html, "reporte");
    }//GEN-LAST:event_btnGenerarActionPerformed

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu ayuda;
    private javax.swing.JButton btnGenerar;
    private javax.swing.JButton btnMostrar;
    private javax.swing.JTextField buscadorOperadores;
    private javax.swing.JTextField buscarLibros;
    private javax.swing.JTextField buscarUsuario;
    private javax.swing.JLabel cantLibros;
    private javax.swing.JLabel cantPrestamos;
    private javax.swing.JLabel cantUsuarios;
    private javax.swing.JComboBox<String> comboReportes;
    private javax.swing.JMenu edicion;
    private javax.swing.JButton editarLibro;
    private javax.swing.JButton eliminarLibro;
    private javax.swing.JButton eliminarOperador;
    private javax.swing.JButton eliminarUsuario;
    private javax.swing.JMenu herramientas;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu6;
    private javax.swing.JMenu jMenu7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JTabbedPane menu;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JScrollPane menuLibros;
    private javax.swing.JScrollPane menuPrestamos;
    private javax.swing.JScrollPane menuReportes;
    private javax.swing.JScrollPane menuUsuarios;
    private javax.swing.JPanel modificarLibro;
    private javax.swing.JButton modificarOperador;
    private javax.swing.JButton modificarUsuario;
    private javax.swing.JToggleButton nuevoLibro;
    private javax.swing.JButton nuevoOperador;
    private javax.swing.JButton nuevoPrestamo;
    private javax.swing.JButton nuevoUsuario;
    private javax.swing.JButton registrarPrestamo;
    private javax.swing.JScrollPane scrollLibros;
    private javax.swing.JScrollPane scrollReportes;
    private javax.swing.JScrollPane scrollUsuarios;
    private javax.swing.JTable tablaLibros;
    private javax.swing.JTable tablaOperadores;
    private javax.swing.JTable tablaPrestamos;
    private javax.swing.JTable tablaReportes;
    private javax.swing.JTable tablaUsuarios;
    private javax.swing.JComboBox<String> tipoDevolucion;
    private javax.swing.JLabel tipoRol;
    private javax.swing.JLabel tipoSesion;
    private javax.swing.JMenu ver;
    // End of variables declaration//GEN-END:variables
}
