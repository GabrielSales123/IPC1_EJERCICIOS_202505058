
package com.mycompany.biblioSystem.principal;
import com.mycompany.biblioSystem.vista.*;
import com.mycompany.biblioSystem.controlador.*;
import com.mycompany.biblioSystem.modelo.*;
        
     
     
public class Principal {
    public static void main(String[] args) {
        SistemaUsuarios sistema = new SistemaUsuarios(100);
        SistemaLibros sistemalib = new SistemaLibros(100);
        SistemaPrestamos sistemap = new SistemaPrestamos(200);
        sistema.cargarUsuarios();
        sistema.mostrarUsuarios();
        sistemap.cargarPrestamos();
        System.out.println("Inicio interfaz");
         java.awt.EventQueue.invokeLater(() -> {
        new Login(sistema, sistemalib, sistemap).setVisible(true);
    });
        System.out.println("Fin interfaz");
        
        
    }
}
