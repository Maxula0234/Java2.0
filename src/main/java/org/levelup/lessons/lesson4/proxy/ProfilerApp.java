package org.levelup.lessons.lesson4.proxy;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ProfilerApp {
    public static void main(String[] args) {

        LoginService loginService = getLoginService();
        loginService.login("Pet", "Tre");
    }

    static LoginService getLoginService() {
        DefaultLoginServise loginServise = new DefaultLoginServise();
        Method[] methods = loginServise.getClass().getDeclaredMethods();
        for (Method method : methods) {
            ProfileExecutionTime annotation = method.getAnnotation(ProfileExecutionTime.class);
            if (annotation != null) {
                return (LoginService) Proxy.newProxyInstance(
                        loginServise.getClass().getClassLoader(),
                        loginServise.getClass().getInterfaces(),
                        new ProfilerInvocatiomHandler(loginServise)
                );
            }
        }
        return loginServise;
    }
}

