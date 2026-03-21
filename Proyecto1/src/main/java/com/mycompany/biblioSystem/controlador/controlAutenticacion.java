
package com.mycompany.biblioSystem.controlador;
import com.mycompany.biblioSystem.modelo.*;

public class controlAutenticacion {
 
    private SistemaUsuarios sistemaUsuarios;

    public controlAutenticacion(SistemaUsuarios sistemaUsuarios) {
        this.sistemaUsuarios = sistemaUsuarios;
    }

    public Usuario login(String id, String password) {
        return sistemaUsuarios.login(id, password);
    }   
    
    public SistemaUsuarios getSistema(){
        return sistemaUsuarios;
    }
}
