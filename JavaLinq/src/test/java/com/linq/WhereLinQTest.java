package com.linq;

import com.linq.WhereLinQ;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import static org.junit.jupiter.api.Assertions.*;

public class WhereLinQTest {

    @Test
    public void testWhereWithPredicate_ShouldReturnEvenNumbers() {
        // Arrange: Chu?n b? d? li?u
        WhereLinQ<Integer> linQ = new WhereLinQ<>();
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        Predicate<Integer> isEven = n -> n % 2 == 0;  // Predicate ki?m tra s? ch?n

        // Act: G?i ph??ng th?c ?? ki?m tra
        Iterable<Integer> evenNumbers = linQ.Where(numbers, isEven);

        // Assert: X�c minh k?t qu?
        List<Integer> result = (List<Integer>) evenNumbers;
        assertEquals(Arrays.asList(2, 4, 6, 8, 10), result);
    }

    @Test
    public void testWhereWithBiPredicate_ShouldReturnGreaterThanFive() {
        // Arrange: Chu?n b? d? li?u
        WhereLinQ<Integer> linQ = new WhereLinQ<>();
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        BiPredicate<Integer, Integer> greaterThan = (n, value) -> n > value;

        // Act: G?i ph??ng th?c ?? ki?m tra v?i gi� tr? b? sung l� 5
        Iterable<Integer> greaterThanFive = linQ.Where(numbers, greaterThan, 5);

        // Assert: X�c minh k?t qu?
        List<Integer> result = (List<Integer>) greaterThanFive;
        assertEquals(Arrays.asList(6, 7, 8, 9, 10), result);
    }

    @Test
    public void testWhereWithNullSource_ShouldThrowException() {
        // Arrange: Chu?n b? ??i t??ng WhereLinQ
        WhereLinQ<Integer> linQ = new WhereLinQ<>();

        // Act & Assert: Ki?m tra xem ngo?i l? IllegalArgumentException c� ???c n�m ra kh�ng
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            linQ.Where(null, n -> n % 2 == 0);
        });

        // Assert: X�c minh th�ng b�o ngo?i l?
        assertEquals("source cannot be null", exception.getMessage());
    }

    @Test
    public void testWhereWithNullPredicate_ShouldThrowException() {
        // Arrange: Chu?n b? d? li?u
        WhereLinQ<Integer> linQ = new WhereLinQ<>();
        List<Integer> numbers = Arrays.asList(1, 2, 3);

        // Act & Assert: Ki?m tra xem ngo?i l? IllegalArgumentException c� ???c n�m ra kh�ng
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            linQ.Where(numbers, null);
        });

        // Assert: X�c minh th�ng b�o ngo?i l?
        assertEquals("predicate cannot be null", exception.getMessage());
    }
}
