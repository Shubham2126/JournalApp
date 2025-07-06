package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.entity.UserEntry;
import net.engineeringdigest.journalApp.repository.UserRepo;
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
import java.util.List;
import java.util.Optional;
@RestController
@RequestMapping("/user")
public class User {

@Autowired
private UserService userService;

@Autowired
private UserRepo userRepo;

//    @GetMapping("/user")
//    public ResponseEntity<?> getALl(){
//        List<UserEntry> users = userService.getAll();
//        return new ResponseEntity<>(users,HttpStatus.OK);
//    }
//
//    @GetMapping("/user/{id}")
//    public ResponseEntity<?> getById(@PathVariable ObjectId id){
//        return new ResponseEntity<>(userService.findById(id),HttpStatus.OK);
//    }
//
    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody UserEntry user){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        UserEntry oldData = userService.getUser(username);
        if(oldData != null){
            oldData.setUserName(user.getUserName());
            oldData.setPassword(user.getPassword());
            userService.save(oldData);
        }
        return new ResponseEntity<>(oldData,HttpStatus.OK);

    }
//
    @DeleteMapping("/delete")
    public void deleteById(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        userRepo.deleteByUserName(username);

    }
}
