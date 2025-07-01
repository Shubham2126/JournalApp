package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.entity.JournalEntry;
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


    public void saveEntry(JournalEntry journalEntry) {
        journalRepo.save(journalEntry);
    }

    public List<JournalEntry> getAll() {
        return journalRepo.findAll();
    }

    public Optional<JournalEntry> findById(Long id) {
        return journalRepo.findById(id);
    }
    public void deleteById(Long id) {
        journalRepo.deleteById(id);
    }

}
