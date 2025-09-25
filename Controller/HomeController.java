package com.charan.e_commerse_web.Controller;


import com.charan.e_commerse_web.DTO.UserDTO;
import com.charan.e_commerse_web.MapHelper.UserMappingHelper;
import com.charan.e_commerse_web.model.Product;
import com.charan.e_commerse_web.model.Users;
import com.charan.e_commerse_web.service.ProductService;
import com.charan.e_commerse_web.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static com.charan.e_commerse_web.MapHelper.UserMappingHelper.map;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@Slf4j

public class HomeController {

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService prodserv;



    @GetMapping("/userdetails/{userName}")
    public ResponseEntity<UserDTO> userdetails(@PathVariable String userName){

        Optional<Users> user=userService.getByUserName(userName);
        System.out.println(userName);
        
        if (user.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Users getuser = user.get();
        UserDTO us=UserMappingHelper.map(getuser);
        return ResponseEntity.ok(us);
    }

    @GetMapping("/products/all")
    public List<Product> findAll(){
        return prodserv.findAll();
    }

}
