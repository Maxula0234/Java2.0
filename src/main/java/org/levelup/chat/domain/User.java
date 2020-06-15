package org.levelup.chat.domain;

import lombok.*;

import javax.persistence.*;
//@RequiredArgsConstructor - создает конструктор, в котором описываются все final field
//CLASS A{
// private int var1;
// final int var2;
// public A(int var2){this.var2 = var2;}}
@Data //(@Getter,@Setter,@EuqualAndHashCode,@ToString,@RequiredArgsConstructor)
@Entity
@NoArgsConstructor
@Table(name = "users")
public class User {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id")
        private Integer id;
        @Column(name = "login")
        private String login;
        @Column(name = "first_name")
        private String firstName;
        @Column(name = "last_name")
        private String lastName;

        public User(String login, String firstName, String lastName){
                this.login = login;
                this.firstName = firstName;
                this.lastName = lastName;
        }
}
