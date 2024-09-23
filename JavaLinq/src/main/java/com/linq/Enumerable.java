package com.linq;

import java.util.function.Function;

// 1 ----- Class Enumerable để nhóm các phần tử của một Iterable theo một key nào đó
public class Enumerable {
    public static <TSource, TKey> GroupedEnumerable<TSource, TKey, TSource> groupBy(
            Iterable<TSource> source,
            Function<TSource, TKey> keySelector) {
        // Sử dụng Function.identity() cho elementSelector nếu không có
        return new GroupedEnumerable<>(source, keySelector, Function.identity(), null);
    }

    public static <TSource, TKey> GroupedEnumerable<TSource, TKey, Object> groupBy(
            Iterable<TSource> source,
            Function<TSource, TKey> keySelector,
            EqualityComparer<TKey> comparer) {
        return new GroupedEnumerable<>(source, keySelector, null, comparer);
    }

    public static <TSource, TKey, TElement> GroupedEnumerable<TSource, TKey, TElement> groupBy(
            Iterable<TSource> source,
            Function<TSource, TKey> keySelector,
            Function<TSource, TElement> elementSelector) {
        return new GroupedEnumerable<>(source, keySelector, elementSelector, null);
    }

    public static <TSource, TKey, TElement> GroupedEnumerable<TSource, TKey, TElement> groupBy(
            Iterable<TSource> source,
            Function<TSource, TKey> keySelector,
            Function<TSource, TElement> elementSelector,
            EqualityComparer<TKey> comparer) {
        return new GroupedEnumerable<>(source, keySelector, elementSelector, comparer);
    }
}
