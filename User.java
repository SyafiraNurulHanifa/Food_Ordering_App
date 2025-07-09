import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class User {
    protected int userId;
    protected String username;
    protected String password;
    protected String nama;
    protected String alamat;
    protected String telepon;
    
    // Constructor
    public User() {}
    
    public User(String username, String password, String nama, String alamat, String telepon) {
        this.username = username;
        this.password = password;
        this.nama = nama;
        this.alamat = alamat;
        this.telepon = telepon;
    }
    
    // Encapsulation - Getters and Setters
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
    
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    
    public String getNama() { return nama; }
    public void setNama(String nama) { this.nama = nama; }
    
    public String getAlamat() { return alamat; }
    public void setAlamat(String alamat) { this.alamat = alamat; }
    
    public String getTelepon() { return telepon; }
    public void setTelepon(String telepon) { this.telepon = telepon; }
    
    // Bagian untuk login
    public boolean login(String username, String password) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, password);
            
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                this.userId = rs.getInt("user_id");
                this.username = rs.getString("username");
                this.nama = rs.getString("nama");
                this.alamat = rs.getString("alamat");
                this.telepon = rs.getString("telepon");
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Login error: " + e.getMessage());
        }
        return false;
    }
    
    // Bagian untuk registrasi
    public boolean register() {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "INSERT INTO users (username, password, nama, alamat, telepon, role) VALUES (?, ?, ?, ?, ?, 'customer')";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, this.username);
            stmt.setString(2, this.password);
            stmt.setString(3, this.nama);
            stmt.setString(4, this.alamat);
            stmt.setString(5, this.telepon);
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Registration error: " + e.getMessage());
            return false;
        }
    }
}
