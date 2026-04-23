import java.lang.reflect.*;
import java.util.Arrays;

interface Evaluatable {
    double evalf(double x);
}

class MathFunction implements Evaluatable {
    public double evalf(double x) {
        try { Thread.sleep((long)(Math.random() * 50)); } catch (Exception e) {}
        return Math.exp(-x) * Math.sin(x);
    }
}

class ProfilingHandler implements InvocationHandler {
    private Object target;
    public ProfilingHandler(Object target) { this.target = target; }
    
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        long start = System.nanoTime();
        System.out.println("[TRACE] Calling method: " + method.getName() + " with args: " + Arrays.toString(args));
        
        Object result = method.invoke(target, args);
        
        long end = System.nanoTime();
        System.out.println("[PROFILE] " + method.getName() + " took " + (end - start) + " ns");
        System.out.println("[TRACE] Method returned: " + result);
        return result;
    }
}

public class ProxyDemonstrator {
    public static void demonstrate() {
        Evaluatable realObj = new MathFunction();
        Evaluatable proxyObj = (Evaluatable) Proxy.newProxyInstance(
            Evaluatable.class.getClassLoader(),
            new Class<?>[]{Evaluatable.class},
            new ProfilingHandler(realObj)
        );
        
        System.out.println("Calling evalf(1.0) via Dynamic Proxy...");
        proxyObj.evalf(1.0);
    }
}
