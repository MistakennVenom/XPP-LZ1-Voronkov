import java.lang.reflect.*;
import java.util.*;

class FunctionNotFoundException extends Exception {
    public FunctionNotFoundException(String msg) { super(msg); }
}

public class ObjectInspector {
    public static void inspectAndInvoke(Object obj) {
        Class<?> clazz = obj.getClass();
        System.out.println("Real type: " + clazz.getName());
        System.out.println("\nState (Fields):");
        for (Field f : clazz.getDeclaredFields()) {
            f.setAccessible(true);
            try {
                System.out.println("  " + f.getName() + " = " + f.get(obj));
            } catch (IllegalAccessException e) {
                System.out.println("  " + f.getName() + " = [Access Denied]");
            }
        }
        
        System.out.println("\nPublic Methods (no parameters):");
        Method[] methods = clazz.getMethods();
        List<Method> noParamMethods = new ArrayList<>();
        for (Method m : methods) {
            if (m.getParameterCount() == 0 && Modifier.isPublic(m.getModifiers())) {
                noParamMethods.add(m);
                System.out.println("  " + noParamMethods.size() + "). " + m.getName());
            }
        }
        
        System.out.print("\nSelect method number to invoke (or 0 to skip): ");
        Scanner sc = new Scanner(System.in);
        int choice = 0;
        try { choice = Integer.parseInt(sc.nextLine()); } catch(Exception e) { System.out.println("Skipping..."); }
        
        if (choice > 0 && choice <= noParamMethods.size()) {
            Method m = noParamMethods.get(choice - 1);
            try {
                System.out.println("Result of " + m.getName() + "(): " + m.invoke(obj));
            } catch (Exception e) {
                System.out.println("Invocation failed: " + e.getMessage());
            }
        }
    }
}
