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

public class kategori extends javax.swing.JFrame {

    //membuat objek    
    private DefaultTableModel model;
    
    //deklarasi variabel
    String kdKategori, nmKategori;
    int hrgSewa;

    /**
     * Creates new form kategori
     */
    public kategori() {
        initComponents();
        this.setLocationRelativeTo(null);
        
        //membuat obyek
        model = new DefaultTableModel();
        
        //memberi nama header pada tabel
        kategoritbl.setModel(model);
        model.addColumn("Kode Kategori Lapangan");
        model.addColumn("Nama Kategori Lapangan");
        model.addColumn("Harga Sewa Lapangan");
        
        //fungsi ambil data
        getDataKategori();
    }
    
        //fungsi membaca data kategori
    public void getDataKategori(){
        //kosongkan tabel
        model.getDataVector().removeAllElements();
        model.fireTableDataChanged();
        
        //eksekusi koneksi dan kirimkan query ke database
        try{
            //tes koneksi
            Statement stat = (Statement) koneksidb.getKoneksi().createStatement();
            
            //perintah sql untuk membaca data dari tabel kategori        
            String sql = "SELECT * FROM tblkategori";
            ResultSet res = stat.executeQuery(sql);
            
            //baca data
            while(res.next()){
                //membuat obyek berjenis array
                Object[] obj = new Object[3];
                obj[0]=res.getString("kd_kategori");
                obj[1]=res.getString("nm_kategori");
                obj[2]=res.getString("hrg_sewa");
                model.addRow(obj);
            }
        }catch(SQLException err){
           JOptionPane.showMessageDialog(null, err.getMessage());
        }
    }

    //membaca data 
    public void loadDataKategori(){
        kdKategori = txtKdKategori.getText();
        nmKategori = txtNmKategori.getText();
        hrgSewa = Integer.parseInt(txtHrgSewa.getText());
    }
    
    //memilih data saat baris pada tabel dipilih dengan mouse
    public void dataSelect(){
        //deklarasi variabel
        int i = kategoritbl.getSelectedRow();
        
        //uji adakah data di tabel?
        if(i == -1){
            //tidak ada yang terpilih atau dipilih.
            return;
        }
        txtKdKategori.setText(""+model.getValueAt(i,0));
        txtNmKategori.setText(""+model.getValueAt(i,1));
        txtHrgSewa.setText(""+model.getValueAt(i,2));
    }
     
    //mengosongkan form
    public void reset(){
        kdKategori = "";
        nmKategori = "";
        hrgSewa = 0;
               
        txtKdKategori.setText(kdKategori);
        txtNmKategori.setText(nmKategori);
        txtHrgSewa.setText("");
    }
    
    public void simpanDataKategori(){
        //panggil fungsi load data
        loadDataKategori();
        
        //uji koneksi dan eksekusi perintah
        try{
            //test koneksi
            Statement stat = (Statement) koneksidb.getKoneksi().createStatement();
            
            //perintah sql untuk simpan data
            String  sql =   "INSERT INTO tblkategori(kd_kategori, nm_kategori, hrg_sewa)" + "VALUES('"+ kdKategori +"','"+ nmKategori +"','"+ hrgSewa +"')";
            PreparedStatement p = (PreparedStatement) koneksidb.getKoneksi().prepareStatement(sql);
            p.executeUpdate();
            
            //ambil data
            getDataKategori();
        }catch(SQLException err){
            JOptionPane.showMessageDialog(null, err.getMessage());
        }
    }
    
    public void perbaruiDataKategori(){
        //fungsi load data kategori
        loadDataKategori();
        
        try{
            //uji koneksi
            Statement stat = (Statement) koneksidb.getKoneksi().createStatement();
            
            //kirim perintah sql
            String sql = "UPDATE tblkategori SET nm_kategori = '"+ nmKategori +"', "
                    + "hrg_sewa = '"+ hrgSewa +"'"
                    + "WHERE kd_kategori = '"+ kdKategori +"'";
            PreparedStatement p =(PreparedStatement)koneksidb.getKoneksi().prepareStatement(sql);
            p.executeUpdate();
            
            //ambil data
            getDataKategori();
            
            //kosongkan data
            reset();
            JOptionPane.showMessageDialog(null, "PERUBAHAN DATA BERHASIL");
        }catch(SQLException err){
            JOptionPane.showMessageDialog(null, err.getMessage());
        }
    }                 

    public void hapusDataKategori(){
        //panggil fungsi ambil data
        loadDataKategori(); 
        
        //Beri peringatan sebelum melakukan penghapusan data
        int pesan = JOptionPane.showConfirmDialog(null, "HAPUS DATA"+ kdKategori +"?","KONFIRMASI", JOptionPane.OK_CANCEL_OPTION);
        
        //jika pengguna memilih OK lanjutkan proses hapus data
        if(pesan == JOptionPane.OK_OPTION){
            //uji koneksi
            try{
                //buka koneksi ke database
                Statement stat = (Statement) koneksidb.getKoneksi().createStatement();
                
                //perintah hapus data
                String sql = "DELETE FROM tblkategori WHERE kd_kategori='"+ kdKategori +"'";
                PreparedStatement p =(PreparedStatement)koneksidb.getKoneksi().prepareStatement(sql);
                p.executeUpdate();
                
                //fungsi ambil data
                getDataKategori();
                
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

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtKdKategori = new javax.swing.JTextField();
        txtNmKategori = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtHrgSewa = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        kategoritbl = new javax.swing.JTable();
        tblkeluar = new javax.swing.JButton();
        tblsimpan = new javax.swing.JButton();
        tblreset = new javax.swing.JButton();
        tblrubah = new javax.swing.JButton();
        tblHapus = new javax.swing.JButton();

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(jTable1);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Viner Hand ITC", 0, 14)); // NOI18N
        jLabel1.setText("MASTER  DATA  KATEGORI  LAPANGAN");

        jLabel2.setText("Kode Kategori Lapangan");

        jLabel3.setText("Nama Kategori Lapangan");

        jLabel4.setText("Harga Sewa Lapangan");

        kategoritbl.setModel(new javax.swing.table.DefaultTableModel(
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
        kategoritbl.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                kategoritblMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(kategoritbl);

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

        tblreset.setText("RESET");
        tblreset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tblresetActionPerformed(evt);
            }
        });

        tblrubah.setText("RUBAH");
        tblrubah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tblrubahActionPerformed(evt);
            }
        });

        tblHapus.setText("HAPUS");
        tblHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tblHapusActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(63, 63, 63)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel1)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel4))
                                .addGap(24, 24, 24)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtKdKategori)
                                    .addComponent(txtNmKategori)
                                    .addComponent(txtHrgSewa)))))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 375, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(tblsimpan)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tblreset)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(tblrubah)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(tblHapus)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(tblkeluar)))
                .addContainerGap(15, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(34, 34, 34)
                        .addComponent(jLabel2))
                    .addComponent(txtKdKategori, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3)
                    .addComponent(txtNmKategori, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel4)
                    .addComponent(txtHrgSewa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tblsimpan)
                    .addComponent(tblreset)
                    .addComponent(tblrubah)
                    .addComponent(tblHapus)
                    .addComponent(tblkeluar))
                .addGap(19, 19, 19)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(27, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    private void tblkeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tblkeluarActionPerformed

        this.dispose();
        
    }//GEN-LAST:event_tblkeluarActionPerformed

    private void tblsimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tblsimpanActionPerformed
        //memanggil fungsi simpan data kategori
        simpanDataKategori();
    }//GEN-LAST:event_tblsimpanActionPerformed

    private void tblresetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tblresetActionPerformed
        //memanggil fungsi reset
        reset();
    }//GEN-LAST:event_tblresetActionPerformed

    private void tblrubahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tblrubahActionPerformed
        //memanggil fungsi perbarui data kategori
        perbaruiDataKategori();
    }//GEN-LAST:event_tblrubahActionPerformed

    private void tblHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tblHapusActionPerformed
        //memanggil fungsi hapus data kategori
        hapusDataKategori();
    }//GEN-LAST:event_tblHapusActionPerformed

    private void kategoritblMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_kategoritblMouseClicked
        // TODO add your handling code here:
        dataSelect();
    }//GEN-LAST:event_kategoritblMouseClicked

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
            java.util.logging.Logger.getLogger(kategori.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(kategori.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(kategori.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(kategori.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new kategori().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable kategoritbl;
    private javax.swing.JButton tblHapus;
    private javax.swing.JButton tblkeluar;
    private javax.swing.JButton tblreset;
    private javax.swing.JButton tblrubah;
    private javax.swing.JButton tblsimpan;
    private javax.swing.JTextField txtHrgSewa;
    private javax.swing.JTextField txtKdKategori;
    private javax.swing.JTextField txtNmKategori;
    // End of variables declaration//GEN-END:variables
}
