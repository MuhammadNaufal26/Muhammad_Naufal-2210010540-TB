/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package wfo;

import java.awt.CardLayout;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.table.DefaultTableModel;
import javax.swing.JOptionPane; // Untuk notifikasi sederhana
import javax.swing.RowFilter;
import javax.swing.table.TableRowSorter;
import java.text.SimpleDateFormat;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.properties.TextAlignment;
import java.io.File;
import java.io.FileOutputStream;
import javax.swing.JFileChooser;

import java.awt.Desktop;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.io.font.constants.StandardFonts;
import java.io.IOException;


/**
 *
 * @author Sudirwo
 */
public class Beranda extends javax.swing.JFrame {
   // urutan generate id
    private int urutTransaksi = 1;
    private int urutPelanggan = 1;
    // Baris sakti agar Beranda yang "berisi alarm" bisa dipanggil balik
    public static Beranda instance;
    // Musik Alarm
    private javax.sound.sampled.Clip clipAlarm;
    /**
     * Creates new form Beranda
     */
    public Beranda() {
        initComponents();
        instance = this; // Simpan alamat frame ini
        // Sesuaikan "/assets/nama_file.jpg" dengan nama file aslimu di package assets
        try {
            java.net.URL imgURL = getClass().getResource("/assets/wallpaper.jpeg");
            if (imgURL != null) {
                javax.swing.ImageIcon iconAsli = new javax.swing.ImageIcon(imgURL);
                java.awt.Image imgSkala = iconAsli.getImage().getScaledInstance(1000, 700, java.awt.Image.SCALE_SMOOTH);
                lblBackground.setIcon(new javax.swing.ImageIcon(imgSkala));
            }
        } catch (Exception e) {
            System.out.println("Gagal memuat gambar: " + e.getMessage());
        }

        // Kode transparansi panel yang tadi (pastikan nama variabel sesuai)
        // Format: (Red, Green, Blue, Alpha) 
        // Alpha 0-255 (makin kecil makin transparan)   
        panelKiri.setBackground(new java.awt.Color(33, 33, 33, 200));        // Hitam transparan

        // Menambahkan waktu di panel Atas
        // 1. Format untuk Jam (Contoh: 20:45:01)
        java.time.format.DateTimeFormatter formatJam = java.time.format.DateTimeFormatter.ofPattern("HH:mm:ss");

        // 2. Setup Timer untuk update lblTime setiap 1 detik (1000 ms)
        javax.swing.Timer timerJam = new javax.swing.Timer(1000, e -> {
            java.time.LocalTime sekarang = java.time.LocalTime.now();
            lblTime.setText(sekarang.format(formatJam));
        });

        // 3. Jalankan Timernya
        timerJam.start();
        
        // 4. Date
        java.time.LocalDate tgl = java.time.LocalDate.now();
        java.time.format.DateTimeFormatter format = java.time.format.DateTimeFormatter.ofPattern("dd MMM yyyy");
        lblTanggal.setText(tgl.format(format));
        
        
        // Menghilangkan background putih pada JTextArea
        jTextArea1.setBackground(new java.awt.Color(0, 0, 0, 0)); 

        // Menghilangkan background dan border pada JScrollPane (wadahnya)
        paneText.setOpaque(false);
        paneText.getViewport().setOpaque(false);
        paneText.setBorder(null);
        paneText.setViewportBorder(null);
        
        // Atur skala/size
        aturSkala(instagramlogo, "/assets/instagram.png", 20, 20);
        aturSkala(anime, "/assets/kiriya aoi.png", 395, 533);
        
        // Menambah data dummy ke tabel
        DefaultTableModel model = (DefaultTableModel) tabelPC.getModel();
        DefaultTableModel modelTr = (DefaultTableModel) tabelTr.getModel();
        
        // BARIS SAKTI: Menghapus semua baris bawaan NetBeans yang kosong
        model.setRowCount(0); 
        modelTr.setRowCount(0);

        // Baru kemudian isi data dummy
        model.addRow(new Object[]{"PC26001", "Asus", "Intel i5", "Rp. 10.000"});
        model.addRow(new Object[]{"PC26002", "Acer", "Intel i3", "Rp. 8.000"});
        
        //tabel pelanggan
        DefaultTableModel modelP = (DefaultTableModel) tabelPL.getModel();
        modelP.setRowCount(0); // Membersihkan baris kosong bawaan
        modelP.addRow(new Object[]{"PL26010001", "Adinata", "Laki-laki", "081234567890", "Jl. Jalan Didu Tamol, Banjarmasin", "adiadita@gmail.com"});
                urutPelanggan = 2; 
                generateID("PL", "PELANGGAN");
                
        //tabel transaksi
        modelTr.addRow(new Object[]{"TR26010001", "19-Jan-2026", "Adinata", "Asus", "Rp. 10.000", "10:00:00", "12:00:00", "02:00:00", "Rp. 20.000", "081234567890"}); 

        // Set Tanggal Hari Ini ke JDateChooser
        jdTrTanggal.setDate(new java.util.Date()); 
        
        // Transaksi
        urutTransaksi = 2;
        generateID("TR", "TRANSAKSI");
        
        
    }
    
    //*-----------------ini sdh luar constructor--------------------------------------------------------------
    
    private void pindahMenu(String namaKartu) {
        java.awt.CardLayout card = (java.awt.CardLayout) panelUtama.getLayout();
        card.show(panelUtama, namaKartu);
        panelUtama.revalidate();
        panelUtama.repaint();
    }
    
    //atur size gambar jLabel
    private void aturSkala(javax.swing.JLabel label, String path, int w, int h) {
        ImageIcon icon = new ImageIcon(getClass().getResource(path));
        Image img = icon.getImage().getScaledInstance(w, h, Image.SCALE_SMOOTH);
        label.setIcon(new ImageIcon(img));
    }
    
    // Fungsi ini akan dipanggil oleh Laman Pelanggan
    public void setDataPelanggan(String nama, String telp) {
        txtTrNama.setText(nama);
        txtTrTelpon.setText(telp);
    }

    // Fungsi ini akan dipanggil oleh Laman PC
    public void setDataPC(String merek, String tarif) {
        txtTrMerek.setText(merek);
        txtTrTarif.setText(tarif);
    }
   
    public void generateID(String prefix, String kategori) {
        // Ambil Tahun dan Bulan (Misal: 2601)
        String tahunBulan = new java.text.SimpleDateFormat("yyMM").format(new java.util.Date());

        String formatUrut = "";

        if (kategori.equalsIgnoreCase("TRANSAKSI")) {
            formatUrut = String.format("%04d", urutTransaksi);
            txtTrID.setText(prefix + tahunBulan + formatUrut);
        } else if (kategori.equalsIgnoreCase("PELANGGAN")) {
            formatUrut = String.format("%04d", urutPelanggan);
            txtPlID.setText(prefix + tahunBulan + formatUrut); // Sesuaikan nama textField-mu
        }
    }
    
    private String formatDuaDigit(String teks) {
            if (teks.length() == 1) {
                return "0" + teks; // "9" jadi "09"
            } else if (teks.length() == 0) {
                return "00"; // Kosong jadi "00"
            }
            return teks; // Sudah 2 digit (misal "12") tetap "12"
        }
    
    public void buatSlotAlarmOtomatis(String id, String nama, String pc, String jamSelesai) {
        // 1. Desain Kotak Slot
        JPanel slot = new JPanel();
        int lebarSlot = 345;
        int tinggiSlot = 220;
        slot.setPreferredSize(new java.awt.Dimension(lebarSlot, tinggiSlot));
        slot.setBackground(new java.awt.Color(25, 25, 25));
        slot.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(100, 100, 100), 2));
        slot.setLayout(null); 

        slot.setFocusable(false); // ANTI-GLITCH

        // 2. Label & Data
        JLabel lblID = new JLabel("ID Transaksi            :  " + id);
        lblID.setForeground(java.awt.Color.WHITE); lblID.setBounds(15, 15, 250, 20);
        JLabel lblNama = new JLabel("Nama Pelanggan   :  " + nama);
        lblNama.setForeground(java.awt.Color.WHITE); lblNama.setBounds(15, 45, 250, 20);
        JLabel lblPC = new JLabel("Merek PC                  :  " + pc);
        lblPC.setForeground(java.awt.Color.WHITE); lblPC.setBounds(15, 75, 250, 20);
        JLabel lblJam = new JLabel("Jam Selesai            : " + jamSelesai);
        lblJam.setForeground(java.awt.Color.WHITE); lblJam.setBounds(15, 105, 200, 20);

        JLabel lblSisa = new JLabel("sisa 00 jam 00 menit 00 detik", javax.swing.SwingConstants.CENTER);
        lblSisa.setForeground(new java.awt.Color(255, 204, 0));
        lblSisa.setFont(new java.awt.Font("Segoe UI", 1, 12));
        lblSisa.setBounds(0, 140, lebarSlot, 25); 

        JButton btnDelete = new JButton("Hapus");
        btnDelete.setBounds((lebarSlot - 150) / 2, 175, 150, 30);
        btnDelete.setBackground(new java.awt.Color(140, 0, 155));
        btnDelete.setForeground(java.awt.Color.WHITE);

        // 3. TIMER PER SLOT
        javax.swing.Timer timerAlarm = new javax.swing.Timer(1000, e -> {
            try {
                java.time.LocalTime sekarang = java.time.LocalTime.now();
                java.time.LocalTime akhir = java.time.LocalTime.parse(jamSelesai);
                java.time.Duration durasi = java.time.Duration.between(sekarang, akhir);
                long detik = durasi.getSeconds();

                if (detik > 0) {
                    long h = detik / 3600; 
                    long m = (detik % 3600) / 60; 
                    long s = detik % 60;
                    lblSisa.setText(String.format("sisa %02d jam %02d menit %02d detik", h, m, s));
                } else {
                    // --- AKSI SAAT WAKTU HABIS ---
                    ((javax.swing.Timer)e.getSource()).stop();
                    lblSisa.setText("WAKTU HABIS!");
                    lblSisa.setForeground(java.awt.Color.RED);

                    // 1. Matikan & Reset Suara (Agar jika ada alarm lain yang sedang bunyi langsung digantikan yang baru)
                    if (clipAlarm != null) {
                        clipAlarm.stop();
                        clipAlarm.close(); 
                    }

                    // 2. Jalankan Bunyi Terbaru
                    putarSuara();

                    // 3. Munculkan Pop-up
                    // Baris ini akan MEM-BLOCK (menahan) baris selanjutnya sampai user klik OK
                    javax.swing.JOptionPane.showMessageDialog(null, 
                        "Waktu Selesai: " + nama + " (PC: " + pc + ")", 
                        "Pemberitahuan Alarm", 
                        javax.swing.JOptionPane.WARNING_MESSAGE);

                    // 4. MATIKAN SUARA (Hanya akan jalan tepat setelah user klik OK)
                    if (clipAlarm != null && clipAlarm.isRunning()) {
                        clipAlarm.stop();
                        clipAlarm.close(); // Tutup agar resource audio dilepaskan dari RAM
                    }
                }
            } catch (Exception ex) { 
                // Abaikan parse error jika jamSelesai tidak valid
            }
        });
        timerAlarm.start();

        // 4. LOGIKA DELETE
        btnDelete.addActionListener(e -> {
            timerAlarm.stop();
            panelWadahSlot.remove(slot);
            refreshScroll(); // Update Counter jumlah slot dan tinggi scroll
        });

        // 5. Rakit
        slot.add(lblID); slot.add(lblNama); slot.add(lblPC); 
        slot.add(lblJam); slot.add(lblSisa); slot.add(btnDelete);
        panelWadahSlot.add(slot);

        // 6. Jalankan Formula Scroll & Counter
        refreshScroll();

        // Scroll otomatis ke bawah agar slot terbaru terlihat
        javax.swing.SwingUtilities.invokeLater(() -> {
            jScrollPaneAlarm.getVerticalScrollBar().setValue(jScrollPaneAlarm.getVerticalScrollBar().getMaximum());
        });
    }
    
    private void putarSuara() {
        try {
            // JIKA ADA SUARA LAGI BUNYI, MATIKAN DULU (Agar tidak double)
            if (clipAlarm != null && clipAlarm.isRunning()) {
                clipAlarm.stop();
                clipAlarm.close();
            }

            java.net.URL url = this.getClass().getResource("/assets/alarmmusic.wav");
            if (url == null) return;

            javax.sound.sampled.AudioInputStream stream = javax.sound.sampled.AudioSystem.getAudioInputStream(url);
            clipAlarm = javax.sound.sampled.AudioSystem.getClip();
            clipAlarm.open(stream);
            clipAlarm.start();

        } catch (Exception e) {
            System.err.println("Gagal putar suara: " + e.getMessage());
        }
    }
    private void refreshScroll() {
        int jumlahSlot = panelWadahSlot.getComponentCount();
        int tinggiBingkai = jScrollPaneAlarm.getHeight();
        if (tinggiBingkai <= 0) tinggiBingkai = 400; // Ukuran default jika belum tampil

        int lebarTetap = 740; // Sesuaikan dengan lebar JScrollPane kamu
        
        // Tampilkan ke label jumlah
        lblHtnSlt.setText("Jumlah : " + jumlahSlot);

        if (jumlahSlot <= 0) {
            // Jika kosong, set ukuran pas dengan bingkai agar scrollbar hilang
            panelWadahSlot.setPreferredSize(new java.awt.Dimension(lebarTetap, tinggiBingkai - 10));
        } else {
            // MATEMATIKA MANUAL
            int tinggiSatuSlot = 220; // Tinggi slot baru kamu
            int gapVertikal = 15;
            int jumlahBaris = (int) Math.ceil(jumlahSlot / 2.0); // 2 Kolom

            int totalTinggi = (jumlahBaris * tinggiSatuSlot) + ((jumlahBaris + 1) * gapVertikal);

            // Jika tinggi konten masih muat di bingkai, jangan munculkan scrollbar
            if (totalTinggi < tinggiBingkai) {
                panelWadahSlot.setPreferredSize(new java.awt.Dimension(lebarTetap, tinggiBingkai - 10));
            } else {
                panelWadahSlot.setPreferredSize(new java.awt.Dimension(lebarTetap, totalTinggi + 20));
            }
        }

        // Refresh UI secara paksa
        panelWadahSlot.revalidate();
        panelWadahSlot.repaint();

        // Update Viewport agar JScrollPane sadar ada perubahan ukuran kertas
        jScrollPaneAlarm.setViewportView(panelWadahSlot);
        jScrollPaneAlarm.revalidate();
         
    }
    
    private String toRupiah(int angka) {
        java.text.DecimalFormat kursIndonesia = (java.text.DecimalFormat) java.text.DecimalFormat.getCurrencyInstance();
        java.text.DecimalFormatSymbols formatRp = new java.text.DecimalFormatSymbols();

        formatRp.setCurrencySymbol("Rp. ");
        formatRp.setMonetaryDecimalSeparator(',');
        formatRp.setGroupingSeparator('.');

        kursIndonesia.setDecimalFormatSymbols(formatRp);

        // --- TAMBAHKAN BARIS INI ---
        kursIndonesia.setMaximumFractionDigits(0); // Menghilangkan ,00 di belakang

        return kursIndonesia.format(angka);
    }
    
    public void eksporKeExcel(javax.swing.JTable table, String namaFileDefault) {
        // 1. Siapkan JFileChooser (Jendela Pemilih File)
        javax.swing.JFileChooser fileChooser = new javax.swing.JFileChooser();
        fileChooser.setDialogTitle("Pilih Lokasi Simpan Laporan");

        // Set nama file default agar admin tidak capek ngetik
        fileChooser.setSelectedFile(new java.io.File(namaFileDefault + ".csv"));

        // 2. Munculkan Popup Save Dialog
        int userSelection = fileChooser.showSaveDialog(null);

        if (userSelection == javax.swing.JFileChooser.APPROVE_OPTION) {
            java.io.File fileSimpan = fileChooser.getSelectedFile();

            // Pastikan file berakhir dengan .csv
            String filePath = fileSimpan.getAbsolutePath();
            if (!filePath.toLowerCase().endsWith(".csv")) {
                fileSimpan = new java.io.File(filePath + ".csv");
            }

            try {
                java.io.FileWriter fw = new java.io.FileWriter(fileSimpan);
                java.io.BufferedWriter bw = new java.io.BufferedWriter(fw);

                // 3. Ambil Header Tabel
                for (int i = 0; i < table.getColumnCount(); i++) {
                    bw.write(table.getColumnName(i) + ";");
                }
                bw.newLine();

                // 4. Ambil Isi Data (DENGAN PERBAIKAN FORMAT NO TELP)
                for (int i = 0; i < table.getRowCount(); i++) {
                    for (int j = 0; j < table.getColumnCount(); j++) {
                        Object val = table.getValueAt(i, j);
                        String data = (val != null ? val.toString() : "");

                        // CEK: Jika data diawali angka '0' atau '62' dan isinya panjang (seperti No HP)
                        // Kita tambahkan petik satu agar Excel menganggapnya TEXT
                        if (data.startsWith("0") || data.startsWith("62")) {
                            bw.write("'" + data + ";");
                        } else {
                            bw.write(data + ";");
                        }
                    }
                    bw.newLine();
                }

                bw.close();
                fw.close();

                // 5. Berhasil! Tanya mau buka filenya sekarang?
                int buka = javax.swing.JOptionPane.showConfirmDialog(null, 
                        "Laporan berhasil disimpan!\nBuka file sekarang?", 
                        "Sukses", javax.swing.JOptionPane.YES_NO_OPTION);

                if (buka == javax.swing.JOptionPane.YES_OPTION) {
                    java.awt.Desktop.getDesktop().open(fileSimpan);
                }

            } catch (Exception e) {
                javax.swing.JOptionPane.showMessageDialog(null, "Terjadi kesalahan: " + e.getMessage());
            }
        }
    }
    
    private void cetakStrukPDF(int row) {
        // 1. Ambil Data Lengkap (Sesuaikan index kolom tabel kamu)
        String idTr = tabelTr.getValueAt(row, 0).toString();
        String nama = tabelTr.getValueAt(row, 2).toString();
        String pc = tabelTr.getValueAt(row, 3).toString();
        // Ambil data waktu & durasi jika ada di tabel, jika tidak gunakan teks default/tambah kolom
        String jamMulai = tabelTr.getValueAt(row, 5).toString(); 
        String jamSelesai = tabelTr.getValueAt(row, 6).toString(); 
        String durasi = tabelTr.getValueAt(row, 7).toString(); 
        String biaya = tabelTr.getValueAt(row, 8).toString();

        String namaFile = idTr + "_" + nama.replace(" ", "") + ".pdf";

        javax.swing.JFileChooser chooser = new javax.swing.JFileChooser();
        chooser.setSelectedFile(new java.io.File(namaFile));

        if (chooser.showSaveDialog(null) == javax.swing.JFileChooser.APPROVE_OPTION) {
            String path = chooser.getSelectedFile().getAbsolutePath();

            try {
                // 2. Setting Ukuran Kertas Struk (58mm x 116mm)
                float width = 58 * 2.83465f;
                float height = 116 * 2.83465f;
                com.itextpdf.kernel.geom.PageSize strukSize = new com.itextpdf.kernel.geom.PageSize(width, height);

                // 3. Siapkan Font
                com.itextpdf.kernel.font.PdfFont bold = com.itextpdf.kernel.font.PdfFontFactory.createFont(com.itextpdf.io.font.constants.StandardFonts.HELVETICA_BOLD);
                com.itextpdf.kernel.font.PdfFont italic = com.itextpdf.kernel.font.PdfFontFactory.createFont(com.itextpdf.io.font.constants.StandardFonts.HELVETICA_OBLIQUE);
                com.itextpdf.kernel.font.PdfFont normal = com.itextpdf.kernel.font.PdfFontFactory.createFont(com.itextpdf.io.font.constants.StandardFonts.HELVETICA);

                // 4. Mulai proses tulis PDF dengan Ukuran Custom
                try (com.itextpdf.kernel.pdf.PdfWriter writer = new com.itextpdf.kernel.pdf.PdfWriter(path);
                     com.itextpdf.kernel.pdf.PdfDocument pdf = new com.itextpdf.kernel.pdf.PdfDocument(writer);
                     com.itextpdf.layout.Document document = new com.itextpdf.layout.Document(pdf, strukSize)) {

                    document.setMargins(10, 10, 10, 10); // Margin agar tidak mepet

                    // --- BAGIAN KOP ---
                    try {
                        // 1. Ambil Gambar dari Package Asset
                        // Ganti "/asset/logo.png" dengan path dan nama file kamu yang sebenarnya
                        java.net.URL logoUrl = getClass().getResource("/assets/logowfo.png"); 

                        if (logoUrl != null) {
                            com.itextpdf.io.image.ImageData imageData = com.itextpdf.io.image.ImageDataFactory.create(logoUrl);
                            com.itextpdf.layout.element.Image logo = new com.itextpdf.layout.element.Image(imageData);

                            // 2. Atur Ukuran Logo (Smooth)
                            // Coba pakai lebar 30f atau 40f supaya pas dengan lebar struk 58mm
                            logo.setWidth(40f); 

                            // 3. Buat Logo ke Tengah
                            logo.setHorizontalAlignment(com.itextpdf.layout.properties.HorizontalAlignment.CENTER);

                            // Tambahkan ke dokumen
                            document.add(logo);
                        }
                    } catch (Exception e) {
                        // Jika logo gagal dimuat, aplikasi tidak akan crash, hanya logonya saja yang tidak muncul
                        System.out.println("Logo tidak ditemukan: " + e.getMessage());
                    }
                    // Judul Warnet
                    document.add(new com.itextpdf.layout.element.Paragraph("WARNET FAL OPAL")
                            .setFont(bold)
                            .setFontSize(10)
                            .setTextAlignment(com.itextpdf.layout.properties.TextAlignment.CENTER)
                            .setMarginBottom(0f) // <--- Hapus jarak bawah judul
                            .setFixedLeading(9f)); // <--- Mengatur jarak antar baris secara manual (angka lebih kecil = lebih rapat)

                    // Alamat
                    document.add(new com.itextpdf.layout.element.Paragraph("Jl. Antara Ada dan Tiada, Desa Air, Banjarmasin")
                            .setFont(normal)
                            .setFontSize(7)
                            .setTextAlignment(com.itextpdf.layout.properties.TextAlignment.CENTER)
                            .setMarginTop(0f) // <--- Hapus jarak atas alamat
                            .setFixedLeading(7f)); // <--- Mengatur jarak antar baris secara manual
                    document.add(new com.itextpdf.layout.element.Paragraph("-------------------------------------------------------------").setFontSize(7));

                    // --- DATA TRANSAKSI ---
                    // Buat tabel dengan 3 kolom. Kolom 1 untuk label, kolom 2 untuk titik dua, kolom 3 untuk nilai.
                    float[] columnWidths = {40f, 5f, 75f}; 
                    com.itextpdf.layout.element.Table table = new com.itextpdf.layout.element.Table(columnWidths);
                    table.setWidth(com.itextpdf.layout.properties.UnitValue.createPercentValue(100));

                    // Fungsi pembantu supaya tidak tulis kode berulang
                    addTableCell(table, "ID TR", idTr, normal);
                    addTableCell(table, "NAMA", nama, normal);
                    addTableCell(table, "PC", pc, normal);
                    addTableCell(table, "MULAI", jamMulai, normal);
                    addTableCell(table, "SELESAI", jamSelesai, normal);
                    addTableCell(table, "DURASI", durasi, normal);

                    document.add(table);
                    document.add(new com.itextpdf.layout.element.Paragraph("-------------------------------------------------------------").setFontSize(7));

                    // --- TOTAL BIAYA ---
                    document.add(new com.itextpdf.layout.element.Paragraph("TOTAL BIAYA : " + biaya)
                            .setFont(bold).setFontSize(8));

                    document.add(new com.itextpdf.layout.element.Paragraph("-------------------------------------------------------------").setFontSize(7));

                    // --- PENUTUP ---
                    document.add(new com.itextpdf.layout.element.Paragraph("Terima Kasih, ditunggu Kunjungan Anda Berikutnya!")
                            .setFont(italic).setFontSize(7).setTextAlignment(com.itextpdf.layout.properties.TextAlignment.CENTER));

                    // 5. Notifikasi & Buka Otomatis
                    javax.swing.JOptionPane.showMessageDialog(null, "Struk PDF Berhasil Dibuat!");
                    java.awt.Desktop.getDesktop().open(new java.io.File(path));
                }
            } catch (java.io.IOException e) {
                javax.swing.JOptionPane.showMessageDialog(null, "Gagal membuat PDF: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
    
    private void addTableCell(com.itextpdf.layout.element.Table table, String label, String value, com.itextpdf.kernel.font.PdfFont font) {
        // Kolom 1: Label
        table.addCell(new com.itextpdf.layout.element.Cell().add(new com.itextpdf.layout.element.Paragraph(label).setFont(font).setFontSize(7))
                .setBorder(com.itextpdf.layout.borders.Border.NO_BORDER).setPadding(0));
        // Kolom 2: Titik Dua
        table.addCell(new com.itextpdf.layout.element.Cell().add(new com.itextpdf.layout.element.Paragraph(":").setFont(font).setFontSize(7))
                .setBorder(com.itextpdf.layout.borders.Border.NO_BORDER).setPadding(0));
        // Kolom 3: Nilai/Isi
        table.addCell(new com.itextpdf.layout.element.Cell().add(new com.itextpdf.layout.element.Paragraph(value).setFont(font).setFontSize(7))
                .setBorder(com.itextpdf.layout.borders.Border.NO_BORDER).setPadding(0));
}
    //*------------------------------------------------------------------------------------------------
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        logo = new javax.swing.JLabel();
        panelHeader = new javax.swing.JPanel();
        lblTanggal = new javax.swing.JLabel();
        lblAlamat = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        lblTime = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        lblAlamat1 = new javax.swing.JLabel();
        panelKiri = new javax.swing.JPanel();
        btnBeranda = new javax.swing.JLabel();
        btnPC = new javax.swing.JLabel();
        btnPelanggan = new javax.swing.JLabel();
        btnTransaksi = new javax.swing.JLabel();
        btnAlarm = new javax.swing.JLabel();
        btnLogout = new javax.swing.JLabel();
        panelUtama = new javax.swing.JPanel();
        panelMenuBeranda = new javax.swing.JPanel();
        instagramlogo = new javax.swing.JLabel();
        lblWelcome = new javax.swing.JLabel();
        paneText = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        anime = new javax.swing.JLabel();
        panelMenuPC = new javax.swing.JPanel();
        judulPC = new javax.swing.JLabel();
        head = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txtID = new javax.swing.JTextField();
        txtMerek = new javax.swing.JTextField();
        txtProcessor = new javax.swing.JTextField();
        btnCetak = new javax.swing.JButton();
        btnDaftar = new javax.swing.JButton();
        btnEdit = new javax.swing.JButton();
        btnHapus = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabelPC = new javax.swing.JTable();
        jLabel7 = new javax.swing.JLabel();
        txtPCCari = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        btnPilih = new javax.swing.JLabel();
        btnBatal = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        txtTarif = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        panelMenuPelanggan = new javax.swing.JPanel();
        judulPl = new javax.swing.JLabel();
        head1 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        txtPlID = new javax.swing.JTextField();
        txtPlNama = new javax.swing.JTextField();
        cmbPlGender = new javax.swing.JComboBox<>();
        txtPlTelpon = new javax.swing.JTextField();
        txtPlAlamat = new javax.swing.JTextField();
        txtPlEmail = new javax.swing.JTextField();
        btnPlCetak = new javax.swing.JButton();
        btnPlDaftar = new javax.swing.JButton();
        btnPlEdit = new javax.swing.JButton();
        btnPlHapus = new javax.swing.JButton();
        btnPlBatal = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tabelPL = new javax.swing.JTable();
        jLabel15 = new javax.swing.JLabel();
        txtPlCari = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        btnPlPilih = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        panelMenuTransaksi = new javax.swing.JPanel();
        jdlTransaksi = new javax.swing.JLabel();
        head2 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        txtTrDtkMulai = new javax.swing.JTextField();
        btnCetakTr = new javax.swing.JButton();
        btnTrDaftar = new javax.swing.JButton();
        btnTrEdit = new javax.swing.JButton();
        btnTrHapus = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tabelTr = new javax.swing.JTable();
        jLabel24 = new javax.swing.JLabel();
        txtTrCari = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        btnPilih1 = new javax.swing.JLabel();
        btnTrBatal = new javax.swing.JButton();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        txtTrBiaya = new javax.swing.JTextField();
        txtTrID = new javax.swing.JTextField();
        btnTrBiaya = new javax.swing.JButton();
        btnTrDurasi = new javax.swing.JButton();
        btnTrJam = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jButton4 = new javax.swing.JButton();
        jLabel31 = new javax.swing.JLabel();
        txtTrNama = new javax.swing.JTextField();
        jLabel32 = new javax.swing.JLabel();
        txtTrTelpon = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        jButton5 = new javax.swing.JButton();
        txtTrTarif = new javax.swing.JTextField();
        txtTrMerek = new javax.swing.JTextField();
        jLabel34 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        txtTrJamMulai = new javax.swing.JTextField();
        txtTrMntMulai = new javax.swing.JTextField();
        txtTrJamSelesai = new javax.swing.JTextField();
        txtTrMntSelesai = new javax.swing.JTextField();
        txtTrDtkSelesai = new javax.swing.JTextField();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        txtTrJamDurasi = new javax.swing.JTextField();
        txtTrMntDurasi = new javax.swing.JTextField();
        txtTrDtkDurasi = new javax.swing.JTextField();
        jLabel41 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        jLabel44 = new javax.swing.JLabel();
        jdTrTanggal = new com.toedter.calendar.JDateChooser();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTextArea2 = new javax.swing.JTextArea();
        panelMenuAlarm = new javax.swing.JPanel();
        judulPC1 = new javax.swing.JLabel();
        head3 = new javax.swing.JLabel();
        panelListAlarm = new javax.swing.JPanel();
        jScrollPaneAlarm = new javax.swing.JScrollPane();
        panelWadahSlot = new javax.swing.JPanel();
        btnKeTransaksi = new javax.swing.JButton();
        lblHtnSlt = new javax.swing.JLabel();
        lblBackground = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setMinimumSize(new java.awt.Dimension(1000, 650));
        setPreferredSize(new java.awt.Dimension(1000, 650));
        setResizable(false);
        setSize(new java.awt.Dimension(1000, 650));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        logo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/logowfo.png"))); // NOI18N
        logo.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        getContentPane().add(logo, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 7, -1, -1));

        panelHeader.setBackground(new java.awt.Color(0, 0, 0));
        panelHeader.setPreferredSize(new java.awt.Dimension(1000, 80));

        lblTanggal.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblTanggal.setForeground(new java.awt.Color(255, 255, 255));
        lblTanggal.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTanggal.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));

        lblAlamat.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        lblAlamat.setForeground(new java.awt.Color(255, 255, 255));
        lblAlamat.setText("Jl. Antara Ada dan Tiada, Desa Air, Banjarmasin");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Date :");

        lblTime.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblTime.setForeground(new java.awt.Color(255, 255, 255));
        lblTime.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTime.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));

        jLabel30.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel30.setForeground(new java.awt.Color(255, 255, 255));
        jLabel30.setText("Time :");

        lblAlamat1.setFont(new java.awt.Font("Simplified Arabic Fixed", 1, 36)); // NOI18N
        lblAlamat1.setForeground(new java.awt.Color(255, 255, 255));
        lblAlamat1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblAlamat1.setText("WARNET FAL OPAL");

        javax.swing.GroupLayout panelHeaderLayout = new javax.swing.GroupLayout(panelHeader);
        panelHeader.setLayout(panelHeaderLayout);
        panelHeaderLayout.setHorizontalGroup(
            panelHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelHeaderLayout.createSequentialGroup()
                .addContainerGap(281, Short.MAX_VALUE)
                .addGroup(panelHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(lblAlamat1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblAlamat, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(99, 99, 99)
                .addGroup(panelHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelHeaderLayout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblTanggal, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelHeaderLayout.createSequentialGroup()
                        .addComponent(jLabel30)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblTime, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(29, 29, 29))
        );
        panelHeaderLayout.setVerticalGroup(
            panelHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelHeaderLayout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addGroup(panelHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblAlamat1)
                    .addGroup(panelHeaderLayout.createSequentialGroup()
                        .addGroup(panelHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblTime, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel30))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel6)
                                .addComponent(lblAlamat))
                            .addComponent(lblTanggal, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(15, Short.MAX_VALUE))
        );

        getContentPane().add(panelHeader, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1000, 80));

        panelKiri.setBackground(new java.awt.Color(153, 153, 153));

        btnBeranda.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/btn_beranda_off.png"))); // NOI18N
        btnBeranda.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnBeranda.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnBerandaMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnBerandaMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnBerandaMouseExited(evt);
            }
        });

        btnPC.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/btn_pc_off.png"))); // NOI18N
        btnPC.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnPC.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnPCMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnPCMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnPCMouseExited(evt);
            }
        });

        btnPelanggan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/btn_pelanggan_off.png"))); // NOI18N
        btnPelanggan.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnPelanggan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnPelangganMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnPelangganMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnPelangganMouseExited(evt);
            }
        });

        btnTransaksi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/btn_transaksi_off.png"))); // NOI18N
        btnTransaksi.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnTransaksi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnTransaksiMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnTransaksiMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnTransaksiMouseExited(evt);
            }
        });

        btnAlarm.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/btn_alarm_off.png"))); // NOI18N
        btnAlarm.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAlarm.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnAlarmMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnAlarmMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnAlarmMouseExited(evt);
            }
        });

        btnLogout.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/btn_logout_off.png"))); // NOI18N
        btnLogout.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnLogout.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnLogoutMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout panelKiriLayout = new javax.swing.GroupLayout(panelKiri);
        panelKiri.setLayout(panelKiriLayout);
        panelKiriLayout.setHorizontalGroup(
            panelKiriLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelKiriLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(panelKiriLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelKiriLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(btnBeranda, javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelKiriLayout.createSequentialGroup()
                            .addGroup(panelKiriLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(btnAlarm)
                                .addComponent(btnLogout))
                            .addGap(24, 24, 24)))
                    .addComponent(btnPC)
                    .addComponent(btnPelanggan)
                    .addComponent(btnTransaksi))
                .addContainerGap(16, Short.MAX_VALUE))
        );
        panelKiriLayout.setVerticalGroup(
            panelKiriLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelKiriLayout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(btnBeranda)
                .addGap(18, 18, 18)
                .addComponent(btnPC)
                .addGap(18, 18, 18)
                .addComponent(btnPelanggan)
                .addGap(18, 18, 18)
                .addComponent(btnTransaksi)
                .addGap(55, 55, 55)
                .addComponent(btnAlarm)
                .addGap(67, 67, 67)
                .addComponent(btnLogout)
                .addContainerGap(138, Short.MAX_VALUE))
        );

        getContentPane().add(panelKiri, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 80, 200, 620));

        panelUtama.setBackground(new java.awt.Color(63, 63, 63));
        panelUtama.setLayout(new java.awt.CardLayout());

        panelMenuBeranda.setBackground(new java.awt.Color(51, 51, 51));
        panelMenuBeranda.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        instagramlogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/instagram.png"))); // NOI18N
        panelMenuBeranda.add(instagramlogo, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 310, 20, 20));

        lblWelcome.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lblWelcome.setForeground(new java.awt.Color(255, 255, 255));
        lblWelcome.setText("SELAMAT DATANG!!");
        panelMenuBeranda.add(lblWelcome, new org.netbeans.lib.awtextra.AbsoluteConstraints(66, 53, -1, -1));

        jTextArea1.setEditable(false);
        jTextArea1.setBackground(new java.awt.Color(0, 0, 0));
        jTextArea1.setColumns(20);
        jTextArea1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jTextArea1.setForeground(new java.awt.Color(204, 204, 204));
        jTextArea1.setLineWrap(true);
        jTextArea1.setRows(5);
        jTextArea1.setText("Di sini menyediakan penyewaan PC dengan harga yang terjangkau se-Banjarmasin.\n\nMau :\n- Ngegame\n- Internetan\n- Nugas\n- Ngetik\ndan lain-lain.\n\nWFO tempatnya. Yuk ramaikan keseruanmu di sosial media, dan tag\n        @WarnetFO\n\nKami usahakan memberikan pelayanan yang terbaik. Anda sopan kami senang.\nTerima kasih\n");
        jTextArea1.setWrapStyleWord(true);
        jTextArea1.setFocusable(false);
        jTextArea1.setOpaque(false);
        paneText.setViewportView(jTextArea1);

        panelMenuBeranda.add(paneText, new org.netbeans.lib.awtextra.AbsoluteConstraints(58, 101, 651, 350));
        panelMenuBeranda.add(anime, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 90, 395, 533));

        panelUtama.add(panelMenuBeranda, "menu_beranda");

        panelMenuPC.setBackground(new java.awt.Color(51, 51, 51));
        panelMenuPC.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        judulPC.setFont(new java.awt.Font("Comic Sans MS", 1, 18)); // NOI18N
        judulPC.setForeground(new java.awt.Color(255, 255, 255));
        judulPC.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        judulPC.setText("DATA PC");
        panelMenuPC.add(judulPC, new org.netbeans.lib.awtextra.AbsoluteConstraints(1, 20, 200, 50));

        head.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/head.png"))); // NOI18N
        panelMenuPC.add(head, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 20, -1, 50));

        jLabel8.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel8.setText("ID :");
        panelMenuPC.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 60, 150, -1));

        jLabel9.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel9.setText("Merek :");
        panelMenuPC.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 90, 150, -1));

        jLabel10.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel10.setText("Processor :");
        panelMenuPC.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 120, 150, -1));

        txtID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIDActionPerformed(evt);
            }
        });
        panelMenuPC.add(txtID, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 60, 250, -1));

        txtMerek.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMerekActionPerformed(evt);
            }
        });
        panelMenuPC.add(txtMerek, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 90, 250, -1));
        panelMenuPC.add(txtProcessor, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 120, 250, -1));

        btnCetak.setBackground(new java.awt.Color(204, 204, 204));
        btnCetak.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        btnCetak.setText("Cetak");
        btnCetak.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnCetak.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCetakActionPerformed(evt);
            }
        });
        panelMenuPC.add(btnCetak, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 470, 80, 30));

        btnDaftar.setBackground(new java.awt.Color(51, 102, 255));
        btnDaftar.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        btnDaftar.setForeground(new java.awt.Color(255, 255, 255));
        btnDaftar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/person_add.png"))); // NOI18N
        btnDaftar.setText("Daftar");
        btnDaftar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnDaftar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnDaftarMouseClicked(evt);
            }
        });
        btnDaftar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDaftarActionPerformed(evt);
            }
        });
        panelMenuPC.add(btnDaftar, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 231, 150, 20));

        btnEdit.setBackground(new java.awt.Color(51, 102, 255));
        btnEdit.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        btnEdit.setForeground(new java.awt.Color(255, 255, 255));
        btnEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/edit.png"))); // NOI18N
        btnEdit.setText("Edit");
        btnEdit.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnEdit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnEditMouseClicked(evt);
            }
        });
        panelMenuPC.add(btnEdit, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 231, 150, 20));

        btnHapus.setBackground(new java.awt.Color(51, 102, 255));
        btnHapus.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        btnHapus.setForeground(new java.awt.Color(255, 255, 255));
        btnHapus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/buang.png"))); // NOI18N
        btnHapus.setText("Hapus");
        btnHapus.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnHapus.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnHapusMouseClicked(evt);
            }
        });
        panelMenuPC.add(btnHapus, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 231, 150, 20));

        jPanel1.setBackground(new java.awt.Color(25, 25, 25));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tabelPC.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "ID", "Merek", "Processor", "Tarif"
            }
        ));
        tabelPC.setFillsViewportHeight(true);
        tabelPC.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelPCMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tabelPC);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, 620, 150));

        jLabel7.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel7.setText("Cari PC :");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 320, -1));

        txtPCCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPCCariActionPerformed(evt);
            }
        });
        txtPCCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtPCCariKeyReleased(evt);
            }
        });
        jPanel1.add(txtPCCari, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 10, 260, -1));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/search.png"))); // NOI18N
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 10, -1, 20));

        panelMenuPC.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 260, 660, 200));

        btnPilih.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/btn_pilihtr_off.png"))); // NOI18N
        btnPilih.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnPilih.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnPilihMouseClicked(evt);
            }
        });
        panelMenuPC.add(btnPilih, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 480, -1, -1));

        btnBatal.setBackground(new java.awt.Color(51, 102, 255));
        btnBatal.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        btnBatal.setForeground(new java.awt.Color(255, 255, 255));
        btnBatal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/batal.png"))); // NOI18N
        btnBatal.setText("Batal");
        btnBatal.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnBatal.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnBatalMouseClicked(evt);
            }
        });
        panelMenuPC.add(btnBatal, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 231, 150, 20));

        jLabel11.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel11.setText("Tarif / jam :");
        panelMenuPC.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 150, 150, -1));

        txtTarif.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTarifActionPerformed(evt);
            }
        });
        txtTarif.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtTarifKeyTyped(evt);
            }
        });
        panelMenuPC.add(txtTarif, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 150, 250, -1));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/print.png"))); // NOI18N
        panelMenuPC.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 470, -1, 30));

        panelUtama.add(panelMenuPC, "menu_pc");

        panelMenuPelanggan.setBackground(new java.awt.Color(51, 51, 51));
        panelMenuPelanggan.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        judulPl.setFont(new java.awt.Font("Comic Sans MS", 1, 18)); // NOI18N
        judulPl.setForeground(new java.awt.Color(255, 255, 255));
        judulPl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        judulPl.setText("PELANGGAN");
        panelMenuPelanggan.add(judulPl, new org.netbeans.lib.awtextra.AbsoluteConstraints(1, 20, 200, 50));

        head1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/head.png"))); // NOI18N
        panelMenuPelanggan.add(head1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 20, -1, 50));

        jLabel13.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel13.setText("ID Pelanggan  :");
        panelMenuPelanggan.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, 110, -1));

        jLabel14.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel14.setText("No. Telpon   :");
        panelMenuPelanggan.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 180, 110, -1));

        txtPlID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPlIDActionPerformed(evt);
            }
        });
        panelMenuPelanggan.add(txtPlID, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 90, 250, -1));

        txtPlNama.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPlNamaActionPerformed(evt);
            }
        });
        panelMenuPelanggan.add(txtPlNama, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 120, 580, -1));

        cmbPlGender.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--pilih--", "Laki-laki", "Perempuan" }));
        panelMenuPelanggan.add(cmbPlGender, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 90, 210, -1));

        txtPlTelpon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPlTelponActionPerformed(evt);
            }
        });
        txtPlTelpon.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPlTelponKeyTyped(evt);
            }
        });
        panelMenuPelanggan.add(txtPlTelpon, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 180, 160, -1));

        txtPlAlamat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPlAlamatActionPerformed(evt);
            }
        });
        txtPlAlamat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPlAlamatKeyTyped(evt);
            }
        });
        panelMenuPelanggan.add(txtPlAlamat, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 150, 580, -1));
        panelMenuPelanggan.add(txtPlEmail, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 180, 310, -1));

        btnPlCetak.setBackground(new java.awt.Color(204, 204, 204));
        btnPlCetak.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        btnPlCetak.setText("Cetak");
        btnPlCetak.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnPlCetak.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPlCetakActionPerformed(evt);
            }
        });
        panelMenuPelanggan.add(btnPlCetak, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 470, 80, 30));

        btnPlDaftar.setBackground(new java.awt.Color(51, 102, 255));
        btnPlDaftar.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        btnPlDaftar.setForeground(new java.awt.Color(255, 255, 255));
        btnPlDaftar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/person_add.png"))); // NOI18N
        btnPlDaftar.setText("Daftar");
        btnPlDaftar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnPlDaftar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnPlDaftarMouseClicked(evt);
            }
        });
        btnPlDaftar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPlDaftarActionPerformed(evt);
            }
        });
        panelMenuPelanggan.add(btnPlDaftar, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 231, 150, 20));

        btnPlEdit.setBackground(new java.awt.Color(51, 102, 255));
        btnPlEdit.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        btnPlEdit.setForeground(new java.awt.Color(255, 255, 255));
        btnPlEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/edit.png"))); // NOI18N
        btnPlEdit.setText("Edit");
        btnPlEdit.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnPlEdit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnPlEditMouseClicked(evt);
            }
        });
        panelMenuPelanggan.add(btnPlEdit, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 231, 150, 20));

        btnPlHapus.setBackground(new java.awt.Color(51, 102, 255));
        btnPlHapus.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        btnPlHapus.setForeground(new java.awt.Color(255, 255, 255));
        btnPlHapus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/buang.png"))); // NOI18N
        btnPlHapus.setText("Hapus");
        btnPlHapus.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnPlHapus.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnPlHapusMouseClicked(evt);
            }
        });
        panelMenuPelanggan.add(btnPlHapus, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 231, 150, 20));

        btnPlBatal.setBackground(new java.awt.Color(51, 102, 255));
        btnPlBatal.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        btnPlBatal.setForeground(new java.awt.Color(255, 255, 255));
        btnPlBatal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/batal.png"))); // NOI18N
        btnPlBatal.setText("Batal");
        btnPlBatal.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnPlBatal.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnPlBatalMouseClicked(evt);
            }
        });
        panelMenuPelanggan.add(btnPlBatal, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 231, 150, 20));

        jPanel2.setBackground(new java.awt.Color(25, 25, 25));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tabelPL.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Nama", "Jenis Kelamin", "No. Telp.", "Alamat", "Email"
            }
        ));
        tabelPL.setFillsViewportHeight(true);
        tabelPL.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelPLMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tabelPL);

        jPanel2.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, 620, 150));

        jLabel15.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel15.setText("Cari PC :");
        jPanel2.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 320, -1));

        txtPlCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPlCariActionPerformed(evt);
            }
        });
        txtPlCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtPlCariKeyReleased(evt);
            }
        });
        jPanel2.add(txtPlCari, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 10, 260, -1));

        jLabel16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/search.png"))); // NOI18N
        jPanel2.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 10, -1, 20));

        panelMenuPelanggan.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 260, 660, 200));

        btnPlPilih.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/btn_pilihtr_off.png"))); // NOI18N
        btnPlPilih.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnPlPilih.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnPlPilihMouseClicked(evt);
            }
        });
        panelMenuPelanggan.add(btnPlPilih, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 480, -1, -1));

        jLabel17.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel17.setText("Alamat  :");
        panelMenuPelanggan.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 150, 110, -1));

        jLabel18.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/print.png"))); // NOI18N
        panelMenuPelanggan.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 470, -1, 30));

        jLabel19.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(255, 255, 255));
        jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel19.setText("Nama Lengkap  :");
        panelMenuPelanggan.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 120, 110, -1));

        jLabel21.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(255, 255, 255));
        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel21.setText("Jenis Kelamin  :");
        panelMenuPelanggan.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 90, 110, -1));

        jLabel23.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(255, 255, 255));
        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel23.setText("Email  :");
        panelMenuPelanggan.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 180, 70, -1));

        panelUtama.add(panelMenuPelanggan, "menu_pelanggan");

        panelMenuTransaksi.setBackground(new java.awt.Color(51, 51, 51));
        panelMenuTransaksi.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jdlTransaksi.setFont(new java.awt.Font("Comic Sans MS", 1, 18)); // NOI18N
        jdlTransaksi.setForeground(new java.awt.Color(255, 255, 255));
        jdlTransaksi.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jdlTransaksi.setText("TRANSAKSI");
        panelMenuTransaksi.add(jdlTransaksi, new org.netbeans.lib.awtextra.AbsoluteConstraints(1, 20, 200, 50));

        head2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/head.png"))); // NOI18N
        panelMenuTransaksi.add(head2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 20, -1, 50));

        jLabel12.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel12.setText("Pilih Tangal :");
        panelMenuTransaksi.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(-40, 100, 150, -1));

        jLabel20.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(255, 255, 255));
        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel20.setText("Jam Mulai  :");
        panelMenuTransaksi.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(-40, 130, 150, -1));

        jLabel22.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(255, 255, 255));
        jLabel22.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel22.setText("Jam Selesai :");
        panelMenuTransaksi.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(-40, 160, 150, -1));

        txtTrDtkMulai.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtTrDtkMulai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTrDtkMulaiActionPerformed(evt);
            }
        });
        txtTrDtkMulai.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtTrDtkMulaiKeyTyped(evt);
            }
        });
        panelMenuTransaksi.add(txtTrDtkMulai, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 130, 30, -1));

        btnCetakTr.setBackground(new java.awt.Color(204, 204, 204));
        btnCetakTr.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        btnCetakTr.setText("Cetak");
        btnCetakTr.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnCetakTr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCetakTrActionPerformed(evt);
            }
        });
        panelMenuTransaksi.add(btnCetakTr, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 470, 80, 30));

        btnTrDaftar.setBackground(new java.awt.Color(51, 102, 255));
        btnTrDaftar.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        btnTrDaftar.setForeground(new java.awt.Color(255, 255, 255));
        btnTrDaftar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/person_add.png"))); // NOI18N
        btnTrDaftar.setText("Daftar");
        btnTrDaftar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnTrDaftar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnTrDaftarMouseClicked(evt);
            }
        });
        panelMenuTransaksi.add(btnTrDaftar, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 231, 150, 20));

        btnTrEdit.setBackground(new java.awt.Color(51, 102, 255));
        btnTrEdit.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        btnTrEdit.setForeground(new java.awt.Color(255, 255, 255));
        btnTrEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/edit.png"))); // NOI18N
        btnTrEdit.setText("Edit");
        btnTrEdit.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnTrEdit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnTrEditMouseClicked(evt);
            }
        });
        panelMenuTransaksi.add(btnTrEdit, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 231, 150, 20));

        btnTrHapus.setBackground(new java.awt.Color(51, 102, 255));
        btnTrHapus.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        btnTrHapus.setForeground(new java.awt.Color(255, 255, 255));
        btnTrHapus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/buang.png"))); // NOI18N
        btnTrHapus.setText("Hapus");
        btnTrHapus.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnTrHapus.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnTrHapusMouseClicked(evt);
            }
        });
        panelMenuTransaksi.add(btnTrHapus, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 231, 150, 20));

        jPanel3.setBackground(new java.awt.Color(25, 25, 25));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tabelTr.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Tanggal", "Nama", "Merek PC", "Tarif/jam", "Jam Mulai", "Jam Selesai", "Durasi", "Biaya", "No. Telp"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                true, true, true, true, true, true, true, true, true, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabelTr.setFillsViewportHeight(true);
        tabelTr.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelTrMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tabelTr);
        if (tabelTr.getColumnModel().getColumnCount() > 0) {
            tabelTr.getColumnModel().getColumn(9).setMinWidth(0);
            tabelTr.getColumnModel().getColumn(9).setPreferredWidth(0);
            tabelTr.getColumnModel().getColumn(9).setMaxWidth(0);
        }

        jPanel3.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, 620, 150));

        jLabel24.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(255, 255, 255));
        jLabel24.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel24.setText("Cari Transaksi :");
        jPanel3.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 320, -1));

        txtTrCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTrCariActionPerformed(evt);
            }
        });
        txtTrCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTrCariKeyReleased(evt);
            }
        });
        jPanel3.add(txtTrCari, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 10, 260, -1));

        jLabel25.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/search.png"))); // NOI18N
        jPanel3.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 10, -1, 20));

        panelMenuTransaksi.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 260, 660, 200));

        btnPilih1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/btn_pilihalarm_off.png"))); // NOI18N
        btnPilih1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnPilih1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnPilih1MouseClicked(evt);
            }
        });
        panelMenuTransaksi.add(btnPilih1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 480, -1, -1));

        btnTrBatal.setBackground(new java.awt.Color(51, 102, 255));
        btnTrBatal.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        btnTrBatal.setForeground(new java.awt.Color(255, 255, 255));
        btnTrBatal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/batal.png"))); // NOI18N
        btnTrBatal.setText("Batal");
        btnTrBatal.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnTrBatal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTrBatalActionPerformed(evt);
            }
        });
        panelMenuTransaksi.add(btnTrBatal, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 231, 150, 20));

        jLabel26.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(255, 255, 255));
        jLabel26.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel26.setText("Durasi  :");
        panelMenuTransaksi.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(-40, 190, 150, -1));

        jLabel27.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/print.png"))); // NOI18N
        panelMenuTransaksi.add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 470, -1, 30));

        jLabel28.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(255, 255, 255));
        jLabel28.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel28.setText("ID Transaksi  :");
        panelMenuTransaksi.add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 140, 110, -1));

        jLabel29.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(255, 255, 255));
        jLabel29.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel29.setText("Biaya :");
        panelMenuTransaksi.add(jLabel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 170, 60, -1));
        panelMenuTransaksi.add(txtTrBiaya, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 170, 170, -1));

        txtTrID.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtTrID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTrIDActionPerformed(evt);
            }
        });
        panelMenuTransaksi.add(txtTrID, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 140, 170, -1));

        btnTrBiaya.setBackground(new java.awt.Color(255, 153, 0));
        btnTrBiaya.setFont(new java.awt.Font("Comic Sans MS", 1, 12)); // NOI18N
        btnTrBiaya.setForeground(new java.awt.Color(255, 255, 255));
        btnTrBiaya.setText("Biaya");
        btnTrBiaya.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnTrBiaya.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTrBiayaActionPerformed(evt);
            }
        });
        panelMenuTransaksi.add(btnTrBiaya, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 190, 70, -1));

        btnTrDurasi.setBackground(new java.awt.Color(255, 153, 0));
        btnTrDurasi.setFont(new java.awt.Font("Comic Sans MS", 1, 12)); // NOI18N
        btnTrDurasi.setForeground(new java.awt.Color(255, 255, 255));
        btnTrDurasi.setText("Durasi");
        btnTrDurasi.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnTrDurasi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTrDurasiActionPerformed(evt);
            }
        });
        panelMenuTransaksi.add(btnTrDurasi, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 160, 70, -1));

        btnTrJam.setBackground(new java.awt.Color(255, 153, 0));
        btnTrJam.setFont(new java.awt.Font("Comic Sans MS", 1, 12)); // NOI18N
        btnTrJam.setForeground(new java.awt.Color(255, 255, 255));
        btnTrJam.setText("Jam");
        btnTrJam.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnTrJam.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTrJamActionPerformed(evt);
            }
        });
        panelMenuTransaksi.add(btnTrJam, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 130, 70, -1));

        jPanel4.setBackground(new java.awt.Color(25, 25, 25));
        jPanel4.setForeground(new java.awt.Color(255, 255, 255));

        jButton4.setBackground(new java.awt.Color(255, 153, 0));
        jButton4.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        jButton4.setForeground(new java.awt.Color(255, 255, 255));
        jButton4.setText("Pilih Pelanggan");
        jButton4.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jLabel31.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel31.setForeground(new java.awt.Color(255, 255, 255));
        jLabel31.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel31.setText("Nama  :");

        jLabel32.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel32.setForeground(new java.awt.Color(255, 255, 255));
        jLabel32.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel32.setText("Telpon  :");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTrNama))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTrTelpon, javax.swing.GroupLayout.DEFAULT_SIZE, 104, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel31)
                    .addComponent(txtTrNama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel32)
                    .addComponent(txtTrTelpon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(15, Short.MAX_VALUE))
        );

        panelMenuTransaksi.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 10, 180, 110));

        jPanel5.setBackground(new java.awt.Color(25, 25, 25));
        jPanel5.setForeground(new java.awt.Color(255, 255, 255));

        jButton5.setBackground(new java.awt.Color(255, 153, 0));
        jButton5.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        jButton5.setForeground(new java.awt.Color(255, 255, 255));
        jButton5.setText("Pilih PC");
        jButton5.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        txtTrTarif.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel34.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel34.setForeground(new java.awt.Color(255, 255, 255));
        jLabel34.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel34.setText("Tarif  :");

        jLabel33.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel33.setForeground(new java.awt.Color(255, 255, 255));
        jLabel33.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel33.setText("Merek  :");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton5, javax.swing.GroupLayout.DEFAULT_SIZE, 168, Short.MAX_VALUE)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel33, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTrMerek))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel34, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTrTarif, javax.swing.GroupLayout.DEFAULT_SIZE, 104, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel33)
                    .addComponent(txtTrMerek, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel34)
                    .addComponent(txtTrTarif, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(15, Short.MAX_VALUE))
        );

        panelMenuTransaksi.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 10, -1, 110));

        txtTrJamMulai.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtTrJamMulai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTrJamMulaiActionPerformed(evt);
            }
        });
        txtTrJamMulai.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtTrJamMulaiKeyTyped(evt);
            }
        });
        panelMenuTransaksi.add(txtTrJamMulai, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 130, 30, -1));

        txtTrMntMulai.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtTrMntMulai.setToolTipText("Maksimal 59");
        txtTrMntMulai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTrMntMulaiActionPerformed(evt);
            }
        });
        txtTrMntMulai.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtTrMntMulaiKeyTyped(evt);
            }
        });
        panelMenuTransaksi.add(txtTrMntMulai, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 130, 30, -1));

        txtTrJamSelesai.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtTrJamSelesai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTrJamSelesaiActionPerformed(evt);
            }
        });
        txtTrJamSelesai.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtTrJamSelesaiKeyTyped(evt);
            }
        });
        panelMenuTransaksi.add(txtTrJamSelesai, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 160, 30, -1));

        txtTrMntSelesai.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtTrMntSelesai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTrMntSelesaiActionPerformed(evt);
            }
        });
        txtTrMntSelesai.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtTrMntSelesaiKeyTyped(evt);
            }
        });
        panelMenuTransaksi.add(txtTrMntSelesai, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 160, 30, -1));

        txtTrDtkSelesai.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtTrDtkSelesai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTrDtkSelesaiActionPerformed(evt);
            }
        });
        txtTrDtkSelesai.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtTrDtkSelesaiKeyTyped(evt);
            }
        });
        panelMenuTransaksi.add(txtTrDtkSelesai, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 160, 30, -1));

        jLabel35.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel35.setForeground(new java.awt.Color(255, 255, 255));
        jLabel35.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel35.setText("Jam");
        panelMenuTransaksi.add(jLabel35, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 160, 30, -1));

        jLabel36.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel36.setForeground(new java.awt.Color(255, 255, 255));
        jLabel36.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel36.setText("Mnt");
        panelMenuTransaksi.add(jLabel36, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 160, 30, -1));

        jLabel37.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel37.setForeground(new java.awt.Color(255, 255, 255));
        jLabel37.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel37.setText("Jam");
        panelMenuTransaksi.add(jLabel37, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 130, 30, -1));

        jLabel38.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel38.setForeground(new java.awt.Color(255, 255, 255));
        jLabel38.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel38.setText("Dtk");
        panelMenuTransaksi.add(jLabel38, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 160, 30, -1));

        jLabel39.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel39.setForeground(new java.awt.Color(255, 255, 255));
        jLabel39.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel39.setText("Mnt");
        panelMenuTransaksi.add(jLabel39, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 130, 30, -1));

        jLabel40.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel40.setForeground(new java.awt.Color(255, 255, 255));
        jLabel40.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel40.setText("Dtk");
        panelMenuTransaksi.add(jLabel40, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 130, 30, -1));

        txtTrJamDurasi.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtTrJamDurasi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTrJamDurasiActionPerformed(evt);
            }
        });
        panelMenuTransaksi.add(txtTrJamDurasi, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 190, 30, -1));

        txtTrMntDurasi.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtTrMntDurasi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTrMntDurasiActionPerformed(evt);
            }
        });
        txtTrMntDurasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtTrMntDurasiKeyTyped(evt);
            }
        });
        panelMenuTransaksi.add(txtTrMntDurasi, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 190, 30, -1));

        txtTrDtkDurasi.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtTrDtkDurasi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTrDtkDurasiActionPerformed(evt);
            }
        });
        txtTrDtkDurasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtTrDtkDurasiKeyTyped(evt);
            }
        });
        panelMenuTransaksi.add(txtTrDtkDurasi, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 190, 30, -1));

        jLabel41.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel41.setForeground(new java.awt.Color(255, 255, 255));
        jLabel41.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel41.setText("Jam");
        panelMenuTransaksi.add(jLabel41, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 190, 30, -1));

        jLabel42.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel42.setForeground(new java.awt.Color(255, 255, 255));
        jLabel42.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel42.setText("Mnt");
        panelMenuTransaksi.add(jLabel42, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 190, 30, -1));

        jLabel43.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel43.setForeground(new java.awt.Color(255, 255, 255));
        jLabel43.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel43.setText("Dtk");
        panelMenuTransaksi.add(jLabel43, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 190, 30, -1));

        jLabel44.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel44.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/transaksi.png"))); // NOI18N
        panelMenuTransaksi.add(jLabel44, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 160, 30, -1));
        panelMenuTransaksi.add(jdTrTanggal, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 100, 210, -1));

        jScrollPane4.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane4.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        jTextArea2.setBackground(new java.awt.Color(255, 204, 0));
        jTextArea2.setColumns(20);
        jTextArea2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jTextArea2.setForeground(new java.awt.Color(255, 255, 255));
        jTextArea2.setLineWrap(true);
        jTextArea2.setRows(6);
        jTextArea2.setWrapStyleWord(true);
        jTextArea2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jScrollPane4.setViewportView(jTextArea2);

        panelMenuTransaksi.add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 140, 30, 60));

        panelUtama.add(panelMenuTransaksi, "menu_transaksi");

        panelMenuAlarm.setBackground(new java.awt.Color(51, 51, 51));
        panelMenuAlarm.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        judulPC1.setFont(new java.awt.Font("Comic Sans MS", 1, 18)); // NOI18N
        judulPC1.setForeground(new java.awt.Color(255, 255, 255));
        judulPC1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        judulPC1.setText("ALARM");
        panelMenuAlarm.add(judulPC1, new org.netbeans.lib.awtextra.AbsoluteConstraints(1, 20, 200, 50));

        head3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/head.png"))); // NOI18N
        panelMenuAlarm.add(head3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 20, -1, 50));

        panelListAlarm.setBackground(new java.awt.Color(21, 21, 21));
        panelListAlarm.setPreferredSize(new java.awt.Dimension(740, 420));
        panelListAlarm.setLayout(new java.awt.BorderLayout());

        jScrollPaneAlarm.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPaneAlarm.setMaximumSize(new java.awt.Dimension(740, 32767));
        jScrollPaneAlarm.setPreferredSize(new java.awt.Dimension(740, 100));
        jScrollPaneAlarm.setViewportView(panelWadahSlot);

        panelWadahSlot.setBackground(new java.awt.Color(30, 30, 30));
        panelWadahSlot.setMaximumSize(new java.awt.Dimension(740, 32767));
        panelWadahSlot.setPreferredSize(new java.awt.Dimension(740, 420));
        panelWadahSlot.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 10, 10));
        jScrollPaneAlarm.setViewportView(panelWadahSlot);

        panelListAlarm.add(jScrollPaneAlarm, java.awt.BorderLayout.CENTER);

        panelMenuAlarm.add(panelListAlarm, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, 740, 420));

        btnKeTransaksi.setBackground(new java.awt.Color(255, 153, 0));
        btnKeTransaksi.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        btnKeTransaksi.setForeground(new java.awt.Color(255, 255, 255));
        btnKeTransaksi.setText("+ Alarm");
        btnKeTransaksi.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnKeTransaksi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKeTransaksiActionPerformed(evt);
            }
        });
        panelMenuAlarm.add(btnKeTransaksi, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 40, 140, -1));

        lblHtnSlt.setForeground(new java.awt.Color(255, 255, 255));
        lblHtnSlt.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        panelMenuAlarm.add(lblHtnSlt, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 10, 140, 20));

        panelUtama.add(panelMenuAlarm, "menu_alarm");

        getContentPane().add(panelUtama, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 80, 790, 620));
        getContentPane().add(lblBackground, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1000, 700));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnBerandaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnBerandaMouseClicked
        // TODO add your handling code here:
        pindahMenu("menu_beranda");
    }//GEN-LAST:event_btnBerandaMouseClicked

    private void btnPCMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPCMouseClicked
        // TODO add your handling code here:
        pindahMenu("menu_pc");
    }//GEN-LAST:event_btnPCMouseClicked

    private void btnPelangganMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPelangganMouseClicked
        // TODO add your handling code here:
        pindahMenu("menu_pelanggan");
    }//GEN-LAST:event_btnPelangganMouseClicked

    private void btnTransaksiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnTransaksiMouseClicked
        // TODO add your handling code here:
        pindahMenu("menu_transaksi");
    }//GEN-LAST:event_btnTransaksiMouseClicked

    private void btnAlarmMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAlarmMouseClicked
        // TODO add your handling code here:
        pindahMenu("menu_alarm");
    }//GEN-LAST:event_btnAlarmMouseClicked

    private void btnBerandaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnBerandaMouseEntered
        // TODO add your handling code here:
        btnBeranda.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/btn_beranda_on.png")));
        // Repaint memastikan gambar berubah instan tanpa jeda
        btnBeranda.repaint();
    }//GEN-LAST:event_btnBerandaMouseEntered

    private void btnBerandaMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnBerandaMouseExited
        // TODO add your handling code here:
        btnBeranda.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/btn_beranda_off.png")));
        btnBeranda.repaint();
    }//GEN-LAST:event_btnBerandaMouseExited

    private void btnPCMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPCMouseEntered
        // TODO add your handling code here:
        btnPC.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/btn_pc_on.png")));
        // Repaint memastikan gambar berubah instan tanpa jeda
        btnPC.repaint();
    }//GEN-LAST:event_btnPCMouseEntered

    private void btnPCMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPCMouseExited
        // TODO add your handling code here:
        btnPC.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/btn_pc_off.png")));
        btnPC.repaint();
    }//GEN-LAST:event_btnPCMouseExited

    private void btnPelangganMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPelangganMouseEntered
        // TODO add your handling code here:
        btnPelanggan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/btn_pelanggan_on.png")));
        // Repaint memastikan gambar berubah instan tanpa jeda
        btnPelanggan.repaint();
    }//GEN-LAST:event_btnPelangganMouseEntered

    private void btnPelangganMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPelangganMouseExited
        // TODO add your handling code here:
        btnPelanggan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/btn_pelanggan_off.png")));
        btnPelanggan.repaint();
    }//GEN-LAST:event_btnPelangganMouseExited

    private void btnTransaksiMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnTransaksiMouseEntered
        // TODO add your handling code here:
        btnTransaksi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/btn_transaksi_on.png")));
        // Repaint memastikan gambar berubah instan tanpa jeda
        btnTransaksi.repaint();
    }//GEN-LAST:event_btnTransaksiMouseEntered

    private void btnTransaksiMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnTransaksiMouseExited
        // TODO add your handling code here:
        btnTransaksi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/btn_transaksi_off.png")));
        btnTransaksi.repaint();
    }//GEN-LAST:event_btnTransaksiMouseExited

    private void btnAlarmMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAlarmMouseEntered
        // TODO add your handling code here:
        btnAlarm.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/btn_alarm_on.png")));
        // Repaint memastikan gambar berubah instan tanpa jeda
        btnAlarm.repaint();
    }//GEN-LAST:event_btnAlarmMouseEntered

    private void btnAlarmMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAlarmMouseExited
        // TODO add your handling code here:
        btnAlarm.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/btn_alarm_off.png")));
        btnAlarm.repaint();
    }//GEN-LAST:event_btnAlarmMouseExited

    private void txtPCCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPCCariActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPCCariActionPerformed

    private void txtTarifActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTarifActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTarifActionPerformed

    private void txtIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIDActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIDActionPerformed

    private void txtMerekActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMerekActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMerekActionPerformed

    private void tabelPCMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelPCMouseClicked
        // TODO add your handling code here:
        int baris = tabelPC.getSelectedRow();
    
        // Penjaga: Jika baris yang diklik valid (bukan -1) 
        // dan datanya tidak kosong
        if (baris != -1 && tabelPC.getValueAt(baris, 0) != null) {
            try {
                txtID.setText(tabelPC.getValueAt(baris, 0).toString());
                txtMerek.setText(tabelPC.getValueAt(baris, 1).toString());
                txtProcessor.setText(tabelPC.getValueAt(baris, 2).toString());
                txtTarif.setText(tabelPC.getValueAt(baris, 3).toString());
            } catch (Exception e) {
                // Jika ada error tipe data, aplikasi tidak akan crash
                System.out.println("Error klik tabel: " + e.getMessage());
            }
        }
    }//GEN-LAST:event_tabelPCMouseClicked

    private void btnDaftarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDaftarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnDaftarActionPerformed

    private void btnDaftarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnDaftarMouseClicked
        // TODO add your handling code here:
        // Validasi: Cek apakah SEMUA field kosong (0/4)
        if (txtID.getText().isEmpty() && txtMerek.getText().isEmpty() && 
            txtProcessor.getText().isEmpty() && txtTarif.getText().isEmpty()) {

            // Jika 0/4 diisi, munculkan peringatan
            JOptionPane.showMessageDialog(this, "Minimal isi salah satu data (ID, Merek, Processor, atau Tarif)!", "Peringatan", JOptionPane.WARNING_MESSAGE);

        } else {
            // Jika minimal 1/4 diisi, data masuk ke tabel
            DefaultTableModel model = (DefaultTableModel) tabelPC.getModel();
            // --- PROSES RUPIAH ---
            String tarifInput = txtTarif.getText();
            String tarifTampilan;

            try {
                // Cek apakah tarif diisi angka. Jika iya, format ke Rupiah
                int angkaTarif = Integer.parseInt(tarifInput);
                tarifTampilan = toRupiah(angkaTarif); 
            } catch (Exception e) {
                // Jika tarif kosong atau bukan angka, biarkan sesuai input user
                tarifTampilan = tarifInput;
            }

            model.addRow(new Object[]{
                txtID.getText(), 
                txtMerek.getText(), 
                txtProcessor.getText(), 
                tarifTampilan // <-- Sekarang tampilannya cantik "Rp. 5.000"
            });

            JOptionPane.showMessageDialog(this, "Data PC Berhasil Didaftarkan!", "Sukses", JOptionPane.INFORMATION_MESSAGE);

            // Opsional: Kosongkan field setelah daftar agar bisa input data baru
            btnBatalMouseClicked(null); 
        }
    }//GEN-LAST:event_btnDaftarMouseClicked

    private void btnEditMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEditMouseClicked
        // TODO add your handling code here:
        int i = tabelPC.getSelectedRow();
        DefaultTableModel model = (DefaultTableModel) tabelPC.getModel();

        if (i >= 0) {
            model.setValueAt(txtID.getText(), i, 0);
            model.setValueAt(txtMerek.getText(), i, 1);
            model.setValueAt(txtProcessor.getText(), i, 2);
            model.setValueAt(txtTarif.getText(), i, 3);

            JOptionPane.showMessageDialog(this, "Data PC Berhasil Diperbarui!", "Update Sukses", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Pilih baris yang ingin diedit di tabel terlebih dahulu!", "Kesalahan", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnEditMouseClicked

    private void btnHapusMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnHapusMouseClicked
        // TODO add your handling code here:
        int i = tabelPC.getSelectedRow();
        DefaultTableModel model = (DefaultTableModel) tabelPC.getModel();

        if (i >= 0) {
            // Tanya konfirmasi
            int tanya = JOptionPane.showConfirmDialog(this, "Yakin ingin menghapus data PC ini?", "Konfirmasi Hapus", JOptionPane.YES_NO_OPTION);

            if (tanya == JOptionPane.YES_OPTION) {
                model.removeRow(i);
                JOptionPane.showMessageDialog(this, "Data Berhasil Dihapus.");
                btnBatalMouseClicked(null);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Pilih data yang mau dihapus!");
        }
    }//GEN-LAST:event_btnHapusMouseClicked

    private void btnBatalMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnBatalMouseClicked
        // TODO add your handling code here:
        txtID.setText("");
        txtMerek.setText("");
        txtProcessor.setText("");
        txtTarif.setText("");
        tabelPC.clearSelection();
    }//GEN-LAST:event_btnBatalMouseClicked

    private void btnPilihMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPilihMouseClicked
        // TODO add your handling code here:
        int row = tabelPC.getSelectedRow(); // Pastikan nama tabel PC kamu benar
        if (row != -1) {
            // Ambil data Merek dan Tarif dari tabel PC
            String merek = tabelPC.getValueAt(row, 1).toString();
            String tarif = tabelPC.getValueAt(row, 3).toString();

            // Panggil fungsi yang sudah kamu buat tadi
            setDataPC(merek, tarif);

            // Pindah ke tampilan transaksi
            java.awt.CardLayout cl = (java.awt.CardLayout) panelUtama.getLayout();
            cl.show(panelUtama, "menu_transaksi"); 

        } else {
            JOptionPane.showMessageDialog(this, "Silakan pilih PC dari tabel dulu!");
        }
    }//GEN-LAST:event_btnPilihMouseClicked

    private void txtTarifKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTarifKeyTyped
        // TODO add your handling code here:
        char c = evt.getKeyChar();
    
        // Cek jika yang diketik bukan angka DAN bukan tombol hapus
        if (!Character.isDigit(c) && c != java.awt.event.KeyEvent.VK_BACK_SPACE) {
            evt.consume(); // Hapus inputannya

            // Munculkan pesan peringatan
            JOptionPane.showMessageDialog(this, "Input salah! Kolom Tarif hanya boleh diisi dengan angka.", "Peringatan Input", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_txtTarifKeyTyped

    private void txtPlIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPlIDActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPlIDActionPerformed

    private void txtPlNamaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPlNamaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPlNamaActionPerformed

    private void btnPlDaftarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPlDaftarMouseClicked
        // TODO add your handling code here:
        String nama = txtPlNama.getText();
        String telp = txtPlTelpon.getText();
        int indexGender = cmbPlGender.getSelectedIndex();

        if (nama.isEmpty() || telp.isEmpty() || indexGender == 0) {
            JOptionPane.showMessageDialog(this, "Nama, No. Telpon, dan Gender wajib diisi!");
        } 
        // CEK DUPLIKAT BERDASARKAN NAMA & TELP
        else if (isPelangganExist(nama, telp)) { 
            JOptionPane.showMessageDialog(this, "Pelanggan dengan Nama dan No. Telpon ini sudah terdaftar!", "Data Ganda", JOptionPane.ERROR_MESSAGE);
        } 
        else if (telp.length() < 11) {
            JOptionPane.showMessageDialog(this, "Nomor Telpon minimal 11 angka!");
        } 
        else {
            // PROSES SIMPAN...
            DefaultTableModel model = (DefaultTableModel) tabelPL.getModel();
            model.addRow(new Object[]{
                txtPlID.getText(),
                nama,
                cmbPlGender.getSelectedItem().toString(),
                telp,
                txtPlAlamat.getText(),
                txtPlEmail.getText()
            });
            JOptionPane.showMessageDialog(this, "Pelanggan Berhasil Didaftarkan!");
            
                urutPelanggan++; 
                generateID("PL", "PELANGGAN");
            btnPlBatalMouseClicked(null); 
        }
       
    }//GEN-LAST:event_btnPlDaftarMouseClicked

    private void btnPlDaftarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPlDaftarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnPlDaftarActionPerformed

    private void btnPlEditMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPlEditMouseClicked
        // TODO add your handling code here:
        int i = tabelPL.getSelectedRow();
        DefaultTableModel model = (DefaultTableModel) tabelPL.getModel();

        if (i != -1) {
            model.setValueAt(txtPlID.getText(), i, 0);
            model.setValueAt(txtPlNama.getText(), i, 1);
            model.setValueAt(cmbPlGender.getSelectedItem().toString(), i, 2);
            model.setValueAt(txtPlTelpon.getText(), i, 3);
            model.setValueAt(txtPlAlamat.getText(), i, 4);
            model.setValueAt(txtPlEmail.getText(), i, 5);

            JOptionPane.showMessageDialog(this, "Data Pelanggan Berhasil Diperbarui!");
        } else {
            JOptionPane.showMessageDialog(this, "Pilih pelanggan di tabel yang ingin diedit!");
        }
    }//GEN-LAST:event_btnPlEditMouseClicked

    private void btnPlHapusMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPlHapusMouseClicked
        // TODO add your handling code here:
        int i = tabelPL.getSelectedRow();
        DefaultTableModel model = (DefaultTableModel) tabelPL.getModel();

        if (i != -1) {
            int konfirmasi = JOptionPane.showConfirmDialog(this, "Hapus data pelanggan: " + txtPlNama.getText() + "?", "Konfirmasi Hapus", JOptionPane.YES_NO_OPTION);

            if (konfirmasi == JOptionPane.YES_OPTION) {
                model.removeRow(i);
                JOptionPane.showMessageDialog(this, "Data telah dihapus.");
                btnPlBatalMouseClicked(null);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Pilih data yang ingin dihapus pada tabel!");
        }
    }//GEN-LAST:event_btnPlHapusMouseClicked

    private void tabelPLMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelPLMouseClicked
        // TODO add your handling code here:
        int i = tabelPL.getSelectedRow();
        if (i != -1 && tabelPL.getValueAt(i, 0) != null) {
            txtPlID.setText(tabelPL.getValueAt(i, 0).toString());
            txtPlNama.setText(tabelPL.getValueAt(i, 1).toString());
            cmbPlGender.setSelectedItem(tabelPL.getValueAt(i, 2).toString());
            txtPlTelpon.setText(tabelPL.getValueAt(i, 3).toString());
            txtPlAlamat.setText(tabelPL.getValueAt(i, 4).toString());
            txtPlEmail.setText(tabelPL.getValueAt(i, 5).toString());
        }
    }//GEN-LAST:event_tabelPLMouseClicked

    private void txtPlCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPlCariActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPlCariActionPerformed

    private void btnPlPilihMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPlPilihMouseClicked
        // TODO add your handling code here:
        int row = tabelPL.getSelectedRow();
        if (row != -1) {
            String nama = tabelPL.getValueAt(row, 1).toString();
            String telp = tabelPL.getValueAt(row, 3).toString();

            // 1. Karena satu file, panggil fungsinya langsung!
            setDataPelanggan(nama, telp);

            // 2. Pindah tampilan CardLayout
            java.awt.CardLayout cl = (java.awt.CardLayout) panelUtama.getLayout();
            cl.show(panelUtama, "menu_transaksi"); 

        } else {
            JOptionPane.showMessageDialog(this, "Silakan pilih pelanggan dari tabel!");
        }
    }//GEN-LAST:event_btnPlPilihMouseClicked

    private void btnPlBatalMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPlBatalMouseClicked
        // TODO add your handling code here:
        txtPlID.setText("");
        txtPlNama.setText("");
        txtPlTelpon.setText("");
        txtPlAlamat.setText("");
        txtPlEmail.setText("");
        cmbPlGender.setSelectedIndex(0);
                generateID("PL", "PELANGGAN");
        tabelPL.clearSelection();
 
    }//GEN-LAST:event_btnPlBatalMouseClicked

    private void txtPlAlamatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPlAlamatActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPlAlamatActionPerformed

    private void txtPlAlamatKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPlAlamatKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPlAlamatKeyTyped

    private void txtPlTelponActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPlTelponActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPlTelponActionPerformed

    private void txtPlTelponKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPlTelponKeyTyped
        // TODO add your handling code here:
        char c = evt.getKeyChar();
        if (!Character.isDigit(c) && c != java.awt.event.KeyEvent.VK_BACK_SPACE) {
            evt.consume();
            
            // Munculkan pesan peringatan
            JOptionPane.showMessageDialog(this, "Input salah! Kolom Tarif hanya boleh diisi dengan angka.", "Peringatan Input", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_txtPlTelponKeyTyped

    private void txtPlCariKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPlCariKeyReleased
        // TODO add your handling code here:
        DefaultTableModel model = (DefaultTableModel) tabelPL.getModel();
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
        tabelPL.setRowSorter(sorter);

        // Ini akan mencari di semua kolom (ID, Nama, Telpon, dll) sekaligus
        sorter.setRowFilter(RowFilter.regexFilter("(?i)" + txtPlCari.getText()));
    }//GEN-LAST:event_txtPlCariKeyReleased

    private void txtPCCariKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPCCariKeyReleased
        // TODO add your handling code here:
        DefaultTableModel model = (DefaultTableModel) tabelPC.getModel();
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
        tabelPC.setRowSorter(sorter);

        // "(?i)" artinya Case Insensitive (tidak peduli huruf besar/kecil)
        sorter.setRowFilter(RowFilter.regexFilter("(?i)" + txtPCCari.getText()));
    }//GEN-LAST:event_txtPCCariKeyReleased

    private void txtTrDtkMulaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTrDtkMulaiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTrDtkMulaiActionPerformed

    private void btnTrDaftarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnTrDaftarMouseClicked
        // TODO add your handling code here:
        // 1. Ambil ID yang mau didaftarkan
        String idBaru = txtTrID.getText();
        DefaultTableModel model = (DefaultTableModel) tabelTr.getModel();

        // 2. LOGIKA IDENTIFIKASI: Cek ke seluruh baris tabel
        boolean idSudahAda = false;
        for (int i = 0; i < model.getRowCount(); i++) {
            String idDiTabel = model.getValueAt(i, 0).toString();
            if (idBaru.equals(idDiTabel)) {
                idSudahAda = true;
                break; // Berhenti cari kalau sudah ketemu yang sama
            }
        }

        // 3. EKSEKUSI BERDASARKAN HASIL CEK
        if (idSudahAda) {
            JOptionPane.showMessageDialog(this, "ID Transaksi " + idBaru + " sudah ada!\nRefresh untuk ID Baru klik tombol (X) Batal.", "Peringatan", JOptionPane.WARNING_MESSAGE);
        } else {
            // PROSES TAMBAH DATA (Jika ID belum ada)
            if (txtTrNama.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Nama tidak boleh kosong!");
                return;
            }

            try {
                String tgl = ((javax.swing.JTextField)jdTrTanggal.getDateEditor().getUiComponent()).getText();
                
                // Gunakan fungsi formatDuaDigit untuk merapikan inputan admin
                String jamM = formatDuaDigit(txtTrJamMulai.getText()) + ":" + 
                              formatDuaDigit(txtTrMntMulai.getText()) + ":" + 
                              formatDuaDigit(txtTrDtkMulai.getText());
                
                String jamS = formatDuaDigit(txtTrJamSelesai.getText()) + ":" + 
                              formatDuaDigit(txtTrMntSelesai.getText()) + ":" + 
                              formatDuaDigit(txtTrDtkSelesai.getText());
                
                String durasi = formatDuaDigit(txtTrJamDurasi.getText()) + ":" + 
                                formatDuaDigit(txtTrMntDurasi.getText()) + ":" + 
                                formatDuaDigit(txtTrDtkDurasi.getText());

                // Masukkan semua field ke tabel
                model.addRow(new Object[]{
                    idBaru, 
                    tgl, 
                    txtTrNama.getText(), 
                    txtTrMerek.getText(), 
                    txtTrTarif.getText(), 
                    jamM, 
                    jamS, 
                    durasi, 
                    txtTrBiaya.getText(),
                    txtTrTelpon.getText()
                });

                JOptionPane.showMessageDialog(this, "Data Berhasil Terdaftar!");

                // 4. Update nomorUrut & Reset ID
                urutTransaksi++; 
                generateID("TR", "TRANSAKSI");

                // 5. Bersihkan field (otomatis manggil reset form)
                btnTrBatalActionPerformed(null);

            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Terjadi kesalahan: " + e.getMessage());
            }
        }
    }//GEN-LAST:event_btnTrDaftarMouseClicked

    private void btnTrEditMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnTrEditMouseClicked
        // TODO add your handling code here:
        int row = tabelTr.getSelectedRow();
    
        // 1. CEK APAKAH ADA BARIS YANG DIPILIH
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Silakan pilih data di tabel yang ingin diedit!");
            return;
        }

        // 2. VALIDASI INPUT (Jangan sampai ada field penting yang kosong)
        if (txtTrNama.getText().isEmpty() || txtTrTarif.getText().isEmpty() || txtTrBiaya.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Data (Nama/Tarif/Biaya) tidak boleh kosong!");
            return; 
        }

        // 3. JIKA LOLOS VALIDASI, BARU PROSES UPDATE
        try {
            DefaultTableModel model = (DefaultTableModel) tabelTr.getModel();

            // Update Kolom Identitas & PC
            model.setValueAt(txtTrID.getText(), row, 0);
            model.setValueAt(((javax.swing.JTextField)jdTrTanggal.getDateEditor().getUiComponent()).getText(), row, 1);
            model.setValueAt(txtTrNama.getText(), row, 2);
            model.setValueAt(txtTrMerek.getText(), row, 3);
            model.setValueAt(txtTrTarif.getText(), row, 4);

            // Gabungkan Waktu
            String jamM = txtTrJamMulai.getText() + ":" + txtTrMntMulai.getText() + ":" + txtTrDtkMulai.getText();
            String jamS = txtTrJamSelesai.getText() + ":" + txtTrMntSelesai.getText() + ":" + txtTrDtkSelesai.getText();
            String durasi = txtTrJamDurasi.getText() + ":" + txtTrMntDurasi.getText() + ":" + txtTrDtkDurasi.getText();

            // Update Kolom Waktu & Biaya
            model.setValueAt(jamM, row, 5);
            model.setValueAt(jamS, row, 6);
            model.setValueAt(durasi, row, 7);
            model.setValueAt(txtTrBiaya.getText(), row, 8);

            JOptionPane.showMessageDialog(this, "Data Berhasil Diperbarui!");

            // 4. RESET TAMPILAN (Kembali ke ID Antrean Baru)
            btnTrBatalActionPerformed(null); 

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Terjadi kesalahan saat update: " + e.getMessage());
        }
    }//GEN-LAST:event_btnTrEditMouseClicked

    private void btnTrHapusMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnTrHapusMouseClicked
        // TODO add your handling code here:
        int row = tabelTr.getSelectedRow();

        if (row != -1) {
            int confirm = JOptionPane.showConfirmDialog(this, "Yakin ingin menghapus data ini?", "Konfirmasi", JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                DefaultTableModel model = (DefaultTableModel) tabelTr.getModel();
                model.removeRow(row);

                JOptionPane.showMessageDialog(this, "Data Berhasil Dihapus!");

                // Panggil si penyelamat kita buat reset ID ke antrean baru
                btnTrBatalActionPerformed(null); 
            }
        } else {
            JOptionPane.showMessageDialog(this, "Pilih data di tabel yang ingin dihapus!");
        }
    }//GEN-LAST:event_btnTrHapusMouseClicked

    private void tabelTrMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelTrMouseClicked
        // TODO add your handling code here:                                   
            int baris = tabelTr.getSelectedRow();

            // Pastikan baris yang diklik ada datanya
            if (baris != -1 && tabelTr.getValueAt(baris, 0) != null) {

            // 1. Tarik Data Utama (ID, Nama, Merek, Tarif, Biaya)
            txtTrID.setText(tabelTr.getValueAt(baris, 0).toString());
            txtTrNama.setText(tabelTr.getValueAt(baris, 2).toString());
            txtTrMerek.setText(tabelTr.getValueAt(baris, 3).toString());
            txtTrTarif.setText(tabelTr.getValueAt(baris, 4).toString());
            txtTrBiaya.setText(tabelTr.getValueAt(baris, 8).toString());
            // TARIK NO TELPON DARI KOLOM RAHASIA (INDEX 9)
            txtTrTelpon.setText(tabelTr.getValueAt(baris, 9).toString());

            // 2. Tarik & Set Tanggal
            try {
                java.util.Date tgl = new java.text.SimpleDateFormat("dd-MM-yyyy").parse(tabelTr.getValueAt(baris, 1).toString());
                jdTrTanggal.setDate(tgl);
            } catch (Exception e) { }

            // 3. Pecah Jam Mulai (Logika Anti-Error)
            String dataJamM = tabelTr.getValueAt(baris, 5).toString();
            if (dataJamM.contains(":")) {
                String[] jM = dataJamM.split(":");
                txtTrJamMulai.setText(jM[0]);
                txtTrMntMulai.setText(jM.length > 1 ? jM[1] : "00");
                txtTrDtkMulai.setText(jM.length > 2 ? jM[2] : "00");
            } else {
                txtTrJamMulai.setText(dataJamM);
                txtTrMntMulai.setText("00");
                txtTrDtkMulai.setText("00");
            }

            // 4. Pecah Jam Selesai (Logika Anti-Error)
            String dataJamS = tabelTr.getValueAt(baris, 6).toString();
            if (dataJamS.contains(":")) {
                String[] jS = dataJamS.split(":");
                txtTrJamSelesai.setText(jS[0]);
                txtTrMntSelesai.setText(jS.length > 1 ? jS[1] : "00");
                txtTrDtkSelesai.setText(jS.length > 2 ? jS[2] : "00");
            } else {
                txtTrJamSelesai.setText(dataJamS);
                txtTrMntSelesai.setText("00");
                txtTrDtkSelesai.setText("00");
            }

            // 5. Pecah Durasi (Logika Anti-Error)
            String dataDur = tabelTr.getValueAt(baris, 7).toString();
            if (dataDur.contains(":")) {
                String[] dur = dataDur.split(":");
                txtTrJamDurasi.setText(dur[0]);
                txtTrMntDurasi.setText(dur.length > 1 ? dur[1] : "00");
                txtTrDtkDurasi.setText(dur.length > 2 ? dur[2] : "00");
            } else {
                txtTrJamDurasi.setText(dataDur);
                txtTrMntDurasi.setText("00");
                txtTrDtkDurasi.setText("00");
            }

            // Kunci ID biar gak diedit manual
            txtTrID.setEditable(false);
        }
      
    }//GEN-LAST:event_tabelTrMouseClicked

    private void txtTrCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTrCariActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTrCariActionPerformed

    private void txtTrCariKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTrCariKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTrCariKeyReleased

    private void btnPilih1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPilih1MouseClicked
        // TODO add your handling code here:
        // 1. Ambil baris yang dipilih di tabel transaksi
        int baris = tabelTr.getSelectedRow();

        if (baris != -1) {
            // 2. Ambil data dari kolom-kolom tabel
            
            String id = tabelTr.getValueAt(baris, 0).toString();
            String nama = tabelTr.getValueAt(baris, 2).toString();
            String pc = tabelTr.getValueAt(baris, 3).toString();
            String jamS = tabelTr.getValueAt(baris, 6).toString(); // Jam Selesai

            // 3. Panggil fungsi sakti pembuat slot
            buatSlotAlarmOtomatis(id, nama, pc, jamS);

            // 4. Teleport ke Laman Alarm
            CardLayout cl = (CardLayout) panelUtama.getLayout();
            cl.show(panelUtama, "menu_alarm"); 

            JOptionPane.showMessageDialog(this, "Alarm untuk " + nama + " berhasil diaktifkan!");

        } else {
            JOptionPane.showMessageDialog(this, "Klik dulu data transaksinya di tabel!");
        }
    }//GEN-LAST:event_btnPilih1MouseClicked

    private void txtTrIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTrIDActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTrIDActionPerformed

    private void txtTrJamMulaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTrJamMulaiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTrJamMulaiActionPerformed

    private void txtTrMntMulaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTrMntMulaiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTrMntMulaiActionPerformed

    private void txtTrJamSelesaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTrJamSelesaiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTrJamSelesaiActionPerformed

    private void txtTrMntSelesaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTrMntSelesaiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTrMntSelesaiActionPerformed

    private void txtTrDtkSelesaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTrDtkSelesaiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTrDtkSelesaiActionPerformed

    private void txtTrJamDurasiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTrJamDurasiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTrJamDurasiActionPerformed

    private void txtTrMntDurasiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTrMntDurasiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTrMntDurasiActionPerformed

    private void txtTrDtkDurasiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTrDtkDurasiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTrDtkDurasiActionPerformed

    private void txtTrJamMulaiKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTrJamMulaiKeyTyped
        // TODO add your handling code here:
        satpamInput(evt, txtTrJamMulai, 23);
    }//GEN-LAST:event_txtTrJamMulaiKeyTyped

    private void txtTrMntMulaiKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTrMntMulaiKeyTyped
        // TODO add your handling code here:
        satpamInput(evt, txtTrMntMulai, 59);
    }//GEN-LAST:event_txtTrMntMulaiKeyTyped

    private void txtTrDtkMulaiKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTrDtkMulaiKeyTyped
        // TODO add your handling code here:
        satpamInput(evt, txtTrDtkMulai, 59);
    }//GEN-LAST:event_txtTrDtkMulaiKeyTyped

    private void txtTrJamSelesaiKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTrJamSelesaiKeyTyped
        // TODO add your handling code here:
        satpamInput(evt, txtTrJamSelesai, 23);
    }//GEN-LAST:event_txtTrJamSelesaiKeyTyped

    private void txtTrMntSelesaiKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTrMntSelesaiKeyTyped
        // TODO add your handling code here:
        satpamInput(evt, txtTrMntSelesai, 59);
    }//GEN-LAST:event_txtTrMntSelesaiKeyTyped

    private void txtTrDtkSelesaiKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTrDtkSelesaiKeyTyped
        // TODO add your handling code here:
        satpamInput(evt, txtTrDtkSelesai, 59);
    }//GEN-LAST:event_txtTrDtkSelesaiKeyTyped

    private void txtTrMntDurasiKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTrMntDurasiKeyTyped
        // TODO add your handling code here:
        satpamInput(evt, txtTrMntDurasi, 59);
    }//GEN-LAST:event_txtTrMntDurasiKeyTyped

    private void txtTrDtkDurasiKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTrDtkDurasiKeyTyped
        // TODO add your handling code here:
        satpamInput(evt, txtTrDtkDurasi, 59);
    }//GEN-LAST:event_txtTrDtkDurasiKeyTyped

    private void btnTrDurasiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTrDurasiActionPerformed
        // TODO add your handling code here:
        // 1. CEK KELENGKAPAN JAM MULAI
        if (txtTrJamMulai.getText().isEmpty() || txtTrMntMulai.getText().isEmpty() || txtTrDtkMulai.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Jam Mulai tidak lengkap!");
            return;
        }

        // 2. CEK KELENGKAPAN DURASI
        if (txtTrJamDurasi.getText().isEmpty() || txtTrMntDurasi.getText().isEmpty() || txtTrDtkDurasi.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Durasi tidak lengkap!");
            return;
        }

        // 3. CEK APAKAH PC SUDAH DIPILIH (Cek tarif kosong)
        if (txtTrTarif.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Pilih PC yang digunakan!");
            // Opsional: Langsung lempar user ke menu PC kalau mau
            return;
        }

        // 4. JIKA SEMUA LENGKAP, BARU PROSES HITUNG
        try {
            long mulai = keDetik(txtTrJamMulai.getText(), txtTrMntMulai.getText(), txtTrDtkMulai.getText());

            long jamD = Long.parseLong(txtTrJamDurasi.getText());
            long mntD = Long.parseLong(txtTrMntDurasi.getText());
            long dtkD = Long.parseLong(txtTrDtkDurasi.getText());

            long durasiDetik = (jamD * 3600) + (mntD * 60) + dtkD;

            long selesai = mulai + durasiDetik;

            if (selesai > 86399) { 
                selesai = 86399;
                JOptionPane.showMessageDialog(this, "Durasi melebihi hari ini. Jam Selesai diset ke 23:59:59");
            }

            keField(selesai, txtTrJamSelesai, txtTrMntSelesai, txtTrDtkSelesai);

            // --- PROSES PEMBERSIH & HITUNG BIAYA ---
    
            // A. Bersihkan tarif (Unformat) dari "Rp. 5.000" jadi 5000
            String tarifBersih = txtTrTarif.getText().replaceAll("[^0-9]", "");
            int tarifPerJam = Integer.parseInt(tarifBersih);

            // B. Hitung Biaya secara dinamis
            int biaya = (int) ((double) durasiDetik / 3600 * tarifPerJam);

            // C. Tampilkan Biaya dengan format Rupiah (Bungkus)
            txtTrBiaya.setText(toRupiah(biaya));
            
            // JURUS BIAR ENTENG: Paksa UI buat update tampilan
            txtTrBiaya.repaint();
            txtTrJamSelesai.repaint();

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Input harus berupa angka!");
        }
    }//GEN-LAST:event_btnTrDurasiActionPerformed

    private void btnTrJamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTrJamActionPerformed
        // TODO add your handling code here:
        // 1. VALIDASI
        if (txtTrJamMulai.getText().isEmpty() || txtTrMntMulai.getText().isEmpty() || txtTrDtkMulai.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Jam Mulai belum diisi!");
            return;
        }
        if (txtTrJamSelesai.getText().isEmpty() || txtTrMntSelesai.getText().isEmpty() || txtTrDtkSelesai.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Jam Selesai belum diisi!");
            return;
        }
        if (txtTrTarif.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Pilih PC untuk menentukan tarif!");
            return;
        }

        // 2. PROSES HITUNG
        try {
            long mulai = keDetik(txtTrJamMulai.getText(), txtTrMntMulai.getText(), txtTrDtkMulai.getText());
            long selesai = keDetik(txtTrJamSelesai.getText(), txtTrMntSelesai.getText(), txtTrDtkSelesai.getText());

            if (selesai < mulai) {
                JOptionPane.showMessageDialog(this, "Jam Selesai tidak boleh sebelum Jam Mulai!");
                return;
            }

            long selisih = selesai - mulai;
            keField(selisih, txtTrJamDurasi, txtTrMntDurasi, txtTrDtkDurasi);

            // --- PROSES PEMBERSIH & HITUNG BIAYA ---

            // A. Bersihkan tarif dari format Rp. (Unformat)
            String tarifBersih = txtTrTarif.getText().replaceAll("[^0-9]", "");
            int tarif = Integer.parseInt(tarifBersih);

            // B. Hitung Biaya
            int biaya = (int) ((double) selisih / 3600 * tarif);

            // C. Tampilkan Biaya dengan format Rupiah lagi (Bungkus)
            txtTrBiaya.setText(toRupiah(biaya));

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Pastikan input berupa angka!");
        }
    }//GEN-LAST:event_btnTrJamActionPerformed

    private void btnTrBiayaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTrBiayaActionPerformed
        // TODO add your handling code here:
        // 1. VALIDASI
        if (txtTrJamMulai.getText().isEmpty() || txtTrMntMulai.getText().isEmpty() || txtTrDtkMulai.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Isi Jam Mulai terlebih dahulu!");
            return;
        }
        if (txtTrBiaya.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Masukkan jumlah biaya/uang!");
            return;
        }
        if (txtTrTarif.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Pilih PC terlebih dahulu!");
            return;
        }

        // 2. PROSES HITUNG
        try {
            // --- JURUS PEMBERSIH (UNFORMAT) ---
            // Bersihkan Rp dan titik dari input Biaya dan Tarif
            String biayaBersih = txtTrBiaya.getText().replaceAll("[^0-9]", "");
            String tarifBersih = txtTrTarif.getText().replaceAll("[^0-9]", "");

            int uang = Integer.parseInt(biayaBersih);
            int tarif = Integer.parseInt(tarifBersih);

            long mulai = keDetik(txtTrJamMulai.getText(), txtTrMntMulai.getText(), txtTrDtkMulai.getText());

            // Cari durasi: (Uang / Tarif) * 3600 detik
            long durasi = (long) ((double) uang / tarif * 3600);
            long selesai = mulai + durasi;

            // Batasi jika tembus hari esok
            if (selesai > 86399) {
                selesai = 86399;
                JOptionPane.showMessageDialog(this, "Durasi mencapai batas maksimal hari ini (23:59:59)");
            }

            // Tampilkan hasil ke field durasi dan selesai
            keField(durasi, txtTrJamDurasi, txtTrMntDurasi, txtTrDtkDurasi);
            keField(selesai, txtTrJamSelesai, txtTrMntSelesai, txtTrDtkSelesai);

            // --- JURUS BUNGKUS ULANG ---
            // Setelah dihitung, format kembali txtTrBiaya agar muncul "Rp. 10.000"
            txtTrBiaya.setText(toRupiah(uang));

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Masukkan jumlah biaya dalam angka!");
        }
    }//GEN-LAST:event_btnTrBiayaActionPerformed

    private void btnTrBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTrBatalActionPerformed
        // TODO add your handling code here:
        // 1. Kosongkan semua JTextField
        txtTrNama.setText("");
        txtTrMerek.setText("");
        txtTrTarif.setText("");
        txtTrJamMulai.setText(""); txtTrMntMulai.setText(""); txtTrDtkMulai.setText("");
        txtTrJamSelesai.setText(""); txtTrMntSelesai.setText(""); txtTrDtkSelesai.setText("");
        txtTrJamDurasi.setText(""); txtTrMntDurasi.setText(""); txtTrDtkDurasi.setText("");
        txtTrBiaya.setText("");
        txtTrTelpon.setText(""); // Jangan lupa yang ini!

        // 2. Reset Tanggal ke hari ini
        jdTrTanggal.setDate(new java.util.Date());

        // 3. Panggil ID Otomatis lagi (biar dapet ID baru/antrean)
        generateID("TR", "TRANSAKSI");

        // 4. Kembalikan fokus ke Nama (biar admin tinggal ketik)
        txtTrNama.requestFocus();
        tabelTr.clearSelection();
    }//GEN-LAST:event_btnTrBatalActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        // 1. Pindah ke Laman C (Data Pelanggan)
        // Asumsi nama card Laman C kamu adalah "cardPelanggan"
        CardLayout cl = (CardLayout) panelUtama.getLayout();
        cl.show(panelUtama, "menu_pelanggan");

        // 2. Kasih pesan singkat biar admin nggak bingung
        JOptionPane.showMessageDialog(this, "Silahkan pilih pelanggan dalam tabel, lalu klik tombol kuning PILIH!");
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        // 1. Pindah ke Laman D (Data PC)
        // Asumsi nama card Laman D kamu adalah "cardPC"
        CardLayout cl = (CardLayout) panelUtama.getLayout();
        cl.show(panelUtama, "menu_pc");

        // 2. Kasih pesan singkat
        JOptionPane.showMessageDialog(this, "Silahkan pilih PC yang tersedia lalu, klik tombol kuning PILIH!");
    }//GEN-LAST:event_jButton5ActionPerformed

    private void btnKeTransaksiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKeTransaksiActionPerformed
        // TODO add your handling code here:
        // Teleport balik ke Laman Transaksi
        CardLayout cl = (CardLayout) panelUtama.getLayout();
        cl.show(panelUtama, "menu_transaksi");
        
        JOptionPane.showMessageDialog(this, "Silahkan pilih transaksi dalam tabel, lalu klik tombol kuning PILIH!");
    }//GEN-LAST:event_btnKeTransaksiActionPerformed

    private void btnLogoutMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLogoutMouseClicked
        // TODO add your handling code here:
        // 1. Cek apakah frame Login masih ada di memori
        if (Login.instance != null) {
            // beberes text sblmnya
            Login.instance.resetForm();
            
            // 2. Ubah label status yang ada di Frame Login
            Login.instance.setStatus("Status: Sesi Berakhir (Alarm Tetap Berjalan)");

            // 3. Tampilkan kembali Frame Login
            Login.instance.setVisible(true);

            // 4. Sembunyikan Beranda (JANGAN dispose() agar data alarm tidak hilang!)
            this.setVisible(false); 

            // Catatan: Karena Beranda cuma setVisible(false), 
            // semua Timer alarm di dalamnya tetap jalan di background.
        } else {
            // Jika karena suatu hal Login.instance hilang, baru buat baru
            new Login().setVisible(true);
            this.dispose();
        }
    }//GEN-LAST:event_btnLogoutMouseClicked

    private void btnCetakActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCetakActionPerformed
        // TODO add your handling code here:
        eksporKeExcel(tabelPC, "Data_PC_WarnetWFO");
    }//GEN-LAST:event_btnCetakActionPerformed

    private void btnPlCetakActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPlCetakActionPerformed
        // TODO add your handling code here:
        String tgl = new java.text.SimpleDateFormat("ddMMyy").format(new java.util.Date());
        eksporKeExcel(tabelPL, "Laporan_Pelanggan_WFO" + tgl);
    }//GEN-LAST:event_btnPlCetakActionPerformed

    private void btnCetakTrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCetakTrActionPerformed
        // TODO add your handling code here:
        int barisTerpilih = tabelTr.getSelectedRow();

        if (barisTerpilih == -1) {
            // SCENARIO A: Cetak Semua (Excel/CSV)
            int konfirmasi = JOptionPane.showConfirmDialog(null, 
                    "Tidak ada data dipilih. Cetak semua laporan transaksi ke Excel?", 
                    "Cetak Laporan", JOptionPane.YES_NO_OPTION);

            if (konfirmasi == JOptionPane.YES_OPTION) {
                eksporKeExcel(tabelTr, "Laporan_Transaksi_WFO_All");
            }
        } else {
            // SCENARIO B: Cetak Struk Personal (PDF)
            // Ambil ID dari kolom pertama (index 0) di baris yang diklik
            String idTerpilih = tabelTr.getValueAt(barisTerpilih, 0).toString();

            int konfirmasi = JOptionPane.showConfirmDialog(null, 
                    "Cetak Struk untuk ID: " + idTerpilih + "?", 
                    "Cetak Struk", JOptionPane.YES_NO_OPTION);

            if (konfirmasi == JOptionPane.YES_OPTION) {
                cetakStrukPDF(barisTerpilih);
            }
        }
    }//GEN-LAST:event_btnCetakTrActionPerformed
    
    //------------------------------------------------------------------di bawah event
    //buat menghindari duplikasi data di tabel pelanggan
    private boolean isPelangganExist(String namaBaru, String telpBaru) {
        for (int i = 0; i < tabelPL.getRowCount(); i++) {
            String namaDiTabel = tabelPL.getValueAt(i, 1).toString();
            String telpDiTabel = tabelPL.getValueAt(i, 3).toString();

            // Cek apakah Nama DAN No Telp sama persis dengan yang ada di tabel
            if (namaDiTabel.equalsIgnoreCase(namaBaru) && telpDiTabel.equals(telpBaru)) {
                return true; // Data orang ini sudah ada!
            }
        }
        return false;
    }
    
    // Fungsi mengubah Jam, Menit, Detik ke total Detik
    private long keDetik(String h, String m, String s) {
        try {
            long hh = h.isEmpty() ? 0 : Long.parseLong(h);
            long mm = m.isEmpty() ? 0 : Long.parseLong(m);
            long ss = s.isEmpty() ? 0 : Long.parseLong(s);

            // Penjaga: Menit dan Detik max 59
            if (mm > 59) mm = 59;
            if (ss > 59) ss = 59;

            // Penjaga: Jam max 23 (untuk Jam Mulai & Selesai)
            if (hh > 23) hh = 23;

            return (hh * 3600) + (mm * 60) + ss;
        } catch (Exception e) { return 0; }
    }

    // Fungsi memecah total Detik kembali ke 3 TextField
    private void keField(long totalDetik, javax.swing.JTextField fH, javax.swing.JTextField fM, javax.swing.JTextField fS) {
        long hh = totalDetik / 3600;
        long mm = (totalDetik % 3600) / 60;
        long ss = totalDetik % 60;
        fH.setText(String.valueOf(hh));
        fM.setText(String.valueOf(mm));
        fS.setText(String.valueOf(ss));
    }
    
    // Fungsi Satpam Universal untuk membatasi input angka pada menu Transaksi
    private void satpamInput(java.awt.event.KeyEvent evt, javax.swing.JTextField field, int maxNilai) {
        char c = evt.getKeyChar();
        String teksSekarang = field.getText();

        // 1. Cek apakah yang diketik itu angka
        if (!Character.isDigit(c)) {
            evt.consume();
            return;
        }

        // 2. Cek jika digabung dengan angka baru, apakah melebihi maxNilai atau lebih dari 2 digit
        try {
            String teksBaru = teksSekarang + c;
            int nilai = Integer.parseInt(teksBaru);

            if (nilai > maxNilai || teksBaru.length() > 2) {
                evt.consume();
            }
        } catch (Exception e) {
            evt.consume();
        }
    }
    
    //------------------------------------------------------------------------------------------------
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
            java.util.logging.Logger.getLogger(Beranda.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Beranda.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Beranda.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Beranda.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Beranda().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel anime;
    private javax.swing.JLabel btnAlarm;
    private javax.swing.JButton btnBatal;
    private javax.swing.JLabel btnBeranda;
    private javax.swing.JButton btnCetak;
    private javax.swing.JButton btnCetakTr;
    private javax.swing.JButton btnDaftar;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnHapus;
    private javax.swing.JButton btnKeTransaksi;
    private javax.swing.JLabel btnLogout;
    private javax.swing.JLabel btnPC;
    private javax.swing.JLabel btnPelanggan;
    private javax.swing.JLabel btnPilih;
    private javax.swing.JLabel btnPilih1;
    private javax.swing.JButton btnPlBatal;
    private javax.swing.JButton btnPlCetak;
    private javax.swing.JButton btnPlDaftar;
    private javax.swing.JButton btnPlEdit;
    private javax.swing.JButton btnPlHapus;
    private javax.swing.JLabel btnPlPilih;
    private javax.swing.JButton btnTrBatal;
    private javax.swing.JButton btnTrBiaya;
    private javax.swing.JButton btnTrDaftar;
    private javax.swing.JButton btnTrDurasi;
    private javax.swing.JButton btnTrEdit;
    private javax.swing.JButton btnTrHapus;
    private javax.swing.JButton btnTrJam;
    private javax.swing.JLabel btnTransaksi;
    private javax.swing.JComboBox<String> cmbPlGender;
    private javax.swing.JLabel head;
    private javax.swing.JLabel head1;
    private javax.swing.JLabel head2;
    private javax.swing.JLabel head3;
    private javax.swing.JLabel instagramlogo;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPaneAlarm;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextArea jTextArea2;
    private com.toedter.calendar.JDateChooser jdTrTanggal;
    private javax.swing.JLabel jdlTransaksi;
    private javax.swing.JLabel judulPC;
    private javax.swing.JLabel judulPC1;
    private javax.swing.JLabel judulPl;
    private javax.swing.JLabel lblAlamat;
    private javax.swing.JLabel lblAlamat1;
    private javax.swing.JLabel lblBackground;
    private javax.swing.JLabel lblHtnSlt;
    private javax.swing.JLabel lblTanggal;
    private javax.swing.JLabel lblTime;
    private javax.swing.JLabel lblWelcome;
    private javax.swing.JLabel logo;
    private javax.swing.JScrollPane paneText;
    private javax.swing.JPanel panelHeader;
    private javax.swing.JPanel panelKiri;
    private javax.swing.JPanel panelListAlarm;
    private javax.swing.JPanel panelMenuAlarm;
    private javax.swing.JPanel panelMenuBeranda;
    private javax.swing.JPanel panelMenuPC;
    private javax.swing.JPanel panelMenuPelanggan;
    public javax.swing.JPanel panelMenuTransaksi;
    public javax.swing.JPanel panelUtama;
    private javax.swing.JPanel panelWadahSlot;
    private javax.swing.JTable tabelPC;
    private javax.swing.JTable tabelPL;
    private javax.swing.JTable tabelTr;
    private javax.swing.JTextField txtID;
    private javax.swing.JTextField txtMerek;
    private javax.swing.JTextField txtPCCari;
    private javax.swing.JTextField txtPlAlamat;
    private javax.swing.JTextField txtPlCari;
    private javax.swing.JTextField txtPlEmail;
    private javax.swing.JTextField txtPlID;
    private javax.swing.JTextField txtPlNama;
    private javax.swing.JTextField txtPlTelpon;
    private javax.swing.JTextField txtProcessor;
    private javax.swing.JTextField txtTarif;
    private javax.swing.JTextField txtTrBiaya;
    private javax.swing.JTextField txtTrCari;
    private javax.swing.JTextField txtTrDtkDurasi;
    private javax.swing.JTextField txtTrDtkMulai;
    private javax.swing.JTextField txtTrDtkSelesai;
    private javax.swing.JTextField txtTrID;
    private javax.swing.JTextField txtTrJamDurasi;
    private javax.swing.JTextField txtTrJamMulai;
    private javax.swing.JTextField txtTrJamSelesai;
    private javax.swing.JTextField txtTrMerek;
    private javax.swing.JTextField txtTrMntDurasi;
    private javax.swing.JTextField txtTrMntMulai;
    private javax.swing.JTextField txtTrMntSelesai;
    private javax.swing.JTextField txtTrNama;
    private javax.swing.JTextField txtTrTarif;
    private javax.swing.JTextField txtTrTelpon;
    // End of variables declaration//GEN-END:variables
}
