package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.service.JournalEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class entrypoint {

    @Autowired
    private JournalEntryService journalEntryService;

    @GetMapping("/get-data")
    public List<JournalEntry> getData() {
        return journalEntryService.getAll();
    }

    @PostMapping("/set-data")
    public JournalEntry createEntry(@RequestBody JournalEntry data){
        data.setDate(LocalDateTime.now());
        journalEntryService.saveEntry(data);
        return data;
    }

    @GetMapping("user/{id}")
    public JournalEntry getById(@PathVariable Long id) {
       return journalEntryService.findById(id).orElse(null);

    }
    @DeleteMapping("user/{id}")
    public void delete(@PathVariable Long id) {
        journalEntryService.deleteById(id);
    }
    @PutMapping("user/{id}")
    public JournalEntry update(@PathVariable Long id, @RequestBody JournalEntry data) {
        JournalEntry old = journalEntryService.findById(id).orElse(null);
        if(old != null) {
            old.setTitle(data.getTitle() != null && !data.getTitle().isEmpty() ? data.getTitle() : old.getTitle());
            old.setContent(data.getContent() != null && !data.getContent().isEmpty() ? data.getContent() : old.getContent());

        }
        journalEntryService.saveEntry(old);
        return old;
    }
}
