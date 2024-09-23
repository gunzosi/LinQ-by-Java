package com.linq;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;

// 0 ----- Class chính để kiểm thử
public class GroupByLinQ {
    public static void main(String[] args) {
        // Arrange: Chuẩn bị dữ liệu
        List<String> fruits = List.of("apple", "banana", "apricot", "blueberry", "blackberry", "cherry", "date", "elderberry", "fig", "grape");

        // Group by the first letter
        GroupedEnumerable<String, Character, String> groupedFruits = Enumerable.groupBy(
                fruits,
                fruit -> fruit.charAt(0), // Key selector
                Function.identity() // Element selector
        );

        // Output the results
        for (IGrouping<Character, String> group : groupedFruits) {
            System.out.println("Group: " + group.getKey());
            for (String fruit : group) {
                System.out.println(" - " + fruit);
            }
        }

        // ------------
        // Group by the Location of tourist
        List<Tourist> tourists = List.of(
                new Tourist("John", "USA"),
                new Tourist("Alice", "UK"),
                new Tourist("Bob", "USA"),
                new Tourist("Charlie", "UK"),
                new Tourist("David", "USA"),
                new Tourist("Eve", "UK"),
                new Tourist("Frank", "USA"),
                new Tourist("Grace", "UK"),
                new Tourist("Henry", "USA"),
                new Tourist("Ivy", "UK")
        );

        GroupedEnumerable<Tourist, String, Tourist> groupedTourists = Enumerable.groupBy(
                tourists,
                tourist -> tourist.getLocation(), // Key selector
                Function.identity() // Element selector
        );

        // Output the results
        for (IGrouping<String, Tourist> group : groupedTourists) {
            System.out.println("Group: " + group.getKey());
            for (Tourist tourist : group) {
                System.out.println(" - " + tourist.getName());
            }
        }
    }
}

class Tourist {
    String name;
    String location;

    public Tourist(String name, String location) {
        this.name = name;
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}





// 2 ------ Interface IGrouping để nhóm các phần tử của một Iterable theo một key nào đó
interface IGrouping<TKey, TElement> extends Iterable<TElement> {
    TKey getKey();
}

// Class Grouping để lưu trữ thông tin về nhóm
class Grouping<TKey, TElement> implements IGrouping<TKey, TElement> {
    private final TKey key;
    private final List<TElement> elements = new ArrayList<>();

    public Grouping(TKey key) {
        this.key = key;
    }

    public void add(TElement element) {
        elements.add(element);
    }

    @Override
    public Iterator<TElement> iterator() {
        return elements.iterator();
    }

    @Override
    public TKey getKey() {
        return key;
    }
}

// 3 ----- Class GroupedEnumerable để nhóm các phần tử của một Iterable theo một key nào đó
class GroupedEnumerable<TSource, TKey, TElement> implements Iterable<Grouping<TKey, TElement>> {
    private final Iterable<TSource> source;
    private final Function<TSource, TKey> keySelector;
    private final Function<TSource, TElement> elementSelector;
    private final EqualityComparer<TKey> comparer;

    public GroupedEnumerable(Iterable<TSource> source, Function<TSource, TKey> keySelector, Function<TSource, TElement> elementSelector, EqualityComparer<TKey> comparer) {
        this.source = source;
        this.keySelector = keySelector;
        this.elementSelector = elementSelector != null ? elementSelector : (Function<TSource, TElement>) Function.identity();
        this.comparer = comparer;
    }

    @Override
    public Iterator<Grouping<TKey, TElement>> iterator() {
        Map<TKey, Grouping<TKey, TElement>> groups = new HashMap<>();
        for (TSource item : source) {
            TKey key = keySelector.apply(item);
            groups.computeIfAbsent(key, k -> new Grouping<>(k)).add(elementSelector.apply(item));
        }
        return groups.values().iterator();
    }

    @Override
    public void forEach(Consumer<? super Grouping<TKey, TElement>> action) {
        Iterable.super.forEach(action);
    }

    @Override
    public Spliterator<Grouping<TKey, TElement>> spliterator() {
        return Iterable.super.spliterator();
    }
}

// 4 ------- Class EqualityComparer để so sánh 2 đối tượng có cùng kiểu dữ liệu
class EqualityComparer<T> {
    public boolean equals(T x, T y) {
        return x == null ? y == null : x.equals(y);
    }

    public int hashCode(T obj) {
        return obj == null ? 0 : obj.hashCode();
    }
}
