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

public class transaksilapangan extends javax.swing.JFrame {

         //membuat objek    
    private DefaultTableModel model;
    
    //deklarasi variabel
    String noBooking,tglBooking,wktBooking,nmPemesan,nmTeam, kdLapangan, nmLapangan, xtotal;
    int hrg_sewa, lama_booking;
    double total, bayar, kembali, sTotal;

    /**
     * Creates new form transaksilapangan
     */
    public transaksilapangan() {
        initComponents();
        this.setLocationRelativeTo(null);
        
        tampil_combo();
        
         //membuat obyek
        model = new DefaultTableModel();
        
        //memberi nama header pada tabel
        transaksilaptbl.setModel(model);
        model.addColumn("NO BOOKING");
        model.addColumn("TANGGAL BOOKING");
        model.addColumn("WAKTU BOOKING");
        model.addColumn("NAMA PEMESAN");
        model.addColumn("KODE LAPANGAN");
        model.addColumn("NAMA LAPANGAN");
        model.addColumn("HARGA SEWA LAPANGAN");
        model.addColumn("LAMA BOOKING");
        model.addColumn("SUBTOTAL");
        
        //fungsi ambil data
        getDataTransaksi();
    }
           
    
    //fungsi membaca data kategori
    public void getDataTransaksi(){
        //kosongkan tabel
        model.getDataVector().removeAllElements();
        model.fireTableDataChanged();
        
        //eksekusi koneksi dan kirimkan query ke database
        try{
            //tes koneksi
            Statement stat = (Statement) koneksidb.getKoneksi().createStatement();
            
            //perintah sql untuk membaca data dari tabel kategori        
            String sql = "SELECT * FROM tbltransaksilapangan";
            ResultSet res = stat.executeQuery(sql);
            
            //baca data
            while(res.next()){
                //membuat obyek berjenis array
                Object[] obj = new Object[9];
                obj[0]=res.getString("no_booking");
                obj[1]=res.getString("tanggal_booking");
                obj[2]=res.getString("waktu_booking");
                obj[3]=res.getString("nama_pemesan");
                obj[4]=res.getString("kd_kategori");
                obj[5]=res.getString("nm_kategori");
                obj[6]=res.getString("hrg_sewa");
                obj[7]=res.getString("lama_booking");
                obj[8]=res.getString("total");

                model.addRow(obj);
            }
        }catch(SQLException err){
           JOptionPane.showMessageDialog(null, err.getMessage());
        }
    }

    //fungsi untuk menampilkan data pada textbox
    public void dataProduk(){   
        try{
            //tes koneksi
            Statement stat = (Statement) koneksidb.getKoneksi().createStatement();
           
            //perintah sql untuk membaca data dari tabel produk
            String sql = "SELECT * FROM tblkategori WHERE kd_kategori = '"+ jComboBoxKode.getSelectedItem() +"'";
            ResultSet res = stat.executeQuery(sql);
                        
            //baca data dan tampilkan pada text produk dan harga
            while(res.next()){
                //membuat obyek berjenis array
               txtJnsLapangan.setText(res.getString("nm_kategori"));
               txtHrgSewa.setText(res.getString("hrg_sewa"));
            }
        }catch(SQLException err){
           JOptionPane.showMessageDialog(null, err.getMessage());
        }
    }

    //fungsi untuk memasukan barang yang sudah dipilih pada tabel sementara
    public void masukTabel(){
        try{
            String noBooking=txtNoBooking.getText();
            String tglBooking=txtTglBooking.getText();
            String wktBooking=(String)jComboBoxWaktu.getSelectedItem();
            String nmPemesan=txtNmPemesan.getText();
            String kdLapangan=(String)jComboBoxKode.getSelectedItem();
            String nmLapangan=txtJnsLapangan.getText();
            double hrgsewa=Double.parseDouble(txtHrgSewa.getText());
            int lama=Integer.parseInt(txtLmBooking.getText());
            sTotal = hrgsewa*lama;
            total = total + sTotal;
            xtotal=Double.toString(total);
            lblTotal.setText(xtotal);
            model.addRow(new Object[]{noBooking,tglBooking,wktBooking,nmPemesan,kdLapangan,nmLapangan,hrgsewa,lama,sTotal});
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
            String  sql =   "INSERT INTO tbltransaksilapangan(no_booking,kd_kategori, nm_kategori, hrg_sewa, nama_team, nama_pemesan,tanggal_booking,waktu_booking,lama_booking,total,bayar,kembali)"
                            + "VALUES('"+ txtNoBooking.getText() +"','"+ jComboBoxKode.getSelectedItem() +"','"+ txtJnsLapangan.getText() +"','"+ txtHrgSewa.getText() +"','"+ txtNmTeam.getText() +"','"+ txtNmPemesan.getText() +"','"+ txtTglBooking.getText() +"','"+ jComboBoxWaktu.getSelectedItem() +"','"+ txtLmBooking.getText() +"','"+ total +"', '"+ txtByr.getText() +"', '"+ kembali +"')";
            PreparedStatement p = (PreparedStatement) koneksidb.getKoneksi().prepareStatement(sql);
            p.executeUpdate();
            
        }catch(SQLException err){
            JOptionPane.showMessageDialog(null, err.getMessage());
        }
    }
        
    //mengosongkan form
    public void reset(){
        noBooking = "";
        tglBooking = "";
        wktBooking = "";
        nmPemesan = "";
        nmTeam = "";
        kdLapangan = ""; 
        nmLapangan = "";
        xtotal = "";
        hrg_sewa = 0; 
        lama_booking = 0;
        total = 0; 
        bayar = 0;
        kembali = 0;
        sTotal = 0;
               
        txtNoBooking.setText(noBooking);
        txtTglBooking.setText(tglBooking);
        txtNmPemesan.setText(nmPemesan);
        txtNmTeam.setText(nmTeam);
        txtJnsLapangan.setText(nmLapangan);
        txtHrgSewa.setText("");
        txtLmBooking.setText("");
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
            String sql = "select kd_kategori from tblkategori order by kd_kategori asc";
            ResultSet res = stt.executeQuery(sql);
            
            while(res.next()){
                Object[] ob = new Object[2];
                ob[1] = res.getString("kd_kategori");
                
                jComboBoxKode.addItem(res.getString(1));
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
            String sql = "select nm_kategori,hrg_sewa from tblkategori where kd_kategori= '"+jComboBoxKode.getSelectedItem()+"'";
            ResultSet res = stt.executeQuery(sql);
            
            while(res.next()){
                Object[] ob = new Object[2];
                ob[0] = res.getString("nm_kategori");
                ob[1] = res.getString("hrg_sewa");
                
                txtJnsLapangan.setText((String) ob[0]);
                txtHrgSewa.setText((String) ob[1]);
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
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtNoBooking = new javax.swing.JTextField();
        txtTglBooking = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtNmPemesan = new javax.swing.JTextField();
        txtNmTeam = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        txtJnsLapangan = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        txtHrgSewa = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtLmBooking = new javax.swing.JTextField();
        jComboBoxKode = new javax.swing.JComboBox<>();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        txtByr = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        transaksilaptbl = new javax.swing.JTable();
        lblTotal = new javax.swing.JLabel();
        lblKembali = new javax.swing.JLabel();
        tblsewa = new javax.swing.JButton();
        tblsimpan = new javax.swing.JButton();
        tblreset = new javax.swing.JButton();
        jComboBoxWaktu = new javax.swing.JComboBox<>();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Viner Hand ITC", 0, 14)); // NOI18N
        jLabel1.setText("TRANSAKSI");

        jLabel2.setFont(new java.awt.Font("Viner Hand ITC", 0, 14)); // NOI18N
        jLabel2.setText("BOOKING  LAPANGAN");

        jLabel3.setText("NO.BOOKING ");

        jLabel4.setText("TANGGAL BOOKING");

        jLabel5.setText("WAKTU BOOKING");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED, null, new java.awt.Color(0, 0, 0)));

        jLabel8.setText("NAMA PEMESAN  =");

        jLabel9.setText("NAMA TEAM  =");

        jLabel10.setText("KODE LAPANGAN =");

        jLabel11.setText("JENIS LAPANGAN =");

        jLabel12.setText("HARGA SEWA      =");

        jLabel6.setText("Jam");

        jLabel7.setText("LAMA BOOKING");

        jComboBoxKode.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-Pilih-" }));
        jComboBoxKode.setToolTipText("");
        jComboBoxKode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxKodeActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtNmPemesan, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtNmTeam))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtJnsLapangan))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel10)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jComboBoxKode, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(148, 148, 148))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel12)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtHrgSewa, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtLmBooking, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel6)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txtNmPemesan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9)
                    .addComponent(txtNmTeam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(jComboBoxKode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(txtJnsLapangan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel7)
                        .addComponent(txtLmBooking, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel6))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel12)
                        .addComponent(txtHrgSewa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel13.setFont(new java.awt.Font("Broadway", 1, 24)); // NOI18N
        jLabel13.setText("TOTAL");

        jLabel14.setFont(new java.awt.Font("Broadway", 1, 24)); // NOI18N
        jLabel14.setText("BAYAR");

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel15.setText("(Wajib Bayar DP 50%)");

        txtByr.setFont(new java.awt.Font("Times New Roman", 3, 18)); // NOI18N

        jLabel16.setFont(new java.awt.Font("Broadway", 1, 24)); // NOI18N
        jLabel16.setText("KEMBALI");

        transaksilaptbl.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(transaksilaptbl);

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

        jComboBoxWaktu.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-Mulai-", "10.00", "11.00", "12.00", "13.00", "14.00", "15.00", "16.00", "17.00", "18.00", "19.00", "20.00", "21.00" }));

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
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtByr, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel15)
                                    .addComponent(jLabel14))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel16, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(lblKembali, javax.swing.GroupLayout.Alignment.TRAILING)))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel4)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(txtTglBooking, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel3)
                                                .addGap(37, 37, 37)
                                                .addComponent(txtNoBooking, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel5)
                                                .addGap(18, 18, 18)
                                                .addComponent(jComboBoxWaktu, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(tblsimpan)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(tblreset)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(jButton1)))
                                        .addGap(26, 26, 26)
                                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jLabel13)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(228, 228, 228)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addComponent(jLabel1)
                                                .addGap(30, 30, 30))
                                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING)))
                                    .addComponent(lblTotal))
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(tblsewa)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addGap(38, 38, 38)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(txtNoBooking, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(txtTglBooking, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(jComboBoxWaktu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(tblsimpan)
                            .addComponent(tblreset)
                            .addComponent(jButton1)))
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tblsewa)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel13)
                        .addGap(3, 3, 3)
                        .addComponent(lblTotal)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel14)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel15)
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

    private void tblsimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tblsimpanActionPerformed
        // TODO add your handling code here:
        simpanDataTransaksi();
    }//GEN-LAST:event_tblsimpanActionPerformed

    private void tblresetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tblresetActionPerformed
        // TODO add your handling code here:
        reset();
    }//GEN-LAST:event_tblresetActionPerformed

    private void jComboBoxKodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxKodeActionPerformed
        // TODO add your handling code here:
        tampil();
    }//GEN-LAST:event_jComboBoxKodeActionPerformed

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
            java.util.logging.Logger.getLogger(transaksilapangan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(transaksilapangan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(transaksilapangan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(transaksilapangan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new transaksilapangan().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JComboBox<String> jComboBoxKode;
    private javax.swing.JComboBox<String> jComboBoxWaktu;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblKembali;
    private javax.swing.JLabel lblTotal;
    private javax.swing.JButton tblreset;
    private javax.swing.JButton tblsewa;
    private javax.swing.JButton tblsimpan;
    private javax.swing.JTable transaksilaptbl;
    private javax.swing.JTextField txtByr;
    private javax.swing.JTextField txtHrgSewa;
    private javax.swing.JTextField txtJnsLapangan;
    private javax.swing.JTextField txtLmBooking;
    private javax.swing.JTextField txtNmPemesan;
    private javax.swing.JTextField txtNmTeam;
    private javax.swing.JTextField txtNoBooking;
    private javax.swing.JTextField txtTglBooking;
    // End of variables declaration//GEN-END:variables
}
