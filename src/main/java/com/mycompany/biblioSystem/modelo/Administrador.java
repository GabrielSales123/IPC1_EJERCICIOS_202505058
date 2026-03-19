
package com.mycompany.biblioSystem.modelo;


public class Administrador extends Usuario{
    public Administrador(String id, String nombre, String password){
        super(id, nombre, password, "ADMIN");
    }
}
