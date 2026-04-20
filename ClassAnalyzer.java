import java.lang.reflect.*;

public class ClassAnalyzer {
    public static String analyze(String className) {
        try {
            return analyze(Class.forName(className));
        } catch (ClassNotFoundException e) {
            return "Class not found: " + className;
        }
    }

    public static String analyze(Class<?> clazz) {
        StringBuilder sb = new StringBuilder();
        Package pkg = clazz.getPackage();
        if (pkg != null) sb.append("package ").append(pkg.getName()).append(";\n\n");
        
        sb.append(Modifier.toString(clazz.getModifiers())).append(" class ").append(clazz.getSimpleName());
        
        Class<?> superclass = clazz.getSuperclass();
        if (superclass != null && superclass != Object.class) {
            sb.append(" extends ").append(superclass.getSimpleName());
        }
        
        Class<?>[] interfaces = clazz.getInterfaces();
        if (interfaces.length > 0) {
            sb.append(" implements ");
            for (int i = 0; i < interfaces.length; i++) {
                sb.append(interfaces[i].getSimpleName()).append(i < interfaces.length - 1 ? ", " : "");
            }
        }
        sb.append(" {\n");
        
        sb.append("\n  // Fields\n");
        for (Field f : clazz.getDeclaredFields()) {
            sb.append("  ").append(Modifier.toString(f.getModifiers())).append(" ")
              .append(f.getType().getSimpleName()).append(" ").append(f.getName()).append(";\n");
        }
        
        sb.append("\n  // Constructors\n");
        for (Constructor<?> c : clazz.getDeclaredConstructors()) {
            sb.append("  ").append(Modifier.toString(c.getModifiers())).append(" ")
              .append(clazz.getSimpleName()).append("(");
            Parameter[] params = c.getParameters();
            for (int i = 0; i < params.length; i++) {
                sb.append(params[i].getType().getSimpleName()).append(" ").append(params[i].getName())
                  .append(i < params.length - 1 ? ", " : "");
            }
            sb.append(");\n");
        }
        
        sb.append("\n  // Methods\n");
        for (Method m : clazz.getDeclaredMethods()) {
            sb.append("  ").append(Modifier.toString(m.getModifiers())).append(" ")
              .append(m.getReturnType().getSimpleName()).append(" ").append(m.getName()).append("(");
            Parameter[] params = m.getParameters();
            for (int i = 0; i < params.length; i++) {
                sb.append(params[i].getType().getSimpleName()).append(" ").append(params[i].getName())
                  .append(i < params.length - 1 ? ", " : "");
            }
            sb.append(");\n");
        }
        sb.append("}\n");
        return sb.toString();
    }
}
