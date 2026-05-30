-- ============================================================
-- FILE: setup-user.sql
-- FUNGSI:
-- File ini digunakan untuk membuat user/schema khusus project
-- Tubes PBO, yaitu TUBESPBO.
--
-- DIJALANKAN SEBAGAI:
-- Oracle Admin / SYSTEM
--
-- KAPAN DIJALANKAN:
-- Jalankan file ini pertama kali sebelum membuat tabel.
--
-- CATATAN:
-- File ini hanya untuk setup user database, bukan untuk membuat
-- tabel aplikasi.
-- ============================================================

CREATE USER TUBESPBO IDENTIFIED BY TubesPBO19;

GRANT CREATE SESSION TO TUBESPBO;
GRANT CREATE TABLE TO TUBESPBO;
GRANT CREATE SEQUENCE TO TUBESPBO;
GRANT CREATE VIEW TO TUBESPBO;
GRANT CREATE TRIGGER TO TUBESPBO;

ALTER USER TUBESPBO QUOTA UNLIMITED ON USERS;