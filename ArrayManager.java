import java.lang.reflect.Array;

public class ArrayManager {
    public static Object createArray(Class<?> type, int size) {
        return Array.newInstance(type, size);
    }
    
    public static Object createMatrix(Class<?> type, int rows, int cols) {
        return Array.newInstance(type, rows, cols);
    }
    
    public static Object resizeArray(Object oldArray, int newSize) {
        int oldSize = Array.getLength(oldArray);
        Class<?> elementType = oldArray.getClass().getComponentType();
        Object newArray = Array.newInstance(elementType, newSize);
        int preserveLength = Math.min(oldSize, newSize);
        if (preserveLength > 0) {
            System.arraycopy(oldArray, 0, newArray, 0, preserveLength);
        }
        return newArray;
    }
    
    public static String arrayToString(Object array) {
        if (array == null) return "null";
        if (!array.getClass().isArray()) return array.toString();
        
        int length = Array.getLength(array);
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < length; i++) {
            Object element = Array.get(array, i);
            if (element != null && element.getClass().isArray()) {
                sb.append(arrayToString(element));
            } else {
                sb.append(element);
            }
            if (i < length - 1) sb.append(", ");
        }
        sb.append("]");
        return sb.toString();
    }
}
