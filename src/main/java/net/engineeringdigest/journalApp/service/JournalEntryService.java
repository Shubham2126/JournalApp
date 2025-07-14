package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.entity.UserEntry;
import net.engineeringdigest.journalApp.repository.JournalRepo;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class JournalEntryService {
    @Autowired
    private JournalRepo journalRepo;
    @Autowired
    private UserService userService;

    @Transactional
    public void saveEntry(JournalEntry data, String userName) {
        UserEntry userEntry = userService.getUser(userName);
        JournalEntry saved = journalRepo.save(data);
        userEntry.getEntries().add(saved);
        userService.save(userEntry);
    }

    public void saveEntry(JournalEntry data) {
        JournalEntry saved = journalRepo.save(data);
    }

    public List<JournalEntry> getAll() {
        return journalRepo.findAll();
    }

    public Optional<JournalEntry> findById(ObjectId id) {
        return journalRepo.findById(id);
    }

    public boolean deleteById(ObjectId id, String userName) {
        boolean removed = false;

        try {
            UserEntry userEntry = userService.getUser(userName);
             removed = userEntry.getEntries().removeIf(entry -> entry.getId().equals(id));
            if (removed) {
            userService.save(userEntry);
            journalRepo.deleteById(id);

            }
        } catch (Exception e) {
            throw new RuntimeException("Error deleting journal entry", e);
        }
        return removed;
    }
}
