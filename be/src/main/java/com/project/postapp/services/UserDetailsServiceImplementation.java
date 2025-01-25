package com.project.postapp.services;

import com.project.postapp.repositories.UserRepository;
import  com.project.postapp.models.User;
import com.project.postapp.security.JwtUserDetails;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImplementation implements UserDetailsService {
    private UserRepository userRepository;

    public UserDetailsServiceImplementation(UserRepository userRepository){
        this.userRepository = userRepository;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        return JwtUserDetails.create(user);
    }
    public UserDetails loadUserById(Long id){    //Verilen ID ile eşleşen useri oluşturuyor ve create ediyor.
        User user = userRepository.findById(id).get();
        return JwtUserDetails.create(user);
    }
}
