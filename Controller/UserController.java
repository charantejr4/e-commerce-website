package com.charan.e_commerse_web.Controller;


import com.charan.e_commerse_web.DTO.CredentialDTO;
import com.charan.e_commerse_web.DTO.LoginDTO;
import com.charan.e_commerse_web.DTO.UserDTO;
import com.charan.e_commerse_web.MapHelper.UserMappingHelper;
import com.charan.e_commerse_web.model.Users;
import com.charan.e_commerse_web.service.JWT;
import com.charan.e_commerse_web.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@Slf4j
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWT jwtservice;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody CredentialDTO credDTO) {
        System.out.println("Received username: " + credDTO.getUserName());
        System.out.println("Received password: " + credDTO.getPassword());

        try {
            Authentication authentication=authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken
                                    (credDTO.getUserName(), credDTO.getPassword()));
            if (authentication.isAuthenticated()) {
                Users user = userService.login(credDTO.getUserName(), credDTO.getPassword());
                String token = jwtservice.generateToken(credDTO.getUserName());
                System.out.println("JWT Token: " + token);
                Map<String, String> response = new HashMap<>();
                response.put("token", token);
                response.put("username", credDTO.getUserName());
                return ResponseEntity.ok(response);
            } else {
                Map<String, String> response = new HashMap<>();
                response.put("error", "Invalid username or password");
                return ResponseEntity.status(401).body(response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Map<String, String> response = new HashMap<>();
            response.put("error", "Server error occurred");
            return ResponseEntity.status(500).body(response);
        }
    }



    @PostMapping("/signup")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<Users> signup(@RequestBody Users user) {
        Users savedUser = userService.save(user);
        return ResponseEntity.ok(savedUser);
    }


    @PostMapping("/save")
    public ResponseEntity<Users> save(@RequestBody Users users){
        if(users.getCredential() != null) {
            UserMappingHelper.mapToUserWithCredential(users, users.getCredential());
        }

        Users savedUser = userService.save(users);
        return ResponseEntity.ok(savedUser);

    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable int userId) {
        Users user = userService.findById(userId);
        return ResponseEntity.ok(UserMappingHelper.map(user));
    }
    
    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<Users> deleteById(@PathVariable int userId){
        Users delOb=userService.findById(userId);
        userService.deleteByUsersId(userId);
        return ResponseEntity.ok(delOb);
    }

}
