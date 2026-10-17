package framework.view;

import adapter.presenter.FinancePresenter;
import domain.entity.SortOption;
import domain.exception.EntityNotFoundException;
import domain.exception.ValidationException;
import framework.util.InputUtil;
import usecase.FinanceUseCase;

public class FinanceView {
    private final FinanceUseCase useCase;
    private final FinancePresenter presenter;

    public FinanceView(FinanceUseCase useCase, FinancePresenter presenter) {
        this.useCase = useCase;
        this.presenter = presenter;
    }

    public void show() {
        boolean running = true;
        while (running) {
            presenter.showTransactions(useCase.getAllTransactions(), useCase.getBalance());
            printMenu();

            String input = InputUtil.input("Pilih");
            System.out.println();

            switch (input) {
                case "1" -> addIncome();
                case "2" -> addExpense();
                case "3" -> searchTransaction();
                case "4" -> sortTransactions();
                case "5" -> showBalance();
                case "6" -> removeTransaction();
                case "x" -> running = false;
                default -> presenter.showInvalidChoice();
            }

            if (running) {
                System.out.println();
            }
        }
    }

    private void printMenu() {
        System.out.println("Menu:");
        System.out.println("1. Tambah Pemasukan");
        System.out.println("2. Tambah Pengeluaran");
        System.out.println("3. Cari");
        System.out.println("4. Urutkan");
        System.out.println("5. Lihat Saldo");
        System.out.println("6. Hapus");
        System.out.println("x. Keluar");
    }

    private void addIncome() {
        System.out.println("[Tambah Pemasukan]");
        String desc = InputUtil.input("Keterangan (x Jika Batal)");
        if (desc.equals("x")) return;

        String amountStr = InputUtil.input("Jumlah");
        try {
            long amount = parseAmount(amountStr);
            presenter.showAddSuccess(useCase.addIncome(desc, amount));
        } catch (ValidationException e) {
            presenter.showInvalidAmount();
        }
    }

    private void addExpense() {
        System.out.println("[Tambah Pengeluaran]");
        String desc = InputUtil.input("Keterangan (x Jika Batal)");
        if (desc.equals("x")) return;

        String amountStr = InputUtil.input("Jumlah");
        try {
            long amount = parseAmount(amountStr);
            presenter.showAddSuccess(useCase.addExpense(desc, amount));
        } catch (ValidationException e) {
            presenter.showInvalidAmount();
        }
    }

    private void searchTransaction() {
        System.out.println("[Cari Transaksi]");
        String keyword = InputUtil.input("Kata Kunci (x Jika Batal)");
        if (!keyword.equals("x")) {
            presenter.showSearchResults(useCase.searchTransactions(keyword), keyword);
        }
    }

    private void sortTransactions() {
        System.out.println("[Urutkan Transaksi]");
        System.out.println("1. Jumlah (Terkecil)");
        System.out.println("2. Jumlah (Terbesar)");
        System.out.println("3. Pemasukan Dulu");
        System.out.println("4. Pengeluaran Dulu");
        System.out.println("x. Batal");

        String input = InputUtil.input("Pilih");
        System.out.println();

        if (input.equals("x")) return;

        SortOption option = mapSortOption(input);
        if (option == null) {
            presenter.showInvalidSortOption();
            return;
        }

        presenter.showSortedTransactions(useCase.sortTransactions(option));
    }

    private void showBalance() {
        System.out.printf("Saldo saat ini: Rp %d%n", useCase.getBalance());
    }

    private void removeTransaction() {
        System.out.println("[Hapus Transaksi]");
        // Perbaikan: label prompt disesuaikan dengan expected output
        String strId = InputUtil.input("ID Transaksi (x Jika Batal)");
        if (strId.equals("x")) return;

        try {
            int id = parseId(strId);
            useCase.removeTransaction(id);
            presenter.showRemoveSuccess();
        } catch (ValidationException e) {
            presenter.showInvalidId();
        } catch (EntityNotFoundException e) {
            presenter.showRemoveFailed(e.getId());
        }
    }

    private int parseId(String value) throws ValidationException {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            throw new ValidationException("ID harus berupa angka numerik");
        }
    }

    private long parseAmount(String value) throws ValidationException {
        try {
            long amount = Long.parseLong(value);
            if (amount <= 0) {
                throw new ValidationException("Jumlah harus lebih dari 0");
            }
            return amount;
        } catch (NumberFormatException e) {
            throw new ValidationException("Jumlah harus berupa angka numerik");
        }
    }

    private SortOption mapSortOption(String input) {
        return switch (input) {
            case "1" -> SortOption.AMOUNT_ASC;
            case "2" -> SortOption.AMOUNT_DESC;
            case "3" -> SortOption.INCOME_FIRST;
            case "4" -> SortOption.EXPENSE_FIRST;
            default -> null;
        };
    }
}