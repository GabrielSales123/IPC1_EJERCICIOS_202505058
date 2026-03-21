
package com.mycompany.biblioSystem.modelo;

public class Estudiante extends Usuario{
    
    private String carne;
    private String carrera;
    private String correo; 
    private int prestamosActivos; 
    private boolean estadoPrestamos; 
    
    public Estudiante(String id, String nombre, String password, String carne, String carrera,
            String correo, int prestamosActivos, boolean estadoPrestamos){
        super (id, nombre, password, "ESTUDIANTE");
        this.carne = carne;
        this.carrera = carrera;
        this.correo = correo; 
        this.prestamosActivos = prestamosActivos;
        this.estadoPrestamos = estadoPrestamos;
    }
    
    public String getCarrera(){
        return carrera;
    }
    
    public String setCarrera(String carrera){
        this.carrera = carrera;
        return carrera;
    }
    
    public String getCorreo(){
        return correo;
    }
    
    public String setCorreo(String correo){
        this.correo = correo;
        return correo;
    }
    
    public String getCarne(){
        return carne;
    }
    
    public String setCarne(String carne){
        this.carne = carne;
        return carne;
    }
    
    public int getPrestamosActivos(){
        return prestamosActivos;
    }
    
    public int setPrestamosActivos(int prestamosActivos){
        this.prestamosActivos = prestamosActivos;
        return prestamosActivos;
    }
    
    public boolean getEstadoPrestamos(){
        return estadoPrestamos;
    }
    
    public boolean setEstadoPrestamos(boolean estadoPrestamos){
        this.estadoPrestamos = estadoPrestamos;
        return estadoPrestamos;
    }
}
