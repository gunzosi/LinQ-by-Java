package com.linq;

import org.junit.jupiter.api.Test;
import java.util.Arrays;
import static org.assertj.core.api.Assertions.assertThat;

public class OfTypeLinQTest {

    @Test
    public void testOfType_withValidTypes() {
        // Arrange
        OfTypeLinQ<String> ofTypeLinQ = new OfTypeLinQ<>();
        Iterable<Object> source = Arrays.asList("one", 2, "three", 4.0);

        // Act
        Iterable<String> result = ofTypeLinQ.OfType(source);

        // Assert
        assertThat(result).containsExactly("one", "three");
    }

    @Test
    public void testCast_withDifferentTypes() {
        // Arrange
        OfTypeLinQ<Integer> ofTypeLinQ = new OfTypeLinQ<>();
        Iterable<Object> source = Arrays.asList(1, "two", 3, 4.0);

        // Act
        Iterable<Integer> result = ofTypeLinQ.Cast(source);

        // Assert
        assertThat(result).containsExactly(1, 3);
    }

    @Test
    public void testOfType_withNullSource() {
        OfTypeLinQ<String> ofTypeLinQ = new OfTypeLinQ<>();

        Exception exception = org.junit.jupiter.api.Assertions.assertThrows(IllegalArgumentException.class, () -> {
            ofTypeLinQ.OfType(null);
        });

        assertThat(exception.getMessage()).isEqualTo("source cannot be null");
    }

    @Test
    public void testCast_withNullSource() {
        OfTypeLinQ<Integer> ofTypeLinQ = new OfTypeLinQ<>();

        Exception exception = org.junit.jupiter.api.Assertions.assertThrows(IllegalArgumentException.class, () -> {
            ofTypeLinQ.Cast(null);
        });

        assertThat(exception.getMessage()).isEqualTo("source cannot be null");
    }
}
