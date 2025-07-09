import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO {
    
    // CREATE - Buat order baru dengan multiple items
    public boolean createOrder(Order order) {
        Connection conn = null;
        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false); // Start transaction
            
            // 1. Insert ke orders_master
            String masterSql = "INSERT INTO orders_master (user_id, total_harga, status) VALUES (?, ?, ?)";
            PreparedStatement masterStmt = conn.prepareStatement(masterSql, Statement.RETURN_GENERATED_KEYS);
            masterStmt.setInt(1, order.getUserId());
            masterStmt.setDouble(2, order.getTotalHarga());
            masterStmt.setString(3, order.getStatus());
            
            int masterResult = masterStmt.executeUpdate();
            if (masterResult == 0) {
                conn.rollback();
                return false;
            }
            
            // Get generated order_id
            ResultSet generatedKeys = masterStmt.getGeneratedKeys();
            int orderId = 0;
            if (generatedKeys.next()) {
                orderId = generatedKeys.getInt(1);
                order.setOrderId(orderId);
            }
            
            // 2. Insert order items
            String itemSql = "INSERT INTO order_items (order_id, menu_id, jumlah, harga_satuan, subtotal) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement itemStmt = conn.prepareStatement(itemSql);
            
            for (OrderItem item : order.getOrderItems()) {
                // Cek stok terlebih dahulu
                if (!checkStock(conn, item.getMenuId(), item.getJumlah())) {
                    conn.rollback();
                    System.out.println("Stok tidak mencukupi untuk menu ID: " + item.getMenuId());
                    return false;
                }
                
                itemStmt.setInt(1, orderId);
                itemStmt.setInt(2, item.getMenuId());
                itemStmt.setInt(3, item.getJumlah());
                itemStmt.setDouble(4, item.getHargaSatuan());
                itemStmt.setDouble(5, item.getSubtotal());
                itemStmt.addBatch();
                
                // Update stok menu
                updateStock(conn, item.getMenuId(), item.getJumlah());
            }
            
            itemStmt.executeBatch();
            conn.commit(); // Commit transaction
            return true;
            
        } catch (SQLException e) {
            try {
                if (conn != null) conn.rollback();
            } catch (SQLException ex) {
                System.out.println("Rollback error: " + ex.getMessage());
            }
            System.out.println("Error creating order: " + e.getMessage());
            return false;
        } finally {
            try {
                if (conn != null) {
                    conn.setAutoCommit(true);
                    conn.close();
                }
            } catch (SQLException e) {
                System.out.println("Error closing connection: " + e.getMessage());
            }
        }
    }
    
    // Helper method untuk cek stok
    private boolean checkStock(Connection conn, int menuId, int jumlah) throws SQLException {
        String sql = "SELECT stok FROM menu WHERE menu_id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, menuId);
        ResultSet rs = stmt.executeQuery();
        
        if (rs.next()) {
            int stokTersedia = rs.getInt("stok");
            return stokTersedia >= jumlah;
        }
        return false;
    }
    
    // Helper method untuk update stok
    private void updateStock(Connection conn, int menuId, int jumlah) throws SQLException {
        String sql = "UPDATE menu SET stok = stok - ? WHERE menu_id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, jumlah);
        stmt.setInt(2, menuId);
        stmt.executeUpdate();
    }
    
    // READ - Get all orders dengan detail items
    public List<Order> getAllOrders() {
        List<Order> orderList = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT om.*, u.nama as customer_name, u.alamat, u.telepon " +
                        "FROM orders_master om " +
                        "JOIN users u ON om.user_id = u.user_id " +
                        "ORDER BY om.tanggal_pesan DESC";
            
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            
            while (rs.next()) {
                Order order = new Order();
                order.setOrderId(rs.getInt("order_id"));
                order.setUserId(rs.getInt("user_id"));
                order.setTotalHarga(rs.getDouble("total_harga"));
                order.setStatus(rs.getString("status"));
                order.setTanggalPesan(rs.getTimestamp("tanggal_pesan"));
                order.setCustomerName(rs.getString("customer_name"));
                order.setAlamat(rs.getString("alamat"));
                order.setTelepon(rs.getString("telepon"));
                
                // Load order items
                order.setOrderItems(getOrderItems(order.getOrderId()));
                orderList.add(order);
            }
        } catch (SQLException e) {
            System.out.println("Error getting all orders: " + e.getMessage());
        }
        return orderList;
    }
    
    // Helper method untuk get order items
    private List<OrderItem> getOrderItems(int orderId) {
        List<OrderItem> items = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT oi.*, m.nama_menu, m.kategori " +
                        "FROM order_items oi " +
                        "JOIN menu m ON oi.menu_id = m.menu_id " +
                        "WHERE oi.order_id = ?";
            
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, orderId);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                OrderItem item = new OrderItem();
                item.setItemId(rs.getInt("item_id"));
                item.setOrderId(rs.getInt("order_id"));
                item.setMenuId(rs.getInt("menu_id"));
                item.setJumlah(rs.getInt("jumlah"));
                item.setHargaSatuan(rs.getDouble("harga_satuan"));
                item.setSubtotal(rs.getDouble("subtotal"));
                item.setNamaMenu(rs.getString("nama_menu"));
                item.setKategori(rs.getString("kategori"));
                items.add(item);
            }
        } catch (SQLException e) {
            System.out.println("Error getting order items: " + e.getMessage());
        }
        return items;
    }
    
    // Get order by ID
    public Order getOrderById(int orderId) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT om.*, u.nama as customer_name, u.alamat, u.telepon " +
                        "FROM orders_master om " +
                        "JOIN users u ON om.user_id = u.user_id " +
                        "WHERE om.order_id = ?";
            
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, orderId);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                Order order = new Order();
                order.setOrderId(rs.getInt("order_id"));
                order.setUserId(rs.getInt("user_id"));
                order.setTotalHarga(rs.getDouble("total_harga"));
                order.setStatus(rs.getString("status"));
                order.setTanggalPesan(rs.getTimestamp("tanggal_pesan"));
                order.setCustomerName(rs.getString("customer_name"));
                order.setAlamat(rs.getString("alamat"));
                order.setTelepon(rs.getString("telepon"));
                
                // Load order items
                order.setOrderItems(getOrderItems(orderId));
                return order;
            }
        } catch (SQLException e) {
            System.out.println("Error getting order by ID: " + e.getMessage());
        }
        return null;
    }
    
    // Update order status
    public boolean updateOrderStatus(int orderId, String newStatus) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "UPDATE orders_master SET status = ? WHERE order_id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, newStatus);
            stmt.setInt(2, orderId);
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error updating order status: " + e.getMessage());
            return false;
        }
    }
    
    // Get orders by status
    public List<Order> getOrdersByStatus(String status) {
        List<Order> orderList = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT om.*, u.nama as customer_name, u.alamat, u.telepon " +
                        "FROM orders_master om " +
                        "JOIN users u ON om.user_id = u.user_id " +
                        "WHERE om.status = ? " +
                        "ORDER BY om.tanggal_pesan DESC";
            
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, status);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Order order = new Order();
                order.setOrderId(rs.getInt("order_id"));
                order.setUserId(rs.getInt("user_id"));
                order.setTotalHarga(rs.getDouble("total_harga"));
                order.setStatus(rs.getString("status"));
                order.setTanggalPesan(rs.getTimestamp("tanggal_pesan"));
                order.setCustomerName(rs.getString("customer_name"));
                order.setAlamat(rs.getString("alamat"));
                order.setTelepon(rs.getString("telepon"));
                
                // Load order items
                order.setOrderItems(getOrderItems(order.getOrderId()));
                orderList.add(order);
            }
        } catch (SQLException e) {
            System.out.println("Error getting orders by status: " + e.getMessage());
        }
        return orderList;
    }
    
    // Get orders by user ID (untuk customer history)
    public List<Order> getOrdersByUserId(int userId) {
        List<Order> orderList = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT om.*, u.nama as customer_name, u.alamat, u.telepon " +
                        "FROM orders_master om " +
                        "JOIN users u ON om.user_id = u.user_id " +
                        "WHERE om.user_id = ? " +
                        "ORDER BY om.tanggal_pesan DESC";
            
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Order order = new Order();
                order.setOrderId(rs.getInt("order_id"));
                order.setUserId(rs.getInt("user_id"));
                order.setTotalHarga(rs.getDouble("total_harga"));
                order.setStatus(rs.getString("status"));
                order.setTanggalPesan(rs.getTimestamp("tanggal_pesan"));
                order.setCustomerName(rs.getString("customer_name"));
                order.setAlamat(rs.getString("alamat"));
                order.setTelepon(rs.getString("telepon"));
                
                // Load order items
                order.setOrderItems(getOrderItems(order.getOrderId()));
                orderList.add(order);
            }
        } catch (SQLException e) {
            System.out.println("Error getting orders by user ID: " + e.getMessage());
        }
        return orderList;
    }
}