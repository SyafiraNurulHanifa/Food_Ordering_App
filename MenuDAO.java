import java.util.*;
import java.sql.*;

public class MenuDAO {
    
    // CREATE - Tambah menu baru
    public boolean createMenu(Menu menu) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "INSERT INTO menu (nama_menu, kategori, harga, stok, deskripsi) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, menu.getNamaMenu());
            stmt.setString(2, menu.getKategori());
            stmt.setDouble(3, menu.getHarga());
            stmt.setInt(4, menu.getStok());
            stmt.setString(5, menu.getDeskripsi());
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error creating menu: " + e.getMessage());
            return false;
        }
    }
    
    // READ - Tampilkan semua menu
    public List<Menu> getAllMenu() {
        List<Menu> menuList = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM menu";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            
            while (rs.next()) {
                Menu menu = new Menu();
                menu.setMenuId(rs.getInt("menu_id"));
                menu.setNamaMenu(rs.getString("nama_menu"));
                menu.setKategori(rs.getString("kategori"));
                menu.setHarga(rs.getDouble("harga"));
                menu.setStok(rs.getInt("stok"));
                menu.setDeskripsi(rs.getString("deskripsi"));
                menuList.add(menu);
            }
        } catch (SQLException e) {
            System.out.println("Error reading menu: " + e.getMessage());
        }
        return menuList;
    }
    
    // UPDATE - Update menu
    public boolean updateMenu(Menu menu) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "UPDATE menu SET nama_menu = ?, kategori = ?, harga = ?, stok = ?, deskripsi = ? WHERE menu_id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, menu.getNamaMenu());
            stmt.setString(2, menu.getKategori());
            stmt.setDouble(3, menu.getHarga());
            stmt.setInt(4, menu.getStok());
            stmt.setString(5, menu.getDeskripsi());
            stmt.setInt(6, menu.getMenuId());
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error updating menu: " + e.getMessage());
            return false;
        }
    }
    
    // DELETE - Hapus menu
    public boolean deleteMenu(int menuId) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "DELETE FROM menu WHERE menu_id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, menuId);
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error deleting menu: " + e.getMessage());
            return false;
        }
    }
    
    // SEARCH - Cari menu berdasarkan nama atau kategori
    public List<Menu> searchMenu(String keyword) {
        List<Menu> menuList = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM menu WHERE nama_menu LIKE ? OR kategori LIKE ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, "%" + keyword + "%");
            stmt.setString(2, "%" + keyword + "%");
            
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Menu menu = new Menu();
                menu.setMenuId(rs.getInt("menu_id"));
                menu.setNamaMenu(rs.getString("nama_menu"));
                menu.setKategori(rs.getString("kategori"));
                menu.setHarga(rs.getDouble("harga"));
                menu.setStok(rs.getInt("stok"));
                menu.setDeskripsi(rs.getString("deskripsi"));
                menuList.add(menu);
            }
        } catch (SQLException e) {
            System.out.println("Error searching menu: " + e.getMessage());
        }
        return menuList;
    }
    
    // Mendapatkan menu by ID
    public Menu getMenuById(int menuId) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM menu WHERE menu_id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, menuId);
            
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Menu menu = new Menu();
                menu.setMenuId(rs.getInt("menu_id"));
                menu.setNamaMenu(rs.getString("nama_menu"));
                menu.setKategori(rs.getString("kategori"));
                menu.setHarga(rs.getDouble("harga"));
                menu.setStok(rs.getInt("stok"));
                menu.setDeskripsi(rs.getString("deskripsi"));
                return menu;
            }
        } catch (SQLException e) {
            System.out.println("Error getting menu by ID: " + e.getMessage());
        }
        return null;
    }
}
