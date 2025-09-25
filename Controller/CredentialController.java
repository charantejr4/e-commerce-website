package com.charan.e_commerse_web.Controller;


import com.charan.e_commerse_web.service.CredentialService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/credential")
public class CredentialController {

    @Autowired
    private CredentialService credserv;

    @DeleteMapping("/delete/{username}")
    public void delete(@PathVariable String username){
        credserv.delete(username);
    }



}
