package org.levelup.lessons.lesson10.Upload;

import org.levelup.chat.domain.Channel;

import java.util.ArrayList;
import java.util.Collection;

public class UploaderApp {
    public static void main(String[] args) {
        ChannelUploader uploader = new ChannelUploader();


        Collection<Channel> channels = new ArrayList<>();
        for (int i = 0; i < 50 ; i++) {
            channels.add(new Channel("channel" + i));
        }
        uploader.uploadChannels(channels);
    }
}
