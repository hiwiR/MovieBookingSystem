package com.teameth.moviebooking.reservation;

//import com.teameth.moviebooking.service.UserService;
import com.teameth.moviebooking.domain.Role;
import com.teameth.moviebooking.jwt.JwtUtil;
import com.teameth.moviebooking.models.AuthenticationRequest;
import com.teameth.moviebooking.models.AuthenticationResponse;
import com.teameth.moviebooking.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class MyUserDetailService implements UserDetailsService {
    @Autowired
    private UserService userService;

    @Autowired
    JwtUtil jwtUtil;

   /* @Autowired
    AuthenticationManager authenticationManager;*/



    /*public AuthenticationResponse createJwtToken(AuthenticationRequest jwtRequest){
        String userName = jwtRequest.getUsername();
        String pass= jwtRequest.getPassword();

    } */

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        com.teameth.moviebooking.domain.User u1 = userService.getByUsername(userName);
        Set<Role> roles = u1.getRole();
        //Set<Role> role = new HashSet<>();
        // Role r1 = new Role(1,"ADMIN","ADMIN");
        //role.add(r1);
        //List<SimpleGrantedAuthority> authorities = (List<SimpleGrantedAuthority>) buildUserAuthority(roles);
        // System.out.println(authorities.toString());

        return new User(u1.getUsername(), u1.getPassword(), buildUserAuthority(u1));
    }

    /*private User buildUserForAuthentication(u1,List<GrantedAuthority> authorities) {
        return new User(user.getUsername(), user.getPassword(),
                user.isEnabled(), true, true, true, authorities);
    }*/
    private Set buildUserAuthority(com.teameth.moviebooking.domain.User user) {

      /*  Set<GrantedAuthority> setAuths = new HashSet<GrantedAuthority>();
        List<SimpleGrantedAuthority> Result = new ArrayList<SimpleGrantedAuthority>();

        // add user's authorities
        for (Role userRole : userRoles) {
            Result.add(new SimpleGrantedAuthority(userRole.getName()));
        }



        return Result;
    }*/

        Set authorities = new HashSet();
        user.getRole().forEach(role -> {
            authorities.add(
                    (new SimpleGrantedAuthority(role.getName())));
        });
        return authorities;
//}
    }
    /*public void authenticate(String userName,String userPassword){
        auth
    }*/
}

