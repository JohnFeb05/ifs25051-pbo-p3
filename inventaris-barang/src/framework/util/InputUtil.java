package framework.util;

import java.util.Scanner;

/**
 * Utility untuk membaca input string dari keyboard.
 * Berada di layer framework karena bergantung pada {@link System.in}.
 */
public class InputUtil {
    /** Scanner bersama untuk seluruh aplikasi (satu instance cukup). */
    private static final Scanner scanner = new Scanner(System.in);

    /**
     * Menampilkan prompt dan membaca satu baris input dari user.
     *
     * @param info label yang ditampilkan sebelum input
     * @return teks yang diketik user, atau "x" jika input stream berakhir (EOF)
     */
    public static String input(String info) {
        System.out.print(info + " : ");
        
        // PERBAIKAN: Cek apakah masih ada baris input yang tersedia.
        // Jika tidak (EOF), kembalikan "x" agar loop menu di View 
        // dapat terminated secara graceful tanpa melempar exception.
        if (scanner.hasNextLine()) {
            return scanner.nextLine();
        }
        
        return "x"; 
    }
}