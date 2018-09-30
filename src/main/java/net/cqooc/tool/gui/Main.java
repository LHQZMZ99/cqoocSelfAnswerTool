package net.cqooc.tool.gui;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import net.cqooc.tool.APIController;
import net.cqooc.tool.domain.ImportUser;
import net.cqooc.tool.util.CSVReader;
import net.cqooc.tool.util.CapchaSaveConfig;
import net.cqooc.tool.util.JacksonUtil;
import org.htmlparser.util.ParserException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.List;

import static javax.swing.JOptionPane.OK_CANCEL_OPTION;
import static javax.swing.JOptionPane.QUESTION_MESSAGE;

/**
 *
 * @author vincent
 */
public class Main extends javax.swing.JFrame {

    private List<ImportUser> user;
    /**
     * Creates new form Main
     */
    public Main() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        this.setTitle("重庆高校测试讨论视频管理系统V1.0");
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        importAnswerB = new JButton();

        saveButton = new JButton();
        saveButton.setText("选择保存验证码的文件夹");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDragEnabled(true);
                fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
                fileChooser.showOpenDialog(Main.this);
                if(fileChooser.getSelectedFile() != null){
                    if(fileChooser.getSelectedFile().isFile()){
                        JOptionPane.showInputDialog("请选择一个文件夹");
                        return;
                    }else{
                        saveLocationDir = fileChooser.getSelectedFile();
                    }

                }

            }
        });
        saveButton.setVisible(false);
        verifyLoginLabel = new JLabel("是否开启自动验证码登录,默认不需要开启，默认自动登录失败请开启");
        isAuthRadioButton = new JRadioButton();
        isAuthRadioButton.setSelected(false);
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jButton2.setText("导入学生对应账号");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                try {
                    jButton2ActionPerformed(evt);
                } catch (IOException e) {
                    JOptionPane.showOptionDialog( Main.this , "导入失败 " + e.getMessage() , "通知" , OK_CANCEL_OPTION , QUESTION_MESSAGE , null , null , null);
                    //e.printStackTrace();
                }
            }
        });
        importAnswerB.setText("导入简答题答案");
        importAnswerB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JFileChooser jFileChooser = new JFileChooser();
                setDesktopIfWindows(jFileChooser);
                jFileChooser.showOpenDialog(Main.this);
                if(jFileChooser.getSelectedFile() != null){
                    CSVReader.read(jFileChooser.getSelectedFile());
                }
            }
        });
        jButton3.setText("开始");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(52, 52, 52)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(importAnswerB)
                                        .addComponent(jButton2)
                                                .addComponent(verifyLoginLabel)
                                        .addComponent(isAuthRadioButton)
                                        .addComponent(saveButton)
                                                .addComponent(jButton3)

                                        )

                                )
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(importAnswerB)
                                .addGap(18 ,18 ,18)
                                .addComponent(jButton2)
                                .addGap(18 ,18 ,18)

                                .addComponent(verifyLoginLabel)
                                .addComponent(isAuthRadioButton)
                                .addComponent(saveButton)
                                .addComponent(jButton3)

                                .addContainerGap(123, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>

    private void jButton3ActionPerformed(ActionEvent evt) {
        //检测如果开启授权 就需要显示保存验证码的地址
        if(isAuthRadioButton.isSelected()){
            if(saveButton.isVisible()){
                if(saveLocationDir != null){
                    CapchaSaveConfig.SAVE_CONFIG = saveLocationDir.getAbsolutePath() + "/";
                    startDo();
                }
            }else{
                saveButton.setVisible(true);
            }

        }else{
            CapchaSaveConfig.SAVE_CONFIG = "";
            startDo();
        }

    }



    private void startDo() {
        if(user != null && user.size()>0){
            for(ImportUser user : user){
                new APIController(  user.getUsername() , user.getPassword() , isAuthRadioButton.isSelected() , false,false);
            }
        }else{
            JOptionPane.showInputDialog("未导入学生账号，请导入后重新操作");
            return;
        }

    }


    public void setDesktopIfWindows(JFileChooser chooser){
        chooser.setCurrentDirectory(new File(System.getenv("HOME") + "/Desktop"));
        chooser.setDragEnabled(true);
    }
    private void jButton2ActionPerformed(ActionEvent evt) throws IOException {
        JFileChooser jFileChooser = new JFileChooser();
        setDesktopIfWindows(jFileChooser);
        jFileChooser.showOpenDialog(this);
        if(jFileChooser.getSelectedFile() != null){
            user = UserImportUtil.getImportUserList(jFileChooser.getSelectedFile().getAbsolutePath());
        }
    }
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
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Main().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;

    private JRadioButton isAuthRadioButton;
    private JButton importAnswerB;
    private JLabel verifyLoginLabel;

    private JButton saveButton;
    private File saveLocationDir = null;


    // End of variables declaration
}

