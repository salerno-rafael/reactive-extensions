package org.gradle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import rx.Observable;

public class GroupByTest {

	public static void main2(String[] args) {
		Observable.range(1, 10)
		.groupBy(n -> n % 2 == 0)
		.flatMap(grp2 -> grp2.toList())
		.subscribe(x -> System.out.println(x));
	}

	public static void main(String[] args) {
		List<Person> people = new ArrayList<>();
		people.add(new Person("John", "London", 21));
		people.add(new Person("Swann", "London", 21));
		people.add(new Person("Kevin", "London", 23));
		people.add(new Person("Monobo", "Tokyo", 23));
		people.add(new Person("Sam", "Paris", 23));
		people.add(new Person("Nadal", "Paris", 31));

		Map<String, List<Person>> personByCity = new HashMap<>();

		personByCity = people.stream().collect(Collectors.groupingBy(Person::getCity));

		System.out.println("Person grouped by cities in Java 8: " + personByCity);

		Map<Integer, List<Person>> personByAge = people.stream().collect(Collectors.groupingBy(Person::getAge));
		System.out.println("Person grouped by age in Java 8: " + personByAge);

	}

}
