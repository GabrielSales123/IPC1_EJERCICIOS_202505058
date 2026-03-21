
package com.mycompany.biblioSystem.controlador;
import com.mycompany.biblioSystem.modelo.*;
import java.io.FileWriter;
import java.io.IOException;

public class SistemaReportes {
    
    private SistemaPrestamos prestamos;
    private SistemaLibros libros;
    private SistemaUsuarios usuarios;

    public SistemaReportes(SistemaPrestamos p, SistemaLibros l, SistemaUsuarios u) {
        this.prestamos = p;
        this.libros = l;
        this.usuarios = u;
    }
    
    public String reportePrestamosVencidos() {

    String html = "<html><body>";
    html += "<h1>Reporte de Préstamos Vencidos</h1>";
    html += "<table border='1'>";
    html += "<tr><th>Código</th><th>Carnet</th><th>Libro</th><th>Fecha devolución</th></tr>";

    Prestamo[] listaPrestamos = prestamos.getPrestamos();

    for (int i = 0; i < prestamos.getTotPrestamos(); i++) {

        Prestamo p = listaPrestamos[i];

        if (p.getEstado().equals("VENCIDO")) {

            String nombreLibro = libros.getNombreLibroPorCodigo(p.getCodigoLibro());

            html += "<tr>";
            html += "<td>" + p.getCodigoPrestamo() + "</td>";
            html += "<td>" + p.getCarnet() + "</td>";
            html += "<td>" + nombreLibro + "</td>";
            html += "<td>" + p.getFechaLimite() + "</td>";
            html += "</tr>";
        }
    }

    html += "</table></body></html>";
    return html;
}
    
    public String reporteTop5Libros() {

    Libro[] listaLibros = libros.getLibros();
    Prestamo[] listaPrestamos = prestamos.getPrestamos();

    int n = libros.getTotalLibros();
    int[] contador = new int[n];

    // contar préstamos
    for (int i = 0; i < prestamos.getTotPrestamos(); i++) {

        Prestamo p = listaPrestamos[i];

        for (int j = 0; j < n; j++) {
            if (listaLibros[j].getCodigo().equals(p.getCodigoLibro())) {
                contador[j]++;
            }
        }
    }

    // ordenar (burbuja)
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

    String html = "<html><body>";
    html += "<h1>Top 5 Libros Más Prestados</h1>";
    html += "<table border='1'>";
    html += "<tr><th>Título</th><th>Veces prestado</th></tr>";

    for (int i = 0; i < 5 && i < n; i++) {

        html += "<tr>";
        html += "<td>" + listaLibros[i].getTitulo() + "</td>";
        html += "<td>" + contador[i] + "</td>";
        html += "</tr>";
    }

    html += "</table></body></html>";
    return html;
}
    
    public String reporteEstudiantesActivos() {

    Usuario[] listaUsuarios = usuarios.getUsuarios();

    String html = "<html><body>";
    html += "<h1>Estudiantes con Préstamos Activos</h1>";
    html += "<table border='1'>";
    html += "<tr><th>Carnet</th><th>Nombre</th><th>Activos</th><th>Vencidos</th></tr>";

    for (int i = 0; i < usuarios.getTotalUsuarios(); i++) {

        Usuario u = listaUsuarios[i];

        if (u instanceof Estudiante) {

            Estudiante e = (Estudiante) u;

            int activos = usuarios.getPrestamosEstudiantePorCarnet(e.getCarne());
            boolean vencidos = usuarios.getEstadoEstudiantePorCarnet(e.getCarne());

            if (activos > 0) {

                html += "<tr>";
                html += "<td>" + e.getCarne() + "</td>";
                html += "<td>" + e.getNombre() + "</td>";
                html += "<td>" + activos + "</td>";
                html += "<td>" + (vencidos ? "Sí" : "No") + "</td>";
                html += "</tr>";
            }
        }
    }

    html += "</table></body></html>";
    return html;
}
    
    public String reporteLibrosDisponibles() {

    String html = "<html><body>";
    html += "<h1>Libros Disponibles</h1>";
    html += "<table border='1'>";
    html += "<tr><th>Código</th><th>Título</th><th>Disponibles</th></tr>";

    Libro[] listaLibros = libros.getLibros();

    for (int i = 0; i < libros.getTotalLibros(); i++) {

        Libro l = listaLibros[i];

        if (l.getDisponibles() > 0) {

            html += "<tr>";
            html += "<td>" + l.getCodigo() + "</td>";
            html += "<td>" + l.getTitulo() + "</td>";
            html += "<td>" + l.getDisponibles() + "</td>";
            html += "</tr>";
        }
    }

    html += "</table></body></html>";
    return html;
}

    public void guardarReporte(String contenido, String nombreArchivo) {

    try {
        String fecha = java.time.LocalDate.now().toString();
        String nombreFinal = nombreArchivo + "_" + fecha + ".html";

        FileWriter writer = new FileWriter(nombreFinal);
        writer.write(contenido);
        writer.close();

        System.out.println("Reporte generado: " + nombreFinal);

        // abrir automáticamente
        java.awt.Desktop.getDesktop().open(new java.io.File(nombreFinal));

    } catch (IOException e) {
        e.printStackTrace();
    }
}
}
