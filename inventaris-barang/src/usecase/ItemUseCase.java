package usecase;

import domain.entity.Item;
import domain.entity.SortOption;
import domain.exception.EntityNotFoundException;
import domain.exception.ValidationException;
import domain.repository.IItemRepository;

import java.util.List;
import java.util.Optional;

public class ItemUseCase {
    private final IItemRepository repository;

    public ItemUseCase(IItemRepository repository) {
        this.repository = repository;
    }

    public List<Item> getAllItems() {
        return repository.findAll();
    }

    public Item addItem(String name, int quantity, String category) throws ValidationException {
        if (name == null || name.trim().isEmpty()) {
            throw new ValidationException("Nama barang tidak boleh kosong");
        }
        if (quantity <= 0) {
            throw new ValidationException("Jumlah stok tidak valid");
        }
        if (category == null || category.trim().isEmpty()) {
            throw new ValidationException("Kategori tidak boleh kosong");
        }
        return repository.save(name.trim(), quantity, category.trim());
    }

    // Revisi: Parameter quantity diubah menjadi Integer (object) untuk mendukung partial update (null = tidak diubah)
    public Item updateStock(int id, Integer quantity) throws EntityNotFoundException, ValidationException {
        Optional<Item> found = repository.findById(id);
        if (found.isEmpty()) {
            throw new EntityNotFoundException(id);
        }

        Item item = found.get();
        
        // Hanya ubah stok jika quantity tidak null
        if (quantity != null) {
            if (quantity <= 0) {
                throw new ValidationException("Jumlah stok tidak valid");
            }
            item.changeQuantity(quantity);
            repository.update(item);
        }
        return item;
    }

    public void removeItem(int id) throws EntityNotFoundException {
        boolean deleted = repository.deleteById(id);
        if (!deleted) {
            throw new EntityNotFoundException(id);
        }
    }

    public List<Item> searchItems(String keyword) {
        String lowerKeyword = keyword.toLowerCase();
        return repository.findBy(item -> item.getName().toLowerCase().contains(lowerKeyword));
    }

    public List<Item> sortItems(SortOption option) {
        return repository.findAll().stream()
                .sorted(option.comparator())
                .toList();
    }
}