package usecase;

import domain.entity.Activity;
import domain.entity.SortOption;
import domain.exception.EntityNotFoundException;
import domain.exception.ValidationException;
import domain.repository.IActivityRepository;

import java.util.List;
import java.util.Optional;

public class ActivityUseCase {
    private final IActivityRepository repository;

    public ActivityUseCase(IActivityRepository repository) {
        this.repository = repository;
    }

    public List<Activity> getAllActivities() {
        return repository.findAll();
    }

    public Activity addActivity(String title, String day, String time) throws ValidationException {
        if (title == null || title.trim().isEmpty()) throw new ValidationException("Judul tidak boleh kosong");
        if (day == null || day.trim().isEmpty()) throw new ValidationException("Hari tidak boleh kosong");
        if (time == null || time.trim().isEmpty()) throw new ValidationException("Pukul tidak boleh kosong");
        
        return repository.save(title.trim(), day.trim(), time.trim());
    }

    public Activity updateActivity(int id, String title, String day, String time) throws EntityNotFoundException {
        Optional<Activity> found = repository.findById(id);
        if (found.isEmpty()) {
            throw new EntityNotFoundException(id);
        }

        Activity activity = found.get();
        if (title != null) activity.changeTitle(title.trim());
        if (day != null) activity.changeDay(day.trim());
        if (time != null) activity.changeTime(time.trim());
        
        repository.update(activity);
        return activity;
    }

    public void removeActivity(int id) throws EntityNotFoundException {
        if (!repository.deleteById(id)) {
            throw new EntityNotFoundException(id);
        }
    }

    public List<Activity> searchActivities(String keyword) {
        String lowerKeyword = keyword.toLowerCase();
        return repository.findBy(a -> a.getTitle().toLowerCase().contains(lowerKeyword));
    }

    public List<Activity> sortActivities(SortOption option) {
        return repository.findAll().stream()
                .sorted(option.comparator())
                .toList();
    }
}