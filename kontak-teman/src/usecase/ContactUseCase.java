package usecase;

import domain.entity.Contact;
import domain.entity.SortOption;
import domain.exception.EntityNotFoundException;
import domain.exception.ValidationException;
import domain.repository.IContactRepository;

import java.util.List;
import java.util.Optional;

public class ContactUseCase {
    private final IContactRepository repository;

    public ContactUseCase(IContactRepository repository) {
        this.repository = repository;
    }

    public List<Contact> getAllContacts() {
        return repository.findAll();
    }

    public Contact addContact(String name, String phone, String email) throws ValidationException {
        if (name == null || name.trim().isEmpty()) throw new ValidationException("Nama tidak boleh kosong");
        if (phone == null || phone.trim().isEmpty()) throw new ValidationException("Telepon tidak boleh kosong");
        if (email == null || email.trim().isEmpty()) throw new ValidationException("Email tidak boleh kosong");
        
        return repository.save(name.trim(), phone.trim(), email.trim());
    }

    public Contact updateContact(int id, String name, String phone, String email) throws EntityNotFoundException {
        Optional<Contact> found = repository.findById(id);
        if (found.isEmpty()) {
            throw new EntityNotFoundException(id);
        }

        Contact contact = found.get();
        if (name != null) contact.changeName(name.trim());
        if (phone != null) contact.changePhone(phone.trim());
        if (email != null) contact.changeEmail(email.trim());
        
        repository.update(contact);
        return contact;
    }

    public void removeContact(int id) throws EntityNotFoundException {
        if (!repository.deleteById(id)) {
            throw new EntityNotFoundException(id);
        }
    }

    public List<Contact> searchContacts(String keyword) {
        String lowerKeyword = keyword.toLowerCase();
        return repository.findBy(c -> c.getName().toLowerCase().contains(lowerKeyword));
    }

    public List<Contact> sortContacts(SortOption option) {
        return repository.findAll().stream()
                .sorted(option.comparator())
                .toList();
    }
}