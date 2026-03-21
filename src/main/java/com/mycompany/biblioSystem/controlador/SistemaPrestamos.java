
package com.mycompany.biblioSystem.controlador;
import com.mycompany.biblioSystem.modelo.*;
import java.io.*;
import java.util.*;
import java.text.*;

public class SistemaPrestamos {
    
    private Prestamo[] prestamos;
    private int totPrestamos;
    private SistemaLibros sistemaLib;
    
    public SistemaPrestamos(int capacidad, SistemaLibros sistemaLib){
        this.prestamos = new Prestamo[capacidad];
        this.totPrestamos = 0;
        this.sistemaLib = sistemaLib;
    }
    
    public void agregarPrestamo(Prestamo p){
    if (totPrestamos < prestamos.length){
        prestamos[totPrestamos] = p;
        totPrestamos++;

        guardarPrestamoEnArchivo(p);
    }
}
    public int getTotPrestamos(){
        return totPrestamos; 
    }
    
    public int getPrestamosActivos() {
    int contador = 0;
    for (int i = 0; i < totPrestamos; i++) {
        if (prestamos[i].getEstado().equals("ACTIVO")) {
            contador++;
        }
    }
    return contador;
}
    
    public Prestamo[] getPrestamos() {
    Prestamo[] p = new Prestamo[totPrestamos];
    for (int i = 0; i < totPrestamos; i++) {
        p[i] = prestamos[i];
    }
    return p;
}
    
    
    
    public void guardarPrestamoEnArchivo(Prestamo p) {
    try {
        FileWriter fw = new FileWriter("Prestamos.txt", true); 
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(p.getCodigoPrestamo() + ";" + p.getCarnet() + ";" + p.getCodigoLibro() + ";" + 
                p.getFechaPrestamo() +";"+ p.getFechaLimite() + ";" + p.getEstado());
        bw.newLine();
        bw.close();
    } catch (IOException e) {
        System.out.println("Error al guardar operador");
    }
    
}
    
    public String generarCodigo(String fechaPrestamo, String fechaDevolucion) {
        try{
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date fecha1 = sdf.parse(fechaPrestamo);
            Date fecha2 = sdf.parse(fechaDevolucion);
            SimpleDateFormat codigo = new SimpleDateFormat("ddMM");
            String codigo1 = codigo.format(fecha1); 
            String codigo2 = codigo.format(fecha2);
            String numero = String.format("%03d", totPrestamos + 1);
            
            return "P" + codigo1 + "-" + codigo2 + "-" + numero;
        }catch (Exception e) {
        e.printStackTrace();
        return "P000-0000-000";
    }
}
    
    public void cargarPrestamos(){
        try{
            BufferedReader br = new BufferedReader(new FileReader("Prestamos.txt"));
            String linea; 
          
            while ((linea = br.readLine()) != null){
              
                String[] datos = linea.split(";");
                String codigoPrestamo = datos[0];
                String carnet = datos[1];
                String codigoLibro = datos[2];
                String fechaPrestamo = datos[3];
                String fechaLimite = datos[4];
                String estado = datos[5];
                if(!estado.equals("DEVUELTO")){
                estado = compararFecha(fechaLimite);
                }
             
                    prestamos[totPrestamos++] = 
                        new Prestamo(codigoPrestamo,carnet,codigoLibro,fechaPrestamo, 
                                fechaLimite,estado);
                }
                
                br.close();
          
            }catch (IOException e) {
            System.out.println("Error leyendo Prestamos.txt");
        }
      reescribirArchivoPrestamos();
    }
    
    
    public String compararFecha(String fechaDevolucion){
        String cambio = "ACTIVO";
        try{
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date hoy = new Date();
        Date fechaLimite = sdf.parse(fechaDevolucion);

            if (hoy.after(fechaLimite)) {
               cambio = "VENCIDO";
            } else {
               cambio = "ACTIVO";
           }
        
        }catch (Exception e){
            e.printStackTrace();
        }
        
        return cambio;
    }
    
    public void reescribirArchivoPrestamos() {
        try {
            FileWriter fw = new FileWriter("Prestamos.txt"); 
            BufferedWriter bw = new BufferedWriter(fw);

            for (int i = 0; i < totPrestamos; i++) {
                Prestamo p = prestamos[i];
            
                if(p != null){
                    bw.write(
                        p.getCodigoPrestamo() + ";" +
                        p.getCarnet() + ";" +
                        p.getCodigoLibro() + ";" +
                        p.getFechaPrestamo() + ";" +
                        p.getFechaLimite() + ";" +
                        p.getEstado()     
                    );
                    bw.newLine();
                }
            
                else{
                    bw.newLine();
                }
            }

            bw.close();

        } catch (IOException e) {
        System.out.println("Error al reescribir archivo");
    }
}
    
    public void devolverLibro(String id){
            for (int i = 0; i < totPrestamos; i++){
                 if (prestamos[i].getCodigoPrestamo().equals(id)){
                 Prestamo p = prestamos[i];
                 prestamos[i].setEstado("DEVUELTO");
                 sistemaLib.regresarLibrosPorCodigo(p.getCodigoLibro());
                 break;
             }
        }
         reescribirArchivoPrestamos();
     }
    
    
   public boolean tienePrestamosVencidos(String carnet) {
    for (int i = 0; i < totPrestamos; i++) {
        Prestamo p = prestamos[i];
        if (p.getCarnet().equals(carnet) && p.getEstado().equals("VENCIDO")) {
            return true; 
        }
    }
    return false; 
}
    
    
    
}
