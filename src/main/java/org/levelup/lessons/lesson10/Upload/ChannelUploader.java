package org.levelup.lessons.lesson10.Upload;

import lombok.RequiredArgsConstructor;
import org.levelup.chat.domain.Channel;

import java.util.Collection;
import java.util.Iterator;

public class ChannelUploader {

    // Загрузить каналы
    // НО! У нас ограниченное кол-во потоков: 3
    // Каждый поток должен брать из  collection  следующий объект, который нужно загрузить
    public void uploadChannels(Collection<Channel> channels) {
        Iterator<Channel> iterator = channels.iterator();

        ChannelIteratior channelIteratior1 = new ChannelIteratior(iterator);
        ChannelIteratior channelIteratior2 = new ChannelIteratior(iterator);
        ChannelIteratior channelIteratior3 = new ChannelIteratior(iterator);

        new Thread(new ChannelUploadWorker(channelIteratior1)).start();
        new Thread(new ChannelUploadWorker(channelIteratior2)).start();
        new Thread(new ChannelUploadWorker(channelIteratior3)).start();
    }

    @RequiredArgsConstructor
    private class ChannelUploadWorker implements Runnable {
        private final ChannelIteratior channelIteratior;

        @Override
        public void run() {
            Channel c;
            while ((c = channelIteratior.getNextOrNull()) != null) {
                System.out.println(Thread.currentThread().getName() + " Upload channel " + c.getName() + " to database");
            }
        }
    }

    @RequiredArgsConstructor
    private class ChannelIteratior {
        private final Iterator<Channel> iterator;
        synchronized Channel next() {
            return iterator.next();
        }
        synchronized boolean hasNext() {
            return iterator.hasNext();
        }
        synchronized Channel getNextOrNull() {
            synchronized (iterator) {
                return iterator.hasNext() ? iterator.next() : null;
            }
        }
    }

}
