package usecase;

import domain.entity.SortOption;
import domain.entity.Transaction;
import domain.entity.TransactionType;
import domain.exception.EntityNotFoundException;
import domain.exception.ValidationException;
import domain.repository.ITransactionRepository;

import java.util.List;

public class FinanceUseCase {
    private final ITransactionRepository repository;

    public FinanceUseCase(ITransactionRepository repository) {
        this.repository = repository;
    }

    public List<Transaction> getAllTransactions() {
        return repository.findAll();
    }

    public Transaction addIncome(String description, long amount) throws ValidationException {
        validateTransaction(description, amount);
        return repository.save(description, amount, TransactionType.INCOME);
    }

    public Transaction addExpense(String description, long amount) throws ValidationException {
        validateTransaction(description, amount);
        return repository.save(description, amount, TransactionType.EXPENSE);
    }

    private void validateTransaction(String description, long amount) throws ValidationException {
        if (description == null || description.trim().isEmpty()) {
            throw new ValidationException("Keterangan tidak boleh kosong");
        }
        if (amount <= 0) {
            throw new ValidationException("Jumlah harus lebih dari 0");
        }
    }

    public void removeTransaction(int id) throws EntityNotFoundException {
        boolean deleted = repository.deleteById(id);
        if (!deleted) {
            throw new EntityNotFoundException(id);
        }
    }

    public List<Transaction> searchTransactions(String keyword) {
        String lowerKeyword = keyword.toLowerCase();
        return repository.findBy(t -> t.getDescription().toLowerCase().contains(lowerKeyword));
    }

    public List<Transaction> sortTransactions(SortOption option) {
        return repository.findAll().stream()
                .sorted(option.comparator())
                .toList();
    }

    public long getBalance() {
        long balance = 0;
        for (Transaction t : repository.findAll()) {
            if (t.getType() == TransactionType.INCOME) {
                balance += t.getAmount();
            } else {
                balance -= t.getAmount();
            }
        }
        return balance;
    }
}