
package com.mycompany.biblioSystem.modelo;

public abstract class Usuario {
    private String id; 
    private String nombre;
    private String rol;
    private String password; 
    
    public Usuario(String id, String nombre, String password, String rol) {
        this.nombre = nombre;
        this.rol = rol;
        this.password = password; 
        this.id = id; 
    }
    
    public String getNombre(){
        return nombre;
    }
    
    public String setNombre(String nombre){
        this.nombre = nombre;
        return nombre;
    }
    
    public String getRol(){
        return rol; 
    }
    
    public String getPassword(){
        return password; 
    }
    
    public String setPassword(String password){
        this.password = password;
        return password;
    }
    
    public String getId(){
        return id; 
    }
    
    public String setId(String id){
        this.id = id;
        return id;
    }
}
