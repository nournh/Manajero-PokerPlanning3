package com.example.demo.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import  com.example.demo.Repositories.UserRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class IUserDetailsServiceImp implements IUserDetailsService {

    private final UserRepository userRepository;
    @Override
    public UserDetailsService userDetailsService() {
        return new UserDetailsService(){
            @Override
            public UserDetails loadUserByUsername(String s) {
                return (UserDetails) userRepository.findByEmail(s).orElseThrow(() -> new RuntimeException("User not found"));
            }
        };
    }
}
