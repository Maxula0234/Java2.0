package org.levelup.chat.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

public class Users {

    @Entity
    @Getter
    @Setter
    @NoArgsConstructor
    @Table(name = "users")
    public class UsersTable{

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer id;
        @Column(nullable = false,unique = true)
        private String login;
        @Column(name = "first_name")
        private String firstName;
        @Column(name = "last_name")
        private String lastName;
    }
}
