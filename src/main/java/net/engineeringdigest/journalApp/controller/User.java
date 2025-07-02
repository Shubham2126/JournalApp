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
import java.util.List;
import java.util.Optional;

@RestController
public class User {

    @Autowired
    UserService userService;

    @PostMapping("user")
    public ResponseEntity<?> save(@RequestBody UserEntry user){
        try {
        userService.register(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("user")
    public ResponseEntity<?> getALl(){
        List<UserEntry> users = userService.getAll();
        return new ResponseEntity<>(users,HttpStatus.OK);
    }

    @GetMapping("user/{id}")
    public ResponseEntity<?> getById(@PathVariable ObjectId id){
        return new ResponseEntity<>(userService.findById(id),HttpStatus.OK);
    }

    @PutMapping("user/{userName}")
    public ResponseEntity<?> update(@RequestBody UserEntry user, @PathVariable String userName){
        UserEntry oldData = userService.getUser(userName);
        if(oldData != null){
            oldData.setUserName(user.getUserName());
            oldData.setPassword(user.getPassword());
            userService.register(oldData);
        }
        return new ResponseEntity<>(oldData,HttpStatus.OK);

    }

    @DeleteMapping("user/{id}")
    public void deleteById(@PathVariable ObjectId id){
        userService.deleteById(id);
    }
}
