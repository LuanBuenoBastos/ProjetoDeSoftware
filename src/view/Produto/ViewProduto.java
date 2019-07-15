/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.Produto;

import controller.ControlerProdutos;
import java.awt.Color;
import java.awt.HeadlessException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.ModelProdutos;

/**
 *
 * @author LUAN
 */
public class ViewProduto extends javax.swing.JFrame {

    ArrayList<ModelProdutos> listaModelProduto = new ArrayList<>();
    ControlerProdutos controllerProdutos = new ControlerProdutos();
    ModelProdutos modelProduto = new ModelProdutos();

    /**
     * Creates new form ViewCliente
     */
    public ViewProduto() {
        initComponents();
        jScrollPane1.getViewport().setBackground(Color.WHITE);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        atualizarTabela();
    }

    //preenche a tabela com os produtos cadastrados no banco
    private void atualizarTabela() {
        listaModelProduto = controllerProdutos.retornarListaProdutoController();
        DefaultTableModel modelo = (DefaultTableModel) jTable1.getModel();
        modelo.setNumRows(0);
        listaModelProduto.forEach((m) -> {
            modelo.addRow(new Object[]{
                m.getIdProduto(),
                m.getProNome(),
                m.getProValor(),
                m.getProEstoque()
            });
        });
    }

    private void novoProduto() {
        String nome, valor, qnt;
        nome = valor = qnt = "";
        int confirmacao;

        do {
            nome = JOptionPane.showInputDialog("Nome do produto", nome);
            if (nome == null) {
                return;
            }
            valor = JOptionPane.showInputDialog("Valor", valor);
            if (valor == null) {
                return;
            }
            qnt = JOptionPane.showInputDialog("Quantidade", qnt);
            if (qnt == null) {
                return;
            }

            confirmacao = JOptionPane.showConfirmDialog(this, "Nome: " + nome + '\n'
                    + "Valor: " + valor + '\n'
                    + "Quantidade: " + qnt);
        } while (confirmacao == 1);

        if (confirmacao == 0) {
            try {
                modelProduto.setProNome(nome);
                modelProduto.setProEstoque(Integer.parseInt(qnt));
                modelProduto.setProValor(Double.parseDouble(valor));
                if (controllerProdutos.salvarProdutoController(modelProduto) > 0) {
                    atualizarTabela();
                    JOptionPane.showMessageDialog(this, "Cadastro Realizado!");
                } else {
                    JOptionPane.showMessageDialog(this, "Erro ao tentar Cadastrar Produto.");
                }
            } catch (HeadlessException | NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Dados invalidos!");
            }

        }
    }

    private void excluirProduto() {
        int linha = jTable1.getSelectedRow();
        if (linha != -1) {
            int codigoProduto = (int) jTable1.getValueAt(linha, 0);
            if (controllerProdutos.excluirProdutoController(codigoProduto)) {
                atualizarTabela();
                JOptionPane.showMessageDialog(this, "Excluido com sucesso.");
            } else {
                JOptionPane.showMessageDialog(null, "Produto não excluido.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Selecione um produto", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void alterarProduto() {
        String nome, valor, qnt;

        int linha = jTable1.getSelectedRow();
        if (linha != -1) {
            int confirmacao;
            do {
                nome = JOptionPane.showInputDialog("Nome do produto", jTable1.getValueAt(linha, 1));
                if (nome == null) {
                    return;
                }
                valor = JOptionPane.showInputDialog("Valor", jTable1.getValueAt(linha, 2));
                if (valor == null) {
                    return;
                }
                qnt = JOptionPane.showInputDialog("Quantidade", jTable1.getValueAt(linha, 3));
                if (qnt == null) {
                    return;
                }

                confirmacao = JOptionPane.showConfirmDialog(this, "Nome: " + "[" + jTable1.getValueAt(linha, 1) + "] para [" + nome + "]" + '\n'
                        + "Valor: " + "[" + jTable1.getValueAt(linha, 2) + "] para [" + valor + "]" + '\n'
                        + "Quantidade: " + "[" + jTable1.getValueAt(linha, 3) + "] para [" + qnt + "]");
            } while (confirmacao == 1);

            if (confirmacao == 0) {
                try {
                    modelProduto.setProNome(nome);
                    modelProduto.setProEstoque(Integer.parseInt(qnt));
                    modelProduto.setProValor(Double.parseDouble(valor));
                    modelProduto.setIdProduto((int) jTable1.getValueAt(linha, 0));

                    if (controllerProdutos.alterarProdutoController(modelProduto)) {
                        atualizarTabela();
                        JOptionPane.showMessageDialog(this, "Alteração Realizada!");
                    } else {
                        JOptionPane.showMessageDialog(this, "Erro ao tentar Alterar Produto.");
                    }
                } catch (HeadlessException | NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Produto não alterado. Dados invalidos.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Selecione um produto", "Erro", JOptionPane.ERROR_MESSAGE);
        }

    }

    private void detalhesProduto() {
        int linha = jTable1.getSelectedRow();
        if (linha != -1) {
            ModelProdutos produto = controllerProdutos.retornarProdutoController((int) jTable1.getValueAt(linha, 0));
            if (produto != null) {
                JOptionPane.showMessageDialog(this, produto.getProNome()
                        + "\n" + produto.getProValor()
                        + "\n" + produto.getProEstoque(), "Detalhes", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Erro na conexão.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Selecione um produto", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void pesquisarProduto() {
        String entrada = jtf_Pesquisar.getText();
        if (!"".equals(entrada)) {
            DefaultTableModel modelo = (DefaultTableModel) jTable1.getModel();
            modelo.setNumRows(0);

            listaModelProduto.forEach((p) -> {
                String auxiliar;
                if (entrada.length() <= p.getProNome().length()) {
                    auxiliar = p.getProNome().substring(0, entrada.length());
                    if (entrada.equals(auxiliar)) {
                        modelo.addRow(new Object[]{
                            p.getIdProduto(),
                            p.getProNome(),
                            p.getProValor(),
                            p.getProEstoque()
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
        jb_Ver = new javax.swing.JButton();
        jb_Todos = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jTable1.setForeground(new java.awt.Color(204, 0, 0));
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Código", "Nome", "Valor", "Quantidade"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Double.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
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
            jTable1.getColumnModel().getColumn(0).setPreferredWidth(50);
            jTable1.getColumnModel().getColumn(1).setPreferredWidth(120);
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
        jb_Excluir.setFont(new java.awt.Font("Arial", 1, 11)); // NOI18N
        jb_Excluir.setForeground(new java.awt.Color(255, 255, 255));
        jb_Excluir.setText("Excluir");
        jb_Excluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jb_ExcluirActionPerformed(evt);
            }
        });

        jb_Alterar.setBackground(new java.awt.Color(0, 0, 0));
        jb_Alterar.setFont(new java.awt.Font("Arial", 1, 11)); // NOI18N
        jb_Alterar.setForeground(new java.awt.Color(255, 255, 255));
        jb_Alterar.setText("Alterar");
        jb_Alterar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jb_AlterarActionPerformed(evt);
            }
        });

        jb_Novo.setBackground(new java.awt.Color(0, 0, 0));
        jb_Novo.setFont(new java.awt.Font("Arial", 1, 11)); // NOI18N
        jb_Novo.setForeground(new java.awt.Color(255, 255, 255));
        jb_Novo.setText("Novo");
        jb_Novo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jb_NovoActionPerformed(evt);
            }
        });

        jb_Ver.setBackground(new java.awt.Color(0, 0, 0));
        jb_Ver.setFont(new java.awt.Font("Arial", 1, 11)); // NOI18N
        jb_Ver.setForeground(new java.awt.Color(240, 240, 240));
        jb_Ver.setText("Ver");
        jb_Ver.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jb_VerActionPerformed(evt);
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

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(22, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 470, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jtf_Pesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 363, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jb_Todos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jb_Pesquisar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jb_Alterar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jb_Novo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jb_Excluir, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jb_Ver, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(94, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jb_Voltar)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 355, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jb_Ver)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jb_Novo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jb_Alterar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jb_Excluir)))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jb_Pesquisar)
                    .addComponent(jtf_Pesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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

    private void jb_VerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jb_VerActionPerformed
        // TODO add your handling code here:
        detalhesProduto();
    }//GEN-LAST:event_jb_VerActionPerformed

    private void jb_NovoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jb_NovoActionPerformed
        // TODO add your handling code here:
        novoProduto();
    }//GEN-LAST:event_jb_NovoActionPerformed

    private void jb_AlterarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jb_AlterarActionPerformed
        // TODO add your handling code here:
        alterarProduto();
    }//GEN-LAST:event_jb_AlterarActionPerformed

    private void jb_ExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jb_ExcluirActionPerformed
        // TODO add your handling code here:
        excluirProduto();
    }//GEN-LAST:event_jb_ExcluirActionPerformed

    private void jb_PesquisarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jb_PesquisarActionPerformed
        // TODO add your handling code here:
        pesquisarProduto();
    }//GEN-LAST:event_jb_PesquisarActionPerformed

    private void jb_TodosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jb_TodosActionPerformed
        // TODO add your handling code here:
        atualizarTabela();
    }//GEN-LAST:event_jb_TodosActionPerformed

    private void jb_VoltarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jb_VoltarActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_jb_VoltarActionPerformed

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
            java.util.logging.Logger.getLogger(ViewProduto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ViewProduto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ViewProduto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ViewProduto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ViewProduto().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JButton jb_Alterar;
    private javax.swing.JButton jb_Excluir;
    private javax.swing.JButton jb_Novo;
    private javax.swing.JButton jb_Pesquisar;
    private javax.swing.JButton jb_Todos;
    private javax.swing.JButton jb_Ver;
    private javax.swing.JButton jb_Voltar;
    private javax.swing.JTextField jtf_Pesquisar;
    // End of variables declaration//GEN-END:variables
}
