package com.empresa.projetoapi.api.controller;

import com.empresa.projetoapi.model.User;
import com.empresa.projetoapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService=userService;
    }

    @GetMapping(value = "/user", produces = {"application/json", "applications/xml"})
    public User getUser(@RequestParam Integer id){
        Optional<User> user=userService.getUser(id);
        return(User)user.orElse(null);
    }

    @GetMapping("/users")
    public List<User> getAllUsers(){
        return userService.getUserList();
    }

    @PostMapping(value = "/user", consumes = {"application/json", "applications/xml"})
    public ResponseEntity<String> createUser(@RequestBody User user){
        userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body("ok");
    }

    @PutMapping("/user")
    public ResponseEntity<String> updateUser(@RequestParam Integer id, @RequestBody User updatedUser) {
        Optional<User> userOptional = userService.updateUser(id, updatedUser);
        if (userOptional.isPresent()) {
            return ResponseEntity.ok("User updated successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
    }

    @DeleteMapping("/user")
    public ResponseEntity<String> deleteUser(@RequestParam Integer id) {
        boolean deleted = userService.deleteUser(id);
        if (deleted) {
            return ResponseEntity.ok("User deleted successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
    }

}
