-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Waktu pembuatan: 04 Jan 2026 pada 09.38
-- Versi server: 10.4.28-MariaDB
-- Versi PHP: 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `db_penyewaan_pc`
--

-- --------------------------------------------------------

--
-- Struktur dari tabel `tbl_pc`
--

CREATE TABLE `tbl_pc` (
  `id_pc` int(11) NOT NULL,
  `nama_pc` varchar(50) NOT NULL,
  `spesifikasi` varchar(100) DEFAULT NULL,
  `tarif_per_jam` int(11) NOT NULL,
  `status_pc` enum('TERSEDIA','DISEWA','OFF') DEFAULT 'TERSEDIA'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `tbl_pc`
--

INSERT INTO `tbl_pc` (`id_pc`, `nama_pc`, `spesifikasi`, `tarif_per_jam`, `status_pc`) VALUES
(1, 'PC-01', 'Intel i5, 8GB RAM', 5000, 'TERSEDIA'),
(2, 'PC-02', 'Intel i5, 8GB RAM', 5000, 'TERSEDIA'),
(3, 'PC-03', 'Ryzen 5, 16GB RAM', 7000, 'TERSEDIA'),
(4, 'PC-04', 'Ryzen 5, 16GB RAM', 7000, 'TERSEDIA'),
(5, 'PC-05', 'Intel i3, 8GB RAM', 4000, 'TERSEDIA'),
(6, 'PC-06', 'Intel i3, 8GB RAM', 4000, 'TERSEDIA'),
(7, 'PC-07', 'Intel i7, 16GB RAM', 9000, 'OFF'),
(8, 'PC-08', 'Intel i7, 16GB RAM', 9000, 'TERSEDIA');

-- --------------------------------------------------------

--
-- Struktur dari tabel `tbl_transaksi`
--

CREATE TABLE `tbl_transaksi` (
  `id_transaksi` int(11) NOT NULL,
  `nama_penyewa` varchar(100) NOT NULL,
  `id_pc` int(11) NOT NULL,
  `waktu_mulai` datetime NOT NULL,
  `waktu_selesai` datetime DEFAULT NULL,
  `durasi_jam` double DEFAULT NULL,
  `total_bayar` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Struktur dari tabel `tbl_user`
--

CREATE TABLE `tbl_user` (
  `id_user` int(11) NOT NULL,
  `username` varchar(50) NOT NULL,
  `password` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `tbl_user`
--

INSERT INTO `tbl_user` (`id_user`, `username`, `password`) VALUES
(1, 'admin', 'admin');

--
-- Indexes for dumped tables
--

--
-- Indeks untuk tabel `tbl_pc`
--
ALTER TABLE `tbl_pc`
  ADD PRIMARY KEY (`id_pc`);

--
-- Indeks untuk tabel `tbl_transaksi`
--
ALTER TABLE `tbl_transaksi`
  ADD PRIMARY KEY (`id_transaksi`),
  ADD KEY `id_pc` (`id_pc`);

--
-- Indeks untuk tabel `tbl_user`
--
ALTER TABLE `tbl_user`
  ADD PRIMARY KEY (`id_user`);

--
-- AUTO_INCREMENT untuk tabel yang dibuang
--

--
-- AUTO_INCREMENT untuk tabel `tbl_pc`
--
ALTER TABLE `tbl_pc`
  MODIFY `id_pc` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT untuk tabel `tbl_transaksi`
--
ALTER TABLE `tbl_transaksi`
  MODIFY `id_transaksi` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT untuk tabel `tbl_user`
--
ALTER TABLE `tbl_user`
  MODIFY `id_user` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- Ketidakleluasaan untuk tabel pelimpahan (Dumped Tables)
--

--
-- Ketidakleluasaan untuk tabel `tbl_transaksi`
--
ALTER TABLE `tbl_transaksi`
  ADD CONSTRAINT `tbl_transaksi_ibfk_1` FOREIGN KEY (`id_pc`) REFERENCES `tbl_pc` (`id_pc`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
