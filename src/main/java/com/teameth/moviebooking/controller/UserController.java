
package com.teameth.moviebooking.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.teameth.moviebooking.domain.Role;
import com.teameth.moviebooking.domain.User;
import com.teameth.moviebooking.reservation.MyUserDetailService;
import com.teameth.moviebooking.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.Base64;
import java.util.List;
import java.util.Set;

@RestController
public class UserController {

   @Autowired

   UserService userService;


@Autowired
    MyUserDetailService myUserDetailService;

   //@Autowired




    @RequestMapping("/user")
    public List<User> getAllLocations(){
        return userService.getAllUser();
    }

    @RequestMapping(method = RequestMethod.POST ,value="/user")
    public void saveNewUser(@RequestBody User user){
        userService.saveUser(user);
    }
    @RequestMapping("/user/{username}")
      @PreAuthorize("hasAuthority('ADMIN')")
     //@RolesAllowed("CUST")

    public String getAllUsers(@PathVariable String username) throws JsonProcessingException {
        User user=userService.getByUsername(username);
        Set<Role> rls =user.getRole();
        return String.valueOf(rls.toString());
    }





}

/* ObjectMapper ob= new ObjectMapper();
        Role role = ob.readValue(result,Role.class);*/
               /* String[] names = payload.split("[{\":\"-]");
       // result = result.replaceAll("\",","");
        String[] payloadChunks = result.split("[\":]");*/
//String[]
       /*String json = payload;
                //"{ \"f1\" : \"v1\" } ";*/

       /* ObjectMapper objectMapper = new ObjectMapper();

        JsonNode jsonNode = objectMapper.readTree(json);*/

// String tot = nameNode.toString();
//System.out.println(jsonNode.get("f1").asText());
//System.out.println(token);
//var obj = jsonNode.get("a").asText();
