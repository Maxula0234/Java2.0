package org.levelup.chat.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "user_channels")
public class UserChannel {

    @Column(name = "user_id")
    private Integer userId;

    @Id
    @Column(name = "channel_id")
    private Integer channelId;

}
