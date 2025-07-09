import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class Order {
    private int orderId;
    private int userId;
    private double totalHarga;
    private String status;
    private Timestamp tanggalPesan;
    
    // Fields tambahan untuk menampilkan informasi lengkap
    private String customerName;
    private String alamat;
    private String telepon;
    
    // List untuk menampung multiple items
    private List<OrderItem> orderItems;
    
    // Constructor
    public Order() {
        this.orderItems = new ArrayList<>();
        this.status = "pending";
        this.totalHarga = 0.0;
    }
    
    public Order(int userId) {
        this.userId = userId;
        this.orderItems = new ArrayList<>();
        this.status = "pending";
        this.totalHarga = 0.0;
    }
    
    // Method untuk menambah item ke order
    public void addItem(OrderItem item) {
        this.orderItems.add(item);
        calculateTotalHarga();
    }
    
    // Method untuk menghitung total harga
    private void calculateTotalHarga() {
        this.totalHarga = 0.0;
        for (OrderItem item : orderItems) {
            this.totalHarga += item.getSubtotal();
        }
    }
    
    // Method untuk menghapus item dari order
    public void removeItem(int itemId) {
        orderItems.removeIf(item -> item.getItemId() == itemId);
        calculateTotalHarga();
    }
    
    // Method untuk mengupdate jumlah item
    public void updateItemQuantity(int menuId, int newJumlah) {
        for (OrderItem item : orderItems) {
            if (item.getMenuId() == menuId) {
                item.setJumlah(newJumlah);
                break;
            }
        }
        calculateTotalHarga();
    }
    
    // Getters and Setters
    public int getOrderId() { return orderId; }
    public void setOrderId(int orderId) { this.orderId = orderId; }
    
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
    
    public double getTotalHarga() { return totalHarga; }
    public void setTotalHarga(double totalHarga) { this.totalHarga = totalHarga; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    public Timestamp getTanggalPesan() { return tanggalPesan; }
    public void setTanggalPesan(Timestamp tanggalPesan) { this.tanggalPesan = tanggalPesan; }
    
    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }
    
    public String getAlamat() { return alamat; }
    public void setAlamat(String alamat) { this.alamat = alamat; }
    
    public String getTelepon() { return telepon; }
    public void setTelepon(String telepon) { this.telepon = telepon; }
    
    public List<OrderItem> getOrderItems() { return orderItems; }
    public void setOrderItems(List<OrderItem> orderItems) { 
        this.orderItems = orderItems; 
        calculateTotalHarga();
    }
    
    // Method untuk menampilkan detail order
    public void displayOrderDetails() {
        System.out.println("=== DETAIL PESANAN ===");
        System.out.println("ID Pesanan: " + orderId);
        System.out.println("Customer: " + customerName);
        System.out.println("Alamat: " + alamat);
        System.out.println("Telepon: " + telepon);
        System.out.println("Status: " + status);
        System.out.println("Tanggal: " + tanggalPesan);
        System.out.println("\n--- ITEM PESANAN ---");
        
        for (OrderItem item : orderItems) {
            System.out.println("- " + item.getNamaMenu() + 
                             " (x" + item.getJumlah() + ") - Rp " + item.getSubtotal());
        }
        
        System.out.println("\nTOTAL HARGA: Rp " + totalHarga);
        System.out.println("========================");
    }
}