package com.charan.e_commerse_web.service;

import com.charan.e_commerse_web.model.Credential;
import com.charan.e_commerse_web.repository.CredentialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CredentialService {

    @Autowired
    private CredentialRepository credrepo;
    private BCryptPasswordEncoder encoder=new BCryptPasswordEncoder(10);
    public void delete(String username) {
        credrepo.deleteByUserName(username);
    }

//    public Credential save(Credential cred){
//        cred.setPassword(encoder.encode(cred.getPassword()));
//        System.out.println(cred.getPassword());
//        return credrepo.save(cred);
//    }
}
