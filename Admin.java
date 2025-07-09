import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class Admin extends User {
    
    // Constructor
    public Admin() {
        super();
    }
    
    public Admin(String username, String password, String nama, String alamat, String telepon) {
        super(username, password, nama, alamat, telepon);
    }
    
    // Override login untuk admin
    @Override
    public boolean login(String username, String password) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM users WHERE username = ? AND password = ? AND role = 'admin'";
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
            System.out.println("Admin login error: " + e.getMessage());
        }
        return false;
    }
}
