package com.charan.e_commerse_web.repository;

import com.charan.e_commerse_web.model.Credential;
import com.charan.e_commerse_web.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CredentialRepository extends JpaRepository<Credential, Long > {
    void deleteByUserName(String username);

    Credential findByUserName(String username);
}
