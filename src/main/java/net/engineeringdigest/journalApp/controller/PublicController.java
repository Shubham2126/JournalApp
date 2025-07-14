package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.entity.UserEntry;
import net.engineeringdigest.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    public UserService userService;

    @GetMapping("/health-check")
    public String check() {
        return "OK";
    }

    @PostMapping("/register")
    public ResponseEntity<?> save(@RequestBody UserEntry user){
        try {
            userService.saveNewUser(user);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
