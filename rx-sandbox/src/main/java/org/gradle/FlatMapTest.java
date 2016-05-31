package org.gradle;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FlatMapTest {
	
	
	public static void main(String[] args) {
		flatMap() ;
	}
	public static void flatMap() {
		
        List<Developer> team = new ArrayList<>();
        Developer polyglot = new Developer("esoteric");
        polyglot.add("clojure");
        polyglot.add("scala");
        polyglot.add("groovy");
        polyglot.add("go");

        Developer busy = new Developer("pragmatic");
        busy.add("java");
        busy.add("javascript");

        team.add(polyglot);
        team.add(busy);

        List<String> teamLanguages = team.stream()
                .map(d -> d.getLanguages())
                .flatMap(l -> l.stream())
                .collect(Collectors.toList());
        
        teamLanguages.forEach(x-> System.out.println(x));
        
    }
}
