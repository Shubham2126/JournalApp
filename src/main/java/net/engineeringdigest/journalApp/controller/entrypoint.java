package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.entity.UserEntry;
import net.engineeringdigest.journalApp.service.JournalEntryService;
import net.engineeringdigest.journalApp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@RestController
public class entrypoint {

    @Autowired
    private JournalEntryService journalEntryService;
    @Autowired
    private UserService userService;

    @GetMapping("/get-data")
    public ResponseEntity<?> getData() {
        List<JournalEntry> data = journalEntryService.getAll();
        return new ResponseEntity<>(data,HttpStatus.OK);
    }

    @PostMapping("/set-data/{userName}")
    public ResponseEntity<?> createEntry(@RequestBody JournalEntry data, @PathVariable String userName
    ) {
        try {
            data.setDate(LocalDateTime.now());
            journalEntryService.saveEntry(data, userName);
            return new ResponseEntity<>(data,HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("journal/{userName}")
    public ResponseEntity<?> getById(@PathVariable String userName) {
        UserEntry user = userService.getUser(userName);
       List<JournalEntry> data = user.getEntries();
       if(!data.isEmpty()) {
           return new ResponseEntity<>(data, HttpStatus.OK);
       }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @DeleteMapping("journal/{userName}/{id}")
    public ResponseEntity<?> delete(@PathVariable ObjectId id, @PathVariable String userName) {
        journalEntryService.deleteById(id,userName);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }
    @PutMapping("journal/{userName}/{id}")
    public ResponseEntity<JournalEntry> update(@PathVariable ObjectId id,
                                               @PathVariable String userName,
                                               @RequestBody JournalEntry data) {
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
