
package com.mycompany.biblioSystem.modelo;

public class Prestamo {
    private String codigoPrestamo;
    private String carnet;
    private String codigoLibro;
    private String fechaPrestamo;
    private String fechaLimite;
    private String estado;
    
    public Prestamo(String codigoPrestamo, String carnet, String codigoLibro,
                    String fechaPrestamo, String fechaLimite, String estado){
        
        this.codigoPrestamo = codigoPrestamo;
        this.carnet = carnet;
        this.codigoLibro = codigoLibro;
        this.fechaPrestamo = fechaPrestamo;
        this.fechaLimite = fechaLimite;
        this.estado = estado;
        
    }
    
    public String getCodigoPrestamo(){
        return codigoPrestamo;
    }
    
    public String setCodigoPrestamo(String codigoPrestamo){
        this.codigoPrestamo = codigoPrestamo;
        return codigoPrestamo;
    }
    
    public String getCarnet(){
        return carnet;
    }
    
    public String setCarne(String carnet){
        this.carnet = carnet;
        return carnet;
    }
    
    public String getCodigoLibro(){
        return codigoLibro;
    }
    
    public String setCodigoLibro(String codigoLibro){
        this.codigoLibro = codigoLibro;
        return codigoLibro;
    }
    
    public String getFechaPrestamo(){
        return fechaPrestamo;
    }
    
    public String setFechaPrestamo(String fechaPrestamo){
        this.fechaPrestamo = fechaPrestamo;
        return fechaPrestamo;
    }
    
    public String getFechaLimite(){
        return fechaLimite;
    }
    
    public String setFechaLimite(String fechaLimite){
        this.fechaLimite = fechaLimite;
        return fechaLimite;
    }
    
    public String getEstado(){
        return estado;
    }
    
    public String setEstado(String estado){
        this.estado = estado; 
        return estado;
    }
    
}
