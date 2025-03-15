package com.SideQuest_app.domain.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    private int userID;
    private String firstname;
    private String lastname;
    private String password_hash;
    private String nationality;
    private String email;
    private String phone_number;

}