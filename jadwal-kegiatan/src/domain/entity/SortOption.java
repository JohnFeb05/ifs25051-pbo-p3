package domain.entity;

import java.util.Comparator;
import java.util.List;

public enum SortOption {
    // PERBAIKAN: Menambahkan thenComparing(Activity::getTime) sebagai secondary sort.
    // Jika hari sama, maka akan diurutkan berdasarkan waktu.
    DAY_ASC(Comparator.comparingInt((Activity a) -> {
        List<String> days = List.of("Senin", "Selasa", "Rabu", "Kamis", "Jumat", "Sabtu", "Minggu");
        int idx = days.indexOf(a.getDay());
        return idx == -1 ? 99 : idx;
    }).thenComparing(Activity::getTime)),
    
    // Mengurutkan waktu secara natural (berlaku untuk format HH:MM)
    TIME_ASC(Comparator.comparing(Activity::getTime)),
    
    TITLE_ASC(Comparator.comparing(Activity::getTitle, String.CASE_INSENSITIVE_ORDER)),
    TITLE_DESC(Comparator.comparing(Activity::getTitle, String.CASE_INSENSITIVE_ORDER).reversed());

    private final Comparator<Activity> comparator;

    SortOption(Comparator<Activity> comparator) {
        this.comparator = comparator;
    }

    public Comparator<Activity> comparator() {
        return comparator;
    }
}