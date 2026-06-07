-- ============================================================
-- FILE: seed.sql
-- FUNGSI:
-- File ini digunakan untuk mengisi data awal aplikasi Tubes PBO.
--
-- DIJALANKAN SEBAGAI:
-- User/schema TUBESPBO
--
-- KAPAN DIJALANKAN:
-- Jalankan setelah schema.sql berhasil membuat tabel.
--
-- CATATAN:
-- Data awal ini digunakan agar aplikasi bisa langsung login
-- dengan akun default admin, staff, dan owner.
-- ============================================================

INSERT INTO users (username, password, role)
VALUES ('admin', 'admin123', 'ADMIN');

INSERT INTO users (username, password, role)
VALUES ('staff', 'staff123', 'STAFF');

INSERT INTO users (username, password, role)
VALUES ('owner', 'owner123', 'OWNER');

COMMIT;

SELECT * FROM users;