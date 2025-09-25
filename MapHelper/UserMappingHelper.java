package com.charan.e_commerse_web.MapHelper;

import com.charan.e_commerse_web.DTO.CredentialDTO;
import com.charan.e_commerse_web.DTO.UserDTO;
import com.charan.e_commerse_web.model.Credential;
import com.charan.e_commerse_web.model.Users;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class UserMappingHelper {


    public static Users map(UserDTO userDTO) {
        Users user = new Users();
        BeanUtils.copyProperties(userDTO, user);



        return user;
    }

    public static UserDTO map(Users user) {
        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(user, userDTO);



        return userDTO;
    }

    public static Users mapToUserWithCredential(Users user, Credential credential) {

        credential.setUsers(user);
        user.setCredential(credential);

        return user;
    }




}
