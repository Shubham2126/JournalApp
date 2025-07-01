package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.service.JournalEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@RestController
public class entrypoint {

    @Autowired
    private JournalEntryService journalEntryService;

    @GetMapping("/get-data")
    public ResponseEntity<?> getData() {
        List<JournalEntry> data = journalEntryService.getAll();
        return new ResponseEntity<>(data,HttpStatus.OK);
    }

    @PostMapping("/set-data")
    public ResponseEntity<?> createEntry(@RequestBody JournalEntry data){
        try {
            data.setDate(LocalDateTime.now());
            journalEntryService.saveEntry(data);
            return new ResponseEntity<>(data,HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("user/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
       Optional<JournalEntry> data = journalEntryService.findById(id);
       if(data.isPresent()) {
           return new ResponseEntity<>(data.get(), HttpStatus.OK);
       }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @DeleteMapping("user/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        journalEntryService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }
    @PutMapping("user/{id}")
    public ResponseEntity<JournalEntry> update(@PathVariable Long id, @RequestBody JournalEntry data) {
        JournalEntry old = journalEntryService.findById(id).orElse(null);
        if(old != null) {
            old.setTitle(data.getTitle() != null && !data.getTitle().isEmpty() ? data.getTitle() : old.getTitle());
            old.setContent(data.getContent() != null && !data.getContent().isEmpty() ? data.getContent() : old.getContent());
            journalEntryService.saveEntry(old);
            return new ResponseEntity<>(old, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
