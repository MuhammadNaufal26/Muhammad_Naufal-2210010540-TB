package wfo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Koneksi {
    private static Connection mysqlconfig;
    public static Connection configDB() throws SQLException {
        try {
            // Ganti "db_warnet_wfo" sesuai nama database di phpMyAdmin mu
            String url = "jdbc:mysql://localhost:3306/db_warnet_wfo"; 
            String user = "root";
            String pass = ""; // Kosongkan jika pakai XAMPP standar
            
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
            mysqlconfig = DriverManager.getConnection(url, user, pass);            
        } catch (Exception e) {
            System.err.println("Koneksi Gagal: " + e.getMessage());
        }
        return mysqlconfig;
    }
}