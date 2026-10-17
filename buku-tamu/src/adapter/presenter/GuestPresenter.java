package adapter.presenter;

import domain.entity.Guest;
import java.util.List;

public class GuestPresenter {
    
    private String format(Guest g) {
        return String.format("[%d] %s | Tujuan: %s", g.getId(), g.getName(), g.getPurpose());
    }

    private void printList(List<Guest> guests, String header, String emptyMessage) {
        System.out.println(header);
        if (guests.isEmpty()) {
            System.out.println(emptyMessage);
            return;
        }
        for (Guest g : guests) {
            System.out.println(format(g));
        }
    }

    public void showGuests(List<Guest> guests) {
        printList(guests, "Daftar Tamu:", "- Data tamu belum tersedia!");
    }

    public void showSearchResults(List<Guest> guests, String keyword) {
        printList(guests, "Hasil Pencarian: \"" + keyword + "\"", "- Tamu tidak ditemukan!");
    }

    public void showAddSuccess(Guest g) {
        System.out.printf("Berhasil mendaftarkan tamu: %s%n", format(g));
    }

    public void showRemoveSuccess() {
        System.out.println("Berhasil menghapus tamu.");
    }

    public void showRemoveFailed(int id) {
        System.out.printf("[!] Gagal menghapus tamu dengan ID: %d.%n", id);
    }

    public void showInvalidChoice() {
        System.out.println("[!] Pilihan tidak dimengerti.");
    }

    public void showInvalidId() {
        System.out.println("[!] ID tidak valid!");
    }
}