package ru.academits.valyukhov;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

class Person {
    private final String name;
    private int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    @Override
    public String toString() {
        return String.format("name: %7s, age: %-5d", name, age);
    }
}

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
        System.out.println();
        System.out.println("Список уникальных имен:");

        persons
                .stream()
                .map(Person::getName)
                .distinct()
                .sorted()
                .forEach(System.out::println);

        // Б) вывести список уникальных имен в формате:
        //    Имена: Иван, Сергей, Петр.
        System.out.println();
        System.out.println("Список уникальных имен в заданном формате:");

        Optional<String> namesInFormat = persons
                .stream()
                .map(Person::getName)
                .distinct()
                .sorted()
                .reduce((name1, name2) -> name1 + ", " + name2);

        System.out.print("Имена: ");
        namesInFormat.ifPresent(System.out::println);

        // В) получить список людей младше 18, посчитать для них средний возраст
        System.out.println();
        System.out.println("Список людей младше 18 лет:");

        boolean hasPeopleUnder18 = persons
                .stream()
                .anyMatch(person -> person.getAge() < 18);

        if (hasPeopleUnder18) {
            persons
                    .stream()
                    .filter(person -> person.getAge() < 18)
                    .forEach(System.out::println);
        } else {
            System.out.println("В списке нет людей младше 18 лет.");
        }

        System.out.println();
        System.out.print("Средний возраст людей младше 18 лет: ");

        OptionalDouble optionalAverageAge = persons
                .stream()
                .mapToDouble(Person::getAge)
                .filter(age -> age < 18)
                .average();

        if (optionalAverageAge.isPresent()) {
            System.out.printf("%3.2f\n", optionalAverageAge.getAsDouble());
        } else {
            System.out.println("в списке нет людей младше 18 лет.");
        }

        // Г) при помощи группировки получить Map, в котором ключи имена, а значения средний возраст
        Map<String, List<Person>> personsByNames = persons
                .stream()
                .collect(Collectors.groupingBy(Person::getName));

        Map<String, Double> averageAgesByNames = new HashMap<>();

        personsByNames.forEach((name, personsList) -> averageAgesByNames.put(name, personsList.stream()
                .mapToInt(Person::getAge).average().getAsDouble()));

        System.out.println();
        System.out.println("Средний возраст по именам: ");
        averageAgesByNames.forEach((name, averageAge) -> System.out.printf("%-10s: %3.2f\n", name, averageAge));

        // Д) получить людей, возраст которых от 20 до 45,
        //    вывести в консоль их имена в порядке убывания возраста
        System.out.println();
        System.out.println("Список имён людей в возрасте от 20 до 45 в порядке убывания возраста:");

        Predicate<Person> upperAgeLimit = person -> person.getAge() <= 45;
        Predicate<Person> ageRange = upperAgeLimit.and(person -> person.getAge() >= 20);

        persons.stream()
                .filter(ageRange)
                .sorted((person1, person2) -> person2.getAge() - person1.getAge())
                .forEach(person -> System.out.printf("%-10s (%2d)\n", person.getName(), person.getAge()));
    }
}