package adapter.presenter;

import domain.entity.Contact;
import java.util.List;

public class ContactPresenter {
    
    private String format(Contact c) {
        return String.format("[%d] %s | Telp: %s | Email: %s", c.getId(), c.getName(), c.getPhone(), c.getEmail());
    }

    private void printList(List<Contact> contacts, String header, String emptyMessage) {
        System.out.println(header);
        if (contacts.isEmpty()) {
            System.out.println(emptyMessage);
            return;
        }
        for (Contact c : contacts) {
            System.out.println(format(c));
        }
    }

    public void showContacts(List<Contact> contacts) {
        printList(contacts, "Daftar Kontak:", "- Data kontak belum tersedia!");
    }

    public void showSearchResults(List<Contact> contacts, String keyword) {
        printList(contacts, "Hasil Pencarian: \"" + keyword + "\"", "- Kontak tidak ditemukan!");
    }

    public void showSortedContacts(List<Contact> contacts) {
        printList(contacts, "Daftar Kontak (Terurut):", "- Data kontak belum tersedia!");
    }

    public void showAddSuccess(Contact c) {
        System.out.printf("Berhasil menambah kontak: %s%n", format(c));
    }

    // PERBAIKAN: Hapus parameter Contact dan hapus pencetakan detail kontak
    public void showUpdateSuccess() {
        System.out.println("Berhasil mengubah kontak.");
    }

    public void showRemoveSuccess() {
        System.out.println("Berhasil menghapus kontak.");
    }

    public void showRemoveFailed(int id) {
        System.out.printf("[!] Gagal menghapus kontak dengan ID: %d.%n", id);
    }

    public void showUpdateFailed(int id) {
        System.out.printf("[!] Gagal mengubah kontak dengan ID: %d.%n", id);
    }

    public void showInvalidChoice() {
        System.out.println("[!] Pilihan tidak dimengerti.");
    }

    public void showInvalidId() {
        System.out.println("[!] ID tidak valid!");
    }

    public void showInvalidSortOption() {
        System.out.println("[!] Pilihan tidak valid!");
    }
}