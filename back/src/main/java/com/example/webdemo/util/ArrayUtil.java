package com.example.webdemo.util;

public class ArrayUtil {

    /**
     * 将数组输出为(?,?)形式
     *
     * @param intArray int数组
     * @return (?, ?)形式字符串
     */
    public static String getArrayString(int[] intArray) {
        StringBuilder result = new StringBuilder("(");
        for (int i = 0; i < intArray.length; i++) {
            result.append(intArray[i]);
            if (i < intArray.length - 1) {
                result.append(", ");
            }
        }
        result.append(")");
        return result.toString();
    }

}
