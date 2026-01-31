# 🖥️ Aplikasi Warnet - Fal Opal

Project UAS Pemrograman Berbasis Objek menggunakan Java Swing dan MySQL.

## 🗄️ Cara Setup Database
1. Buka **phpMyAdmin**.
2. Buat database baru (misal: `db_warnet_wfo`).
3. Masuk ke folder `AplikasiWarnetWFO` di repo ini, cari file `.sql` kamu.
4. Di phpMyAdmin, klik tab **Import** dan pilih file tersebut.
5. Klik **Go/Import**.

## 🛠️ Library (Jar) yang Diperlukan
Pastikan sudah memasukkan Library berikut di NetBeans:
* **MySQL JDBC Connector** (Koneksi Database)
* **iText PDF 7** (Cetak Struk)

## 📄 Fitur Utama
* Auto-generate ID (Format: TR + yyMM + Urutan).
* Perhitungan durasi otomatis (Jam, Menit, Detik).
* Ekspor laporan ke Excel (.csv) dengan pembersihan format angka.
* Cetak Struk ke PDF dengan layout thermal paper 58mm.

## LOGIN
* Username : admin
* Pass  : admin123
