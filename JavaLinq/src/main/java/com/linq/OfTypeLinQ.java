package com.linq;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class OfTypeLinQ<T> {

    public static void main(String[] args) {
        // Tạo một ArrayList với nhiều loại phần tử
        ArrayList<Object> fruits = new ArrayList<Object>() {{
            add("Mango");
            add("Orange");
            add(null);
            add("Apple");
            add(3.0);
            add("Banana");
            add(4);
            add('A');
        }};

        // Lọc và in ra các phần tử có kiểu String
        System.out.println("1 -- Elements of type 'String' are:");
        for (Object item : fruits) {
            if (item instanceof String) {
                System.out.println("> " + item);
            }
        }

        System.out.println("2 -- Elements of type 'Double' are:");
        for (Object item : fruits) {
            if (item instanceof Double) {
                System.out.println("> " +item);
            }
        }

        System.out.println("3 -- Elements of type 'Character' are:");
        for (Object item : fruits) {
            if (item instanceof Character) {
                System.out.println("> " +item);
            }
        }

        System.out.println("4 -- Elements of type 'Integer' are:");
        for (Object item : fruits) {
            if (item instanceof Integer) {
                System.out.println("> " +item);
            }
        }
    }

    public Iterable<T> OfType(Iterable<?> source) {
        if (source == null) {
            throw new IllegalArgumentException("source can't be null");
        }

        return new Iterable<T>() {
            @Override
            public Iterator<T> iterator() {
                return OfTypeIterator(source);
            }
        };
    }

    private Iterator<T> OfTypeIterator(Iterable<?> source) {
        List<T> result = new ArrayList<>();
        for (Object item : source) {
            if (isInstanceOf(item)) {
                result.add((T) item);
            }
        }
        return result.iterator();
    }

    private boolean isInstanceOf(Object obj) {
        return obj != null && (obj.getClass().isAssignableFrom(Object.class));
    }

    public Iterable<T> Cast(Iterable<?> source) {
        if (source == null) {
            throw new IllegalArgumentException("source cannot be null");
        }

        return new Iterable<T>() {
            @Override
            public Iterator<T> iterator() {
                return CastIterator(source);
            }
        };
    }

    private Iterator<T> CastIterator(Iterable<?> source) {
        List<T> result = new ArrayList<>();
        for (Object item : source) {
            result.add((T) item);
        }
        return result.iterator();
    }
}
