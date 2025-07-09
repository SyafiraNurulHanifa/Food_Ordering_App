public class OrderItem {
    private int itemId;
    private int orderId;
    private int menuId;
    private int jumlah;
    private double hargaSatuan;
    private double subtotal;
    
    // Fields tambahan untuk display
    private String namaMenu;
    private String kategori;
    
    // Constructor
    public OrderItem() {}
    
    public OrderItem(int orderId, int menuId, int jumlah, double hargaSatuan) {
        this.orderId = orderId;
        this.menuId = menuId;
        this.jumlah = jumlah;
        this.hargaSatuan = hargaSatuan;
        this.subtotal = jumlah * hargaSatuan;
    }
    
    // Getters and Setters
    public int getItemId() { return itemId; }
    public void setItemId(int itemId) { this.itemId = itemId; }
    
    public int getOrderId() { return orderId; }
    public void setOrderId(int orderId) { this.orderId = orderId; }
    
    public int getMenuId() { return menuId; }
    public void setMenuId(int menuId) { this.menuId = menuId; }
    
    public int getJumlah() { return jumlah; }
    public void setJumlah(int jumlah) { 
        this.jumlah = jumlah; 
        this.subtotal = jumlah * hargaSatuan;
    }
    
    public double getHargaSatuan() { return hargaSatuan; }
    public void setHargaSatuan(double hargaSatuan) { 
        this.hargaSatuan = hargaSatuan; 
        this.subtotal = jumlah * hargaSatuan;
    }
    
    public double getSubtotal() { return subtotal; }
    public void setSubtotal(double subtotal) { this.subtotal = subtotal; }
    
    public String getNamaMenu() { return namaMenu; }
    public void setNamaMenu(String namaMenu) { this.namaMenu = namaMenu; }
    
    public String getKategori() { return kategori; }
    public void setKategori(String kategori) { this.kategori = kategori; }
}