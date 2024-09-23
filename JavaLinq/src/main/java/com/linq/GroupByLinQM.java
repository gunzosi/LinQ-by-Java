package com.linq;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;

public class GroupByLinQM {

    public static void main(String[] args) {
        List<Pet> petsList = new ArrayList<>();
        petsList.add(new Pet("Barley", 8.3));
        petsList.add(new Pet("Boots", 4.9));
        petsList.add(new Pet("Whiskers", 1.5));
        petsList.add(new Pet("Daisy", 4.3));

        // Sử dụng phương thức GroupBy với chỉ khóa
        Map<Double, List<Pet>> groupedPetsByAge = groupBy(petsList, pet -> Math.floor(pet.age));
        printGroupedPets(groupedPetsByAge);

        // Sử dụng phương thức GroupBy với khóa và giá trị dự án
        Map<Double, List<String>> groupedPetsNamesByAge = groupByWithElement(petsList, pet -> Math.floor(pet.age), pet -> pet.name);
        printGroupedPetsNames(groupedPetsNamesByAge);

        // Sử dụng phương thức GroupBy với khóa và kết quả tùy chỉnh
        Map<Double, String> customGroupedPets = groupByWithCustomResult(petsList, pet -> Math.floor(pet.age), (key, pets) -> "Total pets: " + pets.size());
        printCustomGroupedPets(customGroupedPets);

        // Sử dụng phương thức GroupBy với khóa và giá trị dự án (có so sánh khóa)
        Comparator<Double> ageComparator = Double::compareTo;
        Map<Double, List<String>> sortedGroupedPetsNames = groupByWithComparer(petsList, pet -> Math.floor(pet.age), pet -> pet.name, ageComparator);
        printGroupedPetsNames(sortedGroupedPetsNames);

        // Sử dụng phương thức GroupBy với khóa và kết quả tùy chỉnh (có so sánh khóa)
        Map<Double, String> sortedCustomGroupedPets = groupByWithComparerAndCustomResult(petsList, pet -> Math.floor(pet.age), (key, pets) -> "Total pets: " + pets.size(), ageComparator);
        printCustomGroupedPets(sortedCustomGroupedPets);
    }

    // GroupBy với chỉ khóa
    public static <T, K> Map<K, List<T>> groupBy(List<T> list, Function<T, K> keyExtractor) {
        Map<K, List<T>> groupedMap = new HashMap<>();
        for (T item : list) {
            K key = keyExtractor.apply(item);
            groupedMap.putIfAbsent(key, new ArrayList<>());
            groupedMap.get(key).add(item);
        }
        return groupedMap;
    }

    // GroupBy với khóa và giá trị dự án
    public static <T, K, E> Map<K, List<E>> groupByWithElement(List<T> list, Function<T, K> keyExtractor, Function<T, E> elementExtractor) {
        Map<K, List<E>> groupedMap = new HashMap<>();
        for (T item : list) {
            K key = keyExtractor.apply(item);
            E value = elementExtractor.apply(item);
            groupedMap.putIfAbsent(key, new ArrayList<>());
            groupedMap.get(key).add(value);
        }
        return groupedMap;
    }

    // GroupBy với khóa và kết quả tùy chỉnh
    public static <T, K, R> Map<K, R> groupByWithCustomResult(List<T> list, Function<T, K> keyExtractor, BiFunction<K, List<T>, R> elemExtractor) {
        Map<K, List<T>> groupedMap = groupBy(list, keyExtractor);
        Map<K, R> resultMap = new HashMap<>();
        for (Map.Entry<K, List<T>> entry : groupedMap.entrySet()) {
            resultMap.put(entry.getKey(), elemExtractor.apply(entry.getKey(), entry.getValue()));
        }
        return resultMap;
    }

    // GroupBy với khóa và giá trị dự án (có so sánh khóa)
    public static <T, K, E> Map<K, List<E>> groupByWithComparer(List<T> list, Function<T, K> keyExtractor, Function<T, E> elementExtractor, Comparator<K> comparer) {
        Map<K, List<E>> groupedMap = groupByWithElement(list, keyExtractor, elementExtractor);
        // Sắp xếp nếu cần
        List<K> sortedKeys = new ArrayList<>(groupedMap.keySet());
        sortedKeys.sort(comparer);

        Map<K, List<E>> sortedMap = new LinkedHashMap<>();
        for (K key : sortedKeys) {
            sortedMap.put(key, groupedMap.get(key));
        }
        return sortedMap;
    }

    // GroupBy với khóa và kết quả tùy chỉnh (có so sánh khóa)
    public static <T, K, R> Map<K, R> groupByWithComparerAndCustomResult(List<T> list, Function<T, K> keyExtractor, BiFunction<K, List<T>, R> elementExtractor, Comparator<K> comparer) {
        Map<K, R> resultMap = groupByWithCustomResult(list, keyExtractor, elementExtractor);
        return resultMap;
    }

    /// ----------------------------- HELPER METHODS -----------------------------

    // Phương thức in kết quả nhóm
    private static void printGroupedPets(Map<Double, List<Pet>> groupedPets) {
        for (Map.Entry<Double, List<Pet>> entry : groupedPets.entrySet()) {
            System.out.println("Age group: " + entry.getKey());
            System.out.println("Pets in this group: ");
            for (Pet pet : entry.getValue()) {
                System.out.println(" - " + pet.name + ", Age: " + pet.age);
            }
            System.out.println();
        }
    }

    // Phương thức in tên của thú cưng trong nhóm
    private static void printGroupedPetsNames(Map<Double, List<String>> groupedPets) {
        for (Map.Entry<Double, List<String>> entry : groupedPets.entrySet()) {
            System.out.println("Age group: " + entry.getKey());
            System.out.println("Pet names in this group: " + entry.getValue());
            System.out.println();
        }
    }

    // Phương thức in kết quả tùy chỉnh
    private static void printCustomGroupedPets(Map<Double, String> groupedPets) {
        for (Map.Entry<Double, String> entry : groupedPets.entrySet()) {
            System.out.println("Age group: " + entry.getKey());
            System.out.println(entry.getValue());
            System.out.println();
        }
    }
}


class Pet {
    String name;
    double age;

    Pet(String name, double age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getAge() {
        return age;
    }

    public void setAge(double age) {
        this.age = age;
    }
}
