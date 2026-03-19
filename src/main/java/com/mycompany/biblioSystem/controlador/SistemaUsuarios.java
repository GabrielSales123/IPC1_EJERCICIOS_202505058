
package com.mycompany.biblioSystem.controlador;
import com.mycompany.biblioSystem.modelo.*;
import java.io.*;
public class SistemaUsuarios {
    
  private Usuario[] usuarios; 
  private int totUsuarios; 
  
  public SistemaUsuarios(int capacidad){
      this.usuarios = new Usuario[capacidad];
      this.totUsuarios = 0; 
  }
  
  public Usuario login(String id, String password){

    for (int i = 0; i < totUsuarios; i++){
        if (usuarios[i].getId().equals(id) &&
            usuarios[i].getPassword().equals(password)){
            return usuarios[i];
        }

    }

    return null;
}
  
  public int getTotalUsuarios() {
      return totUsuarios;
  }
  
  
  public void agregarUsuario(Usuario u){
        if (totUsuarios < usuarios.length){
        usuarios[totUsuarios] = u;
        totUsuarios++;
        }
    }
  
  public void eliminarUsuario(String id){
      for (int i = 0; i < totUsuarios; i++){
        if (usuarios[i].getId().equals(id)){
            for (int j = i; j < totUsuarios - 1; j++){
                usuarios[j] = usuarios[j + 1];
            }
            usuarios[totUsuarios - 1] = null;
            totUsuarios--;
            break;
        }
    }
    reescribirArchivo();
  }
  
  public void editarUsuario(String cambio, String nuevoId, String nuevoNombre, String nuevaPassword){
      for (int i = 0; i < totUsuarios; i++){
          if (usuarios[i].getId().equals(cambio)){
              usuarios[i].setNombre(nuevoNombre);
              usuarios[i].setPassword(nuevaPassword);
              usuarios[i].setId(nuevoId);
              
          }
      }
       reescribirArchivo();
  }
  
  public void mostrarUsuarios(){

    for(int i = 0; i < totUsuarios; i++){
        System.out.println(
            "ID: " + usuarios[i].getId() +
            " | Nombre: " + usuarios[i].getNombre() +
             " | Nombre: " + usuarios[i].getPassword() +
            " | Rol: " + usuarios[i].getRol()
        );
    }

}
  
  public void cargarUsuarios(){
      try{
          BufferedReader br = new BufferedReader(new FileReader("cuentas.txt"));
          String linea; 
          
          while ((linea = br.readLine()) != null){
              
              String[] datos = linea.split(";");
              String id = datos[0];
              String nombre = datos[1];
              String password = datos[2];
              String rol = datos[3];
              
               if (rol.equals("ADMIN")){
                  usuarios[totUsuarios++] = 
                          new Administrador (id, nombre, password);
              }
              
              if (rol.equals("OPERADOR")){
                  usuarios[totUsuarios++] = 
                          new Operador(id, nombre, password);
              }
              
              if (rol.equals("ESTUDIANTE")){
                  usuarios[totUsuarios++] = 
                          new Estudiante(id, nombre, password);
              }
          }
          br.close();
          
      }catch (IOException e) {
        System.out.println("Error leyendo cuentas.txt");
    }
      
  }
  
  public void reescribirArchivo() {
    try {
        FileWriter fw = new FileWriter("cuentas.txt"); 
        BufferedWriter bw = new BufferedWriter(fw);

        for (int i = 0; i < totUsuarios; i++) {
            Usuario u = usuarios[i];

            if (u != null) {
                bw.write(
                    u.getId() + ";" +
                    u.getNombre() + ";" +
                    u.getPassword() + ";" +
                    u.getRol()
                );
                bw.newLine();
            }
        }

        bw.close();

    } catch (IOException e) {
        System.out.println("Error al reescribir archivo");
    }
}
  
  //ACCIONES PARA OPERADORES---------------------------------
  
  public  Usuario[] getOperadores() {
    int contador = 0;
    for (int i = 0; i < totUsuarios; i++) {
        if (usuarios[i] instanceof Operador) {
            contador++;
        }
    }

    Usuario[] operadores = new Usuario[contador];
    int index = 0;
    for (int i = 0; i < totUsuarios; i++) {
        if (usuarios[i] instanceof Operador) {
            operadores[index] = usuarios[i];
            index++;
        }
    }
    return operadores;
}
  
  public void agregarOperador(String id, String nombre, String password){
      if (totUsuarios < usuarios.length){
          usuarios[totUsuarios] = new Operador(id, nombre, password);
          totUsuarios++;
          guardarOperadorEnArchivo(id, nombre, password);
      }
      
  }
  
  public void guardarOperadorEnArchivo(String id, String nombre, String password) {
    try {
        FileWriter fw = new FileWriter("cuentas.txt", true); 
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(id + ";" + nombre + ";" + password + ";OPERADOR");
        bw.newLine();
        bw.close();
    } catch (IOException e) {
        System.out.println("Error al guardar operador");
    }
}
  //---------------------------------------------------------------------
  
}
