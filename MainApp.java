public class MainApp {
    public static void main(String[] args) {
        System.out.println("=== Lab 6: Java Reflection API ===\n");
        
        System.out.println("--- Task 1: Class Analyzer ---");
        System.out.println(ClassAnalyzer.analyze("java.lang.String"));
        
        System.out.println("\n--- Task 4: Dynamic Arrays ---");
        Object arr = ArrayManager.createArray(int.class, 3);
        java.lang.reflect.Array.set(arr, 0, 10);
        java.lang.reflect.Array.set(arr, 1, 20);
        System.out.println("Original array: " + ArrayManager.arrayToString(arr));
        arr = ArrayManager.resizeArray(arr, 5);
        System.out.println("Resized array: " + ArrayManager.arrayToString(arr));
        
        Object matrix = ArrayManager.createMatrix(double.class, 2, 2);
        System.out.println("Matrix: " + ArrayManager.arrayToString(matrix));
        
        System.out.println("\n--- Task 5: Dynamic Proxy ---");
        ProxyDemonstrator.demonstrate();

        System.out.println("\n--- Task 2 & 3: Object Inspector ---");
        class Dummy {
            private int secret = 42;
            public String name = "Test Object";
            public String sayHello() { return "Hello, " + name + "!"; }
            public int getSecret() { return secret; }
        }
        ObjectInspector.inspectAndInvoke(new Dummy());
    }
}
