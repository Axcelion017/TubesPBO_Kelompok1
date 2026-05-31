-- ============================================================
-- FILE: check.sql
-- FUNGSI:
-- File ini digunakan untuk mengecek koneksi aktif dan tabel
-- yang sudah berhasil dibuat.
--
-- DIJALANKAN SEBAGAI:
-- User/schema TUBESPBO
--
-- KAPAN DIJALANKAN:
-- Jalankan setelah schema.sql untuk memastikan tabel sudah ada.
--
-- CATATAN:
-- Query SELECT USER FROM dual akan menampilkan user Oracle
-- yang sedang aktif.
-- Query SELECT table_name FROM user_tables akan menampilkan
-- daftar tabel milik user/schema yang sedang aktif.
-- ============================================================

SELECT USER FROM dual;

SELECT table_name
FROM user_tables;