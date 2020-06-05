package org.levelup.lessons.lesson4.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class ProfilerInvocatiomHandler implements InvocationHandler {

    private DefaultLoginServise loginServise;

    public ProfilerInvocatiomHandler(DefaultLoginServise loginServise) {
        this.loginServise = loginServise;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        long statrExecution = System.nanoTime();
        Object result = method.invoke(loginServise, args);//loginServise.login(String,String)
        long endExecution = System.nanoTime();
        System.out.println("Execution time: " + (endExecution - statrExecution) + "ms");
        return result;
    }
}
