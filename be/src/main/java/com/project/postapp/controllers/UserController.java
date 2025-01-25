package com.project.postapp.controllers;

import com.project.postapp.models.User;
import com.project.postapp.services.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService; //UserController da Userservice'e bağlanıyor. Diğer controllerlar da ilgili servislere bağlanıyor.

    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping
    public List<User>getAllUsers(){
        return userService.getAllUsers();
    }

    @PostMapping
    public User createUser(@RequestBody User newUser){ //newUser'i, requestin bodysinden alıyoruz.
        return userService.save(newUser); //db'ye kaydedilen useri geri dönüyor.
    }

    @GetMapping("/{userId}")
    public User getUser(@PathVariable Long userId){ //userId'i URI'den alıyoruz.
        //custom Exception... userId, database'de olmayabilir.
        return userService.getUser(userId);
    }

    @PutMapping("/{userId}")
    public User updateUser(@PathVariable Long userId, @RequestBody User newUser){ //userId, URI'dan alırken newUser ise requestin bodysinden alınıyor.
        return userService.updateUser(userId,newUser);

    }

    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable Long userId){
        userService.deleteUser(userId);
    }

}
