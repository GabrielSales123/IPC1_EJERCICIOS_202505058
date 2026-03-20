
package com.mycompany.biblioSystem.modelo;


public class Libro {
    private String codigo;
    private String ISBN;
    private String titulo; 
    private String autor; 
    private String genero;
    private String anio;
    private int disponibles;
    
    public Libro(String codigo, String ISBN, String titulo, String autor, 
                 String genero, String anio, int disponibles){
        
        this.codigo = codigo; 
        this.ISBN = ISBN; 
        this.titulo = titulo; 
        this.autor = autor; 
        this.genero = genero; 
        this.anio = anio;
        this.disponibles = disponibles; 
        
    }
    
    public String getCodigo(){
        return codigo;
    }
    
    public String setCodigo(String codigo){
        this.codigo = codigo; 
        return codigo;
    }
    
    public String getISBN(){
        return ISBN;
    }
    
    public String setISBN(String ISBN){
        this.ISBN = ISBN; 
        return ISBN;
    }
    
    public String getTitulo(){
        return titulo;
    }
    
    public String setTitulo(String titulo){
        this.titulo = titulo; 
        return titulo;
    }
    
    public String getAutor(){
        return autor;
    }
    
    public String setAutor(String autor){
        this.autor = autor; 
        return autor;
    }
    
    public String getGenero(){
        return genero;
    }
    
    public String setGenero(String genero){
        this.genero = genero; 
        return genero;
    }
    
    public String getAnio(){
        return anio;
    }
    
    public String setAnio(String anio){
        this.anio = anio; 
        return anio;
    }
    
    public int getDisponibles(){
        return disponibles;
    }
    
    public int setDisponibles(int disponibles){
        this.disponibles = disponibles; 
        return disponibles;
    }
    
}
