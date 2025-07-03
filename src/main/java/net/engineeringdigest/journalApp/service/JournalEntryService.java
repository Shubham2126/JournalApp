package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.entity.UserEntry;
import net.engineeringdigest.journalApp.repository.JournalRepo;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JournalEntryService {
    @Autowired
    private JournalRepo journalRepo;
    @Autowired
    private UserService userService;


    public void saveEntry(JournalEntry data, String userName) {
        UserEntry userEntry = userService.getUser(userName);
        JournalEntry saved = journalRepo.save(data);
        userEntry.getEntries().add(saved);
        userService.register(userEntry);
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

    public void deleteById(ObjectId id, String userName) {
        UserEntry userEntry = userService.getUser(userName);
        userEntry.getEntries().removeIf(entry -> entry.getId().equals(id));
        userService.register(userEntry);
        journalRepo.deleteById(id);
    }

}
