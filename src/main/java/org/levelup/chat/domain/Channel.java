package org.levelup.chat.domain;
//Entity - сущность

import lombok.*;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Data
@ToString
//@NoArgsConstructor
@Table(name = "channel")
public class Channel {

  public Channel(){}
  public Channel(String name){
    this.name = name;
  }
  public Channel(String name,String displayName){
    this.name = name;
    this.displayName = displayName;
  }
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

  @OneToOne(mappedBy = "channel")
  private ChannelDetails details;


  @OneToMany(mappedBy = "channel")
  private Collection<Message> messages;

  @ManyToMany
  @JoinTable(
          name = "user_channels",
          joinColumns = @JoinColumn(name = "channel_id"), // колонка которая ссылается на эту табличку
          inverseJoinColumns = @JoinColumn(name = "user_id")// колонка которая ссылается на вторую тб из связи  м - то -М
  )
  private Collection<User> users;
}
