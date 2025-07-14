package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.entity.UserEntry;
import net.engineeringdigest.journalApp.service.JournalEntryService;
import net.engineeringdigest.journalApp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/journal")
public class entrypoint {

    @Autowired
    private JournalEntryService journalEntryService;
    @Autowired
    private UserService userService;

    @GetMapping("/getAll")
    public ResponseEntity<?> getData() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        UserEntry user = userService.getUser(userName);
        List<JournalEntry> data = user.getEntries();
        if(!data.isEmpty()) {
        return new ResponseEntity<>(data,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/set-data")
    public ResponseEntity<?> createEntry(@RequestBody JournalEntry data) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userName = authentication.getName();
            data.setDate(LocalDateTime.now());
            journalEntryService.saveEntry(data, userName);
            return new ResponseEntity<>(data,HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getById/{myId}")
    public ResponseEntity<?> getById(@PathVariable ObjectId myId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        UserEntry user = userService.getUser(userName);
       List<JournalEntry> entries = user.getEntries().stream().filter(data -> data.getId().equals(myId)).collect(Collectors.toList());
        if(!entries.isEmpty()) {
            Optional<JournalEntry> journalEntries = journalEntryService.findById(myId);

       if(journalEntries.isPresent()) {
           return new ResponseEntity<>(entries, HttpStatus.OK);
       }}
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @DeleteMapping("/deleteById/{id}")
    public ResponseEntity<?> delete(@PathVariable ObjectId id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        boolean removed = journalEntryService.deleteById(id,userName);
        if(!removed) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }
    @PutMapping("/updateById/{id}")
    public ResponseEntity<JournalEntry> update(@PathVariable ObjectId id,
                                               @RequestBody JournalEntry data) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        UserEntry user = userService.getUser(userName);
        List<JournalEntry> entries = user.getEntries().stream().filter(x -> x.getId().equals(id)).collect(Collectors.toList());
        if(!entries.isEmpty()) {
            Optional<JournalEntry> journalEntries = journalEntryService.findById(id);
            if(journalEntries.isPresent()) {
                    JournalEntry old = journalEntries.get();
                    old.setTitle(data.getTitle() != null && !data.getTitle().isEmpty() ? data.getTitle() : old.getTitle());
                    old.setContent(data.getContent() != null && !data.getContent().isEmpty() ? data.getContent() : old.getContent());
                    journalEntryService.saveEntry(old);
                    return new ResponseEntity<>(old, HttpStatus.OK);

            }}

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
