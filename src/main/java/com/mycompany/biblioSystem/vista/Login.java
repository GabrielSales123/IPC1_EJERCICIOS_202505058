
package com.mycompany.biblioSystem.vista;
import com.mycompany.biblioSystem.controlador.*;
import com.mycompany.biblioSystem.modelo.*;

public class Login extends javax.swing.JFrame {
    
    public static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(Login.class.getName());
    private controlAutenticacion auth;
    private SistemaUsuarios sistema;
    private SistemaLibros sistemaLib;
    private SistemaPrestamos sistemap;
    
    public Login(SistemaUsuarios sistema, SistemaLibros sistemaLib, SistemaPrestamos sistemap) {
        initComponents();
        this.sistema = sistema;
        this.sistemaLib = sistemaLib;
        this.sistemap = sistemap;
        auth = new controlAutenticacion(sistema);
        
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        titulo = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        logUsuario = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        logConfirm = new javax.swing.JToggleButton();
        logPassword = new javax.swing.JPasswordField();
        passVisible = new javax.swing.JToggleButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("BiblioSystem: Log In");

        titulo.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        titulo.setText("Sistema de Gestión de Biblioteca");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(153, 153, 153));
        jLabel2.setText("Universidad - Intoducción a la Programacion 1");

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/libro.jpg"))); // NOI18N

        jLabel1.setText("Usuario:");

        logUsuario.setForeground(new java.awt.Color(153, 153, 153));
        logUsuario.setText("Ingrese su nombre de usuario");
        logUsuario.addActionListener(this::logUsuarioActionPerformed);

        jLabel4.setText("Contraseña:");

        logConfirm.setText("Iniciar Sesión");
        logConfirm.addActionListener(this::logConfirmActionPerformed);

        logPassword.setForeground(new java.awt.Color(153,153,153));
        logPassword.setText("Ingrese su contraseña");
        logPassword.addActionListener(this::logPasswordActionPerformed);
        logPassword.setEchoChar((char)0);

        passVisible.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/ojocerrado.png"))); // NOI18N
        passVisible.addActionListener(this::passVisibleActionPerformed);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(titulo)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(logConfirm)
                            .addComponent(jLabel1)
                            .addComponent(logUsuario, javax.swing.GroupLayout.DEFAULT_SIZE, 224, Short.MAX_VALUE)
                            .addComponent(jLabel4)
                            .addComponent(logPassword))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(passVisible)))
                .addContainerGap(111, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(titulo, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2))
                    .addComponent(jLabel3))
                .addGap(10, 10, 10)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(logUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(passVisible, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(logPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(logConfirm)
                .addContainerGap(27, Short.MAX_VALUE))
        );

        logUsuario.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (logUsuario.getText().equals("Ingrese su nombre de usuario")) {
                    logUsuario.setText("");
                    logUsuario.setForeground(java.awt.Color.BLACK);
                }
            }

            public void focusLost(java.awt.event.FocusEvent evt) {
                if (logUsuario.getText().isEmpty()) {
                    logUsuario.setText("Ingrese su nombre de usuario");
                    logUsuario.setForeground(new java.awt.Color(153,153,153));
                }
            }
        });
        logPassword.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                logPassword.setEchoChar('*');
                if (String.valueOf(logPassword.getPassword()).equals("Ingrese su contraseña")) {
                    passVisible.setSelected(false);
                    passVisible.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/ojocerrado.png")));
                    logPassword.setText("");
                    logPassword.setForeground(java.awt.Color.BLACK);
                    if (passVisible.isSelected()){
                        logPassword.setEchoChar((char)0);}
                    else {logPassword.setEchoChar('*');}
                }
            }

            public void focusLost(java.awt.event.FocusEvent evt) {
                if (logPassword.getPassword().length == 0) {
                    logPassword.setText("Ingrese su contraseña");
                    passVisible.setSelected(true);
                    logPassword.setForeground(new java.awt.Color(153,153,153));
                    logPassword.setEchoChar((char)0); // muestra el placeholder normal
                }
            }

        });

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void logUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logUsuarioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_logUsuarioActionPerformed

    private void logConfirmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logConfirmActionPerformed
        String usuario = logUsuario.getText();
        var password = new String(logPassword.getPassword());
        Usuario u = auth.login(usuario, password);
        if(u != null){
        java.awt.EventQueue.invokeLater(() -> {
    new MenuPrincipal(sistema, sistemaLib, sistemap).setVisible(true);
});
this.dispose();
    }else{
        System.out.println("Usuario o contraseña incorrectos");
    }
        
    }//GEN-LAST:event_logConfirmActionPerformed

    private void logPasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logPasswordActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_logPasswordActionPerformed

    private void passVisibleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_passVisibleActionPerformed
        if (passVisible.isSelected()){
            if(String.valueOf(logPassword.getPassword()).equals("Ingrese su contraseña")){
              passVisible.setSelected(false);
              passVisible.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/ojocerrado.png")));
            }
            else{ 
                passVisible.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/ojoabierto_1.png")));
                logPassword.setEchoChar((char)0);}
    } else {
            if(!String.valueOf(logPassword.getPassword()).equals("Ingrese su contraseña")){
                logPassword.setEchoChar('*');
                passVisible.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/ojocerrado.png")));}
            else {logPassword.setEchoChar((char)0);}
    }
    }//GEN-LAST:event_passVisibleActionPerformed

    public static void menu() {
       
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    public javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    public javax.swing.JToggleButton logConfirm;
    public javax.swing.JPasswordField logPassword;
    public javax.swing.JTextField logUsuario;
    private javax.swing.JToggleButton passVisible;
    private javax.swing.JLabel titulo;
    // End of variables declaration//GEN-END:variables
}
