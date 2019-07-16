/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.Cliente;

import controller.ControllerCliente;
import java.awt.Color;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.ModelCliente;
import view.ViewMenu;

/**
 *
 * @author LUAN
 */
public class ViewCliente extends javax.swing.JFrame {

    ArrayList<ModelCliente> listaModelCliente = new ArrayList<>();
    ControllerCliente controllerCliente = new ControllerCliente();
    ModelCliente modelCliente = new ModelCliente();

    /**
     * Creates new form ViewCliente
     */
    public ViewCliente() {
        initComponents();
        jScrollPane1.getViewport().setBackground(Color.WHITE);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setTitle("Cliente");
        atualizarTabela();
    }

    //preenche a tabela com os produtos cadastrados no banco
    private void atualizarTabela() {
        listaModelCliente = controllerCliente.getListaClienteController();
        DefaultTableModel modelo = (DefaultTableModel) jTable1.getModel();
        modelo.setNumRows(0);
        listaModelCliente.forEach((m) -> {
            modelo.addRow(new Object[]{
                m.getIdCliente(),
                m.getCliNome(),
                m.getCliCidade(),
                m.getCliTelefone()
            });
        });
    }

    private void novoCliente() {
        String nome, endereco, bairro, cidade, estado, cep, telefone;
        nome = endereco = bairro = cidade = estado = cep = telefone = "";
        int confirmacao;

        do {
            nome = JOptionPane.showInputDialog("Nome do cliente", nome);
            if (nome == null) {
                return;
            }
            endereco = JOptionPane.showInputDialog("Endereco", endereco);
            if (endereco == null) {
                return;
            }
            bairro = JOptionPane.showInputDialog("Bairro", bairro);
            if (bairro == null) {
                return;
            }
            cidade = JOptionPane.showInputDialog("Cidade", cidade);
            if (cidade == null) {
                return;
            }
            estado = JOptionPane.showInputDialog("Estado", estado);
            if (estado == null) {
                return;
            }
            cep = JOptionPane.showInputDialog("Cep", cep);
            if (cep == null) {
                return;
            }
            telefone = JOptionPane.showInputDialog("Telefone", telefone);
            if (telefone == null) {
                return;
            }

            Object[] options = {"Confirmar", "Inicio", "Cancelar"};
            confirmacao = JOptionPane.showOptionDialog(null, "Nome: " + nome + '\n'
                    + "Endereco: " + endereco + '\n'
                    + "Bairro: " + bairro + '\n'
                    + "Cidade: " + cidade + '\n'
                    + "Estado: " + estado + '\n'
                    + "Cep: " + cep,
                    "Novo Cliente", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[1]);

        } while (confirmacao == 1);

        if (estado.length() == 2) {
            if (confirmacao == 0) {
                modelCliente.setCliNome(nome);
                modelCliente.setCliCidade(cidade);
                modelCliente.setCliEndereco(endereco);
                modelCliente.setCliBairro(bairro);
                modelCliente.setCliUf(estado);
                modelCliente.setCliCep(cep);
                modelCliente.setCliTelefone(telefone);
                if (controllerCliente.salvarClienteController(modelCliente) > 0) {
                    atualizarTabela();
                    JOptionPane.showMessageDialog(this, "Cadastro Realizado!");
                } else {
                    JOptionPane.showMessageDialog(this, "Erro ao tentar Cadastrar Cliente.");
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Dados invalidos.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void excluirCliente() {
        int linha = jTable1.getSelectedRow();
        if (linha != -1) {
            int codigoProduto = (int) jTable1.getValueAt(linha, 0);
            Object[] options = {"Confirmar", "Cancelar"};
            if (JOptionPane.showOptionDialog(null, "Cofirmar exclusão", "Excluir", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[1]) == 0) {
                if (controllerCliente.excluirClienteController(codigoProduto)) {
                    atualizarTabela();
                    JOptionPane.showMessageDialog(this, "Excluido com sucesso.");
                } else {
                    JOptionPane.showMessageDialog(null, "Cliente não excluido.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Selecione um cliente", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void alterarCliente() {
        String nome, endereco, bairro, cidade, estado, cep, telefone;

        int linha = jTable1.getSelectedRow();
        if (linha != -1) {
            modelCliente = controllerCliente.getClienteController((int) jTable1.getValueAt(linha, 0));

            int confirmacao;
            do {
                nome = JOptionPane.showInputDialog("Nome do cliente", modelCliente.getCliNome());
                if (nome == null) {
                    return;
                }
                endereco = JOptionPane.showInputDialog("Endereco", modelCliente.getCliEndereco());
                if (endereco == null) {
                    return;
                }
                bairro = JOptionPane.showInputDialog("Bairro", modelCliente.getCliBairro());
                if (bairro == null) {
                    return;
                }
                cidade = JOptionPane.showInputDialog("Cidade", modelCliente.getCliCidade());
                if (cidade == null) {
                    return;
                }
                estado = JOptionPane.showInputDialog("Estado", modelCliente.getCliUf());
                if (estado == null) {
                    return;
                }
                cep = JOptionPane.showInputDialog("Cep", modelCliente.getCliCep());
                if (cep == null) {
                    return;
                }
                telefone = JOptionPane.showInputDialog("Telefone", modelCliente.getCliTelefone());
                if (telefone == null) {
                    return;
                }
                Object[] options = {"Confirmar", "Inicio", "Cancelar"};
                confirmacao = JOptionPane.showOptionDialog(null, "[" + modelCliente.getCliNome() + "] para [" + nome + "]" + '\n'
                        + "[" + modelCliente.getCliEndereco() + "] para [" + endereco + "]" + '\n'
                        + "[" + modelCliente.getCliBairro() + "] para [" + bairro + "]" + '\n'
                        + "[" + modelCliente.getCliCidade() + "] para [" + cidade + "]" + '\n'
                        + "[" + modelCliente.getCliUf() + "] para [" + estado + "]" + '\n'
                        + "[" + modelCliente.getCliCep() + "] para [" + cidade + "]" + '\n'
                        + "[" + modelCliente.getCliTelefone() + "] para [" + telefone + "]" + '\n',
                        "Novo Cliente", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[1]);

            } while (confirmacao == 1);

            if (confirmacao == 0) {
                if (modelCliente.getCliUf().length() == 2) {
                    modelCliente.setCliNome(nome);
                    modelCliente.setCliEndereco(endereco);
                    modelCliente.setCliBairro(bairro);
                    modelCliente.setCliCidade(cidade);
                    modelCliente.setCliUf(estado);
                    modelCliente.setCliCep(cep);
                    modelCliente.setCliTelefone(telefone);
                    if (controllerCliente.atualizarClienteController(modelCliente)) {
                        atualizarTabela();
                        JOptionPane.showMessageDialog(this, "Alteração Realizada!");
                    } else {
                        JOptionPane.showMessageDialog(this, "Erro ao tentar Alterar Cliente.");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Cliente não alterado. Dados invalidos.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Selecione um produto", "Erro", JOptionPane.ERROR_MESSAGE);
        }

    }

    private void detalhesProduto() {
        int linha = jTable1.getSelectedRow();
        if (linha != -1) {
            ModelCliente cliente = controllerCliente.getClienteController((int) jTable1.getValueAt(linha, 0));
            if (cliente != null) {
                JOptionPane.showMessageDialog(this, cliente.getCliNome() + '\n'
                        + cliente.getCliEndereco() + '\n'
                        + cliente.getCliBairro() + '\n'
                        + cliente.getCliCidade() + '\n'
                        + cliente.getCliUf() + '\n'
                        + cliente.getCliCep() + '\n', "Detalhes", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Erro na conexão.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Selecione um cliente", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void pesquisarProduto() {
        String entrada = jtf_Pesquisar.getText();
        if (!"".equals(entrada)) {
            DefaultTableModel modelo = (DefaultTableModel) jTable1.getModel();
            modelo.setNumRows(0);

            listaModelCliente.forEach((p) -> {
                String auxiliar;
                if (entrada.length() <= p.getCliNome().length()) {
                    auxiliar = p.getCliNome().substring(0, entrada.length());
                    if (entrada.equals(auxiliar)) {
                        modelo.addRow(new Object[]{
                            p.getIdCliente(),
                            p.getCliNome(),
                            p.getCliCidade(),
                            p.getCliTelefone()
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
        jLabel1 = new javax.swing.JLabel();

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
                "Código", "Nome", "Cidade", "Telefone"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
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

        jb_Ver.setBackground(new java.awt.Color(0, 0, 0));
        jb_Ver.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
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

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/clientes_.png"))); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
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
                        .addGap(18, 18, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jb_Novo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jb_Excluir, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jb_Alterar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jb_Ver, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jb_Voltar)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 355, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jb_Pesquisar)
                            .addComponent(jtf_Pesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jb_Novo, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jb_Excluir, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jb_Alterar, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jb_Ver, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(28, 28, 28)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)))
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
        novoCliente();
    }//GEN-LAST:event_jb_NovoActionPerformed

    private void jb_AlterarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jb_AlterarActionPerformed
        // TODO add your handling code here:
        alterarCliente();
    }//GEN-LAST:event_jb_AlterarActionPerformed

    private void jb_ExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jb_ExcluirActionPerformed
        // TODO add your handling code here:
        excluirCliente();
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
        new ViewMenu().setVisible(true);
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
            java.util.logging.Logger.getLogger(ViewCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ViewCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ViewCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ViewCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ViewCliente().setVisible(true);
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
    private javax.swing.JButton jb_Ver;
    private javax.swing.JButton jb_Voltar;
    private javax.swing.JTextField jtf_Pesquisar;
    // End of variables declaration//GEN-END:variables
}
