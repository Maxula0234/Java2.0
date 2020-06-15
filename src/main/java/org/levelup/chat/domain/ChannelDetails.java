package org.levelup.chat.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@Table(name = "channel_details")
public class ChannelDetails {

    //Channel -> ChannelDetails - one-to-one
    //Unidirectional / Bidirectional

    @Id
    @Column(name = "channel_id")
    private Integer channelId;
    @Column(name = "people_count")
    private Integer peopleCount;
    private String description;

    @OneToOne
    @JoinColumn(name = "channel_id")
    private Channel channel;
}
