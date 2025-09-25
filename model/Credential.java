package com.charan.e_commerse_web.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = {"users"})
public class Credential {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long credentialId;

    private String userName;
    private String password;

    @OneToOne
    @JoinColumn(name="user_id",referencedColumnName = "userId", unique = true)
    @JsonIgnore
    private Users users;
}
