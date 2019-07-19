-- phpMyAdmin SQL Dump
-- version 4.8.3
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Waktu pembuatan: 19 Jul 2019 pada 06.23
-- Versi server: 10.1.37-MariaDB
-- Versi PHP: 7.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `dbbooking`
--

-- --------------------------------------------------------

--
-- Struktur dari tabel `tblkategori`
--

CREATE TABLE `tblkategori` (
  `kd_kategori` char(8) NOT NULL,
  `nm_kategori` varchar(50) NOT NULL,
  `hrg_sewa` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `tblkategori`
--

INSERT INTO `tblkategori` (`kd_kategori`, `nm_kategori`, `hrg_sewa`) VALUES
('K01', 'Lapangan Rumput Sintetis', 65000),
('K02', 'Lapangan Vinyl', 80000),
('K03', 'Lapangan Karpet Plastik', 95000);

-- --------------------------------------------------------

--
-- Struktur dari tabel `tbllogin`
--

CREATE TABLE `tbllogin` (
  `username` varchar(10) NOT NULL,
  `password` varchar(15) NOT NULL,
  `hak_akses` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `tbllogin`
--

INSERT INTO `tbllogin` (`username`, `password`, `hak_akses`) VALUES
('admin', 'admin', 'admin'),
('karyawan', 'karyawan', 'karyawan');

-- --------------------------------------------------------

--
-- Struktur dari tabel `tblproduk`
--

CREATE TABLE `tblproduk` (
  `kd_produk` char(11) NOT NULL,
  `nm_produk` varchar(50) NOT NULL,
  `hrg_sewa` int(11) NOT NULL,
  `stok` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `tblproduk`
--

INSERT INTO `tblproduk` (`kd_produk`, `nm_produk`, `hrg_sewa`, `stok`) VALUES
('P001', 'Rompi', 10000, 50),
('P002', 'Ground Disc', 2000, 100),
('P003', 'Dekker', 7000, 20),
('P004', 'Peluit', 2000, 20),
('P005', 'Bola', 15000, 50);

-- --------------------------------------------------------

--
-- Struktur dari tabel `tbltransaksilapangan`
--

CREATE TABLE `tbltransaksilapangan` (
  `no_booking` char(15) NOT NULL,
  `kd_kategori` char(8) NOT NULL,
  `nm_kategori` varchar(50) NOT NULL,
  `hrg_sewa` int(11) NOT NULL,
  `nama_team` varchar(100) NOT NULL,
  `nama_pemesan` varchar(150) NOT NULL,
  `tanggal_booking` varchar(20) NOT NULL,
  `waktu_booking` char(20) NOT NULL,
  `lama_booking` int(11) NOT NULL,
  `total` int(11) NOT NULL,
  `bayar` int(11) NOT NULL,
  `kembali` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `tbltransaksilapangan`
--

INSERT INTO `tbltransaksilapangan` (`no_booking`, `kd_kategori`, `nm_kategori`, `hrg_sewa`, `nama_team`, `nama_pemesan`, `tanggal_booking`, `waktu_booking`, `lama_booking`, `total`, `bayar`, `kembali`) VALUES
('0001', 'K01', 'Lapangan Rumput Sintetis', 65000, 'Wakanda FC', 'Reza', '17-09-2019', '18.00', 3, 195000, 200000, 5000),
('0003', 'K01', 'Lapangan Rumput Sintetis', 65000, 'Duba FC', 'Faarul', '21-2-2019', '19.00', 2, 130000, 65000, -65000),
('0004', 'K02', 'Lapangan Vinyl', 80000, 'White Panther FC', 'Zikron', '17-08-2019', '13.00', 4, 320000, 350000, 30000),
('0005', 'K02', 'Lapangan Vinyl', 80000, 'Ganteng', 'Nanda', '17-03-2019', '12.00', 4, 320000, 350000, 30000);

-- --------------------------------------------------------

--
-- Struktur dari tabel `tbltransaksiperkap`
--

CREATE TABLE `tbltransaksiperkap` (
  `no_pemesan` char(15) NOT NULL,
  `nama_pemesan` varchar(50) NOT NULL,
  `nama_team` varchar(50) NOT NULL,
  `nm_produk` varchar(50) NOT NULL,
  `kd_produk` char(11) NOT NULL,
  `hrg_sewa` int(11) NOT NULL,
  `jumlah` int(11) NOT NULL,
  `total` int(11) NOT NULL,
  `bayar` int(11) NOT NULL,
  `kembali` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `tbltransaksiperkap`
--

INSERT INTO `tbltransaksiperkap` (`no_pemesan`, `nama_pemesan`, `nama_team`, `nm_produk`, `kd_produk`, `hrg_sewa`, `jumlah`, `total`, `bayar`, `kembali`) VALUES
('T1901', 'Faarul', 'Duba FC', 'Rompi', 'P001', 10000, 3, 30000, 30000, 0),
('T1902', 'Zikron', 'White Panther FC', 'Bola', 'P005', 15000, 4, 60000, 60000, 0);

--
-- Indexes for dumped tables
--

--
-- Indeks untuk tabel `tblkategori`
--
ALTER TABLE `tblkategori`
  ADD PRIMARY KEY (`kd_kategori`);

--
-- Indeks untuk tabel `tbllogin`
--
ALTER TABLE `tbllogin`
  ADD PRIMARY KEY (`username`);

--
-- Indeks untuk tabel `tblproduk`
--
ALTER TABLE `tblproduk`
  ADD PRIMARY KEY (`kd_produk`);

--
-- Indeks untuk tabel `tbltransaksilapangan`
--
ALTER TABLE `tbltransaksilapangan`
  ADD PRIMARY KEY (`no_booking`),
  ADD KEY `kd_kategori` (`kd_kategori`);

--
-- Indeks untuk tabel `tbltransaksiperkap`
--
ALTER TABLE `tbltransaksiperkap`
  ADD PRIMARY KEY (`no_pemesan`),
  ADD KEY `kd_produk` (`kd_produk`);

--
-- Ketidakleluasaan untuk tabel pelimpahan (Dumped Tables)
--

--
-- Ketidakleluasaan untuk tabel `tbltransaksilapangan`
--
ALTER TABLE `tbltransaksilapangan`
  ADD CONSTRAINT `tbltransaksilapangan_ibfk_1` FOREIGN KEY (`kd_kategori`) REFERENCES `tblkategori` (`kd_kategori`);

--
-- Ketidakleluasaan untuk tabel `tbltransaksiperkap`
--
ALTER TABLE `tbltransaksiperkap`
  ADD CONSTRAINT `tbltransaksiperkap_ibfk_1` FOREIGN KEY (`kd_produk`) REFERENCES `tblproduk` (`kd_produk`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
