package org.levelup.chat.dao;

import org.levelup.chat.domain.ChannelDetails;

public interface ChannelDetailsDao {
    ChannelDetails appendDetails(Integer channelId, int peopleCount, String description);

}
