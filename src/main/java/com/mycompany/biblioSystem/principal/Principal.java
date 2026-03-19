
package com.mycompany.biblioSystem.principal;
import com.mycompany.biblioSystem.vista.*;
import com.mycompany.biblioSystem.controlador.*;
import com.mycompany.biblioSystem.modelo.*;
        
     
     
public class Principal {
    public static void main(String[] args) {
        SistemaUsuarios sistema = new SistemaUsuarios(100);
        sistema.cargarUsuarios();
        sistema.mostrarUsuarios();
        System.out.println("Inicio interfaz");
         java.awt.EventQueue.invokeLater(() -> {
        new Login(sistema).setVisible(true);
    });
        System.out.println("Fin interfaz");
        
        
    }
}
