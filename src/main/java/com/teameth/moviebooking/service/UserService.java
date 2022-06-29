
package com.teameth.moviebooking.service;

import com.teameth.moviebooking.domain.Location;
import com.teameth.moviebooking.domain.Role;
import com.teameth.moviebooking.domain.User;
import com.teameth.moviebooking.repository.RoleRepository;
import com.teameth.moviebooking.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    public List<User> getAllUser(){
        return userRepository.findAll();
    }
    public void saveUser(User user){
        Set<Role> roleSet= new HashSet<>();
        Role role= roleRepository.findById(2).get();
        roleSet.add(role);
         String uName= user.getUsername();
        userRepository.save(user);
        User userNew = userRepository.findByUsername(uName);
        userNew.setRole(roleSet);
        userRepository.save(userNew);


    }
    public User getByUsername(String username){
        return userRepository.findByUsername(username);
    }

    public User getUserById(Integer userid){
        return userRepository.findById(userid).get();
    }
}

