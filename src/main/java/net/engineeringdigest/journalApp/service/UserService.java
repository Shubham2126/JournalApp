package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.entity.UserEntry;
import net.engineeringdigest.journalApp.repository.UserRepo;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    public UserRepo userRepo;

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    public void save(UserEntry user){
        userRepo.save(user);
    }

    public void saveNewUser(UserEntry user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList("user"));
        userRepo.save(user);
    }

    public List<UserEntry> getAll(){
       return userRepo.findAll();
    }

    public Optional<UserEntry> findById(ObjectId id){
        return userRepo.findById(id);

    }
    public void deleteById(ObjectId id){
        userRepo.deleteById(id);
    }

    public UserEntry getUser(String name){
      return userRepo.findByUserName(name);
    }


}
