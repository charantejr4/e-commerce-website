package com.charan.e_commerse_web.repository;

import com.charan.e_commerse_web.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users, Integer> {

    Optional<Users> findByUserNameAndCredential_Password(String userName, String password);

    Optional<Users> findByUserName(String userName);
}
