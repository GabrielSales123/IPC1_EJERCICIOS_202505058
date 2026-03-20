
package com.mycompany.biblioSystem.controlador;
import com.mycompany.biblioSystem.modelo.*;

public class SistemaLibros {
    
    private Libro[] libros; 
    private int totLibros;
    
    public SistemaLibros(int capacidad){
        this.libros = new Libro[capacidad];
        this.totLibros = 0;
    }
    
    public int getTotalLibros(){
        return totLibros;
    }
    
    public Libro[] getLibros() {
    Libro[] lib = new Libro[totLibros];
    for (int i = 0; i < totLibros; i++) {
        lib[i] = libros[i];
    }
    return lib;
}
    
    public void agregarLib(Libro u){
        if(totLibros < libros.length){
            libros[totLibros] = u;
            totLibros++;
        }  
    }
    
    public void eliminarLibro(String codigo){
        for (int i = 0; i < totLibros; i++){
            if (libros[i].getCodigo().equals(codigo)){
                 for (int j = i; j < totLibros - 1; j++){
                    libros[j] = libros[j + 1];
                 }
                libros[totLibros - 1] = null;
                totLibros--;
                break;
            }
        }
    }
    
    public void agregarLibro(String codigo, String ISBN, String titulo, 
                         String autor, String genero, String anio, int disponibles){
    if (totLibros < libros.length){
        libros[totLibros] = new Libro(codigo, ISBN, titulo, autor, genero, anio, disponibles);
        totLibros++;
    }
}
   
    
    public void editarLibro(String cambio, String codigo, String ISBN, String titulo, String autor,
                            String genero, String anio, int disponibles){
      for (int i = 0; i < totLibros; i++){
          if (libros[i].getCodigo().equals(cambio)){
              libros[i].setCodigo(codigo);
              libros[i].setISBN(ISBN);
              libros[i].setTitulo(titulo);
              libros[i].setAutor(autor);
              libros[i].setGenero(genero);
              libros[i].setAnio(anio);
              libros[i].setDisponibles(disponibles);
            }
        }
    }
     
     
    
}
