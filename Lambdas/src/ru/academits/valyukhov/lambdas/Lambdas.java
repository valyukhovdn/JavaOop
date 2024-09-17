package ru.academits.valyukhov.lambdas;

import java.util.*;
import java.util.stream.Collectors;

public class Lambdas {
    public static void main(String[] args) {
        List<Person> persons = Arrays.asList(
                new Person("Иван", 16),
                new Person("Елена", 16),
                new Person("Максим", 18),
                new Person("Пётр", 23),
                new Person("Елена", 45),
                new Person("Полина", 23),
                new Person("Денис", 12),
                new Person("Елена", 27),
                new Person("Андрей", 16),
                new Person("Иван", 47),
                new Person("Андрей", 21),
                new Person("Иван", 56)
        );

        System.out.println();
        System.out.println("Полный список людей:");

        persons.forEach(System.out::println);

        // А) получить список уникальных имен
        List<String> uniqueNamesList = persons.stream()
                .map(Person::getName)
                .distinct()
                .sorted()
                .toList();

        // Б) вывести список уникальных имен в формате:
        //    Имена: Иван, Сергей, Петр.
        System.out.println();
        System.out.println("Список уникальных имен в заданном формате:");
        String uniqueNames = uniqueNamesList.stream().collect(Collectors.joining(", ", "Имена: ", "."));
        System.out.println(uniqueNames);

        // В) получить список людей младше 18, посчитать для них средний возраст
        System.out.println();
        System.out.println("Список людей младше 18 лет:");

        List<Person> peopleUnder18 = persons.stream()
                .filter(person -> person.getAge() < 18)
                .toList();

        if (peopleUnder18.isEmpty()) {
            System.out.println("В списке нет людей младше 18 лет.");
        } else {
            peopleUnder18.forEach(System.out::println);
        }

        System.out.println();
        System.out.print("Средний возраст людей младше 18 лет: ");

        OptionalDouble peopleUnder18AverageAge = persons.stream()
                .mapToInt(Person::getAge)
                .filter(age -> age < 18)
                .average();

        if (peopleUnder18AverageAge.isEmpty()) {
            System.out.println("в списке нет людей младше 18 лет.");
        } else {
            System.out.printf("%3.2f", peopleUnder18AverageAge.getAsDouble());
            System.out.println();
        }

        // Г) при помощи группировки получить Map, в котором ключи имена, а значения средний возраст
        Map<String, Double> averageAgesByNames = persons.stream()
                .collect(Collectors.groupingBy(Person::getName, Collectors.averagingInt(Person::getAge)));

        System.out.println();
        System.out.println("Средний возраст по именам:");

        averageAgesByNames.forEach((name, averageAge) -> {
            System.out.printf("%-10s: %3.2f", name, averageAge);
            System.out.println();
        });

        // Д) получить людей, возраст которых от 20 до 45,
        //    вывести в консоль их имена в порядке убывания возраста
        System.out.println();
        System.out.println("Список имён людей в возрасте от 20 до 45 в порядке убывания возраста:");

        persons.stream()
                .filter(person -> person.getAge() >= 20 && person.getAge() <= 45)
                .sorted((person1, person2) -> person2.getAge() - person1.getAge())
                .forEach(person -> {
                    System.out.printf("%-10s (%2d)", person.getName(), person.getAge());
                    System.out.println();
                });
    }
}