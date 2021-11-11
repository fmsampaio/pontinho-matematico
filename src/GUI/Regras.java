/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.awt.Image;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 *
 * @author hp
 */
public class Regras extends javax.swing.JFrame {

    int contaCliquesFlechas = 0;

    public Regras() {
        initComponents();
        btn_volta.setVisible(false);
        this.setLocationRelativeTo(null);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        btn_volta = new javax.swing.JLabel();
        btn_proximo = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        imagem_regras = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(995, 600));
        getContentPane().setLayout(null);

        jPanel1.setLayout(null);

        btn_volta.setBackground(new java.awt.Color(204, 0, 204));
        btn_volta.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_voltaMouseClicked(evt);
            }
        });
        jPanel1.add(btn_volta);
        btn_volta.setBounds(120, 106, 60, 50);

        btn_proximo.setBackground(new java.awt.Color(255, 51, 255));
        btn_proximo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_proximoMouseClicked(evt);
            }
        });
        jPanel1.add(btn_proximo);
        btn_proximo.setBounds(820, 106, 60, 50);

        jButton1.setBackground(new java.awt.Color(0, 204, 204));
        jButton1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton1.setText("Voltar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1);
        jButton1.setBounds(820, 510, 150, 40);

        imagem_regras.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens_Jogo/Regras1.png"))); // NOI18N
        imagem_regras.setToolTipText("");
        jPanel1.add(imagem_regras);
        imagem_regras.setBounds(0, -50, 980, 660);

        getContentPane().add(jPanel1);
        jPanel1.setBounds(0, 0, 990, 570);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_voltaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_voltaMouseClicked
        try {
            contaCliquesFlechas = contaCliquesFlechas - 1;
            Image imagem = null;
            switch (contaCliquesFlechas) {
                case 0:
                    imagem = ImageIO.read(getClass().getResource("/Imagens_Jogo/Regras1.png"));
                    btn_volta.setVisible(false);
                    break;

                case 1:
                    imagem = ImageIO.read(getClass().getResource("/Imagens_Jogo/Regras2.png"));
                    btn_volta.setVisible(true);
                    break;

                case 2:
                    imagem = ImageIO.read(getClass().getResource("/Imagens_Jogo/Regras3.png"));
                    break;

                case 3:
                    btn_volta.setLocation(120, 106);
                    btn_proximo.setLocation(830, 106);
                    imagem = ImageIO.read(getClass().getResource("/Imagens_Jogo/Regras4.png"));
                    break;
                case 4:
                    btn_volta.setSize(50, 60);
                    btn_proximo.setSize(50, 60);
                    btn_volta.setLocation(90, 80);
                    btn_proximo.setLocation(870, 80);
                    btn_proximo.setVisible(true);
                    imagem = ImageIO.read(getClass().getResource("/Imagens_Jogo/Regras5.png"));
                    break;
            }
            ImageIcon img = new ImageIcon(imagem);
            imagem_regras.setIcon(img);
        } catch (IOException ex) {
            System.out.println("ERRO");
        }
        System.out.println("Cliques: " + contaCliquesFlechas);
    }//GEN-LAST:event_btn_voltaMouseClicked

    private void btn_proximoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_proximoMouseClicked
        try {
            contaCliquesFlechas = contaCliquesFlechas + 1;
            Image imagem = null;
            switch (contaCliquesFlechas) {
                case 1:
                    imagem = ImageIO.read(getClass().getResource("/Imagens_Jogo/Regras2.png"));
                    btn_volta.setVisible(true);
                    break;

                case 2:
                    imagem = ImageIO.read(getClass().getResource("/Imagens_Jogo/Regras3.png"));
                    break;

                case 3:
                    btn_volta.setLocation(120, 106);
                    btn_proximo.setLocation(830, 106);
                    imagem = ImageIO.read(getClass().getResource("/Imagens_Jogo/Regras4.png"));
                    break;
                case 4:
                    btn_volta.setSize(50, 60);
                    btn_proximo.setSize(50, 60);
                    btn_volta.setLocation(90, 80);
                    btn_proximo.setLocation(870, 80);
                    imagem = ImageIO.read(getClass().getResource("/Imagens_Jogo/Regras5.png"));
                    break;
                case 5:
                    btn_volta.setSize(50, 60);
                    btn_proximo.setSize(50, 60);
                    btn_volta.setLocation(90, 80);
                    btn_proximo.setLocation(870, 80);
                    btn_proximo.setVisible(false);
                    imagem = ImageIO.read(getClass().getResource("/Imagens_Jogo/Regras6.png"));
                    break;
            }
            ImageIcon img = new ImageIcon(imagem);
            imagem_regras.setIcon(img);
        } catch (IOException ex) {
            System.out.println("Erro");
        }
        System.out.println("Cliques: " + contaCliquesFlechas);
    }//GEN-LAST:event_btn_proximoMouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        dispose();
        TelaAcesso acesso = new TelaAcesso();
        acesso.setTitle("Acesso");
        acesso.setResizable(false);
        acesso.setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

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
            java.util.logging.Logger.getLogger(Regras.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Regras.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Regras.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Regras.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Regras().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel btn_proximo;
    private javax.swing.JLabel btn_volta;
    private javax.swing.JLabel imagem_regras;
    private javax.swing.JButton jButton1;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
