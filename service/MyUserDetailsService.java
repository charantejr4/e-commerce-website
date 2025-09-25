package com.charan.e_commerse_web.service;


import com.charan.e_commerse_web.model.Credential;
import com.charan.e_commerse_web.model.UserDetailsImplementation;
import com.charan.e_commerse_web.model.Users;
import com.charan.e_commerse_web.repository.CredentialRepository;
import com.charan.e_commerse_web.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private CredentialRepository credrepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Credential creduser=credrepo.findByUserName(username);
        if(creduser==null){
            System.out.println("User 404");
            throw new UsernameNotFoundException("User 404");
        }

        return new UserDetailsImplementation(creduser);
    }
}
