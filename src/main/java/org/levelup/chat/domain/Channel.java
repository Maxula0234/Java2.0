package org.levelup.chat.domain;
//Entity - сущность

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@Table(name = "channel")
public class Channel {

  //identity
  // insert() -> next_val('seq') , name , displayName

  //SEQUENCE
  //id = select next_val('seq'), insert() -> id, name, displayName
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  @Column(nullable = false,unique = true)
  private String name;
  @Column(name = "display_name",nullable = false, unique = false, length = 100) //display_name varchar(100) not null
  private String displayName;

}
