package com.nistagram.user.config;

import com.nistagram.user.reposiotry.UserInfoRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class JwtUserDetailsService implements UserDetailsService {


    private UserInfoRepository userRepository;

    public JwtUserDetailsService(UserInfoRepository userRepository) {
        this.userRepository = userRepository;
    }

    //Method gets the User with given username from database
    // if user with that username doesn't  exists the method will throw exception
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new User(username, (new BCryptPasswordEncoder()).encode("pass"), null);
    }

}

