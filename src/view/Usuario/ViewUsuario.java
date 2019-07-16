/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.Usuario;

import controller.ControllerUsuario;
import java.awt.Color;
import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.table.DefaultTableModel;
import model.ModelUsuario;
import view.ViewMenu;

/**
 *
 * @author LUAN
 */
public class ViewUsuario extends javax.swing.JFrame {

    ArrayList<ModelUsuario> listaModelUsuario = new ArrayList<>();
    ControllerUsuario controllerUsuario = new ControllerUsuario();
    ModelUsuario modelUsuario = new ModelUsuario();

    /**
     * Creates new form ViewUsuario
     */
    public ViewUsuario() {
        initComponents();
        jScrollPane1.getViewport().setBackground(Color.WHITE);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setTitle("Usuario");
        atualizarTabela();
    }

    private void atualizarTabela() {
        listaModelUsuario = controllerUsuario.getListaUsuarioController();
        DefaultTableModel modelo = (DefaultTableModel) jTable1.getModel();
        modelo.setNumRows(0);
        listaModelUsuario.forEach((m) -> {
            modelo.addRow(new Object[]{
                m.getIdUsuario(),
                m.getUsuNome(),
                m.getUsuLogin()
            });
        });
    }

    private boolean verificaSenha() {
        String[] options = new String[]{"Confirmar", "Cancelar"};
        JPanel panel = new JPanel();
        JLabel label = new JLabel("Senha:");

        JPasswordField pass = new JPasswordField(20);

        panel.add(label);
        panel.add(pass);

        String s;
        int option;
        do {
            option = JOptionPane.showOptionDialog(null, panel, "Senha Antiga",
                    JOptionPane.NO_OPTION, JOptionPane.PLAIN_MESSAGE,
                    null, options, options[1]);
            if (option == 1) {
                break;
            }
            char[] password = pass.getPassword();
            s = new String(password);

            if (!s.equals(modelUsuario.getUsuSenha())) {
                JOptionPane.showMessageDialog(null, "Senha incorreta.", "Erro", JOptionPane.ERROR_MESSAGE);
            } else {
                break;
            }
            pass.setText("");
        } while (true);
        return (option == 0);
    }

    private String criarSenha() {
        String[] options = new String[]{"Confirmar", "Cancelar"};
        JPanel panel = new JPanel();
        JLabel label = new JLabel("Senha:");
        JLabel labelConfirmar = new JLabel("Confirmar:");

        JPasswordField pass = new JPasswordField(20);
        JPasswordField passConfirmar = new JPasswordField(20);

        panel.add(label);
        panel.add(passConfirmar);
        panel.add(labelConfirmar);
        panel.add(pass);

        String s, sC = "";
        int option;
        do {
            option = JOptionPane.showOptionDialog(null, panel, "Senha",
                    JOptionPane.NO_OPTION, JOptionPane.PLAIN_MESSAGE,
                    null, options, options[1]);
            if (option == 1) {
                break;
            }
            char[] password = pass.getPassword();
            char[] passwordC = passConfirmar.getPassword();
            s = new String(password);
            sC = new String(passwordC);

            if (!s.equals(sC)) {
                JOptionPane.showMessageDialog(null, "Senha incorreta.", "Erro", JOptionPane.ERROR_MESSAGE);
            } else {
                break;
            }
            pass.setText("");
            passConfirmar.setText("");
        } while (true);
        if (option == 1) {
            return "";
        }
        return sC;
    }

    private void novoUsuario() {
        String nome, login;

        nome = JOptionPane.showInputDialog("Nome");
        if (nome == null) {
            return;
        }

        login = JOptionPane.showInputDialog("Login");
        if (login == null) {
            return;
        }

        String s = criarSenha();
        if (!s.isEmpty()) {
            modelUsuario.setUsuNome(nome);
            modelUsuario.setUsuLogin(login);
            modelUsuario.setUsuSenha(s);
            if (controllerUsuario.salvarUsuarioController(modelUsuario) > 0) {
                atualizarTabela();
                JOptionPane.showMessageDialog(this, "Cadastro Realizado!");
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao tentar Cadastrar Usuario.");
            }
        }

    }

    private void excluirUsuario() {
        int linha = jTable1.getSelectedRow();
        if (linha != -1) {
            ModelUsuario usu = controllerUsuario.getUsuarioController((int) jTable1.getValueAt(linha, 0));
            if (usu != null) {
                modelUsuario = usu;
                if (verificaSenha()) {
                    String[] options = new String[]{"Confirmar", "Cancelar"};
                    if (JOptionPane.showOptionDialog(null, "Cofirmar exclusão", "Excluir", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[1]) == 0) {
                        if (controllerUsuario.excluirUsuarioController(usu.getIdUsuario())) {
                            atualizarTabela();
                            JOptionPane.showMessageDialog(this, "Excluido com sucesso.");
                        } else {
                            JOptionPane.showMessageDialog(null, "Usuario não excluido.", "Erro", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }

            } else {
                JOptionPane.showMessageDialog(null, "Erro ao acessar o banco", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Selecione um usuario", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void alterarUsuario() {
        int linha = jTable1.getSelectedRow();
        if (linha != -1) {
            ModelUsuario usu = controllerUsuario.getUsuarioController((int) jTable1.getValueAt(linha, 0));
            if (usu != null) {
                modelUsuario = usu;
                if (verificaSenha()) {
                    String nome = JOptionPane.showInputDialog("Novo Nome");
                    if (nome == null) {
                        return;
                    }
                    String login = JOptionPane.showInputDialog("Novo Login");
                    if (login == null) {
                        return;
                    }
                    String senha = criarSenha();
                    usu.setUsuSenha(senha);
                    usu.setUsuNome(nome);
                    usu.setUsuLogin(login);
                    if (!senha.isEmpty()) {
                        if (controllerUsuario.atualizarUsuarioController(modelUsuario)) {
                            atualizarTabela();
                            JOptionPane.showMessageDialog(this, "Alterado com sucesso.");
                        } else {
                            JOptionPane.showMessageDialog(null, "Usuario não excluido.", "Erro", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }

            } else {
                JOptionPane.showMessageDialog(null, "Erro ao acessar o banco", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Selecione um usuario", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void pesquisarUsuario() {
        String entrada = jtf_Pesquisar.getText();
        if (!"".equals(entrada)) {
            DefaultTableModel modelo = (DefaultTableModel) jTable1.getModel();
            modelo.setNumRows(0);

            listaModelUsuario.forEach((p) -> {
                String auxiliar;
                if (entrada.length() <= p.getUsuNome().length()) {
                    auxiliar = p.getUsuNome().substring(0, entrada.length());
                    if (entrada.equals(auxiliar)) {
                        modelo.addRow(new Object[]{
                            p.getIdUsuario(),
                            p.getUsuNome(),
                            p.getUsuLogin(),
                        });
                    }
                }
            });
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jb_Pesquisar = new javax.swing.JButton();
        jb_Voltar = new javax.swing.JButton();
        jb_Excluir = new javax.swing.JButton();
        jb_Alterar = new javax.swing.JButton();
        jb_Novo = new javax.swing.JButton();
        jtf_Pesquisar = new javax.swing.JTextField();
        jb_Todos = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jTable1.setForeground(new java.awt.Color(204, 0, 0));
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Código", "Nome", "Login"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTable1);
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(0).setPreferredWidth(15);
        }

        jb_Pesquisar.setBackground(new java.awt.Color(0, 0, 0));
        jb_Pesquisar.setForeground(new java.awt.Color(255, 255, 255));
        jb_Pesquisar.setText("Pesquisar");
        jb_Pesquisar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jb_PesquisarActionPerformed(evt);
            }
        });

        jb_Voltar.setBackground(java.awt.Color.lightGray);
        jb_Voltar.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jb_Voltar.setText("Voltar");
        jb_Voltar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jb_VoltarActionPerformed(evt);
            }
        });

        jb_Excluir.setBackground(new java.awt.Color(0, 0, 0));
        jb_Excluir.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jb_Excluir.setForeground(new java.awt.Color(255, 255, 255));
        jb_Excluir.setText("Excluir");
        jb_Excluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jb_ExcluirActionPerformed(evt);
            }
        });

        jb_Alterar.setBackground(new java.awt.Color(0, 0, 0));
        jb_Alterar.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jb_Alterar.setForeground(new java.awt.Color(255, 255, 255));
        jb_Alterar.setText("Alterar");
        jb_Alterar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jb_AlterarActionPerformed(evt);
            }
        });

        jb_Novo.setBackground(new java.awt.Color(0, 0, 0));
        jb_Novo.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jb_Novo.setForeground(new java.awt.Color(255, 255, 255));
        jb_Novo.setText("Novo");
        jb_Novo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jb_NovoActionPerformed(evt);
            }
        });

        jb_Todos.setBackground(new java.awt.Color(0, 0, 0));
        jb_Todos.setForeground(new java.awt.Color(255, 255, 255));
        jb_Todos.setText("Todos");
        jb_Todos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jb_TodosActionPerformed(evt);
            }
        });

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/cliente_111x111.png"))); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 470, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jtf_Pesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 363, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jb_Todos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jb_Pesquisar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jb_Novo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jb_Alterar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jb_Excluir, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jb_Voltar)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 355, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jb_Pesquisar)
                            .addComponent(jtf_Pesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jb_Novo, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jb_Excluir, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jb_Alterar, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jb_Todos)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jb_Voltar, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(23, 23, 23))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 497, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    private void jb_PesquisarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jb_PesquisarActionPerformed
        // TODO add your handling code here:
        pesquisarUsuario();
    }//GEN-LAST:event_jb_PesquisarActionPerformed

    private void jb_VoltarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jb_VoltarActionPerformed
        // TODO add your handling code here:
        new ViewMenu().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jb_VoltarActionPerformed

    private void jb_ExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jb_ExcluirActionPerformed
        // TODO add your handling code here:
        excluirUsuario();
    }//GEN-LAST:event_jb_ExcluirActionPerformed

    private void jb_AlterarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jb_AlterarActionPerformed
        // TODO add your handling code here:
        alterarUsuario();
    }//GEN-LAST:event_jb_AlterarActionPerformed

    private void jb_NovoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jb_NovoActionPerformed
        // TODO add your handling code here:
        novoUsuario();
    }//GEN-LAST:event_jb_NovoActionPerformed

    private void jb_TodosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jb_TodosActionPerformed
        // TODO add your handling code here:
        atualizarTabela();
    }//GEN-LAST:event_jb_TodosActionPerformed

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
            java.util.logging.Logger.getLogger(ViewUsuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ViewUsuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ViewUsuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ViewUsuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ViewUsuario().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JButton jb_Alterar;
    private javax.swing.JButton jb_Excluir;
    private javax.swing.JButton jb_Novo;
    private javax.swing.JButton jb_Pesquisar;
    private javax.swing.JButton jb_Todos;
    private javax.swing.JButton jb_Voltar;
    private javax.swing.JTextField jtf_Pesquisar;
    // End of variables declaration//GEN-END:variables
}
