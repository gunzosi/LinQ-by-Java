package com.linq;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

public class WhereLinQ<T> {

    public static void main(String[] args) {
        // Arrange: Chuẩn bị dữ liệu
        WhereLinQ<Integer> linQ = new WhereLinQ<>();
        List<Integer> numbers = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        Predicate<Integer> isEven = n -> n % 2 == 0;  // Predicate kiểm tra số chẵn

        // Act: Gọi phương thức để kiểm tra
        Iterable<Integer> evenNumbers = linQ.Where(numbers, isEven);

        // Assert: Xác minh kết quả
        List<Integer> result = (List<Integer>) evenNumbers;
        System.out.println(result);
    }

    // Where với Predicate
    public Iterable<T> Where(Iterable<T> source, Predicate<T> predicate) {
        Iterator<T> iterator = validateAndPrepare(source, predicate);

        // Tạo danh sách chứa các phần tử thỏa mãn điều kiện
        List<T> result = new ArrayList<>();

        while (iterator.hasNext()) {
            T item = iterator.next();
            if (predicate.test(item)) {
                result.add(item);
            }
        }

        return result;
    }

    // Where với BiPredicate
    public <U> Iterable<T> Where(Iterable<T> source, BiPredicate<T, U> predicate, U value) {
        Iterator<T> iterator = validateAndPrepare(source, predicate);

        // Tạo danh sách chứa các phần tử thỏa mãn điều kiện
        List<T> result = new ArrayList<>();

        while (iterator.hasNext()) {
            T item = iterator.next();
            if (predicate.test(item, value)) {
                result.add(item);
            }
        }

        return result;
    }

    // Private helper method để xử lý kiểm tra điều kiện và lấy iterator
    private Iterator<T> validateAndPrepare(Iterable<T> source, Object predicate) {
        if (source == null) {
            throw new IllegalArgumentException("source can't be null");
        }

        if (predicate == null) {
            throw new IllegalArgumentException("predicate cannot be null");
        }

        // Trả về iterator của source
        return source.iterator();
    }
}
