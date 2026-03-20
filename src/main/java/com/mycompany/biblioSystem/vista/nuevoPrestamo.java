
package com.mycompany.biblioSystem.vista;
import java.util.*;
import java.text.*;
import com.mycompany.biblioSystem.controlador.*;
import com.mycompany.biblioSystem.modelo.*;
/**
 *
 * @author Andri
 */
public class nuevoPrestamo extends javax.swing.JDialog {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(nuevoPrestamo.class.getName());
    private SistemaPrestamos sistema;
    /**
     * Creates new form nuevoPrestamo
     */
    public nuevoPrestamo(java.awt.Frame parent, boolean modal, SistemaPrestamos sistema) {
        super(parent, modal);
        this.sistema = sistema; 
        initComponents();
        
        devolucionTxt.setEditable(false);
        fechaSpinn.setModel(new javax.swing.SpinnerDateModel());
        fechaSpinn.setValue(new Date());
        fechaSpinn.setEditor(new javax.swing.JSpinner.DateEditor(fechaSpinn, "dd/MM/yyyy"));
        calcularFechaDevolucion();
        fechaSpinn.addChangeListener(e -> {
        calcularFechaDevolucion();});
    }

    private void calcularFechaDevolucion(){
        try{
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date fechaPrestamo = (Date) fechaSpinn.getValue();
            Calendar cal = Calendar.getInstance();
            cal.setTime(fechaPrestamo);
            cal.add(Calendar.DAY_OF_MONTH, 15);
            Date fechaDevolucion = cal.getTime();
            devolucionTxt.setText(sdf.format(fechaDevolucion));
            
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        carnetTxt = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        codigoLibroTxt = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        fechaSpinn = new javax.swing.JSpinner();
        devolucionTxt = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setText("No. Carne");

        carnetTxt.addActionListener(this::carnetTxtActionPerformed);

        jLabel2.setText("Codigo Libro");

        jLabel3.setText("Fecha Prestamo");

        jLabel4.setText("Fecha devolucion");

        fechaSpinn.setModel(new javax.swing.SpinnerDateModel());

        devolucionTxt.addActionListener(this::devolucionTxtActionPerformed);

        jButton1.setText("jButton1");
        jButton1.addActionListener(this::jButton1ActionPerformed);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel1)
                        .addComponent(jLabel2)
                        .addComponent(jLabel3)
                        .addComponent(jLabel4)
                        .addComponent(carnetTxt, javax.swing.GroupLayout.DEFAULT_SIZE, 285, Short.MAX_VALUE)
                        .addComponent(codigoLibroTxt))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(devolucionTxt, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(fechaSpinn, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 275, Short.MAX_VALUE))
                    .addComponent(jButton1))
                .addContainerGap(109, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(carnetTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(codigoLibroTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(fechaSpinn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(devolucionTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton1)
                .addContainerGap(53, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void carnetTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_carnetTxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_carnetTxtActionPerformed

    private void devolucionTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_devolucionTxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_devolucionTxtActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String carnet = carnetTxt.getText();
        String codigoLibro = codigoLibroTxt.getText();
        Date fecha = (Date) fechaSpinn.getValue();
        String fechaPrestamo = sdf.format(fecha);
        String fechaDevolucion = devolucionTxt.getText();
        String estado = sistema.compararFecha(fechaDevolucion);
        Prestamo p = new Prestamo(
        sistema.generarCodigo(fechaPrestamo, fechaDevolucion),carnet,codigoLibro,fechaPrestamo, fechaDevolucion,estado);
        sistema.agregarPrestamo(p);
        dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField carnetTxt;
    private javax.swing.JTextField codigoLibroTxt;
    private javax.swing.JTextField devolucionTxt;
    private javax.swing.JSpinner fechaSpinn;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    // End of variables declaration//GEN-END:variables
}
