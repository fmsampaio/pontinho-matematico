package GUI;

import Jogo.Cliente;
import Jogo.Servidor;
import java.io.IOException;
import javax.swing.JOptionPane;

public class TelaAcesso extends javax.swing.JFrame {

    private Cliente cliente;
    Servidor servidor = new Servidor();

    public TelaAcesso() {
        initComponents();
        this.setLocationRelativeTo(null);
        lblCarregando.setVisible(false);
        setResizable(false);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        btnInstrucoes = new javax.swing.JButton();
        btnJogar = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        lblCarregando = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        ipServidor = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtNickJogador = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(990, 600));
        getContentPane().setLayout(null);

        jPanel1.setMaximumSize(new java.awt.Dimension(990, 600));
        jPanel1.setLayout(null);

        jPanel2.setBackground(new java.awt.Color(204, 255, 180));
        jPanel2.setLayout(null);

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jPanel2.add(jLabel4);
        jLabel4.setBounds(183, 24, 0, 0);

        btnInstrucoes.setBackground(new java.awt.Color(245, 171, 53));
        btnInstrucoes.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnInstrucoes.setText("Instruções");
        btnInstrucoes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInstrucoesActionPerformed(evt);
            }
        });
        jPanel2.add(btnInstrucoes);
        btnInstrucoes.setBounds(210, 370, 150, 40);

        btnJogar.setBackground(new java.awt.Color(0, 177, 59));
        btnJogar.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnJogar.setText("Jogar");
        btnJogar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnJogarActionPerformed(evt);
            }
        });
        jPanel2.add(btnJogar);
        btnJogar.setBounds(380, 370, 150, 40);
        jPanel2.add(jLabel7);
        jLabel7.setBounds(90, 130, 40, 0);

        lblCarregando.setBackground(new java.awt.Color(204, 255, 180));
        lblCarregando.setFont(new java.awt.Font("Palatino Linotype", 1, 14)); // NOI18N
        lblCarregando.setForeground(new java.awt.Color(255, 0, 51));
        lblCarregando.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblCarregando.setText("Aguardando oponente conectar-se...");
        lblCarregando.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        lblCarregando.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        lblCarregando.setOpaque(true);
        jPanel2.add(lblCarregando);
        lblCarregando.setBounds(300, 330, 230, 30);

        jPanel3.setBackground(new java.awt.Color(0, 102, 0));

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens_Jogo/oie_zwbEUeoGKx8q_menor.png"))); // NOI18N
        jPanel3.add(jLabel6);

        jPanel2.add(jPanel3);
        jPanel3.setBounds(0, 0, 560, 60);

        jLabel5.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 102, 51));
        jLabel5.setText("IP Servidor:");
        jPanel2.add(jLabel5);
        jLabel5.setBounds(180, 220, 90, 20);

        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens_Jogo/oie_transparent (25).png"))); // NOI18N
        jPanel2.add(jLabel8);
        jLabel8.setBounds(133, 250, 41, 40);

        jLabel9.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 102, 51));
        jLabel9.setText("NickName:");
        jPanel2.add(jLabel9);
        jLabel9.setBounds(180, 120, 90, 20);

        ipServidor.setBackground(new java.awt.Color(204, 255, 204));
        ipServidor.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        ipServidor.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 102, 51), 4, true));
        ipServidor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ipServidorActionPerformed(evt);
            }
        });
        jPanel2.add(ipServidor);
        ipServidor.setBounds(180, 250, 250, 38);

        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens_Jogo/user-icon.png"))); // NOI18N
        jPanel2.add(jLabel10);
        jLabel10.setBounds(140, 150, 50, 35);

        txtNickJogador.setBackground(new java.awt.Color(204, 255, 204));
        txtNickJogador.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        txtNickJogador.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 102, 51), 4, true));
        txtNickJogador.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNickJogadorActionPerformed(evt);
            }
        });
        jPanel2.add(txtNickJogador);
        txtNickJogador.setBounds(180, 150, 250, 38);

        jPanel1.add(jPanel2);
        jPanel2.setBounds(230, 80, 560, 440);

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens_Jogo/fundo.png"))); // NOI18N
        jLabel2.setToolTipText("");
        jPanel1.add(jLabel2);
        jLabel2.setBounds(-260, 0, 1370, 600);

        getContentPane().add(jPanel1);
        jPanel1.setBounds(0, 0, 990, 600);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnInstrucoesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInstrucoesActionPerformed
        dispose();
        Regras regras = new Regras();
        regras.setTitle("Instruções do Jogo");
        regras.setResizable(false);
        regras.setVisible(true);
    }//GEN-LAST:event_btnInstrucoesActionPerformed

    private void btnJogarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnJogarActionPerformed

        if (ipServidor.getText().trim().equals("") || txtNickJogador.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Preencha os dois campos!");
        } else {
            try {
                cliente = new Cliente(ipServidor.getText(), 12345, txtNickJogador.getText());
                cliente.executa();
                cliente.enviaDados("logar;" + txtNickJogador.getText());
                lblCarregando.setVisible(true);
                ipServidor.setEditable(false);
                txtNickJogador.setEditable(false);
                btnJogar.setEnabled(false);
                btnInstrucoes.setEnabled(false);

            } catch (IOException e) {
                lblCarregando.setVisible(false);
                JOptionPane.showMessageDialog(null, "Erro na conexão!");
            }
        }
    }//GEN-LAST:event_btnJogarActionPerformed

    private void ipServidorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ipServidorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ipServidorActionPerformed

    private void txtNickJogadorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNickJogadorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNickJogadorActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(TelaAcesso.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaAcesso.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaAcesso.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaAcesso.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TelaAcesso().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnInstrucoes;
    private javax.swing.JButton btnJogar;
    private javax.swing.JTextField ipServidor;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JLabel lblCarregando;
    private javax.swing.JTextField txtNickJogador;
    // End of variables declaration//GEN-END:variables
}
