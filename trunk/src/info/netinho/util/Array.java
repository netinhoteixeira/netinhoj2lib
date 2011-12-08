package info.netinho.util;

public class Array {

    public static Object resizeArray(Object oldArray, int newSize) {
        int oldSize = java.lang.reflect.Array.getLength(oldArray);
        Class elementType = oldArray.getClass().getComponentType();
        Object newArray = java.lang.reflect.Array.newInstance(elementType, newSize);

        int preserveLength = Math.min(oldSize, newSize);
        if (preserveLength > 0) {
            System.arraycopy(oldArray, 0, newArray, 0, preserveLength);
        }
        return newArray;
    }

    public static Object[] addItemOnArray(Object[] array, Object option) {
        array = (Object[]) (Object[]) resizeArray(array, array.length + 1);
        array[(array.length - 1)] = option;
        return array;
    }
}