package com.springboot.usuarios.app.controller;

import com.springboot.usuarios.app.model.User;
import com.springboot.usuarios.app.service.IUsersService;
import com.springboot.usuarios.app.validation.IValidateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("/api/users")
public class UsersController {

    private final Logger logger = LoggerFactory.getLogger(UsersController.class);

    @Autowired
    private IUsersService iUsersService;

    @Autowired
    private IValidateService iValidateService;

    @GetMapping("/list")
    public ResponseEntity<?> userList() {
        return ResponseEntity.ok().body(iUsersService.findAll());
    }

    @GetMapping("/list/{id}")
    public ResponseEntity<?> searchUser(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        Optional<User> o = iUsersService.findById(id);
        boolean exist = o.isPresent();
        if (!exist) {
            logger.info("user ID: " + id + " doesn't exist");
            response.put("message", "user ID: " + id + " doesn't exist");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok().body(o.get());
    }

    @PostMapping("/create")
    public ResponseEntity<?> createUser(@Valid @RequestBody User user, BindingResult result) {
        if(result.hasErrors()) {
            Map<String, Object> errors = new HashMap<>();
            iValidateService.validateCreate(errors, result);
            return ResponseEntity.badRequest().body(errors);
        }
        user.setCreated(new Date());
        user.setLastLogin(new Date());
        user.setActive(true);
        User userDB = iUsersService.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(userDB);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        Optional<User> o = iUsersService.findById(id);
        boolean exist = o.isPresent();
        if (!exist) {
            logger.info("user ID: " + id + " doesn't exist");
            response.put("message", "user ID: " + id + " doesn't exist");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        User userBD = o.get();
        userBD.setActive(false);
        response.put("message", "User deleted !!!");
        return ResponseEntity.status(HttpStatus.CREATED).body(this.iUsersService.save(userBD));
    }

    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> updateUser(@Valid @RequestBody User user, BindingResult result, @PathVariable Long id) {

        Map<String, Object> response = new HashMap<>();
        if(result.hasErrors()) {
            Map<String, Object> errors = new HashMap<>();
            iValidateService.validateUpdate(errors, result);
            if(!errors.isEmpty()) return ResponseEntity.badRequest().body(errors);
        }

        Optional<User> o = iUsersService.findById(id);
        boolean exist = o.isPresent();
        if (!exist) {
            logger.info("user ID: " + id + " doesn't exist");
            response.put("message", "user ID: " + id + " doesn't exist");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        User userBD = o.get();
        userBD.setName(user.getName());
        userBD.setEmail(user.getEmail());
        userBD.setModified(new Date());
        userBD.setPassword(user.getPassword());
        userBD.setPhones(user.getPhones());
        iUsersService.save(userBD);
        response.put("user", userBD);
        response.put("message", "user updated");
        return ResponseEntity.status(HttpStatus.CREATED).body(userBD);
    }

}
