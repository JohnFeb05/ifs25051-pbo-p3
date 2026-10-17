package adapter.presenter;

import domain.entity.Transaction;
import java.util.List;

public class FinancePresenter {
    
    private String format(Transaction t) {
        return String.format("[%d] %s | Rp %d | [%s]", t.getId(), t.getDescription(), t.getAmount(), t.getType().getLabel());
    }

    private void printList(List<Transaction> transactions, String header, String emptyMessage) {
        System.out.println(header);
        if (transactions.isEmpty()) {
            System.out.println(emptyMessage);
            return;
        }
        for (Transaction t : transactions) {
            System.out.println(format(t));
        }
    }

    public void showTransactions(List<Transaction> transactions, long balance) {
        printList(transactions, "Daftar Transaksi:", "- Belum ada transaksi!");
        System.out.printf("Saldo: Rp %d%n", balance);
    }

    public void showSearchResults(List<Transaction> transactions, String keyword) {
        printList(transactions, "Hasil Pencarian: \"" + keyword + "\"", "- Transaksi tidak ditemukan!");
    }

    // Revisi: Tidak menampilkan saldo setelah berhasil menambah transaksi
    public void showAddSuccess(Transaction t) {
        System.out.printf("Berhasil menambah transaksi: %s%n", format(t));
    }

    public void showRemoveSuccess() {
        System.out.println("Berhasil menghapus transaksi.");
    }

    public void showRemoveFailed(int id) {
        System.out.printf("[!] Gagal menghapus transaksi dengan ID: %d.%n", id);
    }

    public void showInvalidChoice() {
        System.out.println("[!] Pilihan tidak dimengerti.");
    }

    public void showInvalidId() {
        System.out.println("[!] ID tidak valid!");
    }

    public void showInvalidAmount() {
        System.out.println("[!] Jumlah tidak valid!");
    }
    
    public void showInvalidSortOption() {
        System.out.println("[!] Pilihan tidak valid!");
    }

    // Revisi: Tidak menampilkan saldo setelah mengurutkan transaksi
    public void showSortedTransactions(List<Transaction> transactions) {
        printList(transactions, "Daftar Transaksi (Terurut):", "- Belum ada transaksi!");
    }
}