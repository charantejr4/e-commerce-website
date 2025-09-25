package com.charan.e_commerse_web.service;

import com.charan.e_commerse_web.model.Users;
import com.charan.e_commerse_web.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository usersRepository;

    @Autowired
    private CredentialService credentialService;
    private BCryptPasswordEncoder encoder=new BCryptPasswordEncoder(10);

    public Users save(Users users) {
        users.getCredential().setPassword(encoder.encode(users.getCredential().getPassword()));
        System.out.println(users.getCredential().getPassword());
        return usersRepository.save(users);
    }

    public void deleteByUsersId(int userId) {
        Users user = usersRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        if(user.getCredential() != null) {
            credentialService.delete(user.getCredential().getUserName());
        }
        usersRepository.deleteById(userId);
    }

    public Users findById(int userId) {
        return usersRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
    }


    public Users login(String userName, String rawPassword) {
        Optional<Users> userOpt = usersRepository.findByUserName(userName);
        if (userOpt.isPresent()) {
            Users user = userOpt.get();
            if (user.getCredential() != null &&
                    encoder.matches(rawPassword, user.getCredential().getPassword())) {
                return user;
            }
        }
        return null;
    }




    public Optional<Users> getByUserName(String userName) {
        return usersRepository.findByUserName(userName);
    }


    public Optional<Users> findByUserName(String userName) {
        return usersRepository.findByUserName(userName);
    }
}
