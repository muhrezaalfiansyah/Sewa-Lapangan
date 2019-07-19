/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Reza
 */

import Koneksi.koneksidb;
import java.sql.*;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class produk extends javax.swing.JFrame {

    //membuat objek    
    private DefaultTableModel model;
    
    //deklarasi variabel
    String kdProduk, nmProduk;
    int hrgSewa,stok;

    /**
     * Creates new form produk
     */
    public produk() {
        initComponents();
        this.setLocationRelativeTo(null);
        
        //membuat obyek
        model = new DefaultTableModel();
        
        //memberi nama header pada tabel
        produktbl.setModel(model);
        model.addColumn("Kode Produk");
        model.addColumn("Nama Produk");
        model.addColumn("Harga Sewa Produk");
        model.addColumn("Stok");
        
        //fungsi ambil data
        getDataProduk();
    }

        //fungsi membaca data kategori
    public void getDataProduk(){
        //kosongkan tabel
        model.getDataVector().removeAllElements();
        model.fireTableDataChanged();
        
        //eksekusi koneksi dan kirimkan query ke database
        try{
            //tes koneksi
            Statement stat = (Statement) koneksidb.getKoneksi().createStatement();
            
            //perintah sql untuk membaca data dari tabel kategori        
            String sql = "SELECT * FROM tblproduk";
            ResultSet res = stat.executeQuery(sql);
            
            //baca data
            while(res.next()){
                //membuat obyek berjenis array
                Object[] obj = new Object[4];
                obj[0]=res.getString("kd_produk");
                obj[1]=res.getString("nm_produk");
                obj[2]=res.getString("hrg_sewa");
                obj[3]=res.getString("stok");
                model.addRow(obj);
            }
        }catch(SQLException err){
           JOptionPane.showMessageDialog(null, err.getMessage());
        }
    }

    //membaca data 
    public void loadDataProduk(){
        kdProduk = txtKdProduk.getText();
        nmProduk = txtNmProduk.getText();
        hrgSewa = Integer.parseInt(txtHrgProduk.getText());
        stok = Integer.parseInt(txtStok.getText());
    }
    
    //memilih data saat baris pada tabel dipilih dengan mouse
    public void dataSelect(){
        //deklarasi variabel
        int i = produktbl.getSelectedRow();
        
        //uji adakah data di tabel?
        if(i == -1){
            //tidak ada yang terpilih atau dipilih.
            return;
        }
        txtKdProduk.setText(""+model.getValueAt(i,0));
        txtNmProduk.setText(""+model.getValueAt(i,1));
        txtHrgProduk.setText(""+model.getValueAt(i,2));
        txtStok.setText(""+model.getValueAt(i,3));
    }
     
    //mengosongkan form
    public void reset(){
        kdProduk = "";
        nmProduk = "";
        hrgSewa = 0;
        stok = 0;
               
        txtKdProduk.setText(kdProduk);
        txtNmProduk.setText(nmProduk);
        txtHrgProduk.setText("");
        txtStok.setText("");
    }
    
    public void simpanDataProduk(){
        //panggil fungsi load data
        loadDataProduk();
        
        //uji koneksi dan eksekusi perintah
        try{
            //test koneksi
            Statement stat = (Statement) koneksidb.getKoneksi().createStatement();
            
            //perintah sql untuk simpan data
            String  sql =   "INSERT INTO tblproduk(kd_produk, nm_produk, hrg_sewa, stok)" + "VALUES('"+ kdProduk +"','"+ nmProduk +"','"+ hrgSewa +"','"+ stok +"')";
            PreparedStatement p = (PreparedStatement) koneksidb.getKoneksi().prepareStatement(sql);
            p.executeUpdate();
            
            //ambil data
            getDataProduk();
        }catch(SQLException err){
            JOptionPane.showMessageDialog(null, err.getMessage());
        }
    }
    
    public void perbaruiDataProduk(){
        //fungsi load data kategori
        loadDataProduk();
        
        try{
            //uji koneksi
            Statement stat = (Statement) koneksidb.getKoneksi().createStatement();
            
            //kirim perintah sql
            String sql = "UPDATE tblproduk SET nm_produk = '"+ nmProduk +"', "
                    + "hrg_sewa = '"+ hrgSewa +"'"
                    + "stok = '"+ stok +"'"
                    + "WHERE kd_produk = '"+ kdProduk +"'";
            PreparedStatement p =(PreparedStatement)koneksidb.getKoneksi().prepareStatement(sql);
            p.executeUpdate();
            
            //ambil data
            getDataProduk();
            
            //kosongkan data
            reset();
            JOptionPane.showMessageDialog(null, "PERUBAHAN DATA BERHASIL");
        }catch(SQLException err){
            JOptionPane.showMessageDialog(null, err.getMessage());
        }
    }                 

    public void hapusDataProduk(){
        //panggil fungsi ambil data
        loadDataProduk(); 
        
        //Beri peringatan sebelum melakukan penghapusan data
        int pesan = JOptionPane.showConfirmDialog(null, "HAPUS DATA"+ kdProduk +"?","KONFIRMASI", JOptionPane.OK_CANCEL_OPTION);
        
        //jika pengguna memilih OK lanjutkan proses hapus data
        if(pesan == JOptionPane.OK_OPTION){
            //uji koneksi
            try{
                //buka koneksi ke database
                Statement stat = (Statement) koneksidb.getKoneksi().createStatement();
                
                //perintah hapus data
                String sql = "DELETE FROM tblproduk WHERE kd_produk='"+ kdProduk +"'";
                PreparedStatement p =(PreparedStatement)koneksidb.getKoneksi().prepareStatement(sql);
                p.executeUpdate();
                
                //fungsi ambil data
                getDataProduk();
                
                //fungsi reset data
                reset();
                JOptionPane.showMessageDialog(null, "BERHASIL DIHAPUS");
            }catch(SQLException err){
                JOptionPane.showMessageDialog(null, err.getMessage());
            }
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

        jLabel4 = new javax.swing.JLabel();
        txtHrgProduk = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        produktbl = new javax.swing.JTable();
        tblkeluar = new javax.swing.JButton();
        tblsimpan = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        tblreset = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        tblrubah = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        tblHapus = new javax.swing.JButton();
        txtKdProduk = new javax.swing.JTextField();
        txtNmProduk = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtStok = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel4.setText("Harga Sewa Produk Perlengkapan ");

        produktbl.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        produktbl.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                produktblMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(produktbl);

        tblkeluar.setText("KELUAR");
        tblkeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tblkeluarActionPerformed(evt);
            }
        });

        tblsimpan.setText("SIMPAN");
        tblsimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tblsimpanActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Viner Hand ITC", 0, 14)); // NOI18N
        jLabel1.setText("MASTER  DATA  PRODUK  PERLENGKAPAN");

        tblreset.setText("RESET");
        tblreset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tblresetActionPerformed(evt);
            }
        });

        jLabel2.setText("Kode Produk Perlengkapan");

        tblrubah.setText("RUBAH");
        tblrubah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tblrubahActionPerformed(evt);
            }
        });

        jLabel3.setText("Nama Produk Perlengkapan");

        tblHapus.setText("HAPUS");
        tblHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tblHapusActionPerformed(evt);
            }
        });

        jLabel5.setText("Stok Produk Perlengkapan");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(50, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(44, 44, 44))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(tblsimpan)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tblreset)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(tblrubah)
                        .addGap(18, 18, 18)
                        .addComponent(tblHapus)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tblkeluar)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addContainerGap())))
            .addGroup(layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel2)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(txtKdProduk)
                        .addComponent(txtNmProduk)
                        .addComponent(txtHrgProduk, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txtStok, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtKdProduk, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNmProduk, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtHrgProduk, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtStok, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 36, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tblsimpan)
                    .addComponent(tblreset)
                    .addComponent(tblrubah)
                    .addComponent(tblHapus)
                    .addComponent(tblkeluar))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void produktblMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_produktblMouseClicked
        // TODO add your handling code here:
        dataSelect();
    }//GEN-LAST:event_produktblMouseClicked

    private void tblkeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tblkeluarActionPerformed

        this.dispose();

    }//GEN-LAST:event_tblkeluarActionPerformed

    private void tblsimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tblsimpanActionPerformed
        //memanggil fungsi simpan data kategori
        simpanDataProduk();
    }//GEN-LAST:event_tblsimpanActionPerformed

    private void tblresetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tblresetActionPerformed
        //memanggil fungsi reset
        reset();
    }//GEN-LAST:event_tblresetActionPerformed

    private void tblrubahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tblrubahActionPerformed
        //memanggil fungsi perbarui data kategori
        perbaruiDataProduk();
    }//GEN-LAST:event_tblrubahActionPerformed

    private void tblHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tblHapusActionPerformed
        //memanggil fungsi hapus data kategori
        hapusDataProduk();
    }//GEN-LAST:event_tblHapusActionPerformed

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
            java.util.logging.Logger.getLogger(produk.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(produk.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(produk.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(produk.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new produk().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable produktbl;
    private javax.swing.JButton tblHapus;
    private javax.swing.JButton tblkeluar;
    private javax.swing.JButton tblreset;
    private javax.swing.JButton tblrubah;
    private javax.swing.JButton tblsimpan;
    private javax.swing.JTextField txtHrgProduk;
    private javax.swing.JTextField txtKdProduk;
    private javax.swing.JTextField txtNmProduk;
    private javax.swing.JTextField txtStok;
    // End of variables declaration//GEN-END:variables
}
