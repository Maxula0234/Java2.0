package org.levelup.lessons.lesson4.proxy;

public class DefaultLoginServise implements LoginService {
    @Override
    @ProfileExecutionTime
    public void login(String login, String password) {
        System.out.println("Try "+login+"to login...");
    }
}
