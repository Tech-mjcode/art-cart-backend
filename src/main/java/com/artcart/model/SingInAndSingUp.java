package com.artcart.model;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "login_handler_table")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SingInAndSingUp
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String email;
    private String password;
    private String role;
}
