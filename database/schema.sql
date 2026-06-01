-- ============================================================
-- FILE: schema.sql
-- FUNGSI:
-- File ini digunakan untuk membuat struktur tabel aplikasi
-- Tubes PBO.
--
-- DIJALANKAN SEBAGAI:
-- User/schema TUBESPBO
--
-- KAPAN DIJALANKAN:
-- Jalankan setelah setup-user.sql berhasil dan koneksi TUBESPBO
-- sudah bisa digunakan.
--
-- CATATAN:
-- File ini membuat tabel utama seperti users, kendaraan,
-- pelanggan, dan transaksi.
-- ============================================================

CREATE TABLE users (
    username VARCHAR2(50) PRIMARY KEY,
    password VARCHAR2(100) NOT NULL,
    role VARCHAR2(20) NOT NULL
);

CREATE TABLE kendaraan (
    plat_nomor VARCHAR2(20) PRIMARY KEY,
    merek VARCHAR2(100) NOT NULL,
    harga_sewa_per_hari NUMBER NOT NULL,
    status VARCHAR2(30) NOT NULL,
    jenis VARCHAR2(20) NOT NULL,
    jumlah_pintu NUMBER,
    jenis_transmisi VARCHAR2(50)
);

CREATE TABLE pelanggan (
    nomor_ktp VARCHAR2(30) PRIMARY KEY,
    nama_lengkap VARCHAR2(100) NOT NULL,
    nomor_telepon VARCHAR2(30) NOT NULL,
    jumlah_transaksi NUMBER DEFAULT 0,
    level_membership VARCHAR2(20) DEFAULT 'REGULER'
);

CREATE TABLE transaksi (
    id_transaksi VARCHAR2(50) PRIMARY KEY,
    nomor_ktp VARCHAR2(30) NOT NULL,
    plat_nomor VARCHAR2(20) NOT NULL,
    durasi_sewa_hari NUMBER NOT NULL,
    hari_keterlambatan NUMBER DEFAULT 0,
    total_bayar NUMBER DEFAULT 0,
    selesai NUMBER(1) DEFAULT 0,
    CONSTRAINT fk_transaksi_pelanggan FOREIGN KEY (nomor_ktp)
        REFERENCES pelanggan(nomor_ktp),
    CONSTRAINT fk_transaksi_kendaraan FOREIGN KEY (plat_nomor)
        REFERENCES kendaraan(plat_nomor)
);
