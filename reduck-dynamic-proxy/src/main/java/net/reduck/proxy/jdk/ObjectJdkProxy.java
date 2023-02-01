package net.reduck.proxy.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author Gin
 * @since 2023/2/1 10:52
 */
public class ObjectJdkProxy implements InvocationHandler {
    private final UseService useService;

    public ObjectJdkProxy(UseService useService) {
        this.useService = useService;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        System.out.println("日志开始");
        Object invoke = method.invoke(useService, args);
        System.out.println("日志结束");
        return invoke;
    }

    public static void main(String[] args) {
        LocalUserService localUserService = new LocalUserService();
        ObjectJdkProxy proxy = new ObjectJdkProxy(localUserService);

        UseService useService = (UseService) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), LocalUserService.class.getInterfaces(), proxy);

        useService.getName();

    }
}
