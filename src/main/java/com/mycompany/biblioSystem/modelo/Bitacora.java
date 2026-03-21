
package com.mycompany.biblioSystem.modelo;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Bitacora {

    private static final String ARCHIVO = "bitacora.txt";

    public static void registrar(String operacion, String usuario, String modulo) {

        try {
            FileWriter fw = new FileWriter(ARCHIVO, true); // true = append

            Date ahora = new Date();
            SimpleDateFormat fecha = new SimpleDateFormat("dd/MM/yy");
            SimpleDateFormat hora = new SimpleDateFormat("hh:mm a");

            String linea = "[" + operacion + "]"
                         + "[" + usuario + "]"
                         + "[" + modulo + "]"
                         + "[" + fecha.format(ahora) + "]"
                         + "[" + hora.format(ahora) + "]\n";

            fw.write(linea);
            fw.close();

        } catch (IOException e) {
            System.out.println("Error al escribir bitácora: " + e.getMessage());
        }
    }
}