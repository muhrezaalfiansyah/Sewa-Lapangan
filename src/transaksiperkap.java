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


public class transaksiperkap extends javax.swing.JFrame {

         //membuat objek    
    private DefaultTableModel model;
    
    //deklarasi variabel
    String noPemesan,nmPemesan,nmTeam,nmProduk,kdProduk,xtotal;
    int hrg_sewa, jumlah;
    double total, bayar, kembali, sTotal;

    /**
     * Creates new form transaksiperkap
     */
    public transaksiperkap() {
        initComponents();
        this.setLocationRelativeTo(null);
        
        tampil_combo();
        tampil_comboo();
        
         //membuat obyek
        model = new DefaultTableModel();
        
        //memberi nama header pada tabel
        transaksiperkaptbl.setModel(model);
        model.addColumn("NO PEMESAN");
        model.addColumn("NAMA PEMESAN");
        model.addColumn("NAMA TEAM");
        model.addColumn("NAMA PRODUK");
        model.addColumn("KODE PRODUK");
        model.addColumn("HARGA SEWA");
        model.addColumn("JUMLAH");
        model.addColumn("SUBTOTAL");
        
        //fungsi ambil data
        getDataPerkap();
    }
           
    
    //fungsi membaca data kategori
    public void getDataPerkap(){
        //kosongkan tabel
        model.getDataVector().removeAllElements();
        model.fireTableDataChanged();
        
        //eksekusi koneksi dan kirimkan query ke database
        try{
            //tes koneksi
            Statement stat = (Statement) koneksidb.getKoneksi().createStatement();
            
            //perintah sql untuk membaca data dari tabel kategori        
            String sql = "SELECT * FROM tbltransaksiperkap";
            ResultSet res = stat.executeQuery(sql);
            
            //baca data
            while(res.next()){
                //membuat obyek berjenis array
                Object[] obj = new Object[8];
                obj[0]=res.getString("no_pemesan");
                obj[1]=res.getString("nama_pemesan");
                obj[2]=res.getString("nama_team");
                obj[3]=res.getString("nm_produk");
                obj[4]=res.getString("kd_produk");
                obj[5]=res.getString("hrg_sewa");
                obj[6]=res.getString("jumlah");
                obj[7]=res.getString("total");

                model.addRow(obj);
            }
        }catch(SQLException err){
           JOptionPane.showMessageDialog(null, err.getMessage());
        }
    }

    //fungsi untuk menampilkan data pada textbox
    public void dataPerkap(){   
        try{
            //tes koneksi
            Statement stat = (Statement) koneksidb.getKoneksi().createStatement();
           
            //perintah sql untuk membaca data dari tabel transaksilapangan
            String sql = "SELECT * FROM tbltransaksilapangan WHERE nm_pemesan = '"+ jComboBoxNmPemesan.getSelectedItem() +"'";
            ResultSet res = stat.executeQuery(sql);
                        
            //baca data dan tampilkan pada text produk dan harga
            while(res.next()){
                //membuat obyek berjenis array
               txt_team.setText(res.getString("nama_team"));
            }
        }catch(SQLException err){
           JOptionPane.showMessageDialog(null, err.getMessage());
        }
    }

        public void dataPerkapp(){   
        try{
            //tes koneksi
            Statement stat = (Statement) koneksidb.getKoneksi().createStatement();
           
            //perintah sql untuk membaca data dari tabel transaksilapangan
            String sql = "SELECT * FROM tblproduk WHERE nm_produk = '"+ jComboBoxPerkap.getSelectedItem() +"'";
            ResultSet res = stat.executeQuery(sql);
                        
            //baca data dan tampilkan pada text produk dan harga
            while(res.next()){
                //membuat obyek berjenis array
               txt_kodeperkap.setText(res.getString("kd_produk"));
               txt_hrgasewa.setText(res.getString("hrg_sewa"));
            }
        }catch(SQLException err){
           JOptionPane.showMessageDialog(null, err.getMessage());
        }
    }

    
    //fungsi untuk memasukan barang yang sudah dipilih pada tabel sementara
    public void masukTabel(){
        try{
            String noPemesan=txt_idpemesan.getText();
            String nmPemesan=(String)jComboBoxNmPemesan.getSelectedItem();
            String nmTeam=txt_team.getText();
            String nmProduk=(String)jComboBoxPerkap.getSelectedItem();
            String kdProduk=txt_kodeperkap.getText();
            double hrgsewa=Double.parseDouble(txt_hrgasewa.getText());
            int jumlah=Integer.parseInt(txt_jumlah.getText());
            sTotal = hrgsewa*jumlah;
            total = total + sTotal;
            xtotal=Double.toString(total);
            lblTotal.setText(xtotal);
            model.addRow(new Object[]{noPemesan,nmPemesan,nmTeam,nmProduk,kdProduk,hrgsewa,jumlah,sTotal});
        }
        catch(Exception e){
            System.out.println("Error : "+e);
        }
    }

        public void simpanDataTransaksi(){ 
        //proses perhitungan uang kembalian
        bayar = Double.parseDouble(txtByr.getText());
        kembali = bayar - total;
        String xkembali=Double.toString(kembali);
        lblKembali.setText(xkembali);
       
        //uji koneksi dan eksekusi perintah
        try{
            //test koneksi
            Statement stat = (Statement) koneksidb.getKoneksi().createStatement();
            
            //perintah sql untuk simpan data
            String  sql =   "INSERT INTO tbltransaksiperkap(no_pemesan,nama_pemesan, nama_team, nm_produk, kd_produk, hrg_sewa,jumlah,total,bayar,kembali)"
                            + "VALUES('"+ txt_idpemesan.getText() +"','"+ jComboBoxNmPemesan.getSelectedItem() +"','"+ txt_team.getText() +"','"+ jComboBoxPerkap.getSelectedItem() +"','"+ txt_kodeperkap.getText() +"','"+ txt_hrgasewa.getText() +"','"+ txt_jumlah.getText() +"','"+ total +"', '"+ txtByr.getText() +"', '"+ kembali +"')";
            PreparedStatement p = (PreparedStatement) koneksidb.getKoneksi().prepareStatement(sql);
            p.executeUpdate();
            
        }catch(SQLException err){
            JOptionPane.showMessageDialog(null, err.getMessage());
        }
    }
        
    //mengosongkan form
    public void reset(){
        noPemesan = "";
        nmPemesan = "";
        nmTeam = "";
        nmProduk = ""; 
        kdProduk = "";
        xtotal = "";
        hrg_sewa = 0; 
        jumlah = 0;
        total = 0; 
        bayar = 0;
        kembali = 0;
        sTotal = 0;
               
        txt_idpemesan.setText(noPemesan);
        txt_team.setText(nmTeam);
        txt_kodeperkap.setText(kdProduk);
        txt_hrgasewa.setText("");
        txt_jumlah.setText("");
        lblTotal.setText("");
        txtByr.setText("");
        lblKembali.setText("");
    }
        
        
   

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    
    public void tampil_combo()
    {
        try {
            Connection con = koneksidb.getKoneksi();
            Statement stt = con.createStatement();
            String sql = "select nama_pemesan from tbltransaksilapangan order by nama_pemesan";
            ResultSet res = stt.executeQuery(sql);
            
            while(res.next()){
                Object[] ob = new Object[2];
                ob[1] = res.getString("nama_pemesan");
                jComboBoxNmPemesan.addItem(res.getString(1));
            }
            res.close(); stt.close();
        } catch (Exception e) {
                System.out.println(e.getMessage());
        }
    }
    
    public void tampil()
    {
        try {
            Connection con = koneksidb.getKoneksi();
            Statement stt = con.createStatement();
            String sql = "select nama_team from tbltransaksilapangan where nama_pemesan= '"+jComboBoxNmPemesan.getSelectedItem()+"'";
            ResultSet res = stt.executeQuery(sql);
            
            while(res.next()){
                Object[] ob = new Object[2];
                ob[0] = res.getString("nama_team");
                
                txt_team.setText((String) ob[0]);
            }
        res.close(); stt.close();
        
    } catch (Exception e) {
            System.out.println(e.getMessage());
    }
    }

       public void tampil_comboo()
    {
        try {
            Connection con = koneksidb.getKoneksi();
            Statement stt = con.createStatement();
            String sql = "select nm_produk from tblproduk order by nm_produk";
            ResultSet res = stt.executeQuery(sql);
            
            while(res.next()){
                Object[] ob = new Object[2];
                ob[1] = res.getString("nm_produk");
                jComboBoxPerkap.addItem(res.getString(1));
            }
            res.close(); stt.close();
        } catch (Exception e) {
                System.out.println(e.getMessage());
        }
    }
    
    public void tampill()
    {
        try {
            Connection con = koneksidb.getKoneksi();
            Statement stt = con.createStatement();
            String sql = "select kd_produk,hrg_sewa from tblproduk where nm_produk= '"+jComboBoxPerkap.getSelectedItem()+"'";
            ResultSet res = stt.executeQuery(sql);
            
            while(res.next()){
                Object[] ob = new Object[2];
                ob[0] = res.getString("kd_produk");
                ob[1] = res.getString("hrg_sewa");

                
                txt_kodeperkap.setText((String) ob[0]);
                txt_hrgasewa.setText((String) ob[1]);
            }
        res.close(); stt.close();
        
    } catch (Exception e) {
            System.out.println(e.getMessage());
    }
    }

    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jComboBoxNmPemesan = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        txt_team = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jComboBoxPerkap = new javax.swing.JComboBox<>();
        txt_kodeperkap = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txt_hrgasewa = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txt_jumlah = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        transaksiperkaptbl = new javax.swing.JTable();
        jLabel8 = new javax.swing.JLabel();
        txt_idpemesan = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        txtByr = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        lblTotal = new javax.swing.JLabel();
        lblKembali = new javax.swing.JLabel();
        tblsewa = new javax.swing.JButton();
        tblreset = new javax.swing.JButton();
        tblsimpan = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Viner Hand ITC", 0, 14)); // NOI18N
        jLabel1.setText("TRANSAKSI");

        jLabel2.setFont(new java.awt.Font("Viner Hand ITC", 0, 14)); // NOI18N
        jLabel2.setText("BOOKING  PERLENGKAPAN");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 3));

        jLabel3.setText("Nama Pemesan :");

        jComboBoxNmPemesan.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-Pilih-" }));
        jComboBoxNmPemesan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxNmPemesanActionPerformed(evt);
            }
        });

        jLabel4.setText("Nama Team :");

        jLabel5.setText("Perlengkapan :");

        jComboBoxPerkap.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-Pilih-" }));
        jComboBoxPerkap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxPerkapActionPerformed(evt);
            }
        });

        txt_kodeperkap.setEnabled(false);

        jLabel6.setText("Harga Sewa :");

        jLabel7.setText("Jumlah :");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jComboBoxNmPemesan, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_team, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jComboBoxPerkap, 0, 100, Short.MAX_VALUE)
                                    .addComponent(txt_hrgasewa))
                                .addGap(18, 18, 18)
                                .addComponent(txt_kodeperkap, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(txt_jumlah, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(35, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jComboBoxNmPemesan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txt_team, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jComboBoxPerkap, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txt_kodeperkap, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(txt_hrgasewa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addComponent(txt_jumlah, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        transaksiperkaptbl.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(transaksiperkaptbl);

        jLabel8.setText("NO PEMESAN : ");

        jLabel13.setFont(new java.awt.Font("Broadway", 1, 24)); // NOI18N
        jLabel13.setText("TOTAL");

        jLabel14.setFont(new java.awt.Font("Broadway", 1, 24)); // NOI18N
        jLabel14.setText("BAYAR");

        txtByr.setFont(new java.awt.Font("Times New Roman", 3, 18)); // NOI18N

        jLabel16.setFont(new java.awt.Font("Broadway", 1, 24)); // NOI18N
        jLabel16.setText("KEMBALI");

        lblTotal.setFont(new java.awt.Font("Broadway", 1, 24)); // NOI18N
        lblTotal.setText("...");

        lblKembali.setFont(new java.awt.Font("Broadway", 1, 24)); // NOI18N
        lblKembali.setText("...");

        tblsewa.setText("SEWA");
        tblsewa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tblsewaActionPerformed(evt);
            }
        });

        tblreset.setText("RESET");
        tblreset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tblresetActionPerformed(evt);
            }
        });

        tblsimpan.setText("SIMPAN");
        tblsimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tblsimpanActionPerformed(evt);
            }
        });

        jButton1.setText("KELUAR");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(79, 79, 79)
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel2))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(50, 50, 50)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(tblsimpan)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(tblreset)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jButton1)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(tblsewa))
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(layout.createSequentialGroup()
                                            .addGap(19, 19, 19)
                                            .addComponent(jLabel8)
                                            .addGap(18, 18, 18)
                                            .addComponent(txt_idpemesan, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtByr, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel14))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel16, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(lblKembali, javax.swing.GroupLayout.Alignment.TRAILING)))
                            .addComponent(jLabel13)
                            .addComponent(lblTotal))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel8)
                    .addComponent(txt_idpemesan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tblsewa)
                    .addComponent(tblsimpan)
                    .addComponent(tblreset)
                    .addComponent(jButton1))
                .addGap(19, 19, 19)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel13)
                        .addGap(3, 3, 3)
                        .addComponent(lblTotal)
                        .addGap(38, 38, 38)
                        .addComponent(jLabel14)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtByr, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel16)
                        .addGap(8, 8, 8)
                        .addComponent(lblKembali)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tblsewaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tblsewaActionPerformed
        // TODO add your handling code here:
        masukTabel();
    }//GEN-LAST:event_tblsewaActionPerformed

    private void jComboBoxNmPemesanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxNmPemesanActionPerformed
        // TODO add your handling code here:
        tampil();
    }//GEN-LAST:event_jComboBoxNmPemesanActionPerformed

    private void jComboBoxPerkapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxPerkapActionPerformed
        // TODO add your handling code here:
        tampill();
    }//GEN-LAST:event_jComboBoxPerkapActionPerformed

    private void tblsimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tblsimpanActionPerformed
        // TODO add your handling code here:
        simpanDataTransaksi();
    }//GEN-LAST:event_tblsimpanActionPerformed

    private void tblresetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tblresetActionPerformed
        // TODO add your handling code here:
        reset();
    }//GEN-LAST:event_tblresetActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        this.dispose();
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
            java.util.logging.Logger.getLogger(transaksiperkap.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(transaksiperkap.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(transaksiperkap.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(transaksiperkap.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new transaksiperkap().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JComboBox<String> jComboBoxNmPemesan;
    private javax.swing.JComboBox<String> jComboBoxPerkap;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblKembali;
    private javax.swing.JLabel lblTotal;
    private javax.swing.JButton tblreset;
    private javax.swing.JButton tblsewa;
    private javax.swing.JButton tblsimpan;
    private javax.swing.JTable transaksiperkaptbl;
    private javax.swing.JTextField txtByr;
    private javax.swing.JTextField txt_hrgasewa;
    private javax.swing.JTextField txt_idpemesan;
    private javax.swing.JTextField txt_jumlah;
    private javax.swing.JTextField txt_kodeperkap;
    private javax.swing.JTextField txt_team;
    // End of variables declaration//GEN-END:variables
}
