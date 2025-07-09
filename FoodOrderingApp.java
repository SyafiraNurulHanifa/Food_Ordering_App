import java.util.*;
import java.sql.*;

public class FoodOrderingApp {
    private static Scanner scanner = new Scanner(System.in);
    private static User currentUser = null;
    private static boolean isAdmin = false;
    private static MenuDAO menuDAO = new MenuDAO();
    private static OrderDAO orderDAO = new OrderDAO();

    public static void main(String[] args) {
        System.out.println("=== APLIKASI PEMESANAN MAKANAN ===");

        while (true) {
            if (currentUser == null) {
                showMainMenu();
            } else if (isAdmin) {
                showAdminMenu();
            } else {
                showCustomerMenu();
            }
        }
    }

    private static void showMainMenu() {
        System.out.println("\n1. Login");
        System.out.println("2. Registrasi");
        System.out.println("3. Keluar");
        System.out.print("Pilih menu: ");

        int choice = scanner.nextInt();
        scanner.nextLine(); // consume newline

        switch (choice) {
            case 1:
                login();
                break;
            case 2:
                register();
                break;
            case 3:
                System.out.println("Terima kasih!");
                System.exit(0);
                break;
            default:
                System.out.println("Pilihan tidak valid!");
        }
    }

    private static void login() {
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();
        System.out.print("Login sebagai (1=Admin, 2=Customer): ");
        int userType = scanner.nextInt();
        scanner.nextLine();

        if (userType == 1) {
            Admin admin = new Admin();
            if (admin.login(username, password)) {
                currentUser = admin;
                isAdmin = true;
                System.out.println("Login admin berhasil! Selamat datang, " + admin.getNama());
            } else {
                System.out.println("Login admin gagal!");
            }
        } else {
            Customer customer = new Customer();
            if (customer.login(username, password)) {
                currentUser = customer;
                isAdmin = false;
                System.out.println("Login berhasil! Selamat datang, " + customer.getNama());
            } else {
                System.out.println("Login gagal!");
            }
        }
    }

    private static void register() {
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();
        System.out.print("Nama Lengkap: ");
        String nama = scanner.nextLine();
        System.out.print("Alamat: ");
        String alamat = scanner.nextLine();
        System.out.print("Telepon: ");
        String telepon = scanner.nextLine();

        Customer customer = new Customer(username, password, nama, alamat, telepon);
        if (customer.register()) {
            System.out.println("Registrasi berhasil!");
        } else {
            System.out.println("Registrasi gagal!");
        }
    }

    private static void showAdminMenu() {
        System.out.println("\n=== MENU ADMIN ===");
        System.out.println("1. Lihat Semua Menu");
        System.out.println("2. Tambah Menu");
        System.out.println("3. Update Menu");
        System.out.println("4. Hapus Menu");
        System.out.println("5. Cari Menu");
        System.out.println("6. Lihat Semua Pesanan");
        System.out.println("7. Kelola Status Pesanan");
        System.out.println("8. Lihat Pesanan Berdasarkan Status");
        System.out.println("9. Logout");
        System.out.print("Pilih menu: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1:
                viewAllMenu();
                break;
            case 2:
                addMenu();
                break;
            case 3:
                updateMenu();
                break;
            case 4:
                deleteMenu();
                break;
            case 5:
                searchMenu();
                break;
            case 6:
                viewAllOrders();
                break;
            case 7:
                manageOrderStatus();
                break;
            case 8:
                viewOrdersByStatus();
                break;
            case 9:
                logout();
                break;
            default:
                System.out.println("Pilihan tidak valid!");
        }
    }

    private static void showCustomerMenu() {
        System.out.println("\n=== MENU CUSTOMER ===");
        System.out.println("1. Lihat Menu Makanan");
        System.out.println("2. Buat Pesanan Baru");
        System.out.println("3. Cari Menu");
        System.out.println("4. Riwayat Pesanan");
        System.out.println("5. Logout");
        System.out.print("Pilih menu: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1:
                viewAllMenu();
                break;
            case 2:
                createNewOrder();
                break;
            case 3:
                searchMenu();
                break;
            case 4:
                viewCustomerOrderHistory();
                break;
            case 5:
                logout();
                break;
            default:
                System.out.println("Pilihan tidak valid!");
        }
    }

    private static void viewAllMenu() {
        List<Menu> menuList = menuDAO.getAllMenu();
        System.out.println("\n=== DAFTAR MENU ===");
        for (Menu menu : menuList) {
            System.out.println("ID: " + menu.getMenuId());
            System.out.println("Nama: " + menu.getNamaMenu());
            System.out.println("Kategori: " + menu.getKategori());
            System.out.println("Harga: Rp " + menu.getHarga());
            System.out.println("Stok: " + menu.getStok());
            System.out.println("Deskripsi: " + menu.getDeskripsi());
            System.out.println("------------------------");
        }
    }

    private static void addMenu() {
        System.out.print("Nama Menu: ");
        String nama = scanner.nextLine();
        System.out.print("Kategori: ");
        String kategori = scanner.nextLine();
        System.out.print("Harga: ");
        double harga = scanner.nextDouble();
        System.out.print("Stok: ");
        int stok = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Deskripsi: ");
        String deskripsi = scanner.nextLine();

        Menu menu = new Menu(nama, kategori, harga, stok, deskripsi);
        if (menuDAO.createMenu(menu)) {
            System.out.println("Menu berhasil ditambahkan!");
        } else {
            System.out.println("Gagal menambahkan menu!");
        }
    }

    private static void updateMenu() {
        System.out.print("ID Menu yang akan diupdate: ");
        int menuId = scanner.nextInt();
        scanner.nextLine();

        Menu menu = menuDAO.getMenuById(menuId);
        if (menu != null) {
            System.out.println("Data saat ini:");
            System.out.println("Nama: " + menu.getNamaMenu());
            System.out.println("Kategori: " + menu.getKategori());
            System.out.println("Harga: " + menu.getHarga());
            System.out.println("Stok: " + menu.getStok());
            System.out.println("Deskripsi: " + menu.getDeskripsi());

            System.out.print("Nama Menu baru: ");
            menu.setNamaMenu(scanner.nextLine());
            System.out.print("Kategori baru: ");
            menu.setKategori(scanner.nextLine());
            System.out.print("Harga baru: ");
            menu.setHarga(scanner.nextDouble());
            System.out.print("Stok baru: ");
            menu.setStok(scanner.nextInt());
            scanner.nextLine();
            System.out.print("Deskripsi baru: ");
            menu.setDeskripsi(scanner.nextLine());

            if (menuDAO.updateMenu(menu)) {
                System.out.println("Menu berhasil diupdate!");
            } else {
                System.out.println("Gagal mengupdate menu!");
            }
        } else {
            System.out.println("Menu tidak ditemukan!");
        }
    }

    private static void deleteMenu() {
        System.out.print("ID Menu yang akan dihapus: ");
        int menuId = scanner.nextInt();
        scanner.nextLine();

        if (menuDAO.deleteMenu(menuId)) {
            System.out.println("Menu berhasil dihapus!");
        } else {
            System.out.println("Gagal menghapus menu!");
        }
    }

    //search menu
    private static void searchMenu() {
        System.out.print("Masukkan kata kunci (nama atau kategori): ");
        String keyword = scanner.nextLine();

        List<Menu> menuList = menuDAO.searchMenu(keyword);
        if (menuList.isEmpty()) {
            System.out.println("Menu tidak ditemukan!");
        } else {
            System.out.println("\n=== HASIL PENCARIAN ===");
            for (Menu menu : menuList) {
                System.out.println("ID: " + menu.getMenuId());
                System.out.println("Nama: " + menu.getNamaMenu());
                System.out.println("Kategori: " + menu.getKategori());
                System.out.println("Harga: Rp " + menu.getHarga());
                System.out.println("Stok: " + menu.getStok());
                System.out.println("Deskripsi: " + menu.getDeskripsi());
                System.out.println("------------------------");
            }
        }
    }

    private static void createNewOrder() {
        Order order = new Order(currentUser.getUserId());
        boolean addingItems = true;

        while (addingItems) {
            viewAllMenu();
            System.out.print("Pilih ID Menu: ");
            int menuId = scanner.nextInt();
            System.out.print("Jumlah: ");
            int jumlah = scanner.nextInt();
            scanner.nextLine();

            Menu menu = menuDAO.getMenuById(menuId);
            if (menu == null) {
                System.out.println("Menu tidak ditemukan!");
                continue;
            }

            if (jumlah <= 0) {
                System.out.println("Jumlah harus lebih dari nol.");
                continue;
            }

            // Check stock before adding
            if (jumlah > menu.getStok()) {
                System.out.println("Stok tidak mencukupi. Stok tersedia: " + menu.getStok());
                continue;
            }

            OrderItem item = new OrderItem(order.getOrderId(), menuId, jumlah, menu.getHarga());
            order.addItem(item);
            System.out.println("Item ditambahkan: " + menu.getNamaMenu() + " (x" + jumlah + ")");

            System.out.print("Ingin menambah item lain? (y/n): ");
            String response = scanner.nextLine();
            if (!response.equalsIgnoreCase("y")) {
                addingItems = false;
            }
        }

        if (order.getOrderItems().isEmpty()) {
            System.out.println("Tidak ada item yang dipesan. Batalkan pembuatan pesanan.");
            return;
        }

        System.out.println("Total Harga: Rp " + order.getTotalHarga());
        System.out.print("Konfirmasi pesanan (y/n): ");
        String konfirmasi = scanner.nextLine();

        if (konfirmasi.equalsIgnoreCase("y")) {
            boolean success = orderDAO.createOrder(order);
            if (success) {
                System.out.println("Pesanan berhasil dibuat!");
                // Stock is decreased automatically in DAO createOrder method
            } else {
                System.out.println("Gagal membuat pesanan!");
            }
        } else {
            System.out.println("Pesanan dibatalkan.");
        }
    }

    private static void viewCustomerOrderHistory() {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT om.order_id, om.tanggal_pesan, om.status, om.total_harga, " +
                         "oi.item_id, oi.menu_id, oi.jumlah, oi.harga_satuan, oi.subtotal, m.nama_menu " +
                         "FROM orders_master om " +
                         "JOIN order_items oi ON om.order_id = oi.order_id " +
                         "JOIN menu m ON oi.menu_id = m.menu_id " +
                         "WHERE om.user_id = ? " +
                         "ORDER BY om.order_id DESC, oi.item_id ASC";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, currentUser.getUserId());

            ResultSet rs = stmt.executeQuery();

            if (!rs.isBeforeFirst()) {
                System.out.println("Belum ada riwayat pesanan.");
                return;
            }

            int currentOrderId = -1;
            while (rs.next()) {
                int orderId = rs.getInt("order_id");
                if (orderId != currentOrderId) {
                    if (currentOrderId != -1) {
                        System.out.println("------------------------");
                    }
                    currentOrderId = orderId;
                    System.out.println("\nID Pesanan: " + orderId);
                    System.out.println("Tanggal Pesan: " + rs.getTimestamp("tanggal_pesan"));
                    System.out.println("Status: " + rs.getString("status"));
                    System.out.println("Total Harga: Rp " + rs.getDouble("total_harga"));
                    System.out.println("--- Item Pesanan ---");
                }
                System.out.println(" - " + rs.getString("nama_menu") + " (x" + rs.getInt("jumlah") + ") - Rp " + rs.getDouble("subtotal"));
            }
            System.out.println("------------------------");
        } catch (SQLException e) {
            System.out.println("Error melihat riwayat pesanan: " + e.getMessage());
        }
    }

    private static void viewAllOrders() {
        List<Order> orderList = orderDAO.getAllOrders();
        if (orderList.isEmpty()) {
            System.out.println("Tidak ada pesanan!");
            return;
        }

        System.out.println("\n=== SEMUA PESANAN ===");
        for (Order order : orderList) {
            System.out.println("ID Pesanan: " + order.getOrderId());
            System.out.println("Customer: " + order.getCustomerName());
            System.out.println("Tanggal: " + order.getTanggalPesan());
            System.out.println("Status: " + order.getStatus());
            System.out.println("Total: Rp " + order.getTotalHarga());
            System.out.println("--- Item Pesanan ---");
            for (OrderItem item : order.getOrderItems()) {
                System.out.println("Menu: " + item.getNamaMenu() + " (x" + item.getJumlah() + ") - Rp " + item.getSubtotal());
            }
            System.out.println("------------------------");
        }
    }

    private static void manageOrderStatus() {
        viewAllOrders();
        System.out.print("Masukkan ID Pesanan yang akan diubah statusnya: ");
        int orderId = scanner.nextInt();
        scanner.nextLine();

        Order order = orderDAO.getOrderById(orderId);
        if (order == null) {
            System.out.println("Pesanan tidak ditemukan!");
            return;
        }

        System.out.println("\n=== DETAIL PESANAN ===");
        System.out.println("ID Pesanan: " + order.getOrderId());
        System.out.println("Customer: " + order.getCustomerName());
        System.out.println("Tanggal: " + order.getTanggalPesan());
        System.out.println("Status Saat Ini: " + order.getStatus());
        System.out.println("Total: Rp " + order.getTotalHarga());
        System.out.println("--- Item Pesanan ---");
        for (OrderItem item : order.getOrderItems()) {
            System.out.println("Menu: " + item.getNamaMenu() + " (x" + item.getJumlah() + ") - Rp " + item.getSubtotal());
        }

        System.out.println("\n=== PILIH STATUS BARU ===");
        System.out.println("1. pending");
        System.out.println("2. confirmed");
        System.out.println("3. preparing");
        System.out.println("4. ready");
        System.out.println("5. delivered");
        System.out.println("6. cancelled");
        System.out.print("Pilih status (1-6): ");

        int statusChoice = scanner.nextInt();
        scanner.nextLine();

        String[] statusOptions = {"pending", "confirmed", "preparing", "ready", "delivered", "cancelled"};

        if (statusChoice >= 1 && statusChoice <= 6) {
            String newStatus = statusOptions[statusChoice - 1];

            System.out.print("Konfirmasi ubah status ke '" + newStatus + "' (y/n): ");
            String konfirmasi = scanner.nextLine();

            if (konfirmasi.equalsIgnoreCase("y")) {
                if (orderDAO.updateOrderStatus(orderId, newStatus)) {
                    System.out.println("Status pesanan berhasil diubah ke: " + newStatus);
                } else {
                    System.out.println("Gagal mengubah status pesanan!");
                }
            } else {
                System.out.println("Perubahan status dibatalkan.");
            }
        } else {
            System.out.println("Pilihan status tidak valid!");
        }
    }

    private static void viewOrdersByStatus() {
        System.out.println("\n=== PILIH STATUS ===");
        System.out.println("1. pending");
        System.out.println("2. confirmed");
        System.out.println("3. preparing");
        System.out.println("4. ready");
        System.out.println("5. delivered");
        System.out.println("6. cancelled");
        System.out.print("Pilih status (1-6): ");

        int statusChoice = scanner.nextInt();
        scanner.nextLine();

        String[] statusOptions = {"pending", "confirmed", "preparing", "ready", "delivered", "cancelled"};

        if (statusChoice >= 1 && statusChoice <= 6) {
            String selectedStatus = statusOptions[statusChoice - 1];
            List<Order> orderList = orderDAO.getOrdersByStatus(selectedStatus);

            if (orderList.isEmpty()) {
                System.out.println("Tidak ada pesanan dengan status: " + selectedStatus);
                return;
            }

            System.out.println("\n=== PESANAN DENGAN STATUS: " + selectedStatus.toUpperCase() + " ===");
            for (Order order : orderList) {
                System.out.println("ID Pesanan: " + order.getOrderId());
                System.out.println("Customer: " + order.getCustomerName());
                System.out.println("Tanggal: " + order.getTanggalPesan());
                System.out.println("Total: Rp " + order.getTotalHarga());
                System.out.println("--- Item Pesanan ---");
                for (OrderItem item : order.getOrderItems()) {
                    System.out.println("Menu: " + item.getNamaMenu() + " (x" + item.getJumlah() + ") - Rp " + item.getSubtotal());
                }
                System.out.println("------------------------");
            }
        } else {
            System.out.println("Pilihan status tidak valid!");
        }
    }

    private static void logout() {
        currentUser = null;
        isAdmin = false;
        System.out.println("Logout berhasil!");
    }
}

