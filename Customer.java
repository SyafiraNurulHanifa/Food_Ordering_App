import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Customer extends User {
    
    // Constructor
    public Customer() {
        super();
    }
    
    public Customer(String username, String password, String nama, String alamat, String telepon) {
        super(username, password, nama, alamat, telepon);
    }
    
    // Method khusus customer untuk melihat riwayat pesanan
    public void viewOrderHistory() {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT o.*, m.nama_menu FROM orders o JOIN menu m ON o.menu_id = m.menu_id WHERE o.user_id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, this.userId);
            
            ResultSet rs = stmt.executeQuery();
            System.out.println("\n=== RIWAYAT PESANAN ===");
            while (rs.next()) {
                System.out.println("ID Pesanan: " + rs.getInt("order_id"));
                System.out.println("Menu: " + rs.getString("nama_menu"));
                System.out.println("Jumlah: " + rs.getInt("jumlah"));
                System.out.println("Total: Rp " + rs.getDouble("total_harga"));
                System.out.println("Tanggal: " + rs.getTimestamp("tanggal_pesan"));
                System.out.println("Status: " + rs.getString("status"));
                System.out.println("------------------------");
            }
        } catch (SQLException e) {
            System.out.println("Error viewing order history: " + e.getMessage());
        }
    }
}
