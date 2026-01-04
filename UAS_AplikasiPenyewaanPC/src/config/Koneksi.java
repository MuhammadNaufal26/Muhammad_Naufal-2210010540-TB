package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Koneksi {
    private static Connection koneksi;

    public static Connection getKoneksi() {
        if (koneksi == null) {
            try {
                String url = "jdbc:mysql://localhost:3306/db_penyewaan_pc";
                String user = "root";
                String password = ""; // sesuaikan jika ada password

                DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
                koneksi = DriverManager.getConnection(url, user, password);

                System.out.println("Koneksi ke database BERHASIL");
            } catch (SQLException e) {
                System.out.println("Koneksi GAGAL");
                System.out.println(e.getMessage());
            }
        }
        return koneksi;
    }
}
