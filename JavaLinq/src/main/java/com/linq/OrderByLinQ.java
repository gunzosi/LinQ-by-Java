package com.linq;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class OrderByLinQ<T> {
    public static void main(String[] args) {
        // Ví dụ sử dụng
        OrderByLinQ<String> linq = new OrderByLinQ<>();
        // 1. String
        List<String> fruits = List.of("Banana", "Apple", "Mango", "Orange");

        IOrderedEnumerable<String> orderedFruits = linq.Order(fruits);
        System.out.println("Ordered Fruits:");
        for (String fruit : orderedFruits) {
            System.out.println(fruit);
        }

        // 2. Integer
        OrderByLinQ<Integer> intLinq = new OrderByLinQ<>(); // Tạo một đối tượng mới cho Integer
        List<Integer> numbers = List.of(3, 1, 4, 1, 5, 9, 2, 6, 5, 3, 5);
        IOrderedEnumerable<Integer> orderedNumbers = intLinq.OrderByDesc(numbers); // Cập nhật để gọi OrderByDesc với comparator
        System.out.println("Ordered Numbers:");
        for (Integer number : orderedNumbers) {
            System.out.println(number);
        }
    }

    public IOrderedEnumerable<T> Order(Iterable<T> source) {
        if (source == null) {
            throw new IllegalArgumentException("source cannot be null");
        }
        return Order(source, (Comparator<T>) Comparator.naturalOrder()); // Mặc định sử dụng natural order
    }

    public IOrderedEnumerable<T> Order(Iterable<T> source, Comparator<T> comparator) {
        if (source == null) {
            throw new IllegalArgumentException("source cannot be null");
        }
        if (comparator == null) {
            throw new IllegalArgumentException("comparator cannot be null");
        }

        List<T> result = new ArrayList<>();
        for (T item : source) {
            result.add(item);
        }
        result.sort(comparator);
        return new OrderedEnumerable<>(result); // Sử dụng lớp OrderedEnumerable
    }

    // OrderByDesc
    public IOrderedEnumerable<T> OrderByDesc(Iterable<T> source) {
        if (source == null) {
            throw new IllegalArgumentException("source cannot be null");
        }
        return Order(source, (Comparator<T>) Comparator.reverseOrder()); // Sử dụng Comparator.reverseOrder() để sắp xếp giảm dần
    }

    private static class OrderedEnumerable<T> implements IOrderedEnumerable<T> {
        private final List<T> items;

        public OrderedEnumerable(List<T> items) {
            this.items = items;
        }

        @Override
        public Iterator<T> iterator() {
            return items.iterator();
        }

        @Override
        public IOrderedEnumerable<T> thenBy(Comparator<T> keySelector) {
            List<T> sortedItems = new ArrayList<>(items);
            sortedItems.sort(keySelector);
            return new OrderedEnumerable<>(sortedItems);
        }

        @Override
        public IOrderedEnumerable<T> thenByDescending(Comparator<T> keySelector) {
            List<T> sortedItems = new ArrayList<>(items);
            sortedItems.sort(keySelector.reversed());
            return new OrderedEnumerable<>(sortedItems);
        }
    }
}
