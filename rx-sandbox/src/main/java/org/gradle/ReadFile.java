package org.gradle;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import rx.Observable;

public class ReadFile {

	public static void main(String[] args) throws Exception {
		Stream<String[]> linhas = getSource();
		Stream<Pojo> elements = parse(linhas);
		
		elements.forEach(element -> 
			Observable.just(element)
			.filter(x-> x.getElement1().equals("001") || x.getElement1().equals("002"))
			.subscribe(i -> System.out.println(i.getElement1())));
	
	
	}

	private static Stream<Pojo> parse(Stream<String[]> linhas) {
		return linhas.map(x-> new Pojo(x[1],x[2],x[3]));
	}

	private static Stream<String[]> getSource() throws URISyntaxException,IOException {
		Path txt = Paths.get(ReadFile.class.getResource("/csvFile.txt").toURI());
		Stream<String[]> linhas = Files.lines(txt, Charset.defaultCharset()).map(line -> line.split("\\|"));
		return linhas;
	}
	

}
